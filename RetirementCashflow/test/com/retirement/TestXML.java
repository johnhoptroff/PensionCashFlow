package com.retirement;


import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;


class TestXML {
	
	private final Account accFordStandard = new Account("accAvivaJohn", 85687.5, 0.03, true);
	private final IncomeStream streamBankPen = new IncomeStream("* BankPen *", LocalDate.of(2035,11,14), LocalDate.of(2100, 1, 1), 6911.16, 0.04, true, false, false);
	
	
	File file = new File("src/resources/highInflation_jdom.xml");

	@Test
	public void whenCorrectInfoFromFile_thenSuccess() {
		InputDataFromFile idffData = new InputDataFromFile(file);
		Person person = idffData.getListPeople().get(0);
		Account acc = person.getPensionAccount();
		IncomeStream stream = person.getStreams().get(0);
		//assertThat(acc).usingRecursiveComparison().isEqualTo(accFordStandard);
		//assertSame(acc, accFordStandard);
		assertEquals(acc.getName(),accFordStandard.getName());
		assertEquals(acc.getdBalance(),accFordStandard.getdBalance());
		assertEquals(acc.getdRate(),accFordStandard.getdRate());
		assertEquals(acc.getisTaxable(),accFordStandard.getisTaxable());
		//assertEquals(acc,accFordStandard);
		//assertEquals(stream,streamBankPen);
	}
}
