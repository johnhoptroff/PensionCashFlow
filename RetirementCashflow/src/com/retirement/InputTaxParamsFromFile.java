package com.retirement;

import java.io.File;
import java.time.LocalDate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.retirement.fileutils.LoadXML;

public class InputTaxParamsFromFile {

private TaxParams txPars;
private NIParams niPars;

	public InputTaxParamsFromFile(File file) {
		LoadXML fact = new LoadXML(file);
		Document doc = fact.getDoc();
		NodeList nodesStops = doc.getElementsByTagName("stopsParams");

		Element eNode = (Element) nodesStops.item(0);
		String[] strDate = eNode.getAttribute("date").split("/");
		LocalDate dateParamsDate = LocalDate.of(Integer.parseInt(strDate[2]), Integer.parseInt(strDate[1]),
				Integer.parseInt(strDate[0]));
		System.out.println("reading params for date:" + dateParamsDate);
		NodeList nlTaxParams = eNode.getElementsByTagName("tax");
		Element eTaxInfo = (Element) nlTaxParams.item(0);
		String strDetails = eTaxInfo.getTextContent();
		String[] strContents = strDetails.split("\n");

		double dTaxLowamt = Double.parseDouble(strContents[1].replaceAll("\\s", ""));
		double dTaxHighAmt = Double.parseDouble(strContents[2].replaceAll("\\s", ""));
		double dTaxLowpc = Double.parseDouble(strContents[3].replaceAll("\\s", ""));
		double dTaxHighpc = Double.parseDouble(strContents[4].replaceAll("\\s", ""));
		this.txPars = new TaxParams(dTaxLowamt, dTaxHighAmt, dTaxLowpc, dTaxHighpc);
		
		NodeList nlNIParams = eNode.getElementsByTagName("ni");
		Element eNIInfo = (Element) nlNIParams.item(0);
		strDetails = eNIInfo.getTextContent();
		strContents = strDetails.split("\n");

		double dNILowAmtWk = Double.parseDouble(strContents[1].replaceAll("\\s", ""));
		double dNIHighAmtWk = Double.parseDouble(strContents[2].replaceAll("\\s", ""));
		double dNIHighPaypc = Double.parseDouble(strContents[3].replaceAll("\\s", ""));
		double dNILowPaypc = Double.parseDouble(strContents[4].replaceAll("\\s", ""));
		this.niPars = new NIParams(dNIHighPaypc,dNILowPaypc,dNIHighAmtWk, dNILowAmtWk);
	}

	public TaxParams getTxPars() {
		return txPars;
	}

	public NIParams getNiPars() {
		return niPars;
	}


}
