package org.tactical.sports.client.log.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LogViewImpl extends ResizeComposite implements LogView {

	private static final Binder binder = GWT.create(Binder.class);
	@UiField VerticalPanel m_logView;
	@UiField SplitLayoutPanel m_rootView;
	@UiField ScrollPanel m_logScroller;
	@UiField SimplePanel m_mainView;
	
	interface Binder extends UiBinder<Widget, LogViewImpl> {
	}

	public LogViewImpl() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void log(String entry) {
		HTML entryLabel = new HTML(entry,true);
		m_logView.add(entryLabel);
		m_logScroller.scrollToBottom();
	}

	@Override
	public void setMainView(IsWidget mainView) {
		m_mainView.add( mainView);
	}
}
