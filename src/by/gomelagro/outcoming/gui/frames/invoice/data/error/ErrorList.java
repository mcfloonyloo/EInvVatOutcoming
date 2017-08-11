package by.gomelagro.outcoming.gui.frames.invoice.data.error;

import java.util.AbstractList;

public class ErrorList extends AbstractList<ErrorItem> {
	private ErrorItem[] list = new ErrorItem[1];
	private int size = 0;
	
	public boolean add(ErrorItem item){
		if(size>=list.length){
			ErrorItem[] newList = new ErrorItem[list.length+1];
			System.arraycopy(list, 0, newList, 0, list.length);
			list = newList;
		}
		list[size] = item;
		size++;
		return true;		
	}
	
	@Override
	public ErrorItem get(int index) {
		return list[index];
	}

	@Override
	public int size() {
		return size;
	}
	
	public boolean getFlagErrorResult(){
		boolean result = true;
		for(int index = 0; index < size; index++){
			if(list[index] != null){
				if(list[index].getFlagError() == false){
					result = false;
				}
			}
		}
		return result;
	}
}
