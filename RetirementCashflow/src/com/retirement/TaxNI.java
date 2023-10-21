package com.retirement;

public class TaxNI {

	public static double calcTax(double dbGross, double dbTaxlow, double dbTaxhigh, double dbTaxlowpc,
			double dbTaxhighpc) {
		double dbPayLow;
		double dbPayHigh;
		if (dbGross > dbTaxhigh) {
			dbPayLow = dbTaxhigh - dbTaxlow; // sets amount paid at low rate
			dbPayHigh = dbGross - dbTaxhigh; // pay higher tax on the rest of salary
		} else if (dbGross < dbTaxlow) {
			dbPayLow = 0.0;
			dbPayHigh = 0.0;
		} else {
			dbPayHigh = 0.0;
			dbPayLow = dbGross - dbTaxlow;
		}

		return (dbPayLow * dbTaxlowpc) + (dbPayHigh * dbTaxhighpc);

	}

	public static double calcNI(double dbGross, double dbNIhighpc, double dbNIlowpc, double dbNIhighwk,
			double dbNIlowwk, int intPaidMnths) {
		double dbPayWeekly;
		dbPayWeekly = dbGross / (365 / 7);
		double dbPayLow;
		double dbPayHigh;
		if(dbPayWeekly > dbNIhighwk) {
		   dbPayLow = dbNIhighwk - dbNIlowwk; //sets amount paid at low rate
		   dbPayHigh = dbPayWeekly - dbNIhighwk; //pay higher NI on the rest of salary
		} else if (dbPayWeekly < dbNIlowwk) {
		   dbPayLow = 0;
		   dbPayHigh = 0;
		} else { // pay between low and high
		   dbPayHigh = 0;
		   dbPayLow = dbPayWeekly - dbNIlowwk;
		}
		return ((dbPayLow * dbNIlowpc) + (dbPayHigh * dbNIhighpc)) * 52 * (intPaidMnths / 12);
	}

}
