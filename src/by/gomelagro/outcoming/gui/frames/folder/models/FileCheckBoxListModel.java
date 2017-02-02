package by.gomelagro.outcoming.gui.frames.folder.models;

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
	
	public void addElement(String itemValue, boolean selected, boolean enabled){
		this.addElement(new JFileCheckBox(itemValue, selected, enabled));
	}

}
