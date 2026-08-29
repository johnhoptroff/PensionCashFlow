package com.retirement;

public class TaxedAccount extends AccountAbstract {

	
	public TaxedAccount(String strName, double dOpenBal, double dRate) {
		super(strName, dOpenBal, dRate);
		// basic account with no maximum balance, no embargo on withdrawal taxes interest as per return and does not count as income.
		super.setTaxInterest(true);
		super.setEarnings(false);
	}

	public Double getInterest() {
		return super.getdBalance()*super.getdRate();
	}

}
