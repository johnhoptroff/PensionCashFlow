package com.retirement;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.retirement.fileutils.LoadXML;

public class InputDataFromFile {
	private List<Person> listPeople = new ArrayList<Person>();
	//private List<List<IncomeStream>> groupStreams = new ArrayList<List<IncomeStream>>(2);
	//private List<List<Account>> groupAccounts = new ArrayList<List<Account>>(2);

	public InputDataFromFile(File file) {
		LoadXML fact = new LoadXML(file);
		Document doc = fact.getDoc();
		NodeList nodesPeople = doc.getElementsByTagName("person");
		for (int i = 0; i < nodesPeople.getLength(); i++) { // loop through each person
			List<AccountAbstract> accounts = new ArrayList<>();
			List<IncomeStream> streams = new ArrayList<IncomeStream>();
			PensionAccount accPen;
			Element eNode = (Element) nodesPeople.item(i);
			String name = eNode.getAttribute("name");
			String[] strDob = eNode.getAttribute("dob").split("/");
			LocalDate dateDOB = LocalDate.of(Integer.parseInt(strDob[2]),Integer.parseInt(strDob[1]),Integer.parseInt(strDob[0]));
			NodeList nlPension = eNode.getElementsByTagName("pension");
			Element ePensionInfo = (Element)nlPension.item(0);
			String strPenName = ePensionInfo.getAttribute("name");
			String strDetails = ePensionInfo.getTextContent();
			String[] strContents = strDetails.split("\n");
			
			double dPensionAmt = Double.parseDouble(strContents[1].replaceAll("\\s", ""));
			double dPensionAmtEmp = Double.parseDouble(strContents[2].replaceAll("\\s", ""));
			double dPensionpot = Double.parseDouble(strContents[3].replaceAll("\\s", ""));
			double dRate = Double.parseDouble(strContents[4].replaceAll("\\s", ""));
			boolean boolWithdrawTaxable = Boolean.parseBoolean(strContents[5].replaceAll("\\s", ""));
			boolean boolInterestTaxable = Boolean.parseBoolean(strContents[6].replaceAll("\\s", ""));
			accPen = new PensionAccount(strPenName, dPensionpot, dRate);
			accounts.add(accPen);

			Element eElement = (Element) nodesPeople.item(i);
			NodeList accList = eElement.getElementsByTagName("account");
			for (int count = 0; count < accList.getLength(); count++) { // loop through each account
				Node node1 = accList.item(count);
				if (node1.getNodeType() == Node.ELEMENT_NODE) {
					Element acc = (Element) node1;

					strDetails = acc.getTextContent();
					strContents = strDetails.split("\n");
					double dBalance = Double.parseDouble(strContents[1].replaceAll("\\s", ""));
					dRate = Double.parseDouble(strContents[2].replaceAll("\\s", ""));
					boolWithdrawTaxable = Boolean.parseBoolean(strContents[3].replaceAll("\\s", ""));

					System.out.print("account name : ");
					System.out.println(acc.getAttribute("name"));

					System.out.println("account details : ");
					System.out.println(dBalance);
					System.out.println(dRate);
					System.out.println(boolWithdrawTaxable);
					
					// add account here
					accounts.add(new AccountAbstract(acc.getAttribute("name"), dBalance, dRate));
				}
			}
			NodeList streamList = eElement.getElementsByTagName("stream");
			for (int count = 0; count < streamList.getLength(); count++) { // loop through each stream
				Node node1 = streamList.item(count);
				if (node1.getNodeType() == Node.ELEMENT_NODE) {
					Element stream = (Element) node1;

					strDetails = stream.getTextContent();
					strContents = strDetails.split("\n");
					String[] strDateStart = strContents[1].replaceAll("\\s", "").split("/");
					LocalDate dateStart = LocalDate.of(Integer.parseInt(strDateStart[2]),Integer.parseInt(strDateStart[1]),Integer.parseInt(strDateStart[0]));
					String[] strDateEnd = strContents[2].replaceAll("\\s", "").split("/");
					LocalDate dateEnd = LocalDate.of(Integer.parseInt(strDateEnd[2]),Integer.parseInt(strDateEnd[1]),Integer.parseInt(strDateEnd[0]));
					double dStipend = Double.parseDouble(strContents[3].replaceAll("\\s", ""));
					dRate = Double.parseDouble(strContents[4].replaceAll("\\s", ""));
					boolWithdrawTaxable = Boolean.parseBoolean(strContents[5].replaceAll("\\s", ""));
					boolean boolNIable = Boolean.parseBoolean(strContents[6].replaceAll("\\s", ""));
					boolean boolIsEmployment = Boolean.parseBoolean(strContents[7].replaceAll("\\s", ""));

					System.out.print("stream name : ");
					System.out.println(stream.getAttribute("name"));

					System.out.println("stream details : ");
					System.out.println(dateStart);
					System.out.println(dateEnd);
					System.out.println(dStipend);
					System.out.println(dRate);
					System.out.println(boolWithdrawTaxable);
					System.out.println(boolNIable);
					System.out.println(boolIsEmployment);
					// add stream here
					streams.add(new IncomeStream(stream.getAttribute("name"), dateStart, dateEnd, dStipend, dRate, boolWithdrawTaxable, boolNIable, boolIsEmployment));
				}
			}
			Person person = new Person(name, dateDOB, streams, accounts, accPen, dPensionAmt, dPensionAmtEmp);
			listPeople.add(person);
			System.out.println(person);
			// add list of accounts and list of streams here to fields
		}
	}

	public List<Person> getListPeople() {
		return listPeople;
	}



}
