package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Special rack can hold and modify a player's special tiles
 * 
 * @author zhaoru
 *
 */
public class SpecialRack {
	private List<SpecialTile> specialTiles;

	/**
	 * Constructor
	 */
	public SpecialRack() {
		specialTiles = new ArrayList<SpecialTile>();
	}

	/**
	 * get all special tiles
	 * 
	 * @return all special tiles
	 */
	public List<SpecialTile> getSpecialTiles() {
		return specialTiles;
	}

	/**
	 * get number of the special tiles the player has
	 * 
	 * @return the number of the special tiles
	 */
	public int getNum() {
		return specialTiles.size();
	}

	/**
	 * remove one special tile from the rack
	 * 
	 * @param special
	 *            the special tile to remove
	 */
	public void removeRackSpecial(SpecialTile special) {
		if (special != null && specialTiles.size() > 0)
			specialTiles.remove(special);
	}

	/**
	 * add one special tile to the rack
	 * 
	 * @param special
	 *            the special tile to add
	 */
	public void addRackSpecial(SpecialTile special) {
		if (special != null)
			specialTiles.add(special);
	}
}
