package by.gomelagro.outcoming.gui.frames.invoice.parse;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import by.gomelagro.outcoming.gui.frames.invoice.data.list.ConsignorList;

public class ParseConsignors {
	public static ConsignorList parse(Node node){
		NodeList element = node.getChildNodes();
		
		ConsignorList list = new ConsignorList();
		
		for(int index = 0; index < element.getLength(); index++){
			Node value = element.item(index);
			if(value.getNodeType() != Node.TEXT_NODE){
				switch(value.getNodeName()){
				case "consignor": {list.add(ParseSenderItem.parse(value)); break;}
				}
			}
		}
		
		return list;
	}
}
