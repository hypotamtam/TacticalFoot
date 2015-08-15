package org.tactical.sports.client.network;

import java.util.List;

import org.tactical.sports.client.network.polling.PollingRequester;
import org.tactical.sports.client.network.polling.WaitMatchOpponentPolling;
import org.tactical.sports.shared.domain.Match;
import org.tactical.sports.shared.domain.Team;
import org.tactical.sports.shared.services.MatchService;
import org.tactical.sports.shared.services.MatchServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class MatchStarterService {

	private MatchServiceAsync m_matchService = GWT.create(MatchService.class);
	
	public void joinMatch(Team team, Match match, AsyncCallback<Match> callback) {
		m_matchService.join(team.getId(), match.getId(), callback);
	}
	
	public void getMatchsNotStarted(AsyncCallback<List<Match>> callback) {
		m_matchService.getMatchsNotStarted(callback);
	}
	
	public void createMachtAndWaitOpponent(Team team, final AsyncCallback<Match> callback) {
		m_matchService.create(team.getId(), new AsyncCallback<Match>() {
			
			@Override
			public void onSuccess(Match result) {
				WaitMatchOpponentPolling request = new WaitMatchOpponentPolling(result, callback);
				PollingRequester<Match> requester = new PollingRequester<Match>(request, 2000);
				requester.start();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				callback.onFailure(caught);
			}
		});
	}
	
	public void getMatch(long matchId, AsyncCallback<Match> callback) {
		m_matchService.get(matchId, callback);
	}
	
}
