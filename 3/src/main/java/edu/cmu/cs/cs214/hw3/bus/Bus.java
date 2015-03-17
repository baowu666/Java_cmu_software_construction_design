package edu.cmu.cs.cs214.hw3.bus;

import java.util.List;

import edu.cmu.cs.cs214.hw3.Entity;
import edu.cmu.cs.cs214.hw3.Simulation;
import edu.cmu.cs.cs214.hw3.person.Person;
import edu.cmu.cs.cs214.hw3.routeplanner.Stop;

/**
 * Interface that extends the entity, which is for all buses
 * 
 * @author zhaoru
 *
 */
public interface Bus extends Entity {

	/**
	 * This method do things within one simulation step of a bus.
	 * 
	 * @param samulation
	 *            simulation parameter
	 */
	void step(Simulation samulation);

	/**
	 * get if the bus is arrived at a stop
	 * 
	 * @return true or false
	 */
	boolean getIsArrived();

	/**
	 * check if a person with certain properties can board the bus
	 * 
	 * @param p
	 *            a person
	 * @return true or false
	 */
	boolean isBoardable(Person p);

	/**
	 * get a bus route
	 * 
	 * @return a list of bus stop
	 */
	List<Stop> getBusRoute();

	/**
	 * get a bus's passengers
	 * 
	 * @return a list of persons of this bus
	 */
	List<Person> getPassenger();

	/**
	 * get next stop
	 * 
	 * @return the next stop
	 */
	Stop getNextStop();

	/**
	 * get current stop
	 * 
	 * @return current stop
	 */
	Stop getCurrentStop();

	/**
	 * add some time to the delay time of this segment
	 * 
	 * @param newDelayTimePerStop
	 *            time to add
	 */
	void addDelayTimePerStop(int newDelayTimePerStop);

	/**
	 * reset delay time to the scheduled delay time
	 * 
	 * @param scheduleDelay
	 *            scheduled delay time
	 */
	void resetDelayTimePerStop(int scheduleDelay);

	/**
	 * set total delay time for a bus
	 * 
	 * @param time
	 *            reaching time to set
	 */
	void setRealTimeCurrentStop(int time);

	/**
	 * get a person on the bus
	 * 
	 * @param p
	 *            person to get on
	 */
	void getOnPerson(Person p);

	/**
	 * get a person off a bus
	 * 
	 * @param p
	 *            person to get off
	 */
	void getOffPerson(Person p);

	/**
	 * get starting time of the bus
	 * 
	 * @return start time for the bus
	 */
	int getStartingTime();

	/**
	 * get the bus's speed ratio, which decided by the engine
	 * 
	 * @return a double ratio used to time the scheduled segment time
	 */
	double getSpeedRatio();

	/**
	 * get the bus's unboarding ratio, which decided by multiple doors
	 * 
	 * @return a double ratio used to time the boarding time
	 */
	double getUnboardingRatio();

	/**
	 * add disabled space when person get off
	 * 
	 * @param x
	 *            space to add
	 */
	void addDisSpc(int x);

	/**
	 * add luggage space when person get off
	 * 
	 * @param x
	 *            space to add
	 */
	void addLuggSpc(int x);

	/**
	 * add capacity when person get off
	 * 
	 * @param x
	 *            space to add
	 */
	void addCapSpc(int x);

}
