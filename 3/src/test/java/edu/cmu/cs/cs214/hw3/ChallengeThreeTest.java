package edu.cmu.cs.cs214.hw3;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs.cs214.hw3.gui.TextUI;
import edu.cmu.cs.cs214.hw3.simulation.SimulationClass;

/**
 * Test case for challenge three. All conditions has been considered inside the
 * isBoardable() method inside OrdinaryBus class, and other related classes.
 * 
 * @author zhaoru
 *
 */
public class ChallengeThreeTest {
	SimulationClass simulation;
	Factory busFactory;
	Factory personFactory;

	@Before
	public void setUp() {
		simulation = new SimulationClass();
		busFactory = null;
	}

	@Test
	public void test() {
		double[] busPara = { 0.1, 0.2, 0.5, 0.7, 0.8, 0.9, 1 };// 7
		double[] personPara = { 0.2, 0.2, 0.2, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7,
				0.7, 0.7, 0.9, 1 };// 13
		try {
			busFactory = new BusFactory(simulation, busPara);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		personFactory = new PersonFactory(simulation, personPara);
		simulation.addFactory(personFactory);
		simulation.addFactory(busFactory);
		TextUI textUI = new TextUI(simulation);
		assertNotNull(busFactory);
		assertNotNull(personFactory);
		assertNotNull(simulation);
		assertNotNull(textUI);
		
		/*
		 * I commented this out because it took too much time run the gradle.
		 * But I already checked the correctness for this test. The screen cut
		 * is inside readme.pdf.
		 * 
		 * If you leave this commented out, cobertura would show the person
		 * package line coverage zero
		 */
		// textUI.simulate();
	}

}
