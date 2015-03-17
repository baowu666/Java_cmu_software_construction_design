package edu.cmu.cs.cs214.hw3;

import java.util.List;

import edu.cmu.cs.cs214.hw3.bus.Bus;
import edu.cmu.cs.cs214.hw3.person.Person;
import edu.cmu.cs.cs214.hw3.routeplanner.RoutePlanner;
import edu.cmu.cs.cs214.hw3.routeplanner.Stop;

/**
 * The city transportation simulation. The simulation interface is meant to be a
 * subset of the methods that will be needed for a correct implementation. A
 * single run of the simulation should represent a full day of a bus schedule
 * starting at approximately 3:30am (03:30 hrs) and ending at 12:00am (00:00
 * hrs) the following day.
 */
public interface Simulation {

	/**
	 * Performs a single step in the simulation. Each step should represent one
	 * second in the day's simulation.
	 */
	void step();

	/**
	 * Returns to the caller the seconds that have elapsed since midnight.
	 * 
	 * @return The seconds since midnight
	 */
	int getTime();

	/**
	 * Returns the collection of {@link Entity}s in this Simulation. The
	 * <code>Iterable</code> interface enables this collection to be used in a
	 * "for each" loop:
	 *
	 * <pre>
	 *   e.g. <code> for(Entity entity : simulation.getEntities()) {...}
	 * </pre>
	 *
	 * @return a collection of all {@link Entity}s in this Simulation
	 */
	Iterable<Entity> getEntities();

	/**
	 * getRoutePlanner
	 * 
	 * @return RoutePlanner
	 */
	RoutePlanner getRoutePlanner();

	/**
	 * getBusRoute
	 * 
	 * @return BusRoute
	 */
	List<List<Stop>> getBusRoute();

	/**
	 * get Buses
	 * 
	 * @return all the buses
	 */
	List<Bus> getBuses();

	/**
	 * get Persons
	 * 
	 * @return all the persons
	 */
	List<Person> getPersons();

	/**
	 * remove a person
	 * 
	 * @param p
	 *            person to remove
	 */
	void removePerson(Person p);

	/**
	 * add a Person To Remove list
	 * 
	 * @param p
	 *            person to remove
	 */
	void addPersonToRmv(Person p);

	/**
	 * add a Bus To Remove list
	 * 
	 * @param b
	 *            bus to remove
	 */
	void addBusToRmv(Bus b);

	/**
	 * remove a bus
	 * 
	 * @param b
	 *            bus to remove
	 */
	void removeBus(Bus b);

	/**
	 * a bus arrive at a stop, set the location and real time for the bus and
	 * its schedule
	 * 
	 * @param b
	 *            bus to arrive
	 * @param s
	 *            stop arrives
	 * @return the stop the bus arrives
	 */
	Stop arriveAt(Bus b, Stop s);

	/**
	 * add a factory to simulation
	 * 
	 * @param factory
	 *            a factory to add
	 */
	void addFactory(Factory factory);

	/**
	 * insert person to simulation
	 * 
	 * @param p
	 *            person to insert
	 */
	void insertPerson(Person p);

	/**
	 * insert bus to simulation
	 * 
	 * @param b
	 *            bus to insert
	 */
	void insertBus(Bus b);

	/**
	 * A method that a user of the simulation will use to display an analysis of
	 * the current (or final) state of the simulation. The returned string
	 * should be a summary of important statistics of the simulation. An example
	 * return value could be:
	 * 
	 * "Busses on time: 67%  | People on time: 45%"
	 * 
	 * @return a string with an analysis of the current state of the simulation
	 */
	String getAnalysisResult();

}
