package org.tactical.sports.client.event.round;

import com.google.gwt.event.shared.GwtEvent;

public class RoundEndedEvent extends GwtEvent<RoundEndedEventHandler> {
	public static Type<RoundEndedEventHandler> TYPE = new Type<RoundEndedEventHandler>();
	
	@Override
	public Type<RoundEndedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RoundEndedEventHandler handler) {
		handler.onRoundEnded();
	}

}
