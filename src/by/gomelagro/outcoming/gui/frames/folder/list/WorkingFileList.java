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
import by.gomelagro.outcoming.gui.frames.invoice.Invoice;
import by.gomelagro.outcoming.gui.frames.invoice.LoadInvoice;
import by.gomelagro.outcoming.gui.frames.invoice.verification.PositiveAmountsTestInvoice;
import by.gomelagro.outcoming.gui.frames.invoice.verification.SmallTestInvoice;
import by.gomelagro.outcoming.gui.frames.invoice.verification.XMLValidationTestInvoice;

public class WorkingFileList {

	//��������� ������ ���� ������
	public static List<JFileCheckBox> fillListOfAllFiles(String path){
		List<JFileCheckBox> list = new ArrayList<JFileCheckBox>();
		list.clear();
		
		boolean block = false;
		Color foreColor = Color.BLACK;
		Color backColor = Color.WHITE;
		File[] fList = new File(path).listFiles();
		String errorLine = "";
		for(int index=0;index<fList.length;index++){
			if(fList[index].isFile()){
				if((FileNameInvoiceValidator.validate(fList[index].getName()))){
					Invoice invoice = LoadInvoice.loadFile(fList[index].getAbsolutePath());
					if(invoice != null){
						try {
							if(!WorkingOutcomingTable.Count.isNumberInvoice(NumberInvoiceExtractor.getNumberInvoice(fList[index].getName()))){
								XMLValidationTestInvoice xmlTest = new XMLValidationTestInvoice(invoice).test();
								//XMLValidationTestInvoice xmlTest = new XMLValidationTestInvoice(invoice);
								if(!xmlTest.getList().getFlagErrorResult()){
									errorLine = xmlTest.getErrorLine();
									foreColor = FileCheckBoxFont.getPurpleFore();
									backColor = FileCheckBoxFont.getPurpleBack();
									block = false;
								}else{
									SmallTestInvoice test = new SmallTestInvoice(invoice).test();
									if(!test.getList().getFlagErrorResult()){
										errorLine = test.getErrorLine();
										foreColor = FileCheckBoxFont.getOrangeFore();
										backColor = FileCheckBoxFont.getOrangeBack();
										block = false;
									}else{
										PositiveAmountsTestInvoice positive = new PositiveAmountsTestInvoice(invoice).test();
										if(positive.getList().getFlagErrorResult()){
											
											errorLine = "";
											foreColor = FileCheckBoxFont.getGreenFore();
											backColor = FileCheckBoxFont.getGreenBack();
											block = false;
										}else{
											errorLine = positive.getErrorLine();
											foreColor = FileCheckBoxFont.getBlueFore();
											backColor = FileCheckBoxFont.getBlueBack();
											block = false;
										}
									}
								}
									
							}else{
								errorLine = "";
								foreColor = FileCheckBoxFont.getRedFore();
								backColor = FileCheckBoxFont.getRedBack();
								block = true;
							}	
						} catch (SQLException e) {
							System.err.println(fList[index].getName()+": "+e.getLocalizedMessage());
							block = true;
						}
					}else{
						errorLine = "";
						foreColor = FileCheckBoxFont.getBlackFore();
						backColor = FileCheckBoxFont.getBlackBack();
						block = true;
					}
				}else{
					errorLine = "";
					foreColor = FileCheckBoxFont.getBlackFore();
					backColor = FileCheckBoxFont.getBlackBack();
					block = true;
				}
				JFileCheckBox checkBox = new JFileCheckBox.Builder()
						.setValue(fList[index].getName())
						.setValueFull(path+"/"+fList[index].getName())
						.setChecked(false)
						.setBlocked(block)
						.setForeColor(foreColor)
						.setBackColor(backColor)
						.build().setToolTip(errorLine);
				list.add(checkBox);
			}
		}
		return list;
	}
	
	//��������� ������ ������������� ������
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
									.setValueFull(path+"/"+fList[index].getName())
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
