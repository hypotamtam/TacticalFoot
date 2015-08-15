package org.tactical.sports.shared.rule.solver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tactical.sports.shared.domain.playground.tile.TileIndex;

public class RoundResolution implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private List<PlayersRoundStep> m_playersStep;
	
	private Map<Integer, Movement> m_ballMovement;
	
	private int m_roundIndex;
	
	private int m_roundSetpCount;
	
	private int m_goalStepIndex = -1;

	private boolean m_doesAlocalTeamGoal = false;
	
	
	public RoundResolution() {
		m_playersStep = new ArrayList<PlayersRoundStep>();
		m_ballMovement = new HashMap<Integer, Movement>();
	}
	
	public void addPlayersStep(PlayersRoundStep step) {
		m_playersStep.add(step);
	}
	
	public void addBallMouvement(int stepIndex, Movement ballMovement) {
		m_ballMovement.put(stepIndex, ballMovement);
	}
	
	public void setPlayerWithBall(Long playerId, TileIndex index, int stepIndex) {
		if (m_playersStep.size() > stepIndex) {
			m_playersStep.get(stepIndex).setPlayerWithBall(playerId, index);
		}
	}
	
	public PlayersRoundStep getPlayersStep(int index) {
		if (m_playersStep.size() > index) {
			return m_playersStep.get(index);
		}
		return null;
	}
	
	public Movement getBallindexForStep(int index) {
		return m_ballMovement.get(index);
	}

	public void setRoundSetpCount(int roundSetpCount) {
		m_roundSetpCount = roundSetpCount;
	}

	public int getRoundSetpCount() {
		return m_roundSetpCount;
	}

	public void setRoundIndex(int roundIndex) {
		m_roundIndex = roundIndex;
	}

	public int getRoundIndex() {
		return m_roundIndex;
	}
	
	public boolean hasGoal() {
		return m_goalStepIndex != -1;
	}
	
	public boolean hasGoal(int stepIndex) {
		return m_goalStepIndex == stepIndex;
	}
	
	public int getGoalStepIndex() {
		return m_goalStepIndex;
	}

	public boolean doesALocalTeamGoal() {
		return m_doesAlocalTeamGoal;
	}
	
	public void setGoal(int stepIndex, boolean forLocalTeam) {
		m_doesAlocalTeamGoal = forLocalTeam;
		m_goalStepIndex = stepIndex;
	}
}
