package edu.cmu.cs.cs214.hw3.bus;

import java.util.List;

import edu.cmu.cs.cs214.hw3.routeplanner.Stop;

/**
 * Good engine bus, which can drive faster and has the ratio lower than 1
 * 
 * @author zhaoru
 *
 */
public class GoodEngineBus extends AbstractBus {
	private static final double GOOD_ENGINE_PARA = 0.9;

	/**
	 * Constructor
	 * 
	 * @param newBusRoute
	 *            a route to set
	 */
	public GoodEngineBus(List<Stop> newBusRoute) {
		super(newBusRoute);
	}

	@Override
	public String getName() {
		return "GoodEngineBus";
	}

	@Override
	public double getSpeedRatio() {
		return super.getSpeedRatio() * GOOD_ENGINE_PARA;
	}

}
