package com.retirement;

import java.time.LocalDate;

public class Transaction {
private double dMoney;
private LocalDate dateTrans;
private double dBalance;

	public Transaction(double dMoney, LocalDate dateTrans, double dBalance) {
		this.dMoney = dMoney;
		this.dateTrans = dateTrans;
		this.dBalance = dBalance;
	}

	@Override
	public String toString() {
		return "Transaction [dMoney=" + dMoney + ", dateTrans=" + dateTrans + ", dBalance=" + dBalance + "]";
	}

}
