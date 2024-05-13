package com.retirement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.retirement.fileutils.LoadXML;

public class InputDataFromFile {
	private List<Person> listPeople = new ArrayList<Person>();
	private List<List<IncomeStream>> groupStreams = new ArrayList<List<IncomeStream>>(2);
	private List<List<Account>> groupAccounts = new ArrayList<List<Account>>(2);
	private File file;

	public InputDataFromFile(File file) {
		this.file = file;
		LoadXML fact = new LoadXML(file);
		Document doc = fact.getDoc();
		NodeList nodesPeople = doc.getElementsByTagName("person");
		for (int i = 0; i < nodesPeople.getLength(); i++) {
			Element eNode = (Element) nodesPeople.item(i);
			String name = eNode.getAttribute("name");
			String strDob = eNode.getAttribute("dob");
			String strPensionpot = eNode.getAttribute("pensionpot");
			String strPensionAmt = eNode.getAttribute("pension-amt");
			String strPensionAmtEmp = eNode.getAttribute("pension-amt-emp");
			
			System.out.println("Person: " + name);
			Person person = new Person(name, null, null, null);
			Element eElement = (Element) nodesPeople.item(i);
			NodeList accList = eElement.getElementsByTagName("account");
			for (int count = 0; count < accList.getLength(); count++) {
				Node node1 = accList.item(count);
				if (node1.getNodeType() == Node.ELEMENT_NODE) {
					Element acc = (Element) node1;

					String strDetails = acc.getTextContent();
					String[] strContents = strDetails.split("\n");
					String strBalance = strContents[1].replaceAll("\\s", "");
					String strRate = strContents[2].replaceAll("\\s", "");
					String strTaxable = strContents[3].replaceAll("\\s", "");

					System.out.print("account name : ");
					System.out.println(acc.getAttribute("name"));

					System.out.println("account details : ");
					System.out.println(strBalance);
					System.out.println(strRate);
					System.out.println(strTaxable);

				}
			}
			NodeList streamList = eElement.getElementsByTagName("stream");
			for (int count = 0; count < streamList.getLength(); count++) {
				Node node1 = streamList.item(count);
				if (node1.getNodeType() == Node.ELEMENT_NODE) {
					Element stream = (Element) node1;

					String strDetails = stream.getTextContent();
					String[] strContents = strDetails.split("\n");
					String strDateStart = strContents[1].replaceAll("\\s", "");
					String strDateEnd = strContents[2].replaceAll("\\s", "");
					String strOpBalance = strContents[3].replaceAll("\\s", "");
					String strRate = strContents[4].replaceAll("\\s", "");
					String strTaxable = strContents[5].replaceAll("\\s", "");
					String strNIable = strContents[6].replaceAll("\\s", "");
					String strIsEmployment = strContents[7].replaceAll("\\s", "");

					System.out.print("stream name : ");
					System.out.println(stream.getAttribute("name"));

					System.out.println("stream details : ");
					System.out.println(strDateStart);
					System.out.println(strDateEnd);
					System.out.println(strOpBalance);
					System.out.println(strRate);
					System.out.println(strTaxable);
					System.out.println(strNIable);
					System.out.println(strIsEmployment);

				}
			}
		}
	}

	public Object getAccouts() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getStreams() {
		// TODO Auto-generated method stub
		return null;
	}

}
