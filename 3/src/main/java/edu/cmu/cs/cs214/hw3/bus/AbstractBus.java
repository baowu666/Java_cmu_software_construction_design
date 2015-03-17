package edu.cmu.cs.cs214.hw3.bus;

import java.util.List;

import javax.swing.ImageIcon;

import edu.cmu.cs.cs214.hw3.Location;
import edu.cmu.cs.cs214.hw3.Simulation;
import edu.cmu.cs.cs214.hw3.person.Person;
import edu.cmu.cs.cs214.hw3.routeplanner.Stop;

/**
 * Abstract Decorator for the buses
 * 
 * @author zhaoru
 *
 */
public abstract class AbstractBus implements Bus {
	// CHECKSTYLE:OFF
	protected Bus bus;// I have to use protected tyoe as decorator
	// CHECKSTYLE:ON
	/**
	 * Constructor
	 * 
	 * @param newBusRoute
	 *            the bus route for the bus as a parameter
	 */
	public AbstractBus(List<Stop> newBusRoute) {
		bus = new OrdinaryBus(newBusRoute);
	}

	@Override
	public ImageIcon getImage() {
		return bus.getImage();
	}

	@Override
	public void step(Simulation samulation) {
		bus.step(samulation);
	}

	@Override
	public List<Stop> getBusRoute() {
		return bus.getBusRoute();
	}

	@Override
	public void getOnPerson(Person p) {
		bus.getOnPerson(p);
	}

	@Override
	public void getOffPerson(Person p) {
		bus.getOffPerson(p);
	}

	@Override
	public List<Person> getPassenger() {
		return bus.getPassenger();
	}

	@Override
	public Stop getNextStop() {
		return bus.getNextStop();
	}

	@Override
	public void setRealTimeCurrentStop(int time) {
		bus.setRealTimeCurrentStop(time);
	}

	@Override
	public String getName() {
		return bus.getName();
	}

	@Override
	public Location getLocation() {
		return bus.getLocation();
	}

	@Override
	public void setLocation(Location newLocation) {
		bus.setLocation(newLocation);
	}

	@Override
	public Location getStartLocation() {
		return bus.getStartLocation();
	}

	@Override
	public Location getDestination() {
		return bus.getDestination();
	}

	@Override
	public int getCapacity() {
		return bus.getCapacity();
	}

	@Override
	public void setTotalDelayTime(int reachingTime) {
		bus.setTotalDelayTime(reachingTime);
	}

	@Override
	public int getTotalDelayTime() {
		return bus.getTotalDelayTime();
	}

	@Override
	public int getScheduleTime() {
		return bus.getScheduleTime();
	}

	@Override
	public void addDelayTimePerStop(int newDelayTimePerStop) {
		bus.addDelayTimePerStop(newDelayTimePerStop);
	}

	@Override
	public int getStartingTime() {
		return bus.getStartingTime();
	}

	@Override
	public boolean getIsArrived() {
		return bus.getIsArrived();
	}

	@Override
	public Stop getCurrentStop() {
		return bus.getCurrentStop();
	}

	@Override
	public void resetDelayTimePerStop(int scheduleDelay) {
		bus.resetDelayTimePerStop(scheduleDelay);
	}

	@Override
	public int getLuggageSpace() {
		return bus.getLuggageSpace();
	}

	@Override
	public double getSpeedRatio() {
		return bus.getSpeedRatio();
	}

	@Override
	public double getUnboardingRatio() {
		return bus.getUnboardingRatio();
	}

	@Override
	public int getDisabledSpace() {
		return bus.getDisabledSpace();
	}

	@Override
	public void addDisSpc(int x) {
		bus.addDisSpc(x);
	}

	@Override
	public void addLuggSpc(int x) {
		bus.addLuggSpc(x);
	}

	@Override
	public void addCapSpc(int x) {
		bus.addCapSpc(x);
	}

	@Override
	public boolean isBoardable(Person p) {
		return bus.isBoardable(p);
	}
}
