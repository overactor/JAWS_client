package jaws.presentation.views;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SpinnerModel;

import jal.business.log.LogLevel;
import jaws.presentation.controllers.ConfigDelegate;
import jaws.presentation.controllers.MenuDelegate;
import jaws.presentation.controllers.PresetDelegate;

/**
 * A class representing the main view of the JAWS config-client
 * 
 * @author Rik
 */
public class ConfigView extends JFrame {

	private static final long serialVersionUID = -9041765805686943532L;

	JComboBox<String> presetCombo;

	JTextField webrootField;

	JTextField logPathField;
	JList<String> logList;
	JTextField logTagsField;
	JComboBox<LogLevel> logLevelCombo;
	
	JButton applyButton, resetButton, onOffButton;

	public ConfigView(PresetDelegate presetDelegate, ConfigDelegate configDelegate, MenuDelegate menuDelegate,
			ListModel<String> logsModel, SpinnerModel httpPortModel, SpinnerModel threadModel) {

		super("JAWS config client");

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setLayout(new GridBagLayout());
		Container pane = getContentPane();

		// main menu
		JMenuItem connectItem = new JMenuItem("connect");
		connectItem.addActionListener(ae -> menuDelegate.connectClicked());
		JMenuItem importItem = new JMenuItem("import");
		importItem.addActionListener(ae -> menuDelegate.importClicked());
		JMenuItem exportItem = new JMenuItem("export");
		exportItem.addActionListener(ae -> menuDelegate.exportClicked());

		JMenu connectionMenu = new JMenu("connection");
		connectionMenu.add(connectItem);

		JMenu importExportMenu = new JMenu("import/export");
		importExportMenu.add(importItem);
		importExportMenu.add(exportItem);

		JMenuBar mainMenu = new JMenuBar();
		mainMenu.add(connectionMenu);
		mainMenu.add(importExportMenu);

		setJMenuBar(mainMenu);

		// config
		addWithConstraints(pane, new JLabel("Preset"), 0, 0);
		presetCombo = new JComboBox<>();
		addWithConstraints(pane, presetCombo, 1, 0, 2, 1);

		JButton loadPresetButton = new JButton("load");
		loadPresetButton.addActionListener(ae -> presetDelegate.loadPresetClicked());
		JButton savePresetButton = new JButton("save");
		savePresetButton.addActionListener(ae -> presetDelegate.savePresetClicked());
		JButton deletePresetButton = new JButton("delete");
		deletePresetButton.addActionListener(ae -> presetDelegate.deletePresetClicked());
		addWithConstraints(pane, loadPresetButton, 3, 0);
		addWithConstraints(pane, savePresetButton, 4, 0);
		addWithConstraints(pane, deletePresetButton, 5, 0);

		addWithConstraints(pane, new JLabel("Webroot"), 0, 1);
		webrootField = new JTextField();
		addWithConstraints(pane, webrootField, 1, 1, 5, 1);

		addWithConstraints(pane, new JLabel("Port HTTP"), 0, 2);
		JSpinner httpPortSpinner = new JSpinner(httpPortModel);
		addWithConstraints(pane, httpPortSpinner, 1, 2, 2, 1);

		addWithConstraints(pane, new JLabel("Threads"), 3, 2);
		JSpinner threadSpinner = new JSpinner(threadModel);
		addWithConstraints(pane, threadSpinner, 4, 2, 2, 1, 0, 0);

		// logs
		addWithConstraints(pane, new JLabel("Logs"), 0, 3);
		logPathField = new JTextField();
		addWithConstraints(pane, logPathField, 1, 3, 5, 1);

		logList = new JList<>(logsModel);
		addWithConstraints(pane, new JScrollPane(logList), 0, 4, 6, 1, 6, 6);
		
		addWithConstraints(pane, new JLabel("Tags"), 0, 5);
		logTagsField = new JTextField();
		addWithConstraints(pane, logTagsField, 1, 5, 5, 1);
		
		addWithConstraints(pane, new JLabel("Log-Level"), 0, 6);
		logLevelCombo = new JComboBox<>();
		logLevelCombo.addItem(LogLevel.DEBUG);
		logLevelCombo.addItem(LogLevel.INFO);
		logLevelCombo.addItem(LogLevel.WARNING);
		logLevelCombo.addItem(LogLevel.ERROR);
		addWithConstraints(pane, logLevelCombo, 1, 6, 5, 1);
		
		// bottom
		applyButton = new JButton("apply");
		applyButton.addActionListener(ae -> configDelegate.applyClicked());
		addWithConstraints(pane, applyButton, 0, 7);
		
		resetButton = new JButton("reset");
		resetButton.addActionListener(ae -> configDelegate.resetClicked());
		addWithConstraints(pane, resetButton, 1, 7);

		onOffButton = new JButton("on/off");
		onOffButton.addActionListener(ae -> configDelegate.onOffClicked());
		addWithConstraints(pane, onOffButton, 5, 7);

		setBounds(200, 200, 500, 600);
		setMinimumSize(new Dimension(450, 550));
		setVisible(true);
	}
	
	/**
	 * Populates the preset combobox
	 * 
	 * @param names the names of the presets to populate the combobox with
	 */
	public void setPresetNames(List<String> names) {
		
		presetCombo.removeAll();
		names.stream().forEach(presetCombo::addItem);
	}
	
	/**
	 * @return the name of the currently selected preset
	 */
	public String getPresetName() {
		return presetCombo.getItemAt(presetCombo.getSelectedIndex());
	}
	
	/**
	 * @return the webroot as specified in the GUI
	 */
	public String getWebroot() {
		return webrootField.getText();
	}
	
	/**
	 * Sets the webroot setting in the GUI
	 * 
	 * @param webroot the webroot to set to
	 */
	public void setWebroot(String webroot) {
		webrootField.setText(webroot);
	}
	
	/**
	 * @return the path where the logs are saved on the server as specified in the GUI
	 */
	public String getLogPath() {
		return logPathField.getText();
	}
	
	/**
	 * Sets the logPath setting in the GUI
	 * 
	 * @param logPath the logPath to ste to
	 */
	public void setLogPath(String logPath) {
		logPathField.setText(logPath);
	}
	
	/**
	 * @return the minimum log level that gets shown.
	 */
	public LogLevel getLogLevel() {
		return logLevelCombo.getItemAt(logLevelCombo.getSelectedIndex());
	}

	private static void addWithConstraints(Container container, JComponent component,
	                               int gridx, int gridy) {
		addWithConstraints(container, component, gridx, gridy, 1, 1);
	}

	private static void addWithConstraints(Container container, JComponent component,
	                               int gridx, int gridy,
	                               int gridWidth, int gridHeight) {
		addWithConstraints(container, component, gridx, gridy, gridWidth, gridHeight, gridWidth == 1 ? 0 : gridWidth, gridHeight == 1 ? 0 : gridHeight);
	}

	private static void addWithConstraints(Container container, JComponent component,
	                               int gridx, int gridy,
	                               int gridWidth, int gridHeight,
	                               int weightx, int weighty) {

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = weighty > 0 ? GridBagConstraints.BOTH : GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(2, 5, 2, 5);
		constraints.gridx = gridx;
		constraints.gridy = gridy;
		constraints.gridwidth = gridWidth;
		constraints.gridheight = gridHeight;
		constraints.weightx = weightx;
		constraints.weighty = weighty;

		container.add(component);
		((GridBagLayout) container.getLayout()).setConstraints(component, constraints);
	}
}
