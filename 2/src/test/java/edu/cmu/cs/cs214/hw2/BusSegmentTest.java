package edu.cmu.cs.cs214.hw2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for BusSegment
 * 
 * @author zhaoru
 *
 */
public class BusSegmentTest {
	private TripSegment busSegmentTest;

	@Before
	public void setUp() {
		Stop test1 = new Vertex("A1", "B1", 1, 1, 1);
		Stop test2 = new Vertex("A2", "B2", 2, 2, 2);
		int weight = 1;
		busSegmentTest = new BusSegment(test1, test2, weight);
	}

	/**
	 * testGetStopFrom
	 */
	@Test
	public void testGetStopFrom() {
		assertNotNull(busSegmentTest.getStopFrom());
	}

	/**
	 * testGetStopTo
	 */
	@Test
	public void testGetStopTo() {
		assertNotNull(busSegmentTest.getStopTo());
	}

	/**
	 * testGetWeightTime
	 */
	@Test
	public void testGetWeightTime() {
		assertEquals(busSegmentTest.getWeightTime(), 1);
	}

	/**
	 * testGetMechanism
	 */
	@Test
	public void testGetMechanism() {
		assertEquals(busSegmentTest.getMechanism(), "Bus");
	}
}
