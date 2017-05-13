package by.gomelagro.outcoming.gui.frames.invoice.panels;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import by.gomelagro.outcoming.gui.frames.invoice.data.list.item.SenderItem;

import java.awt.GridBagLayout;

public class ConsigneePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField countryCodeTextField;
	public String getConsignorCountryCode(){return this.countryCodeTextField.getText();}
	public void setConsignorCountryCode(String code){this.countryCodeTextField.setText(code);}
	
	private JTextField unpTextField;
	public String getConsignorUnp(){return this.unpTextField.getText();}
	public void setConsignorUnp(String unp){this.unpTextField.setText(unp);}
	
	private JTextField nameTextField;
	public String getConsignorName(){return this.nameTextField.getText();}
	public void setConsignorName(String name){this.nameTextField.setText(name);}
	
	private JTextField addressTextField;
	public String getConsignorAddress(){return this.addressTextField.getText();}
	public void setConsignorAddress(String address){this.addressTextField.setText(address);}
	
	/**
	 * Create the panel.
	 */
	public ConsigneePanel(SenderItem item) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{10, 84, 6, 29, 10, 0};
		gridBagLayout.rowHeights = new int[]{20, 20, 20, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		setLayout(gridBagLayout);
		
		JLabel consignorLabel = new JLabel("Реквизиты грузополучателя [consignee] ");
		consignorLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_consignorLabel = new GridBagConstraints();
		gbc_consignorLabel.anchor = GridBagConstraints.WEST;
		gbc_consignorLabel.gridwidth = 3;
		gbc_consignorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_consignorLabel.gridx = 1;
		gbc_consignorLabel.gridy = 1;
		this.add(consignorLabel, gbc_consignorLabel);
		
		JLabel countryCodeTagLabel = new JLabel("Код страны Грузополучателя");
		countryCodeTagLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_countryCodeTagLabel = new GridBagConstraints();
		gbc_countryCodeTagLabel.anchor = GridBagConstraints.EAST;
		gbc_countryCodeTagLabel.insets = new Insets(0, 0, 5, 5);
		gbc_countryCodeTagLabel.gridx = 1;
		gbc_countryCodeTagLabel.gridy = 2;
		this.add(countryCodeTagLabel, gbc_countryCodeTagLabel);
		
		JLabel countryCodeLabel = new JLabel(" [countryCode] ");
		countryCodeLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_countryCodeLabel = new GridBagConstraints();
		gbc_countryCodeLabel.anchor = GridBagConstraints.WEST;
		gbc_countryCodeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_countryCodeLabel.gridx = 2;
		gbc_countryCodeLabel.gridy = 2;
		this.add(countryCodeLabel, gbc_countryCodeLabel);
		
		countryCodeTextField = new JTextField();
		GridBagConstraints gbc_countryCodeTextField = new GridBagConstraints();
		gbc_countryCodeTextField.insets = new Insets(0, 0, 5, 5);
		gbc_countryCodeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_countryCodeTextField.gridx = 3;
		gbc_countryCodeTextField.gridy = 2;
		this.add(countryCodeTextField, gbc_countryCodeTextField);
		countryCodeTextField.setColumns(10);
		countryCodeTextField.setText(item.getCountryCode());
		
		JLabel unpLabel = new JLabel("УНП");
		unpLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_unpLabel = new GridBagConstraints();
		gbc_unpLabel.anchor = GridBagConstraints.WEST;
		gbc_unpLabel.insets = new Insets(0, 0, 5, 5);
		gbc_unpLabel.gridx = 1;
		gbc_unpLabel.gridy = 3;
		this.add(unpLabel, gbc_unpLabel);
		
		JLabel unpTagLabel = new JLabel(" [unp] ");
		GridBagConstraints gbc_unpTagLabel = new GridBagConstraints();
		gbc_unpTagLabel.anchor = GridBagConstraints.EAST;
		gbc_unpTagLabel.insets = new Insets(0, 0, 5, 5);
		gbc_unpTagLabel.gridx = 2;
		gbc_unpTagLabel.gridy = 3;
		this.add(unpTagLabel, gbc_unpTagLabel);
		
		unpTextField = new JTextField();
		GridBagConstraints gbc_unpTextField = new GridBagConstraints();
		gbc_unpTextField.insets = new Insets(0, 0, 5, 5);
		gbc_unpTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_unpTextField.gridx = 3;
		gbc_unpTextField.gridy = 3;
		this.add(unpTextField, gbc_unpTextField);
		unpTextField.setColumns(10);
		unpTextField.setText(item.getUnp());
		
		JLabel nameLabel = new JLabel("Наименование");
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.anchor = GridBagConstraints.WEST;
		gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameLabel.gridx = 1;
		gbc_nameLabel.gridy = 4;
		this.add(nameLabel, gbc_nameLabel);
		
		JLabel nameTagLabel = new JLabel(" [name] ");
		GridBagConstraints gbc_nameTagLabel = new GridBagConstraints();
		gbc_nameTagLabel.anchor = GridBagConstraints.EAST;
		gbc_nameTagLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameTagLabel.gridx = 2;
		gbc_nameTagLabel.gridy = 4;
		this.add(nameTagLabel, gbc_nameTagLabel);
		
		nameTextField = new JTextField();
		GridBagConstraints gbc_nameTextField = new GridBagConstraints();
		gbc_nameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameTextField.gridx = 3;
		gbc_nameTextField.gridy = 4;
		this.add(nameTextField, gbc_nameTextField);
		nameTextField.setColumns(10);
		nameTextField.setText(item.getName());
		
		JLabel addressLabel = new JLabel("Адрес отправки");
		addressLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_addressLabel = new GridBagConstraints();
		gbc_addressLabel.anchor = GridBagConstraints.WEST;
		gbc_addressLabel.insets = new Insets(0, 0, 0, 5);
		gbc_addressLabel.gridx = 1;
		gbc_addressLabel.gridy = 5;
		this.add(addressLabel, gbc_addressLabel);
		
		JLabel addressTagLabel = new JLabel(" [address] ");
		GridBagConstraints gbc_addressTagLabel = new GridBagConstraints();
		gbc_addressTagLabel.anchor = GridBagConstraints.EAST;
		gbc_addressTagLabel.insets = new Insets(0, 0, 0, 5);
		gbc_addressTagLabel.gridx = 2;
		gbc_addressTagLabel.gridy = 5;
		this.add(addressTagLabel, gbc_addressTagLabel);
		
		addressTextField = new JTextField();
		GridBagConstraints gbc_addressTextField = new GridBagConstraints();
		gbc_addressTextField.insets = new Insets(0, 0, 0, 5);
		gbc_addressTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_addressTextField.gridx = 3;
		gbc_addressTextField.gridy = 5;
		this.add(addressTextField, gbc_addressTextField);
		addressTextField.setColumns(10);
		addressTextField.setText(item.getAddress());
	}

}