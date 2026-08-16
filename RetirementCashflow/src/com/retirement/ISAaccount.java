package com.retirement;

public class ISAaccount extends AccountAbstract {

	public ISAaccount(String strName, double dOpenBal, double dRate) {
		super(strName, dOpenBal, dRate);
		// TODO Auto-generated constructor stub
		// this account does not tax interest or withdrawal and has maximum deposit of £20,000 per year
	}

}
