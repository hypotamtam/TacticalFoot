package org.tactical.sports.client.view.playground.layer.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.tactical.sports.client.resources.Res;
import org.tactical.sports.client.view.UIConstants;
import org.tactical.sports.client.view.animatedwidget.GridHighlighterWidget;
import org.tactical.sports.client.view.playground.grid.GridPositionHelper;
import org.tactical.sports.client.view.playground.layer.ActionLayer;
import org.tactical.sports.shared.domain.playground.tile.TileIndex;

import com.google.gwt.user.client.ui.LayoutPanel;

public class ActionLayerImpl extends LayoutPanel implements ActionLayer {

	private Map<Long, Collection<TileIndex>> m_actionAreaByPlayerId;
	private Map<Long, GridHighlighterWidget> m_actionWidgetByPlayerId;
	
	private GridPositionHelper m_playgroundManager;
	
	public ActionLayerImpl() {
		m_actionAreaByPlayerId = new HashMap<Long, Collection<TileIndex>>();
		m_actionWidgetByPlayerId = new HashMap<Long, GridHighlighterWidget>();
	}
	
	@Override
	public void setTileManager(GridPositionHelper manager) {
		m_playgroundManager = manager;
	}

	@Override
	public void removeAction(Long playerId) {
		if (m_actionAreaByPlayerId.containsKey(playerId)) {
			GridHighlighterWidget widget = m_actionWidgetByPlayerId.get(playerId);
			widget.keepGridIndices(null);
			m_actionAreaByPlayerId.remove(playerId);
		}
	}
	
	@Override
	public void setAction(Collection<TileIndex> actionArea, long playerId) {
		GridHighlighterWidget widget = null; 
		if (m_actionAreaByPlayerId.containsKey(playerId)) {
			widget = m_actionWidgetByPlayerId.get(playerId);
			widget.keepGridIndices(actionArea);
		} else {
			widget = new GridHighlighterWidget(Res.INSTANCE.hexaPath(), m_playgroundManager);
			m_actionWidgetByPlayerId.put(playerId, widget);
			add(widget);
		}
		m_actionAreaByPlayerId.put(playerId, actionArea);
		widget.addGridIndices(actionArea);
		widget.play(UIConstants.ANIMATION_DURATION);
	}

	@Override
	public void removeAllActions() {
		m_actionAreaByPlayerId.clear();
		for (GridHighlighterWidget widget : m_actionWidgetByPlayerId.values()) {
			remove(widget);
		}
		m_actionWidgetByPlayerId.clear();
		
	}
}
