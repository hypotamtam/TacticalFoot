package org.tactical.sports.client.event.playground;

import org.tactical.sports.shared.domain.playground.tile.Tile;

import com.google.gwt.event.shared.GwtEvent;

public class PlaygroundOverEvent extends GwtEvent<PlaygroundOverEventHandler> {

	public static Type<PlaygroundOverEventHandler> TYPE = new Type<PlaygroundOverEventHandler>();
	
	private Tile m_tile;
	
	public PlaygroundOverEvent(Tile tile) {
		m_tile = tile;
	}
	
	@Override
	public Type<PlaygroundOverEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PlaygroundOverEventHandler handler) {
		handler.onPlaygroundOver(m_tile);		
	}

}
