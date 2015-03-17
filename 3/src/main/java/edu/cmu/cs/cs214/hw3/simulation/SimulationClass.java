package edu.cmu.cs.cs214.hw3.simulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.cs.cs214.hw3.Entity;
import edu.cmu.cs.cs214.hw3.Factory;
import edu.cmu.cs.cs214.hw3.Simulation;
import edu.cmu.cs.cs214.hw3.bus.Bus;
import edu.cmu.cs.cs214.hw3.person.Person;
import edu.cmu.cs.cs214.hw3.routeplanner.RoutePlanner;
import edu.cmu.cs.cs214.hw3.routeplanner.RoutePlannerBuilderClass;
import edu.cmu.cs.cs214.hw3.routeplanner.Stop;

/**
 * This class implements simulation interface. It do the whole simulation. It simulate each step and calls methods from factory and entities
 * @author zhaoru
 *
 */
public class SimulationClass implements Simulation {
	private static final int SECOND_FOUR_OCLOCK = 4*3600;
    private static final String ROUTES_FILE_NAME = "src/main/resources/oakland_stop_times.txt";
	private static final int MAX_WAIT = 1200;
	private RoutePlannerBuilderClass rpbc;
	private RoutePlanner rp;
	private List<List<Stop>> busRoute;
	
	private int time;
	private List<Factory> factories;
	private List<Bus> buses;
	private List<Bus> removedBuses;
	private List<Bus> busToRemove;
	private List<Person> persons;
	private List<Person> removedPersons;
	private List<Person> personToRemove;
	private List<Entity> entities;
	
	/**
	 * Constructor
	 */
	public SimulationClass() {
		time = SECOND_FOUR_OCLOCK;
		factories = new ArrayList<Factory>();
		buses = new ArrayList<Bus>();
		persons = new ArrayList<Person>();
		entities = new ArrayList<Entity>();
		personToRemove = new ArrayList<Person>();
		busToRemove = new ArrayList<Bus>();
		removedBuses = new ArrayList<Bus>();
		removedPersons = new ArrayList<Person>();
		
		rpbc= new RoutePlannerBuilderClass(ROUTES_FILE_NAME, MAX_WAIT);
		try {
			rp = rpbc.build(ROUTES_FILE_NAME, MAX_WAIT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		busRoute = rpbc.getBusRoute();
	}
	
	@Override
	public void step() {
		time++;
		
		for (Factory factory : factories) {
			factory.step();
		}
		for (Bus b : buses) {
			b.step(this);
			if (b.getIsArrived() == true) {
				for (Person p : persons) {
					if (b.getCurrentStop() != null) 
						p.busArrived(b, b.getCurrentStop());
				}
			}
		}
		for (Person pr : personToRemove) {
			removePerson(pr);
		}
		for (Bus br : busToRemove) {
			removeBus(br);
		}
		personToRemove.clear();
		busToRemove.clear();
	}
	
	@Override
	public Stop arriveAt(Bus b, Stop s) {
		b.setLocation(s.getLocation());
		b.setRealTimeCurrentStop(time);
		return s;
	}
	
	@Override
	public int getTime() {
		return time;
	}

	@Override
	public RoutePlanner getRoutePlanner() {
		return rp;
	}

	@Override
	public List<List<Stop>> getBusRoute() {
		return busRoute;
	}
	
	@Override
	public Iterable<Entity> getEntities() {
		return entities;
	}

	@Override
	public List<Bus> getBuses() {
		return buses;
	}

	@Override
	public List<Person> getPersons() {
		return persons;
	}

	@Override
	public void addFactory(Factory factory) {
		factories.add(factory);		
	}
	
	@Override
	public void insertPerson(Person p) {
		persons.add(p);
		entities.add((Entity)p);
	}

	@Override
	public void insertBus(Bus b) {
		buses.add(b);
		entities.add((Entity)b);
	}

	@Override
	public void removePerson(Person p) {
		p.setTotalDelayTime(time);
		removedPersons.add(p);
		persons.remove(p);
	}
	
	@Override
	public void removeBus(Bus b) {
		b.setTotalDelayTime(time);
		removedBuses.add(b);
		buses.remove(b);
	}
	
	@Override
	public void addPersonToRmv(Person p) {
		personToRemove.add(p);
	}

	@Override
	public void addBusToRmv(Bus b) {
		busToRemove.add(b);
	}
	
	@Override
	public String getAnalysisResult() {
		int personTotalDealy = 0, busTotalDelay = 0;
		String lineP, lineB;
		for (Person p : removedPersons) {
			personTotalDealy += p.getTotalDelayTime();
		}
		for (Bus b : removedBuses) {
			busTotalDelay += b.getTotalDelayTime();
		}
		if (removedPersons.size() != 0) {
			lineP = String.valueOf(personTotalDealy / removedPersons.size());
		} else {
			lineP = "0";
		}
		if (removedBuses.size() != 0) {
			lineB = String.valueOf(busTotalDelay / removedBuses.size());
		} else {
			lineB = "0";
		}
		return "Person Delay: " + lineP + " " + "Bus Delay: " + lineB;
	}

}
