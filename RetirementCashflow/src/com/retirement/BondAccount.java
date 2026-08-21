package com.retirement;

import java.time.LocalDate;
import java.time.Period;

public class BondAccount extends AccountAbstract {

	private LocalDate dtOpened;
	private double dCummulativeGain = 0.0;
	private double dCummulativeWithdrawal = 0.0;
	private double dChareable =0.0;

	public BondAccount(String strName, double dOpenBal, double dRate, LocalDate dtOpened) {
		super(strName, dOpenBal, dRate);
		super.setTaxInterest(false);
		super.setEarnings(true);
		this.setDtOpened(dtOpened);
	}

	public LocalDate getDtOpened() {
		return dtOpened;
	}

	private void setDtOpened(LocalDate dtOpened) {
		this.dtOpened = dtOpened;
	}

	private double calcAllowable(LocalDate dateWithdrawal) {
		Period period = Period.between(dateWithdrawal, dtOpened);
		int iYears = period.getYears();

		double dAllowableWithdrawal = iYears * (this.getdOpenBal() / 20.0);
		if (dAllowableWithdrawal > this.getdOpenBal()) {
			dAllowableWithdrawal = this.getdOpenBal();
		}

		return dAllowableWithdrawal;
	}

	public void withdrawBond(double dAmount, LocalDate dateWithdrawal) {
		this.dCummulativeWithdrawal = this.dCummulativeWithdrawal + dAmount;
		double dGain = (dCummulativeWithdrawal - (this.getdOpenBal() + dCummulativeGain));
		dGain = (dCummulativeWithdrawal - (this.getdOpenBal() + dCummulativeGain));
		if (this.dCummulativeWithdrawal < calcAllowable(dateWithdrawal))
			dGain = 0.0;
		if (dGain > 0)
			this.dCummulativeGain = this.dCummulativeGain + dGain;
	}

	public double getCharge() {
		return this.dChareable;
	}

}
