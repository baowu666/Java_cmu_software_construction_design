package edu.cmu.cs.cs214.hw3.person;

import edu.cmu.cs.cs214.hw3.routeplanner.Itinerary;

/**
 * Person with wheelchair and luggage
 * 
 * @author zhaoru
 *
 */
public class WheelchairLuggagePerson extends WheelchairPerson {
	private Person person;

	/**
	 * Constructor
	 * 
	 * @param newItinerary
	 *            the itinerary to set
	 */
	public WheelchairLuggagePerson(Itinerary newItinerary) {
		super(newItinerary);
		person = new LuggagePerson(newItinerary);
	}

	@Override
	public int getBoardingTime() {
		return super.getBoardingTime() + person.getBoardingTime();
	}

	@Override
	public String getName() {
		return "WheelchairLuggagePerson";
	}

	@Override
	public int getCapacity() {
		return 0;
	}

	@Override
	public int getLuggageSpace() {
		return super.getLuggageSpace() + person.getLuggageSpace();
	}

	@Override
	public int getDisabledSpace() {
		return super.getDisabledSpace();
	}

}
