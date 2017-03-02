package by.gomelagro.outcoming.gui.frames.folder.models;

import java.awt.Color;
import java.util.List;

import javax.swing.DefaultListModel;

import by.gomelagro.outcoming.gui.frames.folder.component.JFileCheckBox;

public class FileCheckBoxListModel extends DefaultListModel<JFileCheckBox>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JFileCheckBox getSelectedItem(){
		return this.getSelectedItem();
	}
	
	public void addElement(String value, boolean check, boolean block, Color foreColor, Color backColor){
		this.addElement(new JFileCheckBox.Builder().setValue(value).setChecked(check).setBlocked(block).setForeColor(foreColor).setBackColor(backColor).build());
	}
	
	public void addElements(List<JFileCheckBox> list){
		for(int index=0;index<list.size();index++){
			addElement( list.get(index).getValue(),
						list.get(index).isChecked(),
						list.get(index).isBlocked(),
						list.get(index).getForeColor(),
						list.get(index).getBackColor());
		}
	}
	
	public void addElements(JFileCheckBox[] arr){
		for(int index=0;index<arr.length;index++){
			addElement( arr[index].getValue(),
						arr[index].isChecked(),
						arr[index].isBlocked(),
						arr[index].getForeColor(),
						arr[index].getBackColor());
		}
	}

}
