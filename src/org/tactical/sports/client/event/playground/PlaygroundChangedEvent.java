package org.tactical.sports.client.event.playground;

import org.tactical.sports.shared.domain.playground.Playground;

import com.google.gwt.event.shared.GwtEvent;

public class PlaygroundChangedEvent extends GwtEvent<PlaygroundChangedEventHandler> {
	public static Type<PlaygroundChangedEventHandler> TYPE = new Type<PlaygroundChangedEventHandler>();

	private Playground m_playground;
	
	public PlaygroundChangedEvent(Playground playground) {
		m_playground = playground;
	}

	public Playground getPlayground() {
		return m_playground;
	}
	
	@Override
	public Type<PlaygroundChangedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PlaygroundChangedEventHandler handler) {
		handler.onPlaygroundChanged(this);
	}

}
