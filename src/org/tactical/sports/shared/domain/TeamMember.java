package org.tactical.sports.shared.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class TeamMember implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long m_id;

	
	public TeamMember() {
		
	}
	public Long getId() {
		return m_id;
	}

}
