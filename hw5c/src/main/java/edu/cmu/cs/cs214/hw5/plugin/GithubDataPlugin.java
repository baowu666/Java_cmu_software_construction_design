package edu.cmu.cs.cs214.hw5.plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;

import edu.cmu.cs.cs214.hw5.framework.Post;
import edu.cmu.cs.cs214.hw5.framework.UserData;

/**
 * This class is to get UserData from a given user name. This class uses the
 * package of org.kohsuke.github
 * 
 *
 */
public class GithubDataPlugin implements DataPlugin {
	private static final String PLUGIN_NAME = "Github";

	@Override
	public String getName() {
		return PLUGIN_NAME;
	}

	@Override
	public UserData getUserData(String userName) {
		UserData userData = new UserData(userName, PLUGIN_NAME);

		try {
			GitHub gitHub = GitHub
					.connectUsingOAuth("d72c3a7cb494da73bd8a211f72f794e29b618a3a");

			int rateLimit = gitHub.getRateLimit().remaining;
			if (rateLimit < 0) {
				userData.setErroMsg("Hit rate limit!");
				return userData;
			}

			GHUser user = gitHub.getUser(userName);
			userData.setId(String.valueOf(user.getId()));
			userData.setLocation(user.getLocation());
			userData.setFollowers(user.getFollowersCount());
			userData.setFollowees(user.getFollowingCount());

			Map<String, GHRepository> gitRepository = user.getRepositories();
			List<Post> posts = new ArrayList<Post>();

			for (String s : gitRepository.keySet()) {
				if (gitRepository.get(s) != null) {
					List<GHCommit> commitList = gitRepository.get(s).listCommits().asList();
					String comments = "";
					for (GHCommit commit : commitList) {
						comments += " " + commit.getCommitShortInfo().getMessage();
					}
					posts.add(new Post(comments, gitRepository.get(s).getCreatedAt()));
				}
			}
			userData.setPostList(posts);
			return userData;

		} catch (IOException e) {
			userData.setErroMsg("Token error.The user: " + userName
					+ "is invalid!");
		}
		return null;
	}

}
