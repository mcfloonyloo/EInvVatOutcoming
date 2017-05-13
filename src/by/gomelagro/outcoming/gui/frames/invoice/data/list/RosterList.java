package by.gomelagro.outcoming.gui.frames.invoice.data.list;

import java.util.AbstractList;

import by.gomelagro.outcoming.gui.frames.invoice.data.list.item.RosterItem;

public class RosterList extends AbstractList<RosterItem> implements InvoiceSubSectionListIntf {
	
	private RosterItem[] list = new RosterItem[1];
	private int size = 0;
	
	public boolean add(RosterItem item){
		if(size>=list.length){
			RosterItem[] newList = new RosterItem[list.length+1];
			System.arraycopy(list, 0, newList, 0, list.length);
			list = newList;
		}
		list[size] = item;
		size++;
		return true;		
	}
	
	@Override
	public RosterItem get(int index) {
		return list[index];
	}

	@Override
	public int size() {
		return size;
	}

}
