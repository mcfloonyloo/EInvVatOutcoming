package by.gomelagro.outcoming.gui.frames.invoice.data.error;

import java.util.AbstractList;

public class ErrorMessageList extends AbstractList<ErrorMessage> {
	private ErrorMessage[] list = new ErrorMessage[1];
	private int size = 0;
	
	public boolean add(ErrorMessage item){
		if(size>=list.length){
			ErrorMessage[] newList = new ErrorMessage[list.length+1];
			System.arraycopy(list, 0, newList, 0, list.length);
			list = newList;
		}
		list[size] = item;
		size++;
		return true;		
	}
	
	@Override
	public ErrorMessage get(int index) {
		return list[index];
	}

	@Override
	public int size() {
		return size;
	}

}
