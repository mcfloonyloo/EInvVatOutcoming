 package by.gomelagro.outcoming.gui.frames.folder.models.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.StrokeBorder;

import by.gomelagro.outcoming.gui.frames.folder.component.FileCheckBoxFont;
import by.gomelagro.outcoming.gui.frames.folder.component.JFileCheckBox;

public class FileListCellRenderer extends DefaultListCellRenderer  implements ListCellRenderer<Object>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		
		if (value instanceof JFileCheckBox) {
			JFileCheckBox checkbox = (JFileCheckBox) value;
			checkbox.setBackground(Color.WHITE);
		 	if(isSelected){
		 		checkbox.setForeground(checkbox.getColor());
		 		checkbox.setFont(FileCheckBoxFont.getBoldFont());
		 		checkbox.setBorder(new CompoundBorder(new LineBorder(new Color(255,255,255), 1),new StrokeBorder(new BasicStroke())));
		 	}else{
		 		checkbox.setForeground(checkbox.getColor());
		 		checkbox.setFont(FileCheckBoxFont.getFont());
		 		checkbox.setBorder(new LineBorder(new Color(255,255,255), 2));
		 	}
		 	
		    checkbox.setFocusPainted(false);
		    checkbox.setBorderPainted(true);
		    return checkbox;
		} else {
		  	return super.getListCellRendererComponent(list, value.getClass().getName(), index, isSelected, cellHasFocus);
		}
	}

}
