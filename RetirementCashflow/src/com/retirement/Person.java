package com.retirement;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;

public class Person {
	private String strName;
	private LocalDate dateBDay;
	private Double dTaxableIncome = 0.0;
	private Double dRentalIncome = 0.0;
	private Double dBondIncome = 0.0;
	private Double dChargeable = 0.0;
	private Double dInterest = 0.0;
	private Double dDividend = 0.0;
	private Double dNIableIncome = 0.0;
	private Double dTotalIncome = 0.0;
	private Double dPensionAmnt = 0.0;
	private Double dEmployerPenAmnt = 0.0;
	private List<IncomeStream> streams;
	private List<AccountAbstract> accounts;
	private PensionAccount accPensionPot;
	private boolean boolHaveBond;
	private boolean boolHaveSIPP;
	private boolean boolHaveShares;
	private BondAccount accBond;
	private PensionAccount accSIPP;
	private TaxForm taxform;
	private AccountShares accShares;

	/*
	 * private double dTaxableIncome; private double dInterest; private double
	 * dDividend; private double dBondIncome; private double dSharesGain; private
	 * double dChargeable;
	 */

	public Person(String strName, LocalDate dateBDay, List<IncomeStream> streams, List<AccountAbstract> accounts,
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
				this.accBond = (BondAccount) account;
			}
			if (account instanceof PensionAccount) {
				this.boolHaveSIPP = true;
				this.accSIPP = (PensionAccount) account;
			}
			if (account instanceof AccountShares) {
				this.boolHaveShares = true;
				this.accShares = (AccountShares) account;
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
		return dTotalIncome;
	}

	public void setdTotalIncome(Double dTotalIncome) {
		this.dTotalIncome = dTotalIncome;
	}

	public PensionAccount getPensionAccount() {
		return this.accPensionPot;
	}

	public List<IncomeStream> getStreams() {
		return this.streams;

	}

	@Override
	public String toString() {
		StringBuffer sbOutput = new StringBuffer("----------------------------------\n");
		sbOutput.append(strName + ", Birthday=" + dateBDay + ", TaxableIncome="
				+ NumberFormat.getCurrencyInstance().format(dTaxableIncome));
		sbOutput.append(", NIableIncome=" + NumberFormat.getCurrencyInstance().format(dNIableIncome));
		sbOutput.append(", TotalIncome=" + NumberFormat.getCurrencyInstance().format(dTotalIncome));
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

	public double getTaxYearStreamsTotal(LocalDate dtTxYrStart) {
		this.setNIableIncome(0.0);
		setTaxableIncome(0.0);
		setdTotalIncome(0.0);
		this.streams.forEach((stream) -> addTotalfromStream(stream, dtTxYrStart));
		return this.dTotalIncome;
	}

	private void addTotalfromStream(IncomeStream stream, LocalDate dtTxYrStart) {
		double dStipend = stream.getdStipend();

		// proportion of stream within the tax year
		double dPropn = DateLogic.calcPropInTaxYear(stream.getdateStart(), stream.getEndDate(), dtTxYrStart);
		double dEarning = dStipend * dPropn;

		if (stream.isTaxable()) {
			dTaxableIncome = dTaxableIncome + dEarning;
		}
		if (stream.isLiableNI()) {
			dNIableIncome = dNIableIncome + dEarning;
		}
		if (stream.isEmployment()) {
			double dPensAmnt = getPensionAmnt() * dPropn;
			double dEmployerAmnt = getEmployerAmnt() * dPropn;

			dTaxableIncome = dTaxableIncome - dPensAmnt;
			this.accPensionPot.deposit((dEmployerAmnt + dPensAmnt), dtTxYrStart);
			dEarning = dEarning - dPensAmnt;
		}

		double dCummulitive = this.getdTotalIncome() + dEarning;
		this.setdTotalIncome(dCummulitive);

	}

	public double getTotaltaxedInterestDiv(LocalDate date) {

		accounts.forEach((account) -> addTotalfromAccount(account, date));
		return 0;
	}

	private void addTotalfromAccount(AccountAbstract account, LocalDate dtTxYrStart) {
		TaxedAccount taxed;
		AccountShares shares;
		if (account instanceof TaxedAccount) {
			taxed = (TaxedAccount) account;
			this.dInterest = this.dInterest + taxed.getInterest();
		}

		if (account instanceof AccountShares) {
			shares = (AccountShares) account;
			this.dDividend = this.dDividend + shares.getdDividend();
		}

	}

	public boolean isBond() {
		return this.boolHaveBond;
	}

	public BondAccount getAccBond() {
		return accBond;
	}

	public boolean isSIPP() {
		return boolHaveSIPP;
	}

	public PensionAccount getAccSIPP() {
		return accSIPP;
	}

	public void addChargeableEvent(double charge) {
		this.dChargeable = charge;

	}

	public double getBondIncome() {
		return this.dBondIncome;
	}

	public void setBondIncome(double d) {
		this.dBondIncome = d;
	}

	private TaxForm getTaxform() {
		return taxform;
	}

	public TaxForm getTaxForm() {
		double dSharesGain =0.0;
		if(this.boolHaveShares) {
			dSharesGain = accShares.getdGain();
		}
		this.taxform = new TaxForm(dTaxableIncome, dRentalIncome, dInterest, dDividend, dBondIncome, dSharesGain, dChargeable);
		return this.getTaxform();
	}

	public void setPensionIncome(double d) {
		this.dRentalIncome = d;
	}

}
