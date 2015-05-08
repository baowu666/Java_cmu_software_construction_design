package edu.cmu.cs.cs214.hw5.framework;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test user info
 *
 */
public class UserInfoTest {
	private UserInfoImpl userInfo;

	@Before
	public void setUp() {
		List<Post> hostPost = new ArrayList<Post>();
		hostPost.add(new Post("Host", new Location(0, 0), 0));
		UserBean host = new UserBean("host", hostPost);

		List<UserBean> friends = new ArrayList<UserBean>();

		List<Post> friend1Post = new ArrayList<Post>();
		friend1Post.add(new Post("Friend1", new Location(1, 1), 1));
		friend1Post.add(new Post("Friend1", new Location(10, 10), 10));
		UserBean friend1 = new UserBean("friend1", friend1Post);

		List<Post> friend2Post = new ArrayList<Post>();
		friend2Post.add(new Post("Friend2", new Location(2, 2), 2));
		friend2Post.add(new Post("Friend2", new Location(20, 20), 20));
		UserBean friend2 = new UserBean("friend2", friend2Post);

		friends.add(friend1);
		friends.add(friend2);

		userInfo = new UserInfoImpl(host, friends);
	}

	@Test
	public void test() {
		List<String> friendsNames = userInfo.getHostFriendsName();
		assertEquals(friendsNames.get(0), "friend1");
		assertEquals(friendsNames.size(), 2);
		assertEquals(userInfo.getBeanByName("host").getUserName(), "host");
		assertNotNull(userInfo.getSortPostsByTime());
		assertNotNull(userInfo.getSortPostsByContentLength());
		assertNotNull(userInfo.getHostLocationsOfPosts());
		assertNotNull(userInfo.getHostPostsTimes());
		assertNotNull(userInfo.getHostPostsContent());
		assertNotNull(userInfo.getLocationsOfPosts(userInfo.getHost()
				.getPosts()));
		assertEquals((int) userInfo.findUserLatestLocation("host").getLati(), 0);
		assertEquals((int) userInfo.findUserLatestLocation("host").getLongi(),
				0);
		assertEquals(userInfo.findUserPostsAvgFrequency("host"), 0);
		assertEquals(userInfo.findUserPostsAvgFrequency("friend1"), 9);
		assertEquals(userInfo.findUserPostsAvgFrequency("friend2"), 18);
		assertNotNull(userInfo.getFriendLocationPairs());
		assertNotNull(userInfo.getUserPostsByName("host"));
	}

}
