package jaws.presentation.views;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import jaws.business.ConfigDelegate;
import jaws.business.MenuDelegate;
import jaws.business.PresetDelegate;

public class ConfigView extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = -9041765805686943532L;

	JComboBox<String> presetCombo;

	JTextField webrootField;
	JSpinner httpPortSpinner, threadSpinner;

	JList<String> logList;
	JTextField logPathField;
	JButton applyButton, resetButton, onOffButton;

	public ConfigView(PresetDelegate presetDelegate, ConfigDelegate configDelegate, MenuDelegate menuDelegate) {

		super("JAWS config client");

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setLayout(new GridBagLayout());
		Container pane = getContentPane();
		
		JMenuBar mainMenu = new JMenuBar();
		JMenuItem connectItem = new JMenuItem("connect");
		connectItem.addActionListener(ae -> menuDelegate.connectClicked());
		JMenuItem importItem = new JMenuItem("import");
		JMenuItem exportItem = new JMenuItem("export");

		// config
		addWithConstraints(pane, new JLabel("Preset"), 0, 0);
		presetCombo = new JComboBox<>();
		addWithConstraints(pane, presetCombo, 1, 0, 2, 1);

		JButton loadPresetButton = new JButton("load");
		loadPresetButton.addActionListener(ae -> presetDelegate.loadPresetClicked());
		JButton savePresetButton = new JButton("save");
		savePresetButton.addActionListener(ae -> presetDelegate.savePresetClicked());
		JButton newPresetButton = new JButton("new");
		newPresetButton.addActionListener(ae -> presetDelegate.newPresetClicked());
		addWithConstraints(pane, loadPresetButton, 3, 0);
		addWithConstraints(pane, savePresetButton, 4, 0);
		addWithConstraints(pane, newPresetButton, 5, 0);

		addWithConstraints(pane, new JLabel("Webroot"), 0, 1);
		webrootField = new JTextField();
		addWithConstraints(pane, webrootField, 1, 1, 5, 1);

		addWithConstraints(pane, new JLabel("Port HTTP"), 0, 2);
		SpinnerModel model = new SpinnerNumberModel(80, 1, 65536, 1);
		httpPortSpinner = new JSpinner(model);
		addWithConstraints(pane, httpPortSpinner, 1, 2, 2, 1);

		addWithConstraints(pane, new JLabel("Threads"), 3, 2);
		model = new SpinnerNumberModel(10, 1, 9999, 1);
		threadSpinner = new JSpinner(model);
		addWithConstraints(pane, threadSpinner, 4, 2, 2, 1, 0, 0);

		// loglogPanel.add(logPathPanel, BorderLayout.NORTH);

		addWithConstraints(pane, new JLabel("Logs"), 0, 3);
		logPathField = new JTextField();
		addWithConstraints(pane, logPathField, 1, 3, 5, 1);

		logList = new JList<String>();
		addWithConstraints(pane, new JScrollPane(logList), 0, 4, 6, 1, 6, 6);

		applyButton = new JButton("apply");
		applyButton.addActionListener(ae -> configDelegate.applyClicked());
		addWithConstraints(pane, applyButton, 0, 5);
		resetButton = new JButton("reset");
		resetButton.addActionListener(ae -> configDelegate.resetClicked());
		addWithConstraints(pane, resetButton, 1, 5);

		onOffButton = new JButton("on/off");
		onOffButton.addActionListener(ae -> configDelegate.onOffClicked());
		addWithConstraints(pane, onOffButton, 5, 5);

		setBounds(200, 200, 500, 600);
		setMinimumSize(new Dimension(450, 550));
		setVisible(true);
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
