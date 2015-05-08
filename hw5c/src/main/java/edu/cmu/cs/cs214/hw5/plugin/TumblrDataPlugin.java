package edu.cmu.cs.cs214.hw5.plugin;

import java.util.Date;
import java.util.List;

import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.exceptions.JumblrException;
import com.tumblr.jumblr.types.AnswerPost;
import com.tumblr.jumblr.types.AudioPost;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.ChatPost;
import com.tumblr.jumblr.types.LinkPost;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.QuotePost;
import com.tumblr.jumblr.types.TextPost;
import com.tumblr.jumblr.types.VideoPost;

import edu.cmu.cs.cs214.hw5.framework.UserData;

/**
 * this is a data plugin for tumblr
 * @author zlyu
 *
 */
public class TumblrDataPlugin implements DataPlugin {
	private JumblrClient client;
	private static final int MAX_POST_NUM = 200;
	
	/**
	 * constructor: initialize the tumblr client
	 */
	public TumblrDataPlugin(){
		client = new JumblrClient(
				  "FuH05RihTRFpCYDmPHlgFBIaVRY2KJjFWOq9mhSyUAd0hzbfrJ",
				  "WGeMFdOJXsU814JOmHnnKkbJ0Gmpg5yj9FjR5OZXpyLWjyQeLM"
				);
				client.setToken(
				  "2FAbxwpCf56hHZbEhlZdFT5m8o0j5fj2AzN27jYOk7srGCCzL8",
				  "jd3gERJJeDWxSr09g1faB1oTlVkeYW4UStr4kWW0og9K1WRAsy"
				);
		
	}

	@Override
	public String getName() {
		return "Tumblr data";
	}

	@Override
	public UserData getUserData(String userName) {
		Blog blog;
		try{
			blog = client.blogInfo(userName);
			if(blog==null){
				return null;
			}
		}catch(JumblrException e){
			return null;
		}
		
		UserData user = new UserData(userName, "Tumblr");
		
		/* tumblr doesn't provide location */
		user.setLocation("");
		
		setPosts(blog, user);
		user.setId(blog.getName());
		user.setFollowees(blog.getFollowersCount());
		user.setFollowers(blog.getFollowersCount());
		
		return user;
	}

	/**
	 * this method take in a blog and a userData object.
	 * it will get recent posts of the given blog, and
	 * extract contents and timestamps of posts and 
	 * put these data into userdata. 
	 * @param b blog object
	 * @param usr userData
	 */
	private void setPosts(Blog b, UserData usr){
		edu.cmu.cs.cs214.hw5.framework.Post post;
		List<Post> posts = b.posts(null);
		int maxNum = MAX_POST_NUM;
		if(maxNum>posts.size())
			maxNum = posts.size();
			
		for(Post p:posts.subList(0, maxNum)){
			String content = null;
			Date date = new Date(p.getTimestamp());
			String type = p.getType();
			if (type.equals("photo")) {
				PhotoPost recentPost = (PhotoPost) p;
				content = recentPost.getCaption();
			}else if (type.equals("video")) {
				VideoPost recentPost = (VideoPost) p;
				content = recentPost.getCaption();
			}else if (type.equals("quote")) {
				QuotePost recentPost = (QuotePost) p;
				content = recentPost.getText();
			}else if (type.equals("link")) {
				LinkPost recentPost = (LinkPost) p;
				content = recentPost.getDescription();
			}else if (type.equals("audio")) {
				AudioPost recentPost = (AudioPost) p;
				content = recentPost.getCaption();
			}else if (type.equals("text")) {
				TextPost recentPost = (TextPost) p;
				content = recentPost.getBody();
			}else if (type.equals("answer")) {
				AnswerPost recentPost = (AnswerPost) p;
				content = recentPost.getAnswer();
			}else if (type.equals("chat")) {
				ChatPost recentPost = (ChatPost) p;
				content = recentPost.getBody();
			}
			post = new edu.cmu.cs.cs214.hw5.framework.Post(content, date);
			usr.addPost(post);			
		}
		
		
	}
}
