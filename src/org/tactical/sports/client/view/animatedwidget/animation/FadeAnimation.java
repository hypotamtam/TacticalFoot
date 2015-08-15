package org.tactical.sports.client.view.animatedwidget.animation;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Widget;

public class FadeAnimation extends AbstractAnimation{
	
	private List<Widget> m_widget;
	private boolean m_isFadeIn;
	
	public FadeAnimation(boolean isFadeIn) {
		m_isFadeIn = isFadeIn;
		m_widget = new ArrayList<Widget>();
	}

	public void addWidget(Widget widget) {
		m_widget.add(widget);
		Style currentStyle = widget.getElement().getStyle();
		String opacityStr = currentStyle.getOpacity();
		if (opacityStr.isEmpty()) {
			currentStyle.setOpacity((m_isFadeIn ? 0.0 : 1.0));
		}
	}
	
	public void removeWidget(Widget widget) {
		m_widget.remove(widget);
	}
	
	public void removeAllWidget() {
		m_widget.clear();
	}
	
	@Override
	public void onUpdate(double progress) {
		super.onUpdate(progress);
		for (Widget widget : m_widget) {
			Style currentStyle = widget.getElement().getStyle();
			double currentOpacity = Double.valueOf(currentStyle.getOpacity());
			double newOpacity = m_isFadeIn ? currentOpacity + progress : currentOpacity - progress;
			currentOpacity = Math.max(0.0f, Math.min(1.0f, newOpacity));
			currentStyle.setOpacity(currentOpacity);
		}	
	}

	@Override
	public void onStart() {
	}

}
