package org.tactical.sports.server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import org.tactical.sports.server.dao.TeamDao;
import org.tactical.sports.server.dao.UserDAO;
import org.tactical.sports.shared.domain.Team;
import org.tactical.sports.shared.domain.TeamMember;
import org.tactical.sports.shared.domain.User;
import org.tactical.sports.shared.services.UserService;
import org.tactical.sports.shared.services.error.UserAlreadyExistsException;
import org.tactical.sports.shared.services.error.UserNotFoundException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyService;

public class UserServiceImpl extends RemoteServiceServlet implements UserService {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(UserServiceImpl.class.getName());

	@Override
	public User get(String login, String pass) throws UserNotFoundException {
		 return get(new User(login, getHashPassword(pass)));
	}

	@Override
	public User get(User user) throws UserNotFoundException {
		UserDAO dao = new UserDAO(ObjectifyService.begin());
		User ret = dao.get(user.getLogin(), user.getPassword());
		if (ret == null) {
			throw new UserNotFoundException();
		}
		return ret;
	}
	
	@Override
	public User create(String login, String pass) throws UserAlreadyExistsException {
		UserDAO dao = new UserDAO(ObjectifyService.begin());
		String hashPass = getHashPassword(pass);
		// Pas secure si 2 creations identique ne meme temps mais bon....
		try {
			dao.get(login, hashPass);			
			throw new UserAlreadyExistsException();
		} catch (NotFoundException e) {
		}
		
		User user = new User(login, hashPass);
		Team userTeam = create(login);
		TeamDao teamDao = new TeamDao(ObjectifyService.begin());
		teamDao.put(userTeam);
		user.setTeam(userTeam);
		dao.put(user);
		return user;

	}

	private String getHashPassword(String pass) {
		byte[] bytesOfMessage = pass.getBytes();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return new String(md.digest(bytesOfMessage));
		} catch (NoSuchAlgorithmException e) {
			log.severe("Message digest with MD5 doesn't exists on the server");
		}
		return "";
	}

	private Team create(String name) {
		Team team = new Team(name);
		team.addPlayer(new TeamMember());
		team.addPlayer(new TeamMember());
		team.addPlayer(new TeamMember());
		team.addPlayer(new TeamMember());
		return team;
	}

}
