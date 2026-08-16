package com.retirement;

public class TaxedAccount extends AccountAbstract {
	private boolean boolTaxWithdraw;
	private boolean boolTaxInterest;
	
	public TaxedAccount(String strName, double dOpenBal, double dRate) {
		super(strName, dOpenBal, dRate);
		// basic account with no maximum balance, no embargo on withdrawal taxes interest as per return and does not count as income.
		this.boolTaxInterest = true;
		this.boolTaxWithdraw = false;
	}

	public boolean isBoolTaxWithdraw() {
		return boolTaxWithdraw;
	}

	public boolean isBoolTaxInterest() {
		return boolTaxInterest;
	}


}
