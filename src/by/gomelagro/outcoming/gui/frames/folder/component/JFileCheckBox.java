package by.gomelagro.outcoming.gui.frames.folder.component;

import javax.swing.JCheckBox;

public class JFileCheckBox extends JCheckBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String value = null;

	private boolean red = false;

	public JFileCheckBox(String itemValue, boolean selected, boolean enabled) {
		super(itemValue == null ? "" : "" + itemValue, selected);
		setEnabled(enabled);
		setValue(itemValue);
	}

	public boolean isEnabled(){
		return super.isEnabled();
	}
	
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
	}
	
	public boolean isSelected() {
		return super.isSelected();
	}

	public void setSelected(boolean selected) {
		super.setSelected(selected);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isRed() {
		return red;
	}

	public void setRed(boolean red) {
		this.red = red;
	}

}
