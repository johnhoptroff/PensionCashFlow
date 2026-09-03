package com.retirement;

import java.time.LocalDate;

public class ISAaccount extends AccountAbstract {
	private double dISALimit;
	private double dISAUnused;
	private LocalDate datTxYear;
	public ISAaccount(String strName, double dOpenBal, LocalDate dateOpened, double dRate, double dISALimit) {
		super(strName, dOpenBal, dRate);
		// this account does not tax interest or withdrawal and has maximum deposit of
		// £20,000 per year
		this.dISALimit = dISALimit;
		super.setTaxInterest(false);
		super.setEarnings(false);
		this.datTxYear = dateOpened;
		this.dISAUnused = dISALimit; 
	}


	@Override
	public double deposit(double dMoney, LocalDate dateIn) {
		if(!dateIn.isEqual(datTxYear)) {
			dISAUnused = dISALimit; //depositing in a different tax year
			//TODO make this a true tax year overlap, works for now in test
		}
		
		if(dISAUnused >= dMoney){
			dISAUnused -= dMoney;
			return super.deposit(dMoney, dateIn);
		}else { // trying to put too much into the ISA
			if(dISAUnused != 0.0) super.deposit(dISAUnused, dateIn);
			double dVal = (dMoney - dISAUnused);
			dISAUnused = 0.0;
			return dVal;
		}

	}


	public void setTaxYear(LocalDate date) {
		this.datTxYear = date;
		dISAUnused = dISALimit;
	}
	

}
