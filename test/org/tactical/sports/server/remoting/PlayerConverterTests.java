package org.tactical.sports.server.remoting;

import org.tactical.sports.server.daos.PlayerDao;
import org.tactical.sports.server.domain.Player;
import org.tactical.sports.server.fixtures.PlayerDetailsFactory;
import org.tactical.sports.server.fixtures.PlayerFactory;
import org.tactical.sports.server.utils.GAETestCase;
import org.tactical.sports.shared.domain.PlayerDetails;

public class PlayerConverterTests extends GAETestCase {
	private PlayerConverter converter = new PlayerConverter();

	public void testToPlayerDetails() {
		Player player = PlayerFactory.createAndSaveRonaldo();
		
		PlayerDetails details = converter.toPlayerDetails(player);
		assertEquals(player.getId(), details.getId());
		assertEquals(player.getName(), details.getName());
	}
	
	public void testFromPlayerDetailsForNewPlayer() {
		PlayerDetails details = PlayerDetailsFactory.createMaradona(null);
		
		PlayerDao playerDao = new PlayerDao();
		Player player = converter.fromPlayerDetails(details, playerDao);
		
		assertNull(player.getId());
		assertEquals(details.getName(), player.getName());
	}

	public void testFromPlayerDetailsForExistingPlayer() {
		Player player = PlayerFactory.createAndSaveRonaldo();		
		PlayerDetails details = PlayerDetailsFactory.createMaradona(player.getId());
		
		PlayerDao playerDao = new PlayerDao();
		player = converter.fromPlayerDetails(details, playerDao);
		
		assertNotNull(player.getId());
		assertEquals(details.getId(), player.getId());
		assertEquals(details.getName(), player.getName());
	}
}
