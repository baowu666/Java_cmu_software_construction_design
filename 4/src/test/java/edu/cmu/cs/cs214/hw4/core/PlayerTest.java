package edu.cmu.cs.cs214.hw4.core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test player class
 * 
 * @author zhaoru
 *
 */
public class PlayerTest {
	private Player player;

	@Before
	public void setUp() {
		player = new Player();
	}

	/**
	 * Test player's attributes if we modify
	 */
	@Test
	public void testPlayer() {
		assertNull(player.getName());
		player.setName("1");
		assertEquals(player.getName(), "1");

		assertFalse(player.isTurn());
		player.setTurn(true);
		assertTrue(player.isTurn());

		assertEquals(player.getScore(), 0);
		player.addScore(10);
		assertEquals(player.getScore(), 10);
		player.addScore(-5);
		assertEquals(player.getScore(), 5);
	}

}
