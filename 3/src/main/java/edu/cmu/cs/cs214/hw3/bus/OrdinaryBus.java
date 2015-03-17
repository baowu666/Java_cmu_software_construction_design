package edu.cmu.cs.cs214.hw3.bus;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import edu.cmu.cs.cs214.hw3.Location;
import edu.cmu.cs.cs214.hw3.Simulation;
import edu.cmu.cs.cs214.hw3.Util;
import edu.cmu.cs.cs214.hw3.person.Person;
import edu.cmu.cs.cs214.hw3.routeplanner.Stop;

/**
 * A concrete component of bus type
 * 
 * @author zhaoru
 *
 */
public class OrdinaryBus implements Bus {
	private Random random = new Random();
	private String busName;
	private boolean isArrivedAStop;
	private Stop currentStop;
	private List<Stop> route;
	private List<Stop> visitedRoute;
	private List<Person> passenger;
	private Location location;
	private int delayTimePerStop, totalDelayTime, scheduleTime, startingTime,
			capacity, luggageSpace, disabledSpace;
	private double speedRatio, unboardingRatio;
	private static final ImageIcon BUS_IMAGE = Util.loadImage("bus.png");
	private static final int ORDINARY_BUS_CAP = 30;
	private static final int ORDINARY_BUS_LUGG_SPC = 6;
	private static final int ORDINARY_BUS_DISABLE_SPC = 2;
	private static final int TIME_AT_NIGHT = 20 * 3600;
	private static final double RATIO_TO_BAD_WETHER = 0.1;
	private static final double BAD_WETHER_SPEED_RATIO = 1.1;
	private static final double SPEEDUP_AT_NIGHT = 0.1;
	private static final double SPEED_UP_WHEN_SLOW = 0.05;

	/**
	 * Constructor
	 * 
	 * @param newRoute
	 *            the route to set
	 */
	public OrdinaryBus(List<Stop> newRoute) {
		route = newRoute;
		location = getStartLocation();
		visitedRoute = new LinkedList<Stop>();
		passenger = new ArrayList<Person>();
		capacity = ORDINARY_BUS_CAP;
		luggageSpace = ORDINARY_BUS_LUGG_SPC;
		disabledSpace = ORDINARY_BUS_DISABLE_SPC;
		// 10% to encounter the bad weather condition
		if (random.nextDouble() < RATIO_TO_BAD_WETHER) {
			speedRatio = BAD_WETHER_SPEED_RATIO;
		} else {
			speedRatio = 1.0;
		}
		unboardingRatio = 1.0;
		isArrivedAStop = true;
		currentStop = route.get(0);
		startingTime = route.get(0).getTime();
		scheduleTime = route.get(route.size() - 1).getTime();
		delayTimePerStop = 0;
		if (newRoute.size() != 0)
			busName = route.get(0).getBusName();
	}

	@Override
	public void step(Simulation simulation) {
		int time = simulation.getTime();
		if (route.size() == 0) {
			setTotalDelayTime(time);
			simulation.addBusToRmv(this);
			return;
		}
		if (time == (route.get(0).getTime() + delayTimePerStop)) {
			isArrivedAStop = true;
			currentStop = simulation.arriveAt(this, route.get(0));
		} else {
			isArrivedAStop = false;
		}
	}

	@Override
	public boolean isBoardable(Person p) {
		if (p.getDisabledSpace() != 0) {
			if ((p.getDisabledSpace() <= disabledSpace)
					&& (p.getLuggageSpace() <= luggageSpace)) {
				disabledSpace -= p.getDisabledSpace();
				luggageSpace -= p.getLuggageSpace();
				return true;
			}
		} else {
			if (p.getLuggageSpace() <= luggageSpace) {
				if ((p.getCapacity() <= capacity)) {
					capacity -= p.getCapacity();
					luggageSpace -= p.getLuggageSpace();
					return true;
				}
				if ((p.getCapacity() > capacity)
						&& (p.getCapacity() <= (2 * disabledSpace))) {
					disabledSpace = (disabledSpace * 2 - p.getCapacity()) / 2;
					luggageSpace -= p.getLuggageSpace();
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void setRealTimeCurrentStop(int time) {
		int timeDiff = time - route.get(0).getTime();
		for (Stop stop : route) {
			stop.setArriveTime(timeDiff);
		}
		visitedRoute.add(route.get(0));
		route.remove(route.get(0));
		if (route.size() != 0) {
			// speed up at night
			if (time > TIME_AT_NIGHT)
				speedRatio -= SPEEDUP_AT_NIGHT;
			// if bus is later than schedule, speed up and reduce the transit
			// time by 5%
			if (time > route.get(0).getTime())
				speedRatio -= SPEED_UP_WHEN_SLOW;
			// Set delay time as default scheduled delay time
			resetDelayTimePerStop((int) ((route.get(0).getTime() - visitedRoute
					.get(0).getTime()) * (speedRatio - 1)));
		}
	}

	@Override
	public ImageIcon getImage() {
		return BUS_IMAGE;
	}

	@Override
	public String getName() {
		return busName;
	}

	@Override
	public int getStartingTime() {
		return startingTime;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public void setLocation(Location newLocation) {
		location = newLocation;
	}

	@Override
	public Location getStartLocation() {
		return route.get(0).getLocation();
	}

	@Override
	public Location getDestination() {
		return route.get(route.size() - 1).getLocation();
	}

	@Override
	public int getCapacity() {
		return capacity;
	}

	@Override
	public int getLuggageSpace() {
		return luggageSpace;
	}

	@Override
	public double getSpeedRatio() {
		return speedRatio;
	}

	@Override
	public double getUnboardingRatio() {
		return unboardingRatio;
	}

	@Override
	public int getDisabledSpace() {
		return disabledSpace;
	}

	@Override
	public void addDisSpc(int x) {
		disabledSpace += x;
	}

	@Override
	public void addLuggSpc(int x) {
		luggageSpace += x;
	}

	@Override
	public void addCapSpc(int x) {
		capacity += x;
	}

	@Override
	public void setTotalDelayTime(int reachingTime) {
		totalDelayTime = reachingTime - getScheduleTime();
	}

	@Override
	public int getTotalDelayTime() {
		return totalDelayTime;
	}

	@Override
	public int getScheduleTime() {
		return scheduleTime;
	}

	@Override
	public void addDelayTimePerStop(int newDelayTimePerStop) {
		delayTimePerStop += newDelayTimePerStop;
	}

	@Override
	public void resetDelayTimePerStop(int scheduleDelay) {
		delayTimePerStop = scheduleDelay;
	}

	@Override
	public List<Stop> getBusRoute() {
		return route;
	}

	@Override
	public void getOnPerson(Person p) {
		passenger.add(p);
		p.setLocation(location);
	}

	@Override
	public void getOffPerson(Person p) {
		passenger.remove(p);
	}

	@Override
	public List<Person> getPassenger() {
		return passenger;
	}

	@Override
	public boolean getIsArrived() {
		return isArrivedAStop;
	}

	@Override
	public Stop getNextStop() {
		if (route.size() != 0)
			return route.get(0);
		return null;
	}

	@Override
	public Stop getCurrentStop() {
		return currentStop;
	}

}
