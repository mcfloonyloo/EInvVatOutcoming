package by.gomelagro.outcoming.gui.frames.folder.list;

import java.awt.Color;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.gomelagro.outcoming.gui.db.WorkingOutcomingTable;
import by.gomelagro.outcoming.gui.frames.folder.component.FileCheckBoxFont;
import by.gomelagro.outcoming.gui.frames.folder.component.JFileCheckBox;
import by.gomelagro.outcoming.gui.frames.folder.extractor.NumberInvoiceExtractor;
import by.gomelagro.outcoming.gui.frames.folder.validator.FileNameInvoiceValidator;

public class WorkingFileList {

	//заполнить список всех файлов
	public static List<JFileCheckBox> fillListOfAllFiles(String path){
		List<JFileCheckBox> list = new ArrayList<JFileCheckBox>();
		list.clear();
		
		boolean block = false;
		Color foreColor = Color.BLACK;
		Color backColor = Color.WHITE;
		File[] fList = new File(path).listFiles();
		for(int index=0;index<fList.length;index++){
			if(fList[index].isFile()){
				if(FileNameInvoiceValidator.validate(fList[index].getName())){
					try {
						if(!WorkingOutcomingTable.Count.isNumberInvoice(NumberInvoiceExtractor.getNumberInvoice(fList[index].getName()))){
							foreColor = FileCheckBoxFont.getGreenFore();
							backColor = FileCheckBoxFont.getGreenBack();
							block = false;
						}else{
							foreColor = FileCheckBoxFont.getRedFore();
							backColor = FileCheckBoxFont.getRedBack();
							block = true;
						}
					} catch (SQLException e) {
						System.err.println(fList[index].getName()+": "+e.getLocalizedMessage());
						block = true;
					}
				}else{
					foreColor = FileCheckBoxFont.getBlackFore();
					backColor = FileCheckBoxFont.getBlackBack();
					block = true;
				}
				list.add(new JFileCheckBox.Builder()
						.setValue(fList[index].getName())
						.setChecked(false)
						.setBlocked(block)
						.setForeColor(foreColor)
						.setBackColor(backColor)
						.build());
			}
		}
		return list;
	}
	
	//заполнить список незагруженных файлов
	public static List<JFileCheckBox> fillListOfUnloadedFiles(String path){
		List<JFileCheckBox> list = new ArrayList<JFileCheckBox>();
		list.clear();
		
		boolean block = false;
		Color foreColor = Color.BLACK;
		Color backColor = Color.WHITE;
		File[] fList = new File(path).listFiles();
		for(int index=0;index<fList.length;index++){
			if(fList[index].isFile()){
				if(FileNameInvoiceValidator.validate(fList[index].getName())){
					try {
						if(WorkingOutcomingTable.Count.isNumberInvoice(NumberInvoiceExtractor.getNumberInvoice(fList[index].getName()))){
							foreColor = FileCheckBoxFont.getGreenFore();
							backColor = FileCheckBoxFont.getGreenBack();
							block = false;
							list.add(new JFileCheckBox.Builder()
									.setValue(fList[index].getName())
									.setChecked(false)
									.setBlocked(block)
									.setForeColor(foreColor)
									.setBackColor(backColor)
									.build());
						}
					} catch (SQLException e) {
						System.err.println(fList[index].getName()+": "+e.getLocalizedMessage());
						block = true;
					}
				}
			}	

		}
		return list;
	}

	
}
