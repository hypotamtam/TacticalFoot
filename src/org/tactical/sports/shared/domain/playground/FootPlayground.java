package org.tactical.sports.shared.domain.playground;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.tactical.sports.shared.domain.playground.tile.PlayerDetails;
import org.tactical.sports.shared.domain.playground.tile.Tile;
import org.tactical.sports.shared.domain.playground.tile.TileGraph;
import org.tactical.sports.shared.domain.playground.tile.TileIndex;
import org.tactical.sports.shared.domain.playground.tile.TileType;

public class FootPlayground implements Playground {

	private static final long serialVersionUID = 1L;

	private byte SMALLER_COLUMN_INDEX = 0;
	private int m_width;
	private int m_height;

	private TileGraph m_tileGraph;
	private Set<Tile> m_goalTiles;

	private long m_matchId;

	private Set<Long> m_localPlayers = new HashSet<Long>();
	private Set<Long> m_visitorPlayers = new HashSet<Long>();
	private Set<Long> m_players = new HashSet<Long>();

	public FootPlayground() {
	}

	public FootPlayground(int width, int height) {
		setSize(width, height);
	}

	public void setSize(int width, int height) {
		if ((width % 2) == 0) {
			throw new IllegalArgumentException("Width must be odd.");
		}
		if ((height % 2) == 0) {
			throw new IllegalArgumentException("Height must be odd.");
		}
		m_width = width;
		m_height = height;
		m_tileGraph = new TileGraph(width, height, SMALLER_COLUMN_INDEX);

		// Create the area where the player will move
		for (int x = 1; x < width - 1; x++) {
			for (int y = 0; y < height - ((x % 2) == SMALLER_COLUMN_INDEX ? 1 : 0); y++) {
				m_tileGraph.addTile(x, y, TileType.normal);
			}
		}
		// Create the goals
		m_goalTiles = new HashSet<Tile>();
		int halfHeigh = height / 2;
		m_tileGraph.addTile(0, halfHeigh - 1, TileType.goal);
		m_goalTiles.add(m_tileGraph.getTile(new TileIndex(0, halfHeigh - 1)));
		m_tileGraph.addTile(0, halfHeigh, TileType.goal);
		m_goalTiles.add(m_tileGraph.getTile(new TileIndex(0, halfHeigh)));
		m_tileGraph.addTile(width - 1, halfHeigh, TileType.goal);
		m_goalTiles.add(m_tileGraph.getTile(new TileIndex(width - 1, halfHeigh)));
		m_tileGraph.addTile(width - 1, halfHeigh - 1, TileType.goal);
		m_goalTiles.add(m_tileGraph.getTile(new TileIndex(width - 1, halfHeigh - 1)));
	}

	public Tile getTile(TileIndex index) {
		return m_tileGraph.getTile(index);
	}

	@Override
	public int getWidth() {
		return m_width;
	}

	@Override
	public int getHeight() {
		return m_height;
	}

	@Override
	public Collection<Tile> getTiles() {
		return m_tileGraph.getTiles();
	}

	@Override
	public Collection<Tile> getGoals() {
		return m_goalTiles;
	}

	@Override
	public boolean isLocalTeamGoal(Tile tile) {
		//TODO : Why contains doesn't work?
		for (Tile goalTile : m_goalTiles) {
			if (tile.equals(goalTile)) {
				return tile.getIndex().x == 0;
			}
		}
		return false;
	}

	@Override
	public long getMatchId() {
		return m_matchId;
	}

	@Override
	public void setMatchId(long matchId) {
		m_matchId = matchId;
	}

	@Override
	public void addPlayer(PlayerDetails player, TileIndex tileindex,  boolean isLocal) {
		getTile(tileindex).addPlayer(player);
		if (isLocal) {
			m_localPlayers.add(player.getId());
		} else {
			m_visitorPlayers.add(player.getId());
		}
		m_players.add(player.getId());
	}

	@Override
	public Set<Long> getPlayers() {
		return m_players;
	}

	@Override
	public boolean isALocalPlayer(long id) {
		return m_localPlayers.contains(id);
	}

	@Override
	public String toString() {
		return "FootPlayground [SMALLER_COLUMN_INDEX=" + SMALLER_COLUMN_INDEX + ", m_width=" + m_width + ", m_height=" + m_height + ", m_tileGraph=" + m_tileGraph + ", m_goalTiles=" + m_goalTiles
				+ ", m_matchId=" + m_matchId + ", m_localPlayers=" + m_localPlayers + ", m_visitorPlayers=" + m_visitorPlayers + "]";
	}

}
