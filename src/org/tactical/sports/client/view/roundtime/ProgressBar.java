package org.tactical.sports.client.view.roundtime;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.layout.client.Layout.AnimationCallback;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ProgressBar extends Composite{

	private static ProgressBarUiBinder uiBinder = GWT.create(ProgressBarUiBinder.class);
	@UiField SimplePanel m_background;
	@UiField SimplePanel m_foreground;
	@UiField Image m_bgImage;
	@UiField Image m_fgImage;
	@UiField LayoutPanel m_mainPanel;
	private boolean m_isVertical;

	interface ProgressBarUiBinder extends UiBinder<Widget, ProgressBar> {
	}

	public ProgressBar() {
		this(false);
	}
	
	public ProgressBar(boolean isVertical) {
		initWidget(uiBinder.createAndBindUi(this));
		m_isVertical = isVertical;
	}

	public void animate(double startProgress, double endProgress, int duration, AnimationCallback callBack) {
		if (m_isVertical) {
			m_mainPanel.setWidgetBottomHeight(m_foreground, 0, Unit.PCT, 100 * startProgress, Unit.PCT);
		} else {
			m_mainPanel.setWidgetLeftWidth(m_foreground, 0, Unit.PCT, 100 * startProgress, Unit.PCT);
		}
		
		m_mainPanel.forceLayout();
		
		if (m_isVertical) {
			m_mainPanel.setWidgetBottomHeight(m_foreground, 0, Unit.PCT, 100 * endProgress, Unit.PCT);
		} else {
			m_mainPanel.setWidgetLeftWidth(m_foreground, 0, Unit.PCT, 100 * endProgress, Unit.PCT);
		}
		
		m_mainPanel.animate(duration, callBack);
	}
	
	public void stopAnimation() {
		m_mainPanel.forceLayout();
	}
	
	public boolean isVertical() {
		return m_isVertical;
	}

	public void setVertical(boolean isVertical) {
		m_isVertical = isVertical;
	}

	public void setStyle(DataResource background, DataResource foreground) {
		m_bgImage.setUrl(background.getUrl());
		m_bgImage.setVisible(true);
		m_fgImage.setUrl(foreground.getUrl());
		m_fgImage.setVisible(true);
	}

	public void setStyle(String bgColor, String fgColor) {
		m_background.getElement().getStyle().setBackgroundColor(bgColor);
		m_bgImage.setVisible(false);
		m_foreground.getElement().getStyle().setBackgroundColor(fgColor);
		m_fgImage.setVisible(false);
	}

}
