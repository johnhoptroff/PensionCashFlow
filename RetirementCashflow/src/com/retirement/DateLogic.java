package com.retirement;

import java.time.LocalDate;

public class DateLogic {
	public static double calcProportion(LocalDate dateStart, LocalDate dateEnd, LocalDate date) {
		double dProp = -1.0;
		if (date.isAfter(dateStart) && date.plusYears(1).isBefore(dateEnd)) {
			dProp = 1.0;
			// System.out.print("Stream active " + dProp + " ");
		} else {
			if (date.plusYears(1).isBefore(dateStart)) {
				dProp = 0.0;
				// System.out.print("Stream not started " + dProp + " ");
			}
			if (date.getYear() == dateStart.getYear()) {
				dProp = (date.lengthOfYear() - dateStart.getDayOfYear()) / (double) date.lengthOfYear();
				// System.out.print("Stream in first year " + dateStart.getDayOfYear() + " " + dProp + " ");
			}
			if (date.getYear() == dateEnd.getYear()) {
				dProp = (dateEnd.getDayOfYear()) / (double) date.lengthOfYear();
				// System.out.print("Stream in final year " + dateEnd.getDayOfYear() + " " + dProp + " ");
			}
			if (date.isAfter(dateEnd)) {
				dProp = 0.0;
				// System.out.print("Stream ended " + dateEnd.getDayOfYear() + " " + dProp + " ");
			}
		}
		return dProp;
	}
}