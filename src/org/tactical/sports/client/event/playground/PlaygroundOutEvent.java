package org.tactical.sports.client.event.playground;

import com.google.gwt.event.shared.GwtEvent;


public class PlaygroundOutEvent extends GwtEvent<PlaygroundOutEventHandler> {

	public static Type<PlaygroundOutEventHandler> TYPE = new Type<PlaygroundOutEventHandler>();
	
	@Override
	public Type<PlaygroundOutEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PlaygroundOutEventHandler handler) {
		handler.onPlaygroundOut();
	}

}
