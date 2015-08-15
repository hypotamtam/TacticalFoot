package org.tactical.sports.client.activity.presenter.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.tactical.sports.client.activity.presenter.AbstractPresenter;
import org.tactical.sports.client.event.action.ActionEvent;
import org.tactical.sports.client.event.action.ActionEventHandler;
import org.tactical.sports.client.event.action.ActionPosibilityEvent;
import org.tactical.sports.client.event.action.ActionPosibilityEventHandler;
import org.tactical.sports.client.event.game.GameEvent;
import org.tactical.sports.client.event.playground.PlayerSelectedEvent;
import org.tactical.sports.client.event.playground.PlaygroundChangedEvent;
import org.tactical.sports.client.event.playground.PlaygroundOutEvent;
import org.tactical.sports.client.event.playground.PlaygroundOverEvent;
import org.tactical.sports.client.event.playground.TileSelectedEvent;
import org.tactical.sports.client.event.round.RoundBeginEvent;
import org.tactical.sports.client.log.Logger;
import org.tactical.sports.client.view.playground.PlaygroundView;
import org.tactical.sports.client.view.playground.PlaygroundView.PlaygroundPresenter;
import org.tactical.sports.shared.Constants;
import org.tactical.sports.shared.domain.playground.Playground;
import org.tactical.sports.shared.domain.playground.tile.PlayerDetails;
import org.tactical.sports.shared.domain.playground.tile.Tile;
import org.tactical.sports.shared.domain.playground.tile.TileIndex;
import org.tactical.sports.shared.domain.playground.tile.TileType;
import org.tactical.sports.shared.rule.action.Action;
import org.tactical.sports.shared.rule.solver.Movement;
import org.tactical.sports.shared.rule.solver.PlayersRoundStep;
import org.tactical.sports.shared.rule.solver.RoundResolution;

import com.google.gwt.event.shared.EventBus;

public class PlaygroundPresenterImpl extends AbstractPresenter<PlaygroundView> implements PlaygroundPresenter, ActionPosibilityEventHandler, ActionEventHandler {

	private Playground m_playground;

	private RoundResolution m_roundResolution;
	private int m_currentRoundResolutionStep;

	private boolean m_isShowingRoundStep;

	private long m_userTeamId;

	public PlaygroundPresenterImpl(Playground playground, long userTeamId) {
		m_playground = playground;
		m_userTeamId = userTeamId;
		m_isShowingRoundStep = false;
	}

	public Playground getPlayground() {
		return m_playground;
	}

	@Override
	public void setDisplay(PlaygroundView display) {
		super.setDisplay(display);
		display.setSize(m_playground.getWidth(), m_playground.getHeight());
		Collection<Tile> tiles = m_playground.getTiles();
		for (Tile tile : tiles) {
			if (tile.getType() != TileType.normal) {
				display.setSpecialTile(tile.getIndex(), tile.getType());
			}

			if (tile.hasPlayer()) {
				for (PlayerDetails player : tile.getPlayers()) {
					Logger.log("Add player " + player + " to " + tile.getIndex());
					boolean isLocalTeamMember = m_playground.isALocalPlayer(player.getId());
					boolean isUserPlayer = m_userTeamId == player.getTeamId();
					display.addPlayer(player.getId(), tile.getIndex(), isLocalTeamMember, isUserPlayer);
				}
			}

			if (tile.hasBall()) {
				Logger.log("Set ball to " + tile.getIndex());
				display.setBall(tile.getIndex());
			}
		}
	}

	@Override
	public void onClick(TileIndex index) {
		if (!m_isShowingRoundStep) {
			Tile tile = m_playground.getTile(index);
			if (tile != null) {
				if (tile.hasPlayer()) {
					long localTeamId = m_userTeamId;
					for (PlayerDetails player : tile.getPlayers()) {
						if (player.getTeamId() == localTeamId) {
							getEventBus().fireEvent(new PlayerSelectedEvent(tile, player));
						}
					}
				}
				TileSelectedEvent tileEvent = new TileSelectedEvent();
				tileEvent.setTile(tile);
				getEventBus().fireEvent(tileEvent);
			}
		}
	}

	@Override
	public void onMouseOver(TileIndex index) {
		PlaygroundOverEvent event;
		if (index != null) {
			Tile tile = m_playground.getTile(index);
			if (tile != null) {
				event = new PlaygroundOverEvent(tile);
			} else {
				event = new PlaygroundOverEvent(null);
			}
		} else {
			event = new PlaygroundOverEvent(null);
		}
		getEventBus().fireEvent(event);
	}

	@Override
	public void onMouseOut() {
		PlaygroundOutEvent event = new PlaygroundOutEvent();
		getEventBus().fireEvent(event);
	}

