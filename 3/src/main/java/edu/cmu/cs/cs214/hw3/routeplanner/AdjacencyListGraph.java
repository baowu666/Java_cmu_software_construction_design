package edu.cmu.cs.cs214.hw3.routeplanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class implements the interface of graph, which uses the Adjacency List
 * Algorithm to set connections inside the graph
 * 
 * @author zhaoru
 *
 */
public class AdjacencyListGraph implements Graph {
	private List<Stop> vertexes = new ArrayList<Stop>();
	private HashMap<Stop, List<Stop>> vertexesMap = new HashMap<Stop, List<Stop>>();

	@Override
	public List<Stop> getVertexList() {
		return this.vertexes;
	}

	@Override
	public Stop getSingleVertex(int index) {
		return this.vertexes.get(index);
	}

	@Override
	public void addVertex(Stop newVertex) {
		if (vertexes.contains(newVertex)) {
			System.out.println("This vertex has already exist!"); /*
																 * avoid same
																 * name
																 */
			return;
		}
		vertexes.add(newVertex);
		vertexesMap.put(newVertex, new ArrayList<Stop>());
	}

	@Override
	public void addEdge(Stop newVertex1, Stop newVertex2) {
		List<Stop> stops = vertexesMap.get(newVertex1);
		stops.add(newVertex2);
		vertexesMap.put(newVertex1, stops);
	}

	@Override
	public List<Stop> getNextStop(Stop vertex) {
		return this.vertexesMap.get(vertex);
	}
}
