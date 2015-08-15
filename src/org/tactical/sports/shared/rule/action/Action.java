package org.tactical.sports.shared.rule.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.tactical.sports.shared.domain.playground.tile.PlayerDetails;
import org.tactical.sports.shared.domain.playground.tile.Tile;

public class Action implements Serializable {
	 
	private static final long serialVersionUID = 1L;
	
	private ActionType m_type;
	private PlayerDetails m_player;

	private Collection<Tile> m_area;
	private Tile m_startTile;
	
	public Action() {
	}
	
	public Action(ActionType type, Tile startTile, PlayerDetails player) {
		if (startTile.hasPlayer()) {
			m_type = type;
			m_player = player;
			m_startTile = startTile;
			m_area = new ArrayList<Tile>();
		};
	}
		
	public ActionType getType() {
		return m_type;
	}

	public PlayerDetails getPlayer() {
		return m_player;
	}
	
	public Collection<Tile> getArea() {
		return m_area;
	}
	
	public Tile getStartTile() {
		return m_startTile;
	}

	public void setTileArea(Collection<Tile> tiles) {
		m_area = tiles; 
	}
	
	public void addTileArea(Collection<Tile> tiles) {
		if (tiles != null) {
			m_area.addAll(tiles);
		}
	}
}
