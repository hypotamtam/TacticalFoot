package org.tactical.sports.shared.actions;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.tactical.sports.shared.domain.PlayerDetails;
import org.tactical.sports.shared.tiles.FakeTile;
import org.tactical.sports.shared.tiles.Tile;

public class ActionTest  extends TestCase{

	public void testAddTileAddesTileToTheListEnd() {
		Action action = new Action(ActionType.NONE, new PlayerDetails());
		assertEquals(0, action.getArea().size());
		
		Tile tile = new FakeTile(0, 0);
		action.addTile(tile);
		assertEquals(tile, action.getArea().get(action.getArea().size() - 1));
		assertEquals(1, action.getArea().size());
		
		List<Tile> tiles = new ArrayList<Tile>();
		tiles.add(new FakeTile(0, 0));
		tiles.add(new FakeTile(1, 0));
		tiles.add(new FakeTile(0, 1));
		tiles.add(new FakeTile(10, 10));
		action.addTiles(tiles);
		assertEquals(1 + tiles.size(), action.getArea().size());
		for (int i = 0; i < tiles.size(); i++) {
			assertEquals(tiles.get(i), action.getArea().get(action.getArea().size() - tiles.size() + i));
		}
		
		
	}
	
}
