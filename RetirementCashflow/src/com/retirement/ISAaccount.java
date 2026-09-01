package com.retirement;

import java.time.LocalDate;

public class ISAaccount extends AccountAbstract {
	private double dISALimit;
	private double dISAUnused;
	private LocalDate datTxYear;
	public ISAaccount(String strName, double dOpenBal, double dRate, double dISALimit) {
		super(strName, dOpenBal, dRate);
		// this account does not tax interest or withdrawal and has maximum deposit of
		// £20,000 per year
		this.dISALimit = dISALimit;
		super.setTaxInterest(false);
		super.setEarnings(false);
		this.datTxYear = LocalDate.of(1900, 1, 1);
	}


	@Override
	public double deposit(double dMoney, LocalDate dateIn) {
		if(!dateIn.isEqual(datTxYear)) {
			dISAUnused = dISALimit;
		}
		
		if(dISAUnused >= dMoney){
			dISAUnused -= dMoney;
			return super.deposit(dMoney, dateIn);
		}else { // trying to put too much into the ISA
			super.deposit(dISAUnused, dateIn);
			dISAUnused = 0.0;
			return (dMoney - dISAUnused);
		}

	}
	

}
