package org.tactical.sports.client.event.connection;

import org.tactical.sports.shared.domain.User;

import com.google.gwt.event.shared.GwtEvent;

public class ConnectionEvent extends GwtEvent<ConnectionEventHandler> {

	public static final Type<ConnectionEventHandler> TYPE = new Type<ConnectionEventHandler>();
	
	private User m_user;
	
	public ConnectionEvent(User user) {
		m_user = user;
	}
	
	@Override
	public Type<ConnectionEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ConnectionEventHandler handler) {
		handler.onConnectionDone(m_user);
	}

}
