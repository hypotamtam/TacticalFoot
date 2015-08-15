package org.tactical.sports.server.utils;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import junit.framework.TestCase;

public abstract class GAETestCase extends TestCase {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    public void setUp() {
        helper.setUp();
    }

    public void tearDown() {
        helper.tearDown();
    }

}
