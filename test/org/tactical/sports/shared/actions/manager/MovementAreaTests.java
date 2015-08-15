package org.tactical.sports.shared.actions.manager;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.tactical.sports.shared.actions.manager.MovementArea;
import org.tactical.sports.shared.domain.PlayerDetails;
import org.tactical.sports.shared.tiles.FakeTile;
import org.tactical.sports.shared.tiles.Tile;
import org.tactical.sports.shared.tiles.TileSearcher;

public class MovementAreaTests extends TestCase {

	public void testIsMovementAllowedUsesTileSearcherResult() {
		Tile fakeTile = new FakeTile(2, 2);
		fakeTile.setPlayer(new PlayerDetails());
		MovementArea action = new MovementArea(fakeTile, 100);
		TileSearcher searcher = mock(TileSearcher.class);
		action.m_searcher = searcher;
		
		Map<Tile, Integer> fakeResults = new HashMap<Tile, Integer>();
		FakeTile fakeValidPosition = new FakeTile(3, 4);
		FakeTile fakeInvalidPosition = new FakeTile(6, 6);
		fakeResults.put(fakeValidPosition, 3);
		when(searcher.getMarkedItems()).thenReturn(fakeResults);

		assertTrue(action.isMovementAllowed(fakeValidPosition));
		assertFalse(action.isMovementAllowed(fakeInvalidPosition));
		verify(searcher, times(2)).getMarkedItems();
	}
	
	public void testGetAllowedMovementsUsesTileSearcherResult() {
		Tile fakeTile = new FakeTile(2, 2);
		fakeTile.setPlayer(new PlayerDetails());
		MovementArea action = new MovementArea(fakeTile, 100);
		TileSearcher searcher = mock(TileSearcher.class);
		action.m_searcher = searcher;
		
		Map<Tile, Integer> fakeResults = new HashMap<Tile, Integer>();
		FakeTile fakeValidPosition = new FakeTile(3, 4);
		fakeResults.put(fakeValidPosition, 3);
		when(searcher.getMarkedItems()).thenReturn(fakeResults);
		
		Map<Tile, Integer> results = action.getAllowedMovements();
		assertEquals(fakeResults, results);		
		verify(searcher).getMarkedItems();
	}
}
