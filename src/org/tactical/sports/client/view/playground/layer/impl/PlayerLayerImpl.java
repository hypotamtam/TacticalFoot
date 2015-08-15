package org.tactical.sports.client.view.playground.layer.impl;

import java.util.HashMap;
import java.util.Map;

import org.tactical.sports.client.resources.Res;
import org.tactical.sports.client.view.playground.MovementDirection;
import org.tactical.sports.client.view.playground.grid.GridPosition;
import org.tactical.sports.client.view.playground.grid.GridPositionHelper;
import org.tactical.sports.client.view.playground.layer.PlayerLayer;
import org.tactical.sports.shared.domain.playground.tile.TileIndex;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class PlayerLayerImpl extends LayoutPanel implements PlayerLayer {

	private GridPositionHelper m_tileManager;
	
	private Map<Long, PlayerView> m_players;
	private Map<PlayerView, GridPosition> m_playersPositions;
	private Image m_ball;
	
	public PlayerLayerImpl() {
		m_players = new HashMap<Long, PlayerView>();
		m_playersPositions = new HashMap<PlayerView, GridPosition>();
	}
	
	@Override
	public void addPlayer(long playerId, TileIndex index, boolean isLocal, boolean ishighlighable) {
		PlayerView player = new PlayerView(isLocal, ishighlighable);
		add(player);
		setPlayerPosition(player, index);
		m_players.put(playerId, player);
	}

	@Override
	public void movePlayer(long playerId, TileIndex destination) {
		PlayerView player = m_players.get(playerId);
		if (player == null) {
			throw new IllegalArgumentException("No player view found for id : " + playerId);
		}
		setPlayerPosition(player, destination);
		m_players.put(playerId, player);
	}
	
	private void setPlayerPosition(PlayerView player, TileIndex index) {
		GridPosition position = m_tileManager.getGridPosition(index);
		int x = position.getLeft() + (m_tileManager.getGridElementWidth() - player.getWidth()) / 2;
		int y = position.getTop() + (m_tileManager.getGridElementHeight() - player.getHeight()) / 2;
		setWidgetTopHeight(player, y, Unit.PX, player.getHeight(), Unit.PX);
		setWidgetLeftWidth(player, x, Unit.PX, player.getWidth(), Unit.PX);
		
		GridPosition lastPosition = m_playersPositions.get(player);
		MovementDirection nextDirection = getMovementDirection(position, lastPosition);
		player.setDirection(nextDirection);
		m_playersPositions.put(player, position);	
	}

	private MovementDirection getMovementDirection(GridPosition position, GridPosition lastPosition) {
		MovementDirection nextDirection = MovementDirection.NONE;
		if (lastPosition == null) {
			return nextDirection;
		}
		if (!position.equals(lastPosition)) {
			if (position.getTop() > lastPosition.getTop()) {
				if (position.getLeft() < lastPosition.getLeft()) {
					nextDirection = MovementDirection.DOWN_LEFT;
				} else if (position.getLeft() > lastPosition.getLeft()) {
					nextDirection = MovementDirection.DOWN_RIGHT;
				} else {
					nextDirection = MovementDirection.DOWN;					
				}
			} else {
				if (position.getLeft() < lastPosition.getLeft()) {
					nextDirection = MovementDirection.UP_LEFT;
				} else if (position.getLeft() > lastPosition.getLeft()) {
					nextDirection = MovementDirection.UP_RIGHT;
				} else {
					nextDirection = MovementDirection.UP;					
				}
			}
		}
		return nextDirection;
	}
	
	@Override
	public void setBall(TileIndex index) {
		if (m_ball == null) {
			m_ball = new Image(Res.INSTANCE.ball());
		}
		/*
		 * PATCH: Juste pour être sure que la ball est devant le joueur.
		 * En fonction de la direction il faudrait la passer derrier.
		 */
		m_ball.getElement().getStyle().setZIndex(100);
		add(m_ball);
		setBallPosition(index);
	}

	@Override
	public void moveBall(TileIndex index) {
		setBallPosition(index);
	}
	
	private void setBallPosition(TileIndex index) {
		GridPosition position = m_tileManager.getGridPosition(index);
		int x = position.getLeft() + (m_tileManager.getGridElementWidth() - m_ball.getWidth()) / 2;
		int y = position.getTop() + (m_tileManager.getGridElementHeight() + m_ball.getHeight()) / 2;
		setWidgetTopHeight(m_ball, y, Unit.PX, m_ball.getHeight(), Unit.PX);
		setWidgetLeftWidth(m_ball, x, Unit.PX, m_ball.getWidth(), Unit.PX);
	}

	@Override
	public Widget asWidget() {
		return this;
	}
	
	@Override
	public void setTileManager(GridPositionHelper manager) {
		m_tileManager = manager;
	}

	@Override
	public void update(double progress) {
		for (PlayerView player : m_players.values()) {
			player.update(progress);
		}
	}

	@Override
	public void stop() {
		for (PlayerView player : m_players.values()) {
			player.showIdleAnimation();
			player.setDirection(MovementDirection.NONE);
		}
	}
}
