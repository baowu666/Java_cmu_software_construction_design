package edu.cmu.cs.cs214.hw4.core;

/**
 * Square class, which hold information and location and tiles
 * 
 * @author zhaoru
 *
 */
public class Square {
	private Coordinate coordinate;
	private WordTile wordTile;
	private SpecialTile specialTile;
	private int letterBonus, wordBonus;

	/**
	 * Constructor, set the bonus number to 1 as default
	 * 
	 * @param i
	 *            row location
	 * @param j
	 *            col location
	 */
	public Square(int i, int j) {
		coordinate = new Coordinate(i, j);
		wordTile = null;
		specialTile = null;
		letterBonus = 1;
		wordBonus = 1;
	}

	/**
	 * get the location of the square
	 * 
	 * @return the location
	 */
	public Coordinate getCoordinate() {
		return coordinate;
	}

	/**
	 * get letter bonus multiplier
	 * 
	 * @return letter bonus multiplier
	 */
	public int getLetterBonus() {
		return letterBonus;
	}

	/**
	 * set letter bonus multiplier
	 * 
	 * @param letterBonus1
	 *            letter bonus multiplier to set
	 */
	public void setLetterBonus(int letterBonus1) {
		letterBonus = letterBonus1;
	}

	/**
	 * get word bonus multiplier
	 * 
	 * @return word bonus multiplier
	 */
	public int getWordBonus() {
		return wordBonus;
	}

	/**
	 * set word bonus multiplier
	 * 
	 * @param wordBonus1
	 *            word bonus multiplier
	 */
	public void setWordBonus(int wordBonus1) {
		wordBonus = wordBonus1;
	}

	/**
	 * get word tile of the square
	 * 
	 * @return the word tile of the square
	 */
	public WordTile getWord() {
		return wordTile;
	}

	/**
	 * set word tile of the square
	 * 
	 * @param wordTile1
	 *            word tile of the square to set
	 */
	public void setWord(WordTile wordTile1) {
		this.wordTile = wordTile1;
	}

	/**
	 * remove the word tile from the word tile
	 */
	public void removeWord() {
		wordTile = null;
	}

	/**
	 * get the special tile form the square
	 * 
	 * @return the special tile form the square
	 */
	public SpecialTile getSpecial() {
		return specialTile;
	}

	/**
	 * set the special tile form the square
	 * 
	 * @param specialTile1
	 *            the special tile form the square to set
	 */
	public void setSpecial(SpecialTile specialTile1) {
		this.specialTile = specialTile1;
	}

	/**
	 * remove the special tile from the word tile
	 */
	public void removeSpecial() {
		specialTile = null;
	}
}
