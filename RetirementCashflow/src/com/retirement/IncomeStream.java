package com.retirement;

import java.time.LocalDate;

public class IncomeStream {
private boolean isTimebound;
private LocalDate dateStart;
private double dStipend;
private LocalDate dateEnd;

public IncomeStream(boolean isTimebound, LocalDate dateStart, double dStipend) {
	super();
	this.isTimebound = isTimebound;
	this.dateStart = dateStart;
	this.dStipend = dStipend;
}
public IncomeStream(Account accAvivaFund, boolean isTimebound, boolean isTaxable) {
	// TODO Auto-generated constructor stub
}
public boolean isTimebound() {
	return isTimebound;
}

public LocalDate getiStartYear() {
	return dateStart;
}

public double getdStipend(LocalDate dateInstance) {

	if(isTimebound && dateInstance.isAfter(dateStart)) {
		return dStipend;
	}else {
		return 0.0;
	}
}
public void setdStipend(double dStipend) {
	this.dStipend = dStipend;
}
public void setEndDate(LocalDate dateEnd) {
	this.dateEnd = dateEnd;
}
public LocalDate getEndDate() {
	return dateEnd;
}
public void setdIncrease(double dIncrease) {
	this.dStipend = dStipend + dIncrease;
}



}
