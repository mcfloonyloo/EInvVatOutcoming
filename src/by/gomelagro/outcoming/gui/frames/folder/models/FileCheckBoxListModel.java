package by.gomelagro.outcoming.gui.frames.folder.models;

import java.awt.Color;

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
	
	public void addElement(String value, boolean selected, Color color){
		this.addElement(new JFileCheckBox.Builder().setValue(value).setSelected(selected).setColor(color).build());
	}

}
