package org.tactical.sports.server.daos;

import org.tactical.sports.server.utils.GAETestCase;
import org.tactical.sports.server.domain.Player;

public class PlayerDaoTests extends GAETestCase {
    
	public void testSaveAndGet() {
		PlayerDao playerDao = new PlayerDao();
		Player player = new Player();
		player.setName("serge");
		
		playerDao.save(player);
		assertNotNull(player.getId());
		
		Player retreived = playerDao.get(player.getId());
		assertNotNull(retreived);
		assertEquals(player.getName(), retreived.getName());
	}
}
