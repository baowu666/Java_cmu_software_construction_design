package edu.cmu.cs.cs214.hw3.person;

import edu.cmu.cs.cs214.hw3.routeplanner.Itinerary;

/**
 * Person with bike, who needs more time to board
 * 
 * @author zhaoru
 *
 */
public class BikePerson extends AbstractPerson {
	private static final int BIKE_SPEED = 3;

	/**
	 * Constructor
	 * 
	 * @param newItinerary
	 *            the itinerary to set
	 */
	public BikePerson(Itinerary newItinerary) {
		super(newItinerary);
	}

	@Override
	public int getBoardingTime() {
		return super.getBoardingTime() + BIKE_SPEED;
	}

	@Override
	public String getName() {
		return "BikePerson";
	}

}
