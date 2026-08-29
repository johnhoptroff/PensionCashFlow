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
	
	private static double[] taxFromSavingsandDivi(SuplimentalTaxParams suppParams, TaxParams txParams, double dPSA, double dNonSavingsInc, double dInterest, double dBondOn, double dBondOff, double dDivi) {
		// it is allowed to use any unused allowance below the lower tax level on either savings interest or dividend
		
		double[] tax = {0.0,0.0,0.0};
		// tax deemed paid for on-shore bond
		double dSavingsIncome = dInterest + dBondOff;
		double dUnusedToLow = Math.max(0, txParams.getTaxLow() - dNonSavingsInc);
		double dUnusedToHigh = Math.max(0, txParams.getTaxHigh() - dNonSavingsInc);
		double dSavingsAmt;
		double dDiviAmt;
		double dTaxDueOnshoreBond=0.0;
		double dTaxDeemedPaidOnshoreBond = dBondOn*txParams.getTaxLowpc();
		if(dUnusedToHigh >(dSavingsIncome + dDivi + dBondOn) ) {
			// tax deemed paid for on-shore bond at low rate
			
			// savings first
			dDiviAmt = Math.max(0, dDivi - suppParams.getDiviAllowance());
			dSavingsAmt = Math.max(0,(dSavingsIncome-dUnusedToLow-dPSA));
			double dDivTax1 = dDiviAmt*suppParams.getDiviRateLow();
			double dSavTax1 = dSavingsAmt*txParams.getTaxLowpc();

			// dividend first
			dDiviAmt = Math.max(0, dDivi - dUnusedToLow - suppParams.getDiviAllowance());
			dSavingsAmt = Math.max(0,(dSavingsIncome-dPSA));
			double dDivTax2 = dDiviAmt*suppParams.getDiviRateLow();
			double dSavTax2 = dSavingsAmt*txParams.getTaxLowpc();
			if((dDivTax1+dSavTax1) <= (dDivTax2 + dSavTax2)) {
				// better to take allowance from savings
				dSavingsAmt = Math.max(0, (dSavingsIncome-dUnusedToLow-dPSA));
				dDiviAmt = Math.max(0, dDivi - suppParams.getDiviAllowance());
				
			}else {
				// better to take allowance from dividend
				dSavingsAmt = Math.max(0, (dSavingsIncome-dPSA));
				dDiviAmt = Math.max(0, dDivi - dUnusedToLow - suppParams.getDiviAllowance());
			}
			double dSavingsTax =  dSavingsAmt*txParams.getTaxLowpc();
			double dDiviTax = dDiviAmt*suppParams.getDiviRateLow();
			double dUnusedPSA = Math.max(0, (dPSA-dSavingsAmt));
			dTaxDueOnshoreBond = Math.max(0,(dBondOn-dUnusedPSA))*txParams.getTaxLowpc();
			tax[0] = dSavingsTax;
			tax[1] = dDiviTax;
			
		}else {
			// total earnings above higher tax limit

			if(dUnusedToHigh == 0.0) {
				// no leeway to high limit so full savings taxed
				tax[0] = Math.max(0, (dSavingsIncome-dPSA))*txParams.getTaxHighpc();
			}else {
				// some taxed at the lower rate
				double dAmtToHigh =  Math.max(0, (dSavingsIncome-dUnusedToHigh));
				double dAmtAboveHigh = dSavingsIncome-dAmtToHigh;
				double dUnusedPSA =  Math.max(0, (dPSA-dAmtAboveHigh));
				tax[0] = Math.max(0, (dSavingsIncome-dAmtToHigh-dPSA))*txParams.getTaxHighpc();
				tax[0] += Math.max(0, (dAmtToHigh-dUnusedPSA))*txParams.getTaxLowpc();
			}
			dDiviAmt = Math.max(0, dDivi - suppParams.getDiviAllowance());
			tax[1]= dDiviAmt*suppParams.getDiviRateHigh();
		}
	
		tax[2] = Math.max(0,dTaxDeemedPaidOnshoreBond-dTaxDueOnshoreBond); // tax deemed paid on on-shore bond
		return tax;
	}

	public static double calcIncomeTax(TaxForm taxForm, TaxParams txParams, NIParams niParams,
			SuplimentalTaxParams suppParams) {
		double dCummInc = 0.0;
		double tax = 0.0;
		double dIncome = taxForm.getdTaxableEarnings();
		double dRentalIncome = taxForm.getRentalIncome();
		double dBondChargeOff = taxForm.getdBondChargeOff();
		double dBondChargeOn = taxForm.getdBondChargeOn();
		double dInterest = taxForm.getdInterest();
		double dDiviIncome = taxForm.getdDividend();
		double dUnusedToLow = txParams.getTaxLow();
		double dUnusedToHigh = txParams.getTaxHigh();

		double dSharesAllowance = suppParams.getDbCGTLimit();
		//double dDivisuppParams.get // need to include dividend rates

		double dTotalEarnings = dIncome + dRentalIncome + dInterest + dDiviIncome + dBondChargeOff + dBondChargeOn;

		// Earnings not savings related
		dCummInc = dIncome;
		if (dIncome <= txParams.getTaxLow())
			dUnusedToLow = txParams.getTaxLow() - dIncome;
		if (dIncome > txParams.getTaxLow() && dIncome <= txParams.getTaxHigh()) {
			tax = tax + ((dIncome - txParams.getTaxLow()) * txParams.getTaxLowpc());
			dUnusedToLow = 0.0;
			dUnusedToHigh = txParams.getTaxHigh() - dIncome;
		}
		if (dIncome > txParams.getTaxHigh()) {
			tax = tax + ((txParams.getTaxHigh() - txParams.getTaxLow()) * txParams.getTaxLowpc());
			tax = tax + ((dIncome - txParams.getTaxHigh()) * txParams.getTaxHighpc());
			dUnusedToLow = 0.0;
			dUnusedToHigh = 0.0;
		}
		dCummInc = dCummInc + dRentalIncome;

		// Rental Earnings (taxed at rate + 2%)
		if (dCummInc < txParams.getTaxLow())
			dUnusedToLow = dUnusedToLow - dRentalIncome;
		if (dCummInc >= txParams.getTaxLow() && (dCummInc) <= txParams.getTaxHigh()) {
			tax = tax + Math.max(0, (dRentalIncome - dUnusedToLow - suppParams.getdRentAllowance())  * (txParams.getTaxLowpc() + 0.02));
			dUnusedToLow = 0.0;
			dUnusedToHigh = dUnusedToHigh - dRentalIncome;
		}
		if (dCummInc >= txParams.getTaxHigh()) {
			if(dIncome > txParams.getTaxHigh()) {
				tax += dRentalIncome*(txParams.getTaxHighpc()+0.02);
			}else {
				tax +=Math.max(0, (dRentalIncome - dUnusedToHigh))* (txParams.getTaxLowpc() + 0.02);
				tax +=(dCummInc-txParams.getTaxHigh())* (txParams.getTaxHighpc()+ 0.02);
			}
			dUnusedToLow = 0.0;
			dUnusedToHigh = 0.0;
		}
		//dCummInc = dCummInc + dBondChargeOn + dBondChargeOff + dDiviIncome + dInterest;
		// Savings Income taking into account PSA and SRSB
		// first calculate SRSB (Starting Rate Savers Band)
		double dSRSB = suppParams.getSRSB();
		if (dCummInc > (txParams.getTaxLow() + dSRSB)) {
			dSRSB = 0;
		}
		if (dCummInc > txParams.getTaxLow() && dCummInc < (txParams.getTaxLow() + dSRSB)) {
			dSRSB = dSRSB - (dCummInc - txParams.getTaxLow());
		}
		// then add to PSA
		double dPSA = suppParams.getdPSAlow();
		if (dTotalEarnings > txParams.getTaxHigh()) {
			dPSA = suppParams.getdPSAhigh();
		}
		dPSA = dPSA + dSRSB; // total tax relief on savings
        double[] dSavDiv = taxFromSavingsandDivi(suppParams, txParams, dPSA, (dIncome + dRentalIncome), dInterest, dBondChargeOn, dBondChargeOff, dDiviIncome);
		tax += dSavDiv[0]; // savings
        // offset if bond gain tax > tax deemed paid
        tax -= Math.max(0, dSavDiv[2]); // to credit back to non dividend earnings, NB tax min=0.
        tax = Math.max(0, tax);
        tax += dSavDiv[1];// dividend
        
        
		// finally CGT on shares
		if ((dTotalEarnings + taxForm.getdSharesGain()) > txParams.getTaxHigh()) {
			tax = tax + Math.max(0, (taxForm.getdSharesGain()-dSharesAllowance)) * suppParams.getdHighTaxGCTrate();
		} else {
			tax = tax + Math.max(0, (taxForm.getdSharesGain()-dSharesAllowance)) * suppParams.getdLowTaxCGTrate();
		}
		
		return tax;
	}

}
