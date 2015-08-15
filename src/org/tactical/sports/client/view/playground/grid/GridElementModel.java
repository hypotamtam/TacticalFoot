package org.tactical.sports.client.view.playground.grid;

import java.util.ArrayList;
import java.util.List;

import org.tactical.sports.shared.math.Point;
import org.tactical.sports.shared.math.Vector;

import com.google.gwt.resources.client.ImageResource;

public class GridElementModel {
	private ImageResource m_resource;
	private int m_width;
	private int m_height;
	private List<Vector> m_vectors;

	GridElementModel(ImageResource resource) {
		m_resource = resource;
		m_width = resource.getWidth();
		m_height = resource.getHeight();
		
		int halfHeight = m_height / 2;
		int quaterWidth = m_width / 4;
		int threeQuaterWidth = 3 * quaterWidth ;
		Point p1 = new Point(0, halfHeight);
		Point p2 = new Point(quaterWidth, 0);
		Point p3 = new Point(threeQuaterWidth, 0);
		Point p4 = new Point(m_width, halfHeight);
		Point p5 = new Point(threeQuaterWidth, m_height);
		Point p6 = new Point(quaterWidth, m_height);
		
		m_vectors = new ArrayList<Vector>();
		m_vectors.add(new Vector(p1, p2));
		m_vectors.add(new Vector(p2, p3));
		m_vectors.add(new Vector(p3, p4));
		m_vectors.add(new Vector(p4, p5));
		m_vectors.add(new Vector(p5, p6));
		m_vectors.add(new Vector(p6, p1));
	}
	
	public int getWidth() {
		return m_width;
	}
	
	public int getHeight() {
		return m_height;
	}
	
	public ImageResource getResource() {
		return m_resource;
	}

	public boolean isInWorkingArea(int x, int y) {
		final Point p = new Point();
		p.setX(x);
		p.setY(y);
		
		for (Vector v : m_vectors) {
			if (v.getSide(p) < 0) {
				return false;
			}
		}
		return true;
	}
}
