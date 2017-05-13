package by.gomelagro.outcoming.gui.frames.invoice.parse;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import by.gomelagro.outcoming.gui.frames.invoice.data.list.ConsigneeList;

public class ParseConsignees {
	public static ConsigneeList parse(Node node){
		NodeList element = node.getChildNodes();
		
		ConsigneeList list = new ConsigneeList();
		
		for(int index = 0; index < element.getLength(); index++){
			Node value = element.item(index);
			if(value.getNodeType() != Node.TEXT_NODE){
				switch(value.getNodeName()){
				case "consignee": {list.add(ParseSenderItem.parse(value)); break;}
				}
			}
		}
		
		return list;
	}
}
