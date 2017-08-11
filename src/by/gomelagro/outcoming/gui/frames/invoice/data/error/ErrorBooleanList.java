package by.gomelagro.outcoming.gui.frames.invoice.data.error;

import java.util.AbstractList;

import by.gomelagro.outcoming.gui.frames.invoice.data.list.basic.StringList;

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
	
	public boolean getBoolean(){
		boolean result = true;
		if(size > 0){
			for(int index = 0; index < size; index++){
				if(list[index] != null){
					if(list[index].getFlagError() == false){
						result = false;
					}
				}
			}
		}
		return result;
	}
	
	public String[] getMessage(){
		StringList strList = new StringList();
		int strSize = 0;
		for(int index = 0; index < size; index++){
			if(list[index] != null){
				if(list[index].getError() != null){
					strList.add(list[index].getError());
					strSize++;
				}
			}
		}
		if(strList.size() > 0){
			String[] arrList = new String[strSize];
			for(int index = 0; index < strSize;index++){
				arrList[index] = strList.get(index).trim();
			}
			return arrList;
		}else{
			return null;
		}
	}

}
