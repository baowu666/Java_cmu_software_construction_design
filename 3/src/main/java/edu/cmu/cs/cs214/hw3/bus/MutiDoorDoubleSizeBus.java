package edu.cmu.cs.cs214.hw3.bus;

import java.util.List;

import edu.cmu.cs.cs214.hw3.routeplanner.Stop;

/**
 * Multidoor and double sized bus
 * 
 * @author zhaoru
 *
 */
public class MutiDoorDoubleSizeBus extends DoubleSizeBus {
	private static final double MULTI_DOOR_PARA = 0.5;

	/**
	 * Constructor
	 * 
	 * @param newBusRoute
	 *            a route to set
	 */
	public MutiDoorDoubleSizeBus(List<Stop> newBusRoute) {
		super(newBusRoute);
	}

	@Override
	public String getName() {
		return "MutiDoorDoubleSizeBus";
	}

	@Override
	public double getUnboardingRatio() {
		return super.getUnboardingRatio() * MULTI_DOOR_PARA;
	}

}
