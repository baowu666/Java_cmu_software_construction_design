package edu.cmu.cs.cs214.hw4.core;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test board class
 * 
 * @author zhaoru
 *
 */
public class BoardTest {
	private Board board;
	private List<Square> squares;

	@Before
	public void setUp() {
		board = new Board();
		squares = new ArrayList<Square>();
		squares.add(board.getSquares().get(0));
		squares.add(board.getSquares().get(1));
		squares.add(board.getSquares().get(2));
	}

	/**
	 * Test if board's attributes are correct
	 */
	@Test
	public void testBoardAttributes() {
		assertEquals(board.getSquares().get(112).getCoordinate(),
				new Coordinate(7, 7));
		assertEquals(board.getSquares().get(112).getWordBonus(), 2);
		assertEquals(board.getSquares().get(0).getLetterBonus(), 1);
		assertEquals(board.getSquares().get(0).getWordBonus(), 3);
		assertEquals(board.getSquares().get(220).getLetterBonus(), 1);
		assertEquals(board.getSquares().get(220).getWordBonus(), 1);
	}

	/**
	 * if special tile can be checked correct
	 */
	@Test
	public void testCheckSpecialLocation() {
		assertNull(board.checkSpecialLocation(null));
		assertNull(board.checkSpecialLocation(new ArrayList<Square>()));
		assertEquals(board.checkSpecialLocation(squares).size(), 0);
		squares.get(0).setSpecial(new BoomTile());
		squares.get(2).setSpecial(new NegativeTile());
		assertEquals(board.checkSpecialLocation(squares).size(), 2);
	}

	/**
	 * check if we can get square by an coordinate
	 */
	@Test
	public void testGetSquareByCoord() {
		Coordinate coord1 = new Coordinate(0, 20);
		Coordinate coord2 = new Coordinate(7, 7);
		assertNull(board.getSquareByCoord(coord1));
		assertNotNull(board.getSquareByCoord(coord2));
		assertEquals(board.getSquareByCoord(coord2).getCoordinate(),
				new Coordinate(7, 7));
	}

	/**
	 * check if words can be removed correctly
	 */
	@Test
	public void testRemoveWords() {
		for (Square s1 : squares) {
			assertNull(s1.getWord());
			s1.setWord(new WordTile("a", 1));
			assertNotNull(s1.getWord());
		}
		board.removeWords(squares);
		for (Square s2 : squares) {
			assertNull(s2.getWord());
		}
		board.removeWords(squares);
		for (Square s3 : squares) {
			assertNull(s3.getWord());
		}
	}
}
