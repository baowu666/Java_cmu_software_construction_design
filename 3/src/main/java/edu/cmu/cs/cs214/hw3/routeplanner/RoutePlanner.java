package edu.cmu.cs.cs214.hw3.routeplanner;

import java.util.List;

/**
 * The interface to find the bus stop and compute the fastest route of the trip
 * 
 * @author zhaoru
 *
 */
public interface RoutePlanner {

	/**
	 * method to find stops by substring
	 * 
	 * @param search
	 *            the first some string with which the user want to find a stop
	 * @return a list of stops containing the searching string
	 */
	List<Stop> findStopsBySubstring(String search);

	/**
	 * method to compute the fastest itinerary
	 * 
	 * @param src
	 *            starting stop
	 * @param dest
	 *            destination stop
	 * @param time
	 *            starting time
	 * @return the fatest itinerary
	 */
	Itinerary computeRoute(String src, String dest, int time);
}
