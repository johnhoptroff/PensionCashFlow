package com.retirement;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

class TestWithdrawFromAppropriate {
	private final TaxedAccount acc1 = new TaxedAccount("acc1",50000.0,0.06);
	private final TaxedAccount acc2 = new TaxedAccount("acc2",50000.0,0.03);
	private final TaxedAccount acc3 = new TaxedAccount("acc3",50000.0,0.04);
	private final ISAaccount acc4 = new ISAaccount("acc4",49000.0,0.01);
	private final TaxedAccount acc5 = new TaxedAccount("acc5",50000.0,0.05);
	List<AccountAbstract> Accounts = new ArrayList<>();


	@Test
	void testLowestInterest() {
		Accounts.add(acc1);
		Accounts.add(acc2);
		Accounts.add(acc3);
		Accounts.add(acc4);
		Accounts.add(acc5);
		Collections.sort(Accounts);
		Accounts.get(0).withdraw(1000.0,LocalDate.of(2024, 1, 1));
		assertEquals(48000.0, Accounts.get(0).getdBalance(),0.1);
	}

}
