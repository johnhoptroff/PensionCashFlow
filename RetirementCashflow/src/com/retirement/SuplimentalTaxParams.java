package com.retirement;

public class SuplimentalTaxParams {

	private double dbCGTLimit;
	private double dbLowTaxIntlimit;
	private double dbHighTaxIntLimit;
	private double dbLowTaxDiviLimit;
	private double dbHighTaxDiviLimit;
	private double dLowTaxCGTrate;
	private double dHighTaxGCTrate;
	private double dSRSB;
	private double dPSAlow;
	private double dPSAhigh;
	private double dRentAllowance;

	public SuplimentalTaxParams(double dCGTLimit, double dbLowTaxIntLimit, double dbHighTaxIntLimit,
			double dLowTaxDiviLimit, double dbHighTaxDiviLimit, double dLowTaxCGTrate, double dHighTaxCGTrate, 
			double dSRSB, double dPSAlow, double dPSAHigh, double dRentAllowance) {
		
		this.setDbCGTLimit(dCGTLimit);
		this.setDbLowTaxIntlimit(dbLowTaxIntLimit);
		this.setDbHighTaxIntLimit(dbHighTaxIntLimit);
		this.setDbLowTaxDiviLimit(dLowTaxDiviLimit);
		this.setDbHighTaxDiviLimit(dbHighTaxDiviLimit);
		this.setdLowTaxCGTrate(dLowTaxCGTrate);
		this.setdHighTaxGCTrate(dHighTaxCGTrate);
		this.setSRSB(dSRSB);
		this.setdPSAlow(dPSAlow);
		this.setdPSAhigh(dPSAHigh);
		this.setdRentAllowance(dRentAllowance);
	}

	public double getDbCGTLimit() {
		return dbCGTLimit;
	}

	public void setDbCGTLimit(double dbCGTLimit) {
		this.dbCGTLimit = dbCGTLimit;
	}

	public double getDbLowTaxIntlimit() {
		return dbLowTaxIntlimit;
	}

	public void setDbLowTaxIntlimit(double dbLowTaxIntlimit) {
		this.dbLowTaxIntlimit = dbLowTaxIntlimit;
	}

	public double getDbHighTaxIntLimit() {
		return dbHighTaxIntLimit;
	}

	public void setDbHighTaxIntLimit(double dbHighTaxIntLimit) {
		this.dbHighTaxIntLimit = dbHighTaxIntLimit;
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
		dbCGTLimit = dbCGTLimit *(1+dRate);
		dbLowTaxIntlimit = dbLowTaxIntlimit *(1+dRate);
		dbHighTaxIntLimit = dbHighTaxIntLimit *(1+dRate);
		dbLowTaxDiviLimit = dbLowTaxDiviLimit *(1+dRate);
		dbHighTaxDiviLimit = dbHighTaxDiviLimit *(1+dRate);
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

}
