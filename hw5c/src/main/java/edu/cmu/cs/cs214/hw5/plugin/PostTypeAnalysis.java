package edu.cmu.cs.cs214.hw5.plugin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import edu.cmu.cs.cs214.hw5.framework.AnalyzedData;

/**
 * This analysis plug in can analyze user's posts type
 * There are three types posts type
 * First is topic-love posts
 * Second is website-love posts
 * Third is your post like @ people
 *
 */
public class PostTypeAnalysis implements AnalysisPlugin{
	private static final String AT = "@";
	private static final String TOPIC = "#";
	private static final String WEB = "://";
	private int atNum = 0;
	private int topicNum = 0;
	private int webNum = 0;
	private static final int FONT_20 = 20;

	@Override
	public JPanel analyzeData(List<AnalyzedData> analyzedDatas) {
		JPanel analyze = new JPanel(new BorderLayout());
		HashMap<String, Integer> nonWordsCount = new HashMap<String, Integer>();
		HashMap<String, Integer> wordsCount = new HashMap<String, Integer>();
		JPanel result = new JPanel();
		result.setLayout(new GridLayout(analyzedDatas.size(),1));
		
		JLabel title = new JLabel("What kinds of post do you like?");
		
		
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		final String series1 = "@";
		final String series2 = "#Topic#";
		final String series3 = "http://";
		
		for (int i=0; i< analyzedDatas.size(); i++) {
			AnalyzedData data = analyzedDatas.get(i);
			nonWordsCount = data.getNonWordsCount();
			wordsCount = data.getWordsCount();
			atNum = nonWordsCount.get(AT);
			topicNum = nonWordsCount.get(TOPIC);
			webNum = nonWordsCount.get(WEB);
			
			int total = 0;
			for (String nonWord : nonWordsCount.keySet()) {
			    total += nonWordsCount.get(nonWord);
			}

			
			
			
			dataset.addValue(atNum*1.0/total, series1, data.getName());
			dataset.addValue(topicNum*1.0/total, series2, data.getName());
			dataset.addValue(webNum*1.0/total, series3, data.getName());
	
			
			
			JFreeChart chart = ChartFactory.createBarChart3D(
					"Post type analysis", // chart title
					"Category", // domain axis label
					"Value", // range axis label
					dataset, // data
					PlotOrientation.VERTICAL, true, true, false);
			ChartPanel chartPanel = new ChartPanel(chart);
			analyze.add(chartPanel,BorderLayout.CENTER);
			
			JLabel textResult = new JLabel();
			if (atNum >= webNum &&  atNum >= topicNum) {
				textResult.setText(data.getName()+" loves @ friends in posts!");
			} 
			if (webNum >= atNum  &&  webNum>=topicNum) {
				textResult.setText(data.getName()+" loves share website in posts!");
			} 
			if (topicNum >= atNum && topicNum >= webNum) {
				textResult.setText(data.getName()+" loves join hot topics!");
			}
			if (topicNum == atNum) {
				textResult.setText(data.getName()+" loves join hot topics and @ friends!");
			}
			if (atNum ==webNum) {
				textResult.setText(data.getName()+" loves share website and @ friends!");
			}
			if (topicNum == webNum) {
				textResult.setText(data.getName()+" loves join hot topics and share website!");
			}
			if (atNum == webNum && atNum == topicNum) {
				textResult.setText(data.getName()+" loves every kinds of posts!!");
			}
			
			result.add(textResult);

		}

		
		title.setFont(new Font("Chalkboard",Font.BOLD,FONT_20));
		title.setForeground(Color.RED);
		analyze.add(title, BorderLayout.NORTH);
		analyze.add(result,BorderLayout.SOUTH);
		

		return analyze;
	}

	@Override
	public String getName() {
		String name = "Post type analysis";
		return name;
	}

}
