package org.tactical.sports.client.activity.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ConnectionPlace extends Place {


	private ConnectedPlace m_nextPlace;


	public ConnectionPlace() {
	}

	public ConnectionPlace(ConnectedPlace nextPlace) {
		m_nextPlace = nextPlace;
	}
	

	public ConnectedPlace getNextPlace() {
		return m_nextPlace;
	}


	/**
	 * PlaceTokenizer knows how to serialize the Place's state to a URL token.
	 */
	public static class Tokenizer implements PlaceTokenizer<ConnectionPlace> {

		@Override
		public String getToken(ConnectionPlace place) {
			return "";
		}

		@Override
		public ConnectionPlace getPlace(String token) {
			return new ConnectionPlace();
		}

	}
	
}
