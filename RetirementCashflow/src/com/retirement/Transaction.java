package com.retirement;

import java.text.NumberFormat;
import java.time.LocalDate;

public class Transaction {
private double dAmnt;
private LocalDate dateTrans;
private double dBalance;
private String strName;

public Transaction(String strName, double dAmnt, LocalDate dateTrans, double dBalance) {
		this.strName = strName;
		this.dAmnt = dAmnt;
		this.dateTrans = dateTrans;
		this.dBalance = dBalance;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		//buffer.append("Transaction [" + strName + " Amount=");
		//buffer.append(NumberFormat.getCurrencyInstance().format(dMoney) + ", Date=" + dateTrans);
		//buffer.append(", Balance=" + NumberFormat.getCurrencyInstance().format(dBalance) + "]");
        buffer.append(strName + "\t"+ dateTrans +"\t" + NumberFormat.getCurrencyInstance().format(dAmnt) +"\t" + NumberFormat.getCurrencyInstance().format(dBalance));
		return buffer.toString();	
	}

	public LocalDate getDate() {
		return this.dateTrans;
	}

	public double getBalance() {
		return this.dBalance;
	}
	public double getAmnt() {
		return this.dAmnt;
	}

}
