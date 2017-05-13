package by.gomelagro.outcoming.gui.frames.invoice.parse;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import by.gomelagro.outcoming.gui.frames.invoice.data.ForInvoice;
import by.gomelagro.outcoming.gui.frames.invoice.data.Provider;
import by.gomelagro.outcoming.gui.frames.invoice.data.Taxes;

public class ParseProvider {
	public static Provider parse(Node node){
		NodeList element = node.getChildNodes();
		
		String providerStatus = "";
		String dependentPerson = "";
		String residentsOfOffshore = "";
		String specialDealGoods = "";
		String bigCompany = "";
		String countryCode = "";
		String unp = "";
		String branchCode = "";
		String name = "";
		String address = "";
		ForInvoice principal = null;
		ForInvoice vendor = null;
		String declaration = "";
		String dateRelease = "";
		String dateActualExport = "";
		Taxes taxes = null;
		
		for(int index = 0;index < element.getLength();index++){
			Node value = element.item(index);
			if(value.getNodeType() != Node.TEXT_NODE){
				switch(value.getNodeName()){
				case "providerStatus": {providerStatus = value.getTextContent(); break;}
				case "dependentPerson": {dependentPerson = value.getTextContent(); break;}
				case "residentsOfOffshore": {residentsOfOffshore = value.getTextContent(); break;}
				case "specialDealGoods": {specialDealGoods = value.getTextContent(); break;}
				case "bigCompany": {bigCompany = value.getTextContent(); break;}
				case "countryCode": {countryCode = value.getTextContent(); break;}
				case "unp": {unp = value.getTextContent(); break;}
				case "branchCode": {branchCode = value.getTextContent(); break;}
				case "name": {name = value.getTextContent(); break;}
				case "address": {address = value.getTextContent(); break;}
				case "principal": {principal = getForInvoiceValues(value); break;}
				case "vendor": {vendor = getForInvoiceValues(value); break;}
				case "declaration": {declaration = value.getTextContent(); break;}
				case "dateRelease": {dateRelease = value.getTextContent(); break;}
				case "dateActualExport": {dateActualExport = value.getTextContent(); break;}
				case "taxes": {taxes = ParseTaxes.getTaxesValues(value); break;}
				}
			}
		}
		
		return new Provider.Builder()
				.setProviderStatus(providerStatus)
				.setDependentPerson(dependentPerson)
				.setResidentsOfOffshore(residentsOfOffshore)
				.setSpecialDealGoods(specialDealGoods)
				.setBigCompany(bigCompany)
				.setCountryCode(countryCode)
				.setUnp(unp)
				.setBranchCode(branchCode)
				.setName(name)
				.setAddress(address)
				.setPrincipal(principal)
				.setVendor(vendor)
				.setDeclaration(declaration)
				.setDateRelease(dateRelease)
				.setDateActualExport(dateActualExport)
				.setTaxes(taxes)
				.build();
	}
	
	private static ForInvoice getForInvoiceValues(Node node){
		NodeList element = node.getChildNodes();
		
		String number = "";
		String date = "";
		
		for(int index = 0;index < element.getLength();index++){
			Node value = element.item(index);
			if(value.getNodeType() != Node.TEXT_NODE){
				switch(value.getNodeName()){
				case "number": {number = value.getTextContent(); break;}
				case "date": {date = value.getTextContent(); break;}
				}
			}
		}
		
		return new ForInvoice(number,date);		
	}
}
