package org.tactical.sports.client.view.playground;

import java.util.Collection;

import org.tactical.sports.client.view.helper.CompositeWithPresenter;
import org.tactical.sports.client.view.playground.PlaygroundView.PlaygroundPresenter;
import org.tactical.sports.client.view.playground.layer.ActionLayer;
import org.tactical.sports.client.view.playground.layer.BackgroundAnimationLayer;
import org.tactical.sports.client.view.playground.layer.BackgroundLayer;
import org.tactical.sports.client.view.playground.layer.LayerManager;
import org.tactical.sports.client.view.playground.layer.PlayerLayer;
import org.tactical.sports.shared.domain.playground.tile.TileIndex;
import org.tactical.sports.shared.domain.playground.tile.TileType;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.layout.client.Layout.AnimationCallback;
import com.google.gwt.layout.client.Layout.Layer;

public class PlaygroundViewImpl extends CompositeWithPresenter<PlaygroundPresenter> implements PlaygroundView, ClickHandler, MouseMoveHandler, AnimationCallback, MouseOutHandler {
	private LayerManager m_layerManager;

	public PlaygroundViewImpl() {
		m_layerManager = new LayerManager();

		m_layerManager.addDomHandler(this, ClickEvent.getType());
		m_layerManager.addDomHandler(this, MouseMoveEvent.getType());
		m_layerManager.addDomHandler(this, MouseOutEvent.getType());
		
		initWidget(m_layerManager);
		getElement().setId(PlaygroundView.class.getName());
	}

	@Override
	public void setSize(int columnCount, int rowCount) {
		m_layerManager.setSize(columnCount, rowCount);
	}

	@Override
	public void setSpecialTile(TileIndex tileIndex, TileType type) {
		BackgroundLayer bgLayer = m_layerManager.getLayer(BackgroundLayer.class);
		bgLayer.setSpecialTyle(tileIndex, type);
	}

	@Override
	public void addPlayer(long playerId, TileIndex pos, boolean isLocal, boolean ishighlighable) {
		PlayerLayer playerLayer = m_layerManager.getLayer(PlayerLayer.class);
		playerLayer.addPlayer(playerId, pos, isLocal, ishighlighable);
	}
	
	@Override
	public void prepareAnimation() {
		PlayerLayer playerLayer = m_layerManager.getLayer(PlayerLayer.class);
		playerLayer.forceLayout();
		
	}
	
	@Override
	public void movePlayer(long playerId, TileIndex destination) {
		PlayerLayer playerLayer = m_layerManager.getLayer(PlayerLayer.class);
		playerLayer.movePlayer(playerId, destination);
	}

	@Override
	public void setBall(TileIndex pos) {
		PlayerLayer playerLayer = m_layerManager.getLayer(PlayerLayer.class);
		playerLayer.setBall(pos);
	}
	
	@Override
	public void moveBall(TileIndex destination) {
		PlayerLayer playerLayer = m_layerManager.getLayer(PlayerLayer.class);
		playerLayer.moveBall(destination);
	}

	@Override
	public void startAnimation(int duration) {
		PlayerLayer playerLayer = m_layerManager.getLayer(PlayerLayer.class);
		playerLayer.animate(duration, this);
	}
	
	@Override
	public void onClick(ClickEvent event) {
		TileIndex index = m_layerManager.getGridIndexFromPosition(event);
		if (index != null) {
			getPresenter().onClick(index);
		}
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		TileIndex index = m_layerManager.getGridIndexFromPosition(event);
		getPresenter().onMouseOver(index);
	}
	
		@Override
	public void onMouseOut(MouseOutEvent event) {
		getPresenter().onMouseOut();
	}
	
	@Override
	public void initActionDefinition(Collection<TileIndex> actionPossibility) {
		BackgroundAnimationLayer bgAnimationLayer = m_layerManager.getLayer(BackgroundAnimationLayer.class);
		bgAnimationLayer.showActionPosibility(actionPossibility);
	}

	@Override
	public void updateActionDefinition(Collection<TileIndex> actionPossibility) {
		BackgroundAnimationLayer bgAnimationLayer = m_layerManager.getLayer(BackgroundAnimationLayer.class);
		bgAnimationLayer.updateActionPosibility(actionPossibility);
	}

	@Override
	public void finishActionDefinition() {
		BackgroundAnimationLayer bgAnimationLayer = m_layerManager.getLayer(BackgroundAnimationLayer.class);
		bgAnimationLayer.hideActionPosibility();
	}
	
	@Override
	public void setAction(Collection<TileIndex> actionArea, long playerId) {
		ActionLayer actionLayer = m_layerManager.getLayer(ActionLayer.class);
		actionLayer.setAction(actionArea, playerId);
	}

	@Override
	public void removeAction(long playerId) {
		ActionLayer actionLayer = m_layerManager.getLayer(ActionLayer.class);
		actionLayer.removeAction(playerId);
	}
	
	@Override
	public void clearActions() {
		ActionLayer actionLayer = m_layerManager.getLayer(ActionLayer.class);
		actionLayer.removeAllActions();
	}

	@Override
	public void onAnimationComplete() {
		PlayerLayer playerLayer = m_layerManager.getLayer(PlayerLayer.class);
		playerLayer.stop();
		getPresenter().onAnimationEnded();
	}

	@Override
	public void onLayout(Layer layer, double progress) {
		PlayerLayer playerLayer = m_layerManager.getLayer(PlayerLayer.class);
		playerLayer.update(progress);
	}

	
}
