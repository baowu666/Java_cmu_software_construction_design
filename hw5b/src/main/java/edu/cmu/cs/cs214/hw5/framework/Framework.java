package edu.cmu.cs.cs214.hw5.framework;

import edu.cmu.cs.cs214.hw5.plugin.DataPlugin;
import edu.cmu.cs.cs214.hw5.plugin.AnalysisPlugin;

/**
 * this is a interface for framework
 * 
 * @author Team28
 *
 */
public interface Framework {

	/**
	 * Do some preparing work for analysis: Initializing the start part of GUI,
	 * allowing user to input query key words
	 */
	void initialize();

	/**
	 * Register Data Plugin to the framework
	 * 
	 * @param plugin
	 *            the Data Plugin to register
	 */
	void registerDataPlugin(DataPlugin plugin);

	/**
	 * Register Analysis Plugin to the framework
	 * 
	 * @param plugin
	 *            the Analysis Plugin to register
	 */
	void registerAnalysisPlugin(AnalysisPlugin plugin);

	/**
	 * observer pattern to interact with GUI
	 * 
	 * @param listener
	 *            FrameworkChangeListener, which could be a panel
	 */
	void addChangerListener(FrameworkChangeListener listener);

	/**
	 * check if the input is a valid input name
	 * 
	 * @param userName
	 *            the string indicating a username
	 * @param postNum
	 *            number of post
	 * @return true or false
	 */
	boolean isValidUserName(String userName, int postNum);

	/**
	 * given the username, call the data plugin's method to get information and
	 * create userbean, and the same process to create userinfo
	 * 
	 * @param userName
	 *            host name
	 * @param fNum
	 *            number of friends
	 * @param postNum
	 *            max number of post needed
	 */
	void generateData(String userName, int postNum, int fNum);

	/**
	 * show analysis result of the analysis plugin, which could call the methods
	 * in analysis plugin
	 */
	void showAnalysis();

	/**
	 * back to the former panel of plugin
	 */
	void backToFormer();

	/**
	 * go to next panel og plugin
	 */
	void goToNext();

}
