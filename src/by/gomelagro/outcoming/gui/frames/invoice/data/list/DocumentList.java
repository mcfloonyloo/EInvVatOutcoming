package by.gomelagro.outcoming.gui.frames.invoice.data.list;

import java.util.AbstractList;

import by.gomelagro.outcoming.gui.frames.invoice.data.list.item.DocumentItem;

public class DocumentList extends AbstractList<DocumentItem> implements InvoiceSubSectionListIntf {

	private DocumentItem[] list = new DocumentItem[1];
	private int size = 0;
	
	public boolean add(DocumentItem item){
		if(size>=list.length){
			DocumentItem[] newList = new DocumentItem[list.length+1];
			System.arraycopy(list, 0, newList, 0, list.length);
			list = newList;
		}
		list[size] = item;
		size++;
		return true;		
	}
	
	@Override
	public DocumentItem get(int index) {
		return list[index];
	}

	@Override
	public int size() {
		return size;
	}
	
}
