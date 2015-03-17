package edu.cmu.cs.cs214.hw3;

import java.util.Random;

import edu.cmu.cs.cs214.hw3.person.BikeLuggagePerson;
import edu.cmu.cs.cs214.hw3.person.BikePerson;
import edu.cmu.cs.cs214.hw3.person.CashBikeLuggage;
import edu.cmu.cs.cs214.hw3.person.CashPerson;
import edu.cmu.cs.cs214.hw3.person.CreditCardPerson;
import edu.cmu.cs.cs214.hw3.person.CreditCardWheelchairLuggagePerson;
import edu.cmu.cs.cs214.hw3.person.LuggagePerson;
import edu.cmu.cs.cs214.hw3.person.LuggagesPerson;
import edu.cmu.cs.cs214.hw3.person.OrdinaryPerson;
import edu.cmu.cs.cs214.hw3.person.Person;
import edu.cmu.cs.cs214.hw3.person.RFIDLuggagePerson;
import edu.cmu.cs.cs214.hw3.person.RFIDPerson;
import edu.cmu.cs.cs214.hw3.person.WheelchairLuggagePerson;
import edu.cmu.cs.cs214.hw3.person.WheelchairPerson;
import edu.cmu.cs.cs214.hw3.routeplanner.Itinerary;
import edu.cmu.cs.cs214.hw3.routeplanner.RoutePlanner;

/**
 * Person factory is to generate person randomly and insert persons into
 * simulation
 * 
 * @author zhaoru
 *
 */
public class PersonFactory implements Factory {
	private Random random = new Random();
	private int time;
	private double numNewPeople;
	private double[] simuParameter;
	private Simulation simulation;
	private RoutePlanner rp;
	private static final int TOTAL_RIDERS_PER_DAY = 10000;
	private static final int SECONDS_PER_HOUR = 3600;

	/**
	 * Constructor
	 * 
	 * @param newSimulation
	 *            simulation as a parameter
	 * @param parameter
	 *            parameter list
	 */
	public PersonFactory(Simulation newSimulation, double[] parameter) {
		simulation = newSimulation;
		rp = newSimulation.getRoutePlanner();
		simuParameter = parameter;
		numNewPeople = 0;
	}

	@Override
	public void step() {
		time = simulation.getTime();
		numNewPeople += (Util.getRidersPerSecond(time, TOTAL_RIDERS_PER_DAY) / SECONDS_PER_HOUR);
		if (numNewPeople >= 1) {
			String s, e;
			Person person;
			int numNewPerInt = (int) numNewPeople;
			while (numNewPerInt > 0) {
				s = Util.getRandomStop();
				e = Util.getRandomStop();
				Itinerary itinerary = rp.computeRoute(s, e, time);
				if (itinerary != null) {
					if (!itinerary.isEmpty()) {
						double num = random.nextDouble();
						// CHECKSTYLE:OFF
						if (num < simuParameter[0]) {
							person = new OrdinaryPerson(itinerary);
						} else if (num < simuParameter[1]) {
							person = new CashPerson(itinerary);
						} else if (num < simuParameter[2]) {
							person = new RFIDPerson(itinerary);
						} else if (num < simuParameter[3]) {
							person = new CreditCardPerson(itinerary);
						} else if (num < simuParameter[4]) {
							person = new WheelchairPerson(itinerary);
						} else if (num < simuParameter[5]) {
							person = new BikePerson(itinerary);
						} else if (num < simuParameter[6]) {
							person = new LuggagePerson(itinerary);
						} else if (num < simuParameter[7]) {
							person = new LuggagesPerson(itinerary);
						} else if (num < simuParameter[8]) {
							person = new BikeLuggagePerson(itinerary);
						} else if (num < simuParameter[9]) {
							person = new CashBikeLuggage(itinerary);
						} else if (num < simuParameter[10]) {
							person = new RFIDLuggagePerson(itinerary);
						} else if (num < simuParameter[11]) {
							person = new WheelchairLuggagePerson(itinerary);
						} else {
							person = new CreditCardWheelchairLuggagePerson(
									itinerary);
						}
						// CHECKSTYLE:ON
						person.setSimulation(simulation);
						simulation.insertPerson(person);
						numNewPerInt--;
					}
				}
			}
			numNewPeople = 0;
		}
	}

}
