package com.retirement;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Person {
	private String strName;
	private LocalDate dateBDay;
	private Double dTaxableIncome = 0.0;
	private Double dRentalIncome = 0.0;
	private Double dBondOffIncome = 0.0;
	private Double dBondOnIncome = 0.0;
	private Double dInterest = 0.0;
	private Double dDividend = 0.0;
	private Double dNIableIncome = 0.0;
	private Double dNetIncome = 0.0;
	private Double dPensionAmnt = 0.0;
	private Double dEmployerPenAmnt = 0.0;
	private List<StreamAbstract> streams;
	private List<AccountAbstract> accounts;
	private PensionAccount accPensionPot;
	private boolean boolHaveBond;
	private boolean boolHaveSIPP;
	private boolean boolHaveShares;
	private PensionAccount accSIPP;
	private TaxForm taxform;
	private AccountShares accShares;
	private boolean boolHaveISA;
	private ISAaccount accISA;


	public Person(String strName, LocalDate dateBDay, List<StreamAbstract> streams, List<AccountAbstract> accounts,
			PensionAccount accPensionPot, double dEmpContribution, double dPensionAmnt) {
		this.strName = strName;
		this.dateBDay = dateBDay;
		this.streams = streams;
		this.accounts = accounts;
		this.accPensionPot = accPensionPot;
		this.dEmployerPenAmnt = dEmpContribution;
		this.dPensionAmnt = dPensionAmnt;
		accounts.forEach(account -> {
			if (account instanceof BondAccount) {
				this.boolHaveBond = true;
			}
			if (account instanceof PensionAccount) {
				this.boolHaveSIPP = true;
				this.accSIPP = (PensionAccount) account;
			}
			if (account instanceof AccountShares) {
				this.boolHaveShares = true;
				this.accShares = (AccountShares) account;
			}
			if (account instanceof ISAaccount) {
				this.boolHaveISA = true;
				this.accISA = (ISAaccount) account;
			}
		});

	}

	public String getStrName() {
		return strName;
	}

	public LocalDate getDateBDay() {
		return dateBDay;
	}

	public Double getTaxableIncome() {
		return dTaxableIncome;
	}

	public void setTaxableIncome(Double dTaxableIncome) {
		this.dTaxableIncome = dTaxableIncome;
	}

	public Double getNIableIncome() {
		return dNIableIncome;
	}

	public void setNIableIncome(Double dNIableIncome) {
		this.dNIableIncome = dNIableIncome;
	}

	public List<AccountAbstract> getAccounts() {
		return this.accounts;

	}

	public Double getdTotalIncome() {
		return dTaxableIncome + dRentalIncome + dBondOffIncome + dBondOnIncome + dInterest + dDividend;
	}


	public PensionAccount getPensionAccount() {
		return this.accPensionPot;
	}

	public List<StreamAbstract> getStreams() {
		return this.streams;

	}

	@Override
	public String toString() {
		StringBuffer sbOutput = new StringBuffer("----------------------------------\n");
		sbOutput.append(strName + ", Birthday=" + dateBDay + ", TaxableIncome="
				+ NumberFormat.getCurrencyInstance().format(dTaxableIncome));
		sbOutput.append(", NIableIncome=" + NumberFormat.getCurrencyInstance().format(dNIableIncome));
		sbOutput.append(", TotalIncome=" + NumberFormat.getCurrencyInstance().format(getdTotalIncome()));
		sbOutput.append(", AVC account=" + accPensionPot.getName());
		sbOutput.append("\nPension Contribution=" + NumberFormat.getCurrencyInstance().format(dPensionAmnt));
		sbOutput.append(", Employer Contribution=" + NumberFormat.getCurrencyInstance().format(dEmployerPenAmnt));
		sbOutput.append(("\n----------------------------------\n"));
		return sbOutput.toString();
	}

	public double getPensionAmnt() {
		return dPensionAmnt;
	}

	public double getEmployerAmnt() {
		return dEmployerPenAmnt;
	}

	public void setPensionAmnt(double dPensAmnt) {
		this.dPensionAmnt = dPensAmnt;

	}

	public void setEmployerPenAmnt(double dEmployerAmnt) {
		this.dEmployerPenAmnt = dEmployerAmnt;

	}

	public void setYearTotaltaxedInterestDiv(LocalDate date) {
		accounts.forEach((account) -> addTotalfromSavDiv(account, date));
	}



	private void addTotalfromSavDiv(AccountAbstract account, LocalDate dtTxYrStart) {
		TaxedAccount taxed;
		AccountShares shares;
		if (account instanceof AccountShares) {
			shares = (AccountShares) account;
			this.dDividend = this.dDividend + shares.getdDividend(dtTxYrStart);
		}
		if (account instanceof TaxedAccount) {
			taxed = (TaxedAccount) account;
			this.dInterest = this.dInterest + taxed.getInterest();
		}

	}

	public boolean isBond() {
		return this.boolHaveBond;
	}

	public boolean isSIPP() {
		return boolHaveSIPP;
	}

	public PensionAccount getAccSIPP() {
		return accSIPP;
	}

	public void addChargeEventOn(double charge) {
		this.dBondOnIncome = charge;

	}
	public void addChargeEventOff(double charge) {
		this.dBondOffIncome = charge;

	}
	public double getBondIncome() {
		return this.dBondOffIncome;
	}

	public void setBondIncome(double d) {
		this.dBondOffIncome = d;
	}


	public TaxForm getTaxForm(LocalDate txYearStart) {
		double dSharesGain = 0.0;
		dTaxableIncome = 0.0;
		dRentalIncome = 0.0;
		streams.forEach(stream -> {
			// proportion of stream within the tax year
			double dPropn = DateLogic.calcPropInTaxYear(stream.getdateStart(), stream.getEndDate(), txYearStart);
			double dStipend = stream.getdStipend(txYearStart)* dPropn;
			int iYears = Math.max(0, txYearStart.getYear()-stream.getdateStart().getYear());
			double dInflation = Math.pow(1+stream.getRate(), iYears+1.0);
			// calculate taxable income from streams
			if (stream instanceof EmploymentStream || stream instanceof PensionStream) {
				dTaxableIncome += dStipend*dPropn*dInflation;
			}
			// calculate rental income from streams
			if (stream instanceof RentalStream) {
				dRentalIncome += dStipend*dPropn*dInflation;
			}		
		});
		accounts.forEach(account -> {
			// calculate interest from accounts
			dInterest = 0.0;
			dDividend = 0.0;
			dBondOffIncome = 0.0;
			dBondOnIncome = 0.0;
			if (account instanceof TaxedAccount) {
				dInterest += ((TaxedAccount) account).getInterest();	
			}
			// calculate dividend from shares account
			if (account instanceof AccountShares) {
				dDividend += ((AccountShares)account).getdDividend(txYearStart);	
			}
			// calculate off-shore bond gain
			if (account instanceof AccOffBond) {
				dBondOffIncome += ((AccOffBond)account).getCharge(txYearStart);
			}
			// calculate on-shore bond gain
			if (account instanceof AccOnBond) {
				dBondOnIncome += ((AccOnBond)account).getCharge(txYearStart);
			}
		});
		// calculate shares gain
		if (this.boolHaveShares) {
			dSharesGain = accShares.getdGain();
		}

		this.taxform = new TaxForm(dTaxableIncome, dRentalIncome, dInterest, dDividend, dSharesGain, dBondOffIncome,
				dBondOnIncome);
		return this.taxform;
	}

	public void setPensionIncome(double d) {
		this.dRentalIncome = d;
	}

	public double getInterest() {
		return this.dInterest;
	}
	public double getDividend() {
		return this.dDividend;
	}

	public ArrayList<BondAccount> getBonds() {
		ArrayList<BondAccount> bonds = new ArrayList<BondAccount>();
		accounts.forEach(account -> {
			if (account instanceof BondAccount) {
				bonds.add((BondAccount)account);
			}
		});
	return bonds;
	}

	public double getNetIncome() {
		return this.dNetIncome;
	}

	public void setNetIncome(double d) {
		this.dNetIncome = d;
	}

	public ISAaccount getISA() {
		return this.accISA;
	}

	public boolean isISA() {
		return this.boolHaveISA;
	}

}
