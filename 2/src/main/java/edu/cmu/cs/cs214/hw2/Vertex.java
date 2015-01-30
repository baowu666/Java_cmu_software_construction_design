package edu.cmu.cs.cs214.hw2;

/**
 * This class models a vertex of the graph. Each vertex has a name and its
 * associated getters and setters.
 * 
 * @author zhaoru
 *
 */
public class Vertex implements Stop, Comparable<Stop> { // Test cannot reach this line. Piazza said we can ignore it.
	private String busName;
	private String stopName;
	private double latitude;
	private double longitude;
	private int time;
	private Stop previous; // used for Dijkstra compute path
	private int minDistance = Integer.MAX_VALUE; // used for Dijkstra compute
													// path

	// weight is the time difference between this stop and next stop

	/**
	 * Constructor of the vertex
	 * 
	 * @param newBusName
	 *            the name of the bus
	 * @param newStopName
	 *            name of the stop
	 * @param newLatitude
	 *            latitude of the stop
	 * @param newLongitude
	 *            longitude of the stop
	 * @param newTime
	 *            time the bus arrives at the stop
	 */
	public Vertex(String newBusName, String newStopName, double newLatitude,
			double newLongitude, int newTime) {
		this.busName = newBusName;
		this.stopName = newStopName;
		this.latitude = newLatitude;
		this.longitude = newLongitude;
		this.time = newTime;
	}

	@Override
	public String getName() {
		return this.stopName;
	}

	@Override
	public double getLatitude() {
		return this.latitude;
	}

	@Override
	public double getLongitude() {
		return this.longitude;
	}

	@Override
	public String getBusName() {
		return this.busName;
	}

	@Override
	public int getTime() {
		return this.time;
	}

	@Override
	public void setPrevious(Stop newPrevious) {
		this.previous = newPrevious;
	}

	@Override
	public Stop getPrevious() {
		return this.previous;
	}

	@Override
	public void setMinDist(int newMinDistance) {
		this.minDistance = newMinDistance;
	}

	@Override
	public int getMinDist() {
		return this.minDistance;
	}

	@Override
	public int compareTo(Stop other) {
		return Integer.compare(minDistance, other.getMinDist());
	}
}
