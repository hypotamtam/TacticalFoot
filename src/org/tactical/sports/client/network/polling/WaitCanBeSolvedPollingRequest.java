package org.tactical.sports.client.network.polling;

import org.tactical.sports.shared.rule.solver.RoundSolution;
import org.tactical.sports.shared.services.MatchService;
import org.tactical.sports.shared.services.MatchServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class WaitCanBeSolvedPollingRequest extends AbstarctPollingResquest<RoundSolution> {

	private MatchServiceAsync m_matchService = GWT.create(MatchService.class);
	private long m_matchId;
	private int m_roundIndex;
	
	private boolean m_isWaitingResponse;
	private AsyncCallback<RoundSolution> m_callback;
	
	public WaitCanBeSolvedPollingRequest(long matchId, int roundIndex, AsyncCallback<RoundSolution> callback) {
		m_matchId = matchId;
		m_roundIndex = roundIndex;
		m_callback = callback;
		m_isWaitingResponse = false;
	}

	@Override
	public void doAnIteration() {
		if (m_isWaitingResponse) {
			return;
		} 
		
		m_isWaitingResponse = true;
		m_matchService.getRoundSolution(m_matchId, m_roundIndex, this);
	}

	@Override
	public void onFailure(Throwable caught) {
		m_isWaitingResponse = false;
		m_callback.onFailure(caught);
		finished();
	}

	@Override
	public void onSuccess(RoundSolution result) {
		m_isWaitingResponse = false;
		if (result != null) {
			m_callback.onSuccess(result);
			finished();
		}
	}
	
}
