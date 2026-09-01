package com.retirement;


public class TaxForm {
	private double dTaxableEarnings;
	private double dRentalIncome;
	private double dInterest;
	private double dDividend;
	private double dSharesGain;
	private double dBondChargeOff;
	private double dBondChargeOn;
	
	public TaxForm(double dTaxableEarnings, double dRentalIncome, double dInterest, double dDividend,
			double dSharesGain, double dBondChargeOff, double dBondChargeOn) {
		this.dTaxableEarnings = dTaxableEarnings;
		this.dRentalIncome = dRentalIncome;
		this.dInterest = dInterest;
		this.dDividend = dDividend;
		this.dSharesGain = dSharesGain;
		this.dBondChargeOff = dBondChargeOff;
		this.dBondChargeOn = dBondChargeOn;
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


	public double getdSharesGain() {
		return dSharesGain;
	}


	public double getdBondChargeOff() {
		return dBondChargeOff;
	}

	public double getRentalIncome() {
		return this.dRentalIncome;
	}

	public double getdBondChargeOn() {
		return this.dBondChargeOn;
	}

	public void setInterest(double d) {
		this.dInterest = d;
		
	}

	public void setPension(double d) {
		this.dTaxableEarnings += d;
		
	}

	public void setBondsCharge(BondAccount acc, double d) {
		if(acc instanceof AccOffBond) {
			this.dBondChargeOff = d;
		}else {
			this.dBondChargeOn = d;
		}
		
	}
	
}
