package by.gomelagro.outcoming.gui.frames.invoice.parse;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import by.gomelagro.outcoming.gui.frames.invoice.data.Description;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.DescriptionList;

public class ParseDescriptions {
	public static DescriptionList parse(Node node){		
		NodeList element = node.getChildNodes();
		
		DescriptionList descriptions = new DescriptionList();
		
		for(int index = 0;index < element.getLength();index++){
			Node value = element.item(index);
			if(value.getNodeType() != Node.TEXT_NODE){
				switch(value.getNodeName()){
				case "description": {descriptions.add(new Description(value.getTextContent())); break;}
				}
			}
		}
		
		return descriptions;
	}
}
