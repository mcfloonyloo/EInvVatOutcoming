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
		
	//конструктор
	public JFileCheckBoxList() {
		super();
		setModel(new FileCheckBoxListModel());
		setCellRenderer(new FileListCellRenderer());
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				int index = locationToIndex(me.getPoint());
				if (index != -1) {
					switch(me.getButton()){
						case MouseEvent.BUTTON1:{
							setSelectedIndex(index);
						}
						case MouseEvent.BUTTON3:{
							if(getModel().getElementAt(index).isEnabled()){
								setSelectedIndex(index);
								getModel().getElementAt(index).setChecked(!getModel().getElementAt(index).isChecked());
								repaint();
							}
							break;
						}
					}
				}
			}
		});
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	//количество выделенных элементов
	public int getCountSelected(){
		int count = 0;
		for(int index=0;index<getModel().getSize();index++){
			if(getModel().getElementAt(index).isChecked()){
				count++;
			}
		}
		return count;
	}
	
	//выделить всё
	public void selectAll(){
		for(int index=0;index<getModel().getSize();index++){
			if(!getModel().getElementAt(index).isBlocked()){
				getModel().getElementAt(index).setChecked(true);
			}
		}
		repaint();
	}
	
	//снять выделение
	public void unselectAll(){
		for(int index=0;index<getModel().getSize();index++){
			getModel().getElementAt(index).setSelected(false);
		}
		repaint();
	}
	
	//количество всех элементов
	public int getCountAll(){
		return getModel().getSize();
	}
	
	//список индексов выделенных элементов
	public int[] getCheckedIndexes() {
		List<Integer> list = new ArrayList<Integer>();
		for(int index=0;index<getModel().getSize();index++){
			if(getModel().getElementAt(index).isChecked()){
				list.add(new Integer(index));
			}
		}
		int[] indexes = new int[list.size()];

		for (int i = 0; i < list.size(); ++i) {
			indexes[i] = ((Integer) list.get(i)).intValue();}
	    return indexes;
	}

	//список выделенных элементовы
	public List<JFileCheckBox> getCheckedItems() {
		List<JFileCheckBox> list = new ArrayList<JFileCheckBox>();
		for(int index=0;index<getModel().getSize();index++){
			if(getModel().getElementAt(index).isChecked()){
				list.add(getModel().getElementAt(index));
			}
		}
		return list;
	}
}
