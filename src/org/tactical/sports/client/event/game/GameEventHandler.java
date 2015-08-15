package org.tactical.sports.client.event.game;

import com.google.gwt.event.shared.EventHandler;

public interface GameEventHandler extends EventHandler {
	void onGameEvent(String event);
}
