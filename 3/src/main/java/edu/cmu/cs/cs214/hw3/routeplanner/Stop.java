package edu.cmu.cs.cs214.hw3.routeplanner;

import edu.cmu.cs.cs214.hw3.Location;

/**
 * The interface to set and get all information of a single stop
 * 
 * @author zhaoru
 *
 */
public interface Stop {

	/**
	 * 
	 * @return the name of the stop
	 */
	String getName();

	/**
	 * 
	 * @return latitude of the stop
	 */
	double getLatitude();

	/**
	 * 
	 * @return longitude of the stop
	 */
	double getLongitude();

	/**
	 * get the location of the stop
	 * 
	 * @return location of the stop
	 */
	Location getLocation();

	/**
	 * 
	 * @return the name of the bus
	 */
	String getBusName();

	/**
	 * 
	 * @return the time of the stop that the bus arrives at
	 */
	int getTime();

	/**
	 * 
	 * @param previous
	 *            the previous stop which the fastest route is linking, all the
	 *            way to the starting stop
	 */
	void setPrevious(Stop previous);

	/**
	 * 
	 * @return the previous stop which the fastest route is linking, all the way
	 *         to the starting stop
	 */
	Stop getPrevious();

	/**
	 * 
	 * @param minDistance
	 *            the minimum time between this stop and starting stop
	 */
	void setMinDist(int minDistance);

	/**
	 * 
	 * @return the minimum time between this stop and starting stop
	 */
	int getMinDist();

	/**
	 * 
	 * @param other
	 *            the other stop to compare
	 * @return integer value indicating which minDistance is bigger
	 */
	int compareTo(Stop other);

	/**
	 * set arrive time for a bus's stop
	 * 
	 * @param newTime
	 *            the time to set
	 */
	void setArriveTime(int newTime);
}