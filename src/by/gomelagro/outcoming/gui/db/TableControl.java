package by.gomelagro.outcoming.gui.db;

import java.util.List;

import javax.swing.JOptionPane;

public class TableControl {
	
	public static void runControl(){
		runControlDateDocument();
	}
	
	private static void runControlDateDocument(){
		List<String> list = WorkingOutcomingTable.Table.getColumns();
		if(list == null){
			JOptionPane.showMessageDialog(null, "������ �������� ��������� ������� ��������� ����","������",JOptionPane.ERROR_MESSAGE);
		}else{
			if(!WorkingOutcomingTable.Table.isContainsColumn(list, "DATEDOCUMENT")){
				if(WorkingOutcomingTable.Table.addColumn("DATEDOCUMENT", "TEXT")){
					JOptionPane.showMessageDialog(null, "������� \"���� ���������\" ��������","����������",JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "������ ���������� ������� \"���� ���������\" � ������� ��������� ����","������",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}
