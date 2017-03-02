package by.gomelagro.outcoming.gui.frames;

import java.awt.Component;
import java.awt.Dialog.ModalExclusionType;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import by.avest.edoc.client.AvDocException;
import by.avest.edoc.client.AvEDoc;
import by.avest.edoc.client.AvETicket;
import by.avest.edoc.client.AvError;
import by.avest.edoc.tool.ToolException;
import by.gomelagro.outcoming.gui.db.WorkingOutcomingTable;
import by.gomelagro.outcoming.gui.frames.folder.component.JFileCheckBox;
import by.gomelagro.outcoming.gui.frames.folder.component.JFileCheckBoxList;
import by.gomelagro.outcoming.gui.frames.folder.list.WorkingFileList;
import by.gomelagro.outcoming.gui.frames.folder.models.FileCheckBoxListModel;
import by.gomelagro.outcoming.gui.progress.LoadFileProgressBar;
import by.gomelagro.outcoming.properties.ApplicationProperties;
import by.gomelagro.outcoming.service.EVatServiceSingleton;

public class LoadOfFolderFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	
	private FileCheckBoxListModel model;
	private JFileCheckBoxList filesList;
	
	private JTextField folderPathTextField;
	private JPopupMenu popup;

	/**
	 * Create the frame.
	 */
	public LoadOfFolderFrame() {
		initialize();
	}
	
	private void initialize(){
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setType(Type.UTILITY);
		setTitle("Загрузка ЭСЧФ из папки");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 720, 520);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu selectMenu = new JMenu("Выделение");
		menuBar.add(selectMenu);
		
		JMenuItem selectAllMenuItem = new JMenuItem("Выделить всё");
		selectAllMenuItem.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent me) {
				filesList.selectAll();
			}
		});
		selectMenu.add(selectAllMenuItem);
		
		JMenuItem unselectAllMenuItem = new JMenuItem("Снять выделение");
		unselectAllMenuItem.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent me) {
				filesList.unselectAll();
			}
		});
		selectMenu.add(unselectAllMenuItem);
		
		JMenu sendMenu = new JMenu("Отправление");
		menuBar.add(sendMenu);
		
		JMenuItem sendMenuItem = new JMenuItem("Отправить список...");
		sendMenuItem.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent me) {
				if(EVatServiceSingleton.getInstance().isConnected()){
					//sendListInvoices();
					sendListInvoices();
				}
				else{
					JOptionPane.showMessageDialog(null, "Сервис не подключен","Внимание",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		sendMenu.add(sendMenuItem);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{20, 0, 0, 0, 20, 0};
		gbl_contentPane.rowHeights = new int[]{20, 0, 0, 0, 20, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
				
		JLabel folderPathLabel = new JLabel("Путь к папке ");
		GridBagConstraints gbc_folderPathLabel = new GridBagConstraints();
		gbc_folderPathLabel.anchor = GridBagConstraints.EAST;
		gbc_folderPathLabel.insets = new Insets(0, 0, 5, 5);
		gbc_folderPathLabel.gridx = 1;
		gbc_folderPathLabel.gridy = 1;
		contentPane.add(folderPathLabel, gbc_folderPathLabel);
		
		folderPathTextField = new JTextField();
		folderPathTextField.setEnabled(false);
		GridBagConstraints gbc_folderPathTextField = new GridBagConstraints();
		gbc_folderPathTextField.insets = new Insets(0, 0, 5, 5);
		gbc_folderPathTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_folderPathTextField.gridx = 2;
		gbc_folderPathTextField.gridy = 1;
		contentPane.add(folderPathTextField, gbc_folderPathTextField);
		folderPathTextField.setColumns(10);
		
		//контекстное меню
		popup = new JPopupMenu();
		JMenuItem browseMenuItem = new JMenuItem("Обзор...");
		browseMenuItem.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent me) {
				//выбор папки из диалогового меню
				model.clear();
				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(false);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(chooser.showDialog(null,"Выбрать папку") == JFileChooser.APPROVE_OPTION){
					folderPathTextField.setText(chooser.getSelectedFile().getAbsolutePath());
					if(!folderPathTextField.getText().isEmpty()){
						model.addElements(WorkingFileList.fillListOfAllFiles(folderPathTextField.getText().trim()));
					}
				}
			}
		});
		popup.add(browseMenuItem);
		
		popup.addSeparator();
		
		JMenuItem browseUnloadedMenuItem = new JMenuItem("Обзор... (незагруженные)");
		browseUnloadedMenuItem.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent me) {
				//выбор папки из диалогового меню
				model.clear();
				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(false);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(chooser.showDialog(null,"Выбрать папку") == JFileChooser.APPROVE_OPTION){
					folderPathTextField.setText(chooser.getSelectedFile().getAbsolutePath());
					if(!folderPathTextField.getText().isEmpty()){
						model.addElements(WorkingFileList.fillListOfUnloadedFiles(folderPathTextField.getText().trim()));
					}
				}
			}
		});
		popup.add(browseUnloadedMenuItem);
		
		JButton loadButton = new JButton("Загрузить");
		loadButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {	
				switch(me.getButton()){
					case MouseEvent.BUTTON1:{
						folderPathTextField.setText(ApplicationProperties.getInstance().getFolderInvoicePath());
						if(!folderPathTextField.getText().isEmpty()){
							model.addElements(WorkingFileList.fillListOfAllFiles(folderPathTextField.getText().trim()));
						}
						break;
					}
					case MouseEvent.BUTTON3: {
						showPopup(me);
						break;
					}
				}
			}
		});
		GridBagConstraints gbc_loadButton = new GridBagConstraints();
		gbc_loadButton.insets = new Insets(0, 0, 5, 5);
		gbc_loadButton.gridx = 3;
		gbc_loadButton.gridy = 1;
		contentPane.add(loadButton, gbc_loadButton);
		
		model = new FileCheckBoxListModel();
		
		filesList = new JFileCheckBoxList();
		filesList.setModel(model);
		JScrollPane filesScrollPane = new JScrollPane(filesList);
		filesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		filesScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_filesList = new GridBagConstraints();
		gbc_filesList.gridwidth = 3;
		gbc_filesList.insets = new Insets(0, 0, 5, 5);
		gbc_filesList.fill = GridBagConstraints.BOTH;
		gbc_filesList.gridx = 1;
		gbc_filesList.gridy = 2;
		contentPane.add(filesScrollPane, gbc_filesList);
	}
	
	//показать контекстное меню
	private void showPopup(MouseEvent me){
		Component component = (Component) me.getSource();
		Point point = component.getLocationOnScreen();
		popup.show(this,0,0);
		popup.setLocation(point.x,point.y+component.getHeight());
	}	
	
	/*
	private void sendListInvoice(){
		JFileChooser chooser = new JFileChooser();
		int res = chooser.showDialog(null, "Открыть");
		if(res == JFileChooser.APPROVE_OPTION){
			File file = chooser.getSelectedFile();
			if(!(file.exists() && file.isFile())){
				System.out.println("файл отсутствует");
				return;
			}else{
				byte[] data;
				try {
					data = readFile(file);
					String strData = new String(data,"Cp1251");
					System.out.println(strData);
					AvEDoc doc = EVatServiceSingleton.getInstance().getService().createEDoc();
					doc.getDocument().load(strData.getBytes(Charset.forName("UTF-8")));
					
					String pathXSD = doc.getDocument().getXmlNodeValue("issuance/general/documentType");
					System.out.println(pathXSD);
					System.out.println(file.getAbsolutePath());
					System.out.println(ApplicationProperties.getInstance().getFolderXsdPath());

					
					byte[] xsdSchema = loadXsdSchema(ApplicationProperties.getInstance().getFolderXsdPath().trim(), pathXSD);
					
					if(doc.getDocument().validateXML(xsdSchema)){
						System.out.println(doc.getDocument().getXmlNodeValue("issuance/general/number")+": true");
						doc.sign();
						AvETicket ticket = EVatServiceSingleton.getInstance().getService().sendEDoc(doc);
						if(ticket.accepted()){
							System.out.println("Счет/фактура НДС принята к обработке: "+ticket.getMessage());
						}else{
							AvError err = ticket.getLastError();
							System.err.println("Ошибка: ЭСЧФ не принят. Сообщение: "+ err.getMessage() + ".");
						}
					}else{
						System.out.println(doc.getDocument().getXmlNodeValue("issuance/general/number")+": false");
					};
					
					System.out.println("OK");
				} catch (IOException | AvDocException | ToolException | GeneralSecurityException | ParseException e) {
					System.err.println(e.getLocalizedMessage());
				}
				return;
			}
		}
	}
	*/
	
	private void sendListInvoices(){
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){

			@Override
			protected Void doInBackground() throws Exception {
				List<JFileCheckBox> list = filesList.getCheckedItems();
				if(list != null){
					LoadFileProgressBar progress = new LoadFileProgressBar(list.size()).activated();
					int validCount = 0;
					int invalidCount = 0;
					int acceptCount = 0;
					int errorCount = 0;
								
					for(int index=0;index<list.size();index++){
						File file = new File(folderPathTextField.getText().trim()+"\\"+list.get(index).getValue().trim());
						if(!(file.exists() && file.isFile())){
							System.out.println("файл отсутствует");
							invalidCount++;
							continue;
						}else{
							byte[] data;
							try {
								data = readFile(file);
								String strData = new String(data,"Cp1251");
								
								AvEDoc doc = EVatServiceSingleton.getInstance().getService().createEDoc();
								doc.getDocument().load(strData.getBytes(Charset.forName("UTF-8")));
								
								String pathXSD = doc.getDocument().getXmlNodeValue("issuance/general/documentType");
																
								byte[] xsdSchema = loadXsdSchema(ApplicationProperties.getInstance().getFolderXsdPath().trim(), pathXSD);
								
								String numberInvoice = doc.getDocument().getXmlNodeValue("issuance/general/number");
								switch(WorkingOutcomingTable.Count.getCountRecord(numberInvoice)){
									case -1: JOptionPane.showMessageDialog(null, "Ошибка проверки наличия записи ЭСЧФ "+numberInvoice+" в таблице","Ошибка",JOptionPane.ERROR_MESSAGE); errorCount++; break;
									case 0: {
										if(doc.getDocument().validateXML(xsdSchema)){
											doc.sign();
											validCount++;
											AvETicket ticket = EVatServiceSingleton.getInstance().getService().sendEDoc(doc);
											if(ticket.accepted()){
												System.out.println("ЭСЧФ "+numberInvoice+" принята к обработке: "+ticket.getMessage());
												acceptCount++;
											}else{
												AvError err = ticket.getLastError();
												System.err.println("Ошибка: ЭСЧФ "+numberInvoice+" не принята к обработке: "+err.getMessage());
												errorCount++;
											}
										}else{
											System.out.println("ЭСЧФ "+numberInvoice+" не корректна");
											invalidCount++;
										};
									}
									default:{
										System.out.println("ЭСЧФ "+numberInvoice+" уже загружена на портал");
									}
								}
								
							} catch (IOException | AvDocException | ToolException | ParseException e) {
								System.err.println("Файл "+list.get(index).getValue()+": исключение класса "+e.getClass()+" - "+e.getLocalizedMessage());
								invalidCount++;
								continue;
							}
						}
						progress.setProgress(index);
						if(progress.isCancelled()){
							JOptionPane.showMessageDialog(null, "Выгрузка ЭСЧФ на сайт отменена","Внимание",JOptionPane.WARNING_MESSAGE);
							System.out.println("//------");
							continue;
						}
					}
					JOptionPane.showMessageDialog(null, "Корректных ЭСЧФ - "+validCount+","+System.lineSeparator()+
														"из них: отправлено - "+acceptCount+","+System.lineSeparator()+	
														"        имеют ошибки при отправлении - "+errorCount+";"+System.lineSeparator()+
														"Некорректных ЭСЧФ - "+invalidCount ,"Информация",JOptionPane.INFORMATION_MESSAGE);
					progress.disactivated();
				}else{
					JOptionPane.showMessageDialog(null, "Не выделено ни одной ЭСЧФ","Внимание",JOptionPane.WARNING_MESSAGE);
				}
				return null;
			}
			
		};
		worker.execute();
	}
	
	private static byte[] readFile(File file) throws ToolException {
		byte[] fileData = new byte[(int) file.length()];
		DataInputStream dis = null;
		try {
			dis = new DataInputStream(new FileInputStream(file));
			dis.readFully(fileData);
		} catch (IOException e) {
		} finally {
			if (dis != null) {
				try {
					dis.close();
				} catch (IOException localIOException2) {
				}
			}
		}
		return fileData;
	}
	
	private byte[] loadXsdSchema(String xsdFolderName, String doctype) throws ToolException {
		File xsdFile = null;
		doctype = (doctype == null) ? "" : doctype;

		if ((doctype.equalsIgnoreCase("ORIGINAL")) || (doctype.equalsIgnoreCase("ADD_NO_REFERENCE")))
			xsdFile = new File(xsdFolderName, "MNSATI_original.xsd");
		else if (doctype.equalsIgnoreCase("FIXED"))
			xsdFile = new File(xsdFolderName, "MNSATI_fixed.xsd");
		else if (doctype.equalsIgnoreCase("ADDITIONAL"))
			xsdFile = new File(xsdFolderName, "MNSATI_additional.xsd");
		else {
			throw new ToolException(new StringBuilder().append("Неизвестный тип счета-фактуры НДС '").append(doctype)
					.append("'.").toString());
		}

		if ((!(xsdFile.exists())) && (!(xsdFile.isFile()))) {
			throw new ToolException(new StringBuilder().append("Невозможно загрузить XSD файл '")
					.append(xsdFile.getAbsolutePath()).append("'").toString());
		}

		byte[] result = readFile(xsdFile);

		return result;
		}
		
}
