package com.retirement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TestGetAccBalance {

    private final Account prems = new Account("prems",50000.0,0.05,false);

    @Test
    void futureBal() {
        // test balance after 15 years of inflation with no deposits 
    	//and no withdrawals
    	for(int i=0;i<15;i++) {
    		prems.addInterest();
    	}
    	assertEquals(103946.41, prems.getdBalance(),0.1);
    }

}
