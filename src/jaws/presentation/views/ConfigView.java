package jaws.presentation.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

public class ConfigView extends JFrame {

	JList logList;

	public ConfigView() {

		super("JAWS config client");

		setLayout(new BorderLayout());

		// config panel
		JPanel configPanel = new JPanel(new GridLayout(2, 2));
		add(configPanel, BorderLayout.NORTH);

		// log panel
		JPanel logPanel = new JPanel(new BorderLayout());

		JPanel logPathPanel = new JPanel(new BorderLayout());
		logPanel.add(logPathPanel, BorderLayout.NORTH);

		logList = new JList<String>();
		logPanel.add(logList, BorderLayout.CENTER);

		add(logPanel, BorderLayout.CENTER);

		// bottom panel
		JPanel bottomPanel = new JPanel(new BorderLayout());
		add(bottomPanel, BorderLayout.SOUTH);
	}
}
