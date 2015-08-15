package org.tactical.sports.client.event.playground;

import org.tactical.sports.shared.domain.playground.tile.Tile;

import com.google.gwt.event.shared.GwtEvent;

public class TileSelectedEvent extends GwtEvent<TileSelectedEventHandler> {
	public static Type<TileSelectedEventHandler> TYPE = new Type<TileSelectedEventHandler>();

	private Tile m_tile;
	
	public void setTile(Tile tile) {
		m_tile = tile;
	}
	
	public Tile getTile() {
		return m_tile;
	}
	
	@Override
	public Type<TileSelectedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(TileSelectedEventHandler handler) {
		handler.onTileSelected(this);
	}

}
