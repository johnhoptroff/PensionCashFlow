package com.retirement;

import java.time.LocalDate;

public class BondAccount extends AccountAbstract {

	private LocalDate dtOpened;

	public BondAccount(String strName, double dOpenBal, double dRate, LocalDate dtOpened) {
		super(strName, dOpenBal, dRate);
		super.setTaxInterest(false);
		super.setEarnings(true);
		this.setDtOpened(dtOpened);
	}

	public LocalDate getDtOpened() {
		return dtOpened;
	}

	public void setDtOpened(LocalDate dtOpened) {
		this.dtOpened = dtOpened;
	}

}
