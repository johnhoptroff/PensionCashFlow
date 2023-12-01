package com.retirement;

import java.time.LocalDate;

public class IncomeStream {
	private LocalDate dateStart;
	private double dStipend;
	private LocalDate dateEnd;
	private boolean isTaxable;
	private String strName;
	private boolean isLiableNI;
	private double dRate;
	private boolean boolEmployment;

	public IncomeStream(String strName, LocalDate dateStart, LocalDate dateEnd, double dStipend, double dRate) {
		super();
		this.strName = strName;
		this.dateEnd = dateEnd;
		this.dateStart = dateStart;
		this.dStipend = dStipend;
		this.dRate = dRate;
	}

	public boolean isTaxable() {
		return isTaxable;
	}

	public void inflate() {
		this.dStipend = this.dStipend * (1+ this.dRate);
	}
	public void setEmploment(boolean isEmployment) {
		this.boolEmployment = isEmployment;
	}
	public void setTaxable(boolean isTaxable) {
		this.isTaxable = isTaxable;
	}

	public boolean isLiableNI() {
		return isLiableNI;
	}

	public LocalDate getdateStart() {
		return dateStart;
	}

	public double getRate() {
		return dRate;
	}
	
	public String getName() {
		return strName;
	}

	public double getdStipend() {
		return dStipend;
	}

	public void setdStipend(double dStipend) {
		this.dStipend = dStipend;
	}


	public LocalDate getEndDate() {
		return dateEnd;
	}

	public void setdIncrease(double dIncrease) {
		this.dStipend = dStipend + dIncrease;
	}

	public void setIncursNI(boolean boolIsNIable) {
		this.isLiableNI = boolIsNIable;

	}

	public boolean isEmployment() {
		return boolEmployment;
	}

}
