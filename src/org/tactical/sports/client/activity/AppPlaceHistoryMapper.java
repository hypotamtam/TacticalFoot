package org.tactical.sports.client.activity;

import org.tactical.sports.client.activity.place.ConnectionPlace;
import org.tactical.sports.client.activity.place.GamePlace;
import org.tactical.sports.client.activity.place.HomePlaceTokenizer;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers( { ConnectionPlace.Tokenizer.class, HomePlaceTokenizer.class, GamePlace.Tokenizer.class})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {
}
