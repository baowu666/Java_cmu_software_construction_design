package edu.cmu.cs.cs214.hw5.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import edu.cmu.cs.cs214.hw5.framework.FrameworkImpl;

/**
 * Data Button Listener, which is an ActionListener
 *
 */
public class DataButtonListener implements ActionListener {
	private int num;
	private JTextField userNameField, postNumField, prompt;
	private FrameworkImpl framework;
	private GUI gui;

	private static final int NUM_50 = 50;
	private static final int NUM_20 = 50;

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
	public DataButtonListener(int newNum, FrameworkImpl newFramework, GUI newGui) {
		num = newNum;
		framework = newFramework;
		gui = newGui;
		userNameField = gui.getUserNameField();
		postNumField = gui.getPostNumField();
		prompt = gui.getPrompt();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int postNum = NUM_20;
		prompt.setText(null);
		String userName = userNameField.getText();
		if (userName == null || userName.trim().length() == 0) {
			prompt.setText("Username should not be null");
			userNameField.setText(null);
			return;
		}
		if (postNumField.getText() == null
				|| postNumField.getText().trim().length() == 0) {
			prompt.setText("Post number should not be null");
			postNumField.setText(null);
			return;
		}
		try {
			postNum = Integer.parseInt(postNumField.getText());
		} catch (NumberFormatException e2) {
			prompt.setText("Post number should be an integer");
			postNumField.setText(null);
			return;
		}
		if (postNum > NUM_50) {
			postNum = NUM_50;
			prompt.setText("Post number exceeding 100, converted to 100");
		}
		framework.setCurrentData(framework.getDataPlugins().get(num));
		gui.onDataRetrieving();
		final int newPostNum = postNum;
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				if (framework.isValidUserName(userName, newPostNum)) {
					framework.generateData(userName, newPostNum, NUM_20);
				} else {
					gui.onInvalidUser();
					return;
				}
			}
		});
		t.start();

	}

}
