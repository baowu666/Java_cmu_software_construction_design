package edu.cmu.cs.cs214.hw3.bus;

import java.util.List;

import edu.cmu.cs.cs214.hw3.routeplanner.Stop;

/**
 * this kind of bus is double sized, which has double capacity for people,
 * disabled, luggage
 * 
 * @author zhaoru
 *
 */
public class DoubleSizeBus extends AbstractBus {

	/**
	 * Constructor
	 * 
	 * @param newBusRoute
	 *            a bus route as a parameter
	 */
	public DoubleSizeBus(List<Stop> newBusRoute) {
		super(newBusRoute);
	}

	@Override
	public String getName() {
		return "DoubleSizeBus";
	}

	@Override
	public int getCapacity() {
		return super.getCapacity() * 2;
	}

	@Override
	public int getLuggageSpace() {
		return super.getLuggageSpace() * 2;
	}

	@Override
	public int getDisabledSpace() {
		return super.getDisabledSpace() * 2;
	}

}
