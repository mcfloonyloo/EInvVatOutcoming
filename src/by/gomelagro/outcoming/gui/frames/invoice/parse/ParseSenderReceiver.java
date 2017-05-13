package by.gomelagro.outcoming.gui.frames.invoice.parse;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import by.gomelagro.outcoming.gui.frames.invoice.data.SenderReceiver;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.ConsigneeList;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.ConsignorList;

public class ParseSenderReceiver {
	public static SenderReceiver parse(Node node){
		NodeList element = node.getChildNodes();
		ConsignorList consignors = new ConsignorList();
		ConsigneeList consignees = new ConsigneeList();
		for(int index = 0; index < element.getLength(); index++){
			Node value = element.item(index);
			if(value.getNodeType() != Node.TEXT_NODE){
				switch(value.getNodeName()){
				case "consignors": {consignors = ParseConsignors.parse(value); break;}
				case "consignees": {consignees = ParseConsignees.parse(value); break;}
				}
			}
		}
		
		return new SenderReceiver.Builder()
				.setConsignors(consignors)
				.setConsignees(consignees)
				.build();
	}
}
