package org.tactical.sports.client.activity.place;

import com.google.gwt.place.shared.PlaceTokenizer;

public abstract class ConnectedPlaceTokenizer<T extends ConnectedPlace> implements PlaceTokenizer<T> {

	@Override
	public T getPlace(String token) {
		T t = create();
		t.setNeedConnection(true);
		return t;
	}

	protected abstract T create();
	
}
