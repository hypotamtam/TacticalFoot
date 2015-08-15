package org.tactical.sports.client.event.action;

import org.tactical.sports.shared.rule.action.Action;

import com.google.gwt.event.shared.GwtEvent;

public class ActionEvent extends GwtEvent<ActionEventHandler> {

	public static Type<ActionEventHandler> TYPE = new Type<ActionEventHandler>();
	
	private Action m_action;
	
	private boolean m_isCanceled;
	
	public ActionEvent(Action action, boolean isCanceled) {
		m_action = action;
		m_isCanceled = isCanceled;
	}
	
	public Action getAction() {
		return m_action;
	}
	
	@Override
	public Type<ActionEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ActionEventHandler handler) {
		if (m_isCanceled) {
			handler.onActionCancel(this);
		} else {
			handler.onActionSet(this);
		}
	}

}
