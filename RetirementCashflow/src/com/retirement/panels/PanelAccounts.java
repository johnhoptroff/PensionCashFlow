package com.retirement.panels;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class PanelAccounts extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable tableAccounts;
	/**
	 * Create the panel.
	 */
	public PanelAccounts() {
		tableAccounts = new JTable();
		tableAccounts.setModel(new AccountsTableModel());
		JScrollPane spAccTable = new JScrollPane(tableAccounts);
		this.add(spAccTable);
	}

}
