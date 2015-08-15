package org.tactical.sports.client.event.playground;

import com.google.gwt.event.shared.EventHandler;

public interface PlayerSelectedEventHandler extends EventHandler  {
	void onPlayerSelected(PlayerSelectedEvent event);
}
