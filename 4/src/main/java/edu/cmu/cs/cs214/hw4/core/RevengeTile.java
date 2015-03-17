package edu.cmu.cs.cs214.hw4.core;

/**
 * The revenge tile class. remove the current player's all special tiles that
 * are already used on board
 * 
 * @author zhaoru
 *
 */
public class RevengeTile extends SpecialTile {
	private static final int COST = 20;

	/**
	 * Constructor, set default info of the tile
	 */
	public RevengeTile() {
		super("V", COST);
	}

	@Override
	public void effect(Game game, Coordinate location) {
		Player player = game.getCurrentPlayer();
		for (Square square : game.getBoard().getSquares()) {
			if (square.getSpecial() != null
					&& player == square.getSpecial().getOwner()) {
				square.getSpecial().setOwner(null);
				square.removeSpecial();
			}
		}
		super.setOwner(null);
		Square square = game.getBoard().getSquareByCoord(location);
		square.removeSpecial();
	}

}
