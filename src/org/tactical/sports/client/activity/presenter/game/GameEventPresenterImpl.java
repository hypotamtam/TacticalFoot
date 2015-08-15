package org.tactical.sports.client.activity.presenter.game;

import org.tactical.sports.client.activity.presenter.AbstractPresenter;
import org.tactical.sports.client.event.game.GameEvent;
import org.tactical.sports.client.event.game.GameEventHandler;
import org.tactical.sports.client.view.gameevent.GameEventView;
import org.tactical.sports.client.view.gameevent.GameEventView.GameEventPresenter;

import com.google.gwt.event.shared.EventBus;

public class GameEventPresenterImpl extends AbstractPresenter<GameEventView> implements GameEventPresenter, GameEventHandler {

	@Override
	public void setEventBus(EventBus eventBus) {
		super.setEventBus(eventBus);
		eventBus.addHandler(GameEvent.TYPE, this);
	}

	@Override
	public void eventShown() {
	}

	@Override
	public void onGameEvent(String event) {
		getDisplay().showEvent(event);
	}

}
