package edu.cmu.cs.cs214.hw4.core;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class to test all methods in Game class. Also test nearly all methods
 * inside core package. Do not need to test CurrentMove class, because the
 * isValid() and move() already include all methods in CurrentMove class. I
 * already set complete test cases to test each scenario from isValid() and
 * move().
 * 
 * @author zhaoru
 *
 */
public class GameTest {
	private Game game;

	@Before
	public void setUp() {
		game = new Game();
	}

	/**
	 * Test start game and some game attributes. Also nextPlayer()
	 */
	@Test
	public void testStart() {
		game.newGame(1); // invalid
		assertEquals(game.getNumPlayers(), 0);
		game.newGame(5); // invalid
		assertEquals(game.getNumPlayers(), 0);
		assertNull(game.getPlayers());
		game.newGame(3);
		assertEquals(game.getNumPlayers(), 3);
		assertNotNull(game.getPlayers()[0]);
		assertEquals(game.getPlayers()[0].getName(), "0");
		assertEquals(game.getCurrentPlayer().getName(), "0");
		assertEquals(game.getCurrentPlayer().getWordRack().getNum(), 7);
		assertEquals(game.getTileBag().getNum(), 91);

		game.nextPlayer();
		assertFalse(game.getPlayers()[0].isTurn());
		assertEquals(game.getCurrentPlayer(), game.getPlayers()[1]);
		assertTrue(game.getCurrentPlayer().isTurn());

		game.nextPlayer();
		assertEquals(game.getCurrentPlayer(), game.getPlayers()[2]);

		game.nextPlayer(); // turn around to player[0]
		assertTrue(game.getPlayers()[0].isTurn());
		assertEquals(game.getCurrentPlayer(), game.getPlayers()[0]);
		assertTrue(game.getCurrentPlayer().isTurn());

		game.reverseOrder();
		game.nextPlayer(); // back to player[2]
		assertEquals(game.getCurrentPlayer(), game.getPlayers()[2]);
	}

	/**
	 * Test if the game can end as expected
	 */
	@Test
	public void testGameOver() {
		Player winner = new Player();
		game.newGame(2);
		game.getPlayers()[0].setScore(5);
		game.getPlayers()[1].setScore(10);
		game.getTileBag().getWordTiles(91);
		game.nextPlayer();
		if (game.isGameOver()) {
			winner = game.compareWinner();
		}
		assertNotNull(winner);
		assertEquals(winner, game.getPlayers()[1]);
	}

	/**
	 * Test purchasing and using special tiles
	 */
	@Test
	public void testBuyUseSpecial() {
		// Test purchase
		game.newGame(2);
		game.getCurrentPlayer().setScore(75);
		game.purchaseSpecialTile(new BoomTile());
		game.purchaseSpecialTile(new ReverseTile());
		game.purchaseSpecialTile(new RevengeTile());
		game.purchaseSpecialTile(new NegativeTile());
		game.purchaseSpecialTile(new BoomTile()); // no enough money
		assertEquals(game.getCurrentPlayer().getSpecialRack().getNum(), 4);
		assertEquals(game.getCurrentPlayer().getScore(), 5);

		// test use
		game.getBoard().getSquares().get(0).setWord(new WordTile("a", 1));
		SpecialTile special = game.getCurrentPlayer().getSpecialRack()
				.getSpecialTiles().get(0);
		SpecialTile special1 = game.getCurrentPlayer().getSpecialRack()
				.getSpecialTiles().get(1);

		game.useSpecialTile(special, new Coordinate(0, 0)); // cannot over a
															// word tile
		assertNull(game.getBoard().getSquares().get(0).getSpecial());
		assertEquals(game.getCurrentPlayer().getSpecialRack().getNum(), 4);

		game.useSpecialTile(special, new Coordinate(0, 1)); // successful
		assertNotNull(game.getBoard().getSquares().get(1).getSpecial());
		assertEquals(
				game.getBoard().getSquares().get(1).getSpecial().getName(),
				"B");
		assertEquals(game.getCurrentPlayer().getSpecialRack().getNum(), 3);

		game.useSpecialTile(special1, new Coordinate(20, 20)); // invalid
																// location
		assertEquals(
				game.getBoard().getSquares().get(1).getSpecial().getName(),
				"B");
		assertEquals(game.getCurrentPlayer().getSpecialRack().getNum(), 3);

		game.useSpecialTile(special1, new Coordinate(0, 1)); // replace the
																// special
		assertEquals(
				game.getBoard().getSquares().get(1).getSpecial().getName(),
				"R");
		assertEquals(game.getCurrentPlayer().getSpecialRack().getNum(), 2);
	}

