package by.gomelagro.outcoming.gui.frames.invoice.data.list;

import java.util.AbstractList;

import by.gomelagro.outcoming.gui.frames.invoice.data.list.item.SenderItem;

public class ConsignorList extends AbstractList<SenderItem> implements InvoiceSubSectionListIntf {
	
	private SenderItem[] list = new SenderItem[1];
	private int size = 0;
	
	public boolean add(SenderItem item){
		if(size>=list.length){
			SenderItem[] newList = new SenderItem[list.length+1];
			System.arraycopy(list, 0, newList, 0, list.length);
			list = newList;
		}
		list[size] = item;
		size++;
		return true;		
	}
	
	@Override
	public SenderItem get(int index) {
		return list[index];
	}

	@Override
	public int size() {
		return size;
	}

}
