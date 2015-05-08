package edu.cmu.cs.cs214.hw5.framework;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.cs.cs214.hw5.plugin.DataPlugin;
import edu.cmu.cs.cs214.hw5.plugin.AnalysisPlugin;

import javax.swing.JPanel;

/**
 * a implementation framework
 * 
 * @author Team28
 *
 */
public class FrameworkImpl implements Framework {
	private AnalysisPlugin currentAnalysis;
	private DataPlugin currentData;
	private JPanel currentPane;

	private List<AnalysisPlugin> analysisPlugins;
	private List<DataPlugin> dataPlugins;
	private List<JPanel> allPanels;

	private List<FrameworkChangeListener> changeListener;
	private UserInfoImpl userInfo;

	/**
	 * constructor
	 */
	public FrameworkImpl() {
		currentAnalysis = null;
		currentData = null;
		userInfo = null;
	}

	@Override
	public void initialize() {
		allPanels = new ArrayList<JPanel>();
		analysisPlugins = new ArrayList<AnalysisPlugin>();
		dataPlugins = new ArrayList<DataPlugin>();
		changeListener = new ArrayList<FrameworkChangeListener>();
	}

	@Override
	public void registerDataPlugin(DataPlugin plugin) {
		dataPlugins.add(plugin);
	}

	@Override
	public void registerAnalysisPlugin(AnalysisPlugin plugin) {
		analysisPlugins.add(plugin);
	}

	@Override
	public void addChangerListener(FrameworkChangeListener listener) {
		changeListener.add(listener);
	}

	@Override
	public boolean isValidUserName(String userName, int postNum) {
		if (currentData.extractPostsByName(userName, postNum) == null)
			return false;
		return true;
	}

	@Override
	public void generateData(String userName, int postNum, int fNum) {
		notifyDataRetrieving();
		List<Post> hostPosts = currentData
				.extractPostsByName(userName, postNum);
		UserBean host = new UserBean(userName, hostPosts);
		List<String> friendsNames = currentData.extractConnectorsByName(
				userName, fNum);
		List<UserBean> friends = new ArrayList<UserBean>();
		for (String name : friendsNames) {
			List<Post> friendPosts = currentData.extractPostsByName(name,
					postNum);
			UserBean friend = new UserBean(name, friendPosts);
			friends.add(friend);
		}
		userInfo = new UserInfoImpl(host, friends);
		notifyDataReturned();
	}

	@Override
	public void showAnalysis() {
		currentPane = currentAnalysis.analyze(userInfo);
		notifyAnalysisFinished(currentPane);
		allPanels.add(currentPane);
	}

	@Override
	public void backToFormer() {
		int index = allPanels.indexOf(currentPane);
		if (index > 0 && allPanels.size() > 0) {
			currentPane = allPanels.get(index - 1);
		}
	}

	@Override
	public void goToNext() {
		int index = allPanels.indexOf(currentPane);
		if (index >= 0 && index < allPanels.size() - 1) {
			currentPane = allPanels.get(index + 1);
		}
	}

	/**
	 * notify change listener data is delivered to framework
	 */
	public void notifyDataReturned() {
		for (FrameworkChangeListener f : changeListener)
			f.onDataReturned();
	}

	/**
	 * notify changelistener framework start to retrieve data from data plugin
	 */
	public void notifyDataRetrieving() {
		for (FrameworkChangeListener f : changeListener)
			f.onDataRetrieving();
	}

	/**
	 * notify listener analysis is finished
	 * 
	 * @param result
	 *            the result JPanel
	 */
	public void notifyAnalysisFinished(JPanel result) {
		for (FrameworkChangeListener f : changeListener)
			f.onAnalysisFinished(result);
	}

	/**
	 * get the current analysis plugin
	 * 
	 * @return current analysis plugin
	 */
	public AnalysisPlugin getCurrentAnalysis() {
		return currentAnalysis;
	}

	/**
	 * set current analysis plugin
	 * 
	 * @param ca
	 *            analysis plugin to set
	 */
	public void setCurrentAnalysis(AnalysisPlugin ca) {
		this.currentAnalysis = ca;
	}

	/**
	 * @return the current data plugin
	 */
	public DataPlugin getCurrentData() {
		return currentData;
	}

	/**
	 * set current data plugin
	 * 
	 * @param cd
	 *            current plugin to set
	 */
	public void setCurrentData(DataPlugin cd) {
		this.currentData = cd;
	}

	/**
	 * get the current analysis panel
	 * 
	 * @return current panel
	 */
	public JPanel getCurrentPane() {
		return currentPane;
	}

	/**
	 * set current panel
	 * 
	 * @param cp
	 *            current panel to set
	 */
	public void setCurrentPane(JPanel cp) {
		this.currentPane = cp;
	}

	/**
	 * get analysis plugin list
	 * 
	 * @return the analysis plugins
	 */
	public List<AnalysisPlugin> getAnalysisPlugins() {
		return analysisPlugins;
	}

	/**
	 * @return return data plugin list
	 */
	public List<DataPlugin> getDataPlugins() {
		return dataPlugins;
	}

	/**
	 * @return userInfo for the current user
	 */
	public UserInfoImpl getUserInfo() {
		return userInfo;
	}

	/**
	 * 
	 * @return return all the result panels
	 */
	public List<JPanel> getAllPanels() {
		return allPanels;
	}

	/**
	 * get all the change listeners
	 * 
	 * @return all the change listeners
	 */
	public List<FrameworkChangeListener> getChangeListener() {
		return changeListener;
	}

}
