package edu.cmu.cs.cs214.hw5.plugin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import edu.cmu.cs.cs214.hw5.framework.UserInfoImpl;

/**
 * This analysis plug in can find this user's favorite words in his or her posts
 * It will show a pie chart as a result
 *
 */
public class FavoriteWordAnalysis implements AnalysisPlugin {
	private static final double	MOST_FREQUENT = 0.5;
	private static final double	SECOND_FREQUENT = 0.3;
	private static final double	LESS_FREQUENT = 0.1;
	private static final int MAX_WORD = 8;
	private static final int FONT1 = 20;
	private static final int FONT2 = 13;
	private static final int GRID = 4;
	private static final double GAP = 0.02;
	private boolean mostFreq = false;
	private boolean secondFreq = false;
	private boolean lessFreq = false;
	private int totalWords;

	@Override
	public JPanel analyze(UserInfoImpl userInfo) {
		JPanel analyze = new JPanel();
		analyze.setLayout(new BorderLayout());
		DefaultPieDataset dataSet = new DefaultPieDataset();
		List<String> posts = userInfo.getHostPostsContent();
		

		// get post words
		HashMap<String, Integer> wordsFreq = new HashMap<String, Integer>();
		int count = 0;
		for (String p : posts) {
			String[] words = p.split("\\W");
			for (String w : words) {
				totalWords++;
				if (wordsFreq.containsKey(w)) {
					count = wordsFreq.get(w);
					wordsFreq.put(w, count + 1);
				} else {
					wordsFreq.put(w, 1);
				}
			}
		}

		// find most frequent words
		ArrayList<Map.Entry<String, Integer>> mapList = new ArrayList<Map.Entry<String, Integer>>(
				wordsFreq.entrySet());
		wordsFreq.entrySet();
		Collections.sort(mapList, new Comparator<Map.Entry<String, Integer>>() {

			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				return o2.getValue() - o1.getValue();
			}

		});

		for (int i = 0; i < mapList.size() && i < MAX_WORD; i++) {
			if (mapList.get(i).getKey()!=null && mapList.get(i).getKey().length() > 0) {
				if (mapList.get(i).getValue()*1.0/totalWords >= MOST_FREQUENT){
					mostFreq = true;
				} else if (mapList.get(i).getValue()*1.0/totalWords >= SECOND_FREQUENT) {
					secondFreq = true;
				} else if (mapList.get(i).getValue()*1.0/totalWords >= LESS_FREQUENT){
					lessFreq = true;
				}
				dataSet.setValue(mapList.get(i).getKey(), mapList.get(i)
						.getValue());
			}
		}

		// new pie chart
		// JLabel header = new JLabel("Your favorite words!");
		// analyze.add(header,BorderLayout.NORTH);

		JFreeChart chart = ChartFactory.createPieChart("Your favorite words!",
				dataSet, true, true, false);

		// PiePlot pie = new PiePlot(dataSet);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLabelFont(new Font("Times", Font.PLAIN, FONT1));
		plot.setNoDataMessage("No data available");
		plot.setCircular(true);
		plot.setBackgroundPaint(Color.WHITE);
		plot.setLabelGap(GAP);

		JPanel pieChart = new ChartPanel(chart);
		
		JPanel result = new JPanel();
		JLabel title = new JLabel("Personality analysis:");
		title.setFont(new Font("Chalkboard",Font.BOLD,FONT1));
		title.setForeground(Color.RED);
		JLabel l1 = new JLabel();
		JLabel l2 = new JLabel();
		JLabel l3 = new JLabel();
		
		if (mostFreq) {
			l1.setText("You are an nostalgic and kindhearted person.\n");
			l2.setText("Once you loved something, it's hard to change.\n");
			l3.setText("You are willing to help other people even if it hurts you\n");
		} else if (secondFreq) {
			l1.setText("You are a litte bit nostalgic, but you still can accept new things.\n");
			l2.setText("When someone come to you for help, you will help people if it does not hurt you.\n");
		} else {
			l1.setText("You are an independent person and easily appcetting new things.\n");
			l2.setText("You believe that people should help themselves when thet have trouble.\n");
		}
		result.setLayout(new GridLayout(GRID,1));
		Font font = new Font("Chalkboard",Font.CENTER_BASELINE,FONT2);
		l1.setFont(font);
		l2.setFont(font);
		l3.setFont(font);
		result.add(title);
		result.add(l1);
		result.add(l2);
		result.add(l3);

		analyze.add(pieChart, BorderLayout.CENTER);
		analyze.add(result,BorderLayout.SOUTH);
		

		return analyze;
	}

	@Override
	public String getName() {
		String name = "User's most favorite words";
		return name;
	}

}
