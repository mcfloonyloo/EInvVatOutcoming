package by.gomelagro.outcoming.gui.frames.models;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import by.gomelagro.outcoming.gui.frames.models.data.Outcoming;

public class OutcomingTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	
	private List<String> columnNames = new ArrayList<String>();
	{
		columnNames.add("���");
		columnNames.add("������������");
		columnNames.add("����� ����");
		columnNames.add("������ ����");
		columnNames.add("<html>����<br>�����������");
		columnNames.add("<html>����<br>������������");
		columnNames.add("� ����");
		columnNames.add("<html>����<br>�������������");
		columnNames.add("<html>�����<br>����� ���");
		columnNames.add("<html>�����<br>���������");
	}
	
	private List<Outcoming> data = new ArrayList<Outcoming>();

	@Override
	public int getColumnCount() {
		return columnNames.size();
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		try {
			return data.get(row).getValue(col);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"������", JOptionPane.ERROR_MESSAGE);
			return "";
		}
	}
	
	public Class<?> getColumnClass(int col){
		return getValueAt(0,col).getClass();
	}
	
	public String getColumnName(int col){
		try{
			return columnNames.get(col);
		}catch(Exception e){return null;}
	}
	
	public void deleteRow(int row){
		data.remove(row);
		fireTableRowsDeleted(row, row);
	}
	
	public void deleteAllRows(){
		while(data.size()>0){
			data.remove(0);
			fireTableRowsDeleted(0, 0);
		}
	}
	
	public void addRow(Outcoming rowData){
		data.add(rowData);
		fireTableRowsInserted(data.size() - 1, data.size() - 1);
	}

}
