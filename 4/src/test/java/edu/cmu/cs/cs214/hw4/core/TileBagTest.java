package edu.cmu.cs.cs214.hw4.core;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test tileBag class
 * 
 * @author zhaoru
 *
 */
public class TileBagTest {
	private TileBag tileBag;

	@Before
	public void setUp() {
		tileBag = new TileBag();
	}

	/**
	 * Test tilebag class's attributes
	 */
	@Test
	public void testTileBag() {
		assertEquals(tileBag.getNum(), 98);
		List<WordTile> remove = tileBag.getWordTiles(98);
		assertEquals(tileBag.getNum(), 0);
		tileBag.addBagWords(remove);
		assertEquals(tileBag.getNum(), 98);
	}

	/**
	 * Test if shuffle can work
	 */
	@Test
	public void testShuffle() {
		WordTile t1 = tileBag.getWordTiles(1).get(0);
		String w1 = t1.getLetter();
		tileBag.addBagWord(t1);
		WordTile t2 = tileBag.getWordTiles(1).get(0);
		String w2 = t2.getLetter();
		assertNotEquals(w1, w2);
	}

	/**
	 * check if we can get some tiles as expected and with safety
	 */
	@Test
	public void testGetWordTiles() {
		List<WordTile> tiles = tileBag.getWordTiles(10);
		assertEquals(tileBag.getNum(), 88);
		assertEquals(tiles.size(), 10);
		tiles = tileBag.getWordTiles(89);
		assertEquals(tileBag.getNum(), 88);
		tiles = tileBag.getWordTiles(0);
		assertEquals(tiles.size(), 0);
		tiles = tileBag.getWordTiles(88);
		assertEquals(tileBag.getNum(), 0);
	}
}
