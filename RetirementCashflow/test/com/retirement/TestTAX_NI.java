package com.retirement;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class TestTAX_NI {

	static double dbTaxlow = 12570.0;
	static double dbTaxhigh = 50270.0;
	static double dbTaxlowpc = 0.20;
	static double dbTaxhighpc = 0.40;
	static double dbISAlimit = 20000.0;

	static double dbNIhighPaypc = 0.02;
	static double dbNIlowPaypc = 0.12;
	static double dbNIhighwk = 967.0;
	static double dbNIlowwk = 242.0;
	static LocalDate dtFrozenTh = LocalDate.of(2031, 4, 5);

	private final double dbCGTLimit = 3000.0;
	private final double dbLowTaxDiviLimit = 500.0;
	private final double dbHighTaxDiviLimit = 0.0;
	private final double dLowTaxCGTrate = 0.18;
	private final double dHighTaxGCTrate = 0.24;
	private final double dSRSB = 5000.0;
	private final double dPSAlow = 1000.0;
	private final double dPSAhigh = 500.0;
	private final double dRentAllowance = 1000.0;
	private final double dDiviAllowance = 500.0;
	private final double dDiviRateLow = 0.0875;
	private final double dDiviRateHigh = 0.033;

	TaxParams txParams = new TaxParams(dbTaxlow, dbTaxhigh, dbTaxlowpc, dbTaxhighpc, dbISAlimit, dtFrozenTh);
	NIParams niParams = new NIParams(dbNIhighPaypc, dbNIlowPaypc, dbNIhighwk, dbNIlowwk);

	SuplimentalTaxParams supParams = new SuplimentalTaxParams(dbCGTLimit, dbLowTaxDiviLimit, dbHighTaxDiviLimit,
			dLowTaxCGTrate, dHighTaxGCTrate, dSRSB, dPSAlow, dPSAhigh, dRentAllowance, dDiviAllowance, dDiviRateLow,
			dDiviRateHigh);

	File file = new File("src/resources/taxparams.xml");

	@Test
	void test() {
		double dTax = TaxNI.calcTax(65774.0, txParams);
		assertEquals(13741.6, dTax, 0.1);
		dTax = TaxNI.calcTax(11500.0, txParams);
		assertEquals(0.0, dTax, 0.1);
		dTax = TaxNI.calcTax(32000.0, txParams);
		assertEquals(3886, dTax, 0.1);

		double dNI = TaxNI.calcNI(65774.0, niParams);
		assertEquals(4830.2, dNI, 0.1);

		double dGrossAmnt = TaxNI.calcGrossFromNet(55000, 10000, txParams);
		assertEquals(16666.6667, dGrossAmnt, 0.001);

		dGrossAmnt = TaxNI.calcGrossFromNet(12505, 10000, txParams);
		assertEquals(10000.0, dGrossAmnt, 0.001);

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

	@Test
	void test3() {
		double dTaxableEarnings = 34469.52;
		final double dRentalIncome = 4900.0;
		final double dInterest = 1189.05;
		final double dDividend = 120.0;
		final double dSharesGain = 2995.0;
		final double dBondChargeOff = 0.0;
		final double dBondChargeOn = 0.0;
		TaxForm taxForm = new TaxForm(dTaxableEarnings, dRentalIncome, dInterest, dDividend, dSharesGain,
				dBondChargeOff, dBondChargeOn);

		double dTax = TaxNI.calcIncomeTax(taxForm, txParams, niParams, supParams);
		assertEquals(5275.71, dTax, 0.1);

	}

	@Test
	void test4() {
		double dTaxableEarnings = 7000;
		final double dRentalIncome = 4900.0;
		final double dInterest = 6153.42;
		final double dDividend = 120.0;
		final double dSharesGain = 0.0;
		final double dBondChargeOff = 0.0;
		final double dBondChargeOn = 0.0;
		TaxForm taxForm = new TaxForm(dTaxableEarnings, dRentalIncome, dInterest, dDividend, dSharesGain,
				dBondChargeOff, dBondChargeOn);

		double dTax = TaxNI.calcIncomeTax(taxForm, txParams, niParams, supParams);
		assertEquals(0.0, dTax, 0.1);
	}

	@Test
	void test5() { // from canada life academy website #1.
		double dTaxableEarnings = 11000;
		final double dRentalIncome = 0.0;
		final double dInterest = 4000.0;
		final double dDividend = 10000.0;
		final double dSharesGain = 0.0;
		final double dBondChargeOff = 2000.0;
		final double dBondChargeOn = 0.0;
		TaxForm taxForm = new TaxForm(dTaxableEarnings, dRentalIncome, dInterest, dDividend, dSharesGain,
				dBondChargeOff, dBondChargeOn);

		double dTax = TaxNI.calcIncomeTax(taxForm, txParams, niParams, supParams);
		assertEquals(694.0, dTax, 1.0);

	}

	@Test
	void test6() { // from canada life academy website #2.
		double dTaxableEarnings = 11000;
		final double dRentalIncome = 0.0;
		final double dInterest = 7000.0;
		final double dDividend = 10000.0;
		final double dSharesGain = 0.0;
		final double dBondChargeOff = 2000.0;
		final double dBondChargeOn = 0.0;
		TaxForm taxForm = new TaxForm(dTaxableEarnings, dRentalIncome, dInterest, dDividend, dSharesGain,
				dBondChargeOff, dBondChargeOn);

		double dTax = TaxNI.calcIncomeTax(taxForm, txParams, niParams, supParams);
		assertEquals(1117.25, dTax, 0.1);

	}

	@Test
	void test7() { // from canada life academy website #3.
		double dTaxableEarnings = 30000.0;
		final double dRentalIncome = 0.0;
		final double dInterest = 4000.0;
		final double dDividend = 10000.0;
		final double dSharesGain = 0.0;
		final double dBondChargeOff = 2000.0;
		final double dBondChargeOn = 0.0;
		TaxForm taxForm = new TaxForm(dTaxableEarnings, dRentalIncome, dInterest, dDividend, dSharesGain,
				dBondChargeOff, dBondChargeOn);

		double dTax = TaxNI.calcIncomeTax(taxForm, txParams, niParams, supParams);
		assertEquals(5317.25, dTax, 0.1);

	}

	@Test
	void test8() { // from canada life academy website #4.
		double dTaxableEarnings = 10000.0;
		final double dRentalIncome = 0.0;
		final double dInterest = 4000.0;
		final double dDividend = 10000.0;
		final double dSharesGain = 0.0;
		final double dBondChargeOff = 2000.0;
		final double dBondChargeOn = 10000.0;
		TaxForm taxForm = new TaxForm(dTaxableEarnings, dRentalIncome, dInterest, dDividend, dSharesGain,
				dBondChargeOff, dBondChargeOn);

		double dTax = TaxNI.calcIncomeTax(taxForm, txParams, niParams, supParams);
		assertEquals(606.38, dTax, 0.1);

	}

	@Test
	void test9() { // from MandD site.
		double dTaxableEarnings = 16570.0;
		final double dRentalIncome = 0.0;
		final double dInterest = 0.0;
		final double dDividend = 0.0;
		final double dSharesGain = 0.0;
		final double dBondChargeOff = 0.0;
		final double dBondChargeOn = 30000.0;
		TaxForm taxForm = new TaxForm(dTaxableEarnings, dRentalIncome, dInterest, dDividend, dSharesGain,
				dBondChargeOff, dBondChargeOn);

		double dTax = TaxNI.calcIncomeTax(taxForm, txParams, niParams, supParams);
		assertEquals(400.0, dTax, 0.1);

	}
	@Test
	void test10() { // simple high earner
		double dTaxableEarnings = 60270.0; //10k at high rate
		final double dRentalIncome = 0.0;
		final double dInterest = 0.0;
		final double dDividend = 0.0;
		final double dSharesGain = 0.0;
		final double dBondChargeOff = 0.0;
		final double dBondChargeOn = 0.0;
		TaxForm taxForm = new TaxForm(dTaxableEarnings, dRentalIncome, dInterest, dDividend, dSharesGain,
				dBondChargeOff, dBondChargeOn);

		double dTax = TaxNI.calcIncomeTax(taxForm, txParams, niParams, supParams);
		assertEquals(11540.0, dTax, 0.1);

	}
	@Test
	void test11() { // high earner with savings below limit
		double dTaxableEarnings = 60270.0; //10k at high rate
		final double dRentalIncome = 0.0;
		final double dInterest = 480.0;
		final double dDividend = 0.0;
		final double dSharesGain = 0.0;
		final double dBondChargeOff = 0.0;
		final double dBondChargeOn = 0.0;
		TaxForm taxForm = new TaxForm(dTaxableEarnings, dRentalIncome, dInterest, dDividend, dSharesGain,
				dBondChargeOff, dBondChargeOn);

		double dTax = TaxNI.calcIncomeTax(taxForm, txParams, niParams, supParams);
		assertEquals(11540.0, dTax, 0.1);

	}
	@Test
	void test12() { // high earner with savings above limit
		double dTaxableEarnings = 60270.0; //10k at high rate
		final double dRentalIncome = 0.0;
		final double dInterest = 2000.0;
		final double dDividend = 0.0;
		final double dSharesGain = 0.0;
		final double dBondChargeOff = 0.0;
		final double dBondChargeOn = 0.0;
		TaxForm taxForm = new TaxForm(dTaxableEarnings, dRentalIncome, dInterest, dDividend, dSharesGain,
				dBondChargeOff, dBondChargeOn);

		double dTax = TaxNI.calcIncomeTax(taxForm, txParams, niParams, supParams);
		assertEquals(12140.0, dTax, 0.1);

	}
	@Test
	void test13() { // high earner with savings pushing him into higher tax
		double dTaxableEarnings = 48270.0; //2k below high rate
		final double dRentalIncome = 0.0;
		final double dInterest = 4000.0; // takes to 2k over
		final double dDividend = 0.0;
		final double dSharesGain = 0.0;
		final double dBondChargeOff = 0.0;
		final double dBondChargeOn = 0.0;
		TaxForm taxForm = new TaxForm(dTaxableEarnings, dRentalIncome, dInterest, dDividend, dSharesGain,
				dBondChargeOff, dBondChargeOn);

		double dTax = TaxNI.calcIncomeTax(taxForm, txParams, niParams, supParams);
		assertEquals(8140.0, dTax, 0.1);

	}
	@Test
	void test14() { // high earner with savings pushing him into higher tax but is covered by PSA
		double dTaxableEarnings = 50170.0; //2k below high rate
		final double dRentalIncome = 0.0;
		final double dInterest = 350.0; // takes to 2k over
		final double dDividend = 0.0;
		final double dSharesGain = 0.0;
		final double dBondChargeOff = 0.0;
		final double dBondChargeOn = 0.0;
		TaxForm taxForm = new TaxForm(dTaxableEarnings, dRentalIncome, dInterest, dDividend, dSharesGain,
				dBondChargeOff, dBondChargeOn);

		double dTax = TaxNI.calcIncomeTax(taxForm, txParams, niParams, supParams);
		assertEquals(7520.0, dTax, 0.1);

	}
}
