package com.retirement;

import java.time.LocalDate;
import java.util.List;

public class Person {
	private String strName;
	private LocalDate dateBDay;
	private Double dTaxableIncome = 0.0;
	private Double dNIableIncome = 0.0;
	private List<IncomeStream> streams;
	private List<Account> accounts;
	private Account accPensionPot;

	public Person(String strName, LocalDate dateBDay, List<IncomeStream> streams, List<Account> accounts) {
		this.strName = strName;
		this.dateBDay = dateBDay;
		this.streams = streams;
		this.accounts = accounts;

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

	public void setPensionPot(Account accPensionPot) {
		this.accPensionPot = accPensionPot;

	}

	public List<Account> getAccounts() {
		return this.accounts;

	}

	public Account getPensionAccount() {
		return this.accPensionPot;
	}

	public List<IncomeStream> getStreams() {
		return this.streams;

	}

	@Override
	public String toString() {
		return "Person [strName=" + strName + ", dateBDay=" + dateBDay + ", dTaxableIncome=" + dTaxableIncome
				+ ", dNIableIncome=" + dNIableIncome + "]";
	}
}
