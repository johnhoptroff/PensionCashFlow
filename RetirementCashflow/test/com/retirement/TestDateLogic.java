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
	void testInCal() {
		int iCount = 0;
		for (LocalDate date = firstDate; date.isBefore(lastDate); date = date.plusYears(1)) {
			double dInstant = DateLogic.calcPropInCalYear(dateStart, dateEnd, date);
			assertEquals(dResults[iCount], dInstant, 0.01);
			System.out.println(date);
			iCount++;
		}
	}
	@Test
	void testEventEndsInTaxYr() {
		LocalDate dateStreamStart = LocalDate.of(1986, 9, 30);
		LocalDate dateStreamEnd = LocalDate.of(2024, 6, 1);
		LocalDate dateTaxYrStart = LocalDate.of(2024, 4, 5);
		assertEquals(0.15616438, DateLogic.calcPropInTaxYear(dateStreamStart, dateStreamEnd, dateTaxYrStart), 0.0001);
	}
	@Test
	void testEventEndsInCalYr() {
		LocalDate dateStreamStart = LocalDate.of(1986, 9, 30);
		LocalDate dateStreamEnd = LocalDate.of(2024, 6, 1);
		LocalDate date = LocalDate.of(2024, 4, 5);
		assertEquals(0.416438, DateLogic.calcPropInCalYear(dateStreamStart, dateStreamEnd, date), 0.0001);
	}
	@Test
	void testEventEndsBeforePeriod() {
		LocalDate dateStreamStart = LocalDate.of(1986, 9, 30);
		LocalDate dateStreamEnd = LocalDate.of(2023, 6, 1);
		LocalDate date = LocalDate.of(2024, 4, 5);
		assertEquals(0.0, DateLogic.calcPropInCalYear(dateStreamStart, dateStreamEnd, date), 0.0001);
	}
	@Test
	void testEventStartAfterPeriod() {
		LocalDate dateStreamStart = LocalDate.of(2025, 9, 30);
		LocalDate dateStreamEnd = LocalDate.of(2027, 6, 1);
		LocalDate date = LocalDate.of(2024, 4, 5);
		assertEquals(0.0, DateLogic.calcPropInCalYear(dateStreamStart, dateStreamEnd, date), 0.0001);
	}
	@Test
	void testEventCoversPeriod() {
		LocalDate dateStreamStart = LocalDate.of(1986, 9, 30);
		LocalDate dateStreamEnd = LocalDate.of(2029, 6, 1);
		LocalDate date = LocalDate.of(2024, 4, 5);
		assertEquals(1.0, DateLogic.calcPropInCalYear(dateStreamStart, dateStreamEnd, date), 0.0001);
	}
	@Test
	void testEventContainedWithinPeriod() {
		LocalDate dateStreamStart = LocalDate.of(2024, 2, 28);
		LocalDate dateStreamEnd = LocalDate.of(2024, 10, 1);
		LocalDate date = LocalDate.of(2024, 4, 5);
		assertEquals(0.591781, DateLogic.calcPropInCalYear(dateStreamStart, dateStreamEnd, date), 0.0001);
	}
	@Test
	void testEventStartsWithinPeriod() {
		LocalDate dateStreamStart = LocalDate.of(2024, 4, 23);
		LocalDate dateStreamEnd = LocalDate.of(2030, 10, 1);
		LocalDate date = LocalDate.of(2024, 4, 5);
		assertEquals(0.690411, DateLogic.calcPropInCalYear(dateStreamStart, dateStreamEnd, date), 0.0001);
	}
}