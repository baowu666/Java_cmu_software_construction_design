package edu.cmu.cs.cs214.hw4.core;

/**
 * The abstract class of all special tiles
 * 
 * @author zhaoru
 *
 */
public abstract class SpecialTile {
	private String name;
	private int cost;
	private Player owner;

	/**
	 * Constructor, set the name cost, set owner to null as default
	 * 
	 * @param newName
	 *            name to set
	 * @param newCost
	 *            cost to set
	 */
	public SpecialTile(String newName, int newCost) {
		this.name = newName;
		this.cost = newCost;
		this.owner = null;
	}

	/**
	 * get name of the special tile
	 * 
	 * @return name of the special tile
	 */
	public String getName() {
		return name;
	}

	/**
	 * get the cost of the special tile
	 * 
	 * @return the cost of the special tile
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * get the owner of the special tile
	 * 
	 * @return the owner of the special tile
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * set the owner of the special tile
	 * 
	 * @param owner1
	 *            the owner to set
	 */
	public void setOwner(Player owner1) {
		this.owner = owner1;
	}

	/**
	 * the effect that certain special may differ with each other. It needs the
	 * game instance so that if can affect board, game, or something else. It
	 * also needs the location where it affect.
	 * 
	 * @param game
	 *            the game instance to parse into
	 * @param location
	 *            the location where the special tile affects
	 */
	public abstract void effect(Game game, Coordinate location); // remove
																	// special
																	// at last

}
