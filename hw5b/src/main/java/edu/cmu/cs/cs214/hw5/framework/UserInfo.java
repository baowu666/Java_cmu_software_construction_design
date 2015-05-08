package edu.cmu.cs.cs214.hw5.framework;

import java.util.List;
import java.util.Map;

/**
 * This class contains all user's information This class will passed to analysis
 * plug in as a parameter Analysis plug in should use this class and it's
 * methods to get all data it want
 *
 */
public interface UserInfo {
	/**
	 * get host
	 * 
	 * @return a userBean type host
	 */
	UserBean getHost();

	/**
	 * set the person you want to analysis
	 * 
	 * @param h
	 *            is a UserBean type user, the host This is the person you want
	 *            to analysis
	 */
	void setHost(UserBean h);

	/**
	 * Get this user's friends
	 * 
	 * @return a list of friends, all these friends is a UserBean
	 */
	List<UserBean> getFriends();

	/**
	 * get this host's name
	 * 
	 * @return a string represent user's name
	 */
	String getHostName();

	/**
	 * get all friend's name of this user
	 * 
	 * @return a list of friends' name of this user
	 */
	List<String> getHostFriendsName();

	/**
	 * pass a user name as parameter Get this user's UserBean Each user has a
	 * unique user name
	 * 
	 * @param userName
	 *            is a string represents this user's name
	 * @return a userBean of this user
	 */
	UserBean getBeanByName(String userName);

	/**
	 * give a user name as parameter and then get this user's UserBean return
	 * this user's all posts
	 * 
	 * @param userName
	 *            is a string represent this user's name
	 * @return a list of post of this user
	 */
	List<Post> getUserPostsByName(String userName);

	/**
	 * Get host's post and these posts are sorted by time using ascending order
	 * 
	 * @return a list of post with ascending order
	 */
	List<Post> getSortPostsByTime();

	/**
	 * Get host's post and these posts are sorted by posts' content length using
	 * ascending order
	 * 
	 * @return a list of post with content length's ascending order
	 */
	List<Post> getSortPostsByContentLength();

	/**
	 * get all this host's posts' location
	 * 
	 * @return a list of location that appears in this host's posts
	 */
	List<Location> getHostLocationsOfPosts();

	/**
	 * get all this host's post's time
	 * 
	 * @return a list of time of all these posts
	 */
	List<Long> getHostPostsTimes();

	/**
	 * get this host's posts content, each content is a string
	 * 
	 * @return a list of content as string type
	 */
	List<String> getHostPostsContent();

	/**
	 * get the location lists given a posts list
	 * 
	 * @param posts
	 *            the post list
	 * @return the location list
	 */
	List<Location> getLocationsOfPosts(List<Post> posts);

	/**
	 * find the user's latest location
	 * 
	 * @param userName
	 *            user name to find the location
	 * @return the user's latest location
	 */
	Location findUserLatestLocation(String userName);

	/**
	 * find the user's posts average frequency, which is the gap the user posts
	 * posts
	 * 
	 * @param userName
	 *            user name to find the posts average frequency
	 * @return user name to find the posts average frequency
	 */
	int findUserPostsAvgFrequency(String userName);

	/**
	 * get the friend and location pairs of a user
	 * 
	 * @return the friend and location pairs of a user
	 */
	Map<String, Location> getFriendLocationPairs();
}
