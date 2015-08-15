package org.tactical.sports.server.fixtures;

import org.tactical.sports.shared.domain.PlayerDetails;

public class PlayerDetailsFactory {

	public static PlayerDetails createMaradona(Long id) {
		PlayerDetails player = new PlayerDetails();
		player.setId(id);
		player.setName("Maradona");
		
		return player;
	}
}
