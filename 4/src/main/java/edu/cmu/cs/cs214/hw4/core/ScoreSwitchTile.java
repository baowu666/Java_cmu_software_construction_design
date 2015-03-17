package edu.cmu.cs.cs214.hw4.core;

/**
 * When the tile is triggered, the scores of the player who originally played
 * this tile and the player who triggered the tile will be switched. The score
 * switch occurs after the score has been updated for the current turn. A player
 * triggering her own score-switch tile has no effect.
 * 
 * @author zhaoru
 *
 */
public class ScoreSwitchTile extends SpecialTile {
	private static final int COST = 15;

	/**
	 * Constructor, set default info of the tile
	 */
	public ScoreSwitchTile() {
		super("S", COST);
	}

	@Override
	public void effect(Game game, Coordinate location) {
		/*
		 * first switch the original score of the two
		 */
		int ownerScore = getOwner().getScore();
		int currentPlayerScore = game.getCurrentPlayer().getScore();
		getOwner().setScore(currentPlayerScore);
		game.getCurrentPlayer().setScore(ownerScore);

		/*
		 * then set the player to add score to the owner of the tile, who would
		 * be added the score at the end of move() method in game class
		 */
		game.setPlayerToAddScore(getOwner());

		super.setOwner(null);
		Square square = game.getBoard().getSquareByCoord(location);
		square.removeSpecial();
	}
}
