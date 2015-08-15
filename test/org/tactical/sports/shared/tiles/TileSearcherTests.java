package org.tactical.sports.shared.tiles;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;

import org.tactical.sports.shared.tiles.Tile;
import org.tactical.sports.shared.tiles.TileGraph;
import org.tactical.sports.shared.tiles.TileSearcher;

import junit.framework.TestCase;

public class TileSearcherTests extends TestCase {
	private TileGraph m_graph;
	
	@Override
	protected void setUp() throws Exception {
		 m_graph = new TileGraph(11, 11, (byte)0);
		 for (int i = 0; i < 11; i++) {
			 for (int j = 0; j < 11; j++) {
				m_graph.addTile(i, j, TileType.normal);
			}
		 }
	}

	@Override
	protected void tearDown() throws Exception {
		m_graph = null;
	}

	public void testBuildFloodingGraphRespectsTheMaxDepth() {
		Tile from = m_graph.getTile(5, 5);
		TileSearcher searcher = new TileSearcher(from, 3);		
		Map<Tile, Integer> markers = searcher.getMarkedItems();
		
		for (Integer depth : markers.values()) {
			assertTrue(depth <= 3);
		}
	}
	
	public void testBuildFloodingGraphReturnsValidDepth() {
		Tile from = m_graph.getTile(5, 5);
		TileSearcher searcher = new TileSearcher(from, 3);		
		Map<Tile, Integer> markers = searcher.getMarkedItems();
		
		assertEquals(0, (int)markers.get(m_graph.getTile(5, 5)));
		assertEquals(1, (int)markers.get(m_graph.getTile(4, 4)));
		assertEquals(1, (int)markers.get(m_graph.getTile(5, 4)));
		assertEquals(2, (int)markers.get(m_graph.getTile(5, 3)));
		assertEquals(2, (int)markers.get(m_graph.getTile(4, 6)));
		assertEquals(3, (int)markers.get(m_graph.getTile(3, 3)));
		assertEquals(3, (int)markers.get(m_graph.getTile(8, 3)));
	}
	
	public void testBuildFloodingGraphReturnsSurroundingPositions() {
		Tile from = m_graph.getTile(5, 5);
		TileSearcher searcher = new TileSearcher(from, 1);		
		Map<Tile, Integer> markers = searcher.getMarkedItems();
		
		assertEquals(7, markers.size());
		assertNotNull(markers.get(m_graph.getTile(5, 4)));
		assertNotNull(markers.get(m_graph.getTile(5, 6)));
		assertNotNull(markers.get(m_graph.getTile(4, 4)));
		assertNotNull(markers.get(m_graph.getTile(4, 5)));
		assertNotNull(markers.get(m_graph.getTile(6, 4)));
		assertNotNull(markers.get(m_graph.getTile(6, 5)));
	}
	
	public void testGetPathToFailsWithPositionsBelowDepth() {
		Tile from = m_graph.getTile(5, 5);
		TileSearcher searcher = new TileSearcher(from, 3);
		
		Tile to = m_graph.getTile(9, 5);
		assertNull(searcher.getPathTo(to));
	}
	
	public void testGetPathReturnsAPathToDestination() {
		Tile from = m_graph.getTile(5, 5);
		TileSearcher searcher = new TileSearcher(from, 3);
		
		Tile to = m_graph.getTile(8, 5);
		List<Tile> path = searcher.getPathTo(to);
		
		assertNotNull(path);
		assertEquals(3, path.size());
		
		assertTrue(from.getNeighbours().contains(path.get(0)));
		assertTrue(path.get(0).getNeighbours().contains(path.get(1)));
		assertTrue(path.get(1).getNeighbours().contains(to));
		assertEquals(to, path.get(2));
	}
	
	public void testBuildFloodingGraphUsesProvidedTheTileSelector() {
		Tile from = m_graph.getTile(5, 5);
		TileSelector selector = mock(TileSelector.class);
		when(selector.accept(any(Tile.class))).thenReturn(true);
		when(selector.accept(m_graph.getTile(5, 6))).thenReturn(false);
		TileSearcher searcher = new TileSearcher(from, 1, selector);
		Map<Tile, Integer> markers = searcher.getMarkedItems();
		
		assertNotNull(markers.get(m_graph.getTile(5, 4)));
		assertNull(markers.get(m_graph.getTile(5, 6))); // removed by the selector
		assertNotNull(markers.get(m_graph.getTile(4, 4)));
		assertNotNull(markers.get(m_graph.getTile(4, 5)));
		assertNotNull(markers.get(m_graph.getTile(6, 4)));
		assertNotNull(markers.get(m_graph.getTile(6, 5)));
	}
}
