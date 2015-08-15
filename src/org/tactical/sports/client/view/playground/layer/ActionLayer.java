package org.tactical.sports.client.view.playground.layer;

import java.util.Collection;

import org.tactical.sports.shared.domain.playground.tile.TileIndex;


public interface ActionLayer extends Layer {

	void removeAllActions();
	
	void removeAction(Long playerId);

	void setAction(Collection<TileIndex> actionArea, long playerId);

}