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
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import by.avest.edoc.client.AvEStatus;
import by.gomelagro.outcoming.base.ApplicationConstants;
import by.gomelagro.outcoming.format.date.InvoiceDateFormat;
import by.gomelagro.outcoming.gui.console.component.JConsole;
import by.gomelagro.outcoming.gui.db.ConnectionDB;
import by.gomelagro.outcoming.gui.db.WorkingOutcomingTable;
import by.gomelagro.outcoming.gui.db.files.WorkingFiles;
import by.gomelagro.outcoming.gui.frames.count.ILoadCount;
import by.gomelagro.outcoming.gui.frames.count.InsertLoadCount;
import by.gomelagro.outcoming.gui.frames.count.UpdateCount;
import by.gomelagro.outcoming.gui.frames.enstatus.UpdateEnStatus;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.basic.StringList;
import by.gomelagro.outcoming.gui.frames.list.JMonthPanel;
import by.gomelagro.outcoming.gui.frames.list.MonthPanelListModel;
import by.gomelagro.outcoming.gui.frames.list.MonthYearItem;
import by.gomelagro.outcoming.gui.frames.list.renderer.MonthPanelCellListRenderer;
import by.gomelagro.outcoming.gui.progress.LoadFileProgressBar;
import by.gomelagro.outcoming.properties.ApplicationProperties;
import by.gomelagro.outcoming.service.EVatServiceSingleton;
import by.gomelagro.outcoming.service.certificate.Certificate;
import by.gomelagro.outcoming.status.Status;

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
	private JLabel uncompletedLabel;
	private JLabel cancelledLabel;
	private JLabel undeterminedLabel;
	
	private JComboBox<String> yearComboBox;
	
	private MonthPanelListModel model;
	
	private final String title = ApplicationConstants.APP_MAINFRAME_TITLE+" "+ApplicationConstants.APP_VERSION;
	
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
		//initialize();
		
		if(WorkingFiles.isFile(ApplicationProperties.getInstance().getDbPath())){
			initialize();
			if(WorkingOutcomingTable.Count.getCountAll() > 0)
				updateMainPanel(yearComboBox.getItemAt(yearComboBox.getSelectedIndex()));
			setVisible(true);
		}else{
			JOptionPane.showMessageDialog(null, "Отсутствует файл базы данных."+System.lineSeparator()+
					"Выберите файл базы данных.","Внимание",JOptionPane.WARNING_MESSAGE);
			JFileChooser chooser = new JFileChooser();
			chooser.setMultiSelectionEnabled(false);
			chooser.addChoosableFileFilter(new FileNameExtensionFilter("SQLite Databases (.sqlite)", "sqlite"));
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if(chooser.showDialog(null,"Сохранить файл") == JFileChooser.APPROVE_OPTION){
				ApplicationProperties.getInstance().setDbPath(chooser.getSelectedFile().getAbsolutePath().trim());
				ApplicationProperties.getInstance().saveProperties();
				JOptionPane.showMessageDialog(null, "Новый путь к файлу базы данных записан в настройках программы."+System.lineSeparator()+
						"Пожалуйста, повторно запустите программу","Информация",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {	
		try {
			ConnectionDB.getInstance().load();
			//TableControl.runControl();
		} catch (ClassNotFoundException | SQLException e) {
			JOptionPane.showMessageDialog(null,e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
		}
		setTitle(title);
		setResizable(false);
		setBounds(100, 100, 920, 650);
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
		
		JLabel lblyearLabel = new JLabel("Год: ");
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
		if(WorkingOutcomingTable.Count.getCountAll() > 0){
			if(fillYear()){
				yearComboBox.setSelectedIndex(0);
			}else{
				JOptionPane.showMessageDialog(null, "Невозможно обработать неинициализированный список","Ошибка",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		GridBagConstraints gbc_yearComboBox = new GridBagConstraints();
		gbc_yearComboBox.anchor = GridBagConstraints.SOUTH;
		gbc_yearComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_yearComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_yearComboBox.gridx = 2;
		gbc_yearComboBox.gridy = 1;
		mainPanel.add(yearComboBox, gbc_yearComboBox);
		
		model = new MonthPanelListModel();
		
		JList<JMonthPanel> vatList = new JList<JMonthPanel>();
		vatList.setValueIsAdjusting(true);
		vatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		vatList.setCellRenderer(new MonthPanelCellListRenderer());
		JScrollPane scroll_vatList = new JScrollPane(vatList);
		scroll_vatList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll_vatList	.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		vatList.setModel(model);
		vatList.setFont(new Font("Courier New", Font.PLAIN, 11));
		vatList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_vatList = new GridBagConstraints();
		gbc_vatList.gridheight = 13;
		gbc_vatList.fill = GridBagConstraints.BOTH;
		gbc_vatList.gridx = 5;
		gbc_vatList.gridy = 1;
		mainPanel.add(scroll_vatList, gbc_vatList);
		
		JLabel lblAllInvoicesLabel = new JLabel("Всего ЭСЧФ: ");
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
		
		JLabel ofThemLabel = new JLabel("из них: ");
		ofThemLabel.setFont(new Font("Courier New", Font.PLAIN, 11));
		GridBagConstraints gbc_ofThemLabel = new GridBagConstraints();
		gbc_ofThemLabel.gridwidth = 2;
		gbc_ofThemLabel.anchor = GridBagConstraints.WEST;
		gbc_ofThemLabel.insets = new Insets(0, 0, 5, 5);
		gbc_ofThemLabel.gridx = 1;
		gbc_ofThemLabel.gridy = 3;
		mainPanel.add(ofThemLabel, gbc_ofThemLabel);
		
		JLabel lblCompletedLabel = new JLabel("подписаны: ");
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
		
		JLabel lblNoCompletedLabel = new JLabel("не подписаны: ");
		lblNoCompletedLabel.setFont(new Font("Courier New", Font.PLAIN, 11));
		GridBagConstraints gbc_lblNoCompletedLabel = new GridBagConstraints();
		gbc_lblNoCompletedLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNoCompletedLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNoCompletedLabel.gridx = 2;
		gbc_lblNoCompletedLabel.gridy = 5;
		mainPanel.add(lblNoCompletedLabel, gbc_lblNoCompletedLabel);
		
		uncompletedLabel = new JLabel("");
		uncompletedLabel.setFont(new Font("Courier New", Font.BOLD, 11));
		GridBagConstraints gbc_noCompletedLabel = new GridBagConstraints();
		gbc_noCompletedLabel.anchor = GridBagConstraints.EAST;
		gbc_noCompletedLabel.insets = new Insets(0, 0, 5, 5);
		gbc_noCompletedLabel.gridx = 3;
		gbc_noCompletedLabel.gridy = 5;
		mainPanel.add(uncompletedLabel, gbc_noCompletedLabel);
		
		JLabel lblCancelledLabel = new JLabel("аннулированы: ");
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
		
		JLabel lblUndeterminedLabel = new JLabel("не определено: ");
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
		
		JMenu fileMenu = new JMenu("Файл");
		menuBar.add(fileMenu);
		
	    authMenuItem = new JMenuItem("Авторизация");
		authMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				if(authMenuItem.isEnabled())
					autherization();
			}
		});
		authMenuItem.setEnabled(true);
		fileMenu.add(authMenuItem);
		
		infoCertMenuItem = new JMenuItem("Информация о сертификате");
		infoCertMenuItem.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent evt){
				if(infoCertMenuItem.isEnabled())
					showInfoCertificate();
			}
		});
		infoCertMenuItem.setEnabled(false);
		fileMenu.add(infoCertMenuItem);
		
		fileMenu.addSeparator();
		
		connectMenuItem = new JMenuItem("Подключить");
		connectMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(!authMenuItem.isEnabled()&&connectMenuItem.isEnabled())
					connect();
			}
		});
		connectMenuItem.setEnabled(false);
		fileMenu.add(connectMenuItem);
		
		disconnectMenuItem = new JMenuItem("Отключить");
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
		
		fileMenu.addSeparator();
		
		JMenuItem Settings = new JMenuItem("Настройки");
		Settings.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				new SettingsFrame().open();
			}
		});
		fileMenu.add(Settings);
		
		fileMenu.addSeparator();
		
		JMenuItem exitMenuItem = new JMenuItem("Выход");
		exitMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				exit();
			}
		});
		fileMenu.add(exitMenuItem);
		
		JMenu listMenu = new JMenu("Список ЭСЧФ");
		menuBar.add(listMenu);
		
		loadFileMenuItem = new JMenuItem("Загрузить из файла...");
		loadFileMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				if(loadFileMenuItem.isEnabled()){
					loadFile();
					fillYear();
					selectYear();
					updateMainPanel(yearComboBox.getItemAt(yearComboBox.getSelectedIndex()));
				}
			}
		});
		loadFileMenuItem.setEnabled(false);
		updateVisibleLoadFile();
		listMenu.add(loadFileMenuItem);
		
		loadFolderMenuItem = new JMenuItem("Загрузить из папки...");
		loadFolderMenuItem.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent evt){
				//if(EVatServiceSingleton.getInstance().isConnected()){
					if(loadFolderMenuItem.isEnabled()){
						new LoadOfFolderFrame().setVisible(true);
					}
				//}
			}
		});
		//loadFolderMenuItem.setEnabled(false);
		listMenu.add(loadFolderMenuItem);
		
		listMenu.addSeparator();
		
		updateStatusMenuItem = new JMenuItem("Полное обновление статусов");
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
		
		fastUpdateStatusMenuItem = new JMenuItem("Быстрое обновление статусов");
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
		
		listMenu.addSeparator();
		
		JMenu saveMenu = new JMenu("Отчет по ЭСЧФ...");
		listMenu.add(saveMenu);
		
		saveOneDayMenuItem = new JMenuItem("... за один день");
		saveOneDayMenuItem.addMouseListener(new MouseAdapter(){
			@Override 
			public void mousePressed(MouseEvent evt){
				if(saveOneDayMenuItem.isEnabled()){
					new ReportOneDayFrame().open();
				}
			}
		});
		saveMenu.add(saveOneDayMenuItem);
		
		saveBetweenMenuItem = new JMenuItem("... за период");
		saveBetweenMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				if(saveBetweenMenuItem.isEnabled()){
					new ReportBetweenFrame().open();
				}
			}
		});
		saveMenu.add(saveBetweenMenuItem);

		listMenu.addSeparator();
		
		JMenuItem statMenuItem = new JMenuItem("Статистика");
		statMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				console.setText("");
				StringList list = new StringList();
				try {
					list = WorkingOutcomingTable.Statistics.getStatisticLoad(10);
				} catch (SQLException e) {
					System.err.println("Ошибка загрузки статистики");
					list.clear();
				}
				if(list.size() > 0){
					System.out.println("Статистика обновления ЭСЧФ:");
					for(String item:list){
						System.out.println(item);
					}
					System.out.println("---");
				}else{
					if(list.size() == 0){
						System.out.println("Отсутствуют записи о статистике обновления ЭСЧФ");
					}else{
						System.err.println("Невозможная ошибка загрузки статистики");
					}
				}

				list.clear();
				
				try{
					list = WorkingOutcomingTable.Statistics.getStatisticSend(10);
				}catch(SQLException e){
					System.err.println("Ошибка загрузки статистики");
					list.clear();
				}
				if(list.size() > 0){
					System.out.println("Статистика отправления ЭСЧФ на портал:");
					for(String item:list){
						System.out.println(item);
					}
					System.out.println("---");
				}else{
					if(list.size() == 0){
						System.out.println("Отсутствуют записи о статистике отправления ЭСЧФ на портал");
					}else{
						System.err.println("Невозможная ошибка загрузки статистики");
					}
				}
			}
		});
		listMenu.add(statMenuItem);
	}

	public static void updateVisibleLoadFile(){
		loadFileMenuItem.setVisible(ApplicationProperties.getInstance().getLoadfileMenuitem());
	}
	
	/**
	 * Processing methods fill combobox and labels
	 */	
	private void updateMainPanel(String year){
		if(yearComboBox.getModel().getSize() > 0){
			allInvoicesLabel.setText(String.valueOf(WorkingOutcomingTable.Count.getCountAllInYear(year)));
			completedLabel.setText(String.valueOf(WorkingOutcomingTable.Count.getCountCompletedInYear(year)));
			uncompletedLabel.setText(String.valueOf(WorkingOutcomingTable.Count.getCountUncompletedInYear(year)));
			cancelledLabel.setText(String.valueOf(WorkingOutcomingTable.Count.getCountCancelledInYear(year)));
			undeterminedLabel.setText(String.valueOf(WorkingOutcomingTable.Count.getCountUndeterminedInYear(year)));
			
			List<MonthYearItem> list = WorkingOutcomingTable.Date.selectMonthYear(year);
			if(list != null){
				model.clear();
				for(int index=0;index<list.size();index++){
					try {
						model.addElement(
								InvoiceDateFormat.string2DateReverseSmallDash(WorkingOutcomingTable.Date.getStartMonthOfDate(list.get(index).getMonth(), list.get(index).getYear())), 
								InvoiceDateFormat.string2DateReverseSmallDash(WorkingOutcomingTable.Date.getEndMonthOfDate(list.get(index).getMonth(), list.get(index).getYear())),
								WorkingOutcomingTable.Count.getCountCompletedInMonthYear(list.get(index).getMonth(), list.get(index).getYear()), 
								WorkingOutcomingTable.Count.getCountUncompletedInMonthYear(list.get(index).getMonth(), list.get(index).getYear()), 
								WorkingOutcomingTable.Count.getCountCancelledInMonthYear(list.get(index).getMonth(), list.get(index).getYear()), 
								WorkingOutcomingTable.Count.getCountUndeterminedInMonthYear(list.get(index).getMonth(), list.get(index).getYear())
						);

					} catch (ParseException e) {
						System.err.println("Record "+list.get(index).getMonth()+"-"+list.get(index).getYear()+": "+e.getLocalizedMessage());
					}
				}
			}else{
				JOptionPane.showMessageDialog(null, "Невозможно обработать пустой список","Внимание",JOptionPane.WARNING_MESSAGE);
			}
		}else{
			allInvoicesLabel.setText("0");
			completedLabel.setText("0");
			uncompletedLabel.setText("0");
			cancelledLabel.setText("0");
			undeterminedLabel.setText("0");
		}
	}
	
	private boolean fillYear(){
		List<String> list = WorkingOutcomingTable.Date.selectYearInvoice();
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
		if(WorkingOutcomingTable.Count.getCountAll() > 0){
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
			System.out.println("Авторизация пройдена");
			authMenuItem.setEnabled(false);
			connectMenuItem.setEnabled(true);
			disconnectMenuItem.setEnabled(false);
			infoCertMenuItem.setEnabled(true);
			loadFileMenuItem.setEnabled(true);
			loadFolderMenuItem.setEnabled(true);
			setTitle(title+" ["+ Certificate.getInstance().getOrgName().trim() +" " +Certificate.getInstance().getLastName().trim()+" "+Certificate.getInstance().getFirstMiddleName()+"]");
		}
	}
	
	private void connect(){
		if(EVatServiceSingleton.getInstance().isAuthorized()){
			EVatServiceSingleton.getInstance().connect();
			if(EVatServiceSingleton.getInstance().isConnected()){
				console.setText("");
				System.out.println("Авторизация пройдена");
				System.out.println("Подключение к сервису "+ApplicationProperties.getInstance().getUrlService()+" выполнено успешно");
				connectMenuItem.setEnabled(false);
				disconnectMenuItem.setEnabled(true);
				loadFolderMenuItem.setEnabled(true);
				updateStatusMenuItem.setEnabled(true);
				fastUpdateStatusMenuItem.setEnabled(true);
			}else{
				System.err.println("Ошибка подключения к сервису "+ApplicationProperties.getInstance().getUrlService());
			}
		}
	}
	
	private void disconnect(){
		if(EVatServiceSingleton.getInstance().isAuthorized()){
			if(EVatServiceSingleton.getInstance().isConnected()){
				EVatServiceSingleton.getInstance().disconnect();
				if(!EVatServiceSingleton.getInstance().isConnected()){
					System.out.println("Отключение от сервиса "+ApplicationProperties.getInstance().getUrlService()+" выполнено успешно");
					connectMenuItem.setEnabled(true);
					disconnectMenuItem.setEnabled(false);
					loadFolderMenuItem.setEnabled(false);
					updateStatusMenuItem.setEnabled(false);
					fastUpdateStatusMenuItem.setEnabled(false);
				}else{
					System.err.println("Ошибка отключения от сервиса "+ApplicationProperties.getInstance().getUrlService());
				}
			}
		}
	}
	
	/*private void loadFile(){
		JFileChooser chooser = new JFileChooser();
		int res = chooser.showDialog(null, "Открыть");
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
			@Override
			protected Void doInBackground() throws Exception {
				List<String> lines = null;
				if(res == JFileChooser.APPROVE_OPTION){
					try {
						lines = WorkingFiles.readCSVFile(chooser.getSelectedFile());
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
					}
					if(lines != null){
						int avialCount = 0;
						int errorCount = 0;
						int notavialCount = 0;
						int updateCount = 0;
						int missCount = 0;
						LoadFileProgressBar progress = new LoadFileProgressBar(lines.size()).activated();
						//проверка наличия УНП в сертификате
						String unp = Certificate.getInstance().getUnp();
						int limit = 40;
						//try{
							for(int index=0; index<lines.size();index++){
								String[] fields = lines.get(index).split(";",limit);								
								if(fields[6].trim().equals(unp)){//изменить на чтение сертификата
								//if(fields[6].trim().equals("400047886")){
									System.out.println("Запись "+String.valueOf(index+1)+": Попытка чтения записи входящей ЭСЧФ из файла");
								}else{
									System.out.println(fields[13]);
									switch(WorkingOutcomingTable.Count.getCountRecord(fields[13])){
										case -1: JOptionPane.showMessageDialog(null, "Ошибка проверки наличия записи ЭСЧФ "+fields[13]+" в таблице","Ошибка",JOptionPane.ERROR_MESSAGE); errorCount++; break;
										case  0: {
											Invoice invoice = LoadInvoice.loadPortal(fields[13]);
											if(invoice != null){
												int id = WorkingOutcomingTable.Insert.insertOutcomingFile(invoice);
												if(id > 0){
													if(WorkingOutcomingTable.Insert.insertOutcomingDocumentsFile(id, invoice)){
														AvEStatus status = EVatServiceSingleton.getInstance().getService().getStatus(fields[13]);
														boolean isValid = status.verify();
														if(isValid){
															if(WorkingOutcomingTable.Field.getOutcomingStatus(String.valueOf(id)).trim() != status.getStatus()){
																if(WorkingOutcomingTable.Insert.insertOutcomingStatusesFile(String.valueOf(id), status.getStatus(), Status.valueEnOf(status.getStatus()))){
																	updateCount++;
																}else{
																	errorCount++;
																}
															}else{
																missCount++;
															}
														}else{
															errorCount++;
														}
														
													}else{
														errorCount++;
													}
												}else{
													errorCount++;
												}
											}else{
												System.err.println("ЭСЧФ "+fields[13]+": ошибка загрузки с портала");
												errorCount++;
											}
											break;
										}
										case  1: {
											int id = WorkingOutcomingTable.Field.getOutcomingId(fields[13]);
											if(id > 0){
												AvEStatus status = EVatServiceSingleton.getInstance().getService().getStatus(fields[13]);
												boolean isValid = status.verify();
												if(isValid){
													if(!WorkingOutcomingTable.Field.getOutcomingStatus(String.valueOf(id)).equals(status.getStatus())){
														if(WorkingOutcomingTable.Insert.insertOutcomingStatusesFile(String.valueOf(id), status.getStatus(), Status.valueEnOf(status.getStatus()))){
															updateCount++;
														}else{
															errorCount++;
														}
													}else{
														missCount++;
													}
												}else{
													errorCount++;
												}
												
											}else{
												errorCount++;
											}
											break;
										}
										default: avialCount++; break;
									}
									progress.setProgress(index+1);		
									if(progress.isCancelled()){
										JOptionPane.showMessageDialog(null, "Загрузка файла отменена","Внимание",JOptionPane.WARNING_MESSAGE);
										break;
									}
								}
							}
						} catch (SQLException | ParseException e) {
							JOptionPane.showMessageDialog(null, e.getLocalizedMessage()+System.lineSeparator()+"Загрузка файла прервана","Ошибка",JOptionPane.ERROR_MESSAGE);
							progress.disactivated();
						}
						JOptionPane.showMessageDialog(null, "Добавлено "+notavialCount+" ЭСЧФ"+System.lineSeparator()+
								"Не добавлено из-за их дублирования "+avialCount+" ЭСЧФ"+System.lineSeparator()+
								"Обновлены статусы из файла у " + updateCount + " ЭСЧФ"+System.lineSeparator()+
								"Пропущено обновление статусов у " + missCount + " ЭСЧФ" + System.lineSeparator() + 
								"Не добавлено из-за ошибок "+errorCount+" ЭСЧФ","Информация",JOptionPane.INFORMATION_MESSAGE);
						progress.disactivated();
					}else{
						JOptionPane.showMessageDialog(null, "Загружен файл неверной структуры"+System.lineSeparator()+
								"Выберите другой файл","Ошибка",JOptionPane.ERROR_MESSAGE);
					}
				}
				return null;		
			}			
		};	
		worker.execute();
	}*/
		
	private void loadFile(){
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(false);
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("CSV files (.csv)", "csv"));
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int res = chooser.showDialog(null, "Открыть");
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
			@Override
			protected Void doInBackground() throws Exception {
				List<String> lines = null;
				if(res == JFileChooser.APPROVE_OPTION){
					try {
						lines = WorkingFiles.readCSVFile(chooser.getSelectedFile());
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
					}
					if(lines != null){
						ILoadCount insert = new InsertLoadCount();
						ILoadCount update = new UpdateCount();
						
						LoadFileProgressBar progress = new LoadFileProgressBar(lines.size()).activated();
						//проверка наличия УНП в сертификате
						//String unp = "400047886";
						String unp = Certificate.getInstance().getUnp();
						try{
							int limit = ApplicationConstants.CSV_COUNTCOLUMNS;//количество столбцов							
							for(int index=0; index<lines.size();index++){
								String[] fields = lines.get(index).split(";",limit);
								if(fields[8].trim().equals(unp)){//изменить на чтение сертификата
									System.out.println("Запись "+(index++)+": Попытка чтения записи исходящей ЭСЧФ из файла");
								}else{
									switch(WorkingOutcomingTable.Count.getCountRecord(fields[13])){
									case -1: {insert.addErrorCount();; System.err.println("ЭСЧФ "+fields[13]+" счет-фактура не найден"); break;}
									case  0: {if(WorkingOutcomingTable.Insert.insertOutcoming(fields)) {insert.addBaseCount();}else{
											insert.addErrorCount();;
											System.err.println("ЭСЧФ "+fields[13]+": ошибка добавления счета-фактуры");
										} break;
									}
									case  1: {
										int id = WorkingOutcomingTable.Field.getOutcomingId(fields[13]);
										if(id > 0){
											AvEStatus status = EVatServiceSingleton.getInstance().getService().getStatus(fields[13]);
											boolean isValid = status.verify();
											if(isValid){
												if(!WorkingOutcomingTable.Field.getOutcomingStatus(String.valueOf(id)).trim().equals(status.getStatus())){
													if(WorkingOutcomingTable.Insert.insertOutcomingStatusesFile(String.valueOf(id), status.getStatus(), Status.valueEnOf(status.getStatus()))){
														update.addBaseCount();
													}else{
														update.addErrorCount();;
														System.err.println("ЭСЧФ "+fields[13]+": ошибка добавления статуса");
													}
												}else{
													update.addMissCount();
												}
											}else{
												update.addErrorCount();;
												System.err.println("ЭСЧФ "+fields[13]+": ошибка проверки статуса");
											}
											
										}else{
											update.addErrorCount();
											System.err.println("ЭСЧФ "+fields[13]+": код не найден");
										}
										break;
									}
									default: insert.addMissCount(); break;
									}
									progress.setProgress(index+1);		
									if(progress.isCancelled()){
										JOptionPane.showMessageDialog(null, "Загрузка файла отменена","Внимание",JOptionPane.WARNING_MESSAGE);
										progress.disactivated();
										break;										
									}
								}
							}
						} catch (SQLException | ParseException e) {
							JOptionPane.showMessageDialog(null, e.getLocalizedMessage()+System.lineSeparator()+"Загрузка файла прервана","Ошибка",JOptionPane.ERROR_MESSAGE);
							progress.disactivated();
						}
						JOptionPane.showMessageDialog(null, "Добавлено "+insert.getBaseCount()+" ЭСЧФ"+System.lineSeparator()+
								"Не добавлено из-за их дублирования "+insert.getMissCount()+" ЭСЧФ"+System.lineSeparator()+
								"Не добавлено из-за ошибок "+insert.getErrorCount()+" ЭСЧФ" + System.lineSeparator() +
								"Обновлены статусы из файла у " + update.getBaseCount() + " ЭСЧФ"+System.lineSeparator()+
								"Пропущено обновление статусов у " + update.getMissCount() + " ЭСЧФ" + System.lineSeparator() +
								"Пропущено обновление статусов из-за ошибок у " + update.getErrorCount() + " ЭСЧФ"
								,"Информация",JOptionPane.INFORMATION_MESSAGE);
						if(!WorkingOutcomingTable.Statistics.insertStatisticLoad(insert, update)){
							System.err.println("Ошибка добавления статистических данных о загрузке ЭСЧФ");
						}
						progress.disactivated();
					}else{
						JOptionPane.showMessageDialog(null, "Загружен файл неверной структуры"+System.lineSeparator()+
								"Выберите другой файл","Ошибка",JOptionPane.ERROR_MESSAGE);
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
			textDialog = "Завершить работу программы?"+System.lineSeparator()+"Авторизованный сеанс будет закрыт";
		}else{
			textDialog = "Завершить работу программы?";
		}
		
		if(JOptionPane.showConfirmDialog(null, textDialog,"Завершение работы",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
			System.exit(1);
		}
	}
	
	private void showInfoCertificate(){
		new ShowCertificateFrame().open();
	}
}
