package org.tactical.sports.client.view.animatedwidget;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.tactical.sports.client.view.animatedwidget.animation.Animation;
import org.tactical.sports.client.view.animatedwidget.animation.FadeAnimation;
import org.tactical.sports.client.view.playground.grid.GridPosition;
import org.tactical.sports.client.view.playground.grid.GridPositionHelper;
import org.tactical.sports.shared.domain.playground.tile.TileIndex;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class GridHighlighterWidget implements AnimatedWidget, Animation.Callback {
	private ImageResource m_background;
	private Set<TileIndex> m_gridIndices = new HashSet<TileIndex>();
	private Map<TileIndex, Widget> m_gridIndicesWidget = new HashMap<TileIndex, Widget>();
	
	private LayoutPanel m_mainPanel;
	private FadeAnimation m_addAnimation = new FadeAnimation(true);
	private FadeAnimation m_removeAnimation = new FadeAnimation(false);
	
	private GridPositionHelper m_playgroundManager;
	
	public GridHighlighterWidget(ImageResource GridIndexBackground, GridPositionHelper playgroundManager) {
		m_background = GridIndexBackground;
		m_playgroundManager = playgroundManager;
		m_mainPanel = new LayoutPanel();
	}
	
	public void addGridIndices(Collection<TileIndex> gridIndices) {
		for (TileIndex gridIndex : gridIndices) {
			if (!m_gridIndices.contains(gridIndex)) {
				Widget widget = new Image(m_background);
				GridPosition position = m_playgroundManager.getGridPosition(gridIndex);
				m_mainPanel.add(widget);
				m_mainPanel.setWidgetTopHeight(widget, position.getTop(), Unit.PX, m_background.getHeight(), Unit.PX);
				m_mainPanel.setWidgetLeftWidth(widget, position.getLeft(), Unit.PX, m_background.getWidth(), Unit.PX);
				m_addAnimation.addWidget(widget);
				m_gridIndices.add(gridIndex);
				m_gridIndicesWidget.put(gridIndex, widget);
			}	
		}
	}

	public void keepGridIndices(Collection<TileIndex> tilesToKeep) {
		if (tilesToKeep == null) {
			for (TileIndex tile : m_gridIndices) {
				m_removeAnimation.addWidget(m_gridIndicesWidget.get(tile));
			}
			m_addAnimation.removeAllWidget();
			m_gridIndices.clear();
		} else {
			for (TileIndex tile : m_gridIndices) {
				if (!tilesToKeep.contains(tile)) {
					Widget widget = m_gridIndicesWidget.get(tile);
					m_addAnimation.removeWidget(widget);
					m_removeAnimation.addWidget(widget);
					m_gridIndicesWidget.remove(tilesToKeep);
				}
			}
			m_gridIndices.retainAll(tilesToKeep);
		}
	}
	
	@Override
	public void play(int duration) {
		m_addAnimation.play(duration);
		m_removeAnimation.play(duration, this);
	}

	@Override
	public Widget asWidget() {
		return m_mainPanel;
	}

	@Override
	public void stop() {
		m_addAnimation.stop();
		m_removeAnimation.stop();
	}

	@Override
	public void onAniationUpdated(Animation animation, double progress) {
		
	}

	@Override
	public void onAniationFinished(Animation animation) {
		m_removeAnimation.removeAllWidget();
	}

}
