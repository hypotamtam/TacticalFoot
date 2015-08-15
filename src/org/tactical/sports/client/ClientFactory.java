package org.tactical.sports.client;

import org.tactical.sports.client.view.ConnectionView;
import org.tactical.sports.client.view.GameView;
import org.tactical.sports.client.view.MatchChooserView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;

public interface ClientFactory {
	EventBus getEventBus();
	PlaceController getPlaceController();
	
	GameView getGameView();
	ConnectionView getConnectionView();
	MatchChooserView getMatchChooserView();
}
