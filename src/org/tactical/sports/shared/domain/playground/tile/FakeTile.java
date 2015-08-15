package org.tactical.sports.shared.domain.playground.tile;

/**
 * For testing purpose only.
 * 
 * @author Fred
 */
public class FakeTile extends Tile {

	private static final long serialVersionUID = 1L;

	public FakeTile() {
		super();
	}
	
	public FakeTile(int x, int y) {
		super(x, y, TileType.normal);
	}
}
