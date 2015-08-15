package org.tactical.sports.client.view;

import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class WaitingView {

	PopupPanel m_popup;

	public static WaitingView createWaitingView(String message) {
		return new WaitingView(message);
	}

	private WaitingView(String message) {
		m_popup = new PopupPanel(false);
		m_popup.setWidget(new InlineLabel(message));
		m_popup.setAnimationEnabled(true);
		m_popup.setGlassEnabled(true);
	}
	
	public void show() {
		m_popup.center();
	}
	
	public void show(Widget widget) {
		m_popup.showRelativeTo(widget);
	}
	
	public void hide() {
		m_popup.hide();
	}
}
