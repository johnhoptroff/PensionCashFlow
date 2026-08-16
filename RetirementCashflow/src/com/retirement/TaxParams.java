package com.retirement;

import java.time.LocalDate;

public class TaxParams {
	private double dbTaxlow;
	private double dbTaxhigh;
	private double dbTaxlowpc;
	private double dbTaxhighpc;
	private double dbISAlimit;
	private LocalDate dtFrozenTh;
	

	public TaxParams(double dbTaxlow, double dbTaxhigh, double dbTaxlowpc, double dbTaxhighpc, double dbISAlimit, LocalDate dtFrozenTh) {
		super();
		this.dbTaxlow = dbTaxlow;
		this.dbTaxhigh = dbTaxhigh;
		this.dbTaxlowpc = dbTaxlowpc;
		this.dbTaxhighpc = dbTaxhighpc;
		this.dbISAlimit = dbISAlimit;
		this.dtFrozenTh = dtFrozenTh;
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
	
	public double getISAlimit() {
		return this.dbISAlimit;
	}
	
	public LocalDate getFrozenTh() {
		return this.dtFrozenTh;
	}
	
	public void inflateParams(double dRate) {
		dbTaxlow = dbTaxlow *(1 + dRate);
		dbTaxhigh = dbTaxhigh *(1 + dRate);
	}

}