	private Collection<TileIndex> convertTilesToIndices(Collection<Tile> tiles) {
		Collection<TileIndex> gridIndices = new ArrayList<TileIndex>();
		if (tiles != null) {
			for (Tile tile : tiles) {
				gridIndices.add(tile.getIndex());
			}
		}
		return gridIndices;
	}

	@Override
	public void onActionPosibilityChanged(ActionPosibilityEvent event) {
		switch (event.getState()) {
			case Init:
				getDisplay().initActionDefinition(convertTilesToIndices(event.getAffectedTiles()));
				break;
			case Update:
				getDisplay().updateActionDefinition(convertTilesToIndices(event.getAffectedTiles()));
				break;
			case Finish:
				getDisplay().finishActionDefinition();
				break;
		}
	}

	@Override
	public void onActionSet(ActionEvent actionEvent) {
		Action action = actionEvent.getAction();
		getDisplay().setAction(convertTilesToIndices(action.getArea()), action.getPlayer().getId());
	}

	@Override
	public void onActionCancel(ActionEvent actionEvent) {
		Action action = actionEvent.getAction();
		getDisplay().removeAction(action.getPlayer().getId());
	}

	@Override
	public void setEventBus(EventBus eventBus) {
		super.setEventBus(eventBus);
		eventBus.addHandler(ActionPosibilityEvent.TYPE, this);
		eventBus.addHandler(ActionEvent.TYPE, this);
	}

	public void showRoundSolution(RoundResolution roundResolution) {
		m_isShowingRoundStep = true;
		m_currentRoundResolutionStep = 0;
		m_roundResolution = roundResolution;
		Logger.log("#############################################");
		playRoundStep(m_currentRoundResolutionStep);
	}

	@Override
	public void onAnimationEnded() {
		m_currentRoundResolutionStep++;
		if (m_currentRoundResolutionStep == m_roundResolution.getRoundSetpCount()) {
			getDisplay().clearActions();
			m_isShowingRoundStep = false;
			GameEvent event = new GameEvent("Round " + (m_roundResolution.getRoundIndex() + 1));
			getEventBus().fireEvent(event);
			getEventBus().fireEvent(new PlaygroundChangedEvent(m_playground));
			getEventBus().fireEvent(new RoundBeginEvent());
			m_roundResolution = null;
			Logger.log("#############################################");
		} else {
			if (m_roundResolution.hasGoal(m_currentRoundResolutionStep)) {
				GameEvent event = new GameEvent("GOOOOAAAALLLLLL!!!!!!");
				getEventBus().fireEvent(event);
			}
			playRoundStep(m_currentRoundResolutionStep);
		}
	}

	private void playRoundStep(int index) {
		Logger.log("----------------------------------------");
		Logger.log("Play resolution step " + index);
		getDisplay().prepareAnimation();
		boolean needsToAnimate = false;
		Movement ballMovement = m_roundResolution.getBallindexForStep(m_currentRoundResolutionStep);
		if (ballMovement != null) {
			needsToAnimate = true;
			animateBall(ballMovement);
		}

		PlayersRoundStep step = m_roundResolution.getPlayersStep(m_currentRoundResolutionStep);
		if (step != null) {
			needsToAnimate = true;
			animatePlayers(step);
			Long playerIdWithBall = step.getPlayerWithBall();
			if (playerIdWithBall != null) {
				Tile ballTile = m_playground.getTile(step.getPlayerWithBallTile());
				ballTile.setHasBall(true);
				for (PlayerDetails playerDetails : ballTile.getPlayers()) {
					playerDetails.setHasBall(playerDetails.getId() == playerIdWithBall);
				}
			}
		}

		if (needsToAnimate) {
			getDisplay().startAnimation(Constants.ROUND_STEP_TIME_INTERVAL);
		} else {
			onAnimationEnded();
		}
		Logger.log("----------------------------------------");
	}

	private void animatePlayers(PlayersRoundStep step) {
		Map<Long, Movement> movements = step.getMovements();
		for (Long playerId : movements.keySet()) {
			Movement movement = movements.get(playerId);
			TileIndex movementEnd = movement.getEnd();
			getDisplay().movePlayer(playerId, movementEnd);
			Tile playerTile = m_playground.getTile(movement.getStart());
			PlayerDetails player = playerTile.getPlayer(playerId);
			if (player != null) {
				Logger.log("Move player " + player + " from  " + movement.getStart() + " to " + movement.getEnd());
				playerTile.removePlayer(player);
				playerTile = m_playground.getTile(movementEnd);
				playerTile.addPlayer(player);
			}
		}
	}

	private void animateBall(Movement ballMovement) {
		Logger.log("Move ball from  " + ballMovement.getStart() + " to " + ballMovement.getEnd());
		getDisplay().moveBall(ballMovement.getEnd());
		m_playground.getTile(ballMovement.getStart()).setHasBall(false);
		Tile endTile = m_playground.getTile(ballMovement.getEnd());
		endTile.setHasBall(true);
	}

}
