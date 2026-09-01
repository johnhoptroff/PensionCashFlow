package com.retirement;

import java.time.LocalDate;

public class PremBondsAccount extends AccountAbstract {
	private double dMaxBal;

	public PremBondsAccount(String strName, double dOpenBal, double dRate, double dMaxBal) {

		super(strName, dOpenBal, dRate);
		super.setTaxInterest(false);
		super.setEarnings(false);
		this.dMaxBal = dMaxBal;
	}

	@Override
	public double deposit(double dMoney, LocalDate dateIn) {
		double dBal = super.getdBalance();
		if(dBal > (dMaxBal + dMoney)){
			return super.deposit(dMoney, dateIn);
		}else {
			if(dMaxBal-dBal != 0) super.deposit((dMaxBal-dBal) , dateIn);
			return (dMoney - (dMaxBal-dBal));
		}	
	}
}
