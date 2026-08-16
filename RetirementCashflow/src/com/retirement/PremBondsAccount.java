package com.retirement;

public class PremBondsAccount extends AccountAbstract {

	public PremBondsAccount(String strName, double dOpenBal, double dRate) {
		// maximum balance of £50,000 - must be dealt with elsewhere
		super(strName, dOpenBal, dRate);
		super.setTaxInterest(false);
		super.setEarnings(false);
		super.setMaximumBalance(50000.0);
	}

}
