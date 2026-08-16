package com.retirement;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class TestTAX_NI {

	static double dbTaxlow = 12500.0;
	static double dbTaxhigh = 50000.0;
	static double dbTaxlowpc = 0.20;
	static double dbTaxhighpc = 0.40;
	static double dbISAlimit = 20000.0;

	static double dbNIhighPaypc = 0.02;
	static double dbNIlowPaypc = 0.12;
	static double dbNIhighwk = 967.0;
	static double dbNIlowwk = 242.0;
	static LocalDate dtFrozenTh = LocalDate.of(2031, 4, 5);

	TaxParams txParams = new TaxParams(dbTaxlow, dbTaxhigh, dbTaxlowpc, dbTaxhighpc, dbISAlimit,dtFrozenTh);
	NIParams niParams = new NIParams(dbNIhighPaypc, dbNIlowPaypc, dbNIhighwk, dbNIlowwk);
	File file = new File("src/resources/taxparams.xml");

	@Test
	void test() {
		double dTax = TaxNI.calcTax(65774.0, txParams);
		assertEquals(13809.6, dTax, 0.1);
		dTax = TaxNI.calcTax(11500.0, txParams);
		assertEquals(0.0, dTax, 0.1);
		dTax = TaxNI.calcTax(32000.0, txParams);
		assertEquals(3900, dTax, 0.1);

		double dNI = TaxNI.calcNI(65774.0, niParams);
		assertEquals(4830.2, dNI, 0.1);

		double dGrossAmnt = TaxNI.calcGrossFromNet(55000, 10000, txParams);
		assertEquals(16666.6667, dGrossAmnt, 0.001);

		dGrossAmnt = TaxNI.calcGrossFromNet(12505, 10000, txParams);
		assertEquals(12500.0, dGrossAmnt, 0.001);

		dGrossAmnt = TaxNI.calcGrossFromNet(11900, 10000, txParams);
		assertEquals(10000.0, dGrossAmnt, 0.001);

	}

	@Test
	void test2() {
		InputTaxParamsFromFile itpffData = new InputTaxParamsFromFile(file);
		this.txParams = itpffData.getTxPars();
		this.niParams = itpffData.getNiPars();
		
		double dTax = TaxNI.calcTax(65774.0, txParams);
		assertEquals(13809.6, dTax, 0.1);
		dTax = TaxNI.calcTax(11500.0, txParams);
		assertEquals(0.0, dTax, 0.1);
		dTax = TaxNI.calcTax(32000.0, txParams);
		assertEquals(3900, dTax, 0.1);

		double dNI = TaxNI.calcNI(65774.0, niParams);
		assertEquals(4830.2, dNI, 0.1);

		double dGrossAmnt = TaxNI.calcGrossFromNet(55000, 10000, txParams);
		assertEquals(16666.6667, dGrossAmnt, 0.001);

		dGrossAmnt = TaxNI.calcGrossFromNet(12505, 10000, txParams);
		assertEquals(12500.0, dGrossAmnt, 0.001);

		dGrossAmnt = TaxNI.calcGrossFromNet(11900, 10000, txParams);
		assertEquals(10000.0, dGrossAmnt, 0.001);

	}
}
