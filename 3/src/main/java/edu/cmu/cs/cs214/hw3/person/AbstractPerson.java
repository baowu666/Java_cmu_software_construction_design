package edu.cmu.cs.cs214.hw3.person;

import java.util.List;

import javax.swing.ImageIcon;

import edu.cmu.cs.cs214.hw3.Location;
import edu.cmu.cs.cs214.hw3.Simulation;
import edu.cmu.cs.cs214.hw3.bus.Bus;
import edu.cmu.cs.cs214.hw3.routeplanner.Itinerary;
import edu.cmu.cs.cs214.hw3.routeplanner.Stop;

/**
 * Abstract Decorator for the persons
 * 
 * @author zhaoru
 *
 */
public abstract class AbstractPerson implements Person {
	// CHECKSTYLE:OFF
	protected Person person;// I have to use protected as the abstract decorator

	// CHECKSTYLE:OFF
	/**
	 * Constructor
	 * 
	 * @param newItinerary
	 */
	public AbstractPerson(Itinerary newItinerary) {
		person = new OrdinaryPerson(newItinerary);
	}

	@Override
	public ImageIcon getImage() {
		return person.getImage();
	}

	@Override
	public void busArrived(Bus b, Stop s) {
		person.busArrived(b, s);
	}

	@Override
	public List<Stop> getTransfers() {
		return person.getTransfers();
	}

	@Override
	public int getBoardingTime() {
		return person.getBoardingTime();
	}

	@Override
	public void getOnBus() {
		person.getOnBus();
	}

	@Override
	public void getOffBus() {
		person.getOffBus();
	}

	@Override
	public boolean isOnBus() {
		return person.isOnBus();
	}

	@Override
	public String getName() {
		return person.getName();
	}

	@Override
	public Location getLocation() {
		return person.getLocation();
	}

	@Override
	public void setLocation(Location newLocation) {
		person.setLocation(newLocation);
	}

	@Override
	public Location getStartLocation() {
		return person.getStartLocation();
	}

	@Override
	public Location getDestination() {
		return person.getDestination();
	}

	@Override
	public int getCapacity() {
		return person.getCapacity();
	}

	@Override
	public void setTotalDelayTime(int reachingTime) {
		person.setTotalDelayTime(reachingTime);
	}

	@Override
	public int getTotalDelayTime() {
		return person.getTotalDelayTime();
	}

	@Override
	public int getScheduleTime() {
		return person.getScheduleTime();
	}

	@Override
	public int getLuggageSpace() {
		return person.getLuggageSpace();
	}

	@Override
	public void setSimulation(Simulation newSimulation) {
		person.setSimulation(newSimulation);
	}

	@Override
	public int getDisabledSpace() {
		return person.getDisabledSpace();
	}

}
