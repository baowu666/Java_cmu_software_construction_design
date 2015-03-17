package edu.cmu.cs.cs214.hw4.core;

/**
 * The negative tile class. The word that activated this tile is scored
 * negatively for the player who activated the tile; i.e., the player loses
 * (rather than gains) the points for the played word.
 * 
 * @author zhaoru
 *
 */
public class NegativeTile extends SpecialTile {
	private static final int COST = 25;

	/**
	 * Constructor, set default info of the tile
	 */
	public NegativeTile() {
		super("N", COST);
	}

	@Override
	public void effect(Game game, Coordinate location) {
		game.getCurrentMove().negativeTiles();
		super.setOwner(null);
		Square square = game.getBoard().getSquareByCoord(location);
		square.removeSpecial();
	}
}
