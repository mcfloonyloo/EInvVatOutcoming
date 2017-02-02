 package by.gomelagro.outcoming.gui.frames.folder.models.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

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
		 	checkbox.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
		    if (checkbox.isRed()) {
		    	checkbox.setForeground(Color.red);
		    } else {
		    	checkbox.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
		    }
		    if(!isEnabled()){
		    	checkbox.setEnabled(!isEnabled());
		    }
		    checkbox.setFont(getFont());
		    checkbox.setFocusPainted(false);
		    checkbox.setBorderPainted(true);
		    checkbox.setBorder(isSelected ? UIManager.getBorder("List.focusCellHighlightBorder") : noFocusBorder);
		    return checkbox;
		} else {
		  	return super.getListCellRendererComponent(list, value.getClass().getName(), index, isSelected, cellHasFocus);
		}
	}

}
