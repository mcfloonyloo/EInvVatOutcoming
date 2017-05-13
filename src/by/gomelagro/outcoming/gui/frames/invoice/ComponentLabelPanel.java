package by.gomelagro.outcoming.gui.frames.invoice;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;

public class ComponentLabelPanel {
	private JLabel label;
	private GridBagConstraints contains;
	
	public JLabel getLabel(){return this.label;}
	public GridBagConstraints getContains(){return this.contains;}
	
	public ComponentLabelPanel(String text, int gridx, int gridy){
		label = new JLabel(text);
		contains = new GridBagConstraints();
		contains.gridx = gridx;
		contains.gridy = gridy;
	}
	
	public ComponentLabelPanel setLabelFont(Font font){
		this.label.setFont(font);
		return this;
	}
	
	public ComponentLabelPanel setContainsAnchor(int anchor){
		this.contains.anchor = anchor;
		return this;
	}
	
	public ComponentLabelPanel setContainsInsets(Insets insets){
		this.contains.insets = insets;
		return this;
	}	
	
	public ComponentLabelPanel setContainsGridwidth(int gridwidth){
		this.contains.gridwidth = gridwidth;
		return this;
	}
	
	public ComponentLabelPanel setContainsGridheight(int gridheight){
		this.contains.gridheight = gridheight;
		return this;
	}
}
