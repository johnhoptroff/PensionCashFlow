package com.retirement;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;

class TestTotalEarningSimple {
	// accounts setup as of 1/1/2025
	double dInflation = 0.00;

	List<AccountAbstract> accountsLynne = new ArrayList<>();
	List<AccountAbstract> accountsJohn = new ArrayList<>();
	List<StreamAbstract> StreamsJohn = new ArrayList<>();
	List<StreamAbstract> StreamsLynne = new ArrayList<>();
	
	private final PensionStream streamWorkPen1John = new PensionStream("*RRPension 1", LocalDate.of(2027, 1, 1),LocalDate.of(2100, 1, 1), 0.0, 0.03);

	private final TaxedAccount accFordJohn = new TaxedAccount("Ford John", 10000, 0.0);


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
		// test total earnings to age 90


		StreamsJohn.add(streamWorkPen1John);


		accountsJohn.add(accFordJohn);

		
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

		double dBudget = 312.5;

		CashFlow cashFlow = new CashFlow(People, dBudget, dInflation, LocalDate.of(2027, 1, 1), txParams, niParams,
				supParams);
		System.out.println("starting test...");
		try {
			assertEquals(0.0, cashFlow.getResidual(LocalDate.of(2058, 12, 31)), 0.1);
			// cashFlow.runMaxEarnings(LocalDate.of(2059,12,31));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// assertEquals(5507.58, cashFlow.getResidual(40),0.1);
	}

}
