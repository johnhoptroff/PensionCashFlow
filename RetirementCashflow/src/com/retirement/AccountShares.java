package com.retirement;

public class AccountShares extends AccountAbstract {

	private double dPool;
	private int iNumShares;

	public AccountShares(String strName, double dOpenBal, double dRate,double dPool, int iNumShares) {
		super(strName, dOpenBal, dRate);
		super.setTaxInterest(false);
		super.setEarnings(false);
		this.setdPool(dPool);
		this.setiNumShares(iNumShares);
	}

	public double getdPool() {
		return dPool;
	}

	public void setdPool(double dPool) {
		this.dPool = dPool;
	}

	public int getiNumShares() {
		return iNumShares;
	}

	public void setiNumShares(int iNumShares) {
		this.iNumShares = iNumShares;
	}

}
