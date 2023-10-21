package com.retirement;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestTAX_NI {

static double dbTaxlow = 12500.0;
static double dbTaxhigh = 50000.0;
static double dbTaxlowpc = 0.20;
static double dbTaxhighpc = 0.40;

static double dbNIhighpc = 0.02;
static double dbNIlowpc = 0.12;
static double dbNIhighwk = 962.0;
static double dbNIlowwk  = 166.0;

	@Test
	void test() {
		double dTax = TaxNI.calcTax(65774.0,dbTaxlow,dbTaxhigh,dbTaxlowpc,dbTaxhighpc);
		assertEquals(13809.6, dTax,0.1);
		dTax = TaxNI.calcTax(11500.0,dbTaxlow,dbTaxhigh,dbTaxlowpc,dbTaxhighpc);
		assertEquals(0.0, dTax,0.1);
		dTax = TaxNI.calcTax(32000.0,dbTaxlow,dbTaxhigh,dbTaxlowpc,dbTaxhighpc);
		assertEquals(3900, dTax,0.1);
	}

}
