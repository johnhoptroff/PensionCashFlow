package com.retirement;

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
				accounts.add(account);
			});
		});
	}

	public double getResidual(LocalDate dateEnd) {
		for (LocalDate date = dateStart; date.isBefore(dateEnd); date = date.plusYears(1)) {
			dateInstantaneous = date; // loop through all the years in the term
			double dGap = getFundingGap();
			calcNetWorth();
			System.out.println("Net Worth before:" + dNetWorth);

			// TODO need to think about which date to use, could have concept that it's a
			// full year
			// budget with accrual and deposits at the end
			if (dGap <= 0) {
				// choose best account to add into based on the best rate
				Collections.sort(accounts);
				Account acc = accounts.get(accounts.size() - 1);
				acc.deposit(-dGap, dateInstantaneous);
				System.out.println(acc);
			} else {
				// choose best account to take from based on the worst rate
				Collections.sort(accounts);
				Account acc = accounts.get(0);
				double dAccBal = acc.getdBalance();
				if (dAccBal >= dGap) {// get balance and if > dGap use full amount.
					acc.withdraw(dGap, dateInstantaneous);
				} else {// if balance is less than full amount, remove all funds and delete account from
						// list
					acc.withdraw(dAccBal, dateInstantaneous);
					if (accounts.remove(acc))
						System.out.println("element removed " + acc.getName());
					acc = accounts.get(0); // since list is sorted this is the next worst interest account
					acc.withdraw((dGap - dAccBal), dateInstantaneous); // make up amount to take from next account.
				}

			}
			// TODO need to think about what happens in the end, does the last account go
			// overdrawn?
			
			inflateAll();
		}
		calcNetWorth();
		System.out.println("Net Worth after:" + dNetWorth);
		return dNetWorth;
	}

	private void inflateAll() {
		this.dBudget *= (1 + dInflation);
		accounts.forEach((account) -> {
			account.addInterest();
		});
		people.forEach((person) -> {
			person.getStreams().forEach((stream) -> {
				stream.inflate();
			});
		});
		//TODO need to inflate tax and NI thresholds
	}

	private void calcNetWorth() {
		dNetWorth = 0;
		accounts.forEach((account) -> {
			dNetWorth = dNetWorth + account.getdBalance();
		});
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
			person.setNIableIncome(0.0);
			person.setTaxableIncome(0.0);
			person.getStreams().forEach((stream) -> addTotal(stream));
			double dTaxed = TaxNI.calcTax(person.getTaxableIncome(), txParams);
			double dNId = TaxNI.calcNI(person.getNIableIncome(), niParams);
			dTotalNetIncome = dTotalNetIncome + person.getTaxableIncome() - (dTaxed + dNId);
			System.out.println(person + "Tax paid:" + dTaxed + " NI paid:" + dNId + " Total stops:" + (dTaxed + dNId));
		});
		System.out.println("\nTotal net income: " + dTotalNetIncome + " Budget: " + dBudget + " gap: "
				+ (dBudget - dTotalNetIncome));
		return (dBudget - dTotalNetIncome);
	}

	private void addTotal(IncomeStream stream) { // forward looking for the whole year
		double dStipend = stream.getdStipend();
		double dProportion = DateLogic.calcProportion(stream.getdateStart(), stream.getEndDate(), dateInstantaneous);
		double dEarning = dStipend * dProportion;
		double dTaxable = this.personInstantaneous.getTaxableIncome();
		double dNIable = this.personInstantaneous.getNIableIncome();
		if(stream.isTaxable()) {
			dTaxable += dEarning; 
			this.personInstantaneous.setTaxableIncome(dTaxable);
		}
		if(stream.isLiableNI()) {
			dNIable += dEarning;
			this.personInstantaneous.setNIableIncome(dNIable);
		}
		
		String strStip = String.format("%9.2f", dStipend);

		System.out.print(
				stream.getName() + "\t" + stream.getdateStart() + "\t" + stream.getEndDate() + "\t" + " stipend: £" + strStip + "\t");
		System.out.print("Proportion:");
		System.out.printf("%.3f%n", dProportion);

	}

}
