package org.tactical.sports.client.log.view;

import com.google.gwt.user.client.ui.IsWidget;

public interface LogView extends IsWidget {
	void log(String entry);
	void setMainView(IsWidget mainView);
}
