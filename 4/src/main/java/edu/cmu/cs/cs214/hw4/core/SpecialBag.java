package edu.cmu.cs.cs214.hw4.core;

/**
 * The special bag class can sell special tiles to a player and check if he can
 * afford it
 * 
 * @author zhaoru
 *
 */
public class SpecialBag {
	private static final int MAX_SPECIAL_RACK = 10;

	/**
	 * get a special tile and decrease money
	 * 
	 * @param money
	 *            the money that the player has
	 * @param num
	 *            number of the special tiles the player is having
	 * @param special
	 *            the special tile that the player want to buy
	 * @return the special tile or null of the money is not enough
	 */
	public SpecialTile getSpecialTile(int money, int num, SpecialTile special) {
		if (money >= special.getCost() && num < MAX_SPECIAL_RACK)
			return special;
		return null;
	}

}
