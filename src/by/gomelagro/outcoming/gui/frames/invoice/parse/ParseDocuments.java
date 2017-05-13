package by.gomelagro.outcoming.gui.frames.invoice.parse;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import by.gomelagro.outcoming.gui.frames.invoice.data.list.DocumentList;

public class ParseDocuments {
	public static DocumentList parse(Node node) {
		NodeList element = node.getChildNodes();
		
		DocumentList documents = new DocumentList();
		
		for(int index = 0;index < element.getLength();index++){
			Node value = element.item(index);
			if(value.getNodeType() != Node.TEXT_NODE){
				switch(value.getNodeName()){
					case "document": {documents.add(ParseDocumentItem.parse(value)); break;}
				}
			}
		}
		
		return documents;
	}
}
