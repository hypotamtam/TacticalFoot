package org.tactical.sports.client.view.animatedwidget.animation.timer;

public interface TimerListener {

	public void onCancel();

	public void onStop();

	public void onStart();

	public void onUpdate(double progress);
}
