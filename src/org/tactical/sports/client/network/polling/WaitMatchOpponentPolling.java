package org.tactical.sports.client.network.polling;

import org.tactical.sports.shared.domain.Match;
import org.tactical.sports.shared.domain.MatchState;
import org.tactical.sports.shared.services.MatchService;
import org.tactical.sports.shared.services.MatchServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class WaitMatchOpponentPolling extends AbstarctPollingResquest<Match> {

	private MatchServiceAsync m_matchService = GWT.create(MatchService.class);
	
	private Match m_match;
	private AsyncCallback<Match> m_callback;
	private boolean m_isWaitingReponse;

	public WaitMatchOpponentPolling(Match match, AsyncCallback<Match> callback) {
		m_match = match;
		m_callback = callback;
		m_isWaitingReponse = false;
	}
	
	@Override
	public void doAnIteration() {
		if (m_isWaitingReponse) {
			return;
		}
		m_isWaitingReponse = true;
		m_matchService.get(m_match.getId(), this);
	}

	@Override
	public void onFailure(Throwable caught) {
		m_callback.onFailure(caught);	
		finished();
		m_isWaitingReponse = false;
	}

	@Override
	public void onSuccess(Match result) {
		if (result.getState() == MatchState.IN_GAME) {
			m_callback.onSuccess(result);
			finished();
		} 
		m_isWaitingReponse = false;
	}
	
}
