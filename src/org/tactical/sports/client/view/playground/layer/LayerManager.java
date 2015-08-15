package org.tactical.sports.client.view.playground.layer;

import java.util.HashMap;
import java.util.Map;

import org.tactical.sports.client.view.playground.grid.GridPositionHelper;
import org.tactical.sports.client.view.playground.grid.GridPositionHelperFactory;
import org.tactical.sports.client.view.playground.layer.impl.ActionLayerImpl;
import org.tactical.sports.client.view.playground.layer.impl.BackgroundAnimationLayerImpl;
import org.tactical.sports.client.view.playground.layer.impl.BackgroundLayerImpl;
import org.tactical.sports.client.view.playground.layer.impl.PlayerLayerImpl;
import org.tactical.sports.shared.domain.playground.tile.TileIndex;

import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.LayoutPanel;

public class LayerManager extends Composite {
	
	private Map<Class<? extends Layer>, Layer> m_layer;
	
	private GridPositionHelper m_tileManager;
	private LayoutPanel m_mainPanel;
	
	public LayerManager() {
		m_tileManager = GridPositionHelperFactory.create();
		
		m_mainPanel = new LayoutPanel();
		m_layer = new HashMap<Class<? extends Layer>, Layer>();	
		addLayers();
		
		initWidget(m_mainPanel);
	}

	public void reset() {
		m_mainPanel.clear();
		m_layer.clear();
		addLayers();
	}

	private void addLayers() {
		addLayer(BackgroundLayer.class, new BackgroundLayerImpl());
		addLayer(BackgroundAnimationLayer.class, new BackgroundAnimationLayerImpl());
		addLayer(ActionLayer.class, new ActionLayerImpl());
		addLayer(PlayerLayer.class, new PlayerLayerImpl());
	}

	//TODO: Je ne sais pas commment supprimer proprement ce warning.
	@SuppressWarnings("unchecked")
	public <T extends Layer> T getLayer(Class<T> layerClass) {
		return (T) m_layer.get(layerClass);
	}
	
	private void addLayer(Class<? extends Layer> key, Layer layer) {
		m_layer.put(key, layer);
		layer.setTileManager(getTileManager());
		m_mainPanel.add(layer);
	}
	
	public void setSize(int colCount, int rowCount) {
		int width = getTileManager().getWidth(colCount);
		int height = getTileManager().getHeight(rowCount);
		m_mainPanel.setPixelSize(width, height);
		for (Layer layer : m_layer.values()) {
			layer.setPixelSize(width, height);
		}
	}
	
	public TileIndex getGridIndexFromPosition(MouseEvent<?> mouseEvent) {
		int mouseRelativeX = mouseEvent.getRelativeX(m_mainPanel.getElement());
		int mouseRelativeY = mouseEvent.getRelativeY(m_mainPanel.getElement());
		return m_tileManager.getGridIndexFromPosition(mouseRelativeX, mouseRelativeY);
	}
	
	private GridPositionHelper getTileManager() {
		return m_tileManager;
	}
}
