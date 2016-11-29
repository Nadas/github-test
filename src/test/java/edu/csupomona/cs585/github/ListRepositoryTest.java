package edu.csupomona.cs585.github;

import java.io.IOException;

import org.junit.Test;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class ListRepositoryTest {

	@Mock
	GitHub mockGitHub;

	@Mock
	GHRepository mockRepository;


	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetRepo() {

	}

	@Test
	public void testCompileRepo() {
		//ListRepository.compileRepo();
	}

/*    @Test
    public void testOffline() throws Exception {
        GitHub hub = GitHub.offline();
      //  assertEquals("https://api.github.invalid/test", hub.getApiURL("/test").toString());
        assertTrue(hub.isAnonymous());
        try {
            hub.getRateLimit();
            fail("Offline instance should always fail");
        } catch (IOException e) {
            assertEquals("Offline", e.getMessage());
        }
    }*/
}
