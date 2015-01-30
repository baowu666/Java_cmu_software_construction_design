package edu.cmu.cs.cs214.hw2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for Vertex
 * 
 * @author zhaoru
 *
 */
public class VertexTest {
	private Vertex vertexTest; // To test vertex class construction, so not use
								// Stop type here

	@Before
	public void setUp() {
		vertexTest = new Vertex("A1", "B1", 1, 2, 3);
	}

	/**
	 * testGetName
	 */
	@Test
	public void testGetName() {
		Vertex vertexTest1 = new Vertex("A1", "B2", 1, 2, 3);
		assertEquals(vertexTest1.getName(), "B2");
	}

	/**
	 * testGetLatitude
	 */
	@Test
	public void testGetLatitude() {
		assertEquals((int) vertexTest.getLatitude(), 1);
	}

	/**
	 * testGetLongitude
	 */
	@Test
	public void testGetLongitude() {
		assertEquals((int) vertexTest.getLongitude(), 2);
	}

	/**
	 * testGetBusName
	 */
	@Test
	public void testGetBusName() {
		assertEquals(vertexTest.getBusName(), "A1");
	}

	/**
	 * testGetTime
	 */
	@Test
	public void testGetTime() {
		assertEquals(vertexTest.getTime(), 3);
	}

	/**
	 * testSetPrevious
	 */
	@Test
	public void testSetPrevious() {
		Vertex test2 = new Vertex("A2", "B2", 5, 5, 5);
		assertNull(vertexTest.getPrevious());
		vertexTest.setPrevious(test2);
		assertNotNull(vertexTest.getPrevious());
	}

	/**
	 * testGetPrevious
	 */
	@Test
	public void testGetPrevious() {
		Vertex vertex = new Vertex("A4", "B4", 7, 7, 7);
		assertNull(vertex.getPrevious());
		Vertex test3 = new Vertex("A3", "B3", 6, 6, 6);
		vertex.setPrevious(test3);
		assertNotNull(vertex.getPrevious());
	}

	/**
	 * testSetMinDist
	 */
	@Test
	public void testSetMinDist() {
		assertEquals(vertexTest.getMinDist(), Integer.MAX_VALUE);
		vertexTest.setMinDist(1);
		assertEquals(vertexTest.getMinDist(), 1);
	}

	/**
	 * testGetMinDist
	 */
	@Test
	public void testGetMinDist() {
		Vertex vertex = new Vertex("A5", "B5", 8, 8, 8);
		assertEquals(vertex.getMinDist(), Integer.MAX_VALUE);
		vertex.setMinDist(2);
		assertEquals(vertex.getMinDist(), 2);
	}

	/**
	 * testCompareTo
	 */
	@Test
	public void testCompareTo() {
		Vertex test1 = new Vertex("A11", "B22", 11, 11, 11);
		test1.setMinDist(11);
		Vertex test2 = new Vertex("A22", "B22", 22, 22, 22);
		test2.setMinDist(22);
		Vertex test3 = new Vertex("A33", "B33", 33, 33, 33);
		test3.setMinDist(22);
		assertTrue(test1.compareTo(test2) < 0);
		assertEquals(test2.compareTo(test3), 0);
	}

}
