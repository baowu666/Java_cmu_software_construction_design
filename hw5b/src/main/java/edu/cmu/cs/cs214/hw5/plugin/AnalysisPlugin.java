package edu.cmu.cs.cs214.hw5.plugin;

import javax.swing.JPanel;

import edu.cmu.cs.cs214.hw5.framework.UserInfoImpl;

/**
 * This interface is analysis plug in. There are two method for analysis plug
 * in, analyze(UserInfor) and getName(); getName() method just return your
 * analysis plugin's name analyze(UserInfo) return a JPanel to framework If some
 * of the data you want to use is not available, you should still return JPanel
 * In this situation, your JPanel can has a JLabel or other type of text, image
 * to show there is not available data But no matter what, you should return a
 * JPanel to framework
 * 
 * analyze method passes UserInfo as parameter This UserInfo can provide all
 * kinds of data that analysis plug in should use
 * 
 * There are many methods you can use in UserInfo class, here I list all methods
 * in UserInfo class getHostName() method return the person's name
 * getHostFriendsName() method return a list of this person's friends name
 * getUserPostsByName(String: Name) method return this user's posts
 * getSortPostsByTime() method return a list of host's post, and these posts are
 * sorted by time getSortPostsByContentLength() method return a list of host's
 * posts, and these posts are sorted by length getHostLocationsOfPosts() method
 * return a list of location of this host's all posts getHostPostsTimes() method
 * returns a list of time of posts, time is "long" type getHostPostsContent()
 * method return a list of posts' content, content is "string" type, the content
 * has removed all stop words getLocationsOfPosts(Location) methods return a
 * list of post, all these posts' location equals the location passed as
 * parameter findUserLatestLocation() method return host's latest location
 * findUserPostsAvgFrequency() method return host's posts' frequency, return
 * type is integer getFriendLocationPairs() method return this host's friends
 * and latest location
 * 
 * By using some of these method, analysis plug in can get all data. Our
 * framework has some pre-processing for original data.
 *
 */
public interface AnalysisPlugin {

	/**
	 * do all analysis work in this method
	 * 
	 * @param userInfo
	 *            is a class which contains all the data in framework
	 * @return a JPanel to framework if the data used by analysis plug in is not
	 *         available Analysis plug in should also return a JPanel and
	 *         display some error message on the JPanel
	 */
	JPanel analyze(UserInfoImpl userInfo);

	/**
	 * get this analysis plugin's name
	 * 
	 * @return analysis plug in's name
	 */
	String getName();
}
