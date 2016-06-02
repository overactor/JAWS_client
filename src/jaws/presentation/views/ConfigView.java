package jaws.presentation.views;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ConfigView extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = -9041765805686943532L;
	
	JComboBox<String> presetCombo;
	JButton loadPresetButton, savePresetButton, newPresetButton;
	
	JTextField webrootField;

	JList<String> logList;
	JTextField logPathField;
	JButton applyButton, resetButton, onOffButton;

	public ConfigView() {

		super("JAWS config client");

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setLayout(new BorderLayout());

		// config panel
		JPanel configPanel = new JPanel(new GridBagLayout());
		add(configPanel, BorderLayout.NORTH);
		addWithConstraints(configPanel, new JLabel("Preset"), 0, 0);
		presetCombo = new JComboBox<>();
		addWithConstraints(configPanel, presetCombo, 1, 0, 2, 1);
		
		loadPresetButton = new JButton("load");
		savePresetButton = new JButton("save");
		newPresetButton = new JButton("new");
		addWithConstraints(configPanel, loadPresetButton, 3, 0);
		addWithConstraints(configPanel, savePresetButton, 4, 0);
		addWithConstraints(configPanel, newPresetButton, 5, 0);
		
		addWithConstraints(configPanel, new JLabel("Webroot"), 0, 1);
		webrootField = new JTextField();
		addWithConstraints(configPanel, webrootField, 1, 1, 5, 1);

		// log panel
		JPanel logPanel = new JPanel(new BorderLayout());

		JPanel logPathPanel = new JPanel(new BorderLayout());
		logPanel.add(logPathPanel, BorderLayout.NORTH);
		logPathPanel.add(new JLabel("Logs"), BorderLayout.WEST);
		logPathField = new JTextField();
		logPathPanel.add(logPathField, BorderLayout.CENTER);

		logList = new JList<String>();
		logPanel.add(new JScrollPane(logList), BorderLayout.CENTER);

		add(logPanel, BorderLayout.CENTER);

		// bottom panel
		JPanel bottomPanel = new JPanel(new BorderLayout());
		add(bottomPanel, BorderLayout.SOUTH);

		JPanel bottomButtonsPanel = new JPanel();
		bottomPanel.add(bottomButtonsPanel, BorderLayout.WEST);
		bottomButtonsPanel.setLayout(new BoxLayout(bottomButtonsPanel, BoxLayout.X_AXIS));
		applyButton = new JButton("apply");
		bottomButtonsPanel.add(applyButton);
		resetButton = new JButton("reset");
		bottomButtonsPanel.add(resetButton);

		onOffButton = new JButton("on/off");
		bottomPanel.add(onOffButton, BorderLayout.EAST);

		setBounds(200, 200, 400, 600);
		setVisible(true);
	}
	
	private static void addWithConstraints(Container container, JComponent component,
	                               int gridx, int gridy) {
		addWithConstraints(container, component, gridx, gridy, 1, 1);
	}
	
	private static void addWithConstraints(Container container, JComponent component,
	                               int gridx, int gridy,
	                               int gridWidth, int gridHeight) {
		addWithConstraints(container, component, gridx, gridy, gridWidth, gridHeight, gridWidth, gridHeight);
	}
		
	private static void addWithConstraints(Container container, JComponent component,
	                               int gridx, int gridy,
	                               int gridWidth, int gridHeight,
	                               int weightx, int weighty) {
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
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
