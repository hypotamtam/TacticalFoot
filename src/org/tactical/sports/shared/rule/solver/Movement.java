package org.tactical.sports.shared.rule.solver;

import java.io.Serializable;

import org.tactical.sports.shared.domain.playground.tile.TileIndex;

public class Movement implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private TileIndex m_start;
	private TileIndex m_end;
	
	public Movement() {
		
	}
	
	public Movement(TileIndex start, TileIndex end) {
		m_start = start;
		m_end = end;
	}

	public TileIndex getStart() {
		return m_start;
	}

	public TileIndex getEnd() {
		return m_end;
	}
}