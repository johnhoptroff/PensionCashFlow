package com.retirement;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CashFlow {
	private List<Account> accounts = new ArrayList<Account>();
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
	}

	public double getResidual(LocalDate dateEnd) throws Exception {
		for (LocalDate date = dateStart; date.isBefore(dateEnd); date = date.plusYears(1)) {
			// loop through all the years in the term
			// date set at January the first of the year
			// at the loop end when accounts are settled it assumes the date is year end
			dateInstantaneous = date;
			double dGap = getFundingGap();
			Collections.sort(accounts);
			calcNetWorth(true);
			System.out.println("Net Worth at year beginning:" + NumberFormat.getCurrencyInstance().format(dNetWorth));
			while (dGap != 0) {
				if(dNetWorth <=0.0)throw new Exception("funds depleted!");
				if (dGap <= 0) {
					// shows a surplus so choose best account to add into based on the best rate
					Collections.sort(accounts);
					Account acc = accounts.get(accounts.size() - 1); // the account paying the most interest
					acc.deposit(-dGap, dateInstantaneous);
					System.out.println(acc);
					dGap = 0;
				} else {
					// choose best account to take from based on the worst rate
					Collections.sort(accounts);
					Account acc = accounts.get(0);
					double dAccBal = acc.getdBalance();
					double dTaxableIncome = acc.getHolder().getTaxableIncome();
					if(acc.isTaxable()) dGap = TaxNI.calcGrossFromNet(dTaxableIncome, dGap, txParams);
					if (dAccBal >= dGap) {// get balance and if > dGap use full amount.
						acc.withdraw(dGap, dateInstantaneous);
						dGap = 0;
					} else {// if balance is less than full amount, remove all funds and delete account from
							// list
						acc.withdraw(dAccBal, dateInstantaneous);
						if (accounts.remove(acc))
							System.out.println("element removed " + acc.getName());
						    acc.close(date);
						dGap = dGap - dAccBal;
					}

				}
				calcNetWorth(true);
				System.out.println("Net worth at year end:" + NumberFormat.getCurrencyInstance().format(dNetWorth));
			}
			inflateAll(date);
		}
		calcNetWorth(true);
		System.out.println("End date Net Worth:" + NumberFormat.getCurrencyInstance().format(dNetWorth));
		closeAllAccounts(dateEnd);
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
		txParams.inflateParams(dInflation);
		niParams.inflateParams(dInflation);
	}

	private void calcNetWorth(boolean isPrinted) {
		dNetWorth = 0;
		accounts.forEach((account) -> {
			if(isPrinted) {
				System.out.print(account.getName() + "\t" + NumberFormat.getCurrencyInstance().format(account.getdBalance()) + "\t");
			}
			
			dNetWorth = dNetWorth + account.getdBalance();
		});
		if(isPrinted) System.out.println();
	}

	public double getdInflation() {
		return dInflation;
	}

	public void setdInflation(double dInflation) {
		this.dInflation = dInflation;
	}

	private double getFundingGap() {
		// need everyone's taxable income, then the net income, then add up
		this.dTotalNetIncome = 0.0;
		System.out.println("=========================================================");
		System.out.println("Running calc for date:" + dateInstantaneous);
		System.out.println("Budget:" + dBudget);
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
			System.out.print(person + "Tax paid:" + NumberFormat.getCurrencyInstance().format(dTaxed));
			System.out.print(" NI paid:" + NumberFormat.getCurrencyInstance().format(dNId));
			System.out.println(" Total stops:" + NumberFormat.getCurrencyInstance().format(dTaxed + dNId));
		});
		System.out.print("\nTotal net income: " + NumberFormat.getCurrencyInstance().format(dTotalNetIncome));
		System.out.print(" Budget: " + NumberFormat.getCurrencyInstance().format(dBudget));
		System.out.println(" gap: " + NumberFormat.getCurrencyInstance().format(dBudget - dTotalNetIncome));
		return (dBudget - dTotalNetIncome);
	}

	private void addTotal(IncomeStream stream) { // forward looking for the whole year
		double dStipend = stream.getdStipend();
		double dProportion = DateLogic.calcProportion(stream.getdateStart(), stream.getEndDate(), dateInstantaneous);
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
			double dPensAmnt = personInstantaneous.getPensionAmnt()* dProportion;
			double dEmployerAmnt = personInstantaneous.getEmployerAmnt()* dProportion;
			personInstantaneous.setPensionAmnt(dPensAmnt);
			personInstantaneous.setEmployerPenAmnt(dEmployerAmnt);
			dTaxable = dTaxable - dPensAmnt;
			Account account = personInstantaneous.getPensionAccount();
			account.deposit((dEmployerAmnt + dPensAmnt), dateInstantaneous);
			this.personInstantaneous.setTaxableIncome(dTaxable);
			dEarning = dEarning - dPensAmnt;
		}
		double dCummulitive = this.personInstantaneous.getdTotalIncome() + dEarning;
		this.personInstantaneous.setdTotalIncome(dCummulitive);
		System.out.print(stream.getName() + "\t" + stream.getdateStart() + "\t" + stream.getEndDate() + "\t"
				+ " stipend:" + NumberFormat.getCurrencyInstance().format(dStipend) + "\t");
		System.out.print(" Taxable :" + NumberFormat.getCurrencyInstance().format(dTaxable) + "\t");
		System.out.print(" NIable  :" + NumberFormat.getCurrencyInstance().format(dNIable) + "\t");
		System.out.print(" Earnings:" + NumberFormat.getCurrencyInstance().format(dCummulitive) + "\t");
		System.out.print("Proportion:");
		System.out.printf("%.3f%n", dProportion);
		

	}

}
