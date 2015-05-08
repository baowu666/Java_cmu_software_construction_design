package edu.cmu.cs.cs214.hw5.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.cmu.cs.cs214.hw5.framework.FrameworkChangeListener;
import edu.cmu.cs.cs214.hw5.framework.FrameworkImpl;

/**
 * GUI for the Framework
 *
 */
public class GUI implements FrameworkChangeListener {
	private FrameworkImpl framework;
	private int numDataPlugin, numAnalysisPlugin;
	private JButton[] dataPluginButtons, analysisPluginButtons;
	private JTextField userNameField, postNumField;
	private JFrame frame;
	private JPanel analysisPane;
	private JTextField prompt;

	private static final int FIELD_LENGTH_40 = 40;
	private static final int FIELD_LENGTH_15 = 15;

	/**
	 * Constructor
	 * 
	 * @param newFramework
	 *            Framework to parse in
	 */
	public GUI(FrameworkImpl newFramework) {
		framework = newFramework;
		numDataPlugin = framework.getDataPlugins().size();
		numAnalysisPlugin = framework.getAnalysisPlugins().size();

		frame = new JFrame("Team 28 Framework");
		Container container = frame.getContentPane();
		container.setLayout(new BorderLayout());

		JPanel promptPane = new JPanel();
		prompt = new JTextField(FIELD_LENGTH_40);
		prompt.setEditable(false);
		prompt.setForeground(Color.RED);
		promptPane.add(prompt);

		JPanel dataPane = getDataPane();
		analysisPane = getAnalysisPane();

		container.add(dataPane, BorderLayout.NORTH);
		container.add(promptPane, BorderLayout.CENTER);
		container.add(analysisPane, BorderLayout.SOUTH);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(true);
		frame.setVisible(true);
	}

	private JPanel getDataPane() {
		JPanel dataPane = new JPanel();
		dataPane.setLayout(new BoxLayout(dataPane, BoxLayout.Y_AXIS));

		JLabel instruction = new JLabel(
				"Enter username, max posts, select data scourse and analysis:");
		JLabel userNameLabel = new JLabel("username:");
		JLabel postNumLabel = new JLabel("max posts number:");
		userNameField = new JTextField(FIELD_LENGTH_15);
		postNumField = new JTextField(FIELD_LENGTH_15);
		dataPluginButtons = new JButton[numDataPlugin];
		analysisPluginButtons = new JButton[numAnalysisPlugin];

		for (int i = 0; i < dataPluginButtons.length; i++) {
			dataPluginButtons[i] = new JButton(framework.getDataPlugins()
					.get(i).getSourceName());
			dataPluginButtons[i].addActionListener(new DataButtonListener(i,
					framework, this));
		}

		for (int j = 0; j < analysisPluginButtons.length; j++) {
			analysisPluginButtons[j] = new JButton(framework
					.getAnalysisPlugins().get(j).getName());
			analysisPluginButtons[j]
					.addActionListener(new AnalysisButtonListener(j, framework,
							this));
			analysisPluginButtons[j].setEnabled(false);
		}

		JPanel inputPane = new JPanel();
		JPanel dataButtonPane = new JPanel();
		JPanel analysisButtonPane = new JPanel();

		inputPane.add(userNameLabel);
		inputPane.add(userNameField);
		inputPane.add(postNumLabel);
		inputPane.add(postNumField);

		for (JButton db : dataPluginButtons) {
			dataButtonPane.add(db);
		}

		for (JButton ab : analysisPluginButtons) {
			analysisButtonPane.add(ab);
		}

		dataPane.add(instruction);
		dataPane.add(inputPane);
		dataPane.add(dataButtonPane);
		dataPane.add(analysisButtonPane);

		return dataPane;
	}

	private JPanel getAnalysisPane() {
		if (framework.getCurrentPane() != null)
			return framework.getCurrentPane();
		return new JPanel();
	}

	/**
	 * get the textfield of prompt message
	 * 
	 * @return textfield of prompt message
	 */
	public JTextField getPrompt() {
		return prompt;
	}

	/**
	 * get the textfield of UserName
	 * 
	 * @return textfield of UserName
	 */
	public JTextField getUserNameField() {
		return userNameField;
	}

	/**
	 * get the textfield of PostNum
	 * 
	 * @return textfield of PostNum
	 */
	public JTextField getPostNumField() {
		return postNumField;
	}

	@Override
	public void onDataRetrieving() {
		prompt.setText("Waiting for data retrieving...");
		for (int i = 0; i < dataPluginButtons.length; i++) {
			dataPluginButtons[i].setEnabled(false);
		}
		for (int j = 0; j < analysisPluginButtons.length; j++) {
			analysisPluginButtons[j].setEnabled(false);
		}
		userNameField.setEditable(false);
		postNumField.setEditable(false);
	}

	@Override
	public void onDataReturned() {
		prompt.setText("Data returned, ready to analyze");
		for (int i = 0; i < dataPluginButtons.length; i++) {
			dataPluginButtons[i].setEnabled(true);
		}
		for (int j = 0; j < analysisPluginButtons.length; j++) {
			analysisPluginButtons[j].setEnabled(true);
		}
		userNameField.setEditable(true);
		postNumField.setEditable(true);
	}

	@Override
	public void onInvalidUser() {
		prompt.setText("Username does not exists or reaches rate limit");
		for (int i = 0; i < dataPluginButtons.length; i++) {
			dataPluginButtons[i].setEnabled(true);
		}
		for (int j = 0; j < analysisPluginButtons.length; j++) {
			analysisPluginButtons[j].setEnabled(true);
		}
		userNameField.setEditable(true);
		postNumField.setEditable(true);
	}

	@Override
	public void onAnalysisFinished(JPanel result) {
		prompt.setText("Analysis finished");
		for (int i = 0; i < dataPluginButtons.length; i++) {
			dataPluginButtons[i].setEnabled(true);
		}
		for (int j = 0; j < analysisPluginButtons.length; j++) {
			analysisPluginButtons[j].setEnabled(true);
		}
		frame.getContentPane().remove(
				((BorderLayout) frame.getContentPane().getLayout())
						.getLayoutComponent(BorderLayout.SOUTH));
		frame.getContentPane().add(result, BorderLayout.SOUTH);
		frame.pack();
		frame.setResizable(true);
		frame.setVisible(true);
	}

}
