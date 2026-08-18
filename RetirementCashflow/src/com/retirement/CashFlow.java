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
	private double dTotalNetIncome;
	private LocalDate dateInstantaneous;
	private Person personInstantaneous;
	private TaxParams txParams;
	private NIParams niParams;
	private List<Person> people;
	private StringBuffer sbOut = new StringBuffer();
	private StringBuffer sbBalances = new StringBuffer();
	private double dPersonTaxable;
	private double dPersonNonTaxable;

	@SuppressWarnings("unchecked")
	public CashFlow(List<Person> people, double dBudget, double dInflation, LocalDate dateStart, TaxParams txParams,
			NIParams niParams) {
		this.people = people;
		this.dBudget = dBudget;
		this.dInflation = dInflation;
		this.dateStart = dateStart;
		this.txParams = txParams;
		this.niParams = niParams;
		// initialise dateInstantaneous to allow getFundingGap to work
		this.dateInstantaneous = dateStart;
		people.forEach((person) -> {
			person.getAccounts().forEach((account) -> {
				account.setHolder(person);
				accounts.add(account);
			});
		});
 		origAccounts = (ArrayList<AccountAbstract>) accounts.clone(); // sort and operate on accounts and leave origAccounts for tracking balances
		sbBalances.append("Date" + "\t");
 		origAccounts.forEach((account) -> {
			sbBalances.append(account.getName()+ "\t");

		});
		sbBalances.append("\n" + dateStart + "\t");
		origAccounts.forEach((account) -> {
			sbBalances.append(NumberFormat.getCurrencyInstance().format(account.getdBalance()) + "\t");

		});
		
	}

	private void rationaliseAccounts(double dGap) throws Exception {
		while (dGap != 0) {
			// if(dNetWorth <=0.0)throw new Exception("funds depleted!");
			if (dGap <= 0) {
				// shows a surplus so choose best account to add into based on the best rate
				Collections.sort(accounts);
				AccountAbstract acc = accounts.get(accounts.size() - 1); // the account paying the most interest
				acc.deposit(-dGap, dateInstantaneous);
				//System.out.println(acc);
				dGap = 0;
			} else {
				// choose best account to take from based on the worst rate
				Collections.sort(accounts);
				if (accounts.size() == 0) {
					break;
				}
				AccountAbstract acc = accounts.get(0);
				double dAccBal = acc.getdBalance();
				double dTaxableIncome = acc.getHolder().getTaxableIncome();
				if (acc.isEarnings())
					dGap = TaxNI.calcGrossFromNet(dTaxableIncome, dGap, txParams);
				    //TODO not the best solution, better to create a tax return for the person and pay tax at year end.
				if (dAccBal >= dGap) {// get balance and if > dGap use full amount.
					acc.withdraw(dGap, dateInstantaneous);
					dGap = 0;
				} else {// if balance is less than full amount, remove all funds and delete account from
						// list
					acc.withdraw(dAccBal, dateInstantaneous);
					if (accounts.remove(acc)) {
						// account successfully removed
					}else {
						break;
					}
					//sbOut.append("account removed from List:" + acc.getName());
					acc.close(dateInstantaneous);
					dGap = dGap - dAccBal;
				}
			}

		}
		// transfer any unused ISA allowances to ISAs
		depositToISAs();
		calcNetWorth(false);
		sbOut.append(":Net worth after rationalising accounts:" + NumberFormat.getCurrencyInstance().format(dNetWorth));
		sbBalances.append("\n" + dateInstantaneous+ "\t");
		origAccounts.forEach((account) -> {
			sbBalances.append(NumberFormat.getCurrencyInstance().format(account.getdBalance()) + "\t");

		});
	}

	private void depositToISAs() {
		
		people.forEach((person) -> {
			dPersonTaxable=0;
			dPersonNonTaxable=0;
			person.getAccounts().forEach((account) -> {
				if (account instanceof ISAaccount){
					dPersonNonTaxable = dPersonNonTaxable + account.getdBalance();
				} else {
					dPersonTaxable = dPersonTaxable + account.getdBalance();
				}
			});
			if(dPersonTaxable > txParams.getISAlimit()) {
				
			}
			
			
			
		});
		
	}

	public double getResidual(LocalDate dateEnd) throws Exception {
		for (LocalDate date = dateStart; date.isBefore(dateEnd); date = date.plusYears(1)) {
			// loop through all the years in the term
			// dateStart set in the constructor
			// at the loop end when accounts are settled it assumes the date is year end
			dateInstantaneous = date; // sets the dateInstantaneous field for other functions
			calcNetWorth(false);
			Collections.sort(accounts); // sort on interest rate so the lowest rate account is used
			calcTotalNetIncome();
			double dGap = dBudget - dTotalNetIncome; // NB this is still at dateStart for the 1st iteration
			
			// set date to the end of the year before rationalising accounts
			dateInstantaneous = LocalDate.of(date.getYear(),12,31);
			rationaliseAccounts(dGap); 
			inflateAll(date);// slightly pessimistic because it takes off money before applying interest
		}
		calcNetWorth(false);
		//sbOut.append(dateInstantaneous + " End date Net Worth:" + NumberFormat.getCurrencyInstance().format(dNetWorth) + "\n");
		closeAllAccounts(dateEnd);
		System.out.println(sbOut.toString());
		System.out.println("\n\n\n" + sbBalances.toString());
		return dNetWorth;
	}

	public double runMaxEarnings(LocalDate dateEnd) throws Exception {
		for (LocalDate date = dateStart; date.isBefore(dateEnd); date = date.plusYears(1)) {
			// loop through all the years in the term
			// date set at January the first of the year
			// at the loop end when accounts are settled it assumes the date is year end
			dateInstantaneous = date;
			Collections.sort(accounts);
			calcNetWorth(true);
			calcTotalNetIncome();
			sbOut.append(":Years Net Income:" + NumberFormat.getCurrencyInstance().format(dTotalNetIncome));
			double dGap = dNetWorth / (dateEnd.getYear() - dateInstantaneous.getYear());

			rationaliseAccounts(dGap);

			double dSpendPower = dGap + dTotalNetIncome;
			sbOut.append(":Years Spending power:" + NumberFormat.getCurrencyInstance().format(dSpendPower)+ "\n");
			inflateAll(date);
		}
		calcNetWorth(false);
		sbOut.append("End date Net Worth:" + NumberFormat.getCurrencyInstance().format(dNetWorth));
		closeAllAccounts(dateEnd);
		System.out.println(sbOut.toString());
		return dNetWorth;
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
		people.forEach((person) -> {
			person.getStreams().forEach((stream) -> {
				stream.inflate();
			});
		});
		if(date.isAfter(txParams.getFrozenTh())) {
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

	private void calcTotalNetIncome() {
		// need everyone's taxable income, then the net income, then add up
		this.dTotalNetIncome = 0.0;
		sbOut.append("\n ----Running calc for date:" + dateInstantaneous);
		sbOut.append(":Budget:" + NumberFormat.getCurrencyInstance().format(dBudget));
		// loop through all streams to add the total earnings for each person
		// streams.forEach((stream) -> addTotal(stream));

		people.forEach((person) -> {
			this.personInstantaneous = person;
			// income amounts need to be initialised to stop calculating cumulative amounts
			person.setNIableIncome(0.0);
			person.setTaxableIncome(0.0);
			person.setdTotalIncome(0.0);
			person.getStreams().forEach((stream) -> addTotal(stream));
			double dTaxed = TaxNI.calcTax(person.getTaxableIncome(), txParams);
			double dNId = TaxNI.calcNI(person.getNIableIncome(), niParams);
			dTotalNetIncome = dTotalNetIncome + person.getdTotalIncome() - (dTaxed + dNId);

			
			//TODO need to delete addTotal and use the function in Person() to get gross stream earnings
			// then add in any extra from pensions and bonds to get to HTR threshold if possible
			// need to simulate a tax return ArrayList in Person() for each tax year and then read off tax for the relevant year
			
			
			
		});
		sbOut.append("---Total Net income:" + NumberFormat.getCurrencyInstance().format(dTotalNetIncome));
		if(people.get(0).getdTotalIncome() > txParams.getTaxHigh())sbOut.append("---HIGH TAX!!" + people.get(0).getStrName()) ;
		if(people.get(1).getdTotalIncome() > txParams.getTaxHigh())sbOut.append("---HIGH TAX!!"+ people.get(1).getStrName()) ;
		// System.out.print("\nTotal net income: " +
		// NumberFormat.getCurrencyInstance().format(dTotalNetIncome));
		// System.out.print(" Budget: " +
		// NumberFormat.getCurrencyInstance().format(dBudget));
		// System.out.println(" gap: " +
		// NumberFormat.getCurrencyInstance().format(dBudget - dTotalNetIncome));

	}

	private void addTotal(IncomeStream stream) { // forward looking for the whole year
		double dStipend = stream.getdStipend();
		double dProportion = DateLogic.calcPropInCalYear(stream.getdateStart(), stream.getEndDate(), dateInstantaneous);
		double dEarning = dStipend * dProportion;
		double dTaxable = this.personInstantaneous.getTaxableIncome();
		double dNIable = this.personInstantaneous.getNIableIncome();

		if (stream.isTaxable()) {
			dTaxable = dTaxable + dEarning;
			this.personInstantaneous.setTaxableIncome(dTaxable);
		}
		if (stream.isLiableNI()) {
			dNIable = dNIable + dEarning;
			this.personInstantaneous.setNIableIncome(dNIable);
		}
		if (stream.isEmployment()) {
			double dPensAmnt = personInstantaneous.getPensionAmnt() * dProportion;
			double dEmployerAmnt = personInstantaneous.getEmployerAmnt() * dProportion;
			personInstantaneous.setPensionAmnt(dPensAmnt);
			personInstantaneous.setEmployerPenAmnt(dEmployerAmnt);
			dTaxable = dTaxable - dPensAmnt;
			PensionAccount account = personInstantaneous.getPensionAccount();
			account.deposit((dEmployerAmnt + dPensAmnt), dateInstantaneous);
			this.personInstantaneous.setTaxableIncome(dTaxable);
			dEarning = dEarning - dPensAmnt;
		}
		double dCummulitive = this.personInstantaneous.getdTotalIncome() + dEarning;
		this.personInstantaneous.setdTotalIncome(dCummulitive);
		/*
		 * 
		 * System.out.print(stream.getName() + "\t" + stream.getdateStart() + "\t" +
		 * stream.getEndDate() + "\t" + " stipend:" +
		 * NumberFormat.getCurrencyInstance().format(dStipend) + "\t");
		 * System.out.print(" Taxable :" +
		 * NumberFormat.getCurrencyInstance().format(dTaxable) + "\t");
		 * System.out.print(" NIable  :" +
		 * NumberFormat.getCurrencyInstance().format(dNIable) + "\t");
		 * System.out.print(" Earnings:" +
		 * NumberFormat.getCurrencyInstance().format(dCummulitive) + "\t");
		 * System.out.print("Proportion:"); System.out.printf("%.3f%n", dProportion);
		 * 
		 */

	}

}
