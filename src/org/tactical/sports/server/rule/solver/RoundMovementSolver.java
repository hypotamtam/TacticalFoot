package org.tactical.sports.server.rule.solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.tactical.sports.shared.domain.playground.tile.PlayerDetails;
import org.tactical.sports.shared.domain.playground.tile.Tile;
import org.tactical.sports.shared.domain.playground.tile.TileIndex;
import org.tactical.sports.shared.rule.action.Action;
import org.tactical.sports.shared.rule.action.ActionType;
import org.tactical.sports.shared.rule.solver.Movement;
import org.tactical.sports.shared.rule.solver.PlayersRoundStep;
import org.tactical.sports.shared.rule.solver.RoundResolution;

public class RoundMovementSolver extends AbstractRoundActionSolver {

	private boolean m_hasFinished;

	private Set<PlayerDetails> m_playerWithRemainingAction;
	
	@Override
	public boolean hasFinished() {
		return m_hasFinished;
	}

	@Override
	protected boolean allowAction(Action action) {
		return (action.getType() == ActionType.MOVE);
	}

	@Override
	public void presolvedRoundActions() {
		m_playerWithRemainingAction = new HashSet<PlayerDetails>();
		ArrayList<Action> actionWithoutMovement = new ArrayList<Action>();
		for (Action action : getActions()) {
			if (action.getArea().size() == 1) {
				actionWithoutMovement.add(action);
			} else {
				m_playerWithRemainingAction.add(action.getPlayer());
			}
		}
		getActions().removeAll(actionWithoutMovement);
	};

	@Override
	public void solvedStep(int stepIndex, RoundResolution roundResolution) {
		ArrayList<Action> actionSolved = new ArrayList<Action>();
		PlayersRoundStep step = new PlayersRoundStep();

		for (Action action : getActions()) {
			PlayerDetails player = action.getPlayer();
			if (action.getArea().size() <= stepIndex) {
				break;
			}
			Object[] aimedTiles =  action.getArea().toArray();
			Tile aimedTile = (Tile) aimedTiles[stepIndex];
			aimedTile = getPlayground().getTile(aimedTile.getIndex());
			if (stepIndex != 0) {
				if (stepIndex == (aimedTiles.length - 1)) {
					if (needsToAddAnExtraMovement(player, aimedTile)) {
						Tile nextTile = getRandomNeighbour(aimedTile);
						ArrayList<Tile> actionArea = new ArrayList<Tile>(action.getArea());
						actionArea.add(nextTile);
						action.setTileArea(actionArea);
					} else { 
						m_playerWithRemainingAction.remove(player);
						actionSolved.add(action);
					}
				}

				Tile oldTile = (stepIndex > 0) ? (Tile) aimedTiles[stepIndex - 1] : action.getStartTile();
				oldTile = getPlayground().getTile(oldTile.getIndex());
				movePlayer(player, oldTile, aimedTile);
				Movement movement = new Movement(oldTile.getIndex(), aimedTile.getIndex());
				if (player.hasBall()) {
					roundResolution.addBallMouvement(stepIndex, movement);
				}
				
				step.setMovementForPlayer(movement, player.getId());
			}
		}

		roundResolution.addPlayersStep(step);
		getActions().removeAll(actionSolved);

		m_hasFinished = getActions().isEmpty();
	}

	private boolean needsToAddAnExtraMovement(PlayerDetails player, Tile tile) {
		if (tile.hasPlayer()) {
			for (PlayerDetails playerInTile : tile.getPlayers()) {
				if (player.equals(playerInTile)) {
					break;
				}
				if (!m_playerWithRemainingAction.contains(playerInTile)) {
					return true;
				}
			}
		} 
		return false;
	}
	
	private void movePlayer(PlayerDetails player, Tile oldTile, Tile newTile) {
		newTile.addPlayer(player);
		oldTile.removePlayer(player);
	}

	private Tile getRandomNeighbour(Tile aimedTile) {
		ArrayList<Tile> freeTiles = new ArrayList<Tile>();
		for (TileIndex neighbourIndex : aimedTile.getNeighbours()) {
			Tile tile = getPlayground().getTile(neighbourIndex);
			if (!tile.hasPlayer()) {
				freeTiles.add(tile);
			}
		}

		if (freeTiles.isEmpty()) {
			for (TileIndex neighbourIndex : aimedTile.getNeighbours()) {
				freeTiles.add(getPlayground().getTile(neighbourIndex));
			}
		}
		return freeTiles.get(getRandomIndex(freeTiles.size()));
	}

}
