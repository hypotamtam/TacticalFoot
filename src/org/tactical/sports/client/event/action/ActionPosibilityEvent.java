package org.tactical.sports.client.event.action;

import java.util.Set;

import org.tactical.sports.shared.domain.playground.tile.Tile;
import org.tactical.sports.shared.rule.action.ActionType;

import com.google.gwt.event.shared.GwtEvent;

public class ActionPosibilityEvent extends GwtEvent<ActionPosibilityEventHandler> {

	public static Type<ActionPosibilityEventHandler> TYPE = new Type<ActionPosibilityEventHandler>();
	
	private Set<Tile> m_affectedTiles;
	private ActionType m_type;
	private ActionPosibilityState m_state;

	public Set<Tile> getAffectedTiles() {
		return m_affectedTiles;
	}

	public void setAffectedTiles(Set<Tile> tiles) {
		m_affectedTiles = tiles;
	}

	public ActionType getType() {
		return m_type;
	}

	public void setType(ActionType action) {
		m_type = action;
	}
	
	public ActionPosibilityState getState() {
		return m_state;
	}

	public void setState(ActionPosibilityState state) {
		m_state = state;
	}
	
	@Override
	public Type<ActionPosibilityEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ActionPosibilityEventHandler handler) {
		handler.onActionPosibilityChanged(this);
	}

}
