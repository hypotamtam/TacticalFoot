package org.tactical.sports.client.activity.presenter.game;

import java.util.HashMap;
import java.util.Map;

import org.tactical.sports.client.activity.presenter.AbstractPresenter;
import org.tactical.sports.client.event.action.ActionEvent;
import org.tactical.sports.client.event.action.ActionPosibilityEvent;
import org.tactical.sports.client.event.action.ActionPosibilityState;
import org.tactical.sports.client.event.playground.PlayerSelectedEvent;
import org.tactical.sports.client.event.playground.PlayerSelectedEventHandler;
import org.tactical.sports.client.event.playground.PlaygroundChangedEvent;
import org.tactical.sports.client.event.playground.PlaygroundChangedEventHandler;
import org.tactical.sports.client.event.playground.PlaygroundOutEvent;
import org.tactical.sports.client.event.playground.PlaygroundOutEventHandler;
import org.tactical.sports.client.event.playground.PlaygroundOverEvent;
import org.tactical.sports.client.event.playground.PlaygroundOverEventHandler;
import org.tactical.sports.client.event.playground.TileSelectedEvent;
import org.tactical.sports.client.event.playground.TileSelectedEventHandler;
import org.tactical.sports.client.log.Logger;
import org.tactical.sports.client.view.action.GameActionMenu;
import org.tactical.sports.client.view.action.GameActionMenu.GameActionMenuPresenter;
import org.tactical.sports.shared.domain.playground.tile.PlayerDetails;
import org.tactical.sports.shared.domain.playground.tile.Tile;
import org.tactical.sports.shared.domain.playground.tile.TileIndex;
import org.tactical.sports.shared.rule.action.Action;
import org.tactical.sports.shared.rule.action.ActionType;
import org.tactical.sports.shared.rule.action.manager.ActionManager;
import org.tactical.sports.shared.rule.action.manager.ActionManagerListener;

import com.google.gwt.event.shared.EventBus;

public class GameActionMenuPresenterImpl extends AbstractPresenter<GameActionMenu> implements
		PlayerSelectedEventHandler, TileSelectedEventHandler, PlaygroundOverEventHandler, PlaygroundOutEventHandler, GameActionMenuPresenter,
		ActionManagerListener, PlaygroundChangedEventHandler {

	private Tile m_tile;
	private PlayerDetails m_player;

	private Map<ActionType, ActionManager> m_actionManagers = new HashMap<ActionType, ActionManager>();
	private ActionManager m_currentActionManager;
	private ActionPosibilityEvent m_event = new ActionPosibilityEvent();

	@Override
	public void onActionSelected(ActionType action) {
		GameActionMenu menu = getDisplay();
		if (menu != null) {
			menu.showActionValidation(true, m_tile.getIndex());
		}

		m_currentActionManager = m_actionManagers.get(action);
		if (m_currentActionManager != null) {
			m_currentActionManager.init(m_tile, m_player);
			m_event.setType(action);
			m_event.setState(ActionPosibilityState.Init);
			m_event.setAffectedTiles(m_currentActionManager.getActionPosibility());
			getEventBus().fireEvent(m_event);
		} else {
			Logger.log("Action without logic implemeted yet.");
		}
	}

	@Override
	public void onTileSelected(TileSelectedEvent event) {
		if (m_currentActionManager != null) {
			m_tile = event.getTile();
			m_currentActionManager.update(m_tile);
			// Next test is useful. actionDefined can be call by the update.
			if (m_currentActionManager != null) {
				m_event.setState(ActionPosibilityState.Update);
				m_event.setAffectedTiles(m_currentActionManager.getActionPosibility());
				getEventBus().fireEvent(m_event);
				getDisplay().showActionValidation(true, m_tile.getIndex());
			}
		}
	}

	@Override
	public void onPlayerSelected(PlayerSelectedEvent event) {
		if (m_currentActionManager == null) {
			m_tile = event.getTilewWithPlayerSelected();
			m_player = event.getSelectedPlayer();
			GameActionMenu menu = getDisplay();
			if (menu != null) {
				for (ActionManager actionManager : m_actionManagers.values()) {
					menu.setEnableGameAction(actionManager.getType(), actionManager.isAllowForTile(m_tile));
				}
				menu.showActionChooser(true, m_tile.getIndex());
			}
		}
	}

	@Override
	public void onPlaygroundOver(Tile tile) {
		if (m_currentActionManager != null) {
			actionDefined(m_currentActionManager.getPreview(tile));
		}
	}
	
	@Override
	public void onPlaygroundOut() {
		if (m_currentActionManager != null) {
			actionDefined(m_currentActionManager.getPreview(null));
		}
	}

	@Override
	public void actionDefined(Action action) {
		EventBus eventBus = getEventBus();
		eventBus.fireEvent(new ActionEvent(action, false));
	}

	@Override
	public void actionCancel(Action action) {
		EventBus eventBus = getEventBus();
		eventBus.fireEvent(new ActionEvent(action, true));
	}

	@Override
	public void stop() {
		onActionValidated();
	}

	@Override
	public void setDisplay(GameActionMenu display) {
		super.setDisplay(display);
		GameActionMenu menu = getDisplay();
		if (menu != null) {
			menu.clearGameActions();
			for (ActionType actionType : m_actionManagers.keySet()) {
				menu.addGameAction(actionType);
			}
		}
	}

	@Override
	public void setEventBus(EventBus eventBus) {
		super.setEventBus(eventBus);
		eventBus.addHandler(PlayerSelectedEvent.TYPE, this);
		eventBus.addHandler(TileSelectedEvent.TYPE, this);
		eventBus.addHandler(PlaygroundOverEvent.TYPE, this);
		eventBus.addHandler(PlaygroundOutEvent.TYPE, this);
		eventBus.addHandler(PlaygroundChangedEvent.TYPE, this);
	}

	public void addActionManager(ActionManager manager) {
		m_actionManagers.put(manager.getType(), manager);
		manager.setListener(this);
		GameActionMenu menu = getDisplay();
		if (menu != null) {
			menu.addGameAction(manager.getType());
		}
	}

	@Override
	public void onActionValidated() {
		goToInitialState();
	}

	private void goToInitialState() {
		EventBus eventBus = getEventBus();
		m_event.setState(ActionPosibilityState.Finish);
		m_event.setAffectedTiles(null);
		eventBus.fireEvent(m_event);

		GameActionMenu menu = getDisplay();
		if (menu != null) {
			menu.showActionChooser(false, new TileIndex());
			menu.showActionValidation(false, new TileIndex());
		}
		m_tile = null;
		m_currentActionManager = null;
	}

	@Override
	public void onActionCanceled() {
		if (m_currentActionManager != null) {
			m_currentActionManager.cancel();
		}
		goToInitialState();
	}

	@Override
	public void onPlaygroundChanged(PlaygroundChangedEvent event) {
		for (ActionManager actionManager : m_actionManagers.values()) {
			actionManager.setPlayground(event.getPlayground());
		}
		
	}

}
