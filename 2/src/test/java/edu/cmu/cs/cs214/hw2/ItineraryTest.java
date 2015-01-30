package edu.cmu.cs.cs214.hw2;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for Itinerary
 * 
 * @author zhaoru
 *
 */
public class ItineraryTest {
	private Itinerary itineraryTest;
	private Itinerary itineraryTestEmpty;

	@Before
	public void setUp() {
		List<TripSegment> tripSegments = new ArrayList<TripSegment>();
		List<TripSegment> tripSegmentsEmpty = new ArrayList<TripSegment>();
		Stop test1 = new Vertex("A1", "B1", 1, 1, 1);
		Stop test2 = new Vertex("A1", "B2", 2, 2, 2);
		Stop test3 = new Vertex("A2", "B3", 3, 3, 5);
		Stop test4 = new Vertex("A2", "B4", 4, 4, 9);
		TripSegment segment1 = new BusSegment(test1, test2, 1);
		TripSegment segment2 = new WaitSegment(test2, test3, 2);
		TripSegment segment3 = new BusSegment(test3, test4, 3);
		tripSegments.add(segment1);
		tripSegments.add(segment2);
		tripSegments.add(segment3);
		itineraryTest = new Itinerary("Test", 10, tripSegments);
		itineraryTestEmpty = new Itinerary("Test", 10, tripSegmentsEmpty);
	}

	/**
	 * testGetStartTime
	 */
	@Test
	public void testGetStartTime() {
		assertEquals(itineraryTestEmpty.getStartTime(), -1);
		assertEquals(itineraryTest.getStartTime(), 1);
	}

	/**
	 * testGetEndTime
	 */
	@Test
	public void testGetEndTime() {
		assertEquals(itineraryTestEmpty.getEndTime(), -1);
		assertEquals(itineraryTest.getEndTime(), 9);
	}

	/**
	 * testGetWaitTime
	 */
	@Test
	public void testGetWaitTime() {
		assertEquals(itineraryTestEmpty.getWaitTime(), -1);
		assertEquals(itineraryTest.getWaitTime(), 12); // (5-2)+(10-1) = 12
	}

	/**
	 * testGetStartLocation
	 */
	@Test
	public void testGetStartLocation() {
		assertNull(itineraryTestEmpty.getStartLocation());
		assertEquals(itineraryTest.getStartLocation().getName(), "B1");
	}

	/**
	 * testGetEndLocation
	 */
	@Test
	public void testGetEndLocation() {
		assertNull(itineraryTestEmpty.getEndLocation());
		assertEquals(itineraryTest.getEndLocation().getName(), "B4");
	}

	/**
	 * testFindChangeStop
	 */
	@Test
	public void testFindChangeStop() {
		List<TripSegment> changingSeg = itineraryTest.findChangeStop();
		assertEquals(changingSeg.size(), 1);
		assertEquals(changingSeg.get(0).getStopFrom().getName(), "B2");
		assertEquals(changingSeg.get(0).getStopTo().getName(), "B3");
	}

	/**
	 * testGetInstructions
	 */
	@Test
	public void testGetInstructions() {
		List<TripSegment> tripSegmentsEmpty = new ArrayList<TripSegment>();
		Itinerary itineraryTestEmpty = new Itinerary("Test Empty", 20,
				tripSegmentsEmpty);
		String errorMessage = "Wrong! You cannot get there. Maybe your max wait time is to short.";
		assertEquals(itineraryTestEmpty.getInstructions(), errorMessage);
		assertNotNull(itineraryTest.getInstructions());
	}
}
