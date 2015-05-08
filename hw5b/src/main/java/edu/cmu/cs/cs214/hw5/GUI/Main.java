package edu.cmu.cs.cs214.hw5.GUI;

import javax.swing.SwingUtilities;

import edu.cmu.cs.cs214.hw5.framework.FrameworkImpl;
import edu.cmu.cs.cs214.hw5.plugin.FacebookData;
import edu.cmu.cs.cs214.hw5.plugin.FavoriteWordAnalysis;
import edu.cmu.cs.cs214.hw5.plugin.PersonalityAnalysis;
import edu.cmu.cs.cs214.hw5.plugin.PostTimeAnalysis;
import edu.cmu.cs.cs214.hw5.plugin.TwitterData;

/**
 * Main class
 *
 */
public class Main {

	/**
	 * main method to run the framework system
	 * 
	 * @param args
	 *            arguemnts to parse into, should not be used
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndStartFramework();
			}
		});
	}

	private static void createAndStartFramework() {
		FrameworkImpl framework = new FrameworkImpl();
		framework.initialize();

		framework.registerDataPlugin(new FacebookData());
		framework.registerDataPlugin(new TwitterData());
		framework.registerAnalysisPlugin(new FavoriteWordAnalysis());
		framework.registerAnalysisPlugin(new PostTimeAnalysis());
		framework.registerAnalysisPlugin(new PersonalityAnalysis());

		GUI gui = new GUI(framework);
		framework.addChangerListener(gui);

	}
}
