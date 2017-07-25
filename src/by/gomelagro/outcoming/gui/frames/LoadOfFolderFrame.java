package by.gomelagro.outcoming.gui.frames;

import java.awt.Component;
import java.awt.Dialog.ModalExclusionType;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
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

import by.avest.edoc.client.AvEDoc;
import by.avest.edoc.client.AvEStatus;
import by.avest.edoc.client.AvETicket;
import by.avest.edoc.client.AvError;
import by.gomelagro.outcoming.gui.db.WorkingOutcomingTable;
import by.gomelagro.outcoming.gui.db.number.NumberInvoice;
import by.gomelagro.outcoming.gui.frames.count.ILoadCount;
import by.gomelagro.outcoming.gui.frames.count.ISendCount;
import by.gomelagro.outcoming.gui.frames.count.InsertSendCount;
import by.gomelagro.outcoming.gui.frames.count.UpdateCount;
import by.gomelagro.outcoming.gui.frames.folder.component.JFileCheckBoxList;
import by.gomelagro.outcoming.gui.frames.folder.component.list.FileCheckBoxList;
import by.gomelagro.outcoming.gui.frames.folder.list.WorkingFileList;
import by.gomelagro.outcoming.gui.frames.folder.models.FileCheckBoxListModel;
import by.gomelagro.outcoming.gui.frames.invoice.Invoice;
import by.gomelagro.outcoming.gui.frames.invoice.LoadInvoice;
import by.gomelagro.outcoming.gui.progress.LoadFileProgressBar;
import by.gomelagro.outcoming.properties.ApplicationProperties;
import by.gomelagro.outcoming.service.EVatServiceSingleton;
import by.gomelagro.outcoming.status.Status;

