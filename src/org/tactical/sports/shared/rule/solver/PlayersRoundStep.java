package org.tactical.sports.shared.rule.solver;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.tactical.sports.shared.domain.playground.tile.TileIndex;

public class PlayersRoundStep implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<Long, Movement> m_playersMovement = new HashMap<Long, Movement>();
	private Long m_playerWithBall;
	private TileIndex m_tilePlayerWithBall;

	public void setMovementForPlayer(Movement movement, Long playerId) {
		m_playersMovement.put(playerId, movement);
	}

	public Map<Long, Movement> getMovements() {
		return m_playersMovement;
	}

	public Long getPlayerWithBall() {
		return m_playerWithBall;
	}

	public TileIndex getPlayerWithBallTile() {
		return m_tilePlayerWithBall;
	}

	public void setPlayerWithBall(Long playerWithBall, TileIndex index) {
		m_playerWithBall = playerWithBall;
		m_tilePlayerWithBall = index;
	}

}