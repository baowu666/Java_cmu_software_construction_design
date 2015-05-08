package edu.cmu.cs.cs214.hw5.plugin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.cmu.cs.cs214.hw5.framework.Location;
import edu.cmu.cs.cs214.hw5.framework.Post;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Friend;
import facebook4j.ResponseList;
import facebook4j.User;
import facebook4j.auth.AccessToken;

/**
 * this is a data plugin fetching information from Facebook API and provide
 * functionalities to provide formated data requested by framework
 * 
 * @author Team28
 *
 */
public class FacebookData implements DataPlugin {
	private static Facebook facebook;

	/**
	 * constructor
	 */
	public FacebookData() {
		facebook = new FacebookFactory().getInstance();
		AccessToken token = facebook.getOAuthAccessToken();
		facebook.setOAuthAccessToken(token);
	}

	@Override
	public String getSourceName() {
		return "Facebook data";
	}

	@Override
	public List<Post> extractPostsByName(String userName, int postNum) {
		User user;
		try {
			user = facebook.getUser(userName);
			System.out.println("sdf");
		} catch (FacebookException e1) {
			e1.printStackTrace();
			return null;
		}
		if (user == null)
			return null;
		List<Post> results = new ArrayList<Post>();
		try {
			List<facebook4j.Post> posts = facebook.getPosts(userName);
			for (facebook4j.Post p : posts) {
				Date date = p.getCreatedTime();
				String content = p.getMessage();
				Location loc = null;
				results.add(new Post(content, loc, date.getTime()));
			}
			if (results.size() <= postNum)
				return results;
			return results.subList(0, postNum);

		} catch (FacebookException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<String> extractConnectorsByName(String userName, int fNum) {
		List<String> results = new ArrayList<String>();
		try {
			ResponseList<Friend> friends = facebook.getFriends(userName);
			for (Friend f : friends) {
				results.add(f.getId());
			}
			return results;
		} catch (FacebookException e) {
			System.out.println("fail to get friends list for " + userName);
			return results;
		}
	}

}
