package org.tactical.sports.server.rule.solver;

import java.util.Collection;

import org.tactical.sports.shared.domain.playground.tile.Tile;
import org.tactical.sports.shared.rule.action.Action;
import org.tactical.sports.shared.rule.action.ActionType;
import org.tactical.sports.shared.rule.solver.Movement;
import org.tactical.sports.shared.rule.solver.RoundResolution;

public class RoundPassSolver extends AbstractRoundActionSolver {

	private boolean m_hasFinished;
	private boolean m_isPassByPlayer;
	private int m_endPassStepIndex;
	private Tile m_startPassTile;
	private Tile m_endPassTile;

	public RoundPassSolver() {
		m_hasFinished = false;
		m_isPassByPlayer = false;
		m_endPassStepIndex = 0;
	}

	@Override
	public boolean hasFinished() {
		return m_hasFinished;
	}

	@Override
	protected boolean allowAction(Action action) {
		return (action.getType() == ActionType.PASS) && getActions().isEmpty();
	}

	@Override
	public void presolvedRoundActions() {
		m_isPassByPlayer = !getActions().isEmpty();

		if (m_isPassByPlayer) {
			Action ballAction = (Action) getActions().toArray()[0];

			Collection<Tile> passArea = ballAction.getArea();
			passArea.remove(ballAction.getStartTile());
			if (!passArea.isEmpty()) {
				m_startPassTile = ballAction.getStartTile();
				m_startPassTile = getPlayground().getTile(m_startPassTile.getIndex());
				Object[] tiles =  passArea.toArray();
				m_endPassTile = (Tile) tiles[getRandomIndex(tiles.length)];
				m_endPassTile = getPlayground().getTile(m_endPassTile.getIndex());

				double dx = m_endPassTile.getIndex().x - m_startPassTile.getIndex().x;
				double dy = m_endPassTile.getIndex().y - m_startPassTile.getIndex().y;
				double ballMovementLength = Math.sqrt(dx * dx + dy * dy);
				m_endPassStepIndex = Math.max((int) (ballMovementLength / 2.0), 1);
			} else {
				m_hasFinished = true;
				m_isPassByPlayer = false;
			}
		} else {
			m_hasFinished = true;
		}
	}

	@Override
	public void solvedStep(int stepIndex, RoundResolution roundResolution) {
		if (m_isPassByPlayer) {
			if (stepIndex == m_endPassStepIndex) {
				if (m_startPassTile.hasBall()) {
					m_startPassTile.setHasBall(false);
					m_endPassTile.setHasBall(true);
					roundResolution.addBallMouvement(stepIndex, new Movement(m_startPassTile.getIndex(), m_endPassTile.getIndex()));
				}
				m_hasFinished = true;
			}
		}
	}

}
