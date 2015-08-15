package org.tactical.sports.shared.domain.playground.tile;

import java.io.Serializable;

public class TileIndex implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	public int x;
	public int y;

	public TileIndex() {
	}

	public TileIndex(int x2, int y2) {
		x = x2;
		y = y2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TileIndex other = (TileIndex) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
}