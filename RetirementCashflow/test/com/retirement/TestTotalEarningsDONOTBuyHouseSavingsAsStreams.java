package com.retirement;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;

class TestTotalEarningsDONOTBuyHouseSavingsAsStreams {
	//accounts setup as of 1/1/2025
	double dInflation = 0.036;
	
	private final ISAaccount accISAs = new ISAaccount("ISAs",195977.0,0.045);
	private final TaxedAccount accSavingsTaxed = new TaxedAccount("Taxed",197240.0,0.0375);
	private final PremBondsAccount accSavingsNSI = new PremBondsAccount("PremBonds",100000.0,0.036); 

	List<AccountAbstract> accountsLynne = new ArrayList<>();
	List<AccountAbstract> accountsJohn = new ArrayList<>();
	
	private final IncomeStream streamRentJohn = new IncomeStream("RentJohn",LocalDate.of(2022,5,1),LocalDate.of(2037,1,1),4900.0,(dInflation-0.01),true,false,false);
	private final IncomeStream streamRentLynne = new IncomeStream("RentLynne",LocalDate.of(2022,5,1),LocalDate.of(2037,1,1),4900.0,(dInflation-0.01),true,false,false);
	private final IncomeStream streamBungalowFundJohn = new IncomeStream("BungJohn",LocalDate.of(2037,1,1),LocalDate.of(2057,1,1),6000.0,dInflation,false,false,false);
	private final IncomeStream streamBungalowFundLynne = new IncomeStream("BungLynne",LocalDate.of(2037,1,1),LocalDate.of(2057,1,1),6000.0,dInflation,false,false,false);
	private final IncomeStream streamRRsharesLynne = new IncomeStream("RRL-Lynne",LocalDate.of(2026,6,12),LocalDate.of(2032,6,12),4290.0,0.0,false,false,false);
	private final IncomeStream streamRRsharesJohn = new IncomeStream("RRL-John",LocalDate.of(2026,6,12),LocalDate.of(2029,6,12),5300.0,0.0,false,false,false);
	
	private final IncomeStream streamWorkPen1John = new IncomeStream("*RRPension 1",LocalDate.of(2024,6,1),LocalDate.of(2100,1,1),12025.8,0.02,true,false,false);
	private final IncomeStream streamWorkPen2John = new IncomeStream("*RRPension 2",LocalDate.of(2024,6,1),LocalDate.of(2100,1,1),5186.04,0.025,true,false,false);
	private final IncomeStream streamWorkPen3John = new IncomeStream("*RRPension 3",LocalDate.of(2024,6,1),LocalDate.of(2100,1,1),6498.60,dInflation,true,false,false);
	private final IncomeStream streamWorkPen4John = new IncomeStream("*RRPension 4",LocalDate.of(2024,6,1),LocalDate.of(2035,4,23),10759.08,0.025,true,false,false);
	private final IncomeStream streamWorkPen5John = new IncomeStream("*Aviva Pen 5",LocalDate.of(2026,1,1),LocalDate.of(2035,4,23),11000.0,0.0,true,false,false);
	private final IncomeStream streamWorkPen6John = new IncomeStream("*Aviva Pen 6",LocalDate.of(2035,4,24),LocalDate.of(2038,4,23),7940.52,0.0,true,false,false);
	
	private final IncomeStream streamLGPSPenLynne = new IncomeStream("LGPSPension",LocalDate.of(2028,11,14),LocalDate.of(2100,1,1),11398.19,dInflation,true,false,false);
	private final IncomeStream streamBankPenLynne = new IncomeStream("* BankPen  *",LocalDate.of(2028,11,14),LocalDate.of(2100,1,1),7657.2,dInflation,true,false,false);
	private final IncomeStream streamPruPenLynne = new IncomeStream("* PruPenL  *",LocalDate.of(2027,11,14),LocalDate.of(2031,11,14),11551.5,dInflation,false,false,false);
	
	private final IncomeStream streamBondOnLynne = new IncomeStream("* BondOn  *",LocalDate.of(2027,1,1),LocalDate.of(2029,11,13),27800.0,dInflation,false,false,false);
	private final IncomeStream streamBondOffshore = new IncomeStream("* BondOff1  *",LocalDate.of(2027,1,1),LocalDate.of(2031,1,1),7600.0,dInflation,false,false,false);
	private final IncomeStream streamBondOffshore2 = new IncomeStream("* BondOff2  *",LocalDate.of(2031,1,1),LocalDate.of(2047,1,1),2000.0,dInflation,true,false,false);
	
