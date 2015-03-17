package edu.cmu.cs.cs214.hw3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.cmu.cs.cs214.hw3.bus.Bus;
import edu.cmu.cs.cs214.hw3.bus.DoubleSizeBus;
import edu.cmu.cs.cs214.hw3.bus.DoubleSizeGoodEngineBus;
import edu.cmu.cs.cs214.hw3.bus.GoodEngineBus;
import edu.cmu.cs.cs214.hw3.bus.MutiDoorDoubleSizeBus;
import edu.cmu.cs.cs214.hw3.bus.MutiDoorDoubleSizeGoodEngineBus;
import edu.cmu.cs.cs214.hw3.bus.MutiDoorGoodEngineBus;
import edu.cmu.cs.cs214.hw3.bus.OrdinaryBus;
import edu.cmu.cs.cs214.hw3.routeplanner.Stop;

/**
 * Bus Factory is to read file and generate buses and insert buses to simulation
 * at certain time
 * 
 * @author zhaoru
 *
 */
public class BusFactory implements Factory {
	private Random random = new Random();
	private int time;
	private Simulation simulation;
	private List<List<Stop>> busRoute;
	private List<Bus> buses;

	/**
	 * Constructor
	 * 
	 * @param newSimulation
	 *            simulation as a parameter
	 * @param para
	 *            parameter list
	 * @throws FileNotFoundException
	 *             if FileNotFoundException
	 * @throws IOException
	 *             if IOException
	 */
	public BusFactory(Simulation newSimulation, double[] para)
			throws FileNotFoundException, IOException {
		simulation = newSimulation;
		busRoute = newSimulation.getBusRoute();
		buses = new ArrayList<Bus>();
		Bus bus;
		for (List<Stop> eachBusRoute : busRoute) {
			double num = random.nextDouble();
			// CHECKSTYLE:OFF
			if (num < para[0]) {
				bus = new OrdinaryBus(eachBusRoute);
			} else if (num < para[1]) {
				bus = new DoubleSizeBus(eachBusRoute);
			} else if (num < para[2]) {
				bus = new GoodEngineBus(eachBusRoute);
			} else if (num < para[3]) {
				bus = new DoubleSizeGoodEngineBus(eachBusRoute);
			} else if (num < para[4]) {
				bus = new MutiDoorDoubleSizeBus(eachBusRoute);
			} else if (num < para[5]) {
				bus = new MutiDoorGoodEngineBus(eachBusRoute);
			} else {
				bus = new MutiDoorDoubleSizeGoodEngineBus(eachBusRoute);
			}
			// CHECKSTYLE:ON
			buses.add(bus);
		}
	}

	@Override
	public void step() {
		time = simulation.getTime();
		for (Bus bus : buses) {
			if (time == bus.getStartingTime()) {
				simulation.insertBus(bus);
			}
		}
	}

}
