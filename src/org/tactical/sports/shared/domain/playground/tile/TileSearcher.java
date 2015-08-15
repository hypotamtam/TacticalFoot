package org.tactical.sports.shared.domain.playground.tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tactical.sports.shared.domain.playground.Playground;

public class TileSearcher {
	private Tile m_from;
	private int m_maxDepth;
	private Map<Tile, Integer> m_marked = new HashMap<Tile, Integer>();
	private Playground m_playground;

	public TileSearcher(Playground playground, Tile from, int maxDepth) {
		this(playground, from, maxDepth, null);
	}
	
	public TileSearcher(Playground playground,  Tile from, int maxDepth, TileSelector selector) {
		m_playground = playground;
		m_from = from;
		m_maxDepth = maxDepth;
		
		buildFloodingGraph(m_from, m_maxDepth, selector);
	}

	private void buildFloodingGraph(Tile start, int maxDepth, TileSelector selector) {
		List<Tile> remaining = new ArrayList<Tile>();		
		
		m_marked.put(start, 0);
		remaining.add(start);
		
		while (! remaining.isEmpty()) {
			Tile item = remaining.remove(0);
			Integer depth = m_marked.get(item);
			if (depth == maxDepth) {
				continue;
			}
			
			for (TileIndex neighbourIndex : item.getNeighbours()) {
				Tile neighbour = m_playground.getTile(neighbourIndex);
				Integer currentDepth = m_marked.get(neighbour);
				if ((currentDepth != null) && (depth+1 >= currentDepth)) {
					continue;
				}
				if ((selector != null) && (! selector.accept(neighbour))) {
					continue;
				}
				
				m_marked.put(neighbour, depth+1);
				remaining.add(neighbour);
			}
		}
	}
	
	public Map<Tile, Integer> getMarkedItems() {
		return m_marked;
	}
	
	public List<Tile> getPathTo(Tile to) {
		Integer toDepth = m_marked.get(to);
		if (toDepth == null) {
			return null;
		}
		
		List<Tile> path = new ArrayList<Tile>();
		Tile pos = to;
		path.add(0, pos);
		while (toDepth > 0) {
			pos = findNeighbourAtDepth(pos, toDepth-1);			
			path.add(0, pos);
			toDepth--;
		} 
		
		return path;
	}
	
	private Tile findNeighbourAtDepth(Tile pos, int depth) {
		for (TileIndex neighbourIndex : pos.getNeighbours()) {
			Tile neighbour = m_playground.getTile(neighbourIndex);
			if (m_marked.containsKey(neighbour) && (m_marked.get(neighbour) == depth)) {
				return neighbour;
			}
		}
		
		throw new IllegalArgumentException("could not find neighbour at depth " + depth + " for " + pos);
	}
}
