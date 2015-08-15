package org.tactical.sports.client.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

public class FakeHasClickHandlers implements HasClickHandlers {
	private List<ClickHandler> handlers = new ArrayList<ClickHandler>();

	@Override
	public void fireEvent(GwtEvent<?> event) {
		for (ClickHandler handler : handlers) {
			handler.onClick((ClickEvent)event);
		}
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		handlers.add(handler);
		return null;
	}
	
}