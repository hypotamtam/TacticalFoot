package org.tactical.sports.shared.actions.manager;


import java.util.List;

import junit.framework.TestCase;

import org.mockito.Mockito;
import org.tactical.sports.shared.Constants;
import org.tactical.sports.shared.playground.FootPlayground;
import org.tactical.sports.shared.playground.Playground;
import org.tactical.sports.shared.tiles.Tile;

public class MouvementAvtionManagerTest extends TestCase {

	private MouvementActionManager m_mam;
	
	private Tile m_currentTile;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		int maxMouvement = Constants.MAX_MOVEMENT_SIZE;
		Playground playground =  new FootPlayground(maxMouvement * 3 , maxMouvement * 3);
		m_mam = new MouvementActionManager();
		m_currentTile = playground.getTile((int) (maxMouvement * 1.5) , (int) (maxMouvement * 1.5));
		m_mam.init(m_currentTile);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		m_mam = null;
		m_currentTile = null;
	}
	
	public void testInitGiveTheRightMouvementArea() {
		int expectedMouvementSize = getExpectedMouvement(Constants.MAX_MOVEMENT_SIZE);
		assertEquals(expectedMouvementSize, m_mam.getActionPosibility().size());
	}

	private int getExpectedMouvement(int mouvementSize) {
		int expectedMouvementSize = 1;
		for (int i = 0; i <= mouvementSize; i++) {
			expectedMouvementSize += 6 * i;
		}
		return expectedMouvementSize;
	}
	
	public void testUpdateReducesTheActionArea() {
		int mouvementSize = Constants.MAX_MOVEMENT_SIZE - 1;
		while (mouvementSize > 0) {
			m_currentTile = (Tile) m_currentTile.getNeighbours().toArray()[0];
			m_mam.update(m_currentTile);
			int expectedMouvementSize = getExpectedMouvement(mouvementSize);
			assertEquals(expectedMouvementSize, m_mam.getActionPosibility().size());
			mouvementSize--;
		}
	}
	
	public void testCancelNeverCallsActionDefinedOnTheListener() {
		ActionManagerListener listener = Mockito.mock(ActionManagerListener.class);
		m_mam.setListener(listener);
		m_mam.cancel();
		Mockito.verify(listener, Mockito.never()).actionDefined(null);
	}
	
	public void testWhenMouvementAreUpdatedActionDefinedIsCalled() {
		ActionManagerListener listener = Mockito.mock(ActionManagerListener.class);
		m_mam.setListener(listener);
		while (m_mam.getActionPosibility() != null) {
			m_currentTile = (Tile) m_currentTile.getNeighbours().toArray()[0];
			m_mam.update(m_currentTile);
		}
		Mockito.verify(listener, Mockito.times(Constants.MAX_MOVEMENT_SIZE)).actionDefined(Mockito.any(List.class));
	}
}
 