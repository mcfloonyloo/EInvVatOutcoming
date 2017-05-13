package by.gomelagro.outcoming.gui.frames.invoice.parse;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import by.gomelagro.outcoming.gui.frames.invoice.data.DocumentType;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.item.DocumentItem;

public class ParseDocumentItem {
	public static DocumentItem parse(Node node) {
		NodeList element = node.getChildNodes();
		
		DocumentType docType = null;
		String date = "";
		String blankCode = "";
		String seria = "";
		String number = "";
		
		for(int index = 0;index < element.getLength();index++){
			Node value = element.item(index);
			if(value.getNodeType() != Node.TEXT_NODE){
				switch(value.getNodeName()){
				case "docType": {docType = ParseDocumentType.parse(value); break;}
				case "date": {date = value.getTextContent(); break;}
				case "blankCode": {blankCode = value.getTextContent(); break;}
				case "seria": {seria = value.getTextContent(); break;}
				case "number": {number = value.getTextContent(); break;}
				}
			}
		}
		
		return new DocumentItem.Builder()
				.setDocType(docType)
				.setDate(date)
				.setBlankCode(blankCode)
				.setSeria(seria)
				.setNumber(number)
				.build();
	}
}
