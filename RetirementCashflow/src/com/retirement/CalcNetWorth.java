package com.retirement;



import java.util.List;

public class CalcNetWorth {

	private static double dNetWorth;

	public static double calcTotalWorth(List<Account> accounts) {
		dNetWorth = 0.0;
		accounts.forEach((account) -> {
			dNetWorth = dNetWorth + account.getdBalance();
		});
		return dNetWorth;
	}
}
