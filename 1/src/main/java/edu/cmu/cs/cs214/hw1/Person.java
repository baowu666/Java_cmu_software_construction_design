package edu.cmu.cs.cs214.hw1;

import java.util.ArrayList;

/**
 * Class of person, the unit to manipulate.
 * 
 * @author Ru Zhao
 *
 */
public class Person {
	private String person;
	private ArrayList<Person> connectors = new ArrayList<Person>(); /* list of a person's friends */
	private int distance; 											/* distance from the root */
	private boolean visited = false; 								/* if a person is gone through, originally flase */

	/**
	 * Constructor.
	 * 
	 * @param newPerson The name of the person.
	 */
	public Person(String newPerson) {
		this.person = newPerson;
	}

	/**
	 * Method to get person's name.
	 * 
	 * @return the person's name.
	 */
	public String getName() {
		return this.person;
	}

	/**
	 * Method to tell if the person is visited or modified.
	 * 
	 * @return a boolean whether a person is visited or not.
	 */
	public boolean getVisited() {
		return this.visited;
	}

	/**
	 * Method to set if the person is visited or not.
	 * 
	 * @param visit A boolean to set to the person's attribute.
	 */
	public void setVisited(boolean visit) {
		this.visited = visit;
	}

	/**
	 * Method to get the distance from root person.
	 * 
	 * @return the distance from root person.
	 */
	public int getDistance() {
		return this.distance;
	}

	/**
	 * Method to set the distance from root person.
	 * 
	 * @param newDist Updated distance from root person.
	 */
	public void setDistance(int newDist) {
		this.distance = newDist;
	}

	/**
	 * Method to calculate the number of a person's friends.
	 * 
	 * @return the number of a person's friends.
	 */
	public int getNum() {
		return connectors.size();
	}

	/**
	 * Method to get certain connector.
	 * 
	 * @param index The index of the person's friend.
	 * @return The person of the certain index.
	 */
	public Person getConnector(int index) {
		return connectors.get(index);
	}

	/**
	 * Method to get all friends of a person.
	 * 
	 * @return The list of the person's all friends.
	 */
	public ArrayList<Person> getConnectors() {
		return connectors;
	}

	/**
	 * Method to add a friend to a person's connector list.
	 * 
	 * @param newConnector The person to add.
	 */
	public void setConnector(Person newConnector) {
		connectors.add(newConnector);
	}
}
