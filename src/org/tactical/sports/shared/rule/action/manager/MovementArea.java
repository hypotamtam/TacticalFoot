package org.tactical.sports.shared.rule.action.manager;

import java.util.List;
import java.util.Map;

import org.tactical.sports.shared.domain.playground.Playground;
import org.tactical.sports.shared.domain.playground.tile.Tile;
import org.tactical.sports.shared.domain.playground.tile.TileSearcher;

public class MovementArea {
	TileSearcher m_searcher;
	private Tile m_from;

	public MovementArea(Playground playground, Tile from, int maxMovement) {
		m_from = from;
		m_searcher = new TileSearcher(playground, from, maxMovement);
	}
	
	public boolean isMovementAllowed(Tile to) {
		return m_searcher.getMarkedItems().containsKey(to);
	}
	
	public Map<Tile, Integer> getAllowedMovements() {
		return m_searcher.getMarkedItems();
	}
	
	public Tile getFrom() {
		return m_from;
	}

	public List<Tile> getPath(Tile to) {
		return m_searcher.getPathTo(to);
	}
}
