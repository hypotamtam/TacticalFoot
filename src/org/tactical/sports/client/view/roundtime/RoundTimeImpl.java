package org.tactical.sports.client.view.roundtime;

import org.tactical.sports.client.view.helper.CompositeWithPresenter;
import org.tactical.sports.client.view.roundtime.RoundTime.RoundTimePresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.layout.client.Layout.AnimationCallback;
import com.google.gwt.layout.client.Layout.Layer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

/**
 * Sample implementation of {@link RoundTime}.
 */
public class RoundTimeImpl extends CompositeWithPresenter<RoundTimePresenter> implements RoundTime, AnimationCallback {

	interface Binder extends UiBinder<Widget, RoundTimeImpl> {
	}
	
	private static final Binder binder = GWT.create(Binder.class);
	@UiField Button m_endButton;
	@UiField ProgressBar m_progressBar;

	public RoundTimeImpl() {
		initWidget(binder.createAndBindUi(this));
		getElement().setId(RoundTime.class.getName());
		m_progressBar.setStyle("limegreen", "brown");
	}

	@Override
	public void start(int duration) {
		m_endButton.setEnabled(true);
		m_progressBar.animate(0.0, 1.0, duration, this);
	}

	@Override
	public void stop() {
		m_endButton.setEnabled(false);
	}
	
	@UiHandler("m_endButton")
	void onEndButtonClick(ClickEvent event) {
		m_progressBar.stopAnimation();
	}

	@Override
	public void onAnimationComplete() {
		getPresenter().onRoundTimeStop();
	}

	@Override
	public void onLayout(Layer layer, double progress) {
	}

}
