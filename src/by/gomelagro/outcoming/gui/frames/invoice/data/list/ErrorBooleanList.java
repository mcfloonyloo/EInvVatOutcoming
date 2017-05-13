package by.gomelagro.outcoming.gui.frames.invoice.data.list;

import java.util.AbstractList;

import by.gomelagro.outcoming.gui.frames.invoice.data.list.item.ErrorBooleanItem;

public class ErrorBooleanList extends AbstractList<ErrorBooleanItem> {
	private ErrorBooleanItem[] list = new ErrorBooleanItem[1];
	private int size = 0;
	
	public boolean add(ErrorBooleanItem item){
		if(size>=list.length){
			ErrorBooleanItem[] newList = new ErrorBooleanItem[list.length+1];
			System.arraycopy(list, 0, newList, 0, list.length);
			list = newList;
		}
		list[size] = item;
		size++;
		return true;		
	}
	
	@Override
	public ErrorBooleanItem get(int index) {
		return list[index];
	}

	@Override
	public int size() {
		return size;
	}

}
