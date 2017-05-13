package by.gomelagro.outcoming.gui.frames.invoice.parse;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import by.gomelagro.outcoming.gui.frames.invoice.data.Vat;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.DescriptionList;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.item.RosterItem;

public class ParseRosterItem {
	public static RosterItem parse(Node node){		
		NodeList element = node.getChildNodes();
		
		String number = "";
		String name = "";
		String code = "";
		String codeOced = "";
		String units = "";
		String count = "";
		String price = "";
		String cost = "";
		Vat vat = null;
		String costVat = "";
		DescriptionList descriptions = null;
		String skipDeduction = "";
		
		for(int index = 0;index < element.getLength();index++){
			Node value = element.item(index);
			if(value.getNodeType() != Node.TEXT_NODE){
				switch(value.getNodeName()){
				case "number": {number = value.getTextContent(); break;}
				case "name": {name = value.getTextContent(); break;}
				case "code": {code = value.getTextContent(); break;}
				case "code_oced": {codeOced = value.getTextContent(); break;}
				case "units": {units = value.getTextContent(); break;}
				case "count": {count = value.getTextContent(); break;}
				case "price": {price = value.getTextContent(); break;}
				case "cost": {cost = value.getTextContent(); break;}
				case "vat": {vat = ParseVat.parse(value); break;}
				case "costVat": {costVat = value.getTextContent(); break;}
				case "descriptions": {descriptions = ParseDescriptions.parse(value); break;}
				case "skipDeduction": {skipDeduction = value.getTextContent(); break;}
				}
			}
		}
		
		return new RosterItem.Builder()
				.setNumber(number)
				.setName(name)
				.setCode(code)
				.setCodeOced(codeOced)
				.setUnits(units)
				.setCount(count)
				.setPrice(price)
				.setCost(cost)
				.setVat(vat)
				.setCostVat(costVat)
				.setDescriptions(descriptions)
				.setSkipDeduction(skipDeduction)				
				.build();
	}
}
