package edu.cmu.cs.cs214.hw3.person;

import edu.cmu.cs.cs214.hw3.routeplanner.Itinerary;

/**
 * Person with bike and luggage and use cash
 * 
 * @author zhaoru
 *
 */
public class CashBikeLuggage extends BikeLuggagePerson {
	private Person person;

	/**
	 * Constructor
	 * 
	 * @param newItinerary
	 *            itinerary to set
	 */
	public CashBikeLuggage(Itinerary newItinerary) {
		super(newItinerary);
		person = new CashPerson(newItinerary);
	}

	@Override
	public int getBoardingTime() {
		return super.getBoardingTime() + person.getBoardingTime();
	}

	@Override
	public String getName() {
		return "CashBikeLuggage";
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
