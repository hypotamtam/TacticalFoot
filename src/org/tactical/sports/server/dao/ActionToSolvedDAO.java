package org.tactical.sports.server.dao;

import org.tactical.sports.server.domain.ActionToSolved;
import org.tactical.sports.shared.domain.Match;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

public class ActionToSolvedDAO extends GenericObjectifyDAO {

	static {
		ObjectifyService.register(ActionToSolved.class);
	}
	
	public ActionToSolvedDAO(Objectify ofy) {
		super(ofy);
	}
	
	public ActionToSolved get(long matchId, int roundIndex) {
		Key<Match> matchKey = new Key<Match>(Match.class, matchId);
		Query<ActionToSolved> query = getOfy().query(ActionToSolved.class);
		query = query.ancestor(matchKey);
		query = query.filter("m_roundIndex =", roundIndex);
		return query.get();
	}
	
	public void put(ActionToSolved actionToSolved) {
		getOfy().put(actionToSolved);
	}
	
	
}
