package org.tactical.sports.client.view.animatedwidget.animation;


public interface Animation {

	public void play(int duration);
	
	public void play(int duration, Callback callback);
	
	public void stop();

	public void goToEnd();
	
	public interface Callback {
		void onAniationUpdated(Animation animation, double progress);
		void onAniationFinished(Animation animation);
	}
}

