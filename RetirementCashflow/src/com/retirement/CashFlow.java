package com.retirement;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CashFlow {
	private ArrayList<AccountAbstract> accounts = new ArrayList<>();
	private ArrayList<AccountAbstract> origAccounts;
	private double dNetWorth;
	private double dBudget;
	private double dInflation;
	private LocalDate dateStart;
	// private double dTotalNetIncome;
	// private LocalDate dateInstantaneous;
	// private Person personInstantaneous;
	private TaxParams txParams;
	private NIParams niParams;
	private SuplimentalTaxParams suppParams;
	private List<Person> people;
	private StringBuffer sbOut = new StringBuffer();
	private StringBuffer sbBalances = new StringBuffer();

	@SuppressWarnings("unchecked")
	public CashFlow(List<Person> people, double dBudget, double dInflation, LocalDate dateStart, TaxParams txParams,
			NIParams niParams, SuplimentalTaxParams supParams) {
		this.people = people;
		this.dBudget = dBudget;
		this.dInflation = dInflation;
		this.dateStart = dateStart;
		this.txParams = txParams;
		this.niParams = niParams;
		// initialise dateInstantaneous to allow getFundingGap to work
		// this.dateInstantaneous = dateStart;
		people.forEach((person) -> {
			person.getAccounts().forEach((account) -> {
				account.setHolder(person);
				accounts.add(account);
			});
		});
		origAccounts = (ArrayList<AccountAbstract>) accounts.clone(); // sort and operate on accounts and leave
																		// origAccounts for tracking balances
		sbBalances.append("Date" + "\t");
		origAccounts.forEach((account) -> {
			sbBalances.append(account.getName() + "\t");

		});
		sbBalances.append("\n" + dateStart + "\t");
		origAccounts.forEach((account) -> {
			sbBalances.append(NumberFormat.getCurrencyInstance().format(account.getdBalance()) + "\t");

		});
		this.suppParams = supParams;

	}

	public double getResidual(LocalDate dateEnd) throws Exception {
		calcNetWorth(false);
		sbOut.append(
				"Starting Calculation: Initial Net Worth: " + NumberFormat.getCurrencyInstance().format(dNetWorth));
		for (LocalDate date = dateStart.plusYears(1); date.isBefore(dateEnd); date = date.plusYears(1)) {
			// loop through all the years in the term
			// dateStart set in the constructor
			// at the loop end when accounts are settled it assumes the date is year end
			resetTaxYears(date);
			calcNetWorth(false);
			rationaliseAccounts(date);
			inflateAll(date);// slightly pessimistic because it takes off money before applying interest
		}
		calcNetWorth(false);
		// sbOut.append(dateInstantaneous + " End date Net Worth:" +
		// NumberFormat.getCurrencyInstance().format(dNetWorth) + "\n");
		closeAllAccounts(dateEnd);
		System.out.println(sbOut.toString());
		System.out.println("\n\n\n" + sbBalances.toString());
		return dNetWorth;
	}

	private void resetTaxYears(LocalDate date) {
		people.forEach((person) -> {
			person.getAccounts().forEach((account) -> {
				if (account instanceof ISAaccount) {
					((ISAaccount) account).setTaxYear(date);
				}
			});
		});
		
	}

	private void rationaliseAccounts(LocalDate date) throws Exception {
		double dPaid = 0;
		double dChange = 0.0;
		Collections.sort(accounts); // sort on interest rate so the lowest rate account is used
		double dGap = dBudget - calcTotalNetIncome(date);
		// transfer any unused ISA allowances to ISAs
		while (dGap != 0) {
			// if(dNetWorth <=0.0)throw new Exception("funds depleted!");
			if (dGap > 0) {
				// choose best account to take from based on the worst rate
				Collections.sort(accounts);
				if (accounts.size() == 0) {
					break;
				}
				for (int i = 0; i < accounts.size(); i++) {
					AccountAbstract acc = accounts.get(i);
					TaxForm taxform = acc.getHolder().getTaxForm(date);
					if (acc instanceof TaxedAccount && (dPaid != dGap)) {
						dChange = acc.withdraw((dGap - dPaid), date);
						taxform.setInterest(acc.getdBalance() * acc.getdRate());
						dPaid += (dGap - dChange);
					}
					if (acc instanceof PensionAccount && (dPaid != dGap)) {
						dChange = acc.withdraw((dGap - dPaid), date);
						taxform.setPension((dGap - dPaid) - dChange);
						dPaid += ((dGap - dPaid) - dChange);
					}
					if (acc instanceof BondAccount && (dPaid != dGap)) {
						dChange = acc.withdraw((dGap - dPaid), date);
						taxform.setBondsCharge((BondAccount) acc, (dGap - dChange));
						dPaid += ((dGap - dPaid) - dChange);
					}
					boolean isOtherAcc = ((acc instanceof PremBondsAccount) || (acc instanceof AccountShares) || (acc instanceof AccountEmbargoed));
					if((dGap !=dPaid) && isOtherAcc) {
						dChange = acc.withdraw((dGap - dPaid), date);
						dPaid += ((dGap - dPaid) - dChange);	
					}
					if (acc instanceof ISAaccount && (dPaid != dGap)) {
						dChange = acc.withdraw((dGap - dPaid), date);
						dPaid += ((dGap - dPaid) - dChange);
					}
					if (acc.getdBalance() == 0.0) {
						//acc.close(date);
						//accounts.remove(acc);
					}
					//dGap = dGap - dPaid;
				}
				dGap = dBudget - (calcTotalNetIncome(date) + dPaid);

			} else { // shows a surplus so transfer to the best accounts using last accounts in list.
				dChange=0.0;
				boolean isGoodAccount;
				System.out.println("Surplus!!  " + date);
				Collections.sort(accounts);
				for (int i = (accounts.size() - 1); i >= 0; i--) {
					AccountAbstract acc = accounts.get(i);
					isGoodAccount = ((acc instanceof ISAaccount)|| (acc instanceof TaxedAccount) || (acc instanceof AccountEmbargoed));
					if( dGap != 0.0 && isGoodAccount) {
						dChange = acc.deposit((-dGap), date); // if the account cannot accept a deposit (ISA limit used, embargoed account)
						dGap = -dChange;
						System.out.println(acc.getName() + "  " + dGap);
					}
				}
				if(dGap != 0.0) {
					throw new Exception("unable to rationalise accounts");
				}
			}

		}
		depositToISAs(date);
		calcNetWorth(false);
		sbOut.append(":Net worth after rationalising accounts:" + NumberFormat.getCurrencyInstance().format(dNetWorth));
		sbBalances.append("\n" + date + "\t");
		origAccounts.forEach((account) -> {
			sbBalances.append(NumberFormat.getCurrencyInstance().format(account.getdBalance()) + "\t");

		});
	}

	private void depositToISAs(LocalDate date) {
		ArrayList<TaxedAccount> taxed = new ArrayList<>(); // only worthwhile taking money from taxed accounts
		double dTopup = txParams.getISAlimit();
		// double dTotalTaxed = 0.0;
		for (int i = 0; i < accounts.size(); i++) {
			AccountAbstract acc = accounts.get(i);
			if (acc instanceof TaxedAccount) {
				// dTotalTaxed += acc.getdBalance();
				taxed.add((TaxedAccount) acc);
			}
		}
		Collections.sort(taxed);
		double dShortfall = 0.0;
		double dRepay = 0.0;
		for (int i = 0; i < people.size(); i++) {
			Person person = people.get(i);
			if (person.isISA()) {
				ISAaccount isa = person.getISA();
				for (int j = 0; j < taxed.size(); j++) {
					TaxedAccount ta = taxed.get(j);
					dShortfall -= ta.withdraw(dTopup, date); // if there is not enough balance dShortfall is positive
					//whatever gets withdrawn from here needs to be paid into ISA
					dRepay = isa.deposit((dTopup + dShortfall), date); // if there is not enough ISA allowance for this tax year dRepay is positive
					if (dRepay != 0.0) {
						ta.deposit(dRepay, date);
					}
					if (ta.getdBalance() == 0.0) {
						//ta.close(date);
						//taxed.remove(ta);
						//accounts.remove(ta);
					}
				}
			}

		}

	}

	private void closeAllAccounts(LocalDate date) {
		accounts.forEach((account) -> {
			account.close(date);
		});

	}

	private void inflateAll(LocalDate date) {
		this.dBudget *= (1 + dInflation);
		accounts.forEach((account) -> {
			account.addInterest(date);
		});
		// streams are automatically inflated at rate in constructor
		if (date.isAfter(txParams.getFrozenTh())) {
			txParams.inflateParams(dInflation);
			niParams.inflateParams(dInflation);
		}

	}

	private void calcNetWorth(boolean isPrinted) {
		dNetWorth = 0;
		accounts.forEach((account) -> {
			if (isPrinted) {
				System.out.print(account.getName() + "\t"
						+ NumberFormat.getCurrencyInstance().format(account.getdBalance()) + "\t");
			}

			dNetWorth = dNetWorth + account.getdBalance();
		});
		if (isPrinted)
			System.out.println();
	}

	public double getdInflation() {
		return dInflation;
	}

	public void setdInflation(double dInflation) {
		this.dInflation = dInflation;
	}

	private double calcTotalNetIncome(LocalDate txYearEnd) {
		// need everyone's taxable income, then the net income, then add up
		double dTotalNetIncome = 0.0;
		sbOut.append("\n ----Running calc for date:" + txYearEnd);
		sbOut.append(":Budget:" + NumberFormat.getCurrencyInstance().format(dBudget));

		for (int i = 0; i < people.size(); i++) {
			// this.personInstantaneous = person;
			// income amounts need to be initialised to stop calculating cumulative amounts
			Person person = people.get(i);
			//person.setNIableIncome(0.0);
			//person.setTaxableIncome(0.0);

			// create person's tax form and calculate stoppages
			TaxForm taxForm = person.getTaxForm(txYearEnd); // TODO improvement if this is a map of Tax forms with tax
															// year as key
			double dIncomeTax = TaxNI.calcIncomeTax(taxForm, txParams, niParams, suppParams);
			// calculate total net income for person
			double dNId = TaxNI.calcNI(person.getNIableIncome(), niParams);
			person.setNetIncome(person.getdTotalIncome() - (dIncomeTax + dNId));
			dTotalNetIncome += dTotalNetIncome + person.getNetIncome();
			if (person.getdTotalIncome() > txParams.getTaxHigh())
				sbOut.append("---HIGH TAX!!" + people.get(0).getStrName());
		}

		sbOut.append("---Total Net income:" + NumberFormat.getCurrencyInstance().format(dTotalNetIncome));

		return dTotalNetIncome;
	}

}
