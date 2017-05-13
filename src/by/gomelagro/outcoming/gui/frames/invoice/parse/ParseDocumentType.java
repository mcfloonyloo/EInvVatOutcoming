package by.gomelagro.outcoming.gui.frames.invoice.parse;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import by.gomelagro.outcoming.gui.frames.invoice.data.DocumentType;

public class ParseDocumentType {
	public static DocumentType parse(Node node) {
		NodeList element = node.getChildNodes();
		
		String code = "";
		String value = "";		
		
		for(int index = 0;index < element.getLength();index++){
			Node valueNode = element.item(index);
			if(valueNode.getNodeType() != Node.TEXT_NODE){
				switch(valueNode.getNodeName()){
				case "code": {code = valueNode.getTextContent() ;break;}
				case "value": {value = valueNode.getTextContent() ;break;}
				}
			}
		}
		
		return new DocumentType(code, value);
	}
}