	/**
	 * Test placing single word
	 */
	@Test
	public void testPlaceWord() {
		game.newGame(2);
		Player player = game.getCurrentPlayer();
		game.placeWord(player.getWordRack().getWordTiles().get(0),
				new Coordinate(0, 0));
		game.placeWord(player.getWordRack().getWordTiles().get(0),
				new Coordinate(0, 1));
		game.placeWord(player.getWordRack().getWordTiles().get(0),
				new Coordinate(0, 2));
		assertEquals(player.getWordRack().getNum(), 4);
		assertNotNull(game.getBoard().getSquares().get(0));
		assertNotNull(game.getBoard().getSquares().get(1));
		assertNotNull(game.getBoard().getSquares().get(2));

		// cannot place over a word tile
		game.placeWord(player.getWordRack().getWordTiles().get(0),
				new Coordinate(0, 0));
		game.placeWord(player.getWordRack().getWordTiles().get(0),
				new Coordinate(0, 1));
		assertEquals(player.getWordRack().getNum(), 4);

		// invalid location
		game.placeWord(player.getWordRack().getWordTiles().get(0),
				new Coordinate(20, 20));
		assertEquals(player.getWordRack().getNum(), 4);
	}

	/**
	 * Test swaping tiles with some border cases
	 */
	@Test
	public void testSwapTiles() {
		game.newGame(2);
		List<WordTile> swap = new ArrayList<WordTile>();
		WordTile s1 = game.getCurrentPlayer().getWordRack().getWordTiles()
				.get(5);
		WordTile s2 = game.getCurrentPlayer().getWordRack().getWordTiles()
				.get(6);
		WordTile s3 = game.getCurrentPlayer().getWordRack().getWordTiles()
				.get(4);
		swap.add(s1);
		swap.add(s2);// swap two, reserve one

		// before
		assertEquals(game.getTileBag().getNum(), 91);
		assertEquals(game.getCurrentPlayer().getWordRack().getWordTiles()
				.get(5), s1);
		assertEquals(game.getCurrentPlayer().getWordRack().getWordTiles()
				.get(6), s2);
		assertEquals(game.getCurrentPlayer().getWordRack().getWordTiles()
				.get(4), s3);

		// test not enough tiles in bag
		List<WordTile> remove = game.getTileBag().getWordTiles(90);
		game.swapTiles(swap); // should not successful swap
		assertEquals(game.getTileBag().getNum(), 1);
		assertEquals(game.getCurrentPlayer().getWordRack().getWordTiles()
				.get(5), s1);
		assertEquals(game.getCurrentPlayer().getWordRack().getWordTiles()
				.get(6), s2);
		assertEquals(game.getCurrentPlayer().getWordRack().getWordTiles()
				.get(4), s3);
		game.getTileBag().addBagWords(remove);

		game.swapTiles(swap);

		// after
		assertEquals(game.getTileBag().getNum(), 91);
		assertEquals(game.getCurrentPlayer().getWordRack().getNum(), 7);
		assertNotEquals(game.getCurrentPlayer().getWordRack().getWordTiles()
				.get(5), s1);
		assertNotEquals(game.getCurrentPlayer().getWordRack().getWordTiles()
				.get(6), s2);
		assertEquals(game.getCurrentPlayer().getWordRack().getWordTiles()
				.get(4), s3); // should not change
	}

