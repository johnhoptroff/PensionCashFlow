package com.retirement;

public class Account implements Comparable<Account>{
	private double dBalance;
	private double dOpenBal;
	private double dRate;

	public Account(double dOpenBal, double dRate) {
		this.dBalance = dOpenBal;
		this.setdRate(dRate);
	}

	public void deposit(double dMoney) {
		this.dBalance = this.dBalance + dMoney;
	}
	public void withdraw(double dMoney) {
		this.dBalance = this.dBalance - dMoney;
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

	@Override
	public int compareTo(Account accOther) {
		// TODO Auto-generated method stub
		return Double.compare(getdRate(), accOther.getdRate());
	}

}
