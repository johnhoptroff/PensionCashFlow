package com.retirement;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.retirement.fileutils.FileIO;

public abstract class AccountAbstract implements Comparable<AccountAbstract>{
	private double dBalance;
	private double dOpenBal;
	private LocalDate dateClosed;
	private double dRate;
	private String strName;
	private boolean boolEarnings;
	private boolean boolTaxInterest;
	private boolean boolLimitBalance;
	private boolean isActive = true;
	private List<Transaction> transactions = new ArrayList<>();
	private Person persHolder;
	private AccountAbstract accPayInterest;
	
	public AccountAbstract(String strName, double dOpenBal, double dRate) {
		this.dBalance = dOpenBal;
		this.dOpenBal = dOpenBal;
		this.strName = strName;
		this.setdRate(dRate);
	}

	public double deposit(double dMoney,LocalDate dateIn) {
		this.dBalance = this.dBalance + dMoney;
		transactions.add(new Transaction("deposit",dMoney,dateIn,dBalance));
		return 0.0;
	}

	public double withdraw(double dMoney,LocalDate dateOut) {
		double dChange = 0.0;
		if(this.dBalance >= dMoney) {
			this.dBalance = this.dBalance - dMoney;
			transactions.add(new Transaction("withdrawal",(dMoney*-1),dateOut,dBalance));

		}else {
			dChange = dMoney - this.dBalance;
			this.dBalance = 0.0;
		}

		return dChange;
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
		if(this.isActive) {
			return dBalance;
		}else {
			return 0.0;
		}
	}
	public boolean isEarnings() {
		return boolEarnings;
	}
	public boolean isTaxInterest() {
		return boolTaxInterest;
	}
	public void setIsBalanceLimited(boolean boolBalLimited) {
		this.boolLimitBalance = boolBalLimited;
	}
	public void setPayAccount(AccountAbstract acc) {
		this.accPayInterest = acc;
	}
	public LocalDate getdClosedDate() {
		return dateClosed;
	}

	@Override
	public int compareTo(AccountAbstract accOther) {
		int result = Double.compare(getdRate(), accOther.getdRate());
		if(this instanceof ISAaccount && !(accOther instanceof ISAaccount)) {
			return 1;
		}
		if(this instanceof ISAaccount && (accOther instanceof BondAccount)) {
			return 1;
		}
		if(this instanceof AccountShares && !(accOther instanceof ISAaccount)) {
			return 1;
		}
		if(this instanceof TaxedAccount && !(accOther instanceof TaxedAccount)) {
			return -1;
		}
		if(this instanceof PremBondsAccount && (accOther instanceof TaxedAccount)) {
			return 1;
		}
		if (this instanceof TaxedAccount && accOther instanceof TaxedAccount) {
			return Double.compare(accOther.getdBalance(),getdBalance());
		}
		return result;
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
	public double getdBalanceAtDate(LocalDate date) {
		double dBal=0;
		for (int i = 0; i < transactions.size(); i++) {
			if(date.isAfter(transactions.get(i).getDate()) && date.isBefore(transactions.get(i+1).getDate())){
				dBal=transactions.get(i).getBalance();
			}
		}

		return dBal;
	}
	public void setTaxInterest(boolean b) {
		this.boolTaxInterest = b;
		
	}

	public void setEarnings(boolean b) {
		this.boolEarnings = b;
		
	}


	public Object getTransactions() {
		return this.transactions;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
