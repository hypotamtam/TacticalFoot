package org.tactical.sports.client.view.playground.grid;

public class GridPosition {
	private int m_top;
	private int m_left;
	
	public GridPosition(int top, int left) {
		setLeft(left);
		setTop(top);
	}

	public void setTop(int top) {
		m_top = top;
	}

	public int getTop() {
		return m_top;
	}

	public void setLeft(int left) {
		m_left = left;
	}

	public int getLeft() {
		return m_left;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + m_left;
		result = prime * result + m_top;
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
		GridPosition other = (GridPosition) obj;
		if (m_left != other.m_left)
			return false;
		if (m_top != other.m_top)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GridPosition [top=" + m_top + ", left=" + m_left + "]";
	}
}
