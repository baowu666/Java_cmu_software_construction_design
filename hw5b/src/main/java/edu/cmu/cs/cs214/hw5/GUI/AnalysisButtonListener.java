package edu.cmu.cs.cs214.hw5.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.cmu.cs.cs214.hw5.framework.FrameworkImpl;
import edu.cmu.cs.cs214.hw5.plugin.AnalysisPlugin;

/**
 * Analysis Button Listener, which is an ActionListener
 *
 */
public class AnalysisButtonListener implements ActionListener {
	private int num;
	private FrameworkImpl framework;
	private GUI gui;

	/**
	 * Constructor
	 * 
	 * @param newNum
	 *            index of the button
	 * @param newFramework
	 *            framework to parse into
	 * @param newGui
	 *            gui to parse into
	 */
	public AnalysisButtonListener(int newNum, FrameworkImpl newFramework,
			GUI newGui) {
		num = newNum;
		framework = newFramework;
		gui = newGui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		AnalysisPlugin currentAnalysis = framework.getAnalysisPlugins()
				.get(num);
		framework.setCurrentAnalysis(currentAnalysis);
		framework.showAnalysis();
	}

}
