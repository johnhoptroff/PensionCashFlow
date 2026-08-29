package com.retirement;

import java.time.LocalDate;

public class AccountShares extends AccountAbstract {

	private double dPool;
	private int iSharesHolding;
	private double dSharePrice;
	private double dDividend;
	private double dGain;

	public AccountShares(String strName, double dOpenBal, double dRate, double dPool, int iNumShares,
			double dDivPerShare) {
		super(strName, dOpenBal, dRate);
		super.setTaxInterest(false);
		super.setEarnings(false);
		this.setdPool(dPool);
		this.setiNumShares(iNumShares);
		this.dSharePrice = super.getdBalance() / this.iSharesHolding;
		this.dDividend = dDivPerShare * iNumShares;
	}

	public double getdPool() {
		return dPool;
	}

	public void setdPool(double dPool) {
		this.dPool = dPool;
	}

	public int getiNumShares() {
		return iSharesHolding;
	}

	public void setiNumShares(int iNumShares) {
		this.iSharesHolding = iNumShares;
	}

	public double getdSharePrice() {
		return dSharePrice;
	}

	public double getdDividend(LocalDate txYearStart) {
		return dDividend;
	}

	@Override
	public double getdBalance() {
		return dSharePrice * iSharesHolding;
	}

	private void sell(int iAmount, double dPrice, LocalDate date) {
		// calculate allowable gain and number of shares required to hit CGT limit
		double dSaleFunds = dPrice * iAmount;
		double dSalePropn = iAmount / iSharesHolding;
		double dAlowableGain = dSalePropn * this.dPool;
		double dCharge = dSaleFunds - dAlowableGain;
		this.iSharesHolding = this.iSharesHolding - iAmount;
		this.dPool = this.dPool - dAlowableGain;
		this.dGain = dCharge;
	}

	public void selltoAProfit(double dProfit, double dPrice, LocalDate date) {
		int iAmnt = (int) (dProfit / (this.dSharePrice - (this.dPool / this.iSharesHolding)));
		this.sell(iAmnt, dPrice, date);
	}

	public double getdGain() {
		return dGain;
	}

}
