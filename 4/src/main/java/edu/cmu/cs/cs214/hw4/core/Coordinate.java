package edu.cmu.cs.cs214.hw4.core;

/**
 * Coordinate class, each square has a coordinate
 * 
 * @author zhaoru
 *
 */
public class Coordinate {
	private int row, col;
	private static final int BORDER1 = 0;
	private static final int BORDER2 = 14;
	private static final int HASH = 31;

	/**
	 * Constructor
	 * 
	 * @param row1
	 *            row info of the coordinate
	 * @param col1
	 *            col info of the coordinate
	 */
	public Coordinate(int row1, int col1) {
		row = row1;
		col = col1;
	}

	/**
	 * get row
	 * 
	 * @return row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * get col
	 * 
	 * @return col
	 */
	public int getCol() {
		return col;
	}

	/**
	 * check if the coordinate is inside the board
	 * 
	 * @return true or false
	 */
	public boolean isInBoard() {
		if (row >= BORDER1 && row <= BORDER2 && col >= BORDER1
				&& col <= BORDER2)
			return true;
		return false;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Coordinate))
			return false;
		Coordinate c = (Coordinate) o;
		if (this.row == c.row && this.col == c.col)
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		return row * HASH + col;
	}
}
