package com.retirement;

import java.time.LocalDate;

public class AccOffBond extends BondAccount {

	public AccOffBond(String strName, double dOpenBal, double dRate, LocalDate dtOpened) {
		super(strName, dOpenBal, dRate, dtOpened);
		//Only the type of this account is used to set logic in Person
	}

}
