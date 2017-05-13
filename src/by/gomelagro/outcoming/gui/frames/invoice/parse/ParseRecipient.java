package by.gomelagro.outcoming.gui.frames.invoice.parse;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import by.gomelagro.outcoming.gui.frames.invoice.data.Recipient;
import by.gomelagro.outcoming.gui.frames.invoice.data.Taxes;

public class ParseRecipient {
	public static Recipient parse(Node node){
		NodeList element = node.getChildNodes();
		
		String recipientStatus = "";
		String dependentPerson = "";
		String residentsOfOffshore = "";
		String specialDealGoods = "";
		String bigCompany = "";
		String countryCode = "";
		String unp = "";
		String branchCode = "";
		String name = "";
		String address = "";
		String declaration = "";
		Taxes taxes = null;
		String dateImport = "";		
		
		for(int index = 0; index < element.getLength(); index++){
			Node value = element.item(index);
			if(value.getNodeType() != Node.TEXT_NODE){
				switch(value.getNodeName()){
				case "recipientStatus": {recipientStatus = value.getTextContent(); break;}
				case "dependentPerson": {dependentPerson = value.getTextContent(); break;}
				case "residentsOfOffshore": {residentsOfOffshore = value.getTextContent(); break;}
				case "specialDealGoods": {specialDealGoods = value.getTextContent(); break;}
				case "bigCompany": {bigCompany = value.getTextContent(); break;}
				case "countryCode": {countryCode = value.getTextContent(); break;}
				case "unp": {unp = value.getTextContent(); break;}
				case "branchCode": {branchCode = value.getTextContent(); break;}
				case "name": {name = value.getTextContent(); break;}
				case "address": {address = value.getTextContent(); break;}
				case "declaration": {declaration = value.getTextContent(); break;}
				case "taxes": {taxes = ParseTaxes.getTaxesValues(value); break;}
				case "dateImport": {dateImport = value.getTextContent(); break;}
				}
			}
		}
		
		return new Recipient.Builder()
				.setRecipientStatus(recipientStatus)
				.setDependentPerson(dependentPerson)
				.setResidentsOfOffshore(residentsOfOffshore)
				.setSpecialDealGoods(specialDealGoods)
				.setBigCompany(bigCompany)
				.setCountryCode(countryCode)
				.setUnp(unp)
				.setBranchCode(branchCode)
				.setName(name)
				.setAddress(address)
				.setDeclaration(declaration)
				.setTaxes(taxes)
				.setDateImport(dateImport)
				.build();
	}
}
