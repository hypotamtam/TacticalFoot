package org.tactical.sports.shared.services;

import org.tactical.sports.shared.domain.User;
import org.tactical.sports.shared.services.error.UserAlreadyExistsException;
import org.tactical.sports.shared.services.error.UserNotFoundException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("user")
public interface UserService extends RemoteService {

	User get(String login, String pass)  throws UserNotFoundException;
	User get(User user)  throws UserNotFoundException;
	
	User create(String login, String pass)  throws UserAlreadyExistsException;
	
}