public class LoadOfFolderFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	
	private FileCheckBoxListModel model;
	private JFileCheckBoxList filesList;
	
	private JTextField folderPathTextField;
	private JPopupMenu buttonPopup;

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
		/*
		JMenuItem checkMenuItem = new JMenuItem("Проверить список");
		checkMenuItem.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent me) {
				if(EVatServiceSingleton.getInstance().isConnected()){
					sendListInvoices();
				}
				else{
					JOptionPane.showMessageDialog(null, "Сервис не подключен","Внимание",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		sendMenu.add(checkMenuItem);
		
		sendMenu.addSeparator();
		*/
		JMenuItem sendMenuItem = new JMenuItem("Отправить список");
		sendMenuItem.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent me) {
				if(EVatServiceSingleton.getInstance().isConnected()){
					int select = filesList.getSelectedIndex();
					sendListInvoices();
					model.clear();
					model.addElements(WorkingFileList.fillListOfAllFiles(folderPathTextField.getText().trim()));
					filesList.setSelectedIndex(select);
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
		
		//контекстное меню кнопки
		buttonPopup = new JPopupMenu();
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
		buttonPopup.add(browseMenuItem);
		
		buttonPopup.addSeparator();
		
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
		buttonPopup.add(browseUnloadedMenuItem);
		
		//контекстное меню списка
		
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
						showButtonPopup(me);
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
	private void showButtonPopup(MouseEvent me){
		Component component = (Component) me.getSource();
		Point point = component.getLocationOnScreen();
		buttonPopup.show(this,0,0);
		buttonPopup.setLocation(point.x,point.y+component.getHeight());
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
				List<NumberInvoice> listInvoice = new ArrayList<NumberInvoice>();
				listInvoice.clear();
				
				FileCheckBoxList list = filesList.getCheckedItems();
				if(list != null){
					LoadFileProgressBar progress = new LoadFileProgressBar(list.size()).activated();
					ISendCount insert = new InsertSendCount();					
					ILoadCount update = new UpdateCount();
								
					for(int index=0;index<list.size();index++){
						File file = new File(folderPathTextField.getText().trim()+"\\"+list.get(index).getValue().trim());
						if(!(file.exists() && file.isFile())){
							System.err.println("Файл "+folderPathTextField.getText().trim()+"\\"+list.get(index).getValue().trim()+" отсутствует");
							insert.addInValidCount();
							continue;
						}else{
							Invoice invoice = LoadInvoice.loadFile(folderPathTextField.getText().trim()+"\\"+list.get(index).getValue().trim());
							if(invoice != null){
								AvEDoc doc = EVatServiceSingleton.getInstance().getService().createEDoc();
								doc.getDocument().load(invoice.getContent());
								switch(WorkingOutcomingTable.Count.getCountRecord(invoice.getGeneral().getNumber())){
									case -1: JOptionPane.showMessageDialog(null, "Ошибка проверки наличия записи ЭСЧФ "+invoice.getGeneral().getNumber()+" в таблице","Ошибка",JOptionPane.ERROR_MESSAGE); insert.addErrorCount(); break;
									case 0:{
										if(doc.getDocument().validateXML(invoice.getXsdSchema())){
											doc.sign();
											insert.addValidCount();
											AvETicket ticket = EVatServiceSingleton.getInstance().getService().sendEDoc(doc);
											if(ticket.accepted()){
												int id = WorkingOutcomingTable.Insert.insertOutcomingFile(invoice);
												if(id > 0){
													if(WorkingOutcomingTable.Insert.insertOutcomingDocumentsFile(id, invoice)){
														listInvoice.add(new NumberInvoice().addId(String.valueOf(id)).addNumber(invoice.getGeneral().getNumber()));
														System.out.println("ЭСЧФ "+invoice.getGeneral().getNumber()+" принята к обработке: "+ticket.getMessage());
														insert.addAcceptCount();
													}else{
														insert.addErrorCount();
													}									
												}else{
													insert.addErrorCount();
												}
											}else{
												AvError err = ticket.getLastError();
												System.err.println("Ошибка: ЭСЧФ "+invoice.getGeneral().getNumber()+" не принята к обработке: "+err.getMessage());
												insert.addErrorCount();
											}
										}else{
											System.out.println("ЭСЧФ "+invoice.getGeneral().getNumber()+" не корректна");
											insert.addInValidCount();
										};
										break;
									}
									default:{
										System.out.println("ЭСЧФ "+invoice.getGeneral().getNumber()+" уже загружена на портал");
									}
								}
							}else{
								System.err.println("Файл "+list.get(index).getValue()+": ошибка загрузки");
								insert.addInValidCount();
							}
						}
						progress.setProgress(index);
						if(progress.isCancelled()){
							JOptionPane.showMessageDialog(null, "Загрузка ЭСЧФ на сайт отменена","Внимание",JOptionPane.WARNING_MESSAGE);
							System.out.println("//------");
							continue;
						}
					}
					JOptionPane.showMessageDialog(null, "Корректных ЭСЧФ - "+insert.getValidCount()+","+System.lineSeparator()+
														"из них: отправлено - "+insert.getAcceptCount()+","+System.lineSeparator()+	
														"        имеют ошибки при отправлении - "+insert.getErrorCount()+";"+System.lineSeparator()+
														"Некорректных ЭСЧФ - "+insert.getInValidCount() ,"Информация",JOptionPane.INFORMATION_MESSAGE);
					progress.disactivated();
					
					if(listInvoice != null){
						LoadFileProgressBar progressUpdate = new LoadFileProgressBar(listInvoice.size()).activated();					
															
						for(int index=0;index<listInvoice.size();index++){
							AvEStatus status = EVatServiceSingleton.getInstance().getService().getStatus(listInvoice.get(index).getNumber());
							boolean isValid = status.verify();
							if(isValid){
								if(!WorkingOutcomingTable.Field.getOutcomingStatus(listInvoice.get(index).getId()).equals(status.getStatus())){
									if(WorkingOutcomingTable.Insert.insertOutcomingStatusesFile(listInvoice.get(index).getId(), status.getStatus(), Status.valueEnOf(status.getStatus()))){
										update.addBaseCount();
									}else{
										update.addErrorCount();
									}
								}else{
									update.addMissCount();
								}
							}else{
								update.addErrorCount();
							}
							progressUpdate.setProgress(index);
							if(progressUpdate.isCancelled()){
								JOptionPane.showMessageDialog(null, "Обновление статусов ЭСЧФ после загрузки на сайт отменено","Внимание",JOptionPane.WARNING_MESSAGE);
								System.out.println("//------");
								continue;
							}
						}
						JOptionPane.showMessageDialog(null, "Обновлено статусов ЭСЧФ - "+update.getBaseCount()+","+System.lineSeparator()+	
															"имеют ошибки при обновлении - "+update.getErrorCount()+","+System.lineSeparator()+
															"пропущено ЭСЧФ - " + update.getMissCount(),"Информация",JOptionPane.INFORMATION_MESSAGE);
						progressUpdate.disactivated();
					}
					
				}else{
					JOptionPane.showMessageDialog(null, "Не выделено ни одной ЭСЧФ","Внимание",JOptionPane.WARNING_MESSAGE);
				}
				return null;
			}
			
		};
		worker.execute();
	}
	
	/* Проверка полей
	 * if(!invoice.verifyLimitationFormat())
		System.err.println("ЭСЧФ "+invoice.getGeneral().getNumber()+" имеет ошибки в ограничении форматов");
	if(!invoice.verifyMandatoryFieldFilling())
		System.err.println("ЭСЧФ "+invoice.getGeneral().getNumber()+" имеет ошибки в обязательности заполнения полей");								
	if(!invoice.verifyRuleFieldFilling())
		System.err.println("ЭСЧФ "+invoice.getGeneral().getNumber()+" имеет ошибки в правилах заполнения форматов");
	 */
	
	
	/* Замена значения в поле
	 * Document document = ConverterXml.byteArray2Xml(doc.getDocument().getEncoded());
	
	if(setXmlNodeValue(document,"issuance/general/documentType","TEST")){
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		Result output = new StreamResult(new File(folderPathTextField.getText().trim()+"\\"+list.get(index).getValue().trim()+"_"));
		Source input = new DOMSource(document);
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.transform(input, output);
		System.out.println("Изменено");
	}else{
		System.out.println("Не изменено");
	}
	File fileConv = new File(folderPathTextField.getText().trim()+"\\"+list.get(index).getValue().trim()+"_");
	data = readFile(fileConv);
	String strDataConv = new String(data,"Cp1251");
	
	AvEDoc docConv = EVatServiceSingleton.getInstance().getService().createEDoc();
	docConv.getDocument().load(strDataConv.getBytes(Charset.forName("UTF-8")));
	
	
	pathXSD = docConv.getDocument().getXmlNodeValue("issuance/general/documentType");
	System.out.println(pathXSD);*/
	
	/* установить значение
	 * public static boolean setXmlNodeValue(Node child, String nodePath, String value){
		boolean result = false;
		if(nodePath == null){
			return result;
		}
		
		String elemPath = null;
		String attrName = null;
		int index = 0;
		if ((index = nodePath.indexOf("@")) < 0) {
			elemPath = nodePath;
			attrName = null;
		} else {
			elemPath = nodePath.substring(0, index);
			attrName = nodePath.substring(index + 1);
		}
		
		StringTokenizer st = new StringTokenizer(elemPath, "/");
		while (st.hasMoreTokens()) {
			child = getChild(child, st.nextToken());
			if (child == null) {
				return result;
			}
		}
		
		if (attrName != null) {
			NamedNodeMap attrsMap = child.getAttributes();
			Node attr = attrsMap.getNamedItem(attrName);
			if (attr != null){
				attr.setNodeValue(value);
				result = true;
			}
		} else {
			child.setTextContent(value);
			result = true;
		}
		
		return result;
	}
	*/

	/* получить ноду
	 * private static Node getChild(Node parentNode, String childName) {
		Node result = null;
		NodeList providerChildren = parentNode.getChildNodes();
		for (int i = 0; i < providerChildren.getLength(); ++i) {
			Node node = providerChildren.item(i);
			if (node.getNodeName().equalsIgnoreCase(childName)) {
				result = node;
			}
		}
		return result;
	}
	*/
		
}
