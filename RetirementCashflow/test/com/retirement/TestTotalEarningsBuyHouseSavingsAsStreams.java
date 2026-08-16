package com.retirement;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;

class TestTotalEarningsBuyHouseSavingsAsStreams {
	//accounts setup as of 1/1/2025
	double dInflation = 0.036;
	
	//private final Account accPremBonds = new Account("Premium_Bonds",100000.0,0.039,false,false);
	private final SharesAccount accRRShares = new SharesAccount("R-R+shares",40000.0,0.02);
	private final ISAaccount accISAs = new ISAaccount("ISAs",163700.0,0.04);
	//private final Account accFordJohn = new Account("Flex_accounts",219550.0,0.035,false,true); 

	List<AccountAbstract> accountsLynne = new ArrayList<>();
	List<AccountAbstract> accountsJohn = new ArrayList<>();
	
	private final IncomeStream streamRentJohn = new IncomeStream("RentJohn",LocalDate.of(2022,5,1),LocalDate.of(2100,1,1),5700.0,dInflation,true,false,false);
	private final IncomeStream streamRentLynne = new IncomeStream("RentLynne",LocalDate.of(2022,5,1),LocalDate.of(2100,1,1),5700.0,dInflation,true,false,false);
	
	private final IncomeStream streamWorkPen1John = new IncomeStream("*RRPension 1",LocalDate.of(2024,6,1),LocalDate.of(2100,1,1),11505.17,0.02,true,false,false);
	private final IncomeStream streamWorkPen2John = new IncomeStream("*RRPension 2",LocalDate.of(2024,6,1),LocalDate.of(2100,1,1),4988.78,0.025,true,false,false);
	private final IncomeStream streamWorkPen3John = new IncomeStream("*RRPension 3",LocalDate.of(2024,6,1),LocalDate.of(2100,1,1),6173.16,dInflation,true,false,false);
	private final IncomeStream streamWorkPen4John = new IncomeStream("*RRPension 4",LocalDate.of(2024,6,1),LocalDate.of(2035,4,23),10350.0,0.025,true,false,false);
	private final IncomeStream streamWorkPen5John = new IncomeStream("*Aviva Pen 5",LocalDate.of(2026,1,1),LocalDate.of(2035,4,23),11000.0,0.0,true,false,false);
	private final IncomeStream streamWorkPen6John = new IncomeStream("*Aviva Pen 6",LocalDate.of(2035,4,24),LocalDate.of(2038,4,23),7940.52,0.0,true,false,false);
	
	private final IncomeStream streamLGPSPenLynne = new IncomeStream("LGPSPension",LocalDate.of(2028,11,14),LocalDate.of(2100,1,1),11398.19,dInflation,true,false,false);
	private final IncomeStream streamBankPenLynne = new IncomeStream("* BankPen  *",LocalDate.of(2028,11,14),LocalDate.of(2100,1,1),7657.2,dInflation,true,false,false);
	private final IncomeStream streamPruPenLynne = new IncomeStream("* PruPenL  *",LocalDate.of(2028,11,14),LocalDate.of(2035,11,14),2804.25,dInflation,true,false,false);
	
	private final IncomeStream streamBondOnLynne = new IncomeStream("* BondOn  *",LocalDate.of(2026,4,4),LocalDate.of(2028,11,13),21700.0,dInflation,true,false,false);
	private final IncomeStream streamBondOffshore = new IncomeStream("* BondOff  *",LocalDate.of(2026,4,4),LocalDate.of(2028,11,13),21700.0,dInflation,true,false,false);
	
	
	private final IncomeStream streamStatePenJohn = new IncomeStream("StateJohn",LocalDate.of(2035,4,23),LocalDate.of(2100,1,1),12014.0,dInflation,true,false,false);
	private final IncomeStream streamStatePenLynne = new IncomeStream("StateLynne",LocalDate.of(2035,11,14),LocalDate.of(2100,1,1),12014.0,dInflation,true,false,false);
	private final IncomeStream streamSalLynne = new IncomeStream("SalaryLynne",LocalDate.of(1986,6,9),LocalDate.of(2026,4,4),39000.0,(dInflation-0.01),true,true,true);
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
	
	
	@Test
	void test() {
        // test total earnings after 40 years 

		StreamsLynne.add(streamSalLynne);
		StreamsLynne.add(streamRentLynne);
		StreamsLynne.add(streamLGPSPenLynne);
		StreamsLynne.add(streamBankPenLynne);
		StreamsLynne.add(streamStatePenLynne);
		StreamsLynne.add(streamPruPenLynne);
		StreamsLynne.add(streamBondOnLynne);
		StreamsLynne.add(streamBondOffshore);
		
		StreamsJohn.add(streamRentJohn);
		StreamsJohn.add(streamWorkPen1John);
		StreamsJohn.add(streamWorkPen2John);
		StreamsJohn.add(streamWorkPen3John);
		StreamsJohn.add(streamWorkPen4John);
		StreamsJohn.add(streamWorkPen5John);
		StreamsJohn.add(streamWorkPen6John);
		StreamsJohn.add(streamStatePenJohn);
		

		accountsJohn.add(accRRShares);

		accountsLynne.add(accISAs);

		
		
		Person persJohn = new Person("John Hoptroff",LocalDate.of(1968,4,23),StreamsJohn,accountsJohn, null, 0.0, 0.0);
		Person persLynne = new Person("Lynne Hoptroff",LocalDate.of(1968,11,14),StreamsLynne,accountsLynne, null, 0.0, 0.0);

		List<Person> People = new ArrayList<>();
		
		People.add(persLynne);
		People.add(persJohn);

		TaxParams txParams = new TaxParams(dbTaxlow,dbTaxhigh,dbTaxlowpc,dbTaxhighpc,dbISAlimit,LocalDate.of(2031, 4, 5) );
		NIParams niParams = new NIParams(dbNIhighpc,dbNIlowpc,dbNIhighwk,dbNIlowwk);
		
		double dBudget = 72000.0;
		
		CashFlow cashFlow = new CashFlow(People,dBudget,dInflation,LocalDate.of(2026,1,1),txParams,niParams);
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
