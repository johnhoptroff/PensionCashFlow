package com.retirement.panels;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class PanelStreams extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable tableStreams;
	/**
	 * Create the panel.
	 */
	public PanelStreams() {
		tableStreams = new JTable();
		tableStreams.setModel(new StreamsTableModel());
		JScrollPane spStreamsTable = new JScrollPane(tableStreams);
		this.add(spStreamsTable);
	}

}
