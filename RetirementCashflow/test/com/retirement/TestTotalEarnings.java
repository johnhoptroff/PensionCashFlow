package com.retirement;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;

class TestTotalEarnings {
	private final Account accAvivaJohn = new Account(75900.0, 0.03);
	private final Account accPremBonds = new Account(100000.0,0.01);
	private final Account accRRShares = new Account(30000.0,0.02);
	private final Account accISAs = new Account(100000.0,0.03);
	private final Account accAvivaLynne = new Account(15000.0,0.03);
	private final Account accFordJohn = new Account(50000.0,0.0595);
	List<Account> Accounts = new ArrayList<>();

	
	private final IncomeStream streamAvivaJohn = new IncomeStream(accAvivaJohn,false,true);
	private final IncomeStream streamAvivaLynne = new IncomeStream(accAvivaLynne,false,true);
	private final IncomeStream streamBonds = new IncomeStream(accAvivaLynne,false,false);
	private final IncomeStream streamFord = new IncomeStream(accAvivaLynne,false,false);
	private final IncomeStream streamWorkPenJohn = new IncomeStream(false,LocalDate.of(2024,6,2),27400.00);
	private final IncomeStream streamLGPSPenLynne = new IncomeStream(false,LocalDate.of(2025,4,17),10462.68);
	private final IncomeStream streamBankPenLynne = new IncomeStream(false,LocalDate.of(2028,11,14),6911.16);
	private final IncomeStream streamStatePenJohn = new IncomeStream(false,LocalDate.of(2035,4,23),10636.0);
	private final IncomeStream streamStatePenLynne = new IncomeStream(false,LocalDate.of(2035,11,14),10636.0);
	private final IncomeStream streamSalJohn = new IncomeStream(true,LocalDate.of(1986,9,30),40000.0);
	private final IncomeStream streamSalLynne = new IncomeStream(true,LocalDate.of(1986,6,9),30000.0);
	List<IncomeStream> Streams = new ArrayList<>();
	
	
	@Test
	void test() {
        // test total earnings after 40 years 
		
		Accounts.add(accAvivaJohn);
		Accounts.add(accPremBonds);
		Accounts.add(accRRShares);
		Accounts.add(accISAs);
		Accounts.add(accAvivaLynne);
		Accounts.add(accFordJohn);
		
		this.streamSalJohn.setEndDate(LocalDate.of(2024,6,1));
		this.streamSalLynne.setEndDate(LocalDate.of(2025,4,17));
		Streams.add(streamAvivaJohn);
		Streams.add(streamAvivaLynne);
		Streams.add(streamBonds);
		Streams.add(streamFord);
		Streams.add(streamWorkPenJohn);
		Streams.add(streamLGPSPenLynne);
		Streams.add(streamBankPenLynne);
		Streams.add(streamStatePenJohn);
		Streams.add(streamStatePenLynne);
		Streams.add(streamSalJohn);
		Streams.add(streamSalLynne);
		
		double dBudget = 49200.0;
		double dInflation = 0.05;
		CashFlow cashFlow = new CashFlow(Accounts,Streams,dBudget,dInflation,LocalDate.of(2024,1,1));
		assertEquals(11921.72, cashFlow.getFundingGap(),0.1);
    	//assertEquals(5507.58, cashFlow.getResidual(40),0.1);
	}

}
