package by.gomelagro.outcoming.gui.frames.invoice.parse;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import by.gomelagro.outcoming.gui.frames.invoice.data.list.item.SenderItem;

public class ParseSenderItem {
	public static SenderItem parse(Node node) {
		NodeList element = node.getChildNodes();
		
		String countryCode = "";
		String unp = "";
		String name = "";
		String address = "";			
		
		for(int index = 0;index < element.getLength();index++){
			Node value = element.item(index);
			if(value.getNodeType() != Node.TEXT_NODE){
				switch(value.getNodeName()){
				case "countryCode": {countryCode = value.getTextContent() ;break;}
				case "unp": {unp = value.getTextContent() ;break;}
				case "name": {name = value.getTextContent() ;break;}
				case "address": {address = value.getTextContent() ;break;}
				}
			}
		}
		
		return new SenderItem.Builder()
				.setCountryCode(countryCode)
				.setUnp(unp)
				.setName(name)
				.setAddress(address)
				.build();
	}
}
