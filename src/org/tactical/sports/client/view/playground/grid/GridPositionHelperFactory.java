package org.tactical.sports.client.view.playground.grid;

import org.tactical.sports.client.resources.Res;

public class GridPositionHelperFactory {

	public static GridPositionHelper create() {
		GridPositionHelper gridPositionHelper = new GridPositionHelper();
		gridPositionHelper.setGridElementModel(new GridElementModel(Res.INSTANCE.hexa()));
		return gridPositionHelper;
	}
}
