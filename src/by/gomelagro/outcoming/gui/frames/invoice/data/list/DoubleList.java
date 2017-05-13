package by.gomelagro.outcoming.gui.frames.invoice.data.list;

import java.util.AbstractList;

public class DoubleList extends AbstractList<Double> {
	
	private Double[] list = new Double[1];
	private int size = 0;
	
	public boolean add(Double item){
		if(size>=list.length){
			Double[] newList = new Double[list.length+1];
			System.arraycopy(list, 0, newList, 0, list.length);
			list = newList;
		}
		list[size] = item;
		size++;
		return true;		
	}
	
	@Override
	public Double get(int index) {
		return list[index];
	}

	@Override
	public int size() {
		return size;
	}
	
	public double[] getArray(){
		if (list == null) {
            return null;
        } else if (list.length == 0) {
            return new double[]{};
        }
        final double[] result = new double[list.length];
        for (int i = 0; i < list.length; i++) {
            result[i] = list[i].doubleValue();
        }
        return result;
    }
}

