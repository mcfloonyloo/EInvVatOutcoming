package by.gomelagro.outcoming.gui.frames.invoice.data.list;

import java.util.AbstractList;

public class StringList extends AbstractList<String> {
	private String[] list = new String[1];
	private int size = 0;
	
	public boolean add(String item){
		if(size>=list.length){
			String[] newList = new String[list.length+1];
			System.arraycopy(list, 0, newList, 0, list.length);
			list = newList;
		}
		list[size] = item;
		size++;
		return true;		
	}
	
	@Override
	public String get(int index) {
		return list[index];
	}
	
	public String[] getList(){
		return this.list;
	}

	@Override
	public int size() {
		return size;
	}
}
