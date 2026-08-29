package com.retirement;

import java.time.LocalDate;

public class EmploymentStream extends StreamAbstract {

	public EmploymentStream(String strName, LocalDate dateStart, LocalDate dateEnd, double dStipend, double dRate) {
		super(strName, dateStart, dateEnd, dStipend, dRate);
		super.setIsTaxable(true);
		super.setIsNIable(true);
		super.setIsEmployment(true);
	}

}
