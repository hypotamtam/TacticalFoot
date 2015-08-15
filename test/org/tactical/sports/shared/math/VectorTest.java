package org.tactical.sports.shared.math;

import junit.framework.TestCase;

public class VectorTest extends TestCase {
	
	public void testDistToVector() {
		Point p1 = new Point(0, 0);
		Point p2 = new Point(0, 2);
		Vector v = new Vector(p1, p2);
		Vector invV = new Vector(p2, p1);
		
		assertEquals(0, v.getSide(p1));
		assertEquals(0, v.getSide(p2));
		assertEquals(0, invV.getSide(p1));
		assertEquals(0, invV.getSide(p2));
		
		Point p = new Point(1, 1);
		assertTrue(v.getSide(p) < 0);
		assertTrue(invV.getSide(p) > 0);
	}

}
