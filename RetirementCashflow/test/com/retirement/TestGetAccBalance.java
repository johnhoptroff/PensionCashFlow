package com.retirement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class TestGetAccBalance {

    private final PremBondsAccount prems = new PremBondsAccount("prems",50000.0,0.05,50000.0);
	Person john = new Person("John Hoptroff", null, null, null, null, 0, 0);

    @Test
    void futureBal() {
        // test balance after 15 years of inflation with no deposits 
    	//and no withdrawals
    	prems.setHolder(john);
    	//LocalDate.of(1968,4,23);
    	for (LocalDate date = LocalDate.of(2024,1,15); date.isBefore(LocalDate.of(2039,1,15)); date = date.plusYears(1))
    	{
    		prems.addInterest(date);
    	}
    	assertEquals(103946.41, prems.getdBalance(),0.01);
    	
    	StringBuffer buffer = new StringBuffer(prems.toString());
    	System.out.println(buffer);
    	prems.close(LocalDate.of(2039,1,15));
    }
    
    @Test
    void balanceAtDate() {
        // test balance at a certain date 
    	prems.setHolder(john);
    	for (LocalDate date = LocalDate.of(2024,1,15); date.isBefore(LocalDate.of(2039,1,15)); date = date.plusYears(1))
    	{
    		prems.addInterest(date);
    	}
    	assertEquals(77566.41, prems.getdBalanceAtDate(LocalDate.of(2032,4,4)),0.01);
    	
    	StringBuffer buffer = new StringBuffer(prems.toString());
    	System.out.println(buffer);
    	prems.close(LocalDate.of(2039,1,15));
    }
}
