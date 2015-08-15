package org.tactical.sports.shared.domain.playground;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.tactical.sports.shared.domain.playground.tile.PlayerDetails;
import org.tactical.sports.shared.domain.playground.tile.Tile;
import org.tactical.sports.shared.domain.playground.tile.TileIndex;

public interface Playground extends Serializable {

	public int getWidth();

	public int getHeight();

	public Collection<Tile> getTiles();
	
	public Collection<Tile> getGoals();
	public boolean isLocalTeamGoal(Tile tile);

	public Tile getTile(TileIndex index);
	
	public long getMatchId();

	public void setMatchId(long matchId);
	
	public void addPlayer(PlayerDetails player, TileIndex tileIndex, boolean isLocal);
	public Set<Long> getPlayers();
	public boolean isALocalPlayer(long id);
}
