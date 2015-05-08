package edu.cmu.cs.cs214.hw5.plugin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import edu.cmu.cs.cs214.hw5.framework.AnalyzedData;

/**
 * This sentiment analysis plug in will get all positive words
 * and negative words in user's posts
 * Then analyze if this person a negative or positive person
 *
 */
public class SentimentAnalysis implements AnalysisPlugin {
	private HashSet<String> positiveWords;
	private HashSet<String> negativeWords;
	private static final int COUNT = 9;
	private static final int FONT_20 = 20;
	private static final int FONT_13 = 13;

	/**
	 * This method read all positive words into a hash map
	 */
	private void readPositiveFile() {
		positiveWords = new HashSet<String>();
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("positive.txt"));

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] wordsFromText = line.split("\\W");
				for (String word : wordsFromText)
					positiveWords.add(word.toLowerCase());
			}

		} catch (FileNotFoundException e) {
			System.err.println("Cannot find the file");
		} finally {
			if (scanner != null)
				scanner.close();
		}
	}

	/**
	 * This method read all negative words in user's posts
	 */
	private void readNegativeFile() {
		negativeWords = new HashSet<String>();
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("negative.txt"));

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] wordsFromText = line.split("\\W");
				for (String word : wordsFromText)
					negativeWords.add(word.toLowerCase());
			}

		} catch (FileNotFoundException e) {
			System.err.println("Cannot find the file");
		} finally {
			if (scanner != null)
				scanner.close();
		}
	}

	@Override
	public JPanel analyzeData(List<AnalyzedData> analyzedDatas) {
		JPanel analyze = new JPanel();
		analyze.setLayout(new BorderLayout());
		JPanel chartPanel = new JPanel();
		readPositiveFile();
		readNegativeFile();
		JPanel textResult = new JPanel();
		textResult.setLayout(new GridLayout(analyzedDatas.size(),1));
		if (analyzedDatas.size() <= 2) {
			chartPanel.setLayout(new GridLayout(1, analyzedDatas.size()));
		} else {
			chartPanel.setLayout(new GridLayout(2, analyzedDatas.size()));
		}
		
		for (AnalyzedData data : analyzedDatas) {
			HashMap<String, Integer> wordsCount = data.getWordsCount();

			// sort by value
			ArrayList<Map.Entry<String, Integer>> mapList = new ArrayList<Map.Entry<String, Integer>>(
					wordsCount.entrySet());
			Collections.sort(mapList,
					new Comparator<Map.Entry<String, Integer>>() {

						@Override
						public int compare(Entry<String, Integer> o1,
								Entry<String, Integer> o2) {
							return o2.getValue() - o1.getValue();
						}

					});

			// get positive word and negative word
			DefaultPieDataset dataSet = new DefaultPieDataset();

			ArrayList<String> positive = new ArrayList<String>();
			ArrayList<String> negative = new ArrayList<String>();
			int count = 0;
			for (int i = 0; i < mapList.size(); i++) {
				String word = mapList.get(i).getKey().toLowerCase();
				if (positiveWords.contains(word)) {
					if (count <= COUNT) {
						dataSet.setValue("P: "+word, mapList.get(i).getValue());
					}
					positive.add(word);
					count++;
				}
				if (negativeWords.contains(word)) {
					if (count <= COUNT) {
						dataSet.setValue("N: "+word, mapList.get(i).getValue());
					}
					negative.add(word);
					count++;
				}
			}

			// GUI part

			JFreeChart chart = ChartFactory.createPieChart(
					data.getName(), dataSet, true,
					true, false);
			PiePlot plot = (PiePlot) chart.getPlot();
			plot.setNoDataMessage("No data available");
			plot.setBackgroundPaint(Color.WHITE);
			JPanel pieChart = new ChartPanel(chart);
			
		
			chartPanel.add(pieChart);

			analyze.add(chartPanel, BorderLayout.CENTER);
			
			int pos = positive.size();
			int neg = negative.size();
			JLabel label = new JLabel();
			if (pos > neg) {
			
				label.setText(data.getName()+" is a positive person, well done!");
			} else {
				label.setText(data.getName()+" should be happy and smile!");
			}
			textResult.add(label);
			
		}
		JPanel title = new JPanel();
		title.setLayout(new GridLayout(2,1));
		JLabel title1 = new JLabel("Are you a positive or negative person?");
		title1.setFont(new Font("Chalkboard",Font.BOLD,FONT_20));
		title1.setForeground(Color.RED);
		JLabel title2 = new JLabel("Top 9 positive and negative words in your posts!");
		title2.setFont(new Font("Chalkboard",Font.BOLD,FONT_13));
		title2.setForeground(Color.RED);
		title.add(title1);
		title.add(title2);
		analyze.add(title,BorderLayout.NORTH);

		
		analyze.add(textResult, BorderLayout.SOUTH);
		
		return analyze;
	}

	@Override
	public String getName() {
		String name = "Sentiment Analysis";
		return name;
	}

}
