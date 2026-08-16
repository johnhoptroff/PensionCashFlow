package com.retirement;

public class BondAccount extends AccountAbstract {

	public BondAccount(String strName, double dOpenBal, double dRate) {
		super(strName, dOpenBal, dRate);
		super.setTaxInterest(false);
		super.setEarnings(true);
	}

}