	/**
	 * Test complementing tiles with some border cases
	 */
	@Test
	public void testComplementTiles() {
		game.newGame(2);

		assertEquals(game.getCurrentPlayer(), game.getPlayers()[0]);

		// remove a word tile
		assertEquals(game.getCurrentPlayer().getWordRack().getNum(), 7);
		WordTile remove = game.getCurrentPlayer().getWordRack().getWordTiles()
				.get(6);
		game.getCurrentPlayer().getWordRack().removeRackWord(remove);
		assertEquals(game.getCurrentPlayer().getWordRack().getNum(), 6);

		assertEquals(game.getTileBag().getNum(), 91);

		// complement first player
		game.complementTiles();
		assertEquals(game.getCurrentPlayer().getWordRack().getNum(), 7);
		assertNotEquals(game.getCurrentPlayer().getWordRack().getWordTiles()
				.get(6), remove);

		assertEquals(game.getTileBag().getNum(), 90);

		// automatically complement second player when calling nextPlayer()
		game.nextPlayer();
		assertEquals(game.getTileBag().getNum(), 83);
		assertEquals(game.getCurrentPlayer(), game.getPlayers()[1]);
		assertEquals(game.getCurrentPlayer().getWordRack().getNum(), 7);

		// remove two tiles
		WordTile remove1 = game.getCurrentPlayer().getWordRack().getWordTiles()
				.get(5);
		WordTile remove2 = game.getCurrentPlayer().getWordRack().getWordTiles()
				.get(6);
		game.getCurrentPlayer().getWordRack().removeRackWord(remove1);
		game.getCurrentPlayer().getWordRack().removeRackWord(remove2);
		assertEquals(game.getCurrentPlayer().getWordRack().getNum(), 5);

		// not enough tiles in bag, thus not complement to 7 tiles
		game.getTileBag().getWordTiles(82);
		assertEquals(game.getTileBag().getNum(), 1);
		game.complementTiles();
		assertEquals(game.getTileBag().getNum(), 0);
		assertEquals(game.getCurrentPlayer().getWordRack().getNum(), 6);
	}

	/**
	 * Test if the game can retreat moves as expected
	 */
	@Test
	public void testRetreat() {
		game.newGame(2);

		assertEquals(game.getTileBag().getNum(), 91);

		// place three tiles
		assertEquals(game.getCurrentPlayer().getWordRack().getNum(), 7);
		game.placeWord(game.getCurrentPlayer().getWordRack().getWordTiles()
				.get(0), new Coordinate(0, 0));
		game.placeWord(game.getCurrentPlayer().getWordRack().getWordTiles()
				.get(0), new Coordinate(0, 1));
		game.placeWord(game.getCurrentPlayer().getWordRack().getWordTiles()
				.get(0), new Coordinate(0, 2));
		game.placeWord(game.getCurrentPlayer().getWordRack().getWordTiles()
				.get(0), new Coordinate(0, 2)); // invalid
		assertEquals(game.getCurrentPlayer().getWordRack().getNum(), 4);
		assertNotNull(game.getBoard().getSquares().get(0));
		assertNotNull(game.getBoard().getSquares().get(1));
		assertNotNull(game.getBoard().getSquares().get(2));
		assertEquals(game.getCurrentMove().getCurrentSquares().size(), 3); // move
																			// class's
																			// current
																			// squares

		game.retreat();
		game.retreat();// double check

		// tiles come back to the player
		assertEquals(game.getTileBag().getNum(), 91);
		assertEquals(game.getCurrentPlayer().getWordRack().getNum(), 7);
		assertNull(game.getBoard().getSquares().get(0).getWord());
		assertNull(game.getBoard().getSquares().get(1).getWord());
		assertNull(game.getBoard().getSquares().get(2).getWord());
		assertEquals(game.getCurrentMove().getCurrentSquares().size(), 0); // move
																			// class's
																			// current
																			// squares
	}

