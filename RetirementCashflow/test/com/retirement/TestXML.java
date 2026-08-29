package com.retirement;


import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;


class TestXML {
	
	private final PensionAccount accFordStandard = new PensionAccount("accAvivaJohn", 85687.5, 0.03);
	private final EmploymentStream streamBankPen = new EmploymentStream("* BankPen *", LocalDate.of(2035,11,14), LocalDate.of(2100, 1, 1), 6911.16, 0.04);
	
	
	File file = new File("src/resources/highInflation_jdom.xml");

	@Test
	public void whenCorrectInfoFromFile_thenSuccess() {
		InputDataFromFile idffData = new InputDataFromFile(file);
		Person person = idffData.getListPeople().get(0);
		PensionAccount acc = person.getPensionAccount();
		EmploymentStream  stream = (EmploymentStream)person.getStreams().get(0);
		//assertThat(acc).usingRecursiveComparison().isEqualTo(accFordStandard);
		//assertSame(acc, accFordStandard);
		assertEquals(acc.getName(),accFordStandard.getName());
		assertEquals(acc.getdBalance(),accFordStandard.getdBalance());
		assertEquals(acc.getdRate(),accFordStandard.getdRate());
		assertEquals(acc.isEarnings(),accFordStandard.isEarnings());
		assertEquals(acc.isTaxInterest(),accFordStandard.isTaxInterest());
		//assertEquals(acc,accFordStandard);
		//assertEquals(stream,streamBankPen);
	}
}
