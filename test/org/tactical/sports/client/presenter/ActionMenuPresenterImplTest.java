package org.tactical.sports.client.presenter;

import junit.framework.TestCase;

import org.mockito.Mockito;
import org.tactical.sports.client.activity.presenter.ActionMenuPresenterImpl;
import org.tactical.sports.client.event.PlayerSelectedEvent;
import org.tactical.sports.client.view.ActionMenu;
import org.tactical.sports.shared.actions.ActionType;
import org.tactical.sports.shared.actions.manager.ActionManager;
import org.tactical.sports.shared.domain.PlayerDetails;
import org.tactical.sports.shared.tiles.FakeTile;
import org.tactical.sports.shared.tiles.Tile;

import com.google.gwt.event.shared.SimpleEventBus;

public class ActionMenuPresenterImplTest extends TestCase {

	public void testRightlyCallsMenuShowActionChooser() {
		ActionMenuPresenterImpl presenter = new ActionMenuPresenterImpl();
		presenter.setEventBus(new SimpleEventBus());
		ActionMenu menu = Mockito.mock(ActionMenu.class);
		presenter.setDisplay(menu);
		Tile tile = new FakeTile(0, 0);
		tile.setPlayer(new PlayerDetails());
		
		presenter.onPlayerSelected(new PlayerSelectedEvent(tile));
		Mockito.verify(menu, Mockito.times(1)).showActionChooser(true);
		presenter.onPlayerSelected(new PlayerSelectedEvent(tile));
		Mockito.verify(menu, Mockito.times(2)).showActionChooser(true);
		
		ActionManager manager = Mockito.mock(ActionManager.class);
		Mockito.when(manager.getType()).thenReturn(ActionType.MOVE);
		presenter.setActionManager(manager);
		presenter.onActionSelected(ActionType.MOVE);
		presenter.onActionValidated();
		Mockito.verify(menu, Mockito.times(1)).showActionChooser(false);
		
		presenter.onPlayerSelected(new PlayerSelectedEvent(tile));
		Mockito.verify(menu, Mockito.times(3)).showActionChooser(true);
		presenter.onActionSelected(ActionType.MOVE);
		presenter.onPlayerSelected(new PlayerSelectedEvent(tile));
		Mockito.verify(menu, Mockito.times(3)).showActionChooser(true);
	}
	
	public void testSetItAsListenerOfActionManagerSet() {
		ActionMenuPresenterImpl presenter = new ActionMenuPresenterImpl();
		ActionManager manager = Mockito.mock(ActionManager.class);
		Mockito.when(manager.getType()).thenReturn(ActionType.MOVE);
		presenter.setActionManager(manager);
		Mockito.verify(manager, Mockito.times(1)).setListener(presenter);
	}
	
	public void testRightlyCallsActionManagerInit() {
		ActionMenuPresenterImpl presenter = new ActionMenuPresenterImpl();
		presenter.setEventBus(new SimpleEventBus());
		ActionManager manager = Mockito.mock(ActionManager.class);
		Mockito.when(manager.getType()).thenReturn(ActionType.MOVE);
		presenter.setActionManager(manager);
		
		presenter.onActionSelected(ActionType.MOVE);
		Mockito.verify(manager, Mockito.times(1)).init(null);
		presenter.onActionValidated();
		Tile tile = new FakeTile(0, 0);
		tile.setPlayer(new PlayerDetails());
		presenter.onPlayerSelected(new PlayerSelectedEvent(tile));
		presenter.onActionSelected(ActionType.MOVE);
		Mockito.verify(manager, Mockito.times(1)).init(tile);
		presenter.onActionSelected(ActionType.MOVE);
		Mockito.verify(manager, Mockito.times(2)).init(tile);
	}
	
	public void testRightlyCallsActionManagerCancel() {
		ActionMenuPresenterImpl presenter = new ActionMenuPresenterImpl();
		presenter.setEventBus(new SimpleEventBus());
		ActionManager manager = Mockito.mock(ActionManager.class);
		Mockito.when(manager.getType()).thenReturn(ActionType.MOVE);
		presenter.setActionManager(manager);
		
		presenter.onActionSelected(ActionType.MOVE);
		presenter.actionDefined(null); 
		Mockito.verify(manager, Mockito.never()).cancel();
		presenter.onActionSelected(ActionType.MOVE);
		presenter.onActionValidated();
		presenter.onActionSelected(ActionType.MOVE);
		Mockito.verify(manager, Mockito.times(1)).cancel();
		Tile tile = new FakeTile(0, 0);
		tile.setPlayer(new PlayerDetails());
		presenter.onPlayerSelected(new PlayerSelectedEvent(tile));
		presenter.onActionSelected(ActionType.MOVE);
		presenter.onActionValidated();
		Mockito.verify(manager, Mockito.times(2)).cancel();
	}
	
	
}
