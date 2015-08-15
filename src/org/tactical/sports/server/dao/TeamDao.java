package org.tactical.sports.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.tactical.sports.shared.domain.Team;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public class TeamDao extends GenericObjectifyDAO {
	
	static {
        ObjectifyService.register(Team.class);
    }
	
	public TeamDao(Objectify ofy) {
		super(ofy);
	}
	
	public Team get(long id) {
		return getOfy().get(Team.class, id);
	}
		
	public Team get(String name) {
		return getOfy().query(Team.class).filter("m_name =", name).get();
	}
	
	public void put(Team team) {
		getOfy().put(team);
	}
	
	public List<Team> get(List<String> names) {
		List<Team> res = new ArrayList<Team>();
		for (String name : names) {
			res.add(getOfy().query(Team.class).filter("m_name =", name).get());
		}
		return res;
	}
}
