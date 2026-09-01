package com.retirement;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;

class TestDepositToISAs {
	// accounts setup as of 1/1/2025
	double dInflation = 0.0;

	List<AccountAbstract> accountsJohn = new ArrayList<>();
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
	private final double dMaxPremBal = 50000.0;

	TaxParams txParams = new TaxParams(dbTaxlow, dbTaxhigh, dbTaxlowpc, dbTaxhighpc, dbISAlimit, dtFrozenTh);
	NIParams niParams = new NIParams(dbNIhighpc, dbNIlowpc, dbNIhighwk, dbNIlowwk);
	SuplimentalTaxParams supParams = new SuplimentalTaxParams(dbCGTLimit, dbLowTaxDiviLimit, dbHighTaxDiviLimit,
			dLowTaxCGTrate, dHighTaxGCTrate, dSRSB, dPSAlow, dPSAhigh, dRentAllowance, dDiviAllowance, dDiviRateLow,
			dDiviRateHigh);
	
	private final PensionStream streamWorkPen1John = new PensionStream("*RRPension 1", LocalDate.of(2026, 4, 5),LocalDate.of(2100, 1, 1), 10000.0, 0.0);

	private final PensionAccount accSJPJohn = new PensionAccount("SJP  John", 115000.0, 0.0);
	private final PremBondsAccount accBondsJohn = new PremBondsAccount("Bonds John", 50000.0, 0.0,dMaxPremBal);
	private final AccountShares accRRSharesJohn = new AccountShares("R-R+shares J", 12500.0, 0.0, 6311.0, 995, 0.06);
	private final TaxedAccount accFordJohn = new TaxedAccount("Ford John", 31000.0, 0.0);
	private final ISAaccount accISAJohn = new ISAaccount("ISAsJohn", 105000.0, 0.0,txParams.getISAlimit());
	private final AccOffBond accBondOffJohn = new AccOffBond("OffshoreJohn", 85000.00, 0.0, LocalDate.of(2014, 5, 14));
	private final AccountEmbargoed accBungaJohn = new AccountEmbargoed("Bunga John", 120000.0, 0.0,LocalDate.of(2035, 11, 14));
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
	
	
	@Test
	void test() {
		// test net worth for 9 years

		StreamsJohn.add(streamWorkPen1John);


		accountsJohn.add(accSJPJohn);
		accountsJohn.add(accBondsJohn);
		accountsJohn.add(accFordJohn);
		accountsJohn.add(accRRSharesJohn);
		accountsJohn.add(accISAJohn);
		accountsJohn.add(accBondOffJohn);
		accountsJohn.add(accBungaJohn);

		
		PensionAccount pen = new PensionAccount("nopension", 0.0, 0.0);
		Person persJohn = new Person("John Hoptroff", LocalDate.of(1968, 4, 23), StreamsJohn, accountsJohn, pen, 0.0,
				0.0);

		List<Person> People = new ArrayList<>();

		People.add(persJohn);



		double dBudget = 5000.0;

		CashFlow cashFlow = new CashFlow(People, dBudget, dInflation, LocalDate.of(2027, 4, 5), txParams, niParams,
				supParams);
		System.out.println("starting test...");
		try {
			assertEquals(-5307.13, cashFlow.getResidual(LocalDate.of(2034, 12, 31)), 0.1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
