package org.tactical.sports.client.event.playground;

import com.google.gwt.event.shared.EventHandler;

public interface PlaygroundChangedEventHandler extends EventHandler {
	void onPlaygroundChanged(PlaygroundChangedEvent event);
}
