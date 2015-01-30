package edu.cmu.cs.cs214.hw2;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for AdjacencyMatrixGraph
 * 
 * @author zhaoru
 *
 */
public class AdjacencyMatrixGraphTest {
	private Graph adjacencyMatrixGraphTest;

	@Before
	public void setUp() {
		adjacencyMatrixGraphTest = new AdjacencyMatrixGraph(8);
		Stop test1 = new Vertex("A1", "B1", 1, 1, 1);
		Stop test2 = new Vertex("A2", "B2", 2, 2, 2);
		Stop test3 = new Vertex("A3", "B3", 3, 3, 3);
		adjacencyMatrixGraphTest.addVertex(test1);
		adjacencyMatrixGraphTest.addVertex(test2);
		adjacencyMatrixGraphTest.addVertex(test3);
		adjacencyMatrixGraphTest.addEdge(test1, test2);
		adjacencyMatrixGraphTest.addEdge(test2, test3);
	}

	/**
	 * testGetVertexList
	 */
	@Test
	public void testGetVertexList() {
		List<Stop> stop = adjacencyMatrixGraphTest.getVertexList();
		assertNotNull(stop.get(0));
		assertNotNull(stop.get(1));
		assertNotNull(stop.get(2));
	}

	/**
	 * testGetSingleVertex
	 */
	@Test
	public void testGetSingleVertex() {
		assertNotNull(adjacencyMatrixGraphTest.getSingleVertex(0));
		assertEquals(adjacencyMatrixGraphTest.getSingleVertex(0).getBusName(),
				"A1");
	}

	/**
	 * testAddVertex
	 */
	@Test
	public void testAddVertex() {
		Stop test4 = new Vertex("A4", "B4", 4, 4, 4);
		adjacencyMatrixGraphTest.addVertex(test4);
		assertEquals(adjacencyMatrixGraphTest.getVertexList().size(), 4);
	}

	/**
	 * testAddEdge
	 */
	@Test
	public void testAddEdge() {
		Stop test5 = new Vertex("A5", "B5", 5, 5, 5);
		Stop test6 = new Vertex("A6", "B6", 6, 6, 6);
		adjacencyMatrixGraphTest.addVertex(test5);
		adjacencyMatrixGraphTest.addVertex(test6);
		adjacencyMatrixGraphTest.addEdge(test5, test6);
		assertTrue(adjacencyMatrixGraphTest.getNextStop(test5).contains(test6));
	}

	/**
	 * TestGetNextStop
	 */
	@Test
	public void TestGetNextStop() {
		Stop test7 = new Vertex("A7", "B7", 7, 7, 7);
		Stop test8 = new Vertex("A8", "B8", 8, 8, 8);
		adjacencyMatrixGraphTest.addVertex(test7);
		adjacencyMatrixGraphTest.addVertex(test8);
		adjacencyMatrixGraphTest.addEdge(test7, test8);
		assertNotNull(adjacencyMatrixGraphTest.getNextStop(test7));
	}
}
