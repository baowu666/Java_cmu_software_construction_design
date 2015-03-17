package edu.cmu.cs.cs214.hw3.bus;

import java.util.List;

import edu.cmu.cs.cs214.hw3.routeplanner.Stop;

/**
 * Double size with good engine bus
 * 
 * @author zhaoru
 *
 */
public class DoubleSizeGoodEngineBus extends DoubleSizeBus {
	private Bus bus;

	/**
	 * Constructor
	 * 
	 * @param newBusRoute
	 *            a bus route to set
	 */
	public DoubleSizeGoodEngineBus(List<Stop> newBusRoute) {
		super(newBusRoute);
		bus = new GoodEngineBus(newBusRoute);
	}

	@Override
	public String getName() {
		return "DoubleSizeGoodEngineBus";
	}

	@Override
	public double getSpeedRatio() {
		return bus.getSpeedRatio();
	}

	@Override
	public double getUnboardingRatio() {
		return super.getUnboardingRatio();
	}

	@Override
	public int getCapacity() {
		return super.getCapacity();
	}

	@Override
	public int getLuggageSpace() {
		return super.getLuggageSpace();
	}

	@Override
	public int getDisabledSpace() {
		return super.getDisabledSpace();
	}

}
