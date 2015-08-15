package org.tactical.sports.server.dao;

import com.googlecode.objectify.Objectify;


public class GenericObjectifyDAO {
	private Objectify m_ofy;

	public GenericObjectifyDAO(Objectify ofy) {
		m_ofy = ofy;
	}
	
	public Objectify getOfy() {
		return m_ofy;
	}
	
}
