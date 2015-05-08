package edu.cmu.cs.cs214.hw5.framework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 * This class represent a user, contains all information of this user
 *
 */
public class UserBean {
	private String userName;
	private List<Post> posts;

	/**
	 * Constructor
	 * 
	 * @param newUserName
	 *            is user name
	 * @param newPosts
	 *            is a list of posts
	 */
	public UserBean(String newUserName, List<Post> newPosts) {
		this.userName = newUserName;
		this.posts = newPosts;
	}

	/**
	 * get this user's name
	 * 
	 * @return name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * get all these user's posts
	 * 
	 * @return a list of posts
	 */
	public List<Post> getPosts() {
		return posts;
	}

	/**
	 * get a list of location
	 * 
	 * @return all posts' locationo
	 */
	public List<Location> getPostLocation() {
		List<Location> locations = new ArrayList<Location>();
		if (posts != null && posts.size() != 0) {
			for (Post post : posts) {
				locations.add(post.getLocation());
			}
		}
		return locations;
	}

	/**
	 * get all posts' time
	 * 
	 * @return a list of time, time is long type
	 */
	public List<Long> getPostsTime() {
		List<Long> times = new ArrayList<Long>();
		if (posts != null && posts.size() != 0) {
			for (Post post : posts) {
				times.add(post.getTime());
			}
		}
		return times;
	}

	/**
	 * get this host's posts content
	 * 
	 * @return a list of string represent content
	 */
	public List<String> getPostsContent() {
		List<String> content = new ArrayList<String>();
		HashSet<String> stopWords = getStopWords();
		if (posts != null && posts.size() != 0) {
			for (Post post : posts) {
				if (post.getContent() != null) {
					String postContent = post.getContent();
					String newPost = "";
					String[] postWords = postContent.split("\\W");
					for (String word : postWords) {
						if (stopWords.contains(word.toLowerCase()) == false) {
							newPost += " " + word;
						}
					}
					content.add(newPost);
				}
			}
		}
		return content;
	}

	private HashSet<String> getStopWords() {
		HashSet<String> stopWords = new HashSet<String>();
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("stopWords.txt"));

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] wordsFromText = line.split("\\W");
				for (String word : wordsFromText)
					stopWords.add(word.toLowerCase());
			}

			return stopWords;
		} catch (FileNotFoundException e) {
			System.err.println("Cannot find the file");
		} finally {
			if (scanner != null)
				scanner.close();
		}
		return null;

	}

	/**
	 * find this user's latest location, get from post
	 * 
	 * @return location
	 */
	public Location findLatestLocation() {
		Location currentLocation = null;
		if (posts != null && posts.size() != 0) {
			for (Post post : posts) {
				if (post.getLocation() != null) {
					currentLocation = post.getLocation();
					break;
				}
			}
		}
		return currentLocation;
	}

	/**
	 * get this user's posts frequency use total posts amount divide post time
	 * 
	 * @return frequency as integer type
	 */
	public int findPostsAvgFrequency() {
		int avgFre = 0, timeSum = 0;
		if (posts != null && posts.size() != 0) {
			for (int i = 0; i < posts.size() - 1; i++) {
				timeSum += (posts.get(i + 1).getTime() - posts.get(i).getTime());
			}
			if (posts.size() > 1)
				avgFre = timeSum / (posts.size() - 1);
		}
		return avgFre;
	}
}
