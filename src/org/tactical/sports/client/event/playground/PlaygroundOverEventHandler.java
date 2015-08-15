package org.tactical.sports.client.event.playground;

import org.tactical.sports.shared.domain.playground.tile.Tile;

import com.google.gwt.event.shared.EventHandler;

public interface PlaygroundOverEventHandler extends EventHandler {
	void onPlaygroundOver(Tile tile);
}
