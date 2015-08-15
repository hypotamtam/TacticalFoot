package org.tactical.sports.shared.rule.action.manager;

import org.tactical.sports.shared.domain.playground.Playground;

public abstract class AbstractActionManager implements ActionManager{
	
	private Playground m_playground;
	
	protected Playground getPlayground() {
		return m_playground;
	}

	public void setPlayground(Playground playground) {
		m_playground = playground;
	}
	
}
