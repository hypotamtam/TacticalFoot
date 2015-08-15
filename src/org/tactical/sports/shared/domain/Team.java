package org.tactical.sports.shared.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.NotSaved;

@Entity
public class Team implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long m_id;
	
	private String m_name ;
	
	private int m_experience;
	
	@NotSaved
	private Map<Long,TeamMember> m_players;
	
	public Team() {
		this("");
	}
	
	public Team(String name) {
		m_name = name;
		m_experience = 1000;
		m_players = new HashMap<Long, TeamMember>();
	}
	
	public void addPlayer(TeamMember member) {
		m_players.put(member.getId(), member);
	}
	
	public long getId() {
		return m_id;
	}
	
	public int getExperience() {
		return m_experience;
	}

	public boolean hasMember(Long memberId) {
		return m_players.containsKey(memberId);
	}

	public Collection<TeamMember> getPlayers() {
		return m_players.values();
	}

	public String getName() {
		return m_name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((m_id == null) ? 0 : m_id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		if (m_id == null) {
			if (other.m_id != null)
				return false;
		} else if (!m_id.equals(other.m_id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Team [id=" + m_id + ", name=" + m_name + "]";
	}

}
