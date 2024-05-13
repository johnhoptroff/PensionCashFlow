package com.retirement;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class AccountsTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] strColNames = { "Name", "Opening Bal", "Rate", "Taxable?" };
	private ArrayList<Account> alAccounts;

	public AccountsTableModel() { // default constructor to show an empty table
		alAccounts = new ArrayList<Account>();
	}

	@Override
	public int getRowCount() {
		int iSize;
		if (alAccounts == null) {
			iSize = 0;
		} else {
			iSize = alAccounts.size();
		}
		return iSize;
	}

	@Override
	public int getColumnCount() {
		return strColNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object oTemp = null;
		if (columnIndex == 0) {
			oTemp = alAccounts.get(rowIndex).getName();
		} else if (columnIndex == 1) {
			oTemp = alAccounts.get(rowIndex).getdOpenBal();
		} else if (columnIndex == 2) {
			oTemp = alAccounts.get(rowIndex).getdRate();
		} else if (columnIndex == 3) {
			oTemp = alAccounts.get(rowIndex).isTaxable();
		}
		return oTemp;
	}
	// needed to show column name
	public String getColumnName(int col) {
		return strColNames[col];
	}

}
