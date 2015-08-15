package org.tactical.sports.shared.services;

import org.tactical.sports.shared.domain.User;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {

	void get(String login, String pass, AsyncCallback<User> callback);
	void get(User user, AsyncCallback<User> callback);

	void create(String login, String pass, AsyncCallback<User> callback);


}
