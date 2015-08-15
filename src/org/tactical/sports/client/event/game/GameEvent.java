package org.tactical.sports.client.event.game;

import com.google.gwt.event.shared.GwtEvent;

public class GameEvent extends GwtEvent<GameEventHandler> {

	public static final Type<GameEventHandler> TYPE = new Type<GameEventHandler>();
	
	private String m_event;
	
	public GameEvent(String event) {
		m_event = event;
	}
	
	@Override
	public Type<GameEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GameEventHandler handler) {
		handler.onGameEvent(m_event);
	}

}
