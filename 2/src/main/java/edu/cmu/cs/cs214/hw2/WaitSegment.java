package edu.cmu.cs.cs214.hw2;

/**
 * This class indicates the trip segment which exactly a wait segment
 * 
 * @author zhaoru
 *
 */
public class WaitSegment implements TripSegment {
	private Stop from, to;
	private int weight;
	private String mechanism;

	/**
	 * Constructor of a wait segment
	 * 
	 * @param newFrom
	 *            the segment's start point
	 * @param newTo
	 *            the segment's end point
	 * @param newWeight
	 *            the time within a segment
	 */
	public WaitSegment(Stop newFrom, Stop newTo, int newWeight) {
		this.from = newFrom;
		this.to = newTo;
		this.weight = newWeight;
		this.mechanism = "Wait";
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
