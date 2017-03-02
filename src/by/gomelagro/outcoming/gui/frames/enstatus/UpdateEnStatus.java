package by.gomelagro.outcoming.gui.frames.enstatus;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import by.avest.edoc.client.AvEStatus;
import by.gomelagro.outcoming.gui.db.WorkingOutcomingTable;
import by.gomelagro.outcoming.gui.progress.LoadFileProgressBar;
import by.gomelagro.outcoming.service.EVatServiceSingleton;

public class UpdateEnStatus {
	public static void updateFull(){
		if(EVatServiceSingleton.getInstance().isAuthorized()){
			if(EVatServiceSingleton.getInstance().isConnected()){
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
					@Override
					protected Void doInBackground() throws Exception {
						List<String> list = WorkingOutcomingTable.Lists.selectNumbersInvoice();
						if(list != null){
							int errorCount = 0;
							int mainCount = 0;
							LoadFileProgressBar progress = new LoadFileProgressBar(list.size()).activated();
							try{
								for(int index=0;index<list.size();index++){
									AvEStatus status = EVatServiceSingleton.getInstance().getService().getStatus(list.get(index));
									boolean isValid = status.verify();
									if(isValid){
										if(WorkingOutcomingTable.Update.updateStatus(status.getStatus(), list.get(index))){
											mainCount++;
										}else{
											errorCount++;
										}
									}
									progress.setProgress(index);		
									if(progress.isCancelled()){
										JOptionPane.showMessageDialog(null, "���������� �������� ��������","��������",JOptionPane.WARNING_MESSAGE);
										break;
									}
								}
							} catch (SQLException e) {
								JOptionPane.showMessageDialog(null, e.getLocalizedMessage()+System.lineSeparator()+"���������� �������� ��������","������",JOptionPane.ERROR_MESSAGE);
								progress.disactivated();	
							}
							JOptionPane.showMessageDialog(null, "��������� ������� � "+mainCount+" ����"+System.lineSeparator()+
									"�� ��������� ��-�� ������ "+errorCount+" ����","����������",JOptionPane.INFORMATION_MESSAGE);
							progress.disactivated();
						}else{
							JOptionPane.showMessageDialog(null, "�� �������� ������ ���� ��� ���������� �������","������",JOptionPane.ERROR_MESSAGE);
						}
						return null;
					}
				};
				worker.execute();
			}else{
				JOptionPane.showMessageDialog(null, "������ �� ���������","������",JOptionPane.ERROR_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(null, "����������� �� ��������","������",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void updateFast(){
		if(EVatServiceSingleton.getInstance().isAuthorized()){
			if(EVatServiceSingleton.getInstance().isConnected()){
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
					@Override
					protected Void doInBackground() throws Exception {
						List<String> list = WorkingOutcomingTable.Lists.selectNotSignedNumbersInvoice();
						if(list != null){
							int errorCount = 0;
							int mainCount = 0;
							LoadFileProgressBar progress = new LoadFileProgressBar(list.size()).activated();
							try{
								for(int index=0;index<list.size();index++){
									AvEStatus status = EVatServiceSingleton.getInstance().getService().getStatus(list.get(index));
									boolean isValid = status.verify();
									if(isValid){
										if(WorkingOutcomingTable.Update.updateStatus(status.getStatus(), list.get(index))){
											mainCount++;
										}else{
											errorCount++;
										}
									}
									progress.setProgress(index);		
									if(progress.isCancelled()){
										JOptionPane.showMessageDialog(null, "������ ����� ��������","��������",JOptionPane.WARNING_MESSAGE);
										break;
									}
								}
							}catch (SQLException e) {
								JOptionPane.showMessageDialog(null, e.getLocalizedMessage()+System.lineSeparator()+"���������� �������� ��������","������",JOptionPane.ERROR_MESSAGE);
								progress.disactivated();
							}
							JOptionPane.showMessageDialog(null, "��������� ������� � "+mainCount+" ����"+System.lineSeparator()+
									"�� ��������� ��-�� ������ "+errorCount+" ����","����������",JOptionPane.INFORMATION_MESSAGE);
							progress.disactivated();
						}else{
							JOptionPane.showMessageDialog(null, "�� �������� ������ ���� ��� ���������� �������","������",JOptionPane.ERROR_MESSAGE);
						}
						return null;
					}
				};
				worker.execute();
			}else{
				JOptionPane.showMessageDialog(null, "������ �� ���������","������",JOptionPane.ERROR_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(null, "����������� �� ��������","������",JOptionPane.ERROR_MESSAGE);
		}
	}
}
