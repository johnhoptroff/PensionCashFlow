package com.retirement;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;

public class Person {
	private String strName;
	private LocalDate dateBDay;
	private Double dTaxableIncome = 0.0;
	private Double dNIableIncome = 0.0;
	private Double dTotalIncome = 0.0;
	private Double dPensionAmnt = 0.0;
	private Double dEmployerPenAmnt = 0.0;
	private List<IncomeStream> streams;
	private List<Account> accounts;
	private Account accPensionPot;

	public Person(String strName, LocalDate dateBDay, List<IncomeStream> streams, List<Account> accounts,
			Account accPensionPot, double dEmpContribution, double dPensionAmnt) {
		this.strName = strName;
		this.dateBDay = dateBDay;
		this.streams = streams;
		this.accounts = accounts;
		this.accPensionPot = accPensionPot;
		this.dEmployerPenAmnt = dEmpContribution;
		this.dPensionAmnt = dPensionAmnt;

	}

	public String getStrName() {
		return strName;
	}

	public LocalDate getDateBDay() {
		return dateBDay;
	}

	public Double getTaxableIncome() {
		return dTaxableIncome;
	}

	public void setTaxableIncome(Double dTaxableIncome) {
		this.dTaxableIncome = dTaxableIncome;
	}

	public Double getNIableIncome() {
		return dNIableIncome;
	}

	public void setNIableIncome(Double dNIableIncome) {
		this.dNIableIncome = dNIableIncome;
	}

	public List<Account> getAccounts() {
		return this.accounts;

	}

	public Double getdTotalIncome() {
		return dTotalIncome;
	}

	public void setdTotalIncome(Double dTotalIncome) {
		this.dTotalIncome = dTotalIncome;
	}

	public Account getPensionAccount() {
		return this.accPensionPot;
	}

	public List<IncomeStream> getStreams() {
		return this.streams;

	}

	@Override
	public String toString() {
		StringBuffer sbOutput = new StringBuffer("----------------------------------\n");
		sbOutput.append(strName + ", Birthday=" + dateBDay + ", TaxableIncome="
				+ NumberFormat.getCurrencyInstance().format(dTaxableIncome));
		sbOutput.append(", NIableIncome=" + NumberFormat.getCurrencyInstance().format(dNIableIncome));
		sbOutput.append(", TotalIncome=" + NumberFormat.getCurrencyInstance().format(dTotalIncome));
		sbOutput.append(", AVC account=" + accPensionPot.getName());
		sbOutput.append("\nPension Contribution=" + NumberFormat.getCurrencyInstance().format(dPensionAmnt));
		sbOutput.append(", Employer Contribution=" + NumberFormat.getCurrencyInstance().format(dEmployerPenAmnt));
		sbOutput.append(("\n----------------------------------\n"));
		return sbOutput.toString();
	}

	public double getPensionAmnt() {
		return dPensionAmnt;
	}

	public double getEmployerAmnt() {
		return dEmployerPenAmnt;
	}

	public void setPensionAmnt(double dPensAmnt) {
		this.dPensionAmnt = dPensAmnt;
		
	}

	public void setEmployerPenAmnt(double dEmployerAmnt) {
		this.dEmployerPenAmnt = dEmployerAmnt;
		
	}

}
