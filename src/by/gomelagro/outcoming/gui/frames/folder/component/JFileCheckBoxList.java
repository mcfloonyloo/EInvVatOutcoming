package by.gomelagro.outcoming.gui.frames.folder.component;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

import by.gomelagro.outcoming.gui.frames.folder.models.FileCheckBoxListModel;
import by.gomelagro.outcoming.gui.frames.folder.models.renderer.FileListCellRenderer;

public class JFileCheckBoxList extends JList<JFileCheckBox> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	public JFileCheckBoxList() {
		super();
		setModel(new FileCheckBoxListModel());
		setCellRenderer(new FileListCellRenderer());
		addMouseListener(new MouseAdapter() {
		
			@Override
			public void mousePressed(MouseEvent me) {
				int index = locationToIndex(me.getPoint());
				if ((index != -1)&&(me.getButton() == MouseEvent.BUTTON1)) {
					if(getModel().getElementAt(index).isEnabled()){
						getModel().getElementAt(index).setSelected(!getModel().getElementAt(index).isSelected());
						repaint();
					}
	        	}
			}
		});

		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	public int getCountSelected(){
		int count = 0;
		for(int index=0;index<getModel().getSize();index++){
			if(getModel().getElementAt(index).isSelected()){
				count++;
			}
		}
		return count;
	}
	
	public int getCountAll(){
		return getModel().getSize();
	}
	
	public int[] getCheckedIndexes() {
		List<Integer> list = new ArrayList<Integer>();
		for(int index=0;index<getModel().getSize();index++){
			if(getModel().getElementAt(index).isSelected()){
				list.add(new Integer(index));
			}
		}
		int[] indexes = new int[list.size()];

		for (int i = 0; i < list.size(); ++i) {
			indexes[i] = ((Integer) list.get(i)).intValue();}
	    return indexes;
	}

	public List<JFileCheckBox> getCheckedItems() {
		List<JFileCheckBox> list = new ArrayList<JFileCheckBox>();
		for(int index=0;index<getModel().getSize();index++){
			if(getModel().getElementAt(index).isSelected()){
				list.add(getModel().getElementAt(index));
			}
		}
		return list;
	}
}
