package com.retirement;

public class TaxParams {
	private double dbTaxlow;
	private double dbTaxhigh;
	private double dbTaxlowpc;
	private double dbTaxhighpc;
	

	public TaxParams(double dbTaxlow, double dbTaxhigh, double dbTaxlowpc, double dbTaxhighpc) {
		super();
		this.dbTaxlow = dbTaxlow;
		this.dbTaxhigh = dbTaxhigh;
		this.dbTaxlowpc = dbTaxlowpc;
		this.dbTaxhighpc = dbTaxhighpc;
	}

	public double getTaxLow() {
		return this.dbTaxlow;
	}

	public double getTaxHigh() {
		return this.dbTaxhigh;
	}

	public double getTaxLowpc() {
		return this.dbTaxlowpc;
	}

	public double getTaxHighpc() {
		return this.dbTaxhighpc;
	}
	
	public void inflateParams(double dRate) {
		dbTaxlow = dbTaxlow *(1 + dRate);
		dbTaxhigh = dbTaxhigh *(1 + dRate);
	}

}
