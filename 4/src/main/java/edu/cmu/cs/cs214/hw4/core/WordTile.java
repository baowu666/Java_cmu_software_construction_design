package edu.cmu.cs.cs214.hw4.core;

/**
 * Word tile class
 * 
 * @author zhaoru
 *
 */
public class WordTile {
	private String letter;
	private int score;

	/**
	 * Constructor
	 * 
	 * @param newLetter
	 *            the letter of the word tile
	 * @param newScore
	 *            the score of the word tile
	 */
	public WordTile(String newLetter, int newScore) {
		letter = newLetter;
		score = newScore;
	}

	/**
	 * get the word tile's letter
	 * 
	 * @return the letter of the tile
	 */
	public String getLetter() {
		return letter;
	}

	/**
	 * get the score of the word tile
	 * 
	 * @return the score of the word tile
	 */
	public int getScore() {
		return score;
	}
}
