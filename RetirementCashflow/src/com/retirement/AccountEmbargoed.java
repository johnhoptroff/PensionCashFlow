package com.retirement;

import java.time.LocalDate;

public class AccountEmbargoed extends AccountAbstract {
	@Override
	public double deposit(double dMoney, LocalDate dateIn) {
		if(dateIn.isAfter(dtEmbargoEnds)) {
			return super.deposit(dMoney, dateIn);
		}else {
			return dMoney;
		}
		
	}

	private LocalDate dtEmbargoEnds;
	
	public AccountEmbargoed(String strName, double dOpenBal, double dRate, LocalDate dtEmbargoEnds) {
		super(strName, dOpenBal, dRate);
		super.setTaxInterest(false);
		super.setEarnings(true);
		this.setDtEmbargoEnds(dtEmbargoEnds);
	}

	public LocalDate getDtEmbargoEnds() {
		return dtEmbargoEnds;
	}

	public void setDtEmbargoEnds(LocalDate dtEmbargoEnds) {
		this.dtEmbargoEnds = dtEmbargoEnds;
	}

}
