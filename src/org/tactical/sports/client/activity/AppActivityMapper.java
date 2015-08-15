package org.tactical.sports.client.activity;

import org.tactical.sports.client.ClientFactory;
import org.tactical.sports.client.activity.place.ConnectedPlace;
import org.tactical.sports.client.activity.place.ConnectionPlace;
import org.tactical.sports.client.activity.place.GamePlace;
import org.tactical.sports.client.activity.place.HomePlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class AppActivityMapper implements ActivityMapper {

	private ClientFactory m_clientFactory;

	public AppActivityMapper(ClientFactory clientFactory) {
		super();
		m_clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof ConnectedPlace) {
			ConnectedPlace connectedPlace = (ConnectedPlace) place;
			if (connectedPlace.isNeedConnection()) {
				ConnectionPlace connectionPlace = new ConnectionPlace(connectedPlace.clone());
				return new ConnectionActivity(connectionPlace, m_clientFactory);
			}
		}
		
		if (place instanceof GamePlace) {
			return new GameActivity((GamePlace) place, m_clientFactory);
		} else if (place instanceof ConnectionPlace) {
			return new ConnectionActivity((ConnectionPlace) place, m_clientFactory);
		} else if (place instanceof HomePlace) {
			return new HomeActivity((HomePlace) place, m_clientFactory);
		}
		return null;
	}

}
