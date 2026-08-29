package com.retirement;

import java.time.LocalDate;

public class PensionStream extends StreamAbstract {

	public PensionStream(String strName, LocalDate dateStart, LocalDate dateEnd, double dStipend, double dRate) {
		super(strName, dateStart, dateEnd, dStipend, dRate);
		super.setIsTaxable(true);
		super.setIsNIable(false);
		super.setIsEmployment(false);
		super.setIsTaxable(true);
		super.setIsNIable(false);
		super.setIsEmployment(false);
	}

}
