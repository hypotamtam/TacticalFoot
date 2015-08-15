package org.tactical.sports.shared.domain;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Serialized;

@Entity
public class Match implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long m_id;
	
	@Embedded
	private Team m_local;
	@Embedded
	private Team m_visitor;
	
	private int m_localScore;
	private int m_visitorScore;
	
	private int m_roundIndex;
	
	private MatchState m_state;
	
	@Serialized
	private MatchResult m_result;
	
	private Match() {
		m_roundIndex = 1;
		m_state = MatchState.NOT_STARTED;
	}

	public Match(Team localTeam) {
		this();
		m_local = localTeam;
		if (localTeam == null) {
			throw new NullPointerException("Locale team must existe!");
		}
	}
	
	public Team getLocal() {
		return m_local;
	}

	public Team getVisitor() {
		return m_visitor;
	}

	public void setVisitor(Team visitor) {
		m_visitor = visitor;
		m_state = ((m_local != null) && (m_visitor != null)) ? MatchState.IN_GAME : MatchState.NOT_STARTED;
	}

	public int getLocalScore() {
		return m_localScore;
	}

	public void setLocalScore(int localScore) {
		m_localScore = localScore;
	}

	public int getVisitorScore() {
		return m_visitorScore;
	}

	public void setVisitorScore(int visitorScore) {
		m_visitorScore = visitorScore;
	}

	public int increaseRoundIndex() {
		return m_roundIndex++;
	}
	
	public int getRoundIndex() {
		return m_roundIndex;
	}

	public long getId() {
		return m_id;
	}

	public MatchState getState() {
		return m_state;
	}

	public MatchResult getResult() {
		return m_result;
	}

	public void setResult(MatchResult result) {
		m_result = result;
		m_state = MatchState.FINISHED;
	}

	@Override
	public String toString() {
		return "Match [id=" + m_id + ", localTeam=" + m_local + ", visitorTeam=" + m_visitor + "]";
	}
	
}
