package edu.cmu.cs.cs214.hw3.person;

import java.util.Random;

import edu.cmu.cs.cs214.hw3.routeplanner.Itinerary;

/**
 * a group of person
 * 
 * @author zhaoru
 *
 */
public class GroupPerson extends AbstractPerson {
	private int num;
	private Random random = new Random();
	private static final int NUM_PERSON_GROUP = 7;

	/**
	 * constructor
	 * 
	 * @param newItinerary
	 *            the itinerary to set
	 */
	public GroupPerson(Itinerary newItinerary) {
		super(newItinerary);
		num = random.nextInt(NUM_PERSON_GROUP) + 1;
	}

	/**
	 * get the number of persons
	 * 
	 * @return number of persons
	 */
	public int getNumPerson() {
		return num;
	}

	@Override
	public int getBoardingTime() {
		return super.getBoardingTime() * num;
	}

	@Override
	public String getName() {
		return "GroupPerson";
	}

	@Override
	public int getCapacity() {
		return super.getCapacity() * num;
	}

}
