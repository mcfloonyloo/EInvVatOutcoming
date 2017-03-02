package by.gomelagro.outcoming.gui.frames.folder.component;

import java.awt.Color;
import javax.swing.JCheckBox;

public class JFileCheckBox extends JCheckBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String value;
	private Color foreColor;
	private Color backColor;

	public JFileCheckBox(String value, boolean selected) {
		super(value == null ? "" : "" + value, selected);
		this.value = value;
	}
	
	public JFileCheckBox(Builder build){
		super(build.value == null ? "" : "" + build.value, build.check);
		this.value = build.value;
		this.foreColor = build.foreColor;
		this.backColor = build.backColor;
		setSelected(build.check);
		setBlocked(build.block);		
	}

	public boolean isChecked() {
		return super.isSelected();
	}

	public void setChecked(boolean check) {
		super.setSelected(check);
	}

	public boolean isBlocked(){
		return !super.isEnabled();
	}
	
	public void setBlocked(boolean selected) {
		super.setEnabled(!selected);
	}
	
	public String getValue() {
		return value;
	}
	
	public Color getForeColor(){
		return foreColor;
	}
	
	public Color getBackColor(){
		return backColor;
	}
	
	public static class Builder{
		private String value;
		private Color foreColor;
		private Color backColor;
		
		private boolean check;
		private boolean block;
		
		public Builder(){}
		
		public Builder setValue(String value){
			this.value = value;
			return this;
		}
		
		public Builder setForeColor(Color foreColor){
			this.foreColor = foreColor;
			return this;
		}
		
		public Builder setBackColor(Color backColor){
			this.backColor = backColor;
			return this;
		}
		
		public Builder setChecked(boolean check){
			this.check = check;
			return this;
		}
		
		public Builder setBlocked(boolean block){
			this.block = block;
			return this;
		}

		public JFileCheckBox build(){
			return new JFileCheckBox(this);
		}
		
	}

}
