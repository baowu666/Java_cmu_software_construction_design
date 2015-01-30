package edu.cmu.cs.cs214.hw1;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class to help find graph and distance between friends.
 * 
 * @author Ru Zhao
 *
 */
public class FriendshipGraph {
	private ArrayList<Person> people = new ArrayList<Person>(); /* a list to store each vertex */

	/**
	 * Method to indicate a vertex.
	 * 
	 * @param newPerson Argument of a Person class to add vertex.
	 */
	public void addVertex(Person newPerson) {
		if (people.contains(newPerson)) {
			System.out.println("This person is already exist!"); /* avoid same name */
		}
		people.add(newPerson);
	}

	/**
	 * Method to add a friendship of two persons.
	 * 
	 * @param newPerson1 Argument that the friendship is from.
	 * @param newPerson2 Argument that the friendship is to.
	 * 
	 */
	public void addEdge(Person newPerson1, Person newPerson2) {
		newPerson1.setConnector(newPerson2);
	}

	/**
	 * Method to calculate the distance between two persons
	 * 
	 * @param newPerson1 Argument that the friendship is from.
	 * @param newPerson2 Argument that the friendship is to.
	 * @return the distance between two persons.
	 */
	public int getDistance(Person newPerson1, Person newPerson2) {

		if (newPerson1.equals(newPerson2)) {
			return 0; 												 /* distance of oneself */
		}

		LinkedList<Person> findDist = new LinkedList<Person>(); 	 /* store the temporary elements using BFS */
		LinkedList<Person> visitedPeople = new LinkedList<Person>(); /* store all people with certain distance parameter */
		findDist.add(newPerson1);
		newPerson1.setVisited(true);
		newPerson1.setDistance(0);
		
		while (!findDist.isEmpty()) { 								 /* go through all elements input */
			Person cur = findDist.poll(); 							 /* poll the current element from top */
			cur.setVisited(true);
			visitedPeople.add(cur);
			for (Person newPerson : cur.getConnectors()) { 			 /* iterate all connectors for the current person */
				if (newPerson.getVisited() == false) {
					newPerson.setDistance(cur.getDistance() + 1);    /* update each person's distance */
					findDist.add(newPerson);
				}
			}
		}

		if (!(people.contains(newPerson1) && people.contains(newPerson2))) {
			throw new IllegalArgumentException("Error! Invalid person input!"); /* not inside the list */
		}

		for (Person person : visitedPeople) {
			person.setVisited(false); 								 /* restore visit attribute for next use */
		}
		if (visitedPeople.contains(newPerson2)) {
			return newPerson2.getDistance();
		}
		return -1; 													 /* no friendship found*/
	}
}
