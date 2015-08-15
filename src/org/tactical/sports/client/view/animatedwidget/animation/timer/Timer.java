package org.tactical.sports.client.view.animatedwidget.animation.timer;


import com.google.gwt.animation.client.Animation;

public class Timer extends Animation{

	private TimerListener m_listener;
	
	public Timer(TimerListener listener) {
		m_listener = listener;
	}
	
	@Override
	protected void onCancel() {
		super.onCancel();
		m_listener.onCancel();
	}

	@Override
	protected void onComplete() {
		super.onComplete();
		m_listener.onStop();
	}

	@Override
	protected void onStart() {
		super.onStart();
		m_listener.onStart();
	}

	@Override
	protected void onUpdate(double progress) {
		m_listener.onUpdate(progress);
	}

}
