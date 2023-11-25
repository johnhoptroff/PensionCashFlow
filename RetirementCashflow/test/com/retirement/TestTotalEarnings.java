package com.retirement;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;

class TestTotalEarnings {
	private final Account accAvivaJohn = new Account("Aviva John",75900.0, 0.03,true);
	private final Account accPremBonds = new Account("Premium Bonds",100000.0,0.01,false);
	private final Account accRRShares = new Account("R-R shares",30000.0,0.02,false);
	private final Account accISAs = new Account("ISAs",100000.0,0.03,false);
	private final Account accPruLynne = new Account("Pru Lynne",15000.0,0.03,true);
	private final Account accFordJohn = new Account("Ford John",55000.0,0.0595,false);
	List<Account> accountsLynne = new ArrayList<>();
	List<Account> accountsJohn = new ArrayList<>();
	
	private final IncomeStream streamRentJohn = new IncomeStream("RentJohn",LocalDate.of(2022,5,1),LocalDate.of(2100,1,1),5100.0,0.05);
	private final IncomeStream streamRentLynne = new IncomeStream("RentLynne",LocalDate.of(2022,5,1),LocalDate.of(2100,1,1),5100.0,0.05);
	private final IncomeStream streamWorkPenJohn = new IncomeStream("* RRPension*",LocalDate.of(2024,6,1),LocalDate.of(2100,1,1),27400.00,0.04);
	private final IncomeStream streamLGPSPenLynne = new IncomeStream("LGPSPension",LocalDate.of(2025,4,17),LocalDate.of(2100,1,1),10462.68,0.04);
	private final IncomeStream streamBankPenLynne = new IncomeStream("* BankPen  *",LocalDate.of(2028,11,14),LocalDate.of(2100,1,1),6911.16,0.04);
	private final IncomeStream streamStatePenJohn = new IncomeStream("StateJohn",LocalDate.of(2035,4,23),LocalDate.of(2100,1,1),10636.0,0.05);
	private final IncomeStream streamStatePenLynne = new IncomeStream("StateLynne",LocalDate.of(2035,11,14),LocalDate.of(2100,1,1),10636.0,0.05);
	private final IncomeStream streamSalJohn = new IncomeStream("SalaryJohn",LocalDate.of(1986,9,30),LocalDate.of(2024,6,1),41240.0,0.02);
	private final IncomeStream streamSalLynne = new IncomeStream("SalaryLynne",LocalDate.of(1986,6,9),LocalDate.of(2025,4,17),32000.0,0.01);
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
	
	
	@Test
	void test() {
        // test total earnings after 40 years 
		
		
		

		
		//TODO much more unit testing needed here!!
		//need to add the amount to pay into the pension pot
		//the stream will then have to add that amount into the pension account
		//taxable income needs to be reduced to account for the pension payment.
		this.streamSalJohn.setTaxable(true);
		this.streamSalJohn.setIncursNI(true);
		
		this.streamWorkPenJohn.setTaxable(true);
		this.streamWorkPenJohn.setIncursNI(false);
		
		this.streamStatePenJohn.setTaxable(true);
		this.streamStatePenJohn.setIncursNI(false);
		
		this.streamRentJohn.setTaxable(true);
		this.streamRentJohn.setIncursNI(true);
		
		this.streamRentLynne.setTaxable(true);
		this.streamRentLynne.setIncursNI(true);
		
		this.streamSalLynne.setTaxable(true);
		this.streamSalLynne.setIncursNI(true);
		
		this.streamLGPSPenLynne.setTaxable(true);
		this.streamLGPSPenLynne.setIncursNI(false);
		
		this.streamBankPenLynne.setTaxable(true);
		this.streamBankPenLynne.setIncursNI(false);
		
		this.streamStatePenLynne.setTaxable(true);
		this.streamStatePenLynne.setIncursNI(false);
		
		StreamsLynne.add(streamSalLynne);
		StreamsLynne.add(streamRentLynne);
		StreamsLynne.add(streamLGPSPenLynne);
		StreamsLynne.add(streamBankPenLynne);
		StreamsLynne.add(streamStatePenLynne);
		
		StreamsJohn.add(streamSalJohn);
		StreamsJohn.add(streamRentJohn);
		StreamsJohn.add(streamWorkPenJohn);
		StreamsJohn.add(streamStatePenJohn);
		
		
		accountsJohn.add(accAvivaJohn);
		accountsJohn.add(accFordJohn);
		accountsJohn.add(accRRShares);
		
		accountsLynne.add(accPremBonds);
		accountsLynne.add(accISAs);
		accountsLynne.add(accPruLynne);
		
		
		Person persJohn = new Person("John Hoptroff",LocalDate.of(1968,4,23),StreamsJohn,accountsJohn);
		Person persLynne = new Person("Lynne Hoptroff",LocalDate.of(1968,11,14),StreamsLynne,accountsLynne);
		persJohn.setPensionPot(accAvivaJohn);
		persLynne.setPensionPot(accPruLynne);
		List<Person> People = new ArrayList<>();
		
		People.add(persLynne);
		People.add(persJohn);

		TaxParams txParams = new TaxParams(dbTaxlow,dbTaxhigh,dbTaxlowpc,dbTaxhighpc);
		NIParams niParams = new NIParams(dbNIhighpc,dbNIlowpc,dbNIhighwk,dbNIlowwk);
		
		double dBudget = 58000.0;
		double dInflation = 0.05;
		CashFlow cashFlow = new CashFlow(People,dBudget,dInflation,LocalDate.of(2024,1,1),txParams,niParams);
		assertEquals(-5307.13, cashFlow.getResidual(LocalDate.of(2035,12,31)),0.1);
    	//assertEquals(5507.58, cashFlow.getResidual(40),0.1);
	}

}
