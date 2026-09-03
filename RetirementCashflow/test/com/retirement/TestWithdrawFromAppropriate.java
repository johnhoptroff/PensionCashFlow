package com.retirement;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

class TestWithdrawFromAppropriate {
	private final PensionAccount acc1 = new PensionAccount("pen",51000.0,0.06);
	private final AccountShares acc2 = new AccountShares("shares",25000.0,0.03, 50000, 5000, 0.0);
	private final TaxedAccount acc3 = new TaxedAccount("Taxed3",51000.0,0.04);
	private final AccOffBond acc4 = new AccOffBond("OffBond",49000.0,0.055,LocalDate.of(2015, 1, 1));
	private final AccOnBond acc5 = new AccOnBond("OnBond",51000.0,0.05,LocalDate.of(2015, 1, 1));
	private final ISAaccount acc6 = new ISAaccount("ISA",49000.0,LocalDate.of(2026, 4, 5),0.01,20000.0);
	private final PremBondsAccount acc7 = new PremBondsAccount("prems",51000.0,0.035,50000.0);
	private final TaxedAccount acc8 = new TaxedAccount("Taxed1",151000.0,0.04);
	private final TaxedAccount acc9 = new TaxedAccount("Taxed4",1000.0,0.04);
	private final TaxedAccount acc10 = new TaxedAccount("Taxed2",56000.0,0.04);
	List<AccountAbstract> Accounts = new ArrayList<>();


	@Test
	void testLowestInterestNotISA() {
		Accounts.add(acc1);
		Accounts.add(acc2);
		Accounts.add(acc3);
		Accounts.add(acc4);
		Accounts.add(acc5);
		Accounts.add(acc6);
		Accounts.add(acc7);
		Collections.sort(Accounts);
		Accounts.forEach(account -> {
			System.out.println(account.getName());
		});
		Accounts.get(0).withdraw(1000.0,LocalDate.of(2024, 1, 1));
		assertEquals(49000.0, Accounts.get(0).getdBalance(),0.1);
	}
	
	@Test
	void testSortOnBalance() {
		Accounts.add(acc8);
		Accounts.add(acc9);
		Accounts.add(acc3);
		Accounts.add(acc10);
		Collections.sort(Accounts);
		Accounts.forEach(account -> {
			System.out.println(account.getName());
		});
		Accounts.get(0).withdraw(1000.0,LocalDate.of(2024, 1, 1));
		assertEquals(49000.0, Accounts.get(0).getdBalance(),0.1);
	}

}
