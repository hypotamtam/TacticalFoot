package org.tactical.sports.shared.services;

import java.util.Collection;
import java.util.List;

import org.tactical.sports.shared.domain.Match;
import org.tactical.sports.shared.domain.playground.Playground;
import org.tactical.sports.shared.rule.action.Action;
import org.tactical.sports.shared.rule.solver.RoundSolution;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("match")
public interface MatchService extends RemoteService {

	Match create(long teamId);
	Match join(long teamId, long matchId) throws Exception ;
	Match get(long id);
	List<Match> getMatchsNotStarted();
	
	
	RoundSolution setActionToSolved(Collection<Action> actions, long matchId, Playground playground, boolean isLocal) throws Exception;
	RoundSolution getRoundSolution(long matchId, int roundIndex);
}