	/**
	 * Use some border cases to test the isValid()
	 */
	@Test
	public void testIsValidScenario1() {
		/*
		 * h e l l o
		 * o     o
		 * w     a
		 * s e e d
		 * 
		 */
		game.newGame(2);

		// first player not place in middle
		game.placeWord(new WordTile("h", 1), new Coordinate(8, 5));
		game.placeWord(new WordTile("e", 1), new Coordinate(8, 6));
		game.placeWord(new WordTile("l", 1), new Coordinate(8, 7));
		game.placeWord(new WordTile("l", 1), new Coordinate(8, 8));
		assertFalse(game.isValid());
		game.retreat(); // the number of tiles in word rack would be more than
						// 7, but it's ok, we do not care this inside isvalid
						// test

		// not put inside the board
		game.placeWord(new WordTile("x", 1), new Coordinate(0, 15));
		game.placeWord(new WordTile("y", 1), new Coordinate(20, 20));
		assertFalse(game.isValid());
		game.retreat();

		// not put in line
		game.placeWord(new WordTile("h", 1), new Coordinate(7, 7));
		game.placeWord(new WordTile("e", 1), new Coordinate(7, 8));
		game.placeWord(new WordTile("l", 1), new Coordinate(7, 10));
		game.placeWord(new WordTile("l", 1), new Coordinate(7, 11));
		assertFalse(game.isValid());
		game.retreat();

		// not form the first word
		game.placeWord(new WordTile("h", 1), new Coordinate(7, 7));
		game.placeWord(new WordTile("e", 1), new Coordinate(7, 8));
		game.placeWord(new WordTile("l", 1), new Coordinate(7, 9));
		game.placeWord(new WordTile("x", 1), new Coordinate(7, 10));
		assertFalse(game.isValid());
		game.retreat();

		// place the first word, "hell"
		game.placeWord(new WordTile("e", 1), new Coordinate(7, 6));
		game.placeWord(new WordTile("l", 1), new Coordinate(7, 7));
		game.placeWord(new WordTile("h", 1), new Coordinate(7, 5));
		game.placeWord(new WordTile("l", 1), new Coordinate(7, 8));
		assertTrue(game.isValid());

		game.nextPlayer();

		// though "o" cannot form a word itself, but hello can
		game.placeWord(new WordTile("o", 1), new Coordinate(7, 9));
		assertTrue(game.isValid());

		game.nextPlayer();

		// forming "how"
		game.placeWord(new WordTile("w", 1), new Coordinate(9, 5));
		game.placeWord(new WordTile("o", 1), new Coordinate(8, 5));
		assertTrue(game.isValid());

		game.nextPlayer();

		// not form adjacent word
		game.placeWord(new WordTile("y", 1), new Coordinate(8, 6));
		game.placeWord(new WordTile("e", 1), new Coordinate(9, 6));
		assertFalse(game.isValid());
		game.retreat();

		// too far and not adjacent to old tiles
		game.placeWord(new WordTile("o", 1), new Coordinate(8, 12));
		game.placeWord(new WordTile("d", 1), new Coordinate(10, 12));
		game.placeWord(new WordTile("a", 1), new Coordinate(9, 12));
		assertFalse(game.isValid());
		game.retreat();
		
		// forming "load"
		game.placeWord(new WordTile("o", 1), new Coordinate(8, 8));
		game.placeWord(new WordTile("d", 1), new Coordinate(10, 8));
		game.placeWord(new WordTile("a", 1), new Coordinate(9, 8));
		assertTrue(game.isValid());

		game.nextPlayer();

		// check placing only one letter that forms the vertical word
		game.placeWord(new WordTile("s", 1), new Coordinate(11, 8));
		assertTrue(game.isValid());

		game.nextPlayer();
		
		// invalid, "rand" is ok, but "howr" is not
		game.placeWord(new WordTile("r", 1), new Coordinate(10, 5));
		game.placeWord(new WordTile("a", 1), new Coordinate(10, 6));
		game.placeWord(new WordTile("n", 1), new Coordinate(10, 7));
		assertFalse(game.isValid());
		game.retreat();

		// forming "hows", "seed"
		game.placeWord(new WordTile("s", 1), new Coordinate(10, 5));
		game.placeWord(new WordTile("e", 1), new Coordinate(10, 7));
		game.placeWord(new WordTile("e", 1), new Coordinate(10, 6));
		assertTrue(game.isValid());

		game.nextPlayer();
	}

	/**
	 * Use official example from hasbro.com to test isValid()
	 */
	@Test
	public void testIsValidScenario2() {
		/*
		 * an example from hasbro.com
		 *     
		 *         f
		 *         a
		 *     h o r n
		 *         m o b
		 *     p a s t e
		 * b i t
		 *  
		 */
		game.newGame(2);

		// "horn"
		game.placeWord(new WordTile("o", 1), new Coordinate(7, 6));
		game.placeWord(new WordTile("h", 1), new Coordinate(7, 5));
		game.placeWord(new WordTile("n", 1), new Coordinate(7, 8));
		game.placeWord(new WordTile("r", 1), new Coordinate(7, 7));
		assertTrue(game.isValid());

		game.nextPlayer();

		// "farm", and "farms"
		game.placeWord(new WordTile("m", 1), new Coordinate(8, 7));
		game.placeWord(new WordTile("f", 1), new Coordinate(5, 7));
		game.placeWord(new WordTile("a", 1), new Coordinate(6, 7));
		assertTrue(game.isValid());

		game.nextPlayer();

		// "paste"
		game.placeWord(new WordTile("t", 1), new Coordinate(9, 8));
		game.placeWord(new WordTile("p", 1), new Coordinate(9, 5));
		game.placeWord(new WordTile("a", 1), new Coordinate(9, 6));
		game.placeWord(new WordTile("s", 1), new Coordinate(9, 7));
		game.placeWord(new WordTile("e", 1), new Coordinate(9, 9));
		assertTrue(game.isValid());

		game.nextPlayer();

		// "mob", and "not" "be"
		game.placeWord(new WordTile("o", 1), new Coordinate(8, 8));
		game.placeWord(new WordTile("b", 1), new Coordinate(8, 9));
		assertTrue(game.isValid());

		game.nextPlayer();

		// "bit", and "pi" "at"
		game.placeWord(new WordTile("b", 1), new Coordinate(10, 4));
		game.placeWord(new WordTile("i", 1), new Coordinate(10, 5));
		game.placeWord(new WordTile("t", 1), new Coordinate(10, 6));
		assertTrue(game.isValid());

		game.nextPlayer();
	}

