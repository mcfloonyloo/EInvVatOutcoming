package by.gomelagro.outcoming.gui.frames.invoice.data.list.basic;

import java.util.AbstractList;

public class BooleanList extends AbstractList<Boolean> {
	
	private Boolean[] list = new Boolean[1];
	private int size = 0;
	
	public boolean add(Boolean item){
		if(size>=list.length){
			Boolean[] newList = new Boolean[list.length+1];
			System.arraycopy(list, 0, newList, 0, list.length);
			list = newList;
		}
		list[size] = item;
		size++;
		return true;		
	}
	
	@Override
	public Boolean get(int index) {
		return list[index];
	}

	@Override
	public int size() {
		return size;
	}
	
	public boolean getResult(){
		boolean result = true;
		for(int index = 0; index < size; index++){
			if(list[index] == false){
				result = false;
			}
		}
		return result;
	}
	
	public String toString(){
		String result = "";
		for(int index = 0; index < size; index++){
			result += list[index]+"\n";
		}
		return result;
	}
}
