package edu.cmu.cs.cs214.hw5.plugin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

import edu.cmu.cs.cs214.hw5.framework.Location;
import edu.cmu.cs.cs214.hw5.framework.Post;
import twitter4j.GeoLocation;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

/**
 * this is a data plugin fetching information from twitter and provide
 * functionalities to provide formated data requested by framework
 * 
 * @author Team28
 *
 */
public class TwitterData implements DataPlugin {
	private Twitter twitter;

	/**
	 * constructor
	 */
	public TwitterData() {
		twitter = TwitterFactory.getSingleton();
	}

	@Override
	public String getSourceName() {
		return "Twitter data";
	}

	@Override
	public List<Post> extractPostsByName(String userName, int postNum) {
		List<Post> results = new ArrayList<Post>();
		User user = null;

		try {
			user = twitter.showUser(userName);
			if (user == null)
				return null;
		} catch (TwitterException e) {
			return null;
		}

		List<Status> tweets = new ArrayList<Status>();
		try {
			tweets.addAll(twitter.getUserTimeline(userName, new Paging(1,
					postNum)));

		} catch (TwitterException e) {
			System.out.println("Fail to extract tweets for " + userName);
		}
		for (Status tweet : tweets) {
			String content = tweet.getText();
			GeoLocation g = tweet.getGeoLocation();
			Date d = tweet.getCreatedAt();
			Location loc = null;
			if (g != null)
				loc = new Location(g.getLatitude(), g.getLongitude());
			else if (user != null) {
				loc = performGeoCoding(user.getLocation());
			}
			results.add(new Post(content, loc, d.getTime()));
		}
		return results;
	}

	@Override
	public List<String> extractConnectorsByName(String userName, int fNum) {
		List<String> results = new ArrayList<String>();
		try {
			long[] ids = null;
			ids = twitter.getFollowersIDs(userName, -1, fNum).getIDs();
			if (ids == null)
				return results;
			for (long id : ids) {
				User usr = twitter.showUser(id);
				results.add(usr.getName());
			}
		} catch (TwitterException e) {
			System.out.println("Fail to get followers Ids for " + userName);
		}

		return results;
	}

	/**
	 * this method take in a descriptive location (place name) return a Location
	 * with information of latitude an longitude of this place.
	 * 
	 * @param location
	 *            name of the location
	 * @return the location.
	 */
	private Location performGeoCoding(String location) {
		float longi, lati;
		GeoApiContext context = new GeoApiContext()
				.setApiKey("AIzaSyCbStGilSfAm8mPiud8TN-l5B1TWJ3xDeg");
		GeocodingResult[] results;
		try {
			results = GeocodingApi.geocode(context, location).await();
			longi = (float) results[0].geometry.location.lng;
			lati = (float) results[0].geometry.location.lat;
			return new Location(lati, longi);
		} catch (Exception e) {
			return null;
		}
	}
}
