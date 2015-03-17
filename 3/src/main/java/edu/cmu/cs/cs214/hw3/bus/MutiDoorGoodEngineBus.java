package edu.cmu.cs.cs214.hw3.bus;

import java.util.List;

import edu.cmu.cs.cs214.hw3.routeplanner.Stop;

/**
 * Multidoor and good engine bus
 * 
 * @author zhaoru
 *
 */
public class MutiDoorGoodEngineBus extends GoodEngineBus {
	private static final double MULTI_DOOR_PARA = 0.5;
	/**
	 * Constructor
	 * 
	 * @param newBusRoute
	 *            a route to set
	 */
	public MutiDoorGoodEngineBus(List<Stop> newBusRoute) {
		super(newBusRoute);
	}

	@Override
	public String getName() {
		return "MutiDoorGoodEngineBus";
	}

	@Override
	public double getUnboardingRatio() {
		return super.getUnboardingRatio() * MULTI_DOOR_PARA;
	}

}
