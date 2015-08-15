package org.tactical.sports.client.network;

import java.util.ArrayList;
import java.util.Collection;

import org.tactical.sports.client.log.Logger;
import org.tactical.sports.client.network.polling.PollingRequester;
import org.tactical.sports.client.network.polling.WaitCanBeSolvedPollingRequest;
import org.tactical.sports.shared.domain.Match;
import org.tactical.sports.shared.domain.playground.Playground;
import org.tactical.sports.shared.rule.action.Action;
import org.tactical.sports.shared.rule.solver.RoundSolution;
import org.tactical.sports.shared.services.MatchService;
import org.tactical.sports.shared.services.MatchServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RoundSolverService {

	private MatchServiceAsync m_matchService = GWT.create(MatchService.class);
	private Match m_match;
	private Playground m_playground;
	
	public RoundSolverService(Match match, Playground playground) {
		m_match = match;
		m_playground = playground;
	}
	
	public void solvedActions(Collection<Action> actions, long teamId, final AsyncCallback<RoundSolution> callback) {
		boolean isLocal = (m_match.getLocal().getId() == teamId);
		ArrayList<Action> serializableAction = new ArrayList<Action>(actions);
		m_matchService.setActionToSolved(serializableAction, m_match.getId(), m_playground, isLocal, new AsyncCallback<RoundSolution>() {
			
			@Override
			public void onSuccess(RoundSolution result) {
				if (result != null) {
					callback.onSuccess(result);
				} else {
					waitRoundSolution(callback);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				callback.onFailure(caught);
				Logger.log("Error while we set actions");
			}
		});
	}
	
	
	private void waitRoundSolution(final AsyncCallback<RoundSolution> callback) {
		WaitCanBeSolvedPollingRequest request = new WaitCanBeSolvedPollingRequest(m_match.getId(), m_match.getRoundIndex(), new AsyncCallback<RoundSolution>() {
			
			@Override
			public void onSuccess(RoundSolution result) {
				callback.onSuccess(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				callback.onFailure(caught);
				Logger.log("Error while we wait the opponent actions");
			}
		});
		
		PollingRequester<RoundSolution> requester = new PollingRequester<RoundSolution>(request, 1000);
		requester.start();
	}
}
