package edu.cmu.cs.cs214.hw2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Allows user to select start stop, end stop, and departure times, finding
 * efficient paths in Pittsburgh's transit system.
 * 
 * @author 15-214 course staff
 */
public class TextInterface {
	private static final String TRANSIT_FILE = "src/main/resources/sixtyones_stop_times.txt"; // all_stop_times.txt
	// If the TRANSIT_FILE is changed, the filename inside
	// RoutePlannerBuilderClassTest and RoutePlannerClassTest should also be
	// changed
	private static final int MAX_WAIT = 1200; // don't make rider wait more than

	// The first start wait can exceed 20 min
	// 20 minutes

	/**
	 * Allows user to select start stop, end stop, and departure times, finding
	 * efficient paths in Pittsburgh's transit system.
	 * 
	 * @param args
	 *            Command-line arguments to the program (ignored).
	 * @throws IOException
	 *             if there is IOException
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("Building the route planner, please wait...");
		RoutePlannerBuilder builder = new RoutePlannerBuilderClass(
				TRANSIT_FILE, MAX_WAIT);

		RoutePlanner planner = builder.build(TRANSIT_FILE, MAX_WAIT);

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		new TextInterface().run(planner, in);

	}

	/**
	 * helper method
	 * 
	 * @param planner
	 *            RoutePlanner
	 * @param in
	 *            BufferedReader
	 * @throws IOException
	 *             if there is IOException
	 */
	private void run(RoutePlanner planner, BufferedReader in)
			throws IOException {
		while (true) {
			Stop srcStop = findStop("Enter a substring for the source stop: ",
					planner, in);
			Stop destStop = findStop(
					"Enter a substring for the destination stop: ", planner, in);
			int time = getDepartureTime(
					"Enter your intended departure time in seconds since midnight: ",
					in);

			Itinerary itinerary = planner.computeRoute(srcStop, destStop, time);
			System.out.println(itinerary.getInstructions());
		}
	}

	/**
	 * Interacts with the user (using stdin/stdout) to select a stop
	 * 
	 * @param prompt
	 *            String to print to ask for user input.
	 * @param planner
	 *            RoutePlanner to use to find matching stops.
	 * @param in
	 *            BufferedReader to use to obtain user input.
	 * @return Stop selected by the user, not null
	 * @throws IOException
	 *             if there is IOException
	 */
	private Stop findStop(String prompt, RoutePlanner planner, BufferedReader in)
			throws IOException {
		while (true) {
			System.out.print(prompt);

			String searchString = in.readLine();
			if (searchString == null) {
				// terminating
				System.exit(0);
			}
			List<Stop> matchingStops = planner
					.findStopsBySubstring(searchString);
			if (matchingStops.isEmpty()) {
				System.out
						.println("Sorry, there are no stops matching your search string.  Try again.");
				continue;
			}
			if (matchingStops.size() == 1)
				return matchingStops.get(0);
			else
				return chooseFromStopList("Multiple stops found:",
						matchingStops, in);
		}
	}

	/**
	 * Given a list of stops, prints the list and reads a string as user input
	 * from stdin, returning the stop selected by the user.
	 * 
	 * @param prompt
	 *            String to print to ask for user input.
	 * @param stopList
	 *            List of stops from which user will select a stop.
	 * @param in
	 *            BufferedReader to use to obtain user input.
	 * @return The stop the user selected from the list.
	 * @throws IOException
	 *             if there is IOException
	 */
	private Stop chooseFromStopList(String prompt, List<Stop> stopList,
			BufferedReader in) throws IOException {
		int choice = -1;
		String choiceString = null;
		while (true) {
			System.out.println(prompt);
			for (int i = 0; i < stopList.size(); ++i) {
				System.out.println(String.valueOf(i + 1) + ".\t"
						+ stopList.get(i).getName()); // I added .getName()
			}
			System.out.print("Select a stop: ");
			try {
				choiceString = in.readLine();
				if (choiceString == null) {
					System.exit(0);
				}
				choice = Integer.parseInt(choiceString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			if (choice >= 1 && choice <= stopList.size()) {
				return stopList.get(choice - 1);
			}
			System.out.println(choiceString
					+ " is not a valid choice.  Please try again.");
		}
	}

	/**
	 * Reads a departure time (in seconds since midnight) entered by the user.
	 * 
	 * @param prompt
	 *            String to print to ask for user input.
	 * @param in
	 *            BufferedReader to use to obtain user input.
	 * @return The time (in seconds since midnight) entered by the user.
	 * @throws IOException
	 *             if there is IOException
	 */
	private int getDepartureTime(String prompt, BufferedReader in)
			throws IOException {
		int time = -1;
		String choiceString = null;
		while (true) {
			System.out.print(prompt);
			try {
				choiceString = in.readLine();
				if (choiceString == null) {
					System.exit(0);
				}
				time = Integer.parseInt(choiceString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			if (time >= 0) {
				return time;
			}
			System.out.println(choiceString
					+ " is not a valid choice.  Please try again.");
		}
	}
}
