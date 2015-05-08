package edu.cmu.cs.cs214.hw5.plugin;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import edu.cmu.cs.cs214.hw5.framework.AnalyzedData;
import edu.cmu.cs.cs214.hw5.framework.Post;

/**
 * This class finds the most frequently used word of a user, which is excluded
 * from the stopwords -some commonly happened words with less meaning. And
 * analyze what would be the frequency of his using that word from several parts
 * of his posts.
 * 
 *
 */
public class TopwordFreqAnalysisPlugin implements AnalysisPlugin {
	private static final int FONT_20 = 20;

	@Override
	public JPanel analyzeData(List<AnalyzedData> analyzedDatas) {
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		
		JLabel title = new JLabel("TopWord Frequency Analysis");
		title.setFont(new Font("Chalkboard",Font.BOLD,FONT_20));
		title.setForeground(Color.RED);
		pane.add(title);
		
		for (int i = 0; i < analyzedDatas.size(); i++) {
			HashMap<String, Integer> wordsCount = analyzedDatas.get(i)
					.getWordsCount();
			List<Post> posts = analyzedDatas.get(i).getPosts();

			String mostFreqWord = getMostFreqWord(wordsCount);

			JFreeChart lineChart = ChartFactory.createLineChart(analyzedDatas
					.get(i).getName() + "'s \"" + mostFreqWord + "\"",
					"Posts Chunk Period", "Frequency of the TopWord",
					createDataset(posts, mostFreqWord),
					PlotOrientation.VERTICAL, true, true, false);

			JPanel chartPanel = new ChartPanel(lineChart);

			pane.add(chartPanel);
		}
		JPanel discriptionPane = getDescriptionPane();
		pane.add(discriptionPane);
		return pane;
	}


	@Override
	public String getName() {
		return "TopWord Frequency Analysis";
	}

	/**
	 * Helper Function to create data set
	 * 
	 * @param analyzedData
	 *            the analyzedData
	 * @return the CategoryDataset
	 */
	private CategoryDataset createDataset(List<Post> posts, String mostFreqWord) {
		int numPosts = posts.size();

		String[] postsChunk = new String[numPosts];

		if (posts.size() >= numPosts) {
			for (int n = 0; n < numPosts; n++) {
				postsChunk[n] = "";
				for (int a = posts.size() * n / numPosts; a < posts.size()
						* (n + 1) / numPosts; a++) {
					postsChunk[n] += " " + posts.get(a).getContent();
				}
			}

			int[] topWordFrequency = new int[numPosts];

			for (int b = 0; b < numPosts; b++) {
				String[] words = postsChunk[b].split("\\W");

				for (String word : words) {
					if (word.toLowerCase().equals(mostFreqWord)
							|| word.toLowerCase().contains(mostFreqWord))
						topWordFrequency[b]++;
				}
			}

			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			for (int c = 0; c < numPosts; c++) {
				dataset.addValue(topWordFrequency[c], "TopWordFrequency",
						"Posts" + c + 1);
			}
			return dataset;
		} else {
			throw new IllegalArgumentException("Not enough posts");
		}

	}

	/**
	 * Helper function to get the most frequent word excluded from stop words
	 * 
	 * @param wordsCount
	 *            the map stored sting and integer of frequency
	 * @return the most frequent word
	 */
	private String getMostFreqWord(Map<String, Integer> wordsCount) {
		String mostFreqWord = null;
		HashSet<String> stopWords = getStopWords();

		List<Map.Entry<String, Integer>> mapList = new ArrayList<Map.Entry<String, Integer>>(
				wordsCount.entrySet());

		Collections.sort(mapList, new Comparator<Map.Entry<String, Integer>>() {

			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				// descending order
				return o2.getValue() - o1.getValue();
			}
		});

		for (Map.Entry<String, Integer> map : mapList) {
			if (!stopWords.contains(map.getKey())) {
				mostFreqWord = map.getKey();
				break;
			}
		}
		return mostFreqWord;
	}

	/**
	 * Helper function to get a set of stop words to exclude later.
	 * 
	 * @return a set of stop words
	 */
	private HashSet<String> getStopWords() {
		HashSet<String> stopWords = new HashSet<String>();
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("stopWords.txt"));

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] wordsFromText = line.split("\\W");
				for (String word : wordsFromText)
					stopWords.add(word.toLowerCase());
			}

			return stopWords;
		} catch (FileNotFoundException e) {
			System.err.println("Cannot find the file");
		} finally {
			if (scanner != null)
				scanner.close();
		}
		return null;
	}

	/**
	 * Helper function to get description panel
	 * 
	 * @return the description panel
	 */
	private JPanel getDescriptionPane() {
		JPanel descriptionPane = new JPanel();
		descriptionPane.setLayout(new BoxLayout(descriptionPane,
				BoxLayout.Y_AXIS));
		JLabel descriptionLabel1 = new JLabel();
		JLabel descriptionLabel2 = new JLabel();
		JLabel descriptionLabel3 = new JLabel();
		JLabel descriptionLabel4 = new JLabel();
		JLabel descriptionLabel5 = new JLabel();
		descriptionLabel1
				.setText("This analysis plugin is to find the most frequently");
		descriptionLabel2
				.setText("used word of a user, which is excluded from the");
		descriptionLabel3
				.setText("stopwords-some commonly happened words with less");
		descriptionLabel4
				.setText("meaning. And analyze what would be the frequency");
		descriptionLabel5
				.setText("of his using that word from each part of his posts.");
		descriptionPane.add(descriptionLabel1);
		descriptionPane.add(descriptionLabel2);
		descriptionPane.add(descriptionLabel3);
		descriptionPane.add(descriptionLabel4);
		descriptionPane.add(descriptionLabel5);
		return descriptionPane;
	}
}
