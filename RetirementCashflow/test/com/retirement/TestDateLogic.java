package com.retirement;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class TestDateLogic {

	private final LocalDate dateEnd = LocalDate.of(2024, 3, 1);
	private final LocalDate dateStart = LocalDate.of(2016, 11, 30);
	private final LocalDate firstDate = LocalDate.of(2012, 1, 1);
	private final LocalDate lastDate = LocalDate.of(2030, 1, 1);
	private final double dResults[] = { 0.0, 0.0, 0.0, 0.0, 0.0847, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.1666, 0.0, 0.0,
			0.0, 0.0, 0.0 };

	@Test
	void test() {
		int iCount = 0;
		for (LocalDate date = firstDate; date.isBefore(lastDate); date = date.plusYears(1)) {
			double dInstant = DateLogic.calcProportion(dateStart, dateEnd, date);
			assertEquals(dResults[iCount], dInstant, 0.01);
			System.out.println(date);
			iCount++;
		}
	}
}