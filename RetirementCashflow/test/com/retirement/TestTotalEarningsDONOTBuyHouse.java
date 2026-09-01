package com.retirement;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;

class TestTotalEarningsDONOTBuyHouse {
	// accounts setup as of 1/1/2025
	double dInflation = 0.036;

	List<AccountAbstract> accountsLynne = new ArrayList<>();
	List<AccountAbstract> accountsJohn = new ArrayList<>();
	List<StreamAbstract> StreamsJohn = new ArrayList<>();
	List<StreamAbstract> StreamsLynne = new ArrayList<>();
	
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
	
	private final RentalStream streamRentJohn = new RentalStream("RentJohn", LocalDate.of(2026, 1, 1),LocalDate.of(2100, 1, 1), 4900.0, 0.026);
	private final PensionStream streamWorkPen1John = new PensionStream("*RRPension 1", LocalDate.of(2026, 1, 1),LocalDate.of(2100, 1, 1), 12025.8, 0.02);
	private final PensionStream streamWorkPen3John = new PensionStream("*RRPension 3", LocalDate.of(2026, 1, 1),LocalDate.of(2100, 1, 1), 5186.04, 0.025);
	private final PensionStream streamWorkPen2John = new PensionStream("*RRPension 2", LocalDate.of(2026, 1, 1),LocalDate.of(2100, 1, 1), 6498.6, 0.036);
	private final PensionStream streamWorkPen4John = new PensionStream("*RRPension 4", LocalDate.of(2026, 1, 1),LocalDate.of(2035, 4, 23), 10759.08, 0.025);
	private final PensionStream streamStatePenJohn = new PensionStream("StateJohn", LocalDate.of(2035, 4, 23),LocalDate.of(2100, 1, 1), 12014.0, 0.05);

	private final PensionAccount accSJPJohn = new PensionAccount("SJP  John", 115000.0, 0.09);
	private final PremBondsAccount accBondsJohn = new PremBondsAccount("Bonds John", 50000.0, 0.044,dMaxPremBal);
	private final AccountShares accRRSharesJohn = new AccountShares("R-R+shares J", 13266.39, 0.02, 6311.0, 995, 0.06);
	private final TaxedAccount accFordJohn = new TaxedAccount("Ford John", 31707.98, 0.0595);
	private final ISAaccount accISAJohn = new ISAaccount("ISAsJohn", 105842.96, 0.03,txParams.getISAlimit());
	private final AccOffBond accBondOffJohn = new AccOffBond("OffshoreJohn", 105842.96, 0.03, LocalDate.of(2014, 5, 14));
	private final AccountEmbargoed accBungaJohn = new AccountEmbargoed("Bunga John", 120000.0, 0.0375,LocalDate.of(2035, 11, 14));
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
	private final EmploymentStream streamSalaryLynne = new EmploymentStream("GSA-Lynne", LocalDate.of(2027, 1, 1),LocalDate.of(2026, 6, 1), 44824.56, 0.03);
	private final RentalStream streamRentLynne = new RentalStream("RentLynne", LocalDate.of(2027, 1, 1),LocalDate.of(2100, 1, 1), 4900.0, 0.026);
	private final PensionStream streamLGPSPenLynne = new PensionStream("LGPSPension", LocalDate.of(2028, 11, 14),LocalDate.of(2100, 1, 1), 11398.19, 0.04);
	private final PensionStream streamBankPenLynne = new PensionStream("* BankPen  *", LocalDate.of(2028, 11, 14),LocalDate.of(2100, 1, 1), 7657.2, 0.04);
	private final PensionStream streamStatePenLynne = new PensionStream("StateLynne", LocalDate.of(2035, 11, 14),LocalDate.of(2100, 1, 1), 12014.0, 0.05);
	
	private final PremBondsAccount accBondsLynne = new PremBondsAccount("Bonds Lynne", 50000.0, 0.044,dMaxPremBal);
	private final AccountShares accRRSharesLynne = new AccountShares("R-R+shares L", 24962.68, 0.02, 10427.25, 2300,0.06);
	private final ISAaccount accISALynne = new ISAaccount("ISAsLynne", 106172.0, 0.03,txParams.getISAlimit());
	private final AccountEmbargoed accPruLynne = new AccountEmbargoed("Pru Lynne", 40000.0, 0.05,LocalDate.of(2028, 11, 14));
	private final AccOnBond accBondOnLynne = new AccOnBond("OnshoreLynne", 105842.96, 0.03, LocalDate.of(2014, 5, 14));
	private final AccountEmbargoed accBungaLynne = new AccountEmbargoed("Bunga Lynne", 120000.0, 0.0375,LocalDate.of(2035, 11, 14));
	
	
	@Test
	void test() {
		// test total earnings to age 90

		StreamsJohn.add(streamRentJohn);
		StreamsJohn.add(streamWorkPen1John);
		StreamsJohn.add(streamWorkPen2John);
		StreamsJohn.add(streamWorkPen3John);
		StreamsJohn.add(streamWorkPen4John);
		StreamsJohn.add(streamStatePenJohn);

		accountsJohn.add(accSJPJohn);
		accountsJohn.add(accBondsJohn);
		accountsJohn.add(accFordJohn);
		accountsJohn.add(accRRSharesJohn);
		accountsJohn.add(accISAJohn);
		accountsJohn.add(accBondOffJohn);
		accountsJohn.add(accBungaJohn);

		StreamsLynne.add(streamSalaryLynne);
		StreamsLynne.add(streamRentLynne);
		// StreamsLynne.add(streamDiviLynne); TODO need to find a way of getting a divi from a shares account
		StreamsLynne.add(streamLGPSPenLynne);
		StreamsLynne.add(streamBankPenLynne);
		StreamsLynne.add(streamStatePenLynne);

		accountsLynne.add(accBondsLynne);
		accountsLynne.add(accISALynne);
		accountsLynne.add(accPruLynne);
		accountsLynne.add(accRRSharesLynne);
		accountsLynne.add(accBondOnLynne);
		accountsLynne.add(accBungaLynne);
		
		
		PensionAccount pen = new PensionAccount("nopension", 0.0, 0.0);
		Person persJohn = new Person("John Hoptroff", LocalDate.of(1968, 4, 23), StreamsJohn, accountsJohn, pen, 0.0,
				0.0);
		Person persLynne = new Person("Lynne Hoptroff", LocalDate.of(1968, 11, 14), StreamsLynne, accountsLynne, pen,
				0.0, 0.0);

		List<Person> People = new ArrayList<>();

		People.add(persLynne);
		People.add(persJohn);



		double dBudget = 5000.0;

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
