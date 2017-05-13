package by.gomelagro.outcoming.gui.frames.invoice.parse;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import by.gomelagro.outcoming.gui.frames.invoice.data.Taxes;

public class ParseTaxes {
	public static Taxes getTaxesValues(Node node){
		NodeList element = node.getChildNodes();
		
		String number = "";
		String date = "";
		
		for(int index = 0;index < element.getLength();index++){
			Node value = element.item(index);
			if(value.getNodeType() != Node.TEXT_NODE){
				switch(value.getNodeName()){
				case "number": {number = value.getTextContent(); break;}
				case "date": {date = value.getTextContent(); break;}
				}
			}
		}
		
		return new Taxes(number,date);		
	}
}
