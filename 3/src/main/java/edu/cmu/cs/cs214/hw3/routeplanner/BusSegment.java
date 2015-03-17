package edu.cmu.cs.cs214.hw3.routeplanner;

/**
 * This class indicates the trip segment which exactly a bus segment
 * 
 * @author zhaoru
 *
 */
public class BusSegment implements TripSegment {
	private Stop from, to;
	private int weight;
	private String mechanism;

	/**
	 * Constructor of a bus segment
	 * 
	 * @param fromStop
	 *            the segment's start point
	 * @param toStop
	 *            the segment's end point
	 * @param weightStop
	 *            the time within a segment
	 */
	public BusSegment(Stop fromStop, Stop toStop, int weightStop) {
		this.from = fromStop;
		this.to = toStop;
		this.weight = weightStop;
		this.mechanism = "Bus";
	}

	@Override
	public Stop getStopFrom() {
		return this.from;
	}

	@Override
	public Stop getStopTo() {
		return this.to;
	}

	@Override
	public int getWeightTime() {
		return this.weight;
	}

	@Override
	public String getMechanism() {
		return this.mechanism;
	}

}
