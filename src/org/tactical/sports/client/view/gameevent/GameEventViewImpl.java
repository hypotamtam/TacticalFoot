package org.tactical.sports.client.view.gameevent;

import org.tactical.sports.client.view.gameevent.GameEventView.GameEventPresenter;
import org.tactical.sports.client.view.helper.CompositeWithPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.layout.client.Layout.AnimationCallback;
import com.google.gwt.layout.client.Layout.Layer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class GameEventViewImpl extends CompositeWithPresenter<GameEventPresenter> implements GameEventView,
		AnimationCallback {

	private static GameEventViewImplUiBinder uiBinder = GWT.create(GameEventViewImplUiBinder.class);
	@UiField
	InlineLabel m_text;
	@UiField
	FlowPanel m_event;
	@UiField
	LayoutPanel m_mainPanel;
	@UiField
	FlowPanel m_animatedPanel;

	private int m_animationStep = 0;
	private double m_fontSize;

	interface GameEventViewImplUiBinder extends UiBinder<Widget, GameEventViewImpl> {
	}

	public GameEventViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		m_mainPanel.setVisible(false);
		m_mainPanel.getElement().getStyle().setOverflow(Overflow.HIDDEN);
	}

	@Override
	public void showEvent(String eventText) {
		String fontSize = getCSSValue(m_text.getElement(), "font-size").replace("px", "");
		m_fontSize = Double.valueOf(fontSize);
		m_text.setText(eventText);
		m_mainPanel.setVisible(true);
		m_mainPanel.setWidgetLeftWidth(m_animatedPanel, 100, Unit.PCT, 100, Unit.PCT);
		m_mainPanel.forceLayout();
		m_mainPanel.setWidgetLeftWidth(m_animatedPanel, 0, Unit.PCT, 100, Unit.PCT);
		m_mainPanel.animate(500, this);
	}

	private native String getCSSValue(Element element, String cssRule) /*-{
		var strValue = "";
		if(document.defaultView && document.defaultView.getComputedStyle){
			strValue = document.defaultView.getComputedStyle(element, "").getPropertyValue(cssRule);
		} else if(element.currentStyle){
			try{
				cssRule = cssRule.replace(/\-(\w)/g,
				 						  function (strMatch, p1){
												return p1.toUpperCase();
										  });
				strValue = element.currentStyle[cssRule];
			} catch(e){
				strValue = '0';
			}
		}
		return strValue;
	}-*/;
	
	@Override
	public void onAnimationComplete() {
		m_animationStep++;
		if (m_animationStep == 5) {
			m_text.getElement().getStyle().setFontSize(m_fontSize, Unit.PX);
			m_mainPanel.forceLayout();
			m_mainPanel.setWidgetLeftWidth(m_animatedPanel, 100, Unit.PCT, 100, Unit.PCT);
		}
		if (m_animationStep == 6) {
			m_animationStep = 0;
			m_mainPanel.setVisible(false);
			getPresenter().eventShown();
		} else {
			int duration = (m_animationStep >= 1 && m_animationStep < 5) ? 200 : 500;
			m_mainPanel.animate(duration, this);
		}
	}

	@Override
	public void onLayout(Layer layer, double progress) {
		if (m_animationStep >= 1 && m_animationStep < 5) {
			double factor = (m_animationStep % 2 == 1) ? 1.0 + progress * 0.3 : 1.3 - progress * 0.3; 
			m_text.getElement().getStyle().setFontSize(m_fontSize * factor, Unit.PX);
		}

	}

}
