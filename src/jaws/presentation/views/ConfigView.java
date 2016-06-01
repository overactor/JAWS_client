package jaws.presentation.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
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

	JList<String> logList;

	JTextField logPathField;

	JButton applyButton, resetButton, onOffButton;

	public static void main(String[] args) {
		new ConfigView();
	}

	public ConfigView() {

		super("JAWS config client");

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setLayout(new BorderLayout());

		// config panel
		JPanel configPanel = new JPanel(new GridLayout(2, 2));
		add(configPanel, BorderLayout.NORTH);

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
}
