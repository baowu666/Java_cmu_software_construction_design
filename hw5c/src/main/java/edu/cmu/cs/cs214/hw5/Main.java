package edu.cmu.cs.cs214.hw5;


import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import edu.cmu.cs.cs214.hw5.framework.FrameworkImpl;
import edu.cmu.cs.cs214.hw5.gui.MainMenuGui;
import edu.cmu.cs.cs214.hw5.plugin.FacebookDataPlugin;
import edu.cmu.cs.cs214.hw5.plugin.GithubDataPlugin;
import edu.cmu.cs.cs214.hw5.plugin.PostTypeAnalysis;
import edu.cmu.cs.cs214.hw5.plugin.SentimentAnalysis;
import edu.cmu.cs.cs214.hw5.plugin.TopwordFreqAnalysisPlugin;
import edu.cmu.cs.cs214.hw5.plugin.TumblrDataPlugin;
import edu.cmu.cs.cs214.hw5.plugin.TwitterDataPlugin;

/**
 * The main class to run the framework
 * 
 * @author Jiyu Zhu
 *
 */
public class Main {
	/**
	 * main method
	 * 
	 * @param args
	 *          input
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				init();
			}
		});
	}

	/**
	 * initialize the main method, including create the framework, register the
	 * data plugin and analysis plugins, and show the GUI
	 */
	public static void init() {
		// create the framework
		FrameworkImpl framework = new FrameworkImpl();
		// register the data-plugins

		TwitterDataPlugin tw = new TwitterDataPlugin();
		GithubDataPlugin gh = new GithubDataPlugin();
		TopwordFreqAnalysisPlugin twf = new TopwordFreqAnalysisPlugin();
		FacebookDataPlugin fb = new FacebookDataPlugin();
		SentimentAnalysis st = new SentimentAnalysis();
		PostTypeAnalysis pt = new PostTypeAnalysis();
		TumblrDataPlugin tb = new TumblrDataPlugin();
		
		framework.addDataPlugin(tw);
		framework.addDataPlugin(tb);
		framework.addDataPlugin(gh);
		framework.addDataPlugin(fb);
		framework.addAnalysisPlugin(twf);
		framework.addAnalysisPlugin(st);
		framework.addAnalysisPlugin(pt);
		// register the analysis-plugins

		// create the JFrame
		JFrame frame = new JFrame("Social Network Analysis FrameWork");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
		// add the panel
		MainMenuGui gui = new MainMenuGui(frame, framework);
		frame.add(gui);
		frame.setContentPane(gui);
		frame.setVisible(true);
		frame.pack();

		framework.subscribe(gui);
	}

}