package com.retirement;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;

class TestTotalEarningsBuyHouse {
	// accounts setup as of 1/1/2025
	double dInflation = 0.036;
	private final PensionAccount accAvivaJohn = new PensionAccount("Aviva_John", 104250.0, 0.05);
	private final PensionAccount accPruLynne = new PensionAccount("Pru_Lynne", 22512.0, 0.05);
	private final ISAaccount accLumpJohn = new ISAaccount("Av_JH_notax", 34750.0, 0.05);
	private final ISAaccount accFordLynne = new ISAaccount("Pru_LH_notax", 7504.0, 0.05);

	// private final Account accPremBonds = new
	// Account("Premium_Bonds",100000.0,0.039,false,false);
	private final AccountShares accRRShares = new AccountShares("R-R+shares", 40000.0, 0.02, 6311.0, 995, 0.06);
	private final ISAaccount accISAs = new ISAaccount("ISAs", 263700.0, 0.04); // includes £100k tax free part of mum's
																				// bonds
	// private final Account accFordJohn = new
	// Account("Flex_accounts",219550.0,0.035,false,true);

	List<AccountAbstract> accountsLynne = new ArrayList<>();
	List<AccountAbstract> accountsJohn = new ArrayList<>();

	private final EmploymentStream streamSalJohn = new EmploymentStream("SalaryJohn", LocalDate.of(1986, 9, 30),LocalDate.of(2024, 6, 1), 41240.0, 0.02);
	private final RentalStream streamRentJohn = new RentalStream("RentJohn", LocalDate.of(2022, 5, 1),LocalDate.of(2100, 1, 1), 5700.0, dInflation);
	private final PensionStream streamRentLynne = new PensionStream("RentLynne", LocalDate.of(2022, 5, 1),LocalDate.of(2100, 1, 1), 5700.0, dInflation);
	private final PensionStream streamWorkPen1John = new PensionStream("*RRPension 1", LocalDate.of(2024, 6, 1),LocalDate.of(2100, 1, 1), 11505.17, 0.02);
	private final PensionStream streamWorkPen3John = new PensionStream("*RRPension 3", LocalDate.of(2024, 6, 1),LocalDate.of(2100, 1, 1), 6173.16, dInflation);
	private final PensionStream streamWorkPen2John = new PensionStream("*RRPension 2", LocalDate.of(2024, 6, 1),LocalDate.of(2100, 1, 1), 4988.78, 0.025);
	private final PensionStream streamWorkPen4John = new PensionStream("*RRPension 4", LocalDate.of(2024, 6, 1),LocalDate.of(2035, 4, 23), 10350.0, 0.025);
	private final PensionStream streamStatePenJohn = new PensionStream("StateJohn", LocalDate.of(2035, 4, 23),LocalDate.of(2100, 1, 1), 12014.0, dInflation);
	
	
	private final EmploymentStream streamSalLynne = new EmploymentStream("SalaryLynne", LocalDate.of(1986, 6, 9),LocalDate.of(2026, 4, 4), 39000.0, (dInflation - 0.01));
	private final PensionStream streamLGPSPenLynne = new PensionStream("LGPSPension", LocalDate.of(2031, 4, 17),LocalDate.of(2100, 1, 1), 10283.15, dInflation);
	private final PensionStream streamBankPenLynne = new PensionStream("* BankPen  *", LocalDate.of(2028, 11, 14),LocalDate.of(2100, 1, 1), 6911.16, dInflation);
	private final PensionStream streamStatePenLynne = new PensionStream("StateLynne", LocalDate.of(2035, 11, 14),LocalDate.of(2100, 1, 1), 12014.0, dInflation);


	List<StreamAbstract> StreamsJohn = new ArrayList<>();
	List<StreamAbstract> StreamsLynne = new ArrayList<>();

	private final double dbTaxlow = 12500.0;
	private final double dbTaxhigh = 50000.0;
	private final double dbTaxlowpc = 0.20;
	private final double dbTaxhighpc = 0.40;

	private final double dbNIhighpc = 0.02;
	private final double dbNIlowpc = 0.12;
	private final double dbNIhighwk = 967.0;
	private final double dbNIlowwk = 242.0;
	private final double dbISAlimit = 20000.0;

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
		// test total earnings after 40 years

		StreamsLynne.add(streamSalLynne);
		StreamsLynne.add(streamRentLynne);
		StreamsLynne.add(streamLGPSPenLynne);
		StreamsLynne.add(streamBankPenLynne);
		StreamsLynne.add(streamStatePenLynne);

		StreamsJohn.add(streamSalJohn);
		StreamsJohn.add(streamRentJohn);
		StreamsJohn.add(streamWorkPen1John);
		StreamsJohn.add(streamWorkPen2John);
		StreamsJohn.add(streamWorkPen3John);
		StreamsJohn.add(streamWorkPen4John);
		StreamsJohn.add(streamStatePenJohn);

		// accPremBonds.setMaximumBalance(100000.0);
		// accPremBonds.setPayAccount(accISAs);
		accountsJohn.add(accAvivaJohn);
		// accountsJohn.add(accTransPenJohn);
		// accountsJohn.add(accFordJohn);
		accountsJohn.add(accRRShares);
		accountsJohn.add(accLumpJohn);

		// accountsLynne.add(accPremBonds);
		accountsLynne.add(accISAs);
		accountsLynne.add(accPruLynne);
		accountsLynne.add(accFordLynne);

		Person persJohn = new Person("John Hoptroff", LocalDate.of(1968, 4, 23), StreamsJohn, accountsJohn,
				accAvivaJohn, 16651.0, 4948.80);
		Person persLynne = new Person("Lynne Hoptroff", LocalDate.of(1968, 11, 14), StreamsLynne, accountsLynne,
				accPruLynne, 10800.0, 0.0);

		List<Person> People = new ArrayList<>();

		People.add(persLynne);
		People.add(persJohn);

		TaxParams txParams = new TaxParams(dbTaxlow, dbTaxhigh, dbTaxlowpc, dbTaxhighpc, dbISAlimit,
				LocalDate.of(2031, 4, 5));
		NIParams niParams = new NIParams(dbNIhighpc, dbNIlowpc, dbNIhighwk, dbNIlowwk);
		SuplimentalTaxParams supParams = new SuplimentalTaxParams(dbCGTLimit, dbLowTaxDiviLimit, dbHighTaxDiviLimit,
				dLowTaxCGTrate, dHighTaxGCTrate, dSRSB, dPSAlow, dPSAhigh, dRentAllowance, dDiviAllowance, dDiviRateLow,
				dDiviRateHigh);

		double dBudget = 72500.0;

		CashFlow cashFlow = new CashFlow(People, dBudget, dInflation, LocalDate.of(2026, 1, 1), txParams, niParams,
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
