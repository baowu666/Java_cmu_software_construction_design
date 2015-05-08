package edu.cmu.cs.cs214.hw5.framework;

/**
 * This class represent this post's location
 *
 */
public class Location {
	private double lati, longi;

	/**
	 * Constructor
	 * 
	 * @param newLati
	 *            is latitude of the location
	 * @param newLongi
	 *            is longitude of the location
	 */
	public Location(double newLati, double newLongi) {
		lati = newLati;
		longi = newLongi;
	}

	/**
	 * get latitude
	 * 
	 * @return latitude
	 */
	public double getLati() {
		return lati;
	}

	/**
	 * get longitude
	 * 
	 * @return longitude
	 */
	public double getLongi() {
		return longi;
	}
}
