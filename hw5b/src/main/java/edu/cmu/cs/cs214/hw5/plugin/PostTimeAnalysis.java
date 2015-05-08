package edu.cmu.cs.cs214.hw5.plugin;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import edu.cmu.cs.cs214.hw5.framework.UserInfo;
import edu.cmu.cs.cs214.hw5.framework.UserInfoImpl;

/**
 * This analysis plug in analyze this user's post time It'll show a line chart,
 * x axis is time as hour, y axis is the amount of posts which are posted at
 * this hour
 *
 */
public class PostTimeAnalysis implements AnalysisPlugin{
	private static final int GRID = 24;
	private static final float SIZE1 = 10.0f;
	private static final float SIZE2 = 6.0f;
	private static final float SIZE3 = 0.0f;
	private static final int FOUR_TIME = 4;
	private static final int FIVE_TIME = 5;
	private static final int TWENTY_TWO_TIME = 22;
	private static final int NINE_TIME = 9;
	private static final int HOUR = 4;
	private static final int FONT1 = 20;
	private static final int FONT2 = 13;
	
	@Override
	public JPanel analyze(UserInfoImpl userInfo) {
		JPanel analyze = new JPanel();
		analyze.setLayout(new BorderLayout());
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		
		HashMap<Integer,Integer> postTime = getPostTime(userInfo);
		
		// sort
		ArrayList<Map.Entry<Integer, Integer>> mapList = new ArrayList<Map.Entry<Integer, Integer>>(
				postTime.entrySet());
		postTime.entrySet();
		Collections.sort(mapList, new Comparator<Map.Entry<Integer, Integer>>() {

			@Override
			public int compare(Entry<Integer, Integer> o1,
					Entry<Integer, Integer> o2) {
				return o1.getKey() - o2.getKey();
			}

		});
		
		// chart
		for (int i=0;i<mapList.size();i++) {
			dataSet.addValue(mapList.get(i).getValue(), userInfo.getHostName()+"'s posts time distribute map", mapList.get(i).getKey());
		}
		JFreeChart chart = ChartFactory.createLineChart("Post time", "24 Hours", "Posts Amount", dataSet,PlotOrientation.VERTICAL,true,true,false);
		chart.setBackgroundPaint(Color.white);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);
       

        // customise the range axis...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);
        
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		renderer.setSeriesStroke(0, new BasicStroke(2.0f,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {SIZE1, SIZE2}, SIZE3));
        
        JPanel lineChart = new ChartPanel(chart);
        analyze.add(lineChart, BorderLayout.CENTER);
        
        JPanel textResult = getTextResult(mapList);
        analyze.add(textResult, BorderLayout.SOUTH);
		return analyze;
	}
	
	private JPanel getTextResult(ArrayList<Map.Entry<Integer, Integer>> mapList) {
		JPanel textResult = new JPanel(new GridLayout(FOUR_TIME,1));
		int earlyCount = 0;
		int normalCount = 0;
		int lateCount = 0;
		for (int i=0; i<mapList.size(); i++) {
			if (i < NINE_TIME  &&  i>=FIVE_TIME) {
				earlyCount += mapList.get(i).getValue();
			} else if (i>=TWENTY_TWO_TIME || i<=HOUR) {
				lateCount += mapList.get(i).getValue();
			} else {
				normalCount += mapList.get(i).getValue();
			}
		}
		JLabel l1 = new JLabel();
		JLabel l2 = new JLabel();
		JLabel l3 = new JLabel();
		if (normalCount > lateCount && normalCount > earlyCount) {
			l1.setText("You have a healthy lifestyle, get up early and go to sleep early.");
			l2.setText("You are a very organized very strict in your life.");
			l3.setText("Keep this kind of life and you can success in everything!");
		} else if (lateCount > normalCount && lateCount > earlyCount) {
			l1.setText("You should go to sleep early in the future");
			l2.setText("Sometimes you should relax yourself, do not make yourself so busy.");
			l3.setText("Do not let people who loves you worry! Love yourself, love your life!");
			
		} else if (earlyCount > normalCount && earlyCount > lateCount) {
			l1.setText("You always get up too early. Is that for work or study?");
			l2.setText("No matter what, sometimes you should get enough relax.");
			l3.setText("Your effort will paid off finally!");
		}
		
		JLabel title = new JLabel("Work-rest schedule analysis:");
		title.setFont(new Font("Chalkboard",Font.BOLD,FONT1));
		title.setForeground(Color.RED);
		
		Font font = new Font("Chalkboard",Font.CENTER_BASELINE,FONT2);
		l1.setFont(font);
		l2.setFont(font);
		l3.setFont(font);
		textResult.add(title);
		textResult.add(l1);
		textResult.add(l2);
		textResult.add(l3);
		
		return textResult;
		
	}
	
	
	private HashMap<Integer,Integer> getPostTime(UserInfo userInfo){
		HashMap<Integer,Integer> postTime = new HashMap<Integer,Integer>();
		for(int i=0;i<GRID;i++) {
			postTime.put(i, 0);
		}
		List<Long> times = userInfo.getHostPostsTimes();
		for (Long t:times) {
			Calendar calendar = new GregorianCalendar();
			calendar.setTimeInMillis(t);
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			if (postTime.containsKey(hour)) {
				int value = postTime.get(hour);
				postTime.put(hour, value+1);
			}
		}
		return postTime;
	}

	@Override
	public String getName() {
		String name = "User's post time analysis";
		return name;
	}

}
