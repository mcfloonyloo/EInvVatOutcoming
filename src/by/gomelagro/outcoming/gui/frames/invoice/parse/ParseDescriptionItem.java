package by.gomelagro.outcoming.gui.frames.invoice.parse;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import by.gomelagro.outcoming.gui.frames.invoice.data.Description;

public class ParseDescriptionItem {
	public static Description parse(Node node){		
		NodeList element = node.getChildNodes();
		
		String description = "";
		
		for(int index = 0;index < element.getLength();index++){
			Node value = element.item(index);
			if(value.getNodeType() != Node.TEXT_NODE){
				switch(value.getNodeName()){
				case "description": {description = value.getTextContent(); break;}
				}
			}
		}
		
		return new Description(description);
	}
}
