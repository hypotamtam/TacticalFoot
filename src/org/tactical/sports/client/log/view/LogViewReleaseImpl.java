package org.tactical.sports.client.log.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class LogViewReleaseImpl extends Composite implements LogView {

	private SimplePanel m_panel = new SimplePanel();
	
	public LogViewReleaseImpl() {
		initWidget(m_panel);
	}
	
	@Override
	public Widget asWidget() {
		return m_panel;
	}

	@Override
	public void log(String entry) {
	}

	@Override
	public void setMainView(IsWidget mainView) {
		m_panel.setWidget(mainView);
	}

}
