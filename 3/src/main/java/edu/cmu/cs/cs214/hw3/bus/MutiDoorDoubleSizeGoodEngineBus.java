package edu.cmu.cs.cs214.hw3.bus;

import java.util.List;

import edu.cmu.cs.cs214.hw3.routeplanner.Stop;

/**
 * Multidoor double sized and goof engine bus
 * 
 * @author zhaoru
 *
 */
public class MutiDoorDoubleSizeGoodEngineBus extends MutiDoorDoubleSizeBus {
	private Bus bus;

	/**
	 * Constructor
	 * 
	 * @param newBusRoute
	 *            a route to set
	 */
	public MutiDoorDoubleSizeGoodEngineBus(List<Stop> newBusRoute) {
		super(newBusRoute);
		bus = new GoodEngineBus(newBusRoute);
	}

	@Override
	public String getName() {
		return "MutiDoorDoubleSizeGoodEngineBus";
	}

	@Override
	public double getSpeedRatio() {
		return super.getSpeedRatio() * bus.getSpeedRatio();
	}

	@Override
	public double getUnboardingRatio() {
		return super.getUnboardingRatio() * bus.getUnboardingRatio();
	}

	@Override
	public int getCapacity() {
		return Math.max(super.getCapacity(), bus.getCapacity());
	}

	@Override
	public int getLuggageSpace() {
		return Math.max(super.getLuggageSpace(), bus.getLuggageSpace());
	}

	@Override
	public int getDisabledSpace() {
		return Math.max(super.getDisabledSpace(), bus.getDisabledSpace());
	}

}
