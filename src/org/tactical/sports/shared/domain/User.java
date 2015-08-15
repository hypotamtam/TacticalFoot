package org.tactical.sports.shared.domain;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private String m_login;

	private String m_pass;

	@Embedded
	private Team m_team;
	
	public User() {};
	
	public User(String login, String pass) {
		m_pass = pass;
		m_login = login;
	}

	public String getLogin() {
		return m_login;
	}

	public String getPassword() {
		return m_pass;
	}

	public Team getTeam() {
		return m_team;
	}

	public void setTeam(Team team) {
		m_team = team;
	}

}
