package org.tactical.sports.shared.domain;

import java.io.Serializable;

public class MatchResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean m_isADraw;
	
	private long m_winnerTeamId;
	
	private int m_betWon;
	private int m_betLost;
	
	public MatchResult() {
	}
	
	public MatchResult(long winnerTeamId, int betWon, int betLost, boolean isADraw) {
		m_betWon = betWon;
		m_betLost = betLost;
		m_isADraw = isADraw;
		m_winnerTeamId = winnerTeamId;
	}
	
	public boolean isADraw() {
		return m_isADraw;
	}

	public long getWinnerTeamId() {
		return m_winnerTeamId;
	}

	public int getBetWon() {
		return m_betWon;
	}

	public int getBetLost() {
		return m_betLost;
	}

	
}
