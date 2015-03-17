package edu.cmu.cs.cs214.hw3.person;

import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;

import edu.cmu.cs.cs214.hw3.Location;
import edu.cmu.cs.cs214.hw3.Simulation;
import edu.cmu.cs.cs214.hw3.Util;
import edu.cmu.cs.cs214.hw3.bus.Bus;
import edu.cmu.cs.cs214.hw3.routeplanner.Itinerary;
import edu.cmu.cs.cs214.hw3.routeplanner.Stop;
import edu.cmu.cs.cs214.hw3.routeplanner.TripSegment;

/**
 * A concrete component of person type
 * 
 * @author zhaoru
 *
 */
public class OrdinaryPerson implements Person {
	private Simulation simulation;
	private Itinerary itinerary;
	private List<Stop> transfers;
	private List<Stop> visitedStops;
	private Location location;
	private int boardingTime, totalDelayTime, capacity, luggageSpace,
			disabledSpace;
	private boolean isOnBus;
	private static final ImageIcon PERSON_IMAGE = Util.loadImage("person.png");

	/**
	 * Constructor
	 * 
	 * @param newItinerary
	 *            the itinerary to set to the person
	 */
	public OrdinaryPerson(Itinerary newItinerary) {
		itinerary = newItinerary;
		location = getStartLocation();
		capacity = 1;
		luggageSpace = 0;
		disabledSpace = 0;
		boardingTime = 1;
		isOnBus = false;

		transfers = new LinkedList<Stop>();
		visitedStops = new LinkedList<Stop>();
		List<TripSegment> changeSegs = itinerary.findChangeStop();
		transfers.add(itinerary.getStartLocation());
		for (TripSegment seg : changeSegs) {
			if (!(seg.getStopFrom().getLocation().equals(itinerary
					.getStartLocation().getLocation()))) {
				transfers.add(seg.getStopFrom());
				transfers.add(seg.getStopTo());
			}
		}
		if (!(transfers.get(transfers.size() - 1).getLocation()
				.equals(itinerary.getEndLocation().getLocation()))) {
			transfers.add(itinerary.getEndLocation());
		}
	}

	// stop could be current stop the bus b is at
	@Override
	public void busArrived(Bus b, Stop s) {
		if (transfers.size() == 0) {
			// bus arrived destination
			setTotalDelayTime(simulation.getTime());
			simulation.addPersonToRmv(this);
			return;
		}
		if (!isOnBus) {
			// reach next destination
			if (b.getLocation().equals(s.getLocation())
					&& b.getName().equals(transfers.get(0).getBusName())
					&& s.getLocation().equals(transfers.get(0).getLocation())) {
				if (b.isBoardable(this)) {
					// Already subtracted the space
					isOnBus = true;
					b.getOnPerson(this);
					b.addDelayTimePerStop(boardingTime);
					setLocation(b.getLocation());
					visitedStops.add(transfers.get(0));
					transfers.remove(0);
				}
			}
		} else {
			if (b.getLocation().equals(s.getLocation())
					&& s.getLocation().equals(transfers.get(0).getLocation())) {
				isOnBus = false;
				b.addCapSpc(capacity);
				b.addDisSpc(disabledSpace);
				b.addLuggSpc(luggageSpace);
				b.getOffPerson(this);
				b.addDelayTimePerStop((int) (boardingTime * b
						.getUnboardingRatio())); // mutidoors make unboarding
													// faster
				visitedStops.add(transfers.get(0));
				transfers.remove(0);
			} else {
				setLocation(b.getLocation());
			}
		}
	}

	@Override
	public void setSimulation(Simulation newSimulation) {
		simulation = newSimulation;
	}

	@Override
	public ImageIcon getImage() {
		return PERSON_IMAGE;
	}

	@Override
	public String getName() {
		return "OrdinaryPerson";
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
	public int getCapacity() {
		return capacity;
	}

	@Override
	public int getDisabledSpace() {
		return disabledSpace;
	}

	@Override
	public List<Stop> getTransfers() {
		return transfers;
	}

	@Override
	public Location getStartLocation() {
		return itinerary.getStartLocation().getLocation();
	}

	@Override
	public Location getDestination() {
		return itinerary.getEndLocation().getLocation();
	}

	@Override
	public int getBoardingTime() {
		return boardingTime;
	}

	@Override
	public int getLuggageSpace() {
		return luggageSpace;
	}

	@Override
	public void setTotalDelayTime(int reachingTime) {
		totalDelayTime = reachingTime - itinerary.getStartTime()
				- getScheduleTime();
	}

	@Override
	public int getTotalDelayTime() {
		return totalDelayTime;
	}

	@Override
	public int getScheduleTime() {
		return itinerary.getEndTime() - itinerary.getStartTime();
	}

	@Override
	public void getOnBus() {
		isOnBus = true;
	}

	@Override
	public void getOffBus() {
		isOnBus = false;
	}

	@Override
	public boolean isOnBus() {
		return isOnBus;
	}

}
