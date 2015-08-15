package org.tactical.sports.client.network;

import org.tactical.sports.shared.domain.User;
import org.tactical.sports.shared.services.UserService;
import org.tactical.sports.shared.services.UserServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ConnectionService {
	private UserServiceAsync m_userService = GWT.create(UserService.class);

	public void autoLogin(User user, AsyncCallback<User> callback) {
		m_userService.get(user, callback);
	}
	
	public void login(String login, String password, AsyncCallback<User> callback) {
		m_userService.get(login, password, callback);
	}
	
	public void register(String login, String password, AsyncCallback<User> callback) {
		m_userService.create(login, password, callback);
	}
}