	private final IncomeStream streamStatePenJohn = new IncomeStream("StateJohn",LocalDate.of(2035,4,23),LocalDate.of(2100,1,1),12014.0,dInflation,true,false,false);
	private final IncomeStream streamStatePenLynne = new IncomeStream("StateLynne",LocalDate.of(2035,11,14),LocalDate.of(2100,1,1),12014.0,dInflation,true,false,false);
	private final IncomeStream streamSalLynne = new IncomeStream("SalaryLynne",LocalDate.of(1986,6,9),LocalDate.of(2026,5,14),39000.0,(dInflation-0.01),true,true,true);
	List<IncomeStream> StreamsJohn = new ArrayList<>();
	List<IncomeStream> StreamsLynne = new ArrayList<>();
	

	private final double dbTaxlow = 12570.0;
	private final double dbTaxhigh = 50270.0;
	private final double dbTaxlowpc = 0.20;
	private final double dbTaxhighpc = 0.40;

	private final double dbNIhighpc = 0.02;
	private final double dbNIlowpc = 0.12;
	private final double dbNIhighwk = 967.0;
	private final double dbNIlowwk  = 242.0;
	private final double dbISAlimit = 20000.0;
	private final LocalDate dtFrozenTh = LocalDate.of(2031, 04, 05);
	
	@Test
	void test() {
        // test total earnings to age 90

		StreamsLynne.add(streamSalLynne);
		StreamsLynne.add(streamRentLynne);
		StreamsLynne.add(streamLGPSPenLynne);
		StreamsLynne.add(streamBankPenLynne);
		StreamsLynne.add(streamStatePenLynne);
		StreamsLynne.add(streamPruPenLynne);
		StreamsLynne.add(streamBondOnLynne);
		StreamsLynne.add(streamBungalowFundLynne);
		StreamsLynne.add(streamRRsharesLynne);
		
		StreamsJohn.add(streamRentJohn);
		StreamsJohn.add(streamWorkPen1John);
		StreamsJohn.add(streamWorkPen2John);
		StreamsJohn.add(streamWorkPen3John);
		StreamsJohn.add(streamWorkPen4John);
		StreamsJohn.add(streamWorkPen5John);
		StreamsJohn.add(streamWorkPen6John);
		StreamsJohn.add(streamStatePenJohn);
		StreamsJohn.add(streamBungalowFundJohn);
		StreamsJohn.add(streamRRsharesJohn);
		StreamsJohn.add(streamBondOffshore);
		StreamsLynne.add(streamBondOffshore2);

		accountsJohn.add(accSavingsNSI);
		
		accountsLynne.add(accISAs);
		accountsLynne.add(accSavingsTaxed);
		
		
		PensionAccount pen = new PensionAccount("nopension",0.0,0.0);
		Person persJohn = new Person("John Hoptroff",LocalDate.of(1968,4,23),StreamsJohn,accountsJohn, pen, 0.0, 0.0);
		Person persLynne = new Person("Lynne Hoptroff",LocalDate.of(1968,11,14),StreamsLynne,accountsLynne, pen, 0.0, 0.0);

		List<Person> People = new ArrayList<>();
		
		People.add(persLynne);
		People.add(persJohn);

		TaxParams txParams = new TaxParams(dbTaxlow,dbTaxhigh,dbTaxlowpc,dbTaxhighpc,dbISAlimit,dtFrozenTh);
		NIParams niParams = new NIParams(dbNIhighpc,dbNIlowpc,dbNIhighwk,dbNIlowwk);
		
		double dBudget = 84750.0;
		
		CashFlow cashFlow = new CashFlow(People,dBudget,dInflation,LocalDate.of(2027,1,1),txParams,niParams);
		System.out.println("starting test...");
		try {
			assertEquals(-5307.13, cashFlow.getResidual(LocalDate.of(2058,12,31)),0.1);
			//cashFlow.runMaxEarnings(LocalDate.of(2059,12,31));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//assertEquals(5507.58, cashFlow.getResidual(40),0.1);
	}

}
