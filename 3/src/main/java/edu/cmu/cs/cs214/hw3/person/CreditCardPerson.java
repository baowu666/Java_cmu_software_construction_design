package edu.cmu.cs.cs214.hw3.person;

import edu.cmu.cs.cs214.hw3.routeplanner.Itinerary;

/**
 * Person who use credit card, which needs less time to board
 * 
 * @author zhaoru
 *
 */
public class CreditCardPerson extends AbstractPerson {

	/**
	 * Constructor
	 * 
	 * @param newItinerary
	 *            the itinerary to set
	 */
	public CreditCardPerson(Itinerary newItinerary) {
		super(newItinerary);
	}

	@Override
	public int getBoardingTime() {
		return super.getBoardingTime() - 2;
	}

	@Override
	public String getName() {
		return "CreditCardPerson";
	}

}
