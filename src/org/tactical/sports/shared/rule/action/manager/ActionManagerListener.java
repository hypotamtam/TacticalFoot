package org.tactical.sports.shared.rule.action.manager;

import org.tactical.sports.shared.rule.action.Action;


public interface ActionManagerListener {
	void actionDefined(Action action);
	void actionCancel(Action action);
	void stop();
}
