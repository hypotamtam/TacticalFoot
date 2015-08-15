package org.tactical.sports.server.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.tactical.sports.shared.domain.Match;
import org.tactical.sports.shared.rule.action.Action;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Serialized;

@Entity
public class ActionToSolved {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long m_id;
	
	@Parent
	private Key<Match> m_matchKey;
	
	@Serialized
	private Collection<Action> m_localActions;
	@Serialized
	private Collection<Action> m_visitorActions;
	
	private boolean m_isLocalActionSet = false;
	
	private boolean m_isVisitorActionSet = false;
	
	private Date m_creationDate;
	
	private int m_roundIndex;
	
	private ActionToSolved() {
		Date date = new Date();
		m_creationDate = date;
	}
	
	public ActionToSolved(Match match) {
		this();
		m_matchKey = new Key<Match>(Match.class, match.getId());
	}
	
	public void setAction(Collection<Action> actions, boolean localActions) {
		if (localActions) {
			m_localActions = actions;
			m_isLocalActionSet = true;
		} else {
			m_visitorActions = actions;
			m_isVisitorActionSet = true;
		}
	}
	
	public boolean canBeSolved() {
		return m_isLocalActionSet && m_isVisitorActionSet;
	}

	public int getRoundIndex() {
		return m_roundIndex;
	}

	public void setRoundIndex(int roundIndex) {
		m_roundIndex = roundIndex;
	}
	
	public Collection<Action> getActions() {
		Collection<Action> ret = new ArrayList<Action>();
		if (m_localActions != null) {
			ret.addAll(m_localActions);
		} 
		if (m_visitorActions != null) {
			ret.addAll(m_visitorActions);
		}
		return ret;
	}

	public Long getId() {
		return m_id;
	}

	public long getRandomSeed() {
		return m_creationDate.getTime();
	}
}
