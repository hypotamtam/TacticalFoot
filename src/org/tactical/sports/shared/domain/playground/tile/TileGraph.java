package org.tactical.sports.shared.domain.playground.tile;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TileGraph implements Serializable {

	private static final long serialVersionUID = 1L;
	private int m_width;
	private int m_height;
	private byte m_smallerColumnIndex; // TODO: remove this?
	private Map<TileIndex, Tile> m_tiles;

	public TileGraph() {
	}
	
	public TileGraph(int width, int height, byte smallerColumnIndex) {
		m_width = width;
		m_height = height;
		m_smallerColumnIndex = smallerColumnIndex;
		m_tiles = new HashMap<TileIndex, Tile>();
	}
	
	public Tile getTile(TileIndex index) {
		if ((index.x < 0) || (index.x >= m_width)) {
			return null;
		}
		if ((index.y < 0) || (index.y >= getHeight(index.x))) {
			return null;
		}
		return m_tiles.get(index);
	}
	
	public Tile addTile(int x, int y, TileType type) {
		int dy = ((x % 2) == m_smallerColumnIndex) ? 0 : 1;
		Tile tile = new Tile(x, y, type);

		Tile neighboor = getTile(new TileIndex(x, y - 1));
		if (neighboor != null) {
			tile.addNeighbour(neighboor.getIndex());
			neighboor.addNeighbour(tile.getIndex());
		}
		
		neighboor = getTile(new TileIndex(x, y + 1));
		if (neighboor != null) {
			tile.addNeighbour(neighboor.getIndex());
			neighboor.addNeighbour(tile.getIndex());
		}
		
		neighboor = getTile(new TileIndex(x - 1, y - dy));
		if (neighboor != null) {
			tile.addNeighbour(neighboor.getIndex());
			neighboor.addNeighbour(tile.getIndex());
		}
		
		neighboor = getTile(new TileIndex(x + 1, y - dy));
		if (neighboor != null) {
			tile.addNeighbour(neighboor.getIndex());
			neighboor.addNeighbour(tile.getIndex());
		}
		
		neighboor = getTile(new TileIndex(x - 1, y - dy + 1));
		if (neighboor != null) {
			tile.addNeighbour(neighboor.getIndex());
			neighboor.addNeighbour(tile.getIndex());
		}
		
		neighboor = getTile(new TileIndex(x + 1, y - dy + 1));
		if (neighboor != null) {
			tile.addNeighbour(neighboor.getIndex());
			neighboor.addNeighbour(tile.getIndex());
		}

		m_tiles.put(tile.getIndex(), tile);
		return tile;
	}
	
	public Collection<Tile> getTiles() {
		return m_tiles.values();
	}
	
	private int getHeight(int x) {
		return ((x % 2) == m_smallerColumnIndex ? m_height-1 : m_height);
	}

	@Override
	public String toString() {
		return "TileGraph [m_tiles=" + getTiles() + "]";
	}

}
