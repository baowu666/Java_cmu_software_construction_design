package edu.cmu.cs.cs214.hw3.person;

import edu.cmu.cs.cs214.hw3.routeplanner.Itinerary;

/**
 * Person with wheelchair and luggage and use credit card to board
 * 
 * @author zhaoru
 *
 */
public class CreditCardWheelchairLuggagePerson extends WheelchairLuggagePerson {
	private Person person;

	/**
	 * Constructor
	 * 
	 * @param newItinerary
	 *            the itinerary to set
	 */
	public CreditCardWheelchairLuggagePerson(Itinerary newItinerary) {
		super(newItinerary);
		person = new CreditCardPerson(newItinerary);
	}

	@Override
	public int getBoardingTime() {
		return super.getBoardingTime() + person.getBoardingTime();
	}

	@Override
	public String getName() {
		return "CreditCardWheelchairLuggagePerson";
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
