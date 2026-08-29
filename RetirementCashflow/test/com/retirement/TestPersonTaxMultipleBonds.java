package com.retirement;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;

class TestPersonTaxMultipleBonds {
	// accounts setup as of 1/1/2025
	double dInflation = 0.036;

	List<AccountAbstract> accountsLynne = new ArrayList<>();
	List<AccountAbstract> accountsJohn = new ArrayList<>();

	private final PensionStream streamPension = new PensionStream("Pension", LocalDate.of(2022, 5, 1),
			LocalDate.of(2100, 1, 1), 10000.0, 0.026);

	private final PremBondsAccount accBondOff = new PremBondsAccount("Bonds Offshore", 50000.0, 0.044);
	private final PremBondsAccount accBondOn = new PremBondsAccount("Bonds Onshore", 50000.0, 0.044);
	private final TaxedAccount accFordJohn = new TaxedAccount("Ford John", 40000.0, 0.1);
	private final AccountShares accRRSharesJohn = new AccountShares("R-R+shares", 10000.0, 0.02, 6311.0, 995, 1.0);

	List<StreamAbstract> StreamsJohn = new ArrayList<>();

	private final double dbTaxlow = 12570.0;
	private final double dbTaxhigh = 50270.0;
	private final double dbTaxlowpc = 0.20;
	private final double dbTaxhighpc = 0.40;

	private final double dbNIhighpc = 0.02;
	private final double dbNIlowpc = 0.12;
	private final double dbNIhighwk = 967.0;
	private final double dbNIlowwk = 242.0;
	private final double dbISAlimit = 20000.0;
	private final LocalDate dtFrozenTh = LocalDate.of(2031, 04, 05);

	private final double dbCGTLimit = 3000.0;
	private final double dbLowTaxDiviLimit = 500.0;
	private final double dbHighTaxDiviLimit = 0.0;
	private final double dLowTaxCGTrate = 0.18;
	private final double dHighTaxGCTrate = 0.24;
	private final double dSRSB = 5000.0;
	private final double dPSAlow = 1000.0;
	private final double dPSAhigh = 500.0;
	private final double dRentAllowance = 1000.0;
	private final double dDiviAllowance = 500.0;
	private final double dDiviRateLow = 0.0875;
	private final double dDiviRateHigh = 0.033;

	@Test
	void test() {
		// test set-up is as per Canada life example 1

		StreamsJohn.add(streamPension);

		accountsJohn.add(accBondOn);
		accountsJohn.add(accBondOff);
		accountsJohn.add(accFordJohn);
		accountsJohn.add(accRRSharesJohn);

		PensionAccount pen = new PensionAccount("nopension", 0.0, 0.0);
		Person persJohn = new Person("John Hoptroff", LocalDate.of(1968, 4, 23), StreamsJohn, accountsJohn, pen, 0.0,
				0.0);

		List<Person> People = new ArrayList<>();

		People.add(persJohn);

		TaxParams txParams = new TaxParams(dbTaxlow, dbTaxhigh, dbTaxlowpc, dbTaxhighpc, dbISAlimit, dtFrozenTh);
		NIParams niParams = new NIParams(dbNIhighpc, dbNIlowpc, dbNIhighwk, dbNIlowwk);
		SuplimentalTaxParams supParams = new SuplimentalTaxParams(dbCGTLimit, dbLowTaxDiviLimit, dbHighTaxDiviLimit,
				dLowTaxCGTrate, dHighTaxGCTrate, dSRSB, dPSAlow, dPSAhigh, dRentAllowance, dDiviAllowance, dDiviRateLow,
				dDiviRateHigh);

		double dBudget = 84750.0;

		CashFlow cashFlow = new CashFlow(People, dBudget, dInflation, LocalDate.of(2027, 1, 1), txParams, niParams,
				supParams);
		System.out.println("starting test...");
		try {
			assertEquals(-5307.13, cashFlow.getResidual(LocalDate.of(2058, 12, 31)), 0.1);
			// cashFlow.runMaxEarnings(LocalDate.of(2059,12,31));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// assertEquals(5507.58, cashFlow.getResidual(40),0.1);
	}

}
