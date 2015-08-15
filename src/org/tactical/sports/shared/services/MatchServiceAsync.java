package org.tactical.sports.shared.services;

import java.util.Collection;
import java.util.List;

import org.tactical.sports.shared.domain.Match;
import org.tactical.sports.shared.domain.playground.Playground;
import org.tactical.sports.shared.rule.action.Action;
import org.tactical.sports.shared.rule.solver.RoundSolution;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MatchServiceAsync {

	void get(long id, AsyncCallback<Match> callback);

	void setActionToSolved(Collection<Action> actions, long matchId,
			Playground playground, boolean isLocal,
			AsyncCallback<RoundSolution> callback);

	void getRoundSolution(long matchId, int roundIndex, AsyncCallback<RoundSolution> callback);

	void create(long teamId, AsyncCallback<Match> callback);

	void join(long teamId, long matchId, AsyncCallback<Match> callback);

	void getMatchsNotStarted(AsyncCallback<List<Match>> callback);


}
