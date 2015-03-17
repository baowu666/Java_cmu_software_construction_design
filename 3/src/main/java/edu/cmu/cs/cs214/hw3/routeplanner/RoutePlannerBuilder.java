package edu.cmu.cs.cs214.hw3.routeplanner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * The interface to build the transit graph
 * 
 * @author zhaoru
 *
 */
public interface RoutePlannerBuilder {

	/**
	 * method to build the transit graph
	 * 
	 * @param filename
	 *            the filename to load
	 * @param maxWaitLimit
	 *            the max wait time a user can wait at each stop
	 * @return a RoutePlanner class instance containing the graph information
	 * @throws IOException if input is wrong
	 * @throws FileNotFoundException if the filename is not fount
	 */
	RoutePlanner build(String filename, int maxWaitLimit) throws IOException, FileNotFoundException;
	
	/**
	 * get the bus route for all buses
	 * @return bus route for all buses
	 */
	List<List<Stop>> getBusRoute();
}
