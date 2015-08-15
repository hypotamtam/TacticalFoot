package org.tactical.sports.shared.math;

public class Point {

	private int m_x;
	private int m_y;
	
	public Point(int x, int y) {
		m_x = x;
		m_y = y;
	}

	public Point() {
		this(0,0);
	}

	public int getX() {
		return m_x;
	}

	public int getY() {
		return m_y;
	}

	public void setX(int x) {
		m_x = x;
	}

	public void setY(int y) {
		m_y = y;
	}
	
	
}
