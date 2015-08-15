package org.tactical.sports.client.event.round;

import com.google.gwt.event.shared.GwtEvent;

public class RoundBeginEvent extends GwtEvent<RoundBeginEventHandler> {
	public static Type<RoundBeginEventHandler> TYPE = new Type<RoundBeginEventHandler>();
	
	@Override
	public Type<RoundBeginEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RoundBeginEventHandler handler) {
		handler.onRoundStart();
	}

}
