package org.tactical.sports.server.rule.solver;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

import org.tactical.sports.shared.domain.playground.Playground;
import org.tactical.sports.shared.rule.action.Action;


public abstract class AbstractRoundActionSolver implements RoundActionSolver {

	private Playground m_playground;
	
	private Collection<Action> m_actions = new HashSet<Action>();
	
	private Random m_random;
	
	@Override
	public void setPlayground(Playground playground) {
		m_playground = playground;
	}

	protected Playground getPlayground() {
		return m_playground;
	}
	
	@Override
	public void addAction(Action action) {
		if (allowAction(action)) {
			m_actions.add(action);
		}
	}

	@Override
	public void setRandom(Random random) {
		m_random = random;
	}
	
	protected abstract boolean allowAction(Action action);
	
	protected Collection<Action> getActions() {
		return m_actions;
	}
	
	protected double getLuck() {
		return m_random.nextDouble();
	}

	protected int getRandomIndex(int upperBound) {
		return m_random.nextInt(upperBound);
	}
	
}
