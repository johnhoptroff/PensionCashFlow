package com.retirement;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

class TestWithdrawFromLowInterest {
	private final Account acc1 = new Account(50000.0,0.06);
	private final Account acc2 = new Account(50000.0,0.03);
	private final Account acc3 = new Account(50000.0,0.04);
	private final Account acc4 = new Account(49000.0,0.01);
	private final Account acc5 = new Account(50000.0,0.05);
	List<Account> Accounts = new ArrayList<>();


	@Test
	void test() {
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
