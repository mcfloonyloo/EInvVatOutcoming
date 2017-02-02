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

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import by.gomelagro.outcoming.gui.frames.folder.component.JFileCheckBoxList;
import by.gomelagro.outcoming.gui.frames.folder.models.FileCheckBoxListModel;
import by.gomelagro.outcoming.properties.ApplicationProperties;
import javax.swing.ScrollPaneConstants;

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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{20, 0, 0, 0, 20, 0};
		gbl_contentPane.rowHeights = new int[]{20, 0, 0, 20, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
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
		
		popup = new JPopupMenu();
		JMenuItem browseMenuItem = new JMenuItem("Обзор...");
		browseMenuItem.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent me) {
				//выбор папки из диалогового меню
				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(false);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(chooser.showDialog(null,"Выбрать папку") == JFileChooser.APPROVE_OPTION){
					folderPathTextField.setText(chooser.getSelectedFile().getAbsolutePath());
					if(!folderPathTextField.getText().isEmpty()){
						fillList(folderPathTextField.getText().trim());
					}
				}
			}
		});
		popup.add(browseMenuItem);
		
		JButton loadButton = new JButton("Загрузить");
		loadButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				if(me.getButton() == MouseEvent.BUTTON3){
					showPopup(me);
				}else 
					if(me.getButton() == MouseEvent.BUTTON1){
						folderPathTextField.setText(ApplicationProperties.getInstance().getFolderInvoicePath());
						if(!folderPathTextField.getText().isEmpty()){
							fillList(folderPathTextField.getText().trim());
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
	
	//заполняем список файлов
	private void fillList(String path){
		model.clear();
		File[] fList = new File(path).listFiles();
		for(int index=0;index<fList.length;index++){
			if(fList[index].isFile()){
				model.addElement(fList[index].getName(), false, fList[index].isFile());
			}
		}		
	}
	
}
