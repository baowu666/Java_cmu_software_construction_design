package edu.cmu.cs.cs214.hw3.simulation;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.SwingUtilities;

import edu.cmu.cs.cs214.hw3.BusFactory;
import edu.cmu.cs.cs214.hw3.Factory;
import edu.cmu.cs.cs214.hw3.PersonFactory;
import edu.cmu.cs.cs214.hw3.gui.SimulationUI;

/**
 * Main simulation to simulate: all persons do not use any kind of fast boarding
 * method (card, rfid, etc). There is no mutidoor buses. This simulation is to
 * test if the fast boarding method influence the delay time of whole city buses
 * 
 * @author zhaoru
 *
 */
public class MainSimulationThree extends SimulationClass {
	/**
	 * Starts up the simulation and the GUI representation
	 *
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				SimulationClass simulation = new SimulationClass();
				Factory busFactory = null;
				// CHECKSTYLE:OFF
				double[] busPara = { 0.1, 0.2, 0.5, 0.7, 0.8, 0.9, 1 };// 7
				double[] personPara = { 0.2, 0.2, 0.2, 0.2, 0.3, 0.4, 0.5, 0.6,
						0.7, 0.7, 0.7, 0.9, 1 };// 13
				// CHECKSTYLE:ON
				try {
					busFactory = new BusFactory(simulation, busPara);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Factory personFactory = new PersonFactory(simulation,
						personPara);
				simulation.addFactory(personFactory);
				simulation.addFactory(busFactory);
				SimulationUI simulationUI = new SimulationUI(simulation);
				simulationUI.show();
			}
		});
	}
}
