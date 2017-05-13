package by.gomelagro.outcoming.gui.frames.folder.component.list;

import java.util.AbstractList;

import by.gomelagro.outcoming.gui.frames.folder.component.JFileCheckBox;

public class FileCheckBoxList extends AbstractList<JFileCheckBox> {
	
	private JFileCheckBox[] list = new JFileCheckBox[1];
	private int size = 0;
	
	public boolean add(JFileCheckBox item){
		if(size>=list.length){
			JFileCheckBox[] newList = new JFileCheckBox[list.length+1];
			System.arraycopy(list, 0, newList, 0, list.length);
			list = newList;
		}
		list[size] = item;
		size++;
		return true;		
	}
	
	@Override
	public JFileCheckBox get(int index) {
		return list[index];
	}

	@Override
	public int size() {
		return size;
	}
}
