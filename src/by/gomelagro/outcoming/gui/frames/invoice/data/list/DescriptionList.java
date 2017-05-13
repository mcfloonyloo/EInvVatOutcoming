package by.gomelagro.outcoming.gui.frames.invoice.data.list;

import java.util.AbstractList;

import by.gomelagro.outcoming.gui.frames.invoice.data.Description;

public class DescriptionList extends AbstractList<Description> implements InvoiceSubSectionListIntf {

	private Description[] list = new Description[1];
	private int size = 0;
	
	public boolean add(Description item){
		if(size>=list.length){
			Description[] newList = new Description[list.length+1];
			System.arraycopy(list, 0, newList, 0, list.length);
			list = newList;
		}
		list[size] = item;
		size++;
		return true;		
	}
	
	@Override
	public Description get(int index) {
		return list[index];
	}

	@Override
	public int size() {
		return size;
	}
	
}
