package com.retirement;

public class PensionAccount extends AccountAbstract {

	public PensionAccount(String strName, double dOpenBal, double dRate) {
		super(strName, dOpenBal, dRate);
		
		super.setTaxInterest(false);
		super.setEarnings(true);
	}

}
