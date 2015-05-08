package edu.cmu.cs.cs214.hw5.plugin;

import java.awt.BorderLayout;
import java.awt.Color;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import edu.cmu.cs.cs214.hw5.framework.Post;
import edu.cmu.cs.cs214.hw5.framework.UserInfoImpl;

/**
 * This analysis plug in will analyze this person's personality It will show a
 * histogram graph includes this user's everyday post amount user's average post
 * length and user's latest post time
 *
 */
public class PersonalityAnalysis implements AnalysisPlugin {
	private static final int COLOR1 = 0xCC;
	private static final int COLOR2 = 0xFF;
	private static final int HOUR = 24;
	private static final int THREE = 3;
	private static final int FOUR = 4;
	private static final int FIVE = 5;
	private static final int SIX = 6;
	private static final int SEVEN = 7;

	@Override
	public JPanel analyze(UserInfoImpl userInfo) {
		JPanel analyze = new JPanel(new BorderLayout());

		final CategoryDataset dataset1 = createDataset1(userInfo);

		// create the chart...
		final JFreeChart chart = ChartFactory.createBarChart3D(
				"Personality analysis", // chart title
				"Category", // domain axis label
				"Value", // range axis label
				dataset1, // data
				PlotOrientation.VERTICAL, true, true, false);

		chart.setBackgroundPaint(new Color(COLOR1, COLOR2, COLOR1));

		CategoryPlot plot = chart.getCategoryPlot();
		plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		plot.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);

		CategoryItemRenderer renderer1 = plot.getRenderer();
		renderer1.setSeriesPaint(0, Color.red);
		renderer1.setSeriesPaint(1, Color.yellow);
		renderer1.setSeriesPaint(2, Color.green);

		plot.mapDatasetToRangeAxis(1, 1);
		CategoryItemRenderer renderer2 = new LineAndShapeRenderer();
		renderer2.setSeriesPaint(0, Color.blue);
		plot.setRenderer(1, renderer2);
		plot.setNoDataMessage("No data available");

		plot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);

		final ChartPanel chartPanel = new ChartPanel(chart);

		analyze.add(chartPanel);
		return analyze;

	}

	private CategoryDataset createDataset1(UserInfoImpl userInfo) {

		int postAmountSun = 0;
		int postAmountMon = 0;
		int postAmountTue = 0;
		int postAmountWed = 0;
		int postAmountThur = 0;
		int postAmountFri = 0;
		int postAmountSat = 0;

		int postLengthSun = 0;
		int postLengthMon = 0;
		int postLengthTue = 0;
		int postLengthWed = 0;
		int postLengthThur = 0;
		int postLengthFri = 0;
		int postLengthSat = 0;

		int postTimeSun = 0;
		int postTimeMon = 0;
		int postTimeTue = 0;
		int postTimeWed = 0;
		int postTimeThur = 0;
		int postTimeFri = 0;
		int postTimeSat = 0;

		List<Post> posts = userInfo.getSortPostsByTime();
		for (Post p : posts) {
			long time = p.getTime();
			Calendar calendar = new GregorianCalendar();
			calendar.setTimeInMillis(time);
			int day = calendar.get(Calendar.DAY_OF_WEEK);
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			if (hour == 0)
				hour = HOUR;
			if (day == 1) {
				postAmountSun++;
				if (hour > postTimeSun) {
					postTimeSun = hour;
				}

				String[] words = p.getContent().split("\\W");
				postLengthSun += words.length;
			} else if (day == 2) {
				postAmountMon++;

				if (hour > postTimeMon) {
					postTimeMon = hour;
				}
				String[] words = p.getContent().split("\\W");
				postLengthMon += words.length;

			} else if (day == THREE) {
				postAmountTue++;

				if (hour > postTimeTue) {
					postTimeTue = hour;
				}
				String[] words = p.getContent().split("\\W");
				postLengthTue += words.length;

			} else if (day == FOUR) {
				postAmountWed++;

				if (hour > postTimeWed) {
					postTimeWed = hour;
				}
				String[] words = p.getContent().split("\\W");
				postLengthWed += words.length;
			} else if (day == FIVE) {
				postAmountThur++;
				if (hour > postTimeThur) {
					postTimeThur = hour;
				}
				String[] words = p.getContent().split("\\W");
				postLengthThur += words.length;

			} else if (day == SIX) {
				postAmountFri++;
				if (hour > postTimeFri) {
					postTimeFri = hour;
				}
				String[] words = p.getContent().split("\\W");
				postLengthFri += words.length;

			} else if (day == SEVEN) {
				postAmountSat++;
				if (hour > postTimeSat) {
					postTimeSat = hour;
				}
				String[] words = p.getContent().split("\\W");
				postLengthSat += words.length;

			}
		}
		if (postAmountSun == 0) {
			postLengthSun = 0;
		} else {
			postLengthSun = postLengthSun / postAmountSun;
		}

		if (postAmountMon == 0) {
			postLengthMon = 0;
		} else {
			postLengthMon = postLengthMon / postAmountMon;
		}

		if (postAmountTue == 0) {
			postLengthTue = 0;
		} else {
			postLengthTue = postLengthTue / postAmountTue;
		}
		if (postAmountWed == 0) {
			postLengthWed = 0;
		} else {
			postLengthWed = postLengthWed / postAmountWed;
		}
		if (postAmountThur == 0) {
			postLengthThur = 0;
		} else {
			postLengthThur = postLengthThur / postAmountThur;
		}
		if (postAmountFri == 0) {
			postLengthFri = 0;
		} else {
			postLengthFri = postLengthFri / postAmountFri;
		}
		if (postAmountSat == 0) {
			postLengthSat = 0;
		} else {
			postLengthSat = postLengthSat / postAmountSat;
		}

		// row keys...
		final String series1 = "Post amount";
		final String series2 = "Post average words";
		final String series3 = "Last post time";

		// column keys...
		final String category1 = "Mon";
		final String category2 = "Tue";
		final String category3 = "Wed";
		final String category4 = "Thurs";
		final String category5 = "Fri";
		final String category6 = "Sat";
		final String category7 = "Sun";

		// create the dataset...
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		dataset.addValue(postAmountMon, series1, category1);
		dataset.addValue(postAmountTue, series1, category2);
		dataset.addValue(postAmountWed, series1, category3);
		dataset.addValue(postAmountThur, series1, category4);
		dataset.addValue(postAmountFri, series1, category5);
		dataset.addValue(postAmountSat, series1, category6);
		dataset.addValue(postAmountSun, series1, category7);

		dataset.addValue(postLengthMon, series2, category1);
		dataset.addValue(postLengthTue, series2, category2);
		dataset.addValue(postLengthWed, series2, category3);
		dataset.addValue(postLengthThur, series2, category4);
		dataset.addValue(postLengthFri, series2, category5);
		dataset.addValue(postLengthSat, series2, category6);
		dataset.addValue(postLengthSun, series2, category7);

		dataset.addValue(postTimeMon, series3, category1);
		dataset.addValue(postTimeTue, series3, category2);
		dataset.addValue(postTimeWed, series3, category3);
		dataset.addValue(postTimeThur, series3, category4);
		dataset.addValue(postTimeFri, series3, category5);
		dataset.addValue(postTimeSat, series3, category6);
		dataset.addValue(postTimeSun, series3, category7);

		return dataset;
	}

	@Override
	public String getName() {
		String name = "Personality Analysis";
		return name;
	}

}
