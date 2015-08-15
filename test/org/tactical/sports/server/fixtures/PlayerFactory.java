package org.tactical.sports.server.fixtures;

import org.tactical.sports.server.daos.PlayerDao;
import org.tactical.sports.server.domain.Player;

public class PlayerFactory {

	public static Player createRonaldo() {
		Player player = new Player();
		player.setName("Ronaldo");
		
		return player;
	}
	
	public static Player createAndSaveRonaldo() {
		PlayerDao playerDao = new PlayerDao();
		Player player = createRonaldo();
		playerDao.save(player);
		
		return player;
	}
}
