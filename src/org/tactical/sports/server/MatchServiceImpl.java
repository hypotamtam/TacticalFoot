package org.tactical.sports.server;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.List;

import org.tactical.sports.server.dao.ActionToSolvedDAO;
import org.tactical.sports.server.dao.MatchDAO;
import org.tactical.sports.server.dao.RoundSolutionDAO;
import org.tactical.sports.server.dao.TeamDao;
import org.tactical.sports.server.domain.ActionToSolved;
import org.tactical.sports.server.rule.solver.RoundMovementSolver;
import org.tactical.sports.server.rule.solver.RoundPassSolver;
import org.tactical.sports.server.rule.solver.RoundSolver;
import org.tactical.sports.shared.Constants;
import org.tactical.sports.shared.domain.Match;
import org.tactical.sports.shared.domain.MatchResult;
import org.tactical.sports.shared.domain.MatchState;
import org.tactical.sports.shared.domain.Team;
import org.tactical.sports.shared.domain.playground.Playground;
import org.tactical.sports.shared.rule.action.Action;
import org.tactical.sports.shared.rule.solver.RoundSolution;
import org.tactical.sports.shared.services.MatchService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyService;

public class MatchServiceImpl extends RemoteServiceServlet implements MatchService {

	private static final long serialVersionUID = 1L;

	@Override
	public Match create(long teamId) {
		MatchDAO dao = new MatchDAO(ObjectifyService.begin());
		
		TeamDao teamDao = new TeamDao(dao.getOfy());
		Team team = teamDao.get(teamId);
		
		Match match = new Match(team);
		dao.put(match);
		return match;
	}

	@Override
	public Match join(long teamId, long matchId) throws Exception {
		TeamDao teamDao = new TeamDao(ObjectifyService.begin());
		Team team = teamDao.get(teamId);
		
		Match ret = null;
		MatchDAO dao = new MatchDAO(ObjectifyService.beginTransaction());
		try {
			ret = dao.get(matchId);
			if (ret.getState() == MatchState.IN_GAME) {
				throw new Exception("Match en cours");
			}
			ret.setVisitor(team);
			dao.put(ret);
			dao.getOfy().getTxn().commit();
		} catch (ConcurrentModificationException e) {
			if (dao.getOfy().getTxn().isActive()) {
				dao.getOfy().getTxn().rollback();
			}
			throw new Exception("Match en cours");
		} finally {
			if (dao.getOfy().getTxn().isActive()) {
				dao.getOfy().getTxn().rollback();
			}
		}
		return ret;
	}

	@Override
	public List<Match> getMatchsNotStarted() {
		MatchDAO dao = new MatchDAO(ObjectifyService.begin());
		return dao.getNotStarted();
	}

	@Override
	public Match get(long id) {
		MatchDAO dao = new MatchDAO(ObjectifyService.begin());
		return dao.get(id);
	}

	@Override
	public RoundSolution setActionToSolved(Collection<Action> actions, long matchId, Playground playground, boolean isLocal) throws Exception {
		RoundSolution ret = null;
		MatchDAO matchDAO = new MatchDAO(ObjectifyService.begin());
		Match match = matchDAO.get(matchId);
		if (match.getState() == MatchState.FINISHED) {
			throw new Exception("Match already finiched.");
		}
		boolean hasntSetAction = true;
		ActionToSolvedDAO dao = new ActionToSolvedDAO(ObjectifyService.beginTransaction());
		while (hasntSetAction) {
			try {
				ActionToSolved actionToSolved = dao.get(match.getId(), match.getRoundIndex());
				if (actionToSolved == null) {
					actionToSolved = new ActionToSolved(match);
					actionToSolved.setRoundIndex(match.getRoundIndex());
					actionToSolved.setAction(actions, isLocal);
				} else {
					actionToSolved.setAction(actions, isLocal);
					RoundSolver solver = new RoundSolver(actionToSolved.getRandomSeed(), new RoundMovementSolver(), new RoundPassSolver());
					ret = solver.solveActions(actionToSolved.getActions(), playground, match);
				}
				dao.put(actionToSolved);
				dao.getOfy().getTxn().commit();
				hasntSetAction = false;
			} catch (ConcurrentModificationException e) {
				if (dao.getOfy().getTxn().isActive()) {
					dao.getOfy().getTxn().rollback();
				}
			} finally {
				if (dao.getOfy().getTxn().isActive()) {
					dao.getOfy().getTxn().rollback();
				}
			}
		}

		if (ret != null) {
			match.increaseRoundIndex();
			if (match.getRoundIndex() == Constants.MATCH_ROUND_COUNT) {
				Team winnerTeam = match.getLocal();
				Team loserTeam = match.getVisitor();	
				boolean isADraw = ret.getLocaleTeamScore() == ret.getVisitorTeamScore();
				if (ret.getLocaleTeamScore() < ret.getVisitorTeamScore()) {
					winnerTeam = match.getVisitor();
					loserTeam = match.getLocal();
				} 
				int winBet = (int) (winnerTeam.getExperience() * 0.1);
				int lostBet = (int) (loserTeam.getExperience() * 0.1);
				MatchResult result = new MatchResult(winnerTeam.getId(), winBet, lostBet, isADraw);
				match.setResult(result);
			}
			matchDAO.put(match);
			RoundSolutionDAO roundSolutionDAO = new RoundSolutionDAO(matchDAO.getOfy());
			roundSolutionDAO.put(ret);
		}
		return ret;
	}

	@Override
	public RoundSolution getRoundSolution(long matchId, int roundIndex) {
		RoundSolutionDAO dao = new RoundSolutionDAO(ObjectifyService.begin());
		RoundSolution ret = null;
		try {
			ret = dao.get(RoundSolution.generateId(matchId, roundIndex));
		} catch (NotFoundException e) {
		}
		
		return ret;
	}


}
