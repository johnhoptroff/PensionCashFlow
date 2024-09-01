package com.retirement;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class TestSpendingProfile {

	@Test
	void test() {
		double dBudget = 52100.0;
		double dInflation = 0.05;
		double dIncrease = 10000.0;
		LocalDate dateKinkPoint = LocalDate.of(2038, 1, 1);
		LocalDate dateStart = LocalDate.of(2024, 1, 1);
		SpendingProfile spTarget = new SpendingProfile(dBudget,dInflation,dateStart,dateKinkPoint,dIncrease);
		assertEquals(62100.0, spTarget.getBudget(LocalDate.of(2024, 1, 1)),0.01);
		assertEquals(101154.36, spTarget.getBudget(LocalDate.of(2034, 1, 1)),0.01);
		assertEquals(108312.16, spTarget.getBudget(LocalDate.of(2039, 1, 1)),0.01);
		assertEquals(273699.43, spTarget.getBudget(LocalDate.of(2058, 1, 1)),0.01);
	}

}
