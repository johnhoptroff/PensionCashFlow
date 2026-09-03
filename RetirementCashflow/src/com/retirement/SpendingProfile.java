package com.retirement;

import java.time.LocalDate;

public class SpendingProfile {
	private double dBudget;
	private double dInflation;
	private LocalDate dateKinkPoint;
	private LocalDate dateStart;
	private LocalDate dateEnd;
	private double dIncrease;
	private double dInitialNetWorth;

	public SpendingProfile(double dBudget, double dInflation, LocalDate dateStart, LocalDate dateKinkPoint,
			double dIncrease) {
		super();
		this.dBudget = dBudget;
		this.dInflation = dInflation;
		this.dateStart = dateStart;
		this.dateKinkPoint = dateKinkPoint;
		this.dIncrease = dIncrease;
	}
// overloaded for incremental spend down to 0.0 after term
	public SpendingProfile(double NetWorth, double dInflation, LocalDate dateStart, LocalDate dateEnd) {
		super();
		this.dInflation = dInflation;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.dInitialNetWorth = NetWorth;
		this.dBudget = this.dInitialNetWorth / (this.dateEnd.getYear() - this.dateStart.getYear());
		this.dateKinkPoint = LocalDate.of(2100, 12, 31);
	}

	public double getBudget(LocalDate date) {
		double dFactor = getFactor(date);
		double dNewBudget = dBudget * dFactor;
		double dInflatedIncrease = 0.0;
		if (date.isBefore(dateKinkPoint)) {
			dInflatedIncrease = dIncrease * dFactor;

		}
		return (dNewBudget + dInflatedIncrease);

	}
	public double getBudget(LocalDate date,double dTotalNetErnings) {
		double dFactor = getFactor(date);
		double dNewBudget = dBudget * dFactor;
		return (dNewBudget + dTotalNetErnings);
	}

	private double getFactor(LocalDate date) {
		int iNumYears = date.getYear() - dateStart.getYear();
		double dFactor = Math.pow((1 + dInflation), iNumYears);
		return dFactor;
	}

	public double getInflation() {
		return this.dInflation;
	}
}
