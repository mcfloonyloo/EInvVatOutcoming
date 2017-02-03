package by.gomelagro.outcoming.gui.frames.folder.component;

import java.awt.Color;

import javax.swing.JCheckBox;

public class JFileCheckBox extends JCheckBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String value;
	private Color color;

	public JFileCheckBox(String value, boolean selected) {
		super(value == null ? "" : "" + value, selected);
		this.value = value;
	}
	
	public JFileCheckBox(Builder build){
		super(build.value == null ? "" : "" + build.value, build.selected);
		this.value = build.value;
		this.color = build.color;
		setSelected(build.selected);
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
	
	public Color getColor(){
		return color;
	}
	
	public static class Builder{
		private String value;
		private Color color;	
		private boolean selected;
		
		public Builder(){}
		
		public Builder setValue(String value){
			this.value = value;
			return this;
		}
		
		public Builder setColor(Color color){
			this.color = color;
			return this;
		}
		
		public Builder setSelected(boolean selected){
			this.selected = selected;
			return this;
		}

		public JFileCheckBox build(){
			return new JFileCheckBox(this);
		}
		
	}

}
