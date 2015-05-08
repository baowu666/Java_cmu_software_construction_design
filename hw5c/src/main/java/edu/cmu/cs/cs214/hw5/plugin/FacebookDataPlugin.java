package edu.cmu.cs.cs214.hw5.plugin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.cmu.cs.cs214.hw5.framework.Post;
import edu.cmu.cs.cs214.hw5.framework.UserData;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Friend;
import facebook4j.IdNameEntity;
import facebook4j.ResponseList;
import facebook4j.User;
import facebook4j.auth.AccessToken;

/**
 * This class is Facebook data plugin
 *
 */
public class FacebookDataPlugin implements DataPlugin{
	private static final String FACEBOOK = "Facebook";


	@Override
	public String getName() {
		return FACEBOOK;
	}

	@Override
	public UserData getUserData(String userName) {
		Facebook facebook;
		facebook = new FacebookFactory().getInstance();
		AccessToken token = facebook.getOAuthAccessToken();
		facebook.setOAuthAccessToken(token);
		
		
		UserData userData = new UserData(userName, FACEBOOK);
		try {
			User user = facebook.getUser(userName);
			String userID = user.getId();
			IdNameEntity loc = user.getLocation();
			String userLocation;
			if (loc != null) {
				userLocation = loc.toString();
			} else {
				userLocation = "";
			}
			int followers = 0;
			int followees = 0;

			
			ResponseList<Friend> friends = facebook.getFriends(userID);
			
			if (friends != null) {
				followers = friends.size();
				followees = friends.size();
			}
			
			
			userData.setLocation(userLocation);
			userData.setId(userID);
			userData.setFollowers(followers);
			userData.setFollowees(followees);
			
			List<Post> postList = getPosts(facebook, userID);
			
			if (postList == null || postList.size() == 0) {
				userData.setErroMsg("The user: " + userName + " has no posts!");
				return null;
			}
			
			for (Post post : postList) {
				userData.addPost(post);
			}
			
			return userData;
		} catch (FacebookException e) {
			userData.setErroMsg("The user: " + userName + "is invalid!");
		}
		return null;
		
		
	}
	
	/**
	 * This method can get this user's posts
	 * @param facebook is Facebook
	 * @param userName is the user's id
	 * @return a list of posts
	 */
	private List<Post> getPosts(Facebook facebook, String userName) {
		List<Post> postList = new ArrayList<Post>();
		List<facebook4j.Post> facebookPosts = new ArrayList<facebook4j.Post>();
		try {
			facebookPosts = facebook.getPosts(userName);
		} catch (FacebookException e) {
			return null;
		}
		
		for (facebook4j.Post p : facebookPosts) {
			String content = p.getMessage();
			Date date = p.getCreatedTime();
			Post newPost = new Post(content, date);
			postList.add(newPost);
		}
		
		return postList;
		
	}

}
