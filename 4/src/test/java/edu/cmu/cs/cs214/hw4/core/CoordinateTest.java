package edu.cmu.cs.cs214.hw4.core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Check Coordinate class
 * 
 * @author zhaoru
 *
 */
public class CoordinateTest {
	Coordinate coord1, coord2, coord3, coord4, coord5;

	@Before
	public void setUp() {
		coord1 = new Coordinate(0, 0);
		coord2 = new Coordinate(14, 14);
		coord3 = new Coordinate(5, 6);
		coord4 = new Coordinate(5, 6);
		coord5 = new Coordinate(14, 15);
	}

	/**
	 * check if isInboard() can perform as expected
	 */
	@Test
	public void testIsInBoard() {
		assertTrue(coord1.isInBoard());
		assertTrue(coord2.isInBoard());
		assertTrue(coord3.isInBoard());
		assertTrue(coord4.isInBoard());
		assertFalse(coord5.isInBoard());
	}

	/**
	 * Test the override method equals()
	 */
	@Test
	public void testEquals() {
		assertEquals(coord3, coord4);
		assertNotEquals(coord3, coord5);
		assertNotEquals(coord1, coord2);
	}
}
