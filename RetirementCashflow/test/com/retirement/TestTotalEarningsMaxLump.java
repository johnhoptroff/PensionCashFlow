package com.retirement;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;

class TestTotalEarningsMaxLump {
	private final PensionAccount accAvivaJohn = new PensionAccount("Aviva John",85687.5, 0.03); // added redundo no 2024 contrs actual = £114250.0
	//private final Account accTransPenJohn = new Account("Transfer John",185250.0, 0.03,true); // 25% taken out transfered to Lump acc
	private final PremBondsAccount accBondsJohn = new PremBondsAccount("Bonds John",50000.0,0.044);
	private final PremBondsAccount accBondsLynne = new PremBondsAccount("Bonds Lynne",50000.0,0.044);
	private final AccountShares accRRShares = new AccountShares("R-R+shares",40000.0,0.02,6311.0,995);
	private final ISAaccount accISAs = new ISAaccount("ISAs",106172.0,0.03);
	private final PensionAccount accPruLynne = new PensionAccount("Pru Lynne",12600.0,0.03); // no 2024 contributions
	private final TaxedAccount accFordJohn = new TaxedAccount("Ford John",53137.0,0.0595);
	private final TaxedAccount accLumpJohn = new TaxedAccount("Lump John",28562.5,0.04); // 25% of Aviva
	List<AccountAbstract> accountsLynne = new ArrayList<>();
	List<AccountAbstract> accountsJohn = new ArrayList<>();
	
	private final IncomeStream streamRentJohn = new IncomeStream("RentJohn",LocalDate.of(2022,5,1),LocalDate.of(2100,1,1),5700.0,0.05,true,false,false);
	private final IncomeStream streamRentLynne = new IncomeStream("RentLynne",LocalDate.of(2022,5,1),LocalDate.of(2100,1,1),5700.0,0.05,true,false,false);
	
	private final IncomeStream streamWorkPen1John = new IncomeStream("*RRPension 1",LocalDate.of(2024,6,1),LocalDate.of(2100,1,1),11505.17,0.02,true,false,false);
	private final IncomeStream streamWorkPen3John = new IncomeStream("*RRPension 3",LocalDate.of(2024,6,1),LocalDate.of(2100,1,1),6173.16,0.05,true,false,false);
	private final IncomeStream streamWorkPen2John = new IncomeStream("*RRPension 2",LocalDate.of(2024,6,1),LocalDate.of(2100,1,1),10037.56,0.025,true,false,false);
	
	//private final IncomeStream streamWorkPen4John = new IncomeStream("*RRPension 4",LocalDate.of(2024,6,1),LocalDate.of(2035,4,23),10350.0,0.025,true,false,false);
	
	private final IncomeStream streamLGPSPenLynne = new IncomeStream("LGPSPension",LocalDate.of(2025,4,17),LocalDate.of(2100,1,1),8462.68,0.04,true,false,false);
	private final IncomeStream streamBankPenLynne = new IncomeStream("* BankPen  *",LocalDate.of(2028,11,14),LocalDate.of(2100,1,1),6911.16,0.04,true,false,false);
	private final IncomeStream streamStatePenJohn = new IncomeStream("StateJohn",LocalDate.of(2035,4,23),LocalDate.of(2100,1,1),11502.0,0.05,true,false,false);
	private final IncomeStream streamStatePenLynne = new IncomeStream("StateLynne",LocalDate.of(2035,11,14),LocalDate.of(2100,1,1),11502.0,0.05,true,false,false);
	private final IncomeStream streamSalJohn = new IncomeStream("SalaryJohn",LocalDate.of(1986,9,30),LocalDate.of(2024,6,1),41240.0,0.035,true,true,true);
	private final IncomeStream streamSalLynne = new IncomeStream("SalaryLynne",LocalDate.of(1986,6,9),LocalDate.of(2025,4,17),32000.0,0.01,true,true,true);
	List<IncomeStream> StreamsJohn = new ArrayList<>();
	List<IncomeStream> StreamsLynne = new ArrayList<>();
	

	private final double dbTaxlow = 12500.0;
	private final double dbTaxhigh = 50000.0;
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
		
		StreamsJohn.add(streamSalJohn);
		StreamsJohn.add(streamRentJohn);
		StreamsJohn.add(streamWorkPen1John);
		StreamsJohn.add(streamWorkPen2John);
		StreamsJohn.add(streamWorkPen3John);
		//StreamsJohn.add(streamWorkPen4John);
		StreamsJohn.add(streamStatePenJohn);
		
		accBondsLynne.setMaximumBalance(50000.0);;
		accBondsLynne.setPayAccount(accISAs);
		accBondsJohn.setMaximumBalance(50000.0);
		accBondsJohn.setPayAccount(accISAs);

		accountsJohn.add(accAvivaJohn);
		accountsJohn.add(accBondsJohn);
		accountsJohn.add(accFordJohn);
		accountsJohn.add(accRRShares);
		accountsJohn.add(accLumpJohn);
		
		accountsLynne.add(accBondsLynne);
		accountsLynne.add(accISAs);
		accountsLynne.add(accPruLynne);
		
		
		
		Person persJohn = new Person("John Hoptroff",LocalDate.of(1968,4,23),StreamsJohn,accountsJohn, accAvivaJohn, 16651.0, 4948.80);
		Person persLynne = new Person("Lynne Hoptroff",LocalDate.of(1968,11,14),StreamsLynne,accountsLynne, accPruLynne, 10800.0, 0.0);
		List<Person> People = new ArrayList<>();
		
		People.add(persLynne);
		People.add(persJohn);

		TaxParams txParams = new TaxParams(dbTaxlow,dbTaxhigh,dbTaxlowpc,dbTaxhighpc, dbISAlimit,LocalDate.of(2031, 4, 5) );
		NIParams niParams = new NIParams(dbNIhighpc,dbNIlowpc,dbNIhighwk,dbNIlowwk);
		
		double dBudget = 73700.0;
		double dInflation = 0.05;
		CashFlow cashFlow = new CashFlow(People,dBudget,dInflation,LocalDate.of(2024,1,1),txParams,niParams);
		try {
			assertEquals(-5307.13, cashFlow.getResidual(LocalDate.of(2038,12,31)),0.1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//assertEquals(5507.58, cashFlow.getResidual(40),0.1);
	}

}
