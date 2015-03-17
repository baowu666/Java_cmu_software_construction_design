package edu.cmu.cs.cs214.hw3.person;

import edu.cmu.cs.cs214.hw3.routeplanner.Itinerary;

/**
 * Person with more luggages, which takes more time to board and more room for
 * luggage
 * 
 * @author zhaoru
 *
 */
public class LuggagesPerson extends AbstractPerson {
	private static final int LUGG_PERSON_SPEED = 3;

	/**
	 * Constructor
	 * 
	 * @param newItinerary
	 *            the itinerary to set
	 */
	public LuggagesPerson(Itinerary newItinerary) {
		super(newItinerary);
	}

	@Override
	public int getBoardingTime() {
		return super.getBoardingTime() + LUGG_PERSON_SPEED;
	}

	@Override
	public String getName() {
		return "LuggagesPerson";
	}

	@Override
	public int getLuggageSpace() {
		return super.getLuggageSpace() + 2;
	}

}
