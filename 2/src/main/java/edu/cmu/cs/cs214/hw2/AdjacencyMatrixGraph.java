package edu.cmu.cs.cs214.hw2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class implements the interface of graph, which uses the Adjacency Matrix
 * Algorithm to set connections inside the graph
 * 
 * @author zhaoru
 *
 */
public class AdjacencyMatrixGraph implements Graph {
	private int size;
	private List<Stop> vertexes = new ArrayList<Stop>();
	private HashMap<Stop, List<Integer>> vertexesMap = new HashMap<Stop, List<Integer>>();

	/**
	 * Constructor
	 * 
	 * @param newSize
	 *            size of the matrix
	 */
	public AdjacencyMatrixGraph(int newSize) {
		this.size = newSize;
	}

	@Override
	public List<Stop> getVertexList() {
		List<Stop> list = new ArrayList<Stop>();
		for (Stop stop : vertexes) {
			list.add(stop);
		}
		return list;
	}

	@Override
	public Stop getSingleVertex(int index) {
		return getVertexList().get(index);
	}

	@Override
	public void addVertex(Stop newVertex) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			list.add(0);
		}
		vertexes.add(newVertex);
		vertexesMap.put(newVertex, list);
	}

	@Override
	public void addEdge(Stop newVertex1, Stop newVertex2) {
		List<Integer> list = vertexesMap.get(newVertex1);
		int index = vertexes.indexOf(newVertex2);
		list.set(index, 1);
		vertexesMap.put(newVertex1, list);
	}

	@Override
	public List<Stop> getNextStop(Stop vertex) {
		List<Stop> list = new ArrayList<Stop>();
		List<Integer> listInt = vertexesMap.get(vertex);
		for (int i = 0; i < size; i++) {
			if (listInt.get(i).equals(1)) {
				list.add(vertexes.get(i));
			}
		}
		return list;
	}

}