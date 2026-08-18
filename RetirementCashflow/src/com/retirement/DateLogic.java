package com.retirement;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateLogic {
	public static double calcPropInCalYear(LocalDate dateStart, LocalDate dateEnd, LocalDate date) {
		return calcPropBetweenDates(dateStart,dateEnd,LocalDate.of(date.getYear(), 1, 1),LocalDate.of(date.getYear(), 12, 31));
	}
	public static double calcPropInTaxYear(LocalDate dateStart, LocalDate dateEnd,LocalDate dateTaxYrStart) {
		return calcPropBetweenDates(dateStart,dateEnd,dateTaxYrStart,dateTaxYrStart.plusYears(1));
	}
	
	private static double calcPropBetweenDates(LocalDate dateEventStart, LocalDate dateEventEnd,LocalDate datePeriodStart,LocalDate datePeriodEnd) {
		// calculates the proportion of a period intersected with an event.
		double dProp = -1.0;
		if (dateEventStart.isBefore(datePeriodStart) && dateEventEnd.isAfter(datePeriodEnd)) {
			dProp = 1.0; // Period fully covers event
		}else {
			long periodLength = ChronoUnit.DAYS.between(datePeriodEnd, datePeriodStart);
			long eventDaysInPeriod=0;
			if (dateEventStart.isBefore(datePeriodStart) && dateEventEnd.isBefore(datePeriodEnd)) {
				eventDaysInPeriod = ChronoUnit.DAYS.between(dateEventEnd, datePeriodStart);  	
			}
			if (dateEventStart.isAfter(datePeriodStart) && dateEventEnd.isAfter(datePeriodEnd)) {
				eventDaysInPeriod = ChronoUnit.DAYS.between(datePeriodEnd,dateEventStart);  		
			}
			if (dateEventStart.isAfter(datePeriodStart) && dateEventEnd.isBefore(datePeriodEnd)) {
				eventDaysInPeriod = ChronoUnit.DAYS.between(dateEventEnd,dateEventStart);  		
			}
			dProp = (double)eventDaysInPeriod / (double)periodLength;	
			if (dateEventStart.isAfter(datePeriodEnd)) {
				dProp = 0.0; // event will not start until after the period end
			}
			if (dateEventEnd.isBefore(datePeriodStart)) {
				dProp = 0.0; // event already ended before period starts
			}
		}
		return dProp;
	}
}