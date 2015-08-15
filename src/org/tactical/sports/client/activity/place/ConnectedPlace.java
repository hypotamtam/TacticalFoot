package org.tactical.sports.client.activity.place;

import org.tactical.sports.shared.domain.User;

import com.google.gwt.place.shared.Place;

public abstract class ConnectedPlace extends Place {
	
	private User m_user = null;
	private boolean m_needConnection = true;

	public User getUser() {
		return m_user;
	}
	
	public void setUser(User user) {
		m_user = user;
	}
	
	public boolean isNeedConnection() {
		return m_needConnection;
	}
	
	public void setNeedConnection(boolean needConnection) {
		m_needConnection = needConnection;
	}
	
	public abstract ConnectedPlace clone();
}
