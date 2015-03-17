package edu.cmu.cs.cs214.hw3.routeplanner;

import java.util.ArrayList;
import java.util.List;

/**
 * This class tells the route information to the user
 * 
 * @author zhaoru
 *
 */
public class Itinerary {
	private String name;
	private int startWaitTime;
	private List<TripSegment> tripSegments;

	/**
	 * Constructor
	 * 
	 * @param newName
	 *            this itinerary's name
	 * @param newStartWaitTime
	 *            difference between start time and taking bus time
	 * @param newTripSegments
	 *            list of segment of this route
	 */
	public Itinerary(String newName, int newStartWaitTime,
			List<TripSegment> newTripSegments) {
		this.name = newName;
		this.startWaitTime = newStartWaitTime;
		this.tripSegments = newTripSegments;
	}

	/**
	 * 
	 * @return true if tripSegments is empty or false otherwise
	 */
	public boolean isEmpty() {
		return this.tripSegments.size() == 0;
	}

	/**
	 * 
	 * @return start time of the first segment, which is the time to take the
	 *         first bus
	 */
	public int getStartTime() {
		if (isEmpty())
			return -1;
		return this.tripSegments.get(0).getStopFrom().getTime();
	}

	/**
	 * 
	 * @return end time of the last segment, which is the time to get off the
	 *         last bus
	 */
	public int getEndTime() {
		if (isEmpty())
			return -1;
		return this.tripSegments.get(tripSegments.size() - 1).getStopTo()
				.getTime();
	}

	/**
	 * 
	 * @return total time of waiting
	 */
	public int getWaitTime() {
		if (isEmpty())
			return -1;
		int waitTimeSum = 0;
		for (TripSegment eachTripSegment : tripSegments) {
			if (eachTripSegment.getMechanism().equals("Wait")) {
				waitTimeSum += eachTripSegment.getWeightTime();
			}
		}
		return this.startWaitTime + waitTimeSum;
	}

	/**
	 * 
	 * @return start location of the first segment, which is the start stop to
	 *         take the first bus
	 */
	public Stop getStartLocation() {
		if (isEmpty())
			return null;
		return this.tripSegments.get(0).getStopFrom();
	}

	/**
	 * 
	 * @return end location of the last segment, which is the last stop to get
	 *         off the last bus
	 */
	public Stop getEndLocation() {
		if (isEmpty())
			return null;
		return this.tripSegments.get(tripSegments.size() - 1).getStopTo();
	}

	/**
	 * 
	 * @return a list of stops where the user has to change the bus
	 */
	public List<TripSegment> findChangeStop() {
		List<TripSegment> changingSeg = new ArrayList<TripSegment>();
		for (TripSegment segment : tripSegments) {
			if (!segment.getStopFrom().getBusName()
					.equals(segment.getStopTo().getBusName())) {
				changingSeg.add(segment);
			}
		}
		return changingSeg;
	}

	/**
	 * 
	 * @return the whole instruction for the user to take bus
	 */
	public String getInstructions() {
		String errorMessage = "Wrong! You cannot get there. Maybe your max wait time is to short.";
		if (isEmpty())
			return errorMessage;

		StringBuilder sb = new StringBuilder();
		List<TripSegment> changingSeg = findChangeStop();
		for (TripSegment segment : changingSeg) {
			sb.append("Take ");
			sb.append(segment.getStopFrom().getBusName());
			sb.append(" to ");
			sb.append(segment.getStopFrom().getName());
			sb.append(", wait for ");
			sb.append(segment.getWeightTime());
			sb.append(" seconds; and take ");
			sb.append(segment.getStopTo().getBusName());
			sb.append(" to continue your trip.\n");
		}

		String instructions = this.name + ": You will wait for "
				+ startWaitTime + " seconds, and take "
				+ this.tripSegments.get(0).getStopFrom().getBusName() + " at "
				+ getStartLocation().getName() + " at the time of "
				+ getStartTime() + ".\n" + sb.toString()
				+ "You will arrive at " + getEndLocation().getName()
				+ " at the time of " + getEndTime()
				+ ". The total wait time is " + getWaitTime()
				+ " seconds.\nGood luck!";
		return instructions;

	}
}
