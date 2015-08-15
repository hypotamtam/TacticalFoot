package org.tactical.sports.shared.domain.playground.tile;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Define an immutable position object.
 * 
 * @author Fred
 */
public class Tile implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private TileIndex m_index;
	private Set<TileIndex> m_neighbours = new HashSet<TileIndex>();
	private TileType m_type; // Later, we could abstract this type so that each
								// sport uses it own types.
	private Map<Long, PlayerDetails> m_players;

	private boolean m_hasBall;

	// Constructor is hidden because this object should never be created outside
	// a TileGraph.
	Tile() {
		super();
	}
	
	Tile(int x, int y, TileType type) {
		m_index = new TileIndex(x, y);
		m_type = type;
		m_players = new HashMap<Long, PlayerDetails>();
	}

	void addNeighbour(TileIndex pos) {
		m_neighbours.add(pos);
	}

	public TileIndex getIndex() {
		return m_index;
	}

	public boolean hasPlayer() {
		return !m_players.isEmpty();
	}

	public boolean hasPlayer(long id) {
		return m_players.containsKey(id);
	}

	public Collection<PlayerDetails> getPlayers() {
		return m_players.values();
	}

	public PlayerDetails getPlayer(Long id) {
		return m_players.get(id);
	}
	
	public void addPlayer(PlayerDetails player) {
		m_players.put(player.getId(), player);

		if (player.hasBall()) {
			setHasBall(true);
		}
	}

	public void removePlayer(PlayerDetails player) {
		m_players.remove(player.getId());
		if (player.hasBall()) {
			setHasBall(false);
		}
	}

	public void setHasBall(boolean hasBall) {
		m_hasBall = hasBall;
		if (!m_hasBall) {
			for (PlayerDetails player : m_players.values()) {
				player.setHasBall(false);
			}
		}
	}

	public boolean hasBall() {
		return m_hasBall;
	}

	public TileType getType() {
		return m_type;
	}

	public Set<TileIndex> getNeighbours() {
		return m_neighbours;
	}

	public boolean isNeighbour(Tile current) {
		return getNeighbours().contains(current);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((m_index == null) ? 0 : m_index.hashCode());
		result = prime * result + ((m_type == null) ? 0 : m_type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		if (m_index == null) {
			if (other.m_index != null)
				return false;
		} else if (!m_index.equals(other.m_index))
			return false;
		if (m_type != other.m_type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tile [m_index=" + m_index +  ", m_players=" + m_players + ", m_hasBall=" + m_hasBall + "]";
	}

}
