package edu.cmu.cs.cs214.hw5.framework;

/**
 * This class represent user's post
 *
 */
public class Post {
	private String content;
	private Location location;
	private long time;

	/**
	 * constructor
	 * 
	 * @param newContent
	 *            is this post's content, is a string type
	 * @param newLocation
	 *            is this post's location, is a Location type
	 * @param newTime
	 *            is this post's time, is a long type
	 */
	public Post(String newContent, Location newLocation, long newTime) {
		this.content = newContent;
		this.location = newLocation;
		this.time = newTime;
	}

	/**
	 * get this post's content
	 * 
	 * @return content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * get this post's location
	 * 
	 * @return location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * get this post's time
	 * 
	 * @return time
	 */
	public long getTime() {
		return time;
	}
}
