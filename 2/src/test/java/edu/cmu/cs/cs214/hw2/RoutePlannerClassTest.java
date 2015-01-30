package edu.cmu.cs.cs214.hw2;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for RoutePlannerClass
 * 
 * @author zhaoru
 *
 */
public class RoutePlannerClassTest {
	private RoutePlanner routePlannerClassTest;
	private RoutePlanner routePlannerClassTestHard;
	private String filename = "src/main/resources/sixtyones_stop_times.txt";
	// If the file is changed, here should also be changed.
	private RoutePlannerBuilderClass routePlannerBuilderClassTest;

	@Before
	public void setUp() throws FileNotFoundException, IOException {
		Graph adjacencyListGraphTest = new AdjacencyListGraph();
		Stop test11 = new Vertex("A1", "B1", 1, 1, -1);
		Stop test1 = new Vertex("A1", "B1", 1, 1, 1);
		Stop test2 = new Vertex("A2", "B2", 2, 2, 2);
		Stop test3 = new Vertex("A3", "B3", 3, 3, 3);
		Stop test4 = new Vertex("A4", "B3", 4, 4, 4);
		adjacencyListGraphTest.addVertex(test11);
		adjacencyListGraphTest.addVertex(test1);
		adjacencyListGraphTest.addVertex(test2);
		adjacencyListGraphTest.addVertex(test3);
		adjacencyListGraphTest.addVertex(test4);
		adjacencyListGraphTest.addEdge(test1, test2);
		adjacencyListGraphTest.addEdge(test2, test3);

		routePlannerClassTest = new RoutePlannerClass(adjacencyListGraphTest,
				1200);

		routePlannerBuilderClassTest = new RoutePlannerBuilderClass(filename,
				1200);
		routePlannerClassTestHard = routePlannerBuilderClassTest.build(
				filename, 1200);
	}

	/**
	 * testFindStopsBySubstring
	 */
	@Test
	public void testFindStopsBySubstring() {
		List<Stop> test5 = routePlannerClassTest.findStopsBySubstring("a");
		List<Stop> test6 = routePlannerClassTest.findStopsBySubstring("B");
		List<Stop> test7 = routePlannerClassTest.findStopsBySubstring("CCCC");
		assertEquals(test5.size(), 0);
		assertEquals(test6.size(), 3);
		assertEquals(test7.size(), 0);
		assertEquals(test6.get(0).getBusName(), "A1");
	}

	/**
	 * testComputeRoute
	 */
	@Test
	public void testComputeRoute() {
		Stop test8 = new Vertex("A1", "B1", 1, 1, 1);
		Stop test9 = new Vertex("A2", "B2", 2, 2, 2);
		Stop test10 = new Vertex("A3", "B3", 3, 3, 3);
		Itinerary itineraryTest = routePlannerClassTest.computeRoute(test8,
				test10, 0);
		assertNotNull(itineraryTest);
		assertEquals(itineraryTest.getStartTime(), 1);
		assertEquals(itineraryTest.getWaitTime(), 3);
		assertEquals(itineraryTest.getEndLocation().getName(), "B3");
		assertEquals(itineraryTest.findChangeStop().size(), 2);
		assertNotNull(itineraryTest.getInstructions());

		Stop testHard1 = routePlannerClassTestHard.findStopsBySubstring(
				"5TH AVE AT WOOD ST").get(0); // make sure there is a stop of
												// this name
		Stop testHard2 = routePlannerClassTestHard.findStopsBySubstring(
				"BRADDOCK TERMINAL - NO STOP").get(0); // make sure there is a
														// stop of this name
		Itinerary itineraryTestHard = routePlannerClassTestHard.computeRoute(
				testHard1, testHard2, 0);
		assertNotNull(itineraryTestHard.getInstructions());
	}
}
