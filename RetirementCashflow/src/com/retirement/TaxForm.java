package com.retirement;


public class TaxForm {
	private double dTaxableEarnings;
	private double dRentalIncome;
	private double dInterest;
	private double dDividend;
	private double dBondEarnings;
	private double dSharesGain;
	private double dBondChargeable;
	
	public TaxForm(double dTaxableEarnings, double dRentalIncome, double dInterest, double dDividend, double dBondEarnings,
			double dSharesGain, double dBondChargeable) {
		this.dTaxableEarnings = dTaxableEarnings;
		this.dRentalIncome = dRentalIncome;
		this.dInterest = dInterest;
		this.dDividend = dDividend;
		this.dBondEarnings = dBondEarnings;
		this.dSharesGain = dSharesGain;
		this.dBondChargeable = dBondChargeable;
	}

	public double getdTaxableEarnings() {
		return dTaxableEarnings;
	}

	public double getdInterest() {
		return dInterest;
	}


	public double getdDividend() {
		return dDividend;
	}


	public double getdBondEarnings() {
		return dBondEarnings;
	}


	public double getdSharesGain() {
		return dSharesGain;
	}


	public double getdBondChargeable() {
		return dBondChargeable;
	}

	public double getRentalIncome() {
		return this.dRentalIncome;
	}
	
	
	
	
}
