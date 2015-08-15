package org.tactical.sports.shared.math;

public class Vector {

	private Point m_p1;
	private Point m_p2;
	
	private Point m_u;
	
	public Vector(Point p1, Point p2) {
		m_p1 = p1;
		m_p2 = p2;
		m_u = new Point(m_p2.getX() - m_p1.getX(), m_p2.getY() - m_p1.getY());
	}
	
	public int getSide(Point p) {
		int dx = p.getX() - m_p1.getX();
		int dy = p.getY() - m_p1.getY();
		return  dy * m_u.getX() - dx * m_u.getY();
	}
}
