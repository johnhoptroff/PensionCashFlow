package com.retirement;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;

class TestSetEarningstoLevel {
	private final IncomeStream streamRentJohn = new IncomeStream("RentJohn",LocalDate.of(2022,5,1),LocalDate.of(2100,1,1),4900.0,0.026,true,false,false);
	private final IncomeStream streamWorkPen1John = new IncomeStream("*RRPension 1",LocalDate.of(2024,6,1),LocalDate.of(2100,1,1),12025.8,0.02,true,false,false);
	private final IncomeStream streamWorkPen3John = new IncomeStream("*RRPension 3",LocalDate.of(2024,6,1),LocalDate.of(2100,1,1),5186.04,0.025,true,false,false);
	private final IncomeStream streamWorkPen2John = new IncomeStream("*RRPension 2",LocalDate.of(2024,6,1),LocalDate.of(2100,1,1),6498.6,0.036,true,false,false);
	private final IncomeStream streamWorkPen4John = new IncomeStream("*RRPension 4",LocalDate.of(2024,6,1),LocalDate.of(2035,4,23),10759.08,0.025,true,false,false);
	private final IncomeStream streamStatePenJohn = new IncomeStream("StateJohn",LocalDate.of(2035,4,23),LocalDate.of(2100,1,1),12014.0,0.05,true,false,false);
	
	private final PensionAccount accSJPJohn = new PensionAccount("Aviva John",85687.5, 0.09);
	private final PremBondsAccount accBondsJohn = new PremBondsAccount("Bonds John",50000.0,0.044);
	private final AccountShares accRRSharesJohn = new AccountShares("R-R+shares",13266.39,0.02,6311.0,995);
	private final TaxedAccount accFordJohn = new TaxedAccount("Ford John",31707.98,0.0595);
	private final ISAaccount accISAJohn = new ISAaccount("ISAsJohn",105842.96,0.03);
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
	private final IncomeStream streamSalaryLynne = new IncomeStream("GSA-Lynne",LocalDate.of(2005,5,1),LocalDate.of(2026,6,1),44824.56,0.03,true,false,false);
	private final IncomeStream streamRentLynne = new IncomeStream("RentLynne",LocalDate.of(2022,5,1),LocalDate.of(2100,1,1),4900.0,0.026,true,false,false);
	private final IncomeStream streamDiviLynne = new IncomeStream("DiviLynne",LocalDate.of(2026,1,1),LocalDate.of(2100,1,1),150.0,0.015,true,false,false);
	private final IncomeStream streamLGPSPenLynne = new IncomeStream("LGPSPension",LocalDate.of(2028,11,14),LocalDate.of(2100,1,1),11398.19,0.04,true,false,false);
	private final IncomeStream streamBankPenLynne = new IncomeStream("* BankPen  *",LocalDate.of(2028,11,14),LocalDate.of(2100,1,1),7657.2,0.04,true,false,false);
	private final IncomeStream streamStatePenLynne = new IncomeStream("StateLynne",LocalDate.of(2035,11,14),LocalDate.of(2100,1,1),12014.0,0.05,true,false,false);
	
	private final PremBondsAccount accBondsLynne = new PremBondsAccount("Bonds Lynne",50000.0,0.044);
	private final AccountShares accRRSharesLynne = new AccountShares("R-R+shares",24962.68,0.02,10427.25,2300);
	private final ISAaccount accISALynne = new ISAaccount("ISAsLynne",106172.0,0.03);
	private final AccountEmbargoed accPruLynne = new AccountEmbargoed("Pru Lynne",40000.0,0.05,LocalDate.of(2028,11,14));
	
	List<AccountAbstract> accountsLynne = new ArrayList<>();
	List<AccountAbstract> accountsJohn = new ArrayList<>();
	

	List<IncomeStream> StreamsJohn = new ArrayList<>();
	List<IncomeStream> StreamsLynne = new ArrayList<>();
	
/*
 * 
 * 	private final double dbTaxlow = 12500.0;
	private final double dbTaxhigh = 50000.0;
	private final double dbTaxlowpc = 0.20;
	private final double dbTaxhighpc = 0.40;

	private final double dbNIhighpc = 0.02;
	private final double dbNIlowpc = 0.12;
	private final double dbNIhighwk = 967.0;
	private final double dbNIlowwk  = 242.0;
	private final double dbISAlimit = 20000.0;
 * 
 * 
 */
	@Test
	void testMaxNDLynne() {
        // test maximum non-descrescionary earnings Lynne

		StreamsLynne.add(streamSalaryLynne);
		StreamsLynne.add(streamRentLynne);
		StreamsLynne.add(streamDiviLynne);
		StreamsLynne.add(streamLGPSPenLynne);
		StreamsLynne.add(streamBankPenLynne);
		StreamsLynne.add(streamStatePenLynne);

		accountsLynne.add(accBondsLynne);
		accountsLynne.add(accISALynne);
		accountsLynne.add(accPruLynne);
		accountsLynne.add(accRRSharesLynne);
		
		PensionAccount pen = new PensionAccount("nopension",0.0,0.0);
		Person persLynne = new Person("Lynne Hoptroff",LocalDate.of(1968,11,14),StreamsLynne,accountsLynne, pen, 0.0, 0.0);
		

		try {
			assertEquals(12050.0, persLynne.getTaxYearStreamsTotal(LocalDate.of(2026, 4, 5)),0.1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	void testMaxNDJohn() {
        // test maximum non-descrescionary earnings John

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

		Person persJohn = new Person("John Hoptroff",LocalDate.of(1968,4,23),StreamsJohn,accountsJohn, accSJPJohn, 0.0, 0.0);
		

		try {
			assertEquals(39369.52, persJohn.getTaxYearStreamsTotal(LocalDate.of(2026, 4, 5)),0.1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
