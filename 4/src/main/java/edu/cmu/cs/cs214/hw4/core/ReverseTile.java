package edu.cmu.cs.cs214.hw4.core;

/**
 * The reverse tile class. The turn ends as usual, but after this tile is
 * activated play continues in the reverse of the previous order.
 * 
 * @author zhaoru
 *
 */
public class ReverseTile extends SpecialTile {
	private static final int COST = 10;

	/**
	 * Constructor, set default info of the tile
	 */
	public ReverseTile() {
		super("R", COST);
	}

	@Override
	public void effect(Game game, Coordinate location) {
		game.reverseOrder();
		super.setOwner(null);
		Square square = game.getBoard().getSquareByCoord(location);
		square.removeSpecial();
	}

}
