package org.tactical.sports.client.view.playground.layer;

import com.google.gwt.user.client.ui.AnimatedLayout;

public interface AnimatedLayer extends Layer, AnimatedLayout{
	void update(double progress);
	void stop();
}
