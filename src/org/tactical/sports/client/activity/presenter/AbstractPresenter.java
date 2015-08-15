package org.tactical.sports.client.activity.presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.IsWidget;

public abstract class AbstractPresenter<T extends IsWidget> implements Presenter<T> {

	private EventBus m_eventBus;
	private T m_display;
	
	
	public void setEventBus(EventBus eventBus) {
		m_eventBus = eventBus;
	}
	
	public EventBus getEventBus() {
		return m_eventBus;
	}
	
	public void setDisplay(T display) {
		m_display = display;
	}
	
	public T getDisplay() {
		return m_display;
	}
	
}
