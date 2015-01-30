package edu.cmu.cs.cs214.hw2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The class to build the transit graph
 * 
 * @author zhaoru
 *
 */
public class RoutePlannerBuilderClass implements RoutePlannerBuilder {
	private String filename;
	private int maxWaitLimit;
	private final int commaNumForBus = 1;
	private final int commaNumForStop = 3;
	private final int indexBusInfo = 0;
	private final int indexNumStopsInfo = 1;
	private final int indexStopInfo = 0;
	private final int indexLatitudeInfo = 1;
	private final int indexLongitudeInfo = 2;
	private final int indexTimeInfo = 3;

	/**
	 * Constructor
	 * 
	 * @param newFilename
	 *            the file name to load
	 * @param newMaxWaitLimit
	 *            the max wait time a user can wait at each stop
	 */
	public RoutePlannerBuilderClass(String newFilename, int newMaxWaitLimit) {
		this.filename = newFilename;
		this.maxWaitLimit = newMaxWaitLimit;
	}

	@Override
	public RoutePlanner build(String aMilename, int aMaxWaitLimit)
			throws IOException, FileNotFoundException {
		/*
		 * If using AdjacencyMatrixGraph here, a constructor knowing size of all
		 * vertexes should be given
		 */
		Graph busGraph = new AdjacencyListGraph();
		String busName = null;
		int numStop = 0;
		List<Integer> numStops = new ArrayList<Integer>();
		/*
		 * create all vertexes
		 */
		@SuppressWarnings("resource")
		BufferedReader bf = new BufferedReader(new FileReader(filename));
		String line;
		while ((line = bf.readLine()) != null) {
			String[] words = line.split(",");
			if ((words.length - 1) == commaNumForBus) {
				busName = words[indexBusInfo];
				numStop = Integer.parseInt(words[indexNumStopsInfo]);
				numStops.add(numStop);
			} else { // if ((words.length - 1) == commaNumForStop)
				busGraph.addVertex(new Vertex(busName, words[indexStopInfo],
						Double.parseDouble(words[indexLatitudeInfo]), Double
								.parseDouble(words[indexLongitudeInfo]),
						Integer.parseInt(words[indexTimeInfo])));
			}
		}
		bf.close();

		/*
		 * create edges within one bus
		 */
		int count = 0;
		for (int eachBusStop : numStops) {
			for (int i = count; i < eachBusStop + count - 1; i++) {
				/*
				 * The condition "Stop vertexI = busGraph.getSingleVertex(i);
				 * Stop vertexIPlus = busGraph.getSingleVertex(i + 1); if
				 * (vertexIPlus.getTime() - vertexI.getTime() >= 0)" should meet
				 * because default order is time ascending
				 */
				busGraph.addEdge(busGraph.getSingleVertex(i),
						busGraph.getSingleVertex(i + 1));
			}
			count += eachBusStop;
		}

		/*
		 * sort vertexes by stop name and time
		 */
		Collections.sort(busGraph.getVertexList(), new Comparator<Stop>() {
			public int compare(Stop x, Stop y) {
				if (x.getName().equals(y.getName()))
					return x.getTime() - y.getTime();
				return x.getName().compareTo(y.getName());
			}
		});

		/*
		 * create edges with same stop names
		 */
		for (int j = 0; j < busGraph.getVertexList().size() - 1; j++) {
			Stop vertexJ = busGraph.getVertexList().get(j);
			Stop vertexJPlus = busGraph.getVertexList().get(j + 1);
			if (vertexJ.getName().equals(vertexJPlus.getName())
					&& (vertexJPlus.getTime() - vertexJ.getTime() <= aMaxWaitLimit)) {
				busGraph.addEdge(vertexJ, vertexJPlus);
			}
		}

		RoutePlanner routePlanner = new RoutePlannerClass(busGraph,
				aMaxWaitLimit);
		return routePlanner;
	}

}
