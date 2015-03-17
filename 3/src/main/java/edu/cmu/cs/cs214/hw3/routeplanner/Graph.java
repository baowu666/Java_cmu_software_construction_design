package edu.cmu.cs.cs214.hw3.routeplanner;

import java.util.List;

/**
 * This interface is to provide method to build a connecting graph
 * 
 * @author zhaoru
 *
 */
public interface Graph {

	/**
	 * 
	 * @return a list of all stops inside the graph
	 */
	List<Stop> getVertexList();

	/**
	 * 
	 * @param index
	 *            index of the stop wanted
	 * @return the stop of certain index
	 */
	Stop getSingleVertex(int index);

	/**
	 * Method to indicate a vertex inside the graph.
	 * 
	 * @param newVertex
	 *            Argument of a vertex class to add vertex.
	 */
	void addVertex(Stop newVertex);

	/**
	 * Method to add a friendship of two persons.
	 * 
	 * @param newVertex1
	 *            Argument that the edge is from.
	 * @param newVertex2
	 *            Argument that the edge is to.
	 * 
	 */
	void addEdge(Stop newVertex1, Stop newVertex2);

	/**
	 * 
	 * @param vertex
	 *            the stop of which we want to get the connected stops
	 * @return a list of connected stops of the certain stop
	 */
	List<Stop> getNextStop(Stop vertex);
}
