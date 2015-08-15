package org.tactical.sports.shared.rule.action.manager;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.tactical.sports.shared.domain.playground.tile.PlayerDetails;
import org.tactical.sports.shared.domain.playground.tile.Tile;
import org.tactical.sports.shared.domain.playground.tile.TileSearcher;
import org.tactical.sports.shared.rule.RulesConstants;
import org.tactical.sports.shared.rule.action.Action;
import org.tactical.sports.shared.rule.action.ActionType;

public class PassActionManager extends AbstractActionManager {


	private TileSearcher m_passAreaSearcher;
	
	private ActionManagerListener m_listener;
	private Action m_action;
	

	@Override
	public ActionType getType() {
		return ActionType.PASS;
	}

	@Override
	public boolean isAllowForTile(Tile tile) {
		return tile.hasBall() && tile.hasPlayer();
	}
	
	@Override
	public void init(Tile tile, PlayerDetails player) {
		m_action = new Action(getType(), tile, player);
		m_passAreaSearcher = new TileSearcher(getPlayground(), tile, RulesConstants.MAX_PASS_SIZE);
	}

	@Override
	public void update(Tile tile) {
		if (m_passAreaSearcher.getMarkedItems().keySet().contains(tile)) {
			updateAction(tile);
			m_listener.actionDefined(m_action);
			m_listener.stop();
		}
	}

	private void updateAction(Tile tile) {
		Collection<Tile> tileArea = new HashSet<Tile>();
		tileArea.add(m_action.getStartTile());
		if (m_passAreaSearcher.getMarkedItems().containsKey(tile)) {
			int distance = m_passAreaSearcher.getPathTo(tile).size();
			int errorRange = (distance < 4) ? 0 : 1;
			TileSearcher m_resultArea = new TileSearcher(getPlayground(), tile, errorRange);
			tileArea.addAll(m_resultArea.getMarkedItems().keySet());
		}
		m_action.setTileArea(tileArea);
	}

	@Override
	public void cancel() {
		m_passAreaSearcher = null;
	}

	@Override
	public void setListener(ActionManagerListener listener) {
		m_listener = listener;
	}

	@Override
	public Action getPreview(Tile tile) {
		updateAction(tile);
		return m_action;
	}
	
	@Override
	public Set<Tile> getActionPosibility() {
		if (m_passAreaSearcher != null) {
			Set<Tile> actionPosibility = new HashSet<Tile>(m_passAreaSearcher.getMarkedItems().keySet());
			actionPosibility.removeAll(m_action.getArea());
			return actionPosibility;
		}
		return null;
	}

}
