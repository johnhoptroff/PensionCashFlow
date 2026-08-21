package com.retirement;

public class TaxNI {

	public static double calcTax(double dbGross, TaxParams txParams) {
		double dbTaxlow = txParams.getTaxLow();
		double dbTaxhigh = txParams.getTaxHigh();
		double dbTaxlowpc = txParams.getTaxLowpc();
		double dbTaxhighpc = txParams.getTaxHighpc();
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

	public static double calcNI(double dbGross, NIParams niParams) {
		double dbNIhighPaypc = niParams.getNIhighPaypc();
		double dbNIlowPaypc = niParams.getNIlowPaypc();
		double dbNIhighwk = niParams.getNIhiWk();
		double dbNIlowwk = niParams.getNIlowWk();
		double dbPayWeekly;

		double dWeek = 365.0 / 7.0;
		dbPayWeekly = dbGross / dWeek;
		double dbPayLow;
		double dbPayHigh;
		if (dbPayWeekly > dbNIhighwk) { // pay above upper threshold
			dbPayLow = dbNIhighwk - dbNIlowwk; // sets amount paid between upper and lower thresholds
			dbPayHigh = dbPayWeekly - dbNIhighwk; // pay level above upper threshold
		} else if (dbPayWeekly < dbNIlowwk) { // pay below lower limit
			dbPayLow = 0;
			dbPayHigh = 0;
		} else { // pay between low and high
			dbPayHigh = 0;
			dbPayLow = dbPayWeekly - dbNIlowwk;
		}
		return ((dbPayLow * dbNIlowPaypc) + (dbPayHigh * dbNIhighPaypc)) * 52;
	}

	public static double calcGrossFromNet(double dTaxable, double dbNetAmnt, TaxParams txParams) {
		double dRate = 0.0;
		if (dTaxable >= txParams.getTaxHigh()) {
			dRate = txParams.getTaxHighpc();
		} else {
			if (dTaxable >= txParams.getTaxLow()) {
				dRate = txParams.getTaxLowpc();

			} else {
				dRate = 0.0;
			}
		}
		return dbNetAmnt / (1 - dRate);
	}

	public static double calcStoppages(TaxForm taxForm, TaxParams txParams, NIParams niParams,
			SuplimentalTaxParams suppParams) {
		double dCummInc = 0.0;
		double tax = 0.0;
		double dIncome = taxForm.getdTaxableEarnings();
		double dRentalIncome = taxForm.getRentalIncome();
		double dSavingsIncome = taxForm.getdInterest() + taxForm.getdBondChargeable();
		double dDiviIncome = taxForm.getdDividend();
		
		double dTotalEarnings = dIncome + dRentalIncome + dSavingsIncome + dDiviIncome;
		
		// Earnings not savings related
		dCummInc = dCummInc + dIncome;
		if(dCummInc > txParams.getTaxLow()) {
			tax = tax + ((dCummInc - txParams.getTaxLow())*txParams.getTaxLowpc());
		}
		if(dCummInc > txParams.getTaxHigh()) {
			tax = tax +((dCummInc - txParams.getTaxHigh())*txParams.getTaxHighpc());
		}
		
		// 
		// TODO NO!!!!! this keeps taxing the same amount!!!!!
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		
		// Rental Earnings (taxed at rate + 2%)
		dCummInc = dCummInc + dRentalIncome;
		if(dCummInc > txParams.getTaxLow()) {
			tax = tax + ((dCummInc - txParams.getTaxLow())*txParams.getTaxLowpc());
		}
		if(dCummInc > txParams.getTaxHigh()) {
			tax = tax +((dCummInc - txParams.getTaxHigh())*txParams.getTaxHighpc());
		}
		// Savings Income taking into account PSA and SRSB
		// first calculate SRSB (Starting Rate Savers Band)
		double dSRSB = suppParams.getSRSB();
		if(dCummInc > (txParams.getTaxLow()+dSRSB)) {
			dSRSB = 0;
		}
		if(dCummInc > txParams.getTaxLow() && dCummInc < (txParams.getTaxLow()+dSRSB)) {
			dSRSB = dSRSB - (dCummInc -txParams.getTaxLow() );
		}
		// then add to PSA
		double dPSA=suppParams.getdPSAlow();
		if(dTotalEarnings > txParams.getTaxHigh()) {
			dPSA = suppParams.getdPSAhigh();
		}
		dCummInc = dCummInc + dSavingsIncome;
		// Taxable Earnings First
		//double tax = calcTax(dTotalEarnings, txParams);
		if ((dTotalEarnings + taxForm.getdSharesGain()) > txParams.getTaxHigh()) {
			tax = tax + taxForm.getdSharesGain() * suppParams.getdHighTaxGCTrate();
		} else {
			tax = tax + taxForm.getdSharesGain() * suppParams.getdLowTaxCGTrate();
		}
		return 0;
	}

}
