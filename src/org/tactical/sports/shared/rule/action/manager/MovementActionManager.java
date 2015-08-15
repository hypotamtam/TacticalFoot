package org.tactical.sports.shared.rule.action.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.tactical.sports.shared.domain.playground.tile.PlayerDetails;
import org.tactical.sports.shared.domain.playground.tile.Tile;
import org.tactical.sports.shared.rule.RulesConstants;
import org.tactical.sports.shared.rule.action.Action;
import org.tactical.sports.shared.rule.action.ActionType;

public class MovementActionManager extends AbstractActionManager {

	private MovementArea m_movementArea;
	private int m_remainingMovement;

	private ActionManagerListener m_listener = null;
	private List<Tile> m_movementPath;

	private Action m_action;

	@Override
	public ActionType getType() {
		return ActionType.MOVE;
	}

	@Override
	public boolean isAllowForTile(Tile tile) {
		return tile.hasPlayer();
	}

	@Override
	public void init(Tile tile, PlayerDetails player) {
		m_action = new Action(getType(), tile, player);
		m_remainingMovement = tile.hasBall() ? RulesConstants.MAX_MOVEMENT_SIZE_WITH_BALL
				: RulesConstants.MAX_MOVEMENT_SIZE;
		m_movementArea = new MovementArea(getPlayground(), tile, m_remainingMovement);
		m_movementPath = new ArrayList<Tile>();
		m_movementPath.add(tile);
		updateAction();
	}

	@Override
	public void update(Tile tile) {
		if (m_movementArea.isMovementAllowed(tile)) {
			List<Tile> path = m_movementArea.getPath(tile);
			path.remove(0);
			m_movementPath.addAll(path);
			m_remainingMovement -= m_movementArea.getAllowedMovements().get(tile);
			updateAction();
			if (m_remainingMovement == 0) {
				stop();
			} else {
				m_movementArea = new MovementArea(getPlayground(), tile, m_remainingMovement);
			}
		}
	}

	private void stop() {
		m_movementArea = null;
		if (m_listener != null) {
			m_listener.stop();
			m_movementPath = null;
		}
	}

	private void updateAction() {
		if (m_listener != null) {
			m_action.setTileArea(m_movementPath);
			m_listener.actionDefined(m_action);
		}
	}

	@Override
	public void cancel() {
		m_movementArea = null;
		m_movementPath = null;
		m_listener.actionCancel(m_action);
	}

	@Override
	public Set<Tile> getActionPosibility() {
		if (m_movementArea != null) {
			Set<Tile> actionPosibility = new HashSet<Tile>(m_movementArea.getAllowedMovements().keySet());
			actionPosibility.removeAll(m_movementPath);
			return actionPosibility;
		}
		return null;
	}

	@Override
	public Action getPreview(Tile tile) {
		List<Tile> path = new ArrayList<Tile>(m_movementPath);
		if (m_movementArea.isMovementAllowed(tile)) {
			path.addAll(m_movementArea.getPath(tile));
		}
		m_action.setTileArea(path);
		return m_action;
	}

	@Override
	public void setListener(ActionManagerListener listener) {
		m_listener = listener;
	}
}
