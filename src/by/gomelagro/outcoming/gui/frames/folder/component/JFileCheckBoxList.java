package by.gomelagro.outcoming.gui.frames.folder.component;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import by.gomelagro.outcoming.gui.frames.ShowInvoiceFrame;
import by.gomelagro.outcoming.gui.frames.folder.component.list.FileCheckBoxList;
import by.gomelagro.outcoming.gui.frames.folder.models.FileCheckBoxListModel;
import by.gomelagro.outcoming.gui.frames.folder.models.renderer.FileListCellRenderer;
import by.gomelagro.outcoming.gui.frames.invoice.Invoice;
import by.gomelagro.outcoming.gui.frames.invoice.LoadInvoice;

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
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		addMouseListener(new MouseAdapter() {
						
			@Override		
			public void mouseClicked(MouseEvent me){
				switch(me.getButton()){
				case MouseEvent.BUTTON1:{
					int index = locationToIndex(me.getPoint());
					if (index != -1) {
						if(getModel().getElementAt(index).isEnabled()){
							setSelectedIndex(index);
							switch(me.getClickCount()){
								case 2:{
									Invoice invoice = LoadInvoice.loadFile(getModel().getElementAt(index).getValueFull());
									if(invoice != null){
										ShowInvoiceFrame frame = new ShowInvoiceFrame().initialize(invoice);
										frame.setVisible(true);
									}else{
										JOptionPane.showMessageDialog(null, "Ошибка загрузки файла ЭСЧФ","Ошибка",JOptionPane.ERROR_MESSAGE);
									}
									break;
								}
							}

						}
					}
				break;}
				case MouseEvent.BUTTON3:{
					int index = locationToIndex(me.getPoint());
					if (index != -1) {
						setSelectedIndex(index);
						getModel().getElementAt(index).setChecked(!getModel().getElementAt(index).isChecked());
						repaint();
					}
					
					break;}
				}
			}
		});
		//setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
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
	/*public List<JFileCheckBox> getCheckedItems() {
		List<JFileCheckBox> list = new ArrayList<JFileCheckBox>();
		for(int index=0;index<getModel().getSize();index++){
			if(getModel().getElementAt(index).isChecked()){
				list.add(getModel().getElementAt(index));
			}
		}
		return list;
	}*/
	
	//список выделенных элементовы
	public FileCheckBoxList getCheckedItems() {
		FileCheckBoxList list = new FileCheckBoxList();
		if(getModel().getSize() > 0){
			for(int index=0;index<getModel().getSize();index++){
				if(getModel().getElementAt(index).isChecked()){
					list.add(getModel().getElementAt(index));
				}
			}
			return list;
		}else{
			return null;
		}
	}
}
