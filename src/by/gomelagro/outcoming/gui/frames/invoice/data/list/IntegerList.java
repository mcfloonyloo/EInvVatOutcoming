package by.gomelagro.outcoming.gui.frames.invoice.data.list;

import java.util.AbstractList;

public class IntegerList  extends AbstractList<Integer> {
	
	private Integer[] list = new Integer[1];
	private int size = 0;
	
	public boolean add(Integer item){
		if(size>=list.length){
			Integer[] newList = new Integer[list.length+1];
			System.arraycopy(list, 0, newList, 0, list.length);
			list = newList;
		}
		list[size] = item;
		size++;
		return true;		
	}
	
	@Override
	public Integer get(int index) {
		return list[index];
	}

	@Override
	public int size() {
		return size;
	}
	
	public int[] getArray(){
		if (list == null) {
            return null;
        } else if (list.length == 0) {
            return new int[]{};
        }
        final int[] result = new int[list.length];
        for (int i = 0; i < list.length; i++) {
            result[i] = list[i].intValue();
        }
        return result;
	}
}
