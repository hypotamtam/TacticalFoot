package org.tactical.sports.client.view.playground.layer;

import java.util.Collection;

import org.tactical.sports.shared.domain.playground.tile.TileIndex;

public interface BackgroundAnimationLayer extends Layer {
	
	void showActionPosibility(Collection<TileIndex> gridIndices);

	void updateActionPosibility(Collection<TileIndex> gridIndices);

	void hideActionPosibility();

}