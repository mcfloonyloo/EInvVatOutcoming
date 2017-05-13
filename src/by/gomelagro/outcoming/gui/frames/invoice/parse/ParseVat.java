package by.gomelagro.outcoming.gui.frames.invoice.parse;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import by.gomelagro.outcoming.gui.frames.invoice.data.Vat;

public class ParseVat {
	public static Vat parse(Node node){		
		NodeList element = node.getChildNodes();
		
		String rate = "";
		String rateType = "";
		String summaVat = "";
		
		for(int index = 0;index < element.getLength();index++){
			Node value = element.item(index);
			if(value.getNodeType() != Node.TEXT_NODE){
				switch(value.getNodeName()){
				case "rate": {rate = value.getTextContent(); break;}
				case "rateType": {rateType = value.getTextContent(); break;}
				case "summaVat": {summaVat = value.getTextContent(); break;}
				}
			}
		}
		
		return new Vat.Builder()
				.setRate(rate)
				.setRateType(rateType)
				.setSummaVat(summaVat)
				.build();
	}
}
