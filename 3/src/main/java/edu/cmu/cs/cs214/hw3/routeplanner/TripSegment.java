package edu.cmu.cs.cs214.hw3.routeplanner;

/**
 * The interface to set and get the information of a trip segment
 * 
 * @author zhaoru
 *
 */
public interface TripSegment {

	/**
	 * 
	 * @return the start stop of the segment
	 */
	Stop getStopFrom();

	/**
	 * 
	 * @return the end stop of the segment
	 */
	Stop getStopTo();

	/**
	 * 
	 * @return the weight time of the segment
	 */
	int getWeightTime();

	/**
	 * 
	 * @return the mechanism of the segment, bus or wait
	 */
	String getMechanism();
}
