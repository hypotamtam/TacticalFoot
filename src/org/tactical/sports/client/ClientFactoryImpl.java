package org.tactical.sports.client;

import org.tactical.sports.client.view.ConnectionView;
import org.tactical.sports.client.view.ConnectionViewImpl;
import org.tactical.sports.client.view.GameView;
import org.tactical.sports.client.view.GameViewImpl;
import org.tactical.sports.client.view.MatchChooserView;
import org.tactical.sports.client.view.MatchChooserViewImpl;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;

public class ClientFactoryImpl implements ClientFactory {
	private static final EventBus EVENT_BUS = new SimpleEventBus();
	private static final PlaceController PLACE_CONTROLLER = new PlaceController(EVENT_BUS);
	private static final GameView GAME_VIEW = new GameViewImpl();
	private static final ConnectionView CONNECTION_VIEW = new ConnectionViewImpl();
	private static final MatchChooserView MATCH_CHOOSER_VIEW = new MatchChooserViewImpl();

	public ClientFactoryImpl() {
	}

	@Override
	public EventBus getEventBus() {
		return EVENT_BUS;
	}

	@Override
	public PlaceController getPlaceController() {
		return PLACE_CONTROLLER;
	}

	@Override
	public GameView getGameView() {
		return GAME_VIEW;
	}

	@Override
	public ConnectionView getConnectionView() {
		return CONNECTION_VIEW;
	}

	@Override
	public MatchChooserView getMatchChooserView() {
		return MATCH_CHOOSER_VIEW;
	}

}
