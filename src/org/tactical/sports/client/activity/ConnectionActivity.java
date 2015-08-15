package org.tactical.sports.client.activity;

import java.util.Date;

import org.tactical.sports.client.ClientFactory;
import org.tactical.sports.client.activity.place.ConnectedPlace;
import org.tactical.sports.client.activity.place.ConnectionPlace;
import org.tactical.sports.client.activity.place.HomePlace;
import org.tactical.sports.client.activity.presenter.AbstractPresenter;
import org.tactical.sports.client.network.ConnectionService;
import org.tactical.sports.client.network.Future;
import org.tactical.sports.client.network.FutureListener;
import org.tactical.sports.client.view.ConnectionView;
import org.tactical.sports.client.view.ConnectionView.ConnectionPresenter;
import org.tactical.sports.shared.domain.User;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ConnectionActivity extends AbstractPresenter<ConnectionView> implements Activity, ConnectionPresenter, FutureListener<User> {

	private static final String USER_NOT_FOUND_STR = "User not found";
	private static final String USER_ALREADY_EXIST_STR = "User already exist";
	private static final String LOGGING_STR = "logging in...";
	private static final String REGISTRERING_STR = "creating your team...";
	
	private static final String COOKIE_PASS = "pass";
	private static final String COOKIE_LOGIN = "login";
	
	private ConnectionService m_connectionService = new ConnectionService();
	private String m_errorStr;
	private ClientFactory m_clientFactory;
	private ConnectedPlace m_nextPlace;
	
	public ConnectionActivity(ConnectionPlace place, ClientFactory clientFactory) {
		m_clientFactory = clientFactory;
		m_nextPlace = place.getNextPlace();
		if (m_nextPlace == null) {
			m_nextPlace = new HomePlace();
		}
		m_nextPlace.setNeedConnection(false);
		
		ConnectionView connectionView = clientFactory.getConnectionView();
		connectionView.setPresenter(this);
		setDisplay(connectionView);
	}

	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		containerWidget.setWidget(getDisplay());
		User user = loadUser();
		if (user != null) {
			m_errorStr = "";
			m_connectionService.autoLogin(user, new WaitingFuture<User>(this, LOGGING_STR));
		}
	}
	
	@Override
	public void register(String name, String pass) {
		m_errorStr = USER_ALREADY_EXIST_STR;
		m_connectionService.register(name, pass, new WaitingFuture<User>(this, REGISTRERING_STR));
	}

	@Override
	public void login(String name, String pass) {
		m_errorStr = USER_NOT_FOUND_STR;
		m_connectionService.login(name, pass, new WaitingFuture<User>(this, LOGGING_STR));
	}
	
	private void saveUser(User user) {
		Date expirationDate =  new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
		Cookies.setCookie(COOKIE_LOGIN, user.getLogin(), expirationDate);
		Cookies.setCookie(COOKIE_PASS, user.getPassword(), expirationDate);
	}

	private void clearSavedUser() {
		Cookies.removeCookie(COOKIE_LOGIN);
		Cookies.removeCookie(COOKIE_PASS);
	}
	
	private User loadUser() {
		String login = Cookies.getCookie(COOKIE_LOGIN);
		String pass = Cookies.getCookie(COOKIE_PASS);
		return ((login == null) || (pass == null)) ? null : new User(login, pass);
	}

	@Override
	public void onDone(Future<User> future) {
		try {
			User user = future.getResult();
			saveUser(user);
			m_nextPlace.setUser(user); 
	        m_clientFactory.getPlaceController().goTo(m_nextPlace);
		} catch (Throwable e) {
			clearSavedUser();
			if (!m_errorStr.isEmpty()) {
				getDisplay().showError(m_errorStr);
			}
		}
	}

	@Override
	public String mayStop() {
		return null;
	}

	@Override
	public void onCancel() {
	}

	@Override
	public void onStop() {
	}

}
