package edu.cmu.cs.cs214.hw3.person;

import edu.cmu.cs.cs214.hw3.routeplanner.Itinerary;

/**
 * Person with luggage, who needs more time to board and space for luggage
 * 
 * @author zhaoru
 *
 */
public class LuggagePerson extends AbstractPerson {

	/**
	 * Constructor
	 * 
	 * @param newItinerary
	 *            the itinerary to set
	 */
	public LuggagePerson(Itinerary newItinerary) {
		super(newItinerary);
	}

	@Override
	public int getBoardingTime() {
		return super.getBoardingTime() + 2;
	}

	@Override
	public String getName() {
		return "LuggagePerson";
	}

	@Override
	public int getLuggageSpace() {
		return super.getLuggageSpace() + 2;
	}

}
