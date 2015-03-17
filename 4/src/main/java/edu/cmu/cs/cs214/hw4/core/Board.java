package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Board class handles all the squares inside the board
 * 
 * @author zhaoru
 *
 */
public class Board {
	private List<Square> squares;
	private static final int[] TW = new int[] { 0, 7, 14, 105, 119, 210, 217,
			224 };
	private static final int[] DW = new int[] { 16, 28, 32, 42, 48, 56, 64, 70,
			112, 154, 160, 168, 176, 182, 192, 196, 208 };
	private static final int[] TL = new int[] { 20, 24, 76, 80, 84, 88, 136,
			140, 144, 148, 200, 204 };
	private static final int[] DL = new int[] { 3, 11, 36, 38, 45, 52, 59, 92,
			96, 98, 102, 108, 116, 122, 126, 128, 132, 165, 172, 179, 186, 188,
			213, 221 };
	private static final int MAX_BORDER = 15;
	private static final int MULTIPLY_THREE = 3;
	private static final int MULTIPLY_TWO = 2;

	/**
	 * Constructor, initialize all squares' info and set premium location
	 */
	public Board() {
		squares = new ArrayList<Square>();
		for (int row = 0; row < MAX_BORDER; row++) {
			for (int col = 0; col < MAX_BORDER; col++) {
				squares.add(new Square(row, col));
			}
		}

		for (int tw : TW) {
			squares.get(tw).setWordBonus(MULTIPLY_THREE);
		}

		for (int dw : DW) {
			squares.get(dw).setWordBonus(MULTIPLY_TWO);
		}

		for (int tl : TL) {
			squares.get(tl).setLetterBonus(MULTIPLY_THREE);
		}

		for (int dl : DL) {
			squares.get(dl).setLetterBonus(MULTIPLY_TWO);
		}
	}

	/**
	 * get squares
	 * 
	 * @return all squares of the board
	 */
	public List<Square> getSquares() {
		return squares;
	}

	/**
	 * get the number of squares that have word tiles
	 * 
	 * @return number of squares that have word tiles
	 */
	public int getNum() {
		int count = 0;
		for (Square s: squares) {
			if (s.getWord() != null) {
				count++;
			}
		}
		return count;
	}

	/**
	 * check all squares with special tiles in them
	 * 
	 * @param newSquares
	 *            a list of squares we want to check
	 * @return a list of squares with special tiles
	 */
	public List<Square> checkSpecialLocation(List<Square> newSquares) {
		List<Square> specailSquares = null;
		if (newSquares != null && newSquares.size() > 0) {
			specailSquares = new ArrayList<Square>();
			for (Square square : newSquares) {
				if (square.getSpecial() != null)
					specailSquares.add(square);
			}
		}
		return specailSquares;
	}

	/**
	 * get the square with certain location
	 * 
	 * @param location
	 *            a location to search
	 * @return the square within that location
	 */
	public Square getSquareByCoord(Coordinate location) {
		for (Square square : squares) {
			if (location.equals(square.getCoordinate())) {
				return square;
			}
		}
		return null;
	}

	/**
	 * remove some words inside the board's squares
	 * 
	 * @param newSquares
	 *            the list of squares that we want to remove words from
	 */
	public void removeWords(List<Square> newSquares) {
		if (newSquares != null && newSquares.size() > 0) {
			for (Square square : newSquares) {
				square.removeWord();
			}
		}
	}
}
