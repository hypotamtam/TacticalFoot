package org.tactical.sports.shared.playground;

import java.util.Set;

import junit.framework.TestCase;

import org.tactical.sports.shared.tiles.Tile;
import org.tactical.sports.shared.tiles.TileType;

public class FootPlaygroundTest extends TestCase {

	private final static int PLAYGROUND_WIDTH = 7;
	private final static int PLAYGROUND_HEIGHT = 11;

	private FootPlayground m_playground;

	@Override
	protected void setUp() throws Exception {
		m_playground = new FootPlayground(PLAYGROUND_WIDTH, PLAYGROUND_HEIGHT);
	}

	@Override
	protected void tearDown() throws Exception {
		m_playground = null;
	}

	public void testWidthMustBeOdd() throws Exception {
		try {
			new FootPlayground(4, 6);
			fail();
		} catch (IllegalArgumentException e) {
			// Success
		}
	}

	public void testHeightMustBeOdd() throws Exception {
		try {
			new FootPlayground(5, 4);
			fail();
		} catch (IllegalArgumentException e) {
			// Success
		}
	}

	public void testGetHeightAndGetWidthReturnsThePlaygroundSize() {
		assertEquals(PLAYGROUND_WIDTH, m_playground.getWidth());
		assertEquals(PLAYGROUND_HEIGHT, m_playground.getHeight());
	}

	public void testIsInPlaygroundChecksBoundaries() {
		assertFalse(m_playground.isInPlayground(0, 0));
		assertFalse(m_playground.isInPlayground(PLAYGROUND_WIDTH - 1, 0));
		assertFalse(m_playground.isInPlayground(PLAYGROUND_WIDTH - 1, PLAYGROUND_HEIGHT - 1 - 1));
		assertFalse(m_playground.isInPlayground(0, PLAYGROUND_HEIGHT - 1 - 1));
		
		assertFalse(m_playground.isInPlayground(-1, 0));
		assertFalse(m_playground.isInPlayground(PLAYGROUND_WIDTH, 0));

		assertTrue(m_playground.isInPlayground(0, PLAYGROUND_HEIGHT / 2));
		assertTrue(m_playground.isInPlayground(0, (PLAYGROUND_HEIGHT / 2) - 1));
		assertTrue(m_playground.isInPlayground(1, 0));
		assertTrue(m_playground.isInPlayground(PLAYGROUND_WIDTH - 1 - 1, 0));
		assertTrue(m_playground.isInPlayground(1, PLAYGROUND_HEIGHT - 1 - 1));
		assertTrue(m_playground.isInPlayground(PLAYGROUND_WIDTH - 1 - 1, PLAYGROUND_HEIGHT - 1 - 1));
		assertTrue(m_playground.isInPlayground(PLAYGROUND_WIDTH - 1, PLAYGROUND_HEIGHT / 2));
		assertTrue(m_playground.isInPlayground(PLAYGROUND_WIDTH - 1, (PLAYGROUND_HEIGHT / 2) - 1));
	}	
	
	public void testIsInAGoal() {
		int halfHeight = PLAYGROUND_HEIGHT / 2;
		assertFalse(m_playground.isInAGoal(PLAYGROUND_WIDTH / 2, halfHeight - 1));
		assertFalse(m_playground.isInAGoal(0, 0));
		
		assertTrue(m_playground.isInAGoal(0, halfHeight - 1));
		assertTrue(m_playground.isInAGoal(0, halfHeight));
		assertTrue(m_playground.isInAGoal(PLAYGROUND_WIDTH - 1, halfHeight - 1));
		assertTrue(m_playground.isInAGoal(PLAYGROUND_WIDTH - 1, halfHeight));
	}
	
	public void testGoalTilesAreInAGoal() {
		Set<Tile> tiles = m_playground.getTiles();
		for (Tile tile : tiles) {
			if (tile.getType() == TileType.goal) {
				m_playground.isInAGoal(tile.getX(), tile.getY());
			}
		}
	}
}
