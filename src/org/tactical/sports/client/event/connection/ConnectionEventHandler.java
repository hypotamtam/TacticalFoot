package org.tactical.sports.client.event.connection;

import org.tactical.sports.shared.domain.User;

import com.google.gwt.event.shared.EventHandler;

public interface ConnectionEventHandler extends EventHandler{
	void onConnectionDone(User user);
}
