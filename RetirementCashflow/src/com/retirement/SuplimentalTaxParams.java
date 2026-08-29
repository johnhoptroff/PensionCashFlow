package com.retirement;

public class SuplimentalTaxParams {

	private double dbCGTLimit;
	private double dbLowTaxDiviLimit;
	private double dbHighTaxDiviLimit;
	private double dLowTaxCGTrate;
	private double dHighTaxGCTrate;
	private double dSRSB;
	private double dPSAlow;
	private double dPSAhigh;
	private double dRentAllowance;
	private double dDiviAllowance;
	private double dDiviRateLow;
	private double dDiviRateHigh;

	public SuplimentalTaxParams(double dCGTLimit, double dLowTaxDiviLimit, double dbHighTaxDiviLimit,
			double dLowTaxCGTrate, double dHighTaxCGTrate, double dSRSB, double dPSAlow, double dPSAHigh,
			double dRentAllowance, double dDiviAllowance, double dDiviRateLow, double dDiviRateHigh) {

		this.setDbCGTLimit(dCGTLimit);
		this.setDbLowTaxDiviLimit(dLowTaxDiviLimit);
		this.setDbHighTaxDiviLimit(dbHighTaxDiviLimit);
		this.setdLowTaxCGTrate(dLowTaxCGTrate);
		this.setdHighTaxGCTrate(dHighTaxCGTrate);
		this.setSRSB(dSRSB);
		this.setdPSAlow(dPSAlow);
		this.setdPSAhigh(dPSAHigh);
		this.setdRentAllowance(dRentAllowance);
		this.dDiviAllowance = dDiviAllowance;
		this.dDiviRateLow = dDiviRateLow;
		this.dDiviRateHigh = dDiviRateHigh;

	}

	public double getDbCGTLimit() {
		return dbCGTLimit;
	}

	public void setDbCGTLimit(double dbCGTLimit) {
		this.dbCGTLimit = dbCGTLimit;
	}

	public double getDbLowTaxDiviLimit() {
		return dbLowTaxDiviLimit;
	}

	public void setDbLowTaxDiviLimit(double dbLowTaxDiviLimit) {
		this.dbLowTaxDiviLimit = dbLowTaxDiviLimit;
	}

	public double getDbHighTaxDiviLimit() {
		return dbHighTaxDiviLimit;
	}

	public void setDbHighTaxDiviLimit(double dbHighTaxDiviLimit) {
		this.dbHighTaxDiviLimit = dbHighTaxDiviLimit;
	}

	public double getdLowTaxCGTrate() {
		return dLowTaxCGTrate;
	}

	public void setdLowTaxCGTrate(double dLowTaxCGTrate) {
		this.dLowTaxCGTrate = dLowTaxCGTrate;
	}

	public double getdHighTaxGCTrate() {
		return dHighTaxGCTrate;
	}

	public void setdHighTaxGCTrate(double dHighTaxGCTrate) {
		this.dHighTaxGCTrate = dHighTaxGCTrate;
	}

	public void inflateParams(double dRate) {
		dbCGTLimit = dbCGTLimit * (1 + dRate);
		dPSAhigh = dPSAhigh * (1 + dRate);
		dPSAlow = dPSAlow * (1 + dRate);
		dbLowTaxDiviLimit = dbLowTaxDiviLimit * (1 + dRate);
		dbHighTaxDiviLimit = dbHighTaxDiviLimit * (1 + dRate);
	}

	public double getSRSB() {
		return this.dSRSB;
	}

	public void setSRSB(double dSRSB) {
		this.dSRSB = dSRSB;
	}

	public double getdPSAlow() {
		return dPSAlow;
	}

	public void setdPSAlow(double dPSAlow) {
		this.dPSAlow = dPSAlow;
	}

	public double getdPSAhigh() {
		return dPSAhigh;
	}

	public void setdPSAhigh(double dPSAhigh) {
		this.dPSAhigh = dPSAhigh;
	}

	public double getdRentAllowance() {
		return dRentAllowance;
	}

	public void setdRentAllowance(double dRentAllowance) {
		this.dRentAllowance = dRentAllowance;
	}

	public double getDiviAllowance() {
		return dDiviAllowance;
	}

	public double getDiviRateLow() {

		return this.dDiviRateLow;
	}

	public double getDiviRateHigh() {

		return this.dDiviRateHigh;
	}

}
