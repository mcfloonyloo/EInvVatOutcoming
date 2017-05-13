package by.gomelagro.outcoming.gui.frames.invoice;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextField;

public class ComponentTextFieldPanel {
	private JTextField textField;
	private GridBagConstraints contains;
	
	public JTextField getTextField(){return this.textField;}
	public GridBagConstraints getContains(){return this.contains;}
	
	public ComponentTextFieldPanel(String text, int gridx, int gridy){
		textField = new JTextField(text);
		contains = new GridBagConstraints();
		contains.gridx = gridx;
		contains.gridy = gridy;
	}
	
	public ComponentTextFieldPanel setContainsFill(int fill){
		this.contains.fill = fill;
		return this;
	}
	
	public ComponentTextFieldPanel setContainsInsets(Insets insets){
		this.contains.insets = insets;
		return this;
	}	
	
	public ComponentTextFieldPanel setTextFieldColumns(int columns){
		this.textField.setColumns(columns);
		return this;
	}
}
