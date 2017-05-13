package by.gomelagro.outcoming.gui.frames.invoice.parse;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import by.gomelagro.outcoming.gui.frames.invoice.data.Contract;
import by.gomelagro.outcoming.gui.frames.invoice.data.DeliveryCondition;

public class ParseDeliveryCondition {

	public static DeliveryCondition parse(Node node) {
		NodeList element = node.getChildNodes();
		
		Contract contract = null;
		String description = "";
		
		for(int index = 0;index < element.getLength();index++){
			Node value = element.item(index);
			if(value.getNodeType() != Node.TEXT_NODE){
				switch(value.getNodeName()){
				case "contract": {contract = ParseContract.parse(value); break;}
				case "description": {description = value.getTextContent(); break;}
				}
			}
		}
		
		return new DeliveryCondition.Builder()
				.setContract(contract)
				.setDescription(description)
				.build();
	}
	
}
