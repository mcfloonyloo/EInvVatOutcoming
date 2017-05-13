package by.gomelagro.outcoming.gui.frames.invoice.parse;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import by.gomelagro.outcoming.gui.frames.invoice.data.General;

public class ParseGeneral {

	public static General parse(Node node) {
		NodeList element = node.getChildNodes();
		
		String number = "";
		String dateIssuance = "";
		String dateTransaction = "";
		String documentType = "";
		String invoice = "";
		String dateCancelled = "";					
		
		for(int index = 0;index < element.getLength();index++){
			Node value = element.item(index);
			if(value.getNodeType() != Node.TEXT_NODE){
				switch(value.getNodeName()){
				case "number": {number = value.getTextContent() ;break;}
				case "dateIssuance": {dateIssuance = value.getTextContent() ;break;}
				case "dateTransaction": {dateTransaction = value.getTextContent() ;break;}
				case "documentType": {documentType = value.getTextContent() ;break;}
				case "invoice": {invoice = value.getTextContent() ;break;}
				case "dateCancelled": {dateCancelled = value.getTextContent() ;break;}
				}
			}
		}
		
		return new General.Builder()
				.setNumber(number)
				.setDateIssuance(dateIssuance)
				.setDateTransaction(dateTransaction)
				.setDocumentType(documentType)
				.setInvoice(invoice)
				.setDateCancelled(dateCancelled)
				.build();
	}

}
