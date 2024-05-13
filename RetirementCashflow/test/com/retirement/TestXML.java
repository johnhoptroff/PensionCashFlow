package com.retirement;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.LocalDate;


import com.retirement.fileutils.*;

import org.junit.jupiter.api.Test;
import org.w3c.dom.*;

class TestXML {
	
	private final Account accFordStandard = new Account("accRRShares", 47993, 0.02, false);
	private final IncomeStream streamBankPen = new IncomeStream("* BankPen *", LocalDate.of(2035,11,14), LocalDate.of(2100, 1, 1), 6911.16, 0.04, true, false, false);
	
	
	File file = new File("src/resources/highInflation_jdom.xml");
	LoadXML fact = new LoadXML(file);
	Document doc = fact.getDoc();

	/*
	 *
	 * @Test public void whenGetElementByTag_thenSuccess() { NodeList nodeList =
	 * doc.getElementsByTagName("person"); Node first = nodeList.item(0); NodeList
	 * kids = first.getChildNodes(); System.out.println(nodeList.getLength());
	 * assertEquals(15, kids.getLength()); assertEquals(Node.ELEMENT_NODE,
	 * first.getNodeType()); assertEquals("person", first.getNodeName()); }
	 * 
	 * @Test public void whenGetFirstElementAttributes_thenSuccess() { Node first =
	 * doc.getElementsByTagName("person").item(1); //NodeList attrList =
	 * doc.getElementsByTagName("tutorial"); NamedNodeMap attrList =
	 * first.getAttributes();
	 * 
	 * assertEquals(2, attrList.getLength());
	 * 
	 * assertEquals("persId", attrList.item(0).getNodeName()); assertEquals("02",
	 * attrList.item(0).getNodeValue());
	 * 
	 * assertEquals("type", attrList.item(1).getNodeName()); assertEquals("java",
	 * attrList.item(1).getNodeValue()); }
	 * 
	 * @Test public void whenTraverseChildNodes_thenSuccess() { Node first =
	 * doc.getElementsByTagName("person").item(1); NodeList nodeList =
	 * first.getChildNodes(); int n = nodeList.getLength(); Node current;
	 * 
	 * for (int i=0; i<n; i++) { current = nodeList.item(i);
	 * if(current.getNodeType() == Node.ELEMENT_NODE) { System.out.println(
	 * current.getNodeName() + ": " + current.getTextContent()); }else {
	 * System.out.println(i); } } }
	 * 
	 * 
	 */

	@Test
	public void whenCorrectInfoFromFile_thenSuccess() {
		InputDataFromFile idffData = new InputDataFromFile(file);
		assertEquals(idffData.getAccouts(),accFordStandard);
		assertEquals(idffData.getStreams(),streamBankPen);
	}
}
