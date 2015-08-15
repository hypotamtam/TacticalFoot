package org.tactical.sports.server.dao;

import org.tactical.sports.shared.domain.User;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public class UserDAO extends GenericObjectifyDAO {

	static {
		ObjectifyService.register(User.class);
	}
	
	public UserDAO(Objectify ofy) {
		super(ofy);
	}
	
	public User get(String login, String pass) {
		User user = getOfy().get(User.class, login);
		return user.getPassword().equals(pass) ? user : null;
	}
	
	public void put(User user) {
		getOfy().put(user);
	}
}
