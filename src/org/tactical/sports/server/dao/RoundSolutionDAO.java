package org.tactical.sports.server.dao;

import org.tactical.sports.shared.rule.solver.RoundSolution;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public class RoundSolutionDAO extends GenericObjectifyDAO {
	static {
		ObjectifyService.register(RoundSolution.class);
	}
	
	public RoundSolutionDAO(Objectify ofy) {
		super(ofy);
	}
	
	public RoundSolution get(String roundSolutionId) {
		return getOfy().get(RoundSolution.class, roundSolutionId);
	}
	
	public void put(RoundSolution roundSolution) {
		getOfy().put(roundSolution);
	}
	
}
