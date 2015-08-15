package org.tactical.sports.client.view.playground.layer;

import org.tactical.sports.client.view.playground.grid.GridPositionHelper;

import com.google.gwt.user.client.ui.IsWidget;

public interface Layer extends IsWidget {
	
	void setPixelSize(int width, int height);
	void setTileManager(GridPositionHelper manager);
}
