package edu.cmu.cs.cs214.hw2;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for RoutePlannerBuilderClass
 * 
 * @author zhaoru
 *
 */
public class RoutePlannerBuilderClassTest {
	private RoutePlannerBuilderClass routePlannerBuilderClassTest;
	private String filename = "src/main/resources/sixtyones_stop_times.txt";

	// If the file is changed, here should also be changed.

	@Before
	public void setUp() {
		routePlannerBuilderClassTest = new RoutePlannerBuilderClass(filename,
				1200);
	}

	/**
	 * testBuild
	 * 
	 * @throws FileNotFoundException
	 *             if file not fount
	 * @throws IOException
	 *             if input is wrong
	 */
	@Test
	public void testBuild() throws FileNotFoundException, IOException {
		RoutePlanner routePlannerTest = routePlannerBuilderClassTest.build(
				filename, 1200);
		assertNotNull(routePlannerTest);
	}
}
