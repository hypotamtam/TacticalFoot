package org.tactical.sports.client.activity.place;

public class HomePlaceTokenizer extends ConnectedPlaceTokenizer<HomePlace> {

	@Override
	protected HomePlace create() {
		return new HomePlace();
	}

	@Override
	public String getToken(HomePlace place) {
		return "";
	}
}
