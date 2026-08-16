package com.retirement;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

class TestWithdrawFromLowInterest {
	private final Account acc1 = new Account("acc1",50000.0,0.06,false);
	private final Account acc2 = new Account("acc2",50000.0,0.03,false);
	private final Account acc3 = new Account("acc3",50000.0,0.04,false);
	private final Account acc4 = new Account("acc4",49000.0,0.01,false);
	private final Account acc5 = new Account("acc5",50000.0,0.05,false);
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
