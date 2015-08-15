package org.tactical.sports.client.activity.presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.IsWidget;

public interface Presenter<T extends IsWidget> {

	public void setDisplay(T display);
	public T getDisplay();
	public void setEventBus(EventBus eventBus);  
}
