package org.tactical.sports.server.daos;

import org.tactical.sports.server.domain.Team;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import junit.framework.TestCase;

public class TeamDaoTests extends TestCase {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    public void setUp() {
        helper.setUp();
    }

    public void tearDown() {
        helper.tearDown();
    }
    
	public void testSaveAndGet() {
		TeamDao teamDao = new TeamDao();
		Team team = new Team();
		team.setName("OM");
		
		teamDao.save(team);
		
		Team retreived = teamDao.get(team.getId());
		assertNotNull(retreived);
		assertEquals(team.getName(), retreived.getName());
	}
}
