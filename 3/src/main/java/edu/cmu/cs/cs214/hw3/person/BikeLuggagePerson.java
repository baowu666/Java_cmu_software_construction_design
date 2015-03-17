package edu.cmu.cs.cs214.hw3.person;

import edu.cmu.cs.cs214.hw3.routeplanner.Itinerary;

/**
 * Person with bike and luggage
 * 
 * @author zhaoru
 *
 */
public class BikeLuggagePerson extends BikePerson {
	private Person person;

	/**
	 * Constructor
	 * 
	 * @param newItinerary
	 *            the itinerary to set
	 */
	public BikeLuggagePerson(Itinerary newItinerary) {
		super(newItinerary);
		person = new LuggagePerson(newItinerary);
	}

	@Override
	public int getBoardingTime() {
		return super.getBoardingTime() + person.getBoardingTime();
	}

	@Override
	public String getName() {
		return "BikeLuggagePerson";
	}

	@Override
	public int getCapacity() {
		return super.getCapacity() + person.getCapacity();
	}

	@Override
	public int getLuggageSpace() {
		return super.getLuggageSpace() + person.getLuggageSpace();
	}

}
