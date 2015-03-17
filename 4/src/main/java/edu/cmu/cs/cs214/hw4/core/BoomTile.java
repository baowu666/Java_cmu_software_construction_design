package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.List;

/**
 * The boom tile class. All tiles in a 3-tile radius on the board are removed
 * from the board. Only surviving tiles are scored for this round.
 * 
 * @author zhaoru
 *
 */
public class BoomTile extends SpecialTile {
	private static final int COST = 15;
	private static final int MAX_BORDER = 15;
	private static final int BOOM_RADIUS = 3;

	/**
	 * Constructor, set default info of the tile
	 */
	public BoomTile() {
		super("B", COST);
	}

	@Override
	public void effect(Game game, Coordinate location) {
		int num, row = location.getRow(), col = location.getCol();
		List<Square> removeSquares = new ArrayList<Square>();
		Square square = game.getBoard().getSquareByCoord(location);
		for (int i = row - BOOM_RADIUS; i < row + BOOM_RADIUS + 1; i++) {
			for (int j = col - BOOM_RADIUS; j < col + BOOM_RADIUS + 1; j++) {
				// only need the squares that are in board
				if (new Coordinate(i, j).isInBoard()) {
					num = i * MAX_BORDER + j;
					Square removeSquare = game.getBoard().getSquares().get(num);
					removeSquares.add(removeSquare);
				}
			}
		}
		// remove new squares' words from current move
		game.getCurrentMove().boomedTiles(removeSquares);

		// remove old squares' words from board
		game.getBoard().removeWords(removeSquares);

		super.setOwner(null);
		square.removeSpecial();
	}
}
