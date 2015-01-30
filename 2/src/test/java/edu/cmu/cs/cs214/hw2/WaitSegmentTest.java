package edu.cmu.cs.cs214.hw2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for WaitSegment
 * 
 * @author zhaoru
 *
 */
public class WaitSegmentTest {
	private TripSegment waitSegmentTest;

	@Before
	public void setUp() {
		Stop test1 = new Vertex("A1", "B1", 1, 1, 1);
		Stop test2 = new Vertex("A2", "B2", 2, 2, 2);
		int weight = 1;
		waitSegmentTest = new WaitSegment(test1, test2, weight);
	}

	/**
	 * testGetStopFrom
	 */
	@Test
	public void testGetStopFrom() {
		assertNotNull(waitSegmentTest.getStopFrom());
	}

	/**
	 * testGetStopTo
	 */
	@Test
	public void testGetStopTo() {
		assertNotNull(waitSegmentTest.getStopTo());
	}

	/**
	 * testGetWeightTime
	 */
	@Test
	public void testGetWeightTime() {
		assertEquals(waitSegmentTest.getWeightTime(), 1);
	}

	/**
	 * testGetMechanism
	 */
	@Test
	public void testGetMechanism() {
		assertEquals(waitSegmentTest.getMechanism(), "Wait");
	}
}
