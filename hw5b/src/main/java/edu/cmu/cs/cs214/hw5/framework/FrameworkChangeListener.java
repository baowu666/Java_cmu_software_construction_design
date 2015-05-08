package edu.cmu.cs.cs214.hw5.framework;

import javax.swing.JPanel;

/**
 * this interface defines functionalities of listeners react to change event of
 * framework
 * 
 * @author Team28
 *
 */
public interface FrameworkChangeListener {

	/**
	 * react when data is delivered to framework
	 */
	void onDataReturned();

	/**
	 * react the event when analysis plugin finish the analysis
	 * 
	 * @param result
	 *            a JPanel representing analysis result
	 */
	void onAnalysisFinished(JPanel result);

	/**
	 * react the event when framework start to request data from data plugin
	 */
	void onDataRetrieving();

	/**
	 * react when the user input is invalid
	 */
	void onInvalidUser();
}
