package edu.cmu.cs.cs214.hw3.routeplanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * This class is to find the bus stop and compute the fastest route of the trip
 * 
 * @author zhaoru
 *
 */
public class RoutePlannerClass implements RoutePlanner {
	private Graph busGraph;
	private int maxWaitLimit;

	/**
	 * Constructor
	 * 
	 * @param newBusGraph
	 *            the graph of all buses information
	 * @param newMaxWaitLimit
	 *            the max wait time a user can wait at each stop
	 */
	public RoutePlannerClass(Graph newBusGraph, int newMaxWaitLimit) {
		this.busGraph = newBusGraph;
		this.maxWaitLimit = newMaxWaitLimit;
	}

	@Override
	public List<Stop> findStopsBySubstring(String search) {
		List<Stop> result = new ArrayList<Stop>();
		int searchLen = search.length();
		Stop stop;
		String stopStr;
		boolean isInside = false;
		for (int i = 0; i < busGraph.getVertexList().size(); i++) {
			stop = busGraph.getVertexList().get(i);
			stopStr = stop.getName();
			if ((stopStr.length() >= searchLen)
					&& stopStr.substring(0, searchLen).equals(search)) {
				for (int j = 0; j < result.size(); j++) {
					if (result.get(j).getName().equals(stopStr)) {
						isInside = true;
						break;
					}
				}
				if (isInside == false)
					result.add(stop); // this stop can be the vertex of any time
										// but the certain name
				isInside = false;
			}
		}
		return result;
	}

	@Override
	public Itinerary computeRoute(String src, String dest, int time) {
		List<Itinerary> itinerary = new ArrayList<Itinerary>();
		List<List<TripSegment>> tripSegments = new ArrayList<List<TripSegment>>();
		Stop start = null; // initialize
		int startWaitTime = Integer.MAX_VALUE;
		List<List<Stop>> routeStops = new ArrayList<List<Stop>>();
		
		/*
		 * Test branch failed. Because I iterate this graph to find first vertex
		 * that meets the requirement, and then break. If I do not break, the
		 * later some vertex may override the first one. So in this way, the
		 * whole iteration must not complete(except for worst case). So the
		 * branch coverage requirement should not meet.
		 */
		for (Stop startTemp : busGraph.getVertexList()) {
			int startWaitTimeTemp = startTemp.getTime() - time;
			if (src.equals(startTemp.getName())
					&& (startWaitTimeTemp >= 0)) {
				start = startTemp;
				startWaitTime = startWaitTimeTemp;
				break; // graph vertexes are sorted, the first larger time than
						// src time would be the start time
			}
		}

		if (start == null) return null;
		
		computePaths(start);

		int routeNum = 0;
		for (int j = 0; j < busGraph.getVertexList().size(); j++) {
			if (busGraph.getVertexList().get(j).getName()
					.equals(dest)) {
				routeStops.add(getShortestPathTo(busGraph.getVertexList()
						.get(j)));

				if (routeStops.get(routeNum).size() > 1) {
					List<TripSegment> tripSegmentTemp = new ArrayList<TripSegment>();
					for (int i = 0; i < routeStops.get(routeNum).size() - 1; i++) {
						Stop stopI = routeStops.get(routeNum).get(i);
						Stop stopIPlus = routeStops.get(routeNum).get(i + 1);
						int timeDifference = stopIPlus.getTime()
								- stopI.getTime();
						if (stopI.getBusName().equals(stopIPlus.getBusName())) {
							tripSegmentTemp.add(new BusSegment(stopI,
									stopIPlus, timeDifference));
						} else {
							tripSegmentTemp.add(new WaitSegment(stopI,
									stopIPlus, timeDifference));
						}
					}
					tripSegments.add(tripSegmentTemp);
					itinerary.add(new Itinerary("Your Itinerary",
							startWaitTime, tripSegments.get(routeNum)));
				} else {
					tripSegments.add(null);
				}
				routeNum++;
			}
		}

		Itinerary theItinerary = Collections.min(itinerary,
				new Comparator<Itinerary>() {
					public int compare(Itinerary x, Itinerary y) {
						return (x.getEndTime() - x.getStartTime())
								- (y.getEndTime() - y.getStartTime());
					}
				});

		return theItinerary;
	}

	/**
	 * helper method to calculate the fast path given the start stop using
	 * Dijstra Algorithm
	 * 
	 * @param source
	 *            start stop
	 */
	private void computePaths(Stop source) {
		Queue<Stop> vertexQueue = new PriorityQueue<Stop>();
		source.setMinDist(0);
		vertexQueue.add(source);
		while (!vertexQueue.isEmpty()) {
			Stop u = vertexQueue.poll();

			// Visit each edge exiting u
			for (Stop e : busGraph.getNextStop(u)) {
				int weight = e.getTime() - u.getTime();
				int distanceThroughU = u.getMinDist() + weight;
				if (distanceThroughU < e.getMinDist()) {
					vertexQueue.remove(e);
					e.setMinDist(distanceThroughU);
					e.setPrevious(u);
					vertexQueue.add(e);
				}
			}
		}
	}

	/**
	 * helper method to get a list of stops which is the fast route from the
	 * start stop to target stop
	 * 
	 * @param target
	 *            tart stop
	 * @return a list of stops which is the fast route from the start stop to
	 *         target stop
	 */
	private List<Stop> getShortestPathTo(Stop target) {
		List<Stop> path = new ArrayList<Stop>();
		for (Stop vertex = target; vertex != null; vertex = vertex
				.getPrevious())
			path.add(vertex);
		Collections.reverse(path);
		return path;
	}
}
