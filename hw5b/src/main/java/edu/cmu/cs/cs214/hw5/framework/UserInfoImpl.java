package edu.cmu.cs.cs214.hw5.framework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class contains all user's information This class will passed to analysis
 * plug in as a parameter Analysis plug in should use this class and it's
 * methods to get all data it want
 *
 */
public class UserInfoImpl implements UserInfo {
	private UserBean host;
	private List<UserBean> friends;

	/**
	 * constructor
	 * 
	 * @param newHost
	 *            is the host, it UserBean type In UserBean, it contains this
	 *            user's name and all posts
	 * @param newFriends
	 *            is a list of friends, all of these friends are UserBean type
	 */
	public UserInfoImpl(UserBean newHost, List<UserBean> newFriends) {
		this.host = newHost;
		this.friends = newFriends;
	}

	@Override
	public UserBean getHost() {
		return host;
	}

	@Override
	public void setHost(UserBean h) {
		this.host = h;
	}

	@Override
	public List<UserBean> getFriends() {
		return friends;
	}

	@Override
	public String getHostName() {
		return host.getUserName();
	}

	@Override
	public List<String> getHostFriendsName() {
		List<String> friendsNames = new ArrayList<String>();
		for (UserBean friend : friends) {
			friendsNames.add(friend.getUserName());
		}
		return friendsNames;
	}

	@Override
	public UserBean getBeanByName(String userName) {
		if (userName.equals(host.getUserName()))
			return host;
		UserBean bean = null;
		for (UserBean friend : friends) {
			if (userName.equals(friend.getUserName()))
				return friend;
		}
		return bean;
	}

	@Override
	public List<Post> getUserPostsByName(String userName) {
		UserBean user = getBeanByName(userName);
		if (user != null)
			return user.getPosts();
		return new ArrayList<Post>();
	}

	@Override
	public List<Post> getSortPostsByTime() {
		List<Post> posts = getHost().getPosts();
		Collections.sort(posts, new Comparator<Post>() {

			@Override
			public int compare(Post p1, Post p2) {
				// careful
				return (int) (p1.getTime() - p2.getTime());
			}

		});
		return posts;
	}

	@Override
	public List<Post> getSortPostsByContentLength() {
		List<Post> posts = new ArrayList<Post>();
		Collections.sort(posts, new Comparator<Post>() {

			@Override
			public int compare(Post p1, Post p2) {
				return p1.getContent().length() - p2.getContent().length();
			}

		});
		return posts;
	}

	@Override
	public List<Location> getHostLocationsOfPosts() {
		return host.getPostLocation();
	}

	@Override
	public List<Long> getHostPostsTimes() {
		return host.getPostsTime();
	}

	@Override
	public List<String> getHostPostsContent() {
		return host.getPostsContent();
	}

	@Override
	public List<Location> getLocationsOfPosts(List<Post> posts) {
		List<Location> locations = new ArrayList<Location>();
		if (posts != null && posts.size() != 0) {
			for (Post p : posts) {
				locations.add(p.getLocation());
			}
		}
		return locations;
	}

	@Override
	public Location findUserLatestLocation(String userName) {
		UserBean user = getBeanByName(userName);
		return user.findLatestLocation();
	}

	@Override
	public int findUserPostsAvgFrequency(String userName) {
		UserBean user = getBeanByName(userName);
		return user.findPostsAvgFrequency();
	}

	@Override
	public Map<String, Location> getFriendLocationPairs() {
		Map<String, Location> map = new HashMap<String, Location>();
		for (UserBean friend : friends) {
			map.put(friend.getUserName(),
					findUserLatestLocation(friend.getUserName()));
		}
		return map;
	}
}
