package edu.cmu.cs.cs214.hw5.framework;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs.cs214.hw5.plugin.AnalysisPlugin;
import edu.cmu.cs.cs214.hw5.plugin.DataPlugin;

/**
 * Test framework implementation
 *
 */
public class FrameworkTest {
	private FrameworkImpl framework;

	@Before
	public void setUp() {
		framework = new FrameworkImpl();
		framework.initialize();
		framework.registerDataPlugin(new DataPlugin() {

			@Override
			public String getSourceName() {
				return "TestDataPlugin";
			}

			@Override
			public List<Post> extractPostsByName(String userName, int postNum) {
				return new ArrayList<Post>();
			}

			@Override
			public List<String> extractConnectorsByName(String userName,
					int fNum) {
				String friend1 = "1", friend2 = "2";
				List<String> friends = new ArrayList<String>();
				friends.add(friend1);
				friends.add(friend2);
				return friends;
			}
		});
		framework.registerAnalysisPlugin(new AnalysisPlugin() {

			@Override
			public String getName() {
				return "TestAnalysisPlugin";
			}

			@Override
			public JPanel analyze(UserInfoImpl userInfo) {
				JPanel pane = new JPanel();
				pane.add(new JTextField(40));
				return pane;
			}
		});
		framework.addChangerListener(new FrameworkChangeListener() {

			@Override
			public void onDataReturned() {
			}

			@Override
			public void onAnalysisFinished(JPanel result) {
			}

			@Override
			public void onDataRetrieving() {
			}

			@Override
			public void onInvalidUser() {
			}
		});
		framework.setCurrentData(framework.getDataPlugins().get(0));
		framework.setCurrentAnalysis(framework.getAnalysisPlugins().get(0));
	}

	@Test
	public void testInitialization() {
		assertEquals(framework.getChangeListener().size(), 1);
		assertEquals(framework.getDataPlugins().size(), 1);
		assertEquals(framework.getAnalysisPlugins().size(), 1);
		assertEquals(framework.getAllPanels().size(), 0);
	}

	@Test
	public void testData() {
		assertNull(framework.getUserInfo());
		framework.generateData("testHost", 20, 20);
		assertNotNull(framework.getUserInfo());
		assertEquals(framework.getUserInfo().getFriends().size(), 2);
		assertEquals(framework.getUserInfo().getHostName(), "testHost");
	}

	@Test
	public void testAnalysis() {
		assertNull(framework.getCurrentPane());
		assertEquals(framework.getAllPanels().size(), 0);
		framework.showAnalysis();
		assertNotNull(framework.getCurrentPane());
		assertEquals(framework.getAllPanels().size(), 1);
	}

	@Test
	public void testBackAndNext() {
		framework.showAnalysis();
		assertNotNull(framework.getCurrentPane());
		JPanel first = framework.getCurrentPane();
		assertEquals(framework.getAllPanels().size(), 1);

		framework.showAnalysis();
		JPanel second = framework.getCurrentPane();
		assertNotNull(framework.getCurrentPane());
		assertEquals(framework.getAllPanels().size(), 2);

		assertNotEquals(first, second);

		framework.backToFormer();
		assertEquals(first, framework.getCurrentPane());

		framework.goToNext();
		assertEquals(second, framework.getCurrentPane());
	}
}
