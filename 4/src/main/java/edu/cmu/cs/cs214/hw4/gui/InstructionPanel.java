package edu.cmu.cs.cs214.hw4.gui;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * InstructionPanel is a panel showing some string of instructions
 * 
 * @author zhaoru
 *
 */
public class InstructionPanel extends JPanel {
	private static final long serialVersionUID = 4429369057735568782L;

	private static final String INSTRUCTION1 = "INSTRUCTION:";
	private static final String INSTRUCTION2 = "Backgrounds' Color";
	private static final String INSTRUCTION3 = "Yello-DL Cyan-TL";
	private static final String INSTRUCTION4 = "Red-DW Green-TW";
	private static final String INSTRUCTION5 = "Swap: Enter indeices";
	private static final String INSTRUCTION6 = "of your tiles";
	private static final String INSTRUCTION7 = "(index 1-7 of your";
	private static final String INSTRUCTION8 = "current word tiles)";
	private static final String INSTRUCTION9 = "without sapce to swap.";
	private static final String INSTRUCTION10 = "Swap or Pass twice";
	private static final String INSTRUCTION11 = "would end the game.";
	private static final String INSTRUCTION12 = "Prority to place";
	private static final String INSTRUCTION13 = "special tiles is lower";
	private static final String INSTRUCTION14 = "than word tiles.";
	private static final String INSTRUCTION15 = "You can retreat and";
	private static final String INSTRUCTION16 = "place special tiles";
	private static final String INSTRUCTION17 = "retreat button would";
	private static final String INSTRUCTION18 = "pull back word tiles";
	private static final String INSTRUCTION19 = "you placed this turn";

	/**
	 * Constructor
	 */
	public InstructionPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel instructionLabel1 = new JLabel(INSTRUCTION1);
		JLabel instructionLabel2 = new JLabel(INSTRUCTION2);
		JLabel instructionLabel3 = new JLabel(INSTRUCTION3);
		JLabel instructionLabel4 = new JLabel(INSTRUCTION4);
		JLabel instructionLabel5 = new JLabel(INSTRUCTION5);
		JLabel instructionLabel6 = new JLabel(INSTRUCTION6);
		JLabel instructionLabel7 = new JLabel(INSTRUCTION7);
		JLabel instructionLabel8 = new JLabel(INSTRUCTION8);
		JLabel instructionLabel9 = new JLabel(INSTRUCTION9);
		JLabel instructionLabel10 = new JLabel(INSTRUCTION10);
		JLabel instructionLabel11 = new JLabel(INSTRUCTION11);
		JLabel instructionLabel12 = new JLabel(INSTRUCTION12);
		JLabel instructionLabel13 = new JLabel(INSTRUCTION13);
		JLabel instructionLabel14 = new JLabel(INSTRUCTION14);
		JLabel instructionLabel15 = new JLabel(INSTRUCTION15);
		JLabel instructionLabel16 = new JLabel(INSTRUCTION16);
		JLabel instructionLabel17 = new JLabel(INSTRUCTION17);
		JLabel instructionLabel18 = new JLabel(INSTRUCTION18);
		JLabel instructionLabel19 = new JLabel(INSTRUCTION19);

		add(new JLabel(" "));
		add(new JLabel(" "));
		add(new JLabel(" "));
		add(new JLabel(" "));
		add(new JLabel(" "));
		add(new JLabel(" "));
		add(new JLabel(" "));
		add(instructionLabel1);
		add(new JLabel(" "));
		add(instructionLabel2);
		add(instructionLabel3);
		add(instructionLabel4);
		add(new JLabel(" "));
		add(instructionLabel5);
		add(instructionLabel6);
		add(instructionLabel7);
		add(instructionLabel8);
		add(instructionLabel9);
		add(new JLabel(" "));
		add(instructionLabel10);
		add(instructionLabel11);
		add(new JLabel(" "));
		add(instructionLabel12);
		add(instructionLabel13);
		add(instructionLabel14);
		add(instructionLabel15);
		add(instructionLabel16);
		add(new JLabel(" "));
		add(instructionLabel17);
		add(instructionLabel18);
		add(instructionLabel19);
	}
}
