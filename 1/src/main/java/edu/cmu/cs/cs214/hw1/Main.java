package edu.cmu.cs.cs214.hw1;

/**
 * Class to help you test your graph implementation.
 */
public class Main {
	/**
	 * Main method to help you test your graph implementation.
	 * 
	 * @param args
	 *            Arguments to the program.
	 */
	public static void main(String[] args) {
		// Un-comment the following code (CTRL + /).

		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		System.out.println(graph.getDistance(rachel, ross)); // should print 1
		System.out.println(graph.getDistance(rachel, ben)); // should print 2
		System.out.println(graph.getDistance(rachel, rachel)); // should print 0
		System.out.println(graph.getDistance(rachel, kramer)); // should print
																// -1

		// You should write more samples to be confident in the
		// correctness of your solution.
	}
}
