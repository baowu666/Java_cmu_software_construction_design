package edu.cmu.cs.cs214.hw5.plugin;

import java.util.List;

import edu.cmu.cs.cs214.hw5.framework.Post;

/**
 * interface for data plugin. framework will call these methods when querying
 * data
 * 
 * @author Team28
 *
 */
public interface DataPlugin {
	/**
	 * this methods take in a user's name and max number of posts, return the
	 * most recent posts of the user. the number of the posts is specified by
	 * the postNum. if the total number of posts of the user is smaller than
	 * postNumer, return all of these posts. if the user specified by the
	 * provided user name doesn't exist, return null to notify the framework. if
	 * the user have no posts, please return an empty list instead of null. If
	 * reaching rate limit when fetching data, catch the exception and return
	 * null to notify the framework.
	 * 
	 * Post class: post is a framework defined class. You should provide
	 * location:Location, time:long and content:string of the post. if location
	 * is not available, set location as null. time is the number of
	 * milliseconds since January 1, 1970, 00:00:00.
	 * 
	 * @param userName
	 *            name of the user
	 * @param postNum
	 *            number of posts
	 * @return the recent posts of the user
	 */
	List<Post> extractPostsByName(String userName, int postNum);

	/**
	 * given the userName, and number of friends, return a list of friends of
	 * this user. friends is stand for by their screen name or user id in
	 * network. If no friends exists, return a empty list of strings. If
	 * reaching rate limit when fetching data, return null to notify framework.
	 * if the user don't have connectors, return a empty list rather than a
	 * null.
	 * 
	 * @param userName
	 *            name of the user
	 * @param fNum
	 *            friend number
	 * @return a list of friends
	 */
	List<String> extractConnectorsByName(String userName, int fNum);

	/**
	 * get the name of data source, framework will call this method to notify
	 * user the name of network
	 * 
	 * @return the name of the data source.
	 */
	String getSourceName();
}
