package org.tactical.sports.client.view.playground.layer;

import org.tactical.sports.shared.domain.playground.tile.TileIndex;


public interface PlayerLayer extends AnimatedLayer {

	void addPlayer(long playerId, TileIndex index, boolean isLocal, boolean ishighlighable);
	void movePlayer(long playerId, TileIndex destination);
	
	void setBall(TileIndex index);
	void moveBall(TileIndex index);

}