package com.retirement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Account implements Comparable<Account>{
	private double dBalance;
	private double dOpenBal;
	private double dRate;
	private String strName;
	private boolean boolTaxable;
	private List<Transaction> transactions = new ArrayList<>();
	private Person persHolder;
	
	public Account(String strName, double dOpenBal, double dRate, boolean boolTaxable) {
		this.dBalance = dOpenBal;
		this.dOpenBal = dOpenBal;
		this.strName = strName;
		this.boolTaxable = boolTaxable;
		this.setdRate(dRate);
	}

	public void deposit(double dMoney,LocalDate dateIn) {
		this.dBalance = this.dBalance + dMoney;
		transactions.add(new Transaction(dMoney,dateIn,dBalance));
	}

	public void withdraw(double dMoney,LocalDate dateOut) {
		this.dBalance = this.dBalance - dMoney;
		transactions.add(new Transaction((dMoney*-1),dateOut,dBalance));
	}


	@Override
	public String toString() {
		return "Account [Holder:" + persHolder.getStrName() + "balance:" + dBalance + ", dOpenBal=" + dOpenBal + ", dRate=" + dRate + ", strName=" + strName
				+ ", transactions=" + transactions + "]";
	}

	public void addInterest() {
		this.dBalance = this.dBalance * (1 + this.dRate);
	}
	public double getdOpenBal() {
		return dOpenBal;
	}

	public void setdOpenBal(double dOpenBal) {
		this.dOpenBal = dOpenBal;
	}

	public double getdRate() {
		return dRate;
	}

	public void setdRate(double dRate) {
		this.dRate = dRate;
	}

	public double getdBalance() {
		return dBalance;
	}
	public boolean isTaxable() {
		return boolTaxable;
	}

	@Override
	public int compareTo(Account accOther) {
		return Double.compare(getdRate(), accOther.getdRate());
	}

	public String getName() {
		return strName;
	}

	public void setHolder(Person person) {
		this.persHolder = person;
		
	}

	public Person getHolder() {
		return persHolder;
	}

}
