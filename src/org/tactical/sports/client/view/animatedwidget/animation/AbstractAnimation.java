package org.tactical.sports.client.view.animatedwidget.animation;

import org.tactical.sports.client.view.animatedwidget.animation.timer.Timer;
import org.tactical.sports.client.view.animatedwidget.animation.timer.TimerListener;

public abstract class AbstractAnimation implements Animation, TimerListener{

	private Timer m_animation;
	private Callback m_callBack;
	
	@Override
	public void play(int duration) {
		stop();
		m_animation = new Timer(this);
		m_animation.run(duration);
	}

	public void play(int duration, Callback callback) {
		m_callBack = callback;
		play(duration);
	}
	
	@Override
	public void stop() {
		if (m_animation != null) {
			m_animation.cancel();
		}
	}
	
	public void goToEnd() {
		if (m_animation != null) {
			this.onUpdate(1.0f);
			this.stop();
		}
	}
	
	
	public void onUpdate(double progress) {
		if (m_callBack != null) {
			m_callBack.onAniationUpdated(this, progress);
		}
	}
	
	@Override
	public void onCancel() {
		onStop();
	}

	@Override
	public void onStop() {
		m_animation = null;
		if (m_callBack != null) {
			m_callBack.onAniationFinished(this);
		}
	}

}
