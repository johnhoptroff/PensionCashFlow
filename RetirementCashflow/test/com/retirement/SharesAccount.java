package com.retirement;

public class SharesAccount extends AccountAbstract {

	public SharesAccount(String strName, double dOpenBal, double dRate) {
		super(strName, dOpenBal, dRate);
		super.setTaxInterest(false);
		super.setEarnings(false);
	}

}