	/**
	 * use earlier test case to test if the move() can perform as expected,
	 * calculate the right score
	 */
	@Test
	public void testMoveScenario1() {
		/*
		 * an example from hasbro.com
		 *     
		 *         f
		 *         a
		 *     h o r n
		 *         m o b
		 *     p a s t e
		 *   b i t
		 *  
		 */
		game.newGame(2);

		// "horn"
		game.placeWord(new WordTile("o", 1), new Coordinate(7, 6));
		game.placeWord(new WordTile("h", 1), new Coordinate(7, 5));
		game.placeWord(new WordTile("n", 1), new Coordinate(7, 8));
		game.placeWord(new WordTile("r", 2), new Coordinate(7, 7));
		assertTrue(game.isValid());
		assertEquals(game.getPlayers()[0].getScore(), 0);
		game.move();
		// active a DL from middle
		assertEquals(game.getPlayers()[0].getScore(), 10);

		game.nextPlayer();

		// "farm"
		game.placeWord(new WordTile("m", 2), new Coordinate(8, 7));
		game.placeWord(new WordTile("f", 3), new Coordinate(5, 7));
		game.placeWord(new WordTile("a", 1), new Coordinate(6, 7));
		assertTrue(game.isValid());
		assertEquals(game.getPlayers()[1].getScore(), 0);
		game.move();
		assertEquals(game.getPlayers()[1].getScore(), 8);

		game.nextPlayer();

		// "paste", and "farms"
		game.placeWord(new WordTile("t", 1), new Coordinate(9, 8));
		game.placeWord(new WordTile("p", 2), new Coordinate(9, 5));
		game.placeWord(new WordTile("a", 1), new Coordinate(9, 6));
		game.placeWord(new WordTile("s", 2), new Coordinate(9, 7));
		game.placeWord(new WordTile("e", 3), new Coordinate(9, 9));
		assertTrue(game.isValid());
		assertEquals(game.getPlayers()[0].getScore(), 10);
		game.move();
		// active 2 TL, "p" and "e"
		assertEquals(game.getPlayers()[0].getScore(), 39);

		game.nextPlayer();

		// "mob", and "not" "be"
		game.placeWord(new WordTile("o", 4), new Coordinate(8, 8));
		game.placeWord(new WordTile("b", 3), new Coordinate(8, 9));
		assertTrue(game.isValid());
		assertEquals(game.getPlayers()[1].getScore(), 8);
		game.move();
		// active a DL, which can only be used once, on "mob", the first letter
		assertEquals(game.getPlayers()[1].getScore(), 33);

		game.nextPlayer();

		// "bit", and "pi" "at"
		game.placeWord(new WordTile("b", 3), new Coordinate(10, 4));
		game.placeWord(new WordTile("i", 3), new Coordinate(10, 5));
		game.placeWord(new WordTile("t", 3), new Coordinate(10, 6));
		assertTrue(game.isValid());
		assertEquals(game.getPlayers()[0].getScore(), 39);
		game.move();
		// active a DW, which is "bit"
		assertEquals(game.getPlayers()[0].getScore(), 66);

		game.nextPlayer();
	}

