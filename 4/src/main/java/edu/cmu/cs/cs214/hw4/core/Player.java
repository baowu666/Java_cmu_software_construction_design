package edu.cmu.cs.cs214.hw4.core;

/**
 * Player class, which has some basic information
 * 
 * @author zhaoru
 *
 */
public class Player {
	private String name;
	private int score;
	private boolean inTurn;
	private WordRack wordRack;
	private SpecialRack specialRack;

	/**
	 * Constructor, set the attributed to default
	 */
	public Player() {
		name = null;
		score = 0;
		inTurn = false;
		wordRack = new WordRack();
		specialRack = new SpecialRack();
	}

	/**
	 * get name of player, which is the number string of the player
	 * 
	 * @return the string number
	 */
	public String getName() {
		return name;
	}

	/**
	 * set player's name
	 * 
	 * @param name1
	 *            the name to set
	 */
	public void setName(String name1) {
		this.name = name1;
	}

	/**
	 * get the score of the player
	 * 
	 * @return the score of the player
	 */
	public int getScore() {
		return score;
	}

	/**
	 * set the score the player
	 * 
	 * @param score1
	 *            the score to set
	 */
	public void setScore(int score1) {
		this.score = score1;
	}

	/**
	 * get if the player is on turn
	 * 
	 * @return true or false
	 */
	public boolean isTurn() {
		return inTurn;
	}

	/**
	 * set the turn to the player as true or false
	 * 
	 * @param inTurn1
	 *            the turn to set
	 */
	public void setTurn(boolean inTurn1) {
		this.inTurn = inTurn1;
	}

	/**
	 * get player's word rack
	 * 
	 * @return the word rack of the player
	 */
	public WordRack getWordRack() {
		return wordRack;
	}

	/**
	 * get player's special rack
	 * 
	 * @return the special rack of the player
	 */
	public SpecialRack getSpecialRack() {
		return specialRack;
	}

	/**
	 * add some score to the player's score
	 * 
	 * @param newScore
	 *            new score to add
	 */
	public void addScore(int newScore) {
		score += newScore;
	}

}
