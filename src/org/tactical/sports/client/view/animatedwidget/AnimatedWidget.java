package org.tactical.sports.client.view.animatedwidget;

import com.google.gwt.user.client.ui.IsWidget;

public interface AnimatedWidget extends IsWidget {
	public void play(int duration);
	
	public void stop();
}
