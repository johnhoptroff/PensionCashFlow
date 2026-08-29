package com.retirement;

import java.time.LocalDate;

public class AccOnBond extends BondAccount {

	public AccOnBond(String strName, double dOpenBal, double dRate, LocalDate dtOpened) {
		super(strName, dOpenBal, dRate, dtOpened);
		//Only the type of this account is used to set logic in Person
	}

}