	/**
	 * Add special tiles and test if the game can perform as expected under
	 * different condition
	 */
	@Test
	public void testMoveScenario2() {
		/*
		 * an example from hasbro.com
		 *     
		 *         f
		 *         a
		 *     h o r n
		 *         m o b
		 *     p a s t e
		 *   b i t
		 *  
		 */
		game.newGame(2);

		// "horn"
		game.placeWord(new WordTile("o", 1), new Coordinate(7, 6));
		game.placeWord(new WordTile("h", 1), new Coordinate(7, 5));
		game.placeWord(new WordTile("n", 1), new Coordinate(7, 8));
		game.placeWord(new WordTile("r", 2), new Coordinate(7, 7));

		game.useSpecialTile(new NegativeTile(), new Coordinate(6, 7));
		game.useSpecialTile(new NegativeTile(), new Coordinate(6, 7));
		game.useSpecialTile(new ReverseTile(), new Coordinate(5, 7));
		game.useSpecialTile(new RevengeTile(), new Coordinate(9, 7));
		// following three are used to check revenge tile
		game.useSpecialTile(new ReverseTile(), new Coordinate(0, 0));
		game.useSpecialTile(new ReverseTile(), new Coordinate(0, 1));
		game.useSpecialTile(new ReverseTile(), new Coordinate(0, 2));

		assertTrue(game.isValid());
		assertEquals(game.getPlayers()[0].getScore(), 0);

		game.move();
		// active a DL from middle
		assertEquals(game.getPlayers()[0].getScore(), 10);

		game.nextPlayer();

		// "farm"
		game.placeWord(new WordTile("m", 2), new Coordinate(8, 7));
		game.placeWord(new WordTile("f", 3), new Coordinate(5, 7));
		game.placeWord(new WordTile("a", 1), new Coordinate(6, 7));

		// following two are to check revenge tiles
		game.useSpecialTile(new ReverseTile(), new Coordinate(0, 3));
		game.useSpecialTile(new ReverseTile(), new Coordinate(0, 4));

		assertTrue(game.isValid());
		assertEquals(game.getPlayers()[1].getScore(), 0);
		assertNotNull(game.getBoard().getSquareByCoord(new Coordinate(6, 7))
				.getSpecial());
		assertNotNull(game.getBoard().getSquareByCoord(new Coordinate(5, 7))
				.getSpecial());
		assertEquals(game.getOrderFlag(), 1);

		// active a negative tile and a reverse tile
		game.move();

		assertNull(game.getBoard().getSquareByCoord(new Coordinate(6, 7))
				.getSpecial());
		assertNull(game.getBoard().getSquareByCoord(new Coordinate(5, 7))
				.getSpecial());
		assertEquals(game.getOrderFlag(), -1);
		assertEquals(game.getPlayers()[1].getScore(), -8);

		game.nextPlayer();

		// "paste", and "farms"
		game.placeWord(new WordTile("t", 1), new Coordinate(9, 8));
		game.placeWord(new WordTile("p", 2), new Coordinate(9, 5));
		game.placeWord(new WordTile("a", 1), new Coordinate(9, 6));
		game.placeWord(new WordTile("s", 2), new Coordinate(9, 7));
		game.placeWord(new WordTile("e", 3), new Coordinate(9, 9));
		assertTrue(game.isValid());
		assertEquals(game.getPlayers()[0].getScore(), 10);

		assertNotNull(game.getBoard().getSquareByCoord(new Coordinate(9, 7))
				.getSpecial());
		assertNotNull(game.getBoard().getSquareByCoord(new Coordinate(0, 0))
				.getSpecial());
		assertNotNull(game.getBoard().getSquareByCoord(new Coordinate(0, 1))
				.getSpecial());
		assertNotNull(game.getBoard().getSquareByCoord(new Coordinate(0, 2))
				.getSpecial());
		assertNotNull(game.getBoard().getSquareByCoord(new Coordinate(0, 3))
				.getSpecial());
		assertNotNull(game.getBoard().getSquareByCoord(new Coordinate(0, 4))
				.getSpecial());

		// active a revenge tile
		game.move();
		// active 2 TL, "p" and "e"
		assertEquals(game.getPlayers()[0].getScore(), 39);

		assertNull(game.getBoard().getSquareByCoord(new Coordinate(9, 7))
				.getSpecial());
		assertNull(game.getBoard().getSquareByCoord(new Coordinate(0, 0))
				.getSpecial());
		assertNull(game.getBoard().getSquareByCoord(new Coordinate(0, 1))
				.getSpecial());
		assertNull(game.getBoard().getSquareByCoord(new Coordinate(0, 2))
				.getSpecial());
		assertNotNull(game.getBoard().getSquareByCoord(new Coordinate(0, 3))
				.getSpecial());
		assertNotNull(game.getBoard().getSquareByCoord(new Coordinate(0, 4))
				.getSpecial());

		game.nextPlayer();

		// "mob", and "not" "be"
		game.placeWord(new WordTile("o", 4), new Coordinate(8, 8));
		game.placeWord(new WordTile("b", 3), new Coordinate(8, 9));

		// to boom out "b" "i" "p"
		game.useSpecialTile(new BoomTile(), new Coordinate(10, 2));

		assertTrue(game.isValid());
		assertEquals(game.getPlayers()[1].getScore(), -8);
		game.move();
		// active a DL, which can only be used once, on "mob", the first letter
		assertEquals(game.getPlayers()[1].getScore(), 17);

		game.nextPlayer();

		// "bit", and "pi" "at"
		game.placeWord(new WordTile("b", 3), new Coordinate(10, 4));
		game.placeWord(new WordTile("i", 3), new Coordinate(10, 5));
		game.placeWord(new WordTile("t", 3), new Coordinate(10, 6));

		assertTrue(game.isValid());

		// activate boom
		game.placeWord(new WordTile("x", 3), new Coordinate(10, 1));
		game.placeWord(new WordTile("y", 3), new Coordinate(10, 2));

		assertEquals(game.getPlayers()[0].getScore(), 39);

		assertNotNull(game.getBoard().getSquareByCoord(new Coordinate(10, 1))
				.getWord()); // "x"
		assertNotNull(game.getBoard().getSquareByCoord(new Coordinate(10, 2))
				.getWord()); // "y"
		assertNotNull(game.getBoard().getSquareByCoord(new Coordinate(10, 4))
				.getWord()); // "b"
		assertNotNull(game.getBoard().getSquareByCoord(new Coordinate(10, 5))
				.getWord()); // "i"
		assertNotNull(game.getBoard().getSquareByCoord(new Coordinate(10, 6))
				.getWord()); // "t"
		assertNotNull(game.getBoard().getSquareByCoord(new Coordinate(9, 5))
				.getWord()); // "p"
		assertNotNull(game.getBoard().getSquareByCoord(new Coordinate(9, 6))
				.getWord()); // "a"
		assertNotNull(game.getBoard().getSquareByCoord(new Coordinate(7, 5))
				.getWord()); // "h"
		assertNotNull(game.getBoard().getSquareByCoord(new Coordinate(7, 6))
				.getWord()); // "o"
		assertEquals(game.getBoard().getSquareByCoord(new Coordinate(10, 2))
				.getSpecial().getName(), "B");

		game.move();
		// active a DW, which is "bit". The words that should count are "bit",
		// "pi", and "at". But "b" "i" "p" are boomed out, only "a" and "t"
		// count. "a" and "t" worth 1+3=4 points from word "at". "t" worth 3x2=6
		// points from word "bit"(b has first triggered the TW)
		assertEquals(game.getPlayers()[0].getScore(), 49);

		assertNull(game.getBoard().getSquareByCoord(new Coordinate(10, 1))
				.getWord()); // "x"
		assertNull(game.getBoard().getSquareByCoord(new Coordinate(10, 2))
				.getWord()); // "y"
		assertNull(game.getBoard().getSquareByCoord(new Coordinate(10, 4))
				.getWord()); // "b"
		assertNull(game.getBoard().getSquareByCoord(new Coordinate(10, 5))
				.getWord()); // "i"
		assertNotNull(game.getBoard().getSquareByCoord(new Coordinate(10, 6))
				.getWord()); // "t"
		assertNull(game.getBoard().getSquareByCoord(new Coordinate(9, 5))
				.getWord()); // "p"
		assertNotNull(game.getBoard().getSquareByCoord(new Coordinate(9, 6))
				.getWord()); // "a"
		assertNull(game.getBoard().getSquareByCoord(new Coordinate(7, 5))
				.getWord()); // "h"
		assertNotNull(game.getBoard().getSquareByCoord(new Coordinate(7, 6))
				.getWord()); // "o"
		assertNull(game.getBoard().getSquareByCoord(new Coordinate(10, 2))
				.getSpecial()); // boom tile

		game.nextPlayer();
	}
}
