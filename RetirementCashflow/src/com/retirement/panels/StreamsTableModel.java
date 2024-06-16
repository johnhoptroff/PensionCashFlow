package com.retirement.panels;

import java.util.ArrayList;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import com.retirement.IncomeStream;

public class StreamsTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] strColNames = { "Name", "Start Date", "End Date", "Earning", "Rate" };
	private ArrayList<IncomeStream> alStreams;

	@Override
	public int getRowCount() {
		int iSize;
		if (alStreams == null) {
			iSize = 0;
		} else {
			iSize = alStreams.size();
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
			oTemp = alStreams.get(rowIndex).getName();
		} else if (columnIndex == 1) {
			oTemp = alStreams.get(rowIndex).getdateStart();
		} else if (columnIndex == 2) {
			oTemp = alStreams.get(rowIndex).getEndDate();
		} else if (columnIndex == 3) {
			oTemp = alStreams.get(rowIndex).getdStipend();
		} else if (columnIndex == 4) {
			oTemp = alStreams.get(rowIndex).getRate();
		}
		return oTemp;
	}

	// needed to show column name
	public String getColumnName(int col) {
		return strColNames[col];
	}

}
