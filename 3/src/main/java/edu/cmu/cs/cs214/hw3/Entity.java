package edu.cmu.cs.cs214.hw3;

import javax.swing.ImageIcon;

/**
 * An Entity represents a physical object in the Simulator that has a
 * {@link Location} and is (optionally) represented with an image.
 */
public interface Entity {

	/**
	 * The visualization of this Entity in the simulation. By default, you
	 * should not return an image for Person objects. However, if you would like
	 * to use the GUI to verify that Person objects are moving around as you
	 * expect, you can provide a non-null Image object.
	 *
	 *
	 * @return the image of this Entity or null if this entity should not be
	 *         drawn
	 */
	ImageIcon getImage();

	/**
	 * Gets a String that serves as a description of this entity. Do not use
	 * this method in lieu of using instanceof (which you should avoid using,
	 * too).
	 *
	 * @return the name of this item
	 */
	String getName();

	/**
	 * Gets the {@link Location} of this Entity in the World.
	 *
	 * @return the location in the world
	 */
	Location getLocation();

	/**
	 * Sets the {@link Location} of this Entity in the World.
	 * 
	 * @param newLocation
	 *            the location to set as a parameter
	 */
	void setLocation(Location newLocation);

	/**
	 * get Start Location of an entity
	 * 
	 * @return start location
	 */
	Location getStartLocation();

	/**
	 * get Destination of an entity
	 * 
	 * @return Destination
	 */
	Location getDestination();

	/**
	 * get capacity of an entity
	 * 
	 * @return capacity of an entity
	 */
	int getCapacity();

	/**
	 * get Luggage Space of an entity
	 * 
	 * @return Luggage Space of an entity
	 */
	int getLuggageSpace();

	/**
	 * get Disabled Space of an entity
	 * 
	 * @return Disabled Space of an entity
	 */
	int getDisabledSpace();

	/**
	 * set Total Delay Time of an entity
	 * 
	 * @param reachingTime
	 *            the time the entity is reaching
	 */
	void setTotalDelayTime(int reachingTime);

	/**
	 * get Total Delay Time of an entity
	 * 
	 * @return Total Delay Time of an entity
	 */
	int getTotalDelayTime();

	/**
	 * get Schedule Time of an entity
	 * 
	 * @return Schedule Time
	 */
	int getScheduleTime();

}
