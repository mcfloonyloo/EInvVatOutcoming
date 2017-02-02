package by.gomelagro.outcoming.gui.frames;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import by.gomelagro.outcoming.gui.console.JConsole;
import by.gomelagro.outcoming.gui.db.ConnectionDB;
import by.gomelagro.outcoming.gui.db.WorkingOutcomingTable;
import by.gomelagro.outcoming.gui.db.files.WorkingFiles;
import by.gomelagro.outcoming.gui.frames.enstatus.UpdateEnStatus;
import by.gomelagro.outcoming.gui.progress.LoadFileProgressBar;
import by.gomelagro.outcoming.properties.ApplicationProperties;
import by.gomelagro.outcoming.service.EVatServiceSingleton;
import by.gomelagro.outcoming.service.certificate.Certificate;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;

	private JTextPane console;
	private JMenuItem authMenuItem;
	private JMenuItem infoCertMenuItem;
	private JMenuItem connectMenuItem;
	private JMenuItem disconnectMenuItem;
	private static JMenuItem loadFileMenuItem;
	private JMenuItem loadFolderMenuItem;
	private JMenuItem updateStatusMenuItem;
	private JMenuItem fastUpdateStatusMenuItem;
	private JMenuItem saveOneDayMenuItem;
	private JMenuItem saveBetweenMenuItem;
	
	private JLabel allInvoicesLabel;
	private JLabel completedLabel;
	private JLabel noCompletedLabel;
	private JLabel cancelledLabel;
	private JLabel undeterminedLabel;
	
	private JComboBox<String> yearComboBox;
	
	private final String title = "���������� ��� ��������� ��������� ���� v0.3.5";
	
	static{
		ApplicationProperties.getInstance();	
		System.setProperty("by.avest.loader.shared","true");
		System.setProperty("java.library.path",ApplicationProperties.getInstance().getLibraryPath().trim());
		System.setProperty("classpath", ApplicationProperties.getInstance().getClassPath().trim());
	}
	
	/**
	 * Create the application.
	 */
	public MainFrame() {
		if(WorkingFiles.isFile(ApplicationProperties.getInstance().getDbPath())){
			initialize();
			if(WorkingOutcomingTable.getCountAll() > 0)
				updateMainPanel(yearComboBox.getItemAt(yearComboBox.getSelectedIndex()));
			setVisible(true);
		}else{
			JOptionPane.showMessageDialog(null, "����������� ���� ���� ������."+System.lineSeparator()+
					"�������� ���� ���� ������.","��������",JOptionPane.WARNING_MESSAGE);
			JFileChooser chooser = new JFileChooser();
			chooser.setMultiSelectionEnabled(false);
			chooser.addChoosableFileFilter(new FileNameExtensionFilter("SQLite Databases (.sqlite)", "sqlite"));
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if(chooser.showDialog(null,"��������� ����") == JFileChooser.APPROVE_OPTION){
				ApplicationProperties.getInstance().setDbPath(chooser.getSelectedFile().getAbsolutePath().trim());
				ApplicationProperties.getInstance().saveProperties();
				JOptionPane.showMessageDialog(null, "����� ���� � ����� ���� ������ ������� � ���������� ���������."+System.lineSeparator()+
						"����������, �������� ��������� ���������","����������",JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {	
		try {
			ConnectionDB.getInstance().load();
		} catch (ClassNotFoundException | SQLException e) {
			JOptionPane.showMessageDialog(null,e.getLocalizedMessage(),"������",JOptionPane.ERROR_MESSAGE);
		}
		setTitle(title);
		setResizable(false);
		setBounds(100, 100, 1065, 630);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{400, 129, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_mainPanel = new GridBagConstraints();
		gbc_mainPanel.insets = new Insets(0, 0, 5, 0);
		gbc_mainPanel.fill = GridBagConstraints.BOTH;
		gbc_mainPanel.gridx = 0;
		gbc_mainPanel.gridy = 0;
		getContentPane().add(mainPanel, gbc_mainPanel);
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[]{20, 20, 0, 60, 70, 0, 0};
		gbl_mainPanel.rowHeights = new int[]{20, 20, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_mainPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_mainPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		mainPanel.setLayout(gbl_mainPanel);
		
		JLabel lblyearLabel = new JLabel("���: ");
		lblyearLabel.setFont(new Font("Courier New", Font.BOLD, 11));
		GridBagConstraints gbc_lblyearLabel = new GridBagConstraints();
		gbc_lblyearLabel.anchor = GridBagConstraints.EAST;
		gbc_lblyearLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblyearLabel.gridx = 1;
		gbc_lblyearLabel.gridy = 1;
		mainPanel.add(lblyearLabel, gbc_lblyearLabel);
		
		yearComboBox = new JComboBox<String>();
		yearComboBox.setFont(new Font("Courier New", Font.BOLD, 12));
		yearComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				if(evt.getStateChange() == ItemEvent.SELECTED){
					updateMainPanel(yearComboBox.getItemAt(yearComboBox.getSelectedIndex()));
				}
			}
		});
		if(WorkingOutcomingTable.getCountAll() > 0){
			if(fillYear()){
				yearComboBox.setSelectedIndex(0);
			}else{
				JOptionPane.showMessageDialog(null, "���������� ���������� �������������������� ������","������",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		GridBagConstraints gbc_yearComboBox = new GridBagConstraints();
		gbc_yearComboBox.anchor = GridBagConstraints.SOUTH;
		gbc_yearComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_yearComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_yearComboBox.gridx = 2;
		gbc_yearComboBox.gridy = 1;
		mainPanel.add(yearComboBox, gbc_yearComboBox);
		
		JList<String> titleList = new JList<String>();
		titleList.setEnabled(false);
		titleList.setFont(new Font("Courier New", Font.PLAIN, 11));
		titleList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		titleList.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = new String[] {"[��������� ������ ������, ���������� �������� �� ���� � ��� �� ������� ���������� ����]"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		GridBagConstraints gbc_titleList = new GridBagConstraints();
		gbc_titleList.anchor = GridBagConstraints.SOUTH;
		gbc_titleList.insets = new Insets(0, 0, 5, 0);
		gbc_titleList.fill = GridBagConstraints.HORIZONTAL;
		gbc_titleList.gridx = 5;
		gbc_titleList.gridy = 1;
		mainPanel.add(titleList, gbc_titleList);
		
		JList<String> vatList = new JList<String>();
		vatList.setFont(new Font("Courier New", Font.PLAIN, 11));
		vatList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_vatList = new GridBagConstraints();
		gbc_vatList.gridheight = 12;
		gbc_vatList.fill = GridBagConstraints.BOTH;
		gbc_vatList.gridx = 5;
		gbc_vatList.gridy = 2;
		mainPanel.add(vatList, gbc_vatList);
		
		JLabel lblAllInvoicesLabel = new JLabel("����� ����: ");
		lblAllInvoicesLabel.setFont(new Font("Courier New", Font.PLAIN, 11));
		GridBagConstraints gbc_lblAllInvoicesLabel = new GridBagConstraints();
		gbc_lblAllInvoicesLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblAllInvoicesLabel.gridwidth = 2;
		gbc_lblAllInvoicesLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblAllInvoicesLabel.gridx = 1;
		gbc_lblAllInvoicesLabel.gridy = 2;
		mainPanel.add(lblAllInvoicesLabel, gbc_lblAllInvoicesLabel);
		
		allInvoicesLabel = new JLabel("");
		allInvoicesLabel.setFont(new Font("Courier New", Font.BOLD, 11));
		GridBagConstraints gbc_allInvoicesLabel = new GridBagConstraints();
		gbc_allInvoicesLabel.anchor = GridBagConstraints.SOUTHEAST;
		gbc_allInvoicesLabel.insets = new Insets(0, 0, 5, 5);
		gbc_allInvoicesLabel.gridx = 3;
		gbc_allInvoicesLabel.gridy = 2;
		mainPanel.add(allInvoicesLabel, gbc_allInvoicesLabel);
		
		JLabel ofThemLabel = new JLabel("�� ���: ");
		ofThemLabel.setFont(new Font("Courier New", Font.PLAIN, 11));
		GridBagConstraints gbc_ofThemLabel = new GridBagConstraints();
		gbc_ofThemLabel.gridwidth = 2;
		gbc_ofThemLabel.anchor = GridBagConstraints.WEST;
		gbc_ofThemLabel.insets = new Insets(0, 0, 5, 5);
		gbc_ofThemLabel.gridx = 1;
		gbc_ofThemLabel.gridy = 3;
		mainPanel.add(ofThemLabel, gbc_ofThemLabel);
		
		JLabel lblCompletedLabel = new JLabel("���������: ");
		lblCompletedLabel.setFont(new Font("Courier New", Font.PLAIN, 11));
		GridBagConstraints gbc_lblCompletedLabel = new GridBagConstraints();
		gbc_lblCompletedLabel.anchor = GridBagConstraints.WEST;
		gbc_lblCompletedLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblCompletedLabel.gridx = 2;
		gbc_lblCompletedLabel.gridy = 4;
		mainPanel.add(lblCompletedLabel, gbc_lblCompletedLabel);
		
		completedLabel = new JLabel("");
		completedLabel.setFont(new Font("Courier New", Font.BOLD, 11));
		GridBagConstraints gbc_completedLabel = new GridBagConstraints();
		gbc_completedLabel.anchor = GridBagConstraints.EAST;
		gbc_completedLabel.insets = new Insets(0, 0, 5, 5);
		gbc_completedLabel.gridx = 3;
		gbc_completedLabel.gridy = 4;
		mainPanel.add(completedLabel, gbc_completedLabel);
		
		JLabel lblNoCompletedLabel = new JLabel("�� ���������: ");
		lblNoCompletedLabel.setFont(new Font("Courier New", Font.PLAIN, 11));
		GridBagConstraints gbc_lblNoCompletedLabel = new GridBagConstraints();
		gbc_lblNoCompletedLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNoCompletedLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNoCompletedLabel.gridx = 2;
		gbc_lblNoCompletedLabel.gridy = 5;
		mainPanel.add(lblNoCompletedLabel, gbc_lblNoCompletedLabel);
		
		noCompletedLabel = new JLabel("");
		noCompletedLabel.setFont(new Font("Courier New", Font.BOLD, 11));
		GridBagConstraints gbc_noCompletedLabel = new GridBagConstraints();
		gbc_noCompletedLabel.anchor = GridBagConstraints.EAST;
		gbc_noCompletedLabel.insets = new Insets(0, 0, 5, 5);
		gbc_noCompletedLabel.gridx = 3;
		gbc_noCompletedLabel.gridy = 5;
		mainPanel.add(noCompletedLabel, gbc_noCompletedLabel);
		
		JLabel lblCancelledLabel = new JLabel("������������: ");
		lblCancelledLabel.setFont(new Font("Courier New", Font.PLAIN, 11));
		GridBagConstraints gbc_lblCancelledLabel = new GridBagConstraints();
		gbc_lblCancelledLabel.anchor = GridBagConstraints.WEST;
		gbc_lblCancelledLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblCancelledLabel.gridx = 2;
		gbc_lblCancelledLabel.gridy = 6;
		mainPanel.add(lblCancelledLabel, gbc_lblCancelledLabel);
		
		cancelledLabel = new JLabel("");
		cancelledLabel.setFont(new Font("Courier New", Font.BOLD, 11));
		GridBagConstraints gbc_cancelledLabel = new GridBagConstraints();
		gbc_cancelledLabel.anchor = GridBagConstraints.EAST;
		gbc_cancelledLabel.insets = new Insets(0, 0, 5, 5);
		gbc_cancelledLabel.gridx = 3;
		gbc_cancelledLabel.gridy = 6;
		mainPanel.add(cancelledLabel, gbc_cancelledLabel);
		
		JLabel lblUndeterminedLabel = new JLabel("�� ����������: ");
		lblUndeterminedLabel.setFont(new Font("Courier New", Font.PLAIN, 11));
		GridBagConstraints gbc_lblUndeterminedLabel = new GridBagConstraints();
		gbc_lblUndeterminedLabel.anchor = GridBagConstraints.WEST;
		gbc_lblUndeterminedLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblUndeterminedLabel.gridx = 2;
		gbc_lblUndeterminedLabel.gridy = 7;
		mainPanel.add(lblUndeterminedLabel, gbc_lblUndeterminedLabel);
		
		undeterminedLabel = new JLabel("");
		GridBagConstraints gbc_undeterminedLabel = new GridBagConstraints();
		gbc_undeterminedLabel.anchor = GridBagConstraints.EAST;
		gbc_undeterminedLabel.insets = new Insets(0, 0, 5, 5);
		gbc_undeterminedLabel.gridx = 3;
		gbc_undeterminedLabel.gridy = 7;
		mainPanel.add(undeterminedLabel, gbc_undeterminedLabel);
		
		console = new JConsole();
		console.setFont(new Font("Courier New", Font.PLAIN, 11));
		JScrollPane scrollPane_console = new JScrollPane(console);
		scrollPane_console.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_console.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_console = new GridBagConstraints();
		gbc_console.anchor = GridBagConstraints.NORTH;
		gbc_console.fill = GridBagConstraints.BOTH;
		gbc_console.gridx = 0;
		gbc_console.gridy = 1;
		getContentPane().add(scrollPane_console, gbc_console);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("����");
		menuBar.add(fileMenu);
		
	    authMenuItem = new JMenuItem("�����������");
		authMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				if(authMenuItem.isEnabled())
					autherization();
			}
		});
		authMenuItem.setEnabled(true);
		fileMenu.add(authMenuItem);
		
		infoCertMenuItem = new JMenuItem("���������� � �����������");
		infoCertMenuItem.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent evt){
				if(infoCertMenuItem.isEnabled())
					showInfoCertificate();
			}
		});
		infoCertMenuItem.setEnabled(false);
		fileMenu.add(infoCertMenuItem);
		
		JSeparator separator = new JSeparator();
		fileMenu.add(separator);
		
		connectMenuItem = new JMenuItem("����������");
		connectMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(!authMenuItem.isEnabled()&&connectMenuItem.isEnabled())
					connect();
			}
		});
		connectMenuItem.setEnabled(false);
		fileMenu.add(connectMenuItem);
		
		disconnectMenuItem = new JMenuItem("���������");
		disconnectMenuItem.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e){
				if(!authMenuItem.isEnabled()&&!connectMenuItem.isEnabled()&&disconnectMenuItem.isEnabled()){
					disconnect();
				}
			}
		});
		disconnectMenuItem.setEnabled(false);
		fileMenu.add(disconnectMenuItem);
		
		JSeparator separatorUp = new JSeparator();
		fileMenu.add(separatorUp);
		
		JMenuItem Settings = new JMenuItem("���������");
		Settings.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				new SettingsFrame().open();
			}
		});
		fileMenu.add(Settings);
		
		JSeparator separatorDown = new JSeparator();
		fileMenu.add(separatorDown);
		
		JMenuItem exitMenuItem = new JMenuItem("�����");
		exitMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				exit();
			}
		});
		fileMenu.add(exitMenuItem);
		
		JMenu listMenu = new JMenu("������ ����");
		menuBar.add(listMenu);
		
		loadFileMenuItem = new JMenuItem("��������� �� �����...");
		loadFileMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				if(loadFileMenuItem.isEnabled()){
					loadFile();
					selectYear();
				}
			}
		});
		loadFileMenuItem.setEnabled(false);
		updateVisibleLoadFile();
		listMenu.add(loadFileMenuItem);
		
		loadFolderMenuItem = new JMenuItem("��������� �� �����...");
		loadFolderMenuItem.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent evt){
				if(loadFolderMenuItem.isEnabled()){
					new LoadOfFolderFrame().setVisible(true);
				}
			}
		});
		//loadFolderMenuItem.setEnabled(false);
		listMenu.add(loadFolderMenuItem);
		
		JSeparator upListSeparator = new JSeparator();
		listMenu.add(upListSeparator);
		
		updateStatusMenuItem = new JMenuItem("������ ���������� ��������");
		updateStatusMenuItem.addMouseListener(new MouseAdapter(){
			@Override 
			public void mousePressed(MouseEvent evt){
				if(updateStatusMenuItem.isEnabled()){
					UpdateEnStatus.updateFull();
					selectYear();
				}
			}
		});
		updateStatusMenuItem.setEnabled(false);
		listMenu.add(updateStatusMenuItem);
		
		fastUpdateStatusMenuItem = new JMenuItem("������� ���������� ��������");
		fastUpdateStatusMenuItem.addMouseListener(new MouseAdapter(){
			@Override 
			public void mousePressed(MouseEvent evt){
				if(fastUpdateStatusMenuItem.isEnabled()){
					UpdateEnStatus.updateFast();
					selectYear();
				}
			}
		});
		fastUpdateStatusMenuItem.setEnabled(false);
		listMenu.add(fastUpdateStatusMenuItem);
		
		JSeparator downListSeparator = new JSeparator();
		listMenu.add(downListSeparator);
		
		JMenu saveMenu = new JMenu("����� �� ����...");
		listMenu.add(saveMenu);
		
		saveOneDayMenuItem = new JMenuItem("... �� ���� ����");
		saveOneDayMenuItem.addMouseListener(new MouseAdapter(){
			@Override 
			public void mousePressed(MouseEvent evt){
				if(saveOneDayMenuItem.isEnabled()){
					new ReportOneDayFrame().open();
				}
			}
		});
		saveMenu.add(saveOneDayMenuItem);
		
		saveBetweenMenuItem = new JMenuItem("... �� ������");
		saveBetweenMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				if(saveBetweenMenuItem.isEnabled()){
					new ReportBetweenFrame().open();
				}
			}
		});
		saveMenu.add(saveBetweenMenuItem);
	}

	public static void updateVisibleLoadFile(){
		loadFileMenuItem.setVisible(ApplicationProperties.getInstance().getLoadfileMenuitem());
	}
	
	/**
	 * Processing methods fill combobox and labels
	 */	
	private void updateMainPanel(String year){
		if(yearComboBox.getModel().getSize() > 0){
			allInvoicesLabel.setText(String.valueOf(WorkingOutcomingTable.getCountAllInYear(year)));
			completedLabel.setText(String.valueOf(WorkingOutcomingTable.getCountCompleted(year)));
			noCompletedLabel.setText(String.valueOf(WorkingOutcomingTable.getCountNoCompleted(year)));
			cancelledLabel.setText(String.valueOf(WorkingOutcomingTable.getCountCancelled(year)));
			undeterminedLabel.setText(String.valueOf(WorkingOutcomingTable.getCountUndetermined(year)));				
		}else{
			allInvoicesLabel.setText("0");
			completedLabel.setText("0");
			noCompletedLabel.setText("0");
			cancelledLabel.setText("0");
			undeterminedLabel.setText("0");
		}
	}
	
	private boolean fillYear(){
		List<String> list = WorkingOutcomingTable.selectYearInvoice();
		ComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		if(list == null){
			return false;
		}
		for(int index=0;index<list.size();index++){
			((DefaultComboBoxModel<String>) model).addElement(list.get(index));
		}
		yearComboBox.setModel(model);
		return true;
	}
	
	private void selectYear(){
		if(WorkingOutcomingTable.getCountAll() > 0){
			yearComboBox.setSelectedIndex(0);
			updateMainPanel(yearComboBox.getItemAt(yearComboBox.getSelectedIndex()));
		}
	}
	
	/**
	 * Methods autherization and connection to service
	 */
	private void autherization(){
		EVatServiceSingleton.getInstance().autherization(ApplicationProperties.getInstance());
		if(EVatServiceSingleton.getInstance().isAuthorized()){
			System.out.println("����������� ��������");
			authMenuItem.setEnabled(false);
			connectMenuItem.setEnabled(true);
			disconnectMenuItem.setEnabled(false);
			infoCertMenuItem.setEnabled(true);
			loadFileMenuItem.setEnabled(true);
			setTitle(title+" ["+ Certificate.getInstance().getOrgName().trim() +" " +Certificate.getInstance().getLastName().trim()+" "+Certificate.getInstance().getFirstMiddleName()+"]");
		}
	}
	
	private void connect(){
		if(EVatServiceSingleton.getInstance().isAuthorized()){
			EVatServiceSingleton.getInstance().connect();
			if(EVatServiceSingleton.getInstance().isConnected()){
				console.setText("");
				System.out.println("����������� ��������");
				System.out.println("����������� � ������� "+ApplicationProperties.getInstance().getUrlService()+" ��������� �������");
				connectMenuItem.setEnabled(false);
				disconnectMenuItem.setEnabled(true);
				
				updateStatusMenuItem.setEnabled(true);
				fastUpdateStatusMenuItem.setEnabled(true);
			}else{
				System.err.println("������ ����������� � ������� "+ApplicationProperties.getInstance().getUrlService());
			}
		}
	}
	
	private void disconnect(){
		if(EVatServiceSingleton.getInstance().isAuthorized()){
			if(EVatServiceSingleton.getInstance().isConnected()){
				EVatServiceSingleton.getInstance().disconnect();
				if(!EVatServiceSingleton.getInstance().isConnected()){
					System.out.println("���������� �� ������� "+ApplicationProperties.getInstance().getUrlService()+" ��������� �������");
					connectMenuItem.setEnabled(true);
					disconnectMenuItem.setEnabled(false);
					
					updateStatusMenuItem.setEnabled(false);
					fastUpdateStatusMenuItem.setEnabled(false);
				}else{
					System.err.println("������ ���������� �� ������� "+ApplicationProperties.getInstance().getUrlService());
				}
			}
		}
	}
	
	private void loadFile(){
		JFileChooser chooser = new JFileChooser();
		int res = chooser.showDialog(null, "�������");
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
			@Override
			protected Void doInBackground() throws Exception {
				List<String> lines = null;
				if(res == JFileChooser.APPROVE_OPTION){
					try {
						lines = WorkingFiles.readCSVFile(chooser.getSelectedFile());
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"������",JOptionPane.ERROR_MESSAGE);
					}
					if(lines != null){
						int avialCount = 0;
						int errorCount = 0;
						int notavialCount = 0;
						int updateCount = 0;;
						LoadFileProgressBar progress = new LoadFileProgressBar(lines.size()).activated();
						//�������� ������� ��� � �����������
						String unp = "";
						if(Certificate.getInstance().getUnp2() == ""){//���� unp2 ������
							if(Certificate.getInstance().getUnp101() == ""){//���� unp101 ������
								progress.disactivated();
								JOptionPane.showMessageDialog(null, "�� ��������� ���. �������� ��������","������",JOptionPane.ERROR_MESSAGE);
							}else{
								unp = Certificate.getInstance().getUnp101();
							}
						}else{
							unp = Certificate.getInstance().getUnp2();
						}
						try{
							for(int index=0; index<lines.size();index++){
								String[] fields = lines.get(index).split(";");								
								if(fields[5].trim().equals(unp)){//�������� �� ������ �����������
								//if(fields[5].trim().equals("400047886")){
									System.out.println("������ "+index+": ������� ������ ������ �������� ���� �� �����");
								}else{
									switch(WorkingOutcomingTable.getCountRecord(fields[8])){
									case -1: JOptionPane.showMessageDialog(null, "������ �������� ������� ������ ���� "+fields[10]+" � �������","������",JOptionPane.ERROR_MESSAGE); errorCount++; break;
									case  0: if(WorkingOutcomingTable.insertIncoming(fields)) {notavialCount++;}else{errorCount++;} break;
									case  1: if(WorkingOutcomingTable.updateStatusFromFile(fields[12], fields[10])){updateCount++;}else{errorCount++;} break;
									default: avialCount++; break;
									}
									progress.setProgress(index);		
									if(progress.isCancelled()){
										JOptionPane.showMessageDialog(null, "�������� ����� ��������","��������",JOptionPane.WARNING_MESSAGE);
										break;
									}
								}
							}
						} catch (SQLException | ParseException e) {
							JOptionPane.showMessageDialog(null, e.getLocalizedMessage()+System.lineSeparator()+"�������� ����� ��������","������",JOptionPane.ERROR_MESSAGE);
							progress.disactivated();
						}
						JOptionPane.showMessageDialog(null, "��������� "+notavialCount+" ����"+System.lineSeparator()+
								"�� ��������� ��-�� �� ������������ "+avialCount+" ����"+System.lineSeparator()+
								"��������� ������� �� ����� � " + updateCount + " ����"+System.lineSeparator()+
								"�� ��������� ��-�� ������ "+errorCount+" ����","����������",JOptionPane.INFORMATION_MESSAGE);
						progress.disactivated();
					}else{
						JOptionPane.showMessageDialog(null, "�������� ���� �������� ���������"+System.lineSeparator()+
								"�������� ������ ����","������",JOptionPane.ERROR_MESSAGE);
					}
				}
				return null;		
			}			
		};	
		worker.execute();
	}
		
	private void exit(){
		String textDialog;
		if(EVatServiceSingleton.getInstance().isAuthorized()){
			textDialog = "��������� ������ ���������?"+System.lineSeparator()+"�������������� ����� ����� ������";
		}else{
			textDialog = "��������� ������ ���������?";
		}
		
		if(JOptionPane.showConfirmDialog(null, textDialog,"���������� ������",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
			System.exit(1);
		}
	}
	
	private void showInfoCertificate(){
		new ShowCertificateFrame().open();
	}
}
