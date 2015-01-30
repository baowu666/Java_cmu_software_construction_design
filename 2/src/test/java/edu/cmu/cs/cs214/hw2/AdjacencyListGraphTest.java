package edu.cmu.cs.cs214.hw2;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for AdjacencyListGraph
 * 
 * @author zhaoru
 *
 */
public class AdjacencyListGraphTest {
	private Graph adjacencyListGraphTest;

	@Before
	public void setUp() {
		adjacencyListGraphTest = new AdjacencyListGraph();
		Stop test1 = new Vertex("A1", "B1", 1, 1, 1);
		Stop test2 = new Vertex("A2", "B2", 2, 2, 2);
		Stop test3 = new Vertex("A3", "B3", 3, 3, 3);
		adjacencyListGraphTest.addVertex(test1);
		adjacencyListGraphTest.addVertex(test2);
		adjacencyListGraphTest.addVertex(test3);
		adjacencyListGraphTest.addEdge(test1, test2);
		adjacencyListGraphTest.addEdge(test2, test3);
	}

	/**
	 * testGetVertexList
	 */
	@Test
	public void testGetVertexList() {
		List<Stop> stop = adjacencyListGraphTest.getVertexList();
		assertNotNull(stop.get(0));
		assertNotNull(stop.get(1));
		assertNotNull(stop.get(2));
	}

	/**
	 * testGetSingleVertex
	 */
	@Test
	public void testGetSingleVertex() {
		assertNotNull(adjacencyListGraphTest.getSingleVertex(0));
		assertEquals(adjacencyListGraphTest.getSingleVertex(0).getBusName(),
				"A1");
	}

	/**
	 * testAddVertex
	 */
	@Test
	public void testAddVertex() {
		Stop test4 = new Vertex("A4", "B4", 4, 4, 4);
		Stop test5 = test4;
		adjacencyListGraphTest.addVertex(test4);
		adjacencyListGraphTest.addVertex(test5);
		assertEquals(adjacencyListGraphTest.getVertexList().size(), 4);
	}

	/**
	 * testAddEdge
	 */
	@Test
	public void testAddEdge() {
		Stop test5 = new Vertex("A5", "B5", 5, 5, 5);
		Stop test6 = new Vertex("A6", "B6", 6, 6, 6);
		adjacencyListGraphTest.addVertex(test5);
		adjacencyListGraphTest.addVertex(test6);
		adjacencyListGraphTest.addEdge(test5, test6);
		assertTrue(adjacencyListGraphTest.getNextStop(test5).contains(test6));
	}

	/**
	 * TestGetNextStop
	 */
	@Test
	public void TestGetNextStop() {
		Stop test7 = new Vertex("A7", "B7", 7, 7, 7);
		Stop test8 = new Vertex("A8", "B8", 8, 8, 8);
		adjacencyListGraphTest.addVertex(test7);
		adjacencyListGraphTest.addVertex(test8);
		adjacencyListGraphTest.addEdge(test7, test8);
		assertNotNull(adjacencyListGraphTest.getNextStop(test7));
	}
}
