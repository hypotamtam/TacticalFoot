package org.tactical.sports.server.daos;

import org.tactical.sports.server.utils.GAETestCase;
import org.tactical.sports.server.domain.Player;
import org.tactical.sports.server.domain.Team;

import com.googlecode.objectify.Key;

public class PlayerTeamRelationshipTests extends GAETestCase {
    
	public void testSaveAndGet() {
		PlayerDao playerDao = new PlayerDao();
		Player player = new Player();
		player.setName("serge");

		TeamDao teamDao = new TeamDao();
		Team team = new Team();
		team.setName("Girondins");
		teamDao.save(team);
		
		player.setTeam(new Key<Team>(Team.class, team.getId()));		
		playerDao.save(player);
		
		Player received = playerDao.get(player.getId());
		assertNotNull(received.getTeam());
	}
}
