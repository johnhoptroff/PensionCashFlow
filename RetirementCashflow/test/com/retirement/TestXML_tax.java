package com.retirement;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import org.junit.jupiter.api.Test;

class TestXML_tax {

	File file = new File("src/resources/taxparams.xml");
	double dLowerLimit = 12500.0;
	double dHighPaypc = 0.02;

	@Test
	public void whenCorrectInfoFromFile_thenSuccess() {
		InputTaxParamsFromFile itpffData = new InputTaxParamsFromFile(file);
		TaxParams txPars = itpffData.getTxPars();
		NIParams niPars = itpffData.getNiPars();

		assertEquals(txPars.getTaxLow(), dLowerLimit);
		assertEquals(niPars.getNIhighPaypc(), dHighPaypc);
	}
}
