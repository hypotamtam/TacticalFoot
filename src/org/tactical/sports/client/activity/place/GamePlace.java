package org.tactical.sports.client.activity.place;

import org.tactical.sports.shared.domain.Match;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class GamePlace extends Place {
  
	private Match m_match;
	
	private long m_matchId;

	private long m_userTeamId;
	
	public GamePlace(long userTeamId) {
		m_userTeamId = userTeamId;
	}

	public Match getMatch() {
		return m_match;
	}

	public void setMatch(Match match) {
		m_match = match;
		m_matchId = match.getId();
	}

	public long getMatchId() {
		return m_matchId;
	}

	public void setMatchId(long matchId) {
		m_matchId = matchId;
	}


	public long getUserTeamId() {
		return m_userTeamId;
	}

	/**
	 * PlaceTokenizer knows how to serialize the Place's state to a URL token.
	 */
	public static class Tokenizer implements PlaceTokenizer<GamePlace> {

		@Override
		public String getToken(GamePlace place) {
			return String.valueOf(place.getUserTeamId()) + "&" + String.valueOf(place.getMatchId());
		}

		@Override
		public GamePlace getPlace(String token) {
			String[] params = token.split("&"); 
			GamePlace place = new GamePlace(Long.valueOf(params[0]));
			place.setMatchId(Long.valueOf(params[1]));
			return place;
		}

	}
}
