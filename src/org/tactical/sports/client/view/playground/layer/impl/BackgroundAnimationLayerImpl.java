package org.tactical.sports.client.view.playground.layer.impl;

import java.util.Collection;

import org.tactical.sports.client.resources.Res;
import org.tactical.sports.client.view.UIConstants;
import org.tactical.sports.client.view.animatedwidget.AnimatedWidget;
import org.tactical.sports.client.view.animatedwidget.GridHighlighterWidget;
import org.tactical.sports.client.view.playground.grid.GridPositionHelper;
import org.tactical.sports.client.view.playground.layer.BackgroundAnimationLayer;
import org.tactical.sports.shared.domain.playground.tile.TileIndex;

import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class BackgroundAnimationLayerImpl extends LayoutPanel implements BackgroundAnimationLayer {

	private GridPositionHelper m_playgroundManager;
	private AnimatedWidget m_gridAnimatedWidget;

	@Override
	public void showActionPosibility(Collection<TileIndex> gridIndices) {
		hideActionPosibility();
		
		if (gridIndices != null) {
			GridHighlighterWidget animatedWidget = new GridHighlighterWidget(Res.INSTANCE.hexaZone(), m_playgroundManager);
			animatedWidget.addGridIndices(gridIndices);
			add(animatedWidget);
			animatedWidget.play(UIConstants.ANIMATION_DURATION);
			m_gridAnimatedWidget = animatedWidget;
		}
	}
	
	@Override
	public void updateActionPosibility(Collection<TileIndex> gridIndices) {
		if (m_gridAnimatedWidget != null) {
			GridHighlighterWidget animatedWidget = (GridHighlighterWidget) m_gridAnimatedWidget;
			m_gridAnimatedWidget.stop();
			animatedWidget.keepGridIndices(gridIndices);
			animatedWidget.play(UIConstants.ANIMATION_DURATION);
		}
	}
	
	@Override
	public void hideActionPosibility() {
		if (m_gridAnimatedWidget != null) {
			m_gridAnimatedWidget.stop();
			m_gridAnimatedWidget.asWidget().removeFromParent();
		}
	}
	
	@Override
	public void setTileManager(GridPositionHelper manager) {
		m_playgroundManager = manager;
	}

	@Override
	public Widget asWidget() {
		return this;
	}
}
