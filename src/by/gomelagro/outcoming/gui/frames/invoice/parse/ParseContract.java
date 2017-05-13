package by.gomelagro.outcoming.gui.frames.invoice.parse;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import by.gomelagro.outcoming.gui.frames.invoice.data.Contract;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.DocumentList;

public class ParseContract {

	public static Contract parse(Node node) {
		NodeList element = node.getChildNodes();
		
		String number = "";
		String date = "";
		DocumentList documents = new DocumentList();
		
		for(int index = 0;index < element.getLength();index++){
			Node value = element.item(index);
			if(value.getNodeType() != Node.TEXT_NODE){
				switch(value.getNodeName()){
				case "number": {number = value.getTextContent(); break;}
				case "date": {date = value.getTextContent(); break;}
				case "documents": {documents = ParseDocuments.parse(value); break;}
				}
			}
		}
		
		return new Contract.Builder()
				.setNumber(number)
				.setDate(date)
				.setDocuments(documents)
				.build();
	}
	
}
