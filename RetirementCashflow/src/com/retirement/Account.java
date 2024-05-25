package com.retirement;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.retirement.fileutils.FileIO;

public class Account implements Comparable<Account>{
	private double dBalance;
	private double dOpenBal;
	private LocalDate dateClosed;
	private double dRate;
	private String strName;
	private boolean boolTaxable;
	private boolean boolLimitBalance;
	private List<Transaction> transactions = new ArrayList<>();
	private Person persHolder;
	private Account accPayInterest;
	
	public Account(String strName, double dOpenBal, double dRate, boolean boolTaxable) {
		this.dBalance = dOpenBal;
		this.dOpenBal = dOpenBal;
		this.strName = strName;
		this.boolTaxable = boolTaxable;
		this.setdRate(dRate);
	}

	public void deposit(double dMoney,LocalDate dateIn) {
		this.dBalance = this.dBalance + dMoney;
		transactions.add(new Transaction("deposit",dMoney,dateIn,dBalance));
	}

	public void withdraw(double dMoney,LocalDate dateOut) {
		this.dBalance = this.dBalance - dMoney;
		transactions.add(new Transaction("withdrawal",(dMoney*-1),dateOut,dBalance));
	}
	public void addInterest(LocalDate date) {
		double dMoney = this.dBalance * this.dRate;
		if(this.boolLimitBalance) {
			accPayInterest.deposit(dMoney, date);
		}else {
			this.dBalance = this.dBalance + dMoney;
			transactions.add(new Transaction("interest",dMoney,date,dBalance));		
		}

	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Account [Holder:" + persHolder.getStrName() + " balance:" + dBalance + ", dOpenBal=" + dOpenBal + ", dRate=" + dRate + ", strName=" + strName +"]") ;
		buffer.append("\nTransactions:");
		
		transactions.forEach((transaction) -> {
			buffer.append("\n" + transaction.toString());
		});
		
		return buffer.toString();
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
	public void setIsBalanceLimited(boolean boolBalLimited) {
		this.boolLimitBalance = boolBalLimited;
	}
	public void setPayAccount(Account acc) {
		this.accPayInterest = acc;
	}
	public LocalDate getdClosedDate() {
		return dateClosed;
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

	public void close(LocalDate date) {
		// produce final statement and store on file
    	File opFile = new File(this.strName + "finalstatement.txt");
		System.out.println(this.strName + " Account closed");
		StringBuffer buffer = new StringBuffer(this.toString());
		this.dateClosed = date;
		try {
			FileIO.writeFile(opFile,buffer);
			System.out.println("final statement file:" + opFile.getPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public boolean getisTaxable() {
		return this.boolTaxable;
	}

}
