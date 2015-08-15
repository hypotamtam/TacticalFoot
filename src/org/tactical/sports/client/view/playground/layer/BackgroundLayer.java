package org.tactical.sports.client.view.playground.layer;

import org.tactical.sports.shared.domain.playground.tile.TileIndex;
import org.tactical.sports.shared.domain.playground.tile.TileType;

public interface BackgroundLayer extends Layer {

	void setSpecialTyle(TileIndex pos, TileType type);
}