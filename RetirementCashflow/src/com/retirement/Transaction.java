package com.retirement;

import java.text.NumberFormat;
import java.time.LocalDate;

public class Transaction {
private double dMoney;
private LocalDate dateTrans;
private double dBalance;
private String strName;

public Transaction(String strName, double dMoney, LocalDate dateTrans, double dBalance) {
		this.strName = strName;
		this.dMoney = dMoney;
		this.dateTrans = dateTrans;
		this.dBalance = dBalance;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		//buffer.append("Transaction [" + strName + " Amount=");
		//buffer.append(NumberFormat.getCurrencyInstance().format(dMoney) + ", Date=" + dateTrans);
		//buffer.append(", Balance=" + NumberFormat.getCurrencyInstance().format(dBalance) + "]");
        buffer.append(strName + "\t"+ dateTrans +"\t" + dBalance);
		return buffer.toString();	
	}
}
