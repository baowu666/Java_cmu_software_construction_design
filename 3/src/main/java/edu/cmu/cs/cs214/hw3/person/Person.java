package edu.cmu.cs.cs214.hw3.person;

import java.util.List;

import edu.cmu.cs.cs214.hw3.Entity;
import edu.cmu.cs.cs214.hw3.Simulation;
import edu.cmu.cs.cs214.hw3.bus.Bus;
import edu.cmu.cs.cs214.hw3.routeplanner.Stop;

/**
 * Interface that extends the entity, which is for all persons
 * 
 * @author zhaoru
 *
 */
public interface Person extends Entity {

	/**
	 * set simulation
	 * 
	 * @param newSimulation
	 *            a simulation to set
	 */
	void setSimulation(Simulation newSimulation);

	/**
	 * if a bus is arrived, check the person's status and check if this person
	 * can board this bus, and check if this person has reached the destination.
	 * 
	 * @param b
	 *            the bus that arrived
	 * @param s
	 *            the stop the bus arrived
	 */
	void busArrived(Bus b, Stop s);

	/**
	 * get boarding time of a person
	 * 
	 * @return boarding time
	 */
	int getBoardingTime();

	/**
	 * get the transfer stops of a person, which is the future stops the person
	 * will arrive
	 * 
	 * @return a list of stop
	 */
	List<Stop> getTransfers();

	/**
	 * get the person on bus
	 */
	void getOnBus();

	/**
	 * get the person off the bus
	 */
	void getOffBus();

	/**
	 * check if the person is on bus
	 * 
	 * @return true or false
	 */
	boolean isOnBus();
}
