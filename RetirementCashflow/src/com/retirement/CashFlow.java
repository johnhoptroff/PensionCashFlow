package com.retirement;

import java.time.LocalDate;
import java.util.List;

public class CashFlow {
private List<Account> accounts;
private List<IncomeStream> streams;
private double dBudget;
private double dInflation;
private LocalDate dateStart;
private int iTerm;

	public CashFlow(List<Account> accounts, List<IncomeStream> streams, double dBudget, double dInflation, LocalDate dateStart) {
		// TODO Auto-generated constructor stub
	}

	public double getResidual(int iTerm) {
		this.iTerm = iTerm;
		return 0;
	}

	public double getFundingGap() {
		// TODO Auto-generated method stub
		return 0;
	}


}
