package org.tactical.sports.client.event.action;

import com.google.gwt.event.shared.EventHandler;

public interface ActionEventHandler extends EventHandler {
	void onActionSet(ActionEvent actionEvent);
	void onActionCancel(ActionEvent actionEvent);
}
