package org.tactical.sports.server.dao;

import java.util.List;

import org.tactical.sports.shared.domain.Match;
import org.tactical.sports.shared.domain.MatchState;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public class MatchDAO extends GenericObjectifyDAO {

	static {
        ObjectifyService.register(Match.class);
    }
	
	public MatchDAO(Objectify ofy) {
		super(ofy);
	}
	
	public void put(Match match) {
		getOfy().put(match);
	}
	
	public Match get(long id) {
		return getOfy().get(Match.class, id);
	}
	
	public List<Match> getNotStarted() {
		return getOfy().query(Match.class).filter("m_state =", MatchState.NOT_STARTED).list();
	}
	
}
