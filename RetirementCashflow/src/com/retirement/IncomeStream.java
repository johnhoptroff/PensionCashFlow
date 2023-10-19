package com.retirement;

public class IncomeStream {
private boolean isTimebound;
private boolean isStarted;
private double dStipend;
public IncomeStream(boolean isTimebound, boolean isStarted, double dStipend) {
	super();
	this.isTimebound = isTimebound;
	this.isStarted = isStarted;
	this.dStipend = dStipend;
}
public boolean isTimebound() {
	return isTimebound;
}
public void setTimebound(boolean isTimebound) {
	this.isTimebound = isTimebound;
}
public boolean isStarted() {
	return isStarted;
}
public void setStarted(boolean isStarted) {
	this.isStarted = isStarted;
}
public double getdStipend() {
	return dStipend;
}
public void setdStipend(double dStipend) {
	this.dStipend = dStipend;
}



}
