package org.tactical.sports.client.view.playground.layer.impl;

import java.util.HashMap;
import java.util.Map;

import org.tactical.sports.client.resources.Res;
import org.tactical.sports.client.view.playground.grid.GridPosition;
import org.tactical.sports.client.view.playground.grid.GridPositionHelper;
import org.tactical.sports.client.view.playground.layer.BackgroundLayer;
import org.tactical.sports.shared.domain.playground.tile.TileIndex;
import org.tactical.sports.shared.domain.playground.tile.TileType;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class BackgroundLayerImpl extends LayoutPanel implements BackgroundLayer {

	private Map<TileType, ImageResource> m_specialTileModels = new HashMap<TileType, ImageResource>();

	private GridPositionHelper m_tileManager;
	private LayoutPanel m_tiles;
	private Image m_lines;

	public BackgroundLayerImpl() {
		m_specialTileModels.put(TileType.goal, Res.INSTANCE.hexaGoal());

		addStyleName(Res.INSTANCE.css().grassBg());
		m_tiles = new LayoutPanel();
		add(m_tiles);

		m_lines = new Image(Res.INSTANCE.playgroundLines().getUrl());
		add(new SimplePanel(m_lines));
	}

	@Override
	public void setSpecialTyle(TileIndex index, TileType type) {
		if (type != TileType.normal) {
			GridPosition position = m_tileManager.getGridPosition(index);
			ImageResource ressource = m_specialTileModels.get(type);
			Widget tile = new Image(ressource);
			m_tiles.add(tile);
			m_tiles.setWidgetTopHeight(tile, position.getTop(), Unit.PX, ressource.getHeight(), Unit.PX);
			m_tiles.setWidgetLeftWidth(tile, position.getLeft(), Unit.PX, ressource.getWidth(), Unit.PX);
		}
	}

	@Override
	public void setPixelSize(int width, int height) {
		super.setPixelSize(width, height);
		m_lines.setPixelSize(width, height);
	}
	
	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void setTileManager(GridPositionHelper manager) {
		m_tileManager = manager;
	}

}
