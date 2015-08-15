package org.tactical.sports.shared.tiles;

import java.util.Set;

import org.tactical.sports.shared.tiles.Tile;
import org.tactical.sports.shared.tiles.TileGraph;

import junit.framework.TestCase;

public class TileGraphTests extends TestCase {
	int width = 13;
	int height = 11;
	byte smallerColumnIndex = 0;
	TileGraph graph;
	
	@Override
	protected void setUp() throws Exception {
		graph = new TileGraph(width, height, smallerColumnIndex);
	}

	@Override
	protected void tearDown() throws Exception {
		graph = null;
	}

	public void testGraphIsEmptyWhenItIsInitialized() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				assertNull(graph.getTile(i, j));
			}
		}
	}
	
	public void testAddPositionCreatesThePositionWithNoNeighboor() {
		assertNull(graph.getTile(1, 1));
		graph.addTile(1, 1, TileType.normal);
		assertNotNull(graph.getTile(1, 1));
		assertEquals(0, graph.getTile(1, 1).getNeighbours().size());
	}
	
	public void testAddPositionLinksThePositionsWithTheOther() {
		Tile p = graph.addTile(2, 1, TileType.normal);
		
		Tile added = graph.addTile(1, 1, TileType.normal);
		assertEquals(1, p.getNeighbours().size());
		assertEquals(1, added.getNeighbours().size());
		assertTrue(added.getNeighbours().contains(p));
		assertTrue(p.getNeighbours().contains(added));
		
		added = graph.addTile(3, 1, TileType.normal);
		assertEquals(2, p.getNeighbours().size());
		assertEquals(1, added.getNeighbours().size());
		assertTrue(added.getNeighbours().contains(p));
		assertTrue(p.getNeighbours().contains(added));
		
		added = graph.addTile(1, 2, TileType.normal);
		assertEquals(3, p.getNeighbours().size());
		assertEquals(2, added.getNeighbours().size());
		assertTrue(added.getNeighbours().contains(p));
		assertTrue(p.getNeighbours().contains(added));
		
		added = graph.addTile(3, 2, TileType.normal);
		assertEquals(4, p.getNeighbours().size());
		assertEquals(2, added.getNeighbours().size());
		assertTrue(added.getNeighbours().contains(p));
		assertTrue(p.getNeighbours().contains(added));
		
		added = graph.addTile(2, 0, TileType.normal);
		assertEquals(5, p.getNeighbours().size());
		assertEquals(3, added.getNeighbours().size());
		assertTrue(added.getNeighbours().contains(p));
		assertTrue(p.getNeighbours().contains(added));
		
		added = graph.addTile(2, 2, TileType.normal);
		assertEquals(6, p.getNeighbours().size());
		assertEquals(3, added.getNeighbours().size());
		assertTrue(added.getNeighbours().contains(p));
		assertTrue(p.getNeighbours().contains(added));
	}
	
	public void testPositionGraphHasAValidTopLeft() {
		graph.addTile(1, 0, TileType.normal);
		graph.addTile(0, 1, TileType.normal);
		graph.addTile(1, 1, TileType.normal);
		Tile upperLeft = graph.addTile(0, 0, TileType.normal);
		Set<Tile> neighbours = upperLeft.getNeighbours();
		assertEquals(3, neighbours.size());
		assertTrue(neighbours.contains(graph.getTile(1, 0)));
		assertTrue(neighbours.contains(graph.getTile(0, 1)));
		assertTrue(neighbours.contains(graph.getTile(1, 1)));
	}
	
	public void testPositionGraphHasAValidBottomRight() {
		graph.addTile(width-2, height-1, TileType.normal);
		graph.addTile(width-1, height-3, TileType.normal);
		graph.addTile(width-2, height-2, TileType.normal);
		Tile bottomRight = graph.addTile(width-1, height-2, TileType.normal);
		Set<Tile> neighbours = bottomRight.getNeighbours();
		assertEquals(3, neighbours.size());
		assertTrue(neighbours.contains(graph.getTile(width-2, height-1)));
		assertTrue(neighbours.contains(graph.getTile(width-1, height-3)));
		assertTrue(neighbours.contains(graph.getTile(width-2, height-2)));
	}
	
	public void testPositionGraphHasAValidCenter() {
		graph.addTile(7, 5, TileType.normal);
		graph.addTile(7, 7, TileType.normal);
		graph.addTile(6, 5, TileType.normal);
		graph.addTile(6, 6, TileType.normal);
		graph.addTile(8, 5, TileType.normal);
		graph.addTile(8, 6, TileType.normal);
		Tile center = graph.addTile(7, 6, TileType.normal);
		Set<Tile> neighbours = center.getNeighbours();
		
		assertEquals(6, neighbours.size());
		assertTrue(neighbours.contains(graph.getTile(7, 5)));
		assertTrue(neighbours.contains(graph.getTile(7, 7)));
		assertTrue(neighbours.contains(graph.getTile(6, 5)));
		assertTrue(neighbours.contains(graph.getTile(6, 6)));
		assertTrue(neighbours.contains(graph.getTile(8, 5)));
		assertTrue(neighbours.contains(graph.getTile(8, 6)));
	}
}
