package by.gomelagro.outcoming.gui.frames.invoice.parse;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import by.gomelagro.outcoming.gui.frames.invoice.data.Roster;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.RosterList;

public class ParseRoster {
	public static Roster parse(Node node){
		String totalCostVat = node.getAttributes().getNamedItem("totalCostVat").getNodeValue();
		String totalExcise = node.getAttributes().getNamedItem("totalExcise").getNodeValue();
		String totalVat = node.getAttributes().getNamedItem("totalVat").getNodeValue();
		String totalCost = node.getAttributes().getNamedItem("totalCost").getNodeValue();
		
		NodeList element = node.getChildNodes();
		RosterList roster = new RosterList();
		
		for(int index = 0;index < element.getLength();index++){
			Node value = element.item(index);
			if(value.getNodeType() != Node.TEXT_NODE){
				switch(value.getNodeName()){
				case "rosterItem": {roster.add(ParseRosterItem.parse(value));
				break;}
				}
			}
		}
		
		return new Roster.Builder()
				.setTotalCostVat(totalCostVat)
				.setTotalExcise(totalExcise)
				.setTotalVat(totalVat)
				.setTotalCost(totalCost)
				.setRosters(roster)
				.build();
	}
}
