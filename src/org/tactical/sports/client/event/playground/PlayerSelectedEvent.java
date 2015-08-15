package org.tactical.sports.client.event.playground;

import org.tactical.sports.shared.domain.playground.tile.PlayerDetails;
import org.tactical.sports.shared.domain.playground.tile.Tile;

import com.google.gwt.event.shared.GwtEvent;

public class PlayerSelectedEvent extends GwtEvent<PlayerSelectedEventHandler> {
	public static Type<PlayerSelectedEventHandler> TYPE = new Type<PlayerSelectedEventHandler>();

	private Tile m_tile;
	private PlayerDetails m_player;

	public PlayerSelectedEvent(Tile tile, PlayerDetails player) {
		if (!tile.hasPlayer(player.getId())) {
			throw new IllegalArgumentException("Cannot create PlayerSelecteEvent with a tile without the given player.");
		}
		m_player = player;
		m_tile = tile;
	}

	public Tile getTilewWithPlayerSelected() {
		return m_tile;
	}

	public PlayerDetails getSelectedPlayer() {
		return m_player;
	}

	@Override
	public Type<PlayerSelectedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PlayerSelectedEventHandler handler) {
		handler.onPlayerSelected(this);
	}

}
