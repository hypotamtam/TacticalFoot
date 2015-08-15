package org.tactical.sports.shared.tiles;

import java.util.Set;

import junit.framework.TestCase;

import org.tactical.sports.shared.domain.PlayerDetails;

public class TileTests extends TestCase {
	
	public void testEquals() {
		Tile pos = new Tile(4, 5, TileType.normal);
		
		assertTrue(pos.equals(new Tile(4, 5, TileType.normal)));
		assertFalse(pos.equals(new Tile(5, 4, TileType.normal)));
	}
	
	public void testGetNeighboursReturnAllValidNeighbours() {
		Tile pos = new Tile(4, 5, TileType.normal);
		Tile neighbour1 = new Tile(5, 5, TileType.normal);
		Tile neighbour2 = new Tile(4, 4, TileType.normal);
		pos.addNeighbour(neighbour1);
		pos.addNeighbour(neighbour2);
		Set<Tile> neighbours = pos.getNeighbours();
		
		assertEquals(2, neighbours.size());
		assertTrue(neighbours.contains(neighbour1));
		assertTrue(neighbours.contains(neighbour2));
	}
	
	public void testSetPlayerShouldThrowExceptionIfThereIsAlreadyAPlayer() {
		Tile pos = new Tile(4, 5, TileType.normal);
		pos.setPlayer(new PlayerDetails());
		try {
			pos.setPlayer(new PlayerDetails());
			fail();
		} catch (IllegalArgumentException e) {
			// ok
		}
	}

	public void testSetPlayerShouldNotThrowExceptionIfSettingNullWhenThereIsAPlayer() {
		Tile pos = new Tile(4, 5, TileType.normal);
		pos.setPlayer(new PlayerDetails());
		pos.setPlayer(null); // should not crash
	}
}
