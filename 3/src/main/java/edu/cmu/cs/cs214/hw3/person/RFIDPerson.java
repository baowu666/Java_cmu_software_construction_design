package edu.cmu.cs.cs214.hw3.person;

import edu.cmu.cs.cs214.hw3.routeplanner.Itinerary;

/**
 * Person who use RFID to board, and need less time to board
 * 
 * @author zhaoru
 *
 */
public class RFIDPerson extends AbstractPerson {

	/**
	 * Constructor
	 * 
	 * @param newItinerary
	 *            the itinerary to set
	 */
	public RFIDPerson(Itinerary newItinerary) {
		super(newItinerary);
	}

	@Override
	public String getName() {
		return "RFIDPerson";
	}

	@Override
	public int getBoardingTime() {
		return super.getBoardingTime() - 1;
	}

}
