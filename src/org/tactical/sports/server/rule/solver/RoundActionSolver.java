package org.tactical.sports.server.rule.solver;

import java.util.Random;

import org.tactical.sports.shared.domain.playground.Playground;
import org.tactical.sports.shared.rule.action.Action;
import org.tactical.sports.shared.rule.solver.RoundResolution;

public interface RoundActionSolver {

	void setPlayground(Playground playground);
	void setRandom(Random random);

	void addAction(Action action);
	
	boolean hasFinished();

	void presolvedRoundActions();
	
	void solvedStep(int stepIndex, RoundResolution roundResolution);
}
