package org.tactical.sports.shared.domain.playground.tile;

import java.io.Serializable;

public class PlayerDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	private long m_teamId;

	private long m_id;
	private String m_name;

	private boolean m_hasBall;

	public void setId(long id) {
		this.m_id = id;
	}

	public long getId() {
		return m_id;
	}

	public void setName(String userName) {
		this.m_name = userName;
	}

	public String getName() {
		return m_name;
	}

	public void setTeamId(long teamId) {
		m_teamId = teamId;
	}

	public long getTeamId() {
		return m_teamId;
	}

	public void setHasBall(boolean hasBall) {
		m_hasBall = hasBall;
	}

	public boolean hasBall() {
		return m_hasBall;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (m_id ^ (m_id >>> 32));
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
		PlayerDetails other = (PlayerDetails) obj;
		if (m_id != other.m_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[id=" + m_id + " teamid=" + m_teamId + "]";
	}
}
