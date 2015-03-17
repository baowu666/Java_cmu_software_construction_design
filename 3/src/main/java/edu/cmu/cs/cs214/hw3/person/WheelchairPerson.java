package edu.cmu.cs.cs214.hw3.person;

import edu.cmu.cs.cs214.hw3.routeplanner.Itinerary;

/**
 * Person with wheelchair, who need more time to board and must seat the
 * disabled room
 * 
 * @author zhaoru
 *
 */
public class WheelchairPerson extends AbstractPerson {
	private static final int WHEEL_SPEED = 3;

	/**
	 * Constructor
	 * 
	 * @param newItinerary
	 *            the itinerary to set
	 */
	public WheelchairPerson(Itinerary newItinerary) {
		super(newItinerary);
	}

	@Override
	public int getBoardingTime() {
		return super.getBoardingTime() + WHEEL_SPEED;
	}

	@Override
	public String getName() {
		return "WheelchairPerson";
	}

	@Override
	public int getCapacity() {
		return 0;
	}

	@Override
	public int getDisabledSpace() {
		return super.getDisabledSpace() + 1;
	}

}
