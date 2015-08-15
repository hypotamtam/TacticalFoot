package org.tactical.sports.shared.rule.solver;

import java.io.Serializable;

import javax.persistence.Id;

import org.tactical.sports.shared.domain.Match;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Serialized;

@Entity
public class RoundSolution implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String m_id; 
		
	private int m_localeTeamScore;
	private int m_visitorTeamScore;
	
	@Serialized
	private RoundResolution m_resolution;

	protected RoundSolution() {}
	
	public RoundSolution(Match match) {
		m_id = RoundSolution.generateId(match.getId(), match.getRoundIndex());
		m_localeTeamScore = match.getLocalScore();
		m_visitorTeamScore = match.getVisitorScore();
	}
		
	public static String generateId(long matchId, long roundIndex) {
		return "" + matchId + "_" + roundIndex;
	}
	
	public RoundResolution getResolution() {
		return m_resolution;
	}

	public void setResolution(RoundResolution resolution) {
		m_resolution = resolution;
	}

	public void setGoal(int stepIndex, boolean forLocalTeam) {
		m_resolution.setGoal(stepIndex, forLocalTeam);
		if (forLocalTeam) {
			m_localeTeamScore++;
		} else {
			m_visitorTeamScore++;			
		}
	}

	public int getLocaleTeamScore() {
		return m_localeTeamScore;
	}

	public int getVisitorTeamScore() {
		return m_visitorTeamScore;
	}

}
