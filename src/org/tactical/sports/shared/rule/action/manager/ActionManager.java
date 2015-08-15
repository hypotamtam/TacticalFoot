package org.tactical.sports.shared.rule.action.manager;

import java.util.Set;

import org.tactical.sports.shared.domain.playground.Playground;
import org.tactical.sports.shared.domain.playground.tile.PlayerDetails;
import org.tactical.sports.shared.domain.playground.tile.Tile;
import org.tactical.sports.shared.rule.action.Action;
import org.tactical.sports.shared.rule.action.ActionType;

public interface ActionManager {
	ActionType getType();
	
	void setPlayground(Playground playground);
	boolean isAllowForTile(Tile tile);
	
	void init(Tile tile, PlayerDetails player);
	void update(Tile tile);
	void cancel();
	
	void setListener(ActionManagerListener listener);
	
	Set<Tile> getActionPosibility();
	Action getPreview(Tile tile);
}
