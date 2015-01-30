package edu.cmu.cs.cs214.hw2;

import java.io.FileNotFoundException;
import java.io.IOException;

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
}
