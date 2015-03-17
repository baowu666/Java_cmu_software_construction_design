package edu.cmu.cs.cs214.hw3.simulation;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.SwingUtilities;

import edu.cmu.cs.cs214.hw3.BusFactory;
import edu.cmu.cs.cs214.hw3.Factory;
import edu.cmu.cs.cs214.hw3.PersonFactory;
import edu.cmu.cs.cs214.hw3.gui.SimulationUI;

/**
 * A Main class initializes a simulation and runs it.
 */
public class BasicSimulation {
    /**
     * Starts up the simulation and the GUI representation
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BasicSimulation().createAndShowSimulation();
            }
        });
    }

    /**
     * Sets up the simulation and starts the GUI
     */
    public void createAndShowSimulation() {
        SimulationClass simulation = new SimulationClass();
        Factory busFactory = null;
        // CHECKSTYLE:OFF
        double[] busPara = {1,0,0,0,0,0,0};//7
        double[] personPara = {1,0,0,0,0,0,0,0,0,0,0,0,0};//13
    	// CHECKSTYLE:ON
        try {
			busFactory = new BusFactory(simulation, busPara);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        Factory personFactory = new PersonFactory(simulation, personPara);
        simulation.addFactory(personFactory);
        simulation.addFactory(busFactory);
        SimulationUI simulationUI = new SimulationUI(simulation);
        simulationUI.show();
    }

}
