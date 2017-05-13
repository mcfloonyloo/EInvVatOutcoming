package by.gomelagro.outcoming.gui.frames.invoice;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import by.gomelagro.outcoming.format.date.InvoiceDateFormat;
import by.gomelagro.outcoming.format.lines.HtmlLines;
import by.gomelagro.outcoming.gui.db.WorkingOutcomingTable;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.DoubleList;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.IntegerList;
import by.gomelagro.outcoming.gui.frames.invoice.verification.Verification;

public class InvoicePanel {
	private Invoice invoice;
	
	private JScrollPane generalPane;
	private JScrollPane providerPane;
	private JScrollPane recipientPane;
	private JScrollPane senderReceiverPane;
	private JScrollPane deliveryConditionPane;
	private JScrollPane rosterPane;
	
	private static final Font FONT = new Font("Tahoma", Font.BOLD, 11);
	
	public InvoicePanel(Invoice invoice){
		this.invoice = invoice;
	}
	
	public JScrollPane getGeneralPane(){
		JPanel generalPanel = new JPanel();
		generalPane = new JScrollPane(generalPanel);
		GridBagLayout gbl_generalPanel = new GridBagLayout();
		generalPanel.setLayout(gbl_generalPanel);
		int gridy = 0;
		if(Verification.verifySection(invoice.getGeneral())){			
			if(Verification.verifyField(invoice.getSender())){
				gridy++;//1
				ComponentLabelPanel label = new ComponentLabelPanel("Субъект хозяйствования, составляющий ЭСЧФ ", 1, gridy)
						.setLabelFont(FONT)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0, 0, 5, 5));
				generalPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [sender] ", 2, gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0, 0, 5, 5));
				generalPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getSender(), 3, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				generalPanel.add(textField.getTextField(), textField.getContains());
			}
			
			gridy++;//2
			if(Verification.verifyField(invoice.getGeneral().getNumber())){
				gridy++;//3
				ComponentLabelPanel label = new ComponentLabelPanel("Номер ЭСЧФ ", 1, gridy)
						.setLabelFont(FONT)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0, 0, 5, 5));
				generalPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [number] ", 2, gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0, 0, 5, 5));
				generalPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getGeneral().getNumber(), 3, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				generalPanel.add(textField.getTextField(), textField.getContains());
			}
			
			if(Verification.verifyField(invoice.getGeneral().getDateIssuance())){
				gridy++;//4
				ComponentLabelPanel label = new ComponentLabelPanel("Дата выставления ЭСЧФ", 1, gridy)
						.setLabelFont(FONT)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0, 0, 5, 5));
				generalPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [dateIssuance] ", 2, gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0, 0, 5, 5));
				generalPanel.add(labelR.getLabel(), labelR.getContains());
				
				String date = "";
				try{
					date = new SimpleDateFormat("dd.MM.yyyy").format(InvoiceDateFormat.string2DateReverseSmallDash(invoice.getGeneral().getDateIssuance()));
				}catch(ParseException e){
					date = invoice.getGeneral().getDateIssuance();
				}
				
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(date, 3, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				generalPanel.add(textField.getTextField(), textField.getContains());
			}
				
			if(Verification.verifyField(invoice.getGeneral().getDateTransaction())){
				gridy++;//5
				ComponentLabelPanel label = new ComponentLabelPanel("Дата совершения операции ЭСЧФ ", 1, gridy)
						.setLabelFont(FONT)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0, 0, 5, 5));
				generalPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [dateTransaction] ", 2, gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0, 0, 5, 5));
				generalPanel.add(labelR.getLabel(), labelR.getContains());
				
				String date = "";
				try{
					date = new SimpleDateFormat("dd.MM.yyyy").format(InvoiceDateFormat.string2DateReverseSmallDash(invoice.getGeneral().getDateTransaction()));
				}catch(ParseException e){
					date = invoice.getGeneral().getDateTransaction();
				}
				
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(date, 3, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				generalPanel.add(textField.getTextField(), textField.getContains());
			}
				
			if(Verification.verifyField(invoice.getGeneral().getDocumentType())){
				gridy++;//6
				ComponentLabelPanel label = new ComponentLabelPanel("Тип ЭСЧФ ", 1, gridy)
						.setLabelFont(FONT)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0, 0, 5, 5));
				generalPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [documentType] ", 2, gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0, 0, 5, 5));
				generalPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = null;
				switch(invoice.getGeneral().getDocumentType().trim()){
					case "ORIGINAL":{
						textField= new ComponentTextFieldPanel("Исходный счет-фактура", 3, gridy);
						break;
					}
					case "ADDITIONAL":{
						textField= new ComponentTextFieldPanel("Дополнительный счет-фактура", 3, gridy);
						break;
					}
					case "FIXED":{
						textField= new ComponentTextFieldPanel("Исправленный счет-фактура", 3, gridy);
						break;
					}
					case "ADD_NO_REFERENCE":{
						textField= new ComponentTextFieldPanel("Дополнительный счет-фактура (без ссылки на исходный)", 3, gridy);
						break;
					}
					default: {
						textField= new ComponentTextFieldPanel("", 3, gridy);
						break;
					}
				}
				textField.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				generalPanel.add(textField.getTextField(), textField.getContains());
			}
				
			if(Verification.verifyField(invoice.getGeneral().getInvoice())){
				gridy++;//7
				ComponentLabelPanel label = new ComponentLabelPanel("К ЭСЧФ", 1, gridy)
						.setLabelFont(FONT)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0, 0, 5, 5));
				generalPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [invoice] ", 2, gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0, 0, 5, 5));
				generalPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getGeneral().getInvoice(), 3, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				generalPanel.add(textField.getTextField(), textField.getContains());
			}
				
			if(Verification.verifyField(invoice.getGeneral().getDateCancelled())){
				gridy++;//8
				ComponentLabelPanel label = new ComponentLabelPanel("Дата аннулирования ЭСЧФ ", 1, gridy)
						.setLabelFont(FONT)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0, 0, 5, 5));
				generalPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [dateCancelled] ", 2, gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0, 0, 5, 5));
				generalPanel.add(labelR.getLabel(), labelR.getContains());
				
				String date = "";
				try{
					date = new SimpleDateFormat("dd.MM.yyyy").format(InvoiceDateFormat.string2DateReverseSmallDash(invoice.getGeneral().getDateCancelled()));
				}catch(ParseException e){
					date = invoice.getGeneral().getDateCancelled();
				}
				
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(date, 3, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				generalPanel.add(textField.getTextField(), textField.getContains());
			}
					
			if(Verification.verifyField(invoice.getGeneral().getSendToRecipient())){
				gridy++;//9
				ComponentLabelPanel label = new ComponentLabelPanel("Отобразить получателю ", 1, gridy)
						.setLabelFont(FONT)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0, 0, 5, 5));
				generalPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [sendToRecipient] ", 2, gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0, 0, 5, 5));
				generalPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getGeneral().getSendToRecipient(), 3, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				generalPanel.add(textField.getTextField(), textField.getContains());
			}
		}
	
  		gbl_generalPanel.columnWidths = new int[]{10, 0, 0, 0, 10, 0};
  		gbl_generalPanel.rowHeights = getInt(gridy);
		gbl_generalPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_generalPanel.rowWeights = getDouble(gridy);
		
		return generalPane;
	}
	
	public JScrollPane getProviderPane(){
		JPanel providerPanel = new JPanel();
		providerPane = new JScrollPane(providerPanel);
		GridBagLayout gbl_providerPanel = new GridBagLayout();
		providerPanel.setLayout(gbl_providerPanel);
		
		int gridy = 0;//0
		if(Verification.verifySection(invoice.getProvider())){
			if(Verification.verifyField(invoice.getProvider().getProviderStatus())){
				gridy++;//1
				ComponentLabelPanel label = new ComponentLabelPanel("Статус поставщика", 1, gridy)
						.setLabelFont(FONT)
						.setContainsGridwidth(2)
						.setContainsInsets(new Insets(0,0,5,5))
						.setContainsAnchor(GridBagConstraints.WEST);
				providerPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [providerStatus] ", 3, gridy)
						.setContainsInsets(new Insets(0,0,5,5))
						.setContainsAnchor(GridBagConstraints.EAST);
				providerPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = null; 
				switch(invoice.getProvider().getProviderStatus().trim()){
					case "SELLER":{
						textField = new ComponentTextFieldPanel("Продавец", 4, gridy);
						break;
					}
					case "CONSIGNOR":{
						textField = new ComponentTextFieldPanel("Комитент", 4, gridy);
						break;
					}
					case "COMMISSIONAIRE":{
						textField = new ComponentTextFieldPanel("Комиссионер", 4, gridy);
						break;
					}
					case "TAX_DEDUCTION_PAYER":{
						textField = new ComponentTextFieldPanel("Плательщик, передающий налоговые вычеты", 4, gridy);
						break;
					}
					case "TRUSTEE":{
						textField = new ComponentTextFieldPanel("Доверительный управляющий", 4, gridy);
						break;
					}
					case "FOREIGN_ORGANIZATION":{
						textField = new ComponentTextFieldPanel("Иностранная организация", 4, gridy);
						break;
					}
					case "AGENT":{
						textField = new ComponentTextFieldPanel("Посредник", 4, gridy);
						break;
					}
					case "DEVELOPER":{
						textField = new ComponentTextFieldPanel("Заказчик (застройщик)", 4, gridy);
						break;
					}
					case "TURNOVERS_ON_SALE_PAYER":{
						textField = new ComponentTextFieldPanel("Плательщик, передающий обороты по реализации", 4, gridy);
						break;
					}
					default: {
						textField = new ComponentTextFieldPanel("", 4, gridy);
						break;
					}
				}
				textField.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				providerPanel.add(textField.getTextField(), textField.getContains());
			}
				
			if(Verification.verifyField(invoice.getProvider().getDependentPerson())){
				gridy++;//2
				ComponentLabelPanel label = new ComponentLabelPanel("Взаимозависимое лицо", 2, gridy)
						.setLabelFont(FONT)
						.setContainsInsets(new Insets(0,0,5,5))
						.setContainsAnchor(GridBagConstraints.WEST);
				providerPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [dependentPerson] ", 3, gridy)
						.setContainsInsets(new Insets(0,0,5,5))
						.setContainsAnchor(GridBagConstraints.EAST);
				providerPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = null; 
				switch(invoice.getProvider().getDependentPerson().trim()){
					case "true":{
						textField = new ComponentTextFieldPanel("Да", 4, gridy);
						break;
					}
					case "false":{
						textField = new ComponentTextFieldPanel("Нет", 4, gridy);
						break;
					}
					default: {
						textField = new ComponentTextFieldPanel("", 4, gridy);
						break;
					}
				}
				textField.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				providerPanel.add(textField.getTextField(), textField.getContains());
			}
				
			if(Verification.verifyField(invoice.getProvider().getResidentsOfOffshore())){
				gridy++;//3
				ComponentLabelPanel label = new ComponentLabelPanel(HtmlLines.getHtmlText(new String[]{"Сделка с резидентом","оффшорной группы"}), 2, gridy)
						.setLabelFont(FONT)
						.setContainsGridheight(2)
						.setContainsInsets(new Insets(0,0,5,5))
						.setContainsAnchor(GridBagConstraints.WEST);
				providerPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [residentsOfOffshore] ", 3, gridy)
						.setContainsInsets(new Insets(0,0,5,5))
						.setContainsAnchor(GridBagConstraints.EAST);
				providerPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = null; 
				switch(invoice.getProvider().getResidentsOfOffshore().trim()){
					case "true":{
						textField = new ComponentTextFieldPanel("Да", 4, gridy);
						break;
					}
					case "false":{
						textField = new ComponentTextFieldPanel("Нет", 4, gridy);
						break;
					}
					default: {
						textField = new ComponentTextFieldPanel("", 4, gridy);
						break;
					}
				}
				textField.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				providerPanel.add(textField.getTextField(), textField.getContains());					
				gridy++;//4
			}
				
			if(Verification.verifyField(invoice.getProvider().getSpecialDealGoods())){
				gridy++;//5
				ComponentLabelPanel label = new ComponentLabelPanel(HtmlLines.getHtmlText(new String[]{"Сделка с товарами по перечню,",
																			"определяемому Правительством",
																			"Республики Беларусь, в соответствии",
																			"с подпунктом 1.3 пункта 1 статьи 30",
						   													"Налогового кодекса Республики Беларусь"}), 2, gridy)
						.setLabelFont(FONT)
						.setContainsGridheight(4)
						.setContainsInsets(new Insets(0,0,5,5))
						.setContainsAnchor(GridBagConstraints.WEST);
				providerPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [specialDealGoods] ", 3, gridy)
						.setContainsInsets(new Insets(0,0,5,5))
						.setContainsAnchor(GridBagConstraints.EAST);
				providerPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = null; 
				switch(invoice.getProvider().getSpecialDealGoods().trim()){
					case "true":{
						textField = new ComponentTextFieldPanel("Да", 4, gridy);
						break;
					}
					case "false":{
						textField = new ComponentTextFieldPanel("Нет", 4, gridy);
						break;
					}
					default: {
						textField = new ComponentTextFieldPanel("", 4, gridy);
						break;
					}
				}
				textField.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				providerPanel.add(textField.getTextField(), textField.getContains());	
				gridy = gridy + 3;//6-8
			}
			
			if(Verification.verifyField(invoice.getProvider().getBigCompany())){
				gridy++;//9
				ComponentLabelPanel label = new ComponentLabelPanel(HtmlLines.getHtmlText(new String[]{"Организация, включенная в перечень",
				 															"крупных налогоплательщиков"}), 2, gridy)
						.setLabelFont(FONT)
						.setContainsGridheight(2)
						.setContainsInsets(new Insets(0,0,5,5))
						.setContainsAnchor(GridBagConstraints.WEST);
				providerPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [bigCompany] ", 3, gridy)
						.setContainsInsets(new Insets(0,0,5,5))
						.setContainsAnchor(GridBagConstraints.EAST);
				providerPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = null; 
				switch(invoice.getProvider().getBigCompany().trim()){
					case "true":{
						textField = new ComponentTextFieldPanel("Да", 4, gridy);
						break;
					}
					case "false":{
						textField = new ComponentTextFieldPanel("Нет", 4, gridy);
						break;
					}
					default: {
						textField = new ComponentTextFieldPanel("", 4, gridy);
						break;
					}
				}
				textField.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				providerPanel.add(textField.getTextField(), textField.getContains());
				gridy++;//10
			}
			
			gridy++;
			if(Verification.verifyField(invoice.getProvider().getCountryCode())){
				gridy++;//11
				ComponentLabelPanel label = new ComponentLabelPanel("Код страны поставщика", 1, gridy)
						.setLabelFont(FONT)
						.setContainsGridwidth(2)
						.setContainsInsets(new Insets(0,0,5,5))
						.setContainsAnchor(GridBagConstraints.WEST);
				providerPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [countryCode] ", 3, gridy)
						.setContainsInsets(new Insets(0,0,5,5))
						.setContainsAnchor(GridBagConstraints.EAST);
				providerPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(WorkingOutcomingTable.Additional.getCountryName(invoice.getProvider().getCountryCode().trim()), 4, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				providerPanel.add(textField.getTextField(), textField.getContains());
			}
				
			if(Verification.verifyField(invoice.getProvider().getUnp())){
				gridy++;//12
				ComponentLabelPanel label = new ComponentLabelPanel("УНП", 1, gridy)
						.setLabelFont(FONT)
						.setContainsGridwidth(2)
						.setContainsInsets(new Insets(0,0,5,5))
						.setContainsAnchor(GridBagConstraints.WEST);
				providerPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [unp] ", 3, gridy)
						.setContainsInsets(new Insets(0,0,5,5))
						.setContainsAnchor(GridBagConstraints.EAST);
				providerPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getProvider().getUnp(), 4, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				providerPanel.add(textField.getTextField(), textField.getContains());
			}
				
			if(Verification.verifyField(invoice.getProvider().getBranchCode())){
				gridy++;//13
				ComponentLabelPanel label = new ComponentLabelPanel(HtmlLines.getHtmlText(new String[]{"Код филиала","(обособленного подразделения)"}), 2, gridy)
					.setLabelFont(FONT)
					.setContainsGridheight(2)
					.setContainsInsets(new Insets(0,0,5,5))
					.setContainsAnchor(GridBagConstraints.WEST);
				providerPanel.add(label.getLabel(), label.getContains());
					
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [branchCode] ", 3, gridy)
					.setContainsInsets(new Insets(0,0,5,5))
					.setContainsAnchor(GridBagConstraints.EAST);
				providerPanel.add(labelR.getLabel(), labelR.getContains());
					
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getProvider().getBranchCode(), 4, gridy)
					.setContainsFill(GridBagConstraints.HORIZONTAL)
					.setContainsInsets(new Insets(0,0,5,5))
					.setTextFieldColumns(10);
				providerPanel.add(textField.getTextField(), textField.getContains());				
				gridy++;//14
			}
				
			if(Verification.verifyField(invoice.getProvider().getName())){
				gridy++;//15
				ComponentLabelPanel label = new ComponentLabelPanel("Поставщик", 1, gridy)
						.setLabelFont(FONT)
						.setContainsGridwidth(2)
						.setContainsInsets(new Insets(0,0,5,5))
						.setContainsAnchor(GridBagConstraints.WEST);
				providerPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [name] ", 3, gridy)
						.setContainsInsets(new Insets(0,0,5,5))
						.setContainsAnchor(GridBagConstraints.EAST);
				providerPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getProvider().getName(), 4, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				providerPanel.add(textField.getTextField(), textField.getContains());
			}
			
			if(Verification.verifyField(invoice.getProvider().getAddress())){
				gridy++;//16
				ComponentLabelPanel label = new ComponentLabelPanel("Юридический адрес", 1, gridy)
						.setLabelFont(FONT)
						.setContainsGridwidth(2)
						.setContainsInsets(new Insets(0,0,5,5))
						.setContainsAnchor(GridBagConstraints.WEST);
				providerPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [address] ", 3, gridy)
						.setContainsInsets(new Insets(0,0,5,5))
						.setContainsAnchor(GridBagConstraints.EAST);
				providerPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getProvider().getAddress(), 4, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				providerPanel.add(textField.getTextField(), textField.getContains());
			}
				
			if(Verification.verifySection(invoice.getProvider().getPrincipal())){
				gridy++;//17
				if(Verification.verifyField(invoice.getProvider().getPrincipal().getNumber())){
					gridy++;//18
					ComponentLabelPanel label = new ComponentLabelPanel("Номер ЭСЧФ комитента", 1, gridy)
							.setLabelFont(FONT)
							.setContainsGridwidth(2)
							.setContainsInsets(new Insets(0,0,5,5))
							.setContainsAnchor(GridBagConstraints.WEST);
					providerPanel.add(label.getLabel(), label.getContains());
					
					ComponentLabelPanel labelR = new ComponentLabelPanel(" [principal - number] ", 3, gridy)
							.setContainsInsets(new Insets(0,0,5,5))
							.setContainsAnchor(GridBagConstraints.EAST);
					providerPanel.add(labelR.getLabel(), labelR.getContains());
					
					ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getProvider().getPrincipal().getNumber(), 4, gridy)
							.setContainsFill(GridBagConstraints.HORIZONTAL)
							.setContainsInsets(new Insets(0,0,5,5))
							.setTextFieldColumns(10);
					providerPanel.add(textField.getTextField(), textField.getContains());
				}
				if(Verification.verifyField(invoice.getProvider().getPrincipal().getDate())){
					gridy++;//19	
					ComponentLabelPanel label = new ComponentLabelPanel("Дата выписки", 1, gridy)
							.setLabelFont(FONT)
							.setContainsGridwidth(2)
							.setContainsInsets(new Insets(0,0,5,5))
							.setContainsAnchor(GridBagConstraints.WEST);
					providerPanel.add(label.getLabel(), label.getContains());
					
					ComponentLabelPanel labelR = new ComponentLabelPanel(" [principal - date] ", 3, gridy)
							.setContainsInsets(new Insets(0,0,5,5))
							.setContainsAnchor(GridBagConstraints.EAST);
					providerPanel.add(labelR.getLabel(), labelR.getContains());
					
					String date = "";
					try{
						date = new SimpleDateFormat("dd.MM.yyyy").format(InvoiceDateFormat.string2DateReverseSmallDash(invoice.getProvider().getPrincipal().getDate()));
					}catch(ParseException e){
						date = invoice.getProvider().getPrincipal().getDate();
					}
					
					ComponentTextFieldPanel textField = new ComponentTextFieldPanel(date, 4, gridy)
							.setContainsFill(GridBagConstraints.HORIZONTAL)
							.setContainsInsets(new Insets(0,0,5,5))
							.setTextFieldColumns(10);
					providerPanel.add(textField.getTextField(), textField.getContains());
				}
			}
				
			if(Verification.verifySection(invoice.getProvider().getVendor())){
				gridy++;//20
				if(Verification.verifyField(invoice.getProvider().getVendor().getNumber())){
					gridy++;//21
					ComponentLabelPanel label = new ComponentLabelPanel("Номер ЭСЧФ продавца", 1, gridy)
							.setLabelFont(FONT)
							.setContainsGridwidth(2)
							.setContainsInsets(new Insets(0,0,5,5))
							.setContainsAnchor(GridBagConstraints.WEST);
					providerPanel.add(label.getLabel(), label.getContains());
					
					ComponentLabelPanel labelR = new ComponentLabelPanel(" [vendor - number] ", 3, gridy)
							.setContainsInsets(new Insets(0,0,5,5))
							.setContainsAnchor(GridBagConstraints.EAST);
					providerPanel.add(labelR.getLabel(), labelR.getContains());
					
					ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getProvider().getVendor().getNumber(), 4, gridy)
							.setContainsFill(GridBagConstraints.HORIZONTAL)
							.setContainsInsets(new Insets(0,0,5,5))
							.setTextFieldColumns(10);
					providerPanel.add(textField.getTextField(), textField.getContains());
				}				
				if(Verification.verifyField(invoice.getProvider().getVendor().getDate())){
					gridy++;//22
					ComponentLabelPanel label = new ComponentLabelPanel("Дата выписки", 1, gridy)
							.setLabelFont(FONT)
							.setContainsGridwidth(2)
							.setContainsInsets(new Insets(0,0,5,5))
							.setContainsAnchor(GridBagConstraints.WEST);
					providerPanel.add(label.getLabel(), label.getContains());
					
					ComponentLabelPanel labelR = new ComponentLabelPanel(" [vendor - date] ", 3, gridy)
							.setContainsInsets(new Insets(0,0,5,5))
							.setContainsAnchor(GridBagConstraints.EAST);
					providerPanel.add(labelR.getLabel(), labelR.getContains());
					
					String date = "";
					try{
						date = new SimpleDateFormat("dd.MM.yyyy").format(InvoiceDateFormat.string2DateReverseSmallDash(invoice.getProvider().getVendor().getDate()));
					}catch(ParseException e){
						date = invoice.getProvider().getVendor().getDate();
					}
					
					ComponentTextFieldPanel textField = new ComponentTextFieldPanel(date, 4, gridy)
							.setContainsFill(GridBagConstraints.HORIZONTAL)
							.setContainsInsets(new Insets(0,0,5,5))
							.setTextFieldColumns(10);
					providerPanel.add(textField.getTextField(), textField.getContains());
				}
			}
				
			gridy++;//23
			if(Verification.verifyField(invoice.getProvider().getDeclaration())){
				gridy++;//24
				ComponentLabelPanel label = new ComponentLabelPanel(HtmlLines.getHtmlText(new String[]{"Регистрационный номер","выпуска товаров"}), 1, gridy)
							.setLabelFont(FONT)
							.setContainsGridheight(2)
							.setContainsGridwidth(2)
							.setContainsInsets(new Insets(0,0,5,5))
							.setContainsAnchor(GridBagConstraints.WEST);
				providerPanel.add(label.getLabel(), label.getContains());
					
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [declaration] ", 3, gridy)
							.setContainsInsets(new Insets(0,0,5,5))
								.setContainsAnchor(GridBagConstraints.EAST);
				providerPanel.add(labelR.getLabel(), labelR.getContains());
					
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getProvider().getDeclaration(), 4, gridy)
							.setContainsFill(GridBagConstraints.HORIZONTAL)
							.setContainsInsets(new Insets(0,0,5,5))
							.setTextFieldColumns(10);
				providerPanel.add(textField.getTextField(), textField.getContains());		
				gridy++;//25	
			}
				
			if(Verification.verifyField(invoice.getProvider().getDateRelease())){
				gridy++;//26
				ComponentLabelPanel label = new ComponentLabelPanel("Дата выпуска товаров", 2, gridy)
						.setLabelFont(FONT)
						.setContainsInsets(new Insets(0,0,5,5))
						.setContainsAnchor(GridBagConstraints.WEST);
				providerPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [dateRelease] ", 3, gridy)
						.setContainsInsets(new Insets(0,0,5,5))
						.setContainsAnchor(GridBagConstraints.EAST);
				providerPanel.add(labelR.getLabel(), labelR.getContains());
				
				String date = "";
				try{
					date = new SimpleDateFormat("dd.MM.yyyy").format(InvoiceDateFormat.string2DateReverseSmallDash(invoice.getProvider().getDateRelease()));
				}catch(ParseException e){
					date = invoice.getProvider().getDateRelease();
				}
				
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(date, 4, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				providerPanel.add(textField.getTextField(), textField.getContains());
			}	
			
			if(Verification.verifyField(invoice.getProvider().getDateActualExport())){
				gridy++;//27
				ComponentLabelPanel label = new ComponentLabelPanel(HtmlLines.getHtmlText(new String[]{"Дата разрешения","на убытие товаров"}), 2, gridy)
						.setLabelFont(FONT)
						.setContainsGridheight(2)
						.setContainsInsets(new Insets(0,0,5,5))
						.setContainsAnchor(GridBagConstraints.WEST);
				providerPanel.add(label.getLabel(), label.getContains());
					
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [dateActualExport] ", 3, gridy)
						.setContainsInsets(new Insets(0,0,5,5))
						.setContainsAnchor(GridBagConstraints.EAST);
				providerPanel.add(labelR.getLabel(), labelR.getContains());
					
				String date = "";
				try{
					date = new SimpleDateFormat("dd.MM.yyyy").format(InvoiceDateFormat.string2DateReverseSmallDash(invoice.getProvider().getDateActualExport()));
				}catch(ParseException e){
					date = invoice.getProvider().getDateActualExport();
				}
				
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(date, 4, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				providerPanel.add(textField.getTextField(), textField.getContains());		
			
				gridy++;//28
			}
					
			if(Verification.verifySection(invoice.getProvider().getTaxes())){
				gridy++;//29
				if(Verification.verifyField(invoice.getProvider().getTaxes().getNumber())){
					gridy++;//30
					ComponentLabelPanel label = new ComponentLabelPanel(HtmlLines.getHtmlText(new String[]{"Реквизиты заявления о ввозе товаров","и уплате косвенных налогов - номер"}), 1, gridy)
							.setLabelFont(FONT)
							.setContainsGridheight(2)
							.setContainsGridwidth(2)
							.setContainsInsets(new Insets(0,0,5,5))
							.setContainsAnchor(GridBagConstraints.WEST);
					providerPanel.add(label.getLabel(), label.getContains());
						
					ComponentLabelPanel labelR = new ComponentLabelPanel(" [taxes - number] ", 3, gridy)
							.setContainsInsets(new Insets(0,0,5,5))
							.setContainsAnchor(GridBagConstraints.EAST);
					providerPanel.add(labelR.getLabel(), labelR.getContains());
						
					ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getProvider().getTaxes().getNumber(), 4, gridy)
							.setContainsFill(GridBagConstraints.HORIZONTAL)
							.setContainsInsets(new Insets(0,0,5,5))
							.setTextFieldColumns(10);
					providerPanel.add(textField.getTextField(), textField.getContains());		
					
					gridy++;//31
				}
					
				if(Verification.verifyField(invoice.getProvider().getTaxes().getDate())){
					gridy++;//32
					ComponentLabelPanel label = new ComponentLabelPanel(HtmlLines.getHtmlText(new String[]{"Реквизиты заявления о ввозе товаров","и уплате косвенных налогов - дата"}), 1, gridy)
							.setLabelFont(FONT)
							.setContainsGridheight(2)
							.setContainsGridwidth(2)
							.setContainsInsets(new Insets(0,0,5,5))
							.setContainsAnchor(GridBagConstraints.WEST);
					providerPanel.add(label.getLabel(), label.getContains());
						
					ComponentLabelPanel labelR = new ComponentLabelPanel(" [taxes - date] ", 3, gridy)
							.setContainsInsets(new Insets(0,0,5,5))
							.setContainsAnchor(GridBagConstraints.EAST);
					providerPanel.add(labelR.getLabel(), labelR.getContains());
						
					String date = "";
					try{
						date = new SimpleDateFormat("dd.MM.yyyy").format(InvoiceDateFormat.string2DateReverseSmallDash(invoice.getProvider().getTaxes().getDate()));
					}catch(ParseException e){
						date = invoice.getProvider().getTaxes().getDate();
					}
					
					ComponentTextFieldPanel textField = new ComponentTextFieldPanel(date, 4, gridy)
							.setContainsFill(GridBagConstraints.HORIZONTAL)
							.setContainsInsets(new Insets(0,0,5,5))
							.setTextFieldColumns(10);
					providerPanel.add(textField.getTextField(), textField.getContains());		
					
					gridy++;//33
				}
			}
		}
		
		gbl_providerPanel.columnWidths = new int[]{10, 10, 0, 0, 0, 10, 0};
		gbl_providerPanel.rowHeights = getInt(gridy);
		
		gbl_providerPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_providerPanel.rowWeights = getDouble(gridy);
		
		return providerPane;
	}

	public JScrollPane getRecipientPane(){
		JPanel recipientPanel = new JPanel();
		recipientPane = new JScrollPane(recipientPanel);
		GridBagLayout gbl_recipientPanel = new GridBagLayout();
		recipientPanel.setLayout(gbl_recipientPanel);
		
		int gridy = 0;//0
		if(Verification.verifySection(invoice.getRecipient())){
			if(Verification.verifyField(invoice.getRecipient().getRecipientStatus())){
				gridy++;//1
				ComponentLabelPanel label = new ComponentLabelPanel("Статус получателя ", 1, gridy)
						.setLabelFont(FONT)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsGridwidth(2)
						.setContainsInsets(new Insets(0,0,5,5));
				recipientPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [recipientStatus] ",3,gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0,0,5,5));
				recipientPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = null; 
				switch(invoice.getRecipient().getRecipientStatus().trim()){
					case "CUSTOMER":{
						textField = new ComponentTextFieldPanel("Покупатель", 4, gridy);
						break;
					}
					case "CONSUMER":{
						textField = new ComponentTextFieldPanel("Потребитель", 4, gridy);
						break;
					}
					case "CONSIGNOR":{
						textField = new ComponentTextFieldPanel("Комитент", 4, gridy);
						break;
					}
					case "COMMISSIONAIRE":{
						textField = new ComponentTextFieldPanel("Комиссионер", 4, gridy);
						break;
					}
					case "TAX_DEDUCTION_RECIPIENT":{
						textField = new ComponentTextFieldPanel("Покупатель, получающий налоговые вычеты", 4, gridy);
						break;
					}
					case "FOREIGN_ORGANIZATION_BUYER":{
						textField = new ComponentTextFieldPanel("Покупатель объектов у иностранной организации", 4, gridy);
						break;
					}
					case "TURNOVERS_ON_SALE_PAYER":{
						textField = new ComponentTextFieldPanel("Плательщик, получающий обороты по реализации", 4, gridy);
						break;
					}
					default: {
						textField = new ComponentTextFieldPanel("", 4, gridy);
						break;
					}
				}
				textField.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				recipientPanel.add(textField.getTextField(), textField.getContains());
			}
				
			if(Verification.verifyField(invoice.getRecipient().getDependentPerson())){
				gridy++;//2
				ComponentLabelPanel label = new ComponentLabelPanel("Взаимозависимое лицо ", 2, gridy)
						.setLabelFont(FONT)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0,0,5,5));
				recipientPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [dependentPerson] ",3,gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0,0,5,5));
				recipientPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = null; 
				switch(invoice.getRecipient().getDependentPerson().trim()){
					case "true":{
						textField = new ComponentTextFieldPanel("Да", 4, gridy);
						break;
					}
					case "false":{
						textField = new ComponentTextFieldPanel("Нет", 4, gridy);
						break;
					}
					default: {
						textField = new ComponentTextFieldPanel("", 4, gridy);
						break;
					}
				}
				textField.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				recipientPanel.add(textField.getTextField(), textField.getContains());
			}
			
			if(Verification.verifyField(invoice.getRecipient().getResidentsOfOffshore())){
				gridy++;//3
				ComponentLabelPanel label = new ComponentLabelPanel(HtmlLines.getHtmlText(new String[]{"Сделка с резидентом",
				   																			"оффшорной группы"}), 2, gridy)
						.setLabelFont(FONT)
						.setContainsGridheight(2)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0,0,5,5));
				recipientPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [residentsOfOffshore] ",3,gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0,0,5,5));
				recipientPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = null; 
				switch(invoice.getRecipient().getResidentsOfOffshore().trim()){
					case "true":{
						textField = new ComponentTextFieldPanel("Да", 4, gridy);
						break;
					}
					case "false":{
						textField = new ComponentTextFieldPanel("Нет", 4, gridy);
						break;
					}
					default: {
						textField = new ComponentTextFieldPanel("", 4, gridy);
						break;
					}
				}
				textField.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				recipientPanel.add(textField.getTextField(), textField.getContains());
				
				gridy++;//4
			}
				
			if(Verification.verifyField(invoice.getRecipient().getSpecialDealGoods())){
				gridy++;//5
				ComponentLabelPanel label = new ComponentLabelPanel(HtmlLines.getHtmlText(new String[]{"Сделка с товарами по перечню,",
							  																			"определяемому Правительством",
							  																			"Республики Беларусь, в соответствии",
							  																			"с подпунктом 1.3 пункта 1 статьи 30",
																										"Налогового кодекса Республики Беларусь"}), 2, gridy)
						.setLabelFont(FONT)
						.setContainsGridheight(4)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0,0,5,5));
				recipientPanel.add(label.getLabel(), label.getContains());
						
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [specialDealCompany] ",3,gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0,0,5,5));
				recipientPanel.add(labelR.getLabel(), labelR.getContains());
						
				ComponentTextFieldPanel textField = null; 
				switch(invoice.getRecipient().getSpecialDealGoods().trim()){
					case "true":{
						textField = new ComponentTextFieldPanel("Да", 4, gridy);
						break;
					}
					case "false":{
						textField = new ComponentTextFieldPanel("Нет", 4, gridy);
						break;
					}
					default: {
						textField = new ComponentTextFieldPanel("", 4, gridy);
						break;
					}
				}
				textField.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				recipientPanel.add(textField.getTextField(), textField.getContains());
				gridy = gridy + 3;//6-8
			}
			
			if(Verification.verifyField(invoice.getRecipient().getBigCompany())){
				gridy++;//9
				ComponentLabelPanel label = new ComponentLabelPanel(HtmlLines.getHtmlText(new String[]{"Организация, включенная в перечень",
																										"крупных плательщиков"}), 2, gridy)
						.setLabelFont(FONT)
						.setContainsGridheight(2)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0,0,5,5));
				recipientPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [bigCompany] ",3,gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0,0,5,5));
				recipientPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = null; 
				switch(invoice.getRecipient().getBigCompany().trim()){
					case "true":{
						textField = new ComponentTextFieldPanel("Да", 4, gridy);
						break;
					}
					case "false":{
						textField = new ComponentTextFieldPanel("Нет", 4, gridy);
						break;
					}
					default: {
						textField = new ComponentTextFieldPanel("", 4, gridy);
						break;
					}
				}
				textField.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				recipientPanel.add(textField.getTextField(), textField.getContains());
				gridy++;//10
			}
				
			if(Verification.verifyField(invoice.getRecipient().getCountryCode())){
				gridy++;//11
				ComponentLabelPanel label = new ComponentLabelPanel("Код страны получателя ", 1, gridy)
						.setLabelFont(FONT)
						.setContainsGridwidth(2)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0,0,5,5));
				recipientPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [countryCode] ",3,gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0,0,5,5));
				recipientPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(WorkingOutcomingTable.Additional.getCountryName(invoice.getRecipient().getCountryCode()), 4, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				recipientPanel.add(textField.getTextField(), textField.getContains());
			}
			
			if(Verification.verifyField(invoice.getRecipient().getUnp())){	
				gridy++;//12
				ComponentLabelPanel label = new ComponentLabelPanel("УНП ", 1, gridy)
						.setLabelFont(FONT)
						.setContainsGridwidth(2)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0,0,5,5));
				recipientPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [unp] ",3,gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0,0,5,5));
				recipientPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getRecipient().getUnp(), 4, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				recipientPanel.add(textField.getTextField(), textField.getContains());
			}
					
			if(Verification.verifyField(invoice.getRecipient().getBranchCode())){
				gridy++;//13
				ComponentLabelPanel label = new ComponentLabelPanel(HtmlLines.getHtmlText(new String[]{"Код филиала",
																							"(обособленного подразделения"}), 2, gridy)
						.setLabelFont(FONT)
						.setContainsGridheight(2)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0,0,5,5));
				recipientPanel.add(label.getLabel(), label.getContains());

				ComponentLabelPanel labelR = new ComponentLabelPanel(" [bigCompany] ",3,gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0,0,5,5));
				recipientPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getRecipient().getBranchCode(), 4, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				recipientPanel.add(textField.getTextField(), textField.getContains());				
				gridy++;//14
			}
				
			if(Verification.verifyField(invoice.getRecipient().getName())){
				gridy++;//15
				ComponentLabelPanel label = new ComponentLabelPanel("Получатель ", 1, gridy)
						.setLabelFont(FONT)
						.setContainsGridwidth(2)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0,0,5,5));
				recipientPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [name] ",3,gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0,0,5,5));
				recipientPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getRecipient().getName(), 4, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				recipientPanel.add(textField.getTextField(), textField.getContains());
				
			}
				
			if(Verification.verifyField(invoice.getRecipient().getAddress())){
				gridy++;//16
				ComponentLabelPanel label = new ComponentLabelPanel("Юридический адрес ", 1, gridy)
						.setLabelFont(FONT)
						.setContainsGridwidth(2)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0,0,5,5));
				recipientPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [address] ",3,gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0,0,5,5));
				recipientPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getRecipient().getAddress(), 4, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				recipientPanel.add(textField.getTextField(), textField.getContains());
			}
				
			if(Verification.verifyField(invoice.getRecipient().getDeclaration())){
				gridy++;//17
				ComponentLabelPanel label = new ComponentLabelPanel(HtmlLines.getHtmlText(new String[]{"Регистрационный номер",
																								"выпуска товаров"}), 1, gridy)
						.setLabelFont(FONT)
						.setContainsGridheight(2)
						.setContainsGridwidth(2)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0,0,5,5));
				recipientPanel.add(label.getLabel(), label.getContains());
						
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [declaration] ",3,gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0,0,5,5));
				recipientPanel.add(labelR.getLabel(), labelR.getContains());
						
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getRecipient().getDeclaration(), 4, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				recipientPanel.add(textField.getTextField(), textField.getContains());	
				gridy++;//18
			}
			
			if(Verification.verifySection(invoice.getRecipient().getTaxes())){
				gridy++;//19
				if(Verification.verifyField(invoice.getRecipient().getTaxes().getNumber())){
					gridy++;//20
					ComponentLabelPanel label = new ComponentLabelPanel(HtmlLines.getHtmlText(new String[]{"Реквизиты заявления о ввозе товаров",
																							 "и уплате косвенных налогов - номер"}), 1, gridy)
							.setLabelFont(FONT)
							.setContainsGridheight(2)
							.setContainsGridwidth(2)
							.setContainsAnchor(GridBagConstraints.WEST)
							.setContainsInsets(new Insets(0,0,5,5));
					recipientPanel.add(label.getLabel(), label.getContains());
					
					ComponentLabelPanel labelR = new ComponentLabelPanel(" [taxes - number] ",3,gridy)
							.setContainsAnchor(GridBagConstraints.EAST)
							.setContainsInsets(new Insets(0,0,5,5));
					recipientPanel.add(labelR.getLabel(), labelR.getContains());
					
					ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getRecipient().getTaxes().getNumber(), 4, gridy)
							.setContainsFill(GridBagConstraints.HORIZONTAL)
							.setContainsInsets(new Insets(0,0,5,5))
							.setTextFieldColumns(10);
					recipientPanel.add(textField.getTextField(), textField.getContains());					
					gridy++;//21
				}
				
				if(Verification.verifyField(invoice.getRecipient().getTaxes().getDate())){
					gridy++;//22
					ComponentLabelPanel label = new ComponentLabelPanel(HtmlLines.getHtmlText(new String[]{"Реквизиты заявления о ввозе товаров",
							   																				"и уплате косвенных налогов - дата"}), 1, gridy)
							.setLabelFont(FONT)
							.setContainsGridheight(2)
							.setContainsGridwidth(2)
							.setContainsAnchor(GridBagConstraints.WEST)
							.setContainsInsets(new Insets(0,0,5,5));
					recipientPanel.add(label.getLabel(), label.getContains());
					
					ComponentLabelPanel labelR = new ComponentLabelPanel(" [taxes - date] ",3,gridy)
							.setContainsAnchor(GridBagConstraints.EAST)
							.setContainsInsets(new Insets(0,0,5,5));
					recipientPanel.add(labelR.getLabel(), labelR.getContains());
					
					String date = "";
					try{
						date = new SimpleDateFormat("dd.MM.yyyy").format(InvoiceDateFormat.string2DateReverseSmallDash(invoice.getRecipient().getTaxes().getDate()));
					}catch(ParseException e){
						date = invoice.getRecipient().getTaxes().getDate();
					}
					
					ComponentTextFieldPanel textField = new ComponentTextFieldPanel(date, 4, gridy)
							.setContainsFill(GridBagConstraints.HORIZONTAL)
							.setContainsInsets(new Insets(0,0,5,5))
							.setTextFieldColumns(10);
					recipientPanel.add(textField.getTextField(), textField.getContains());						
					gridy++;//23
				}
			}
				
			gridy++;//24
			if(Verification.verifyField(invoice.getRecipient().getDateImport())){
				gridy++;//25
				
				ComponentLabelPanel label = new ComponentLabelPanel("Дата ввоза товаров", 2,gridy)
						.setLabelFont(FONT)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0,0,5,5));
				recipientPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [dateImport] ", 3, gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0,0,5,5));
				recipientPanel.add(labelR.getLabel(), labelR.getContains());
				
				String date = "";
				try{
					date = new SimpleDateFormat("dd.MM.yyyy").format(InvoiceDateFormat.string2DateReverseSmallDash(invoice.getRecipient().getDateImport()));
				}catch(ParseException e){
					date = invoice.getRecipient().getDateImport();
				}
				
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(date, 4, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				recipientPanel.add(textField.getTextField(), textField.getContains());				
			}
		}
		
		gbl_recipientPanel.columnWidths = new int[]{10, 10, 0, 0, 0, 10, 0};
		gbl_recipientPanel.rowHeights = getInt(gridy);
		
		gbl_recipientPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_recipientPanel.rowWeights = getDouble(gridy);	
		return recipientPane;
	}
	
	public JScrollPane getSenderReceiverPanel(){
		JPanel senderReceiverPanel = new JPanel();
		senderReceiverPane = new JScrollPane(senderReceiverPanel);
		GridBagLayout gbl_senderReceiverPanel = new GridBagLayout();
		senderReceiverPanel.setLayout(gbl_senderReceiverPanel);
		
		int gridy = 0;//0
		if(Verification.verifySection(invoice.getSenderReceiver())){
			 
			if(Verification.verifyList(invoice.getSenderReceiver().getConsignors())){
				gridy++;//1
				ComponentLabelPanel labelConsignor = new ComponentLabelPanel("ГРУЗООТПРАВИТЕЛИ:", 1, gridy)
						.setLabelFont(FONT)
						.setContainsInsets(new Insets(0,0,5,5))
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsGridheight(2);
				senderReceiverPanel.add(labelConsignor.getLabel(), labelConsignor.getContains());
				gridy++;//2
				
				for(int index=0;index<invoice.getSenderReceiver().getConsignors().size();index++){
					if(Verification.verifySection(invoice.getSenderReceiver().getConsignors().get(index))){
						gridy++;//3
						if(Verification.verifyField(invoice.getSenderReceiver().getConsignors().get(index).getCountryCode())){
							ComponentLabelPanel label = new ComponentLabelPanel("Код страны",1,gridy)
									.setLabelFont(FONT)
									.setContainsAnchor(GridBagConstraints.WEST)
									.setContainsInsets(new Insets(0,0,5,5));
							senderReceiverPanel.add(label.getLabel(),label.getContains());
						
							ComponentLabelPanel labelR = new ComponentLabelPanel(" [countryCode] ", 2, gridy)
									.setContainsAnchor(GridBagConstraints.EAST)
									.setContainsInsets(new Insets(0,0,5,5));
							senderReceiverPanel.add(labelR.getLabel(),labelR.getContains());
							
							ComponentTextFieldPanel textField = new ComponentTextFieldPanel(WorkingOutcomingTable.Additional.getCountryName(invoice.getSenderReceiver().getConsignors().get(index).getCountryCode()),3,gridy)
									.setContainsFill(GridBagConstraints.HORIZONTAL)
									.setContainsInsets(new Insets(0,0,5,5))
									.setTextFieldColumns(10);
							senderReceiverPanel.add(textField.getTextField(),textField.getContains());
						}
						
						if(Verification.verifyField(invoice.getSenderReceiver().getConsignors().get(index).getUnp())){
							gridy++;//4
							ComponentLabelPanel label = new ComponentLabelPanel("УНП",1,gridy)
									.setLabelFont(FONT)
									.setContainsAnchor(GridBagConstraints.WEST)
									.setContainsInsets(new Insets(0,0,5,5));
							senderReceiverPanel.add(label.getLabel(),label.getContains());
						
							ComponentLabelPanel labelR = new ComponentLabelPanel(" [unp] ", 2, gridy)
									.setContainsAnchor(GridBagConstraints.EAST)
									.setContainsInsets(new Insets(0,0,5,5));
							senderReceiverPanel.add(labelR.getLabel(),labelR.getContains());
							
							ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getSenderReceiver().getConsignors().get(index).getUnp(),3,gridy)
									.setContainsFill(GridBagConstraints.HORIZONTAL)
									.setContainsInsets(new Insets(0,0,5,5))
									.setTextFieldColumns(10);
							senderReceiverPanel.add(textField.getTextField(),textField.getContains());
						}
						
						if(Verification.verifyField(invoice.getSenderReceiver().getConsignors().get(index).getName())){
							gridy++;//5
							ComponentLabelPanel label = new ComponentLabelPanel("Наименование",1,gridy)
									.setLabelFont(FONT)
									.setContainsAnchor(GridBagConstraints.WEST)
									.setContainsInsets(new Insets(0,0,5,5));
							senderReceiverPanel.add(label.getLabel(),label.getContains());
						
							ComponentLabelPanel labelR = new ComponentLabelPanel(" [name] ", 2, gridy)
									.setContainsAnchor(GridBagConstraints.EAST)
									.setContainsInsets(new Insets(0,0,5,5));
							senderReceiverPanel.add(labelR.getLabel(),labelR.getContains());
							
							ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getSenderReceiver().getConsignors().get(index).getName(),3,gridy)
									.setContainsFill(GridBagConstraints.HORIZONTAL)
									.setContainsInsets(new Insets(0,0,5,5))
									.setTextFieldColumns(10);
							senderReceiverPanel.add(textField.getTextField(),textField.getContains());
						}
						
						if(Verification.verifyField(invoice.getSenderReceiver().getConsignors().get(index).getAddress())){
							gridy++;//6
							ComponentLabelPanel label = new ComponentLabelPanel("УНП",1,gridy)
									.setLabelFont(FONT)
									.setContainsAnchor(GridBagConstraints.WEST)
									.setContainsInsets(new Insets(0,0,5,5));
							senderReceiverPanel.add(label.getLabel(),label.getContains());
						
							ComponentLabelPanel labelR = new ComponentLabelPanel(" [address] ", 2, gridy)
									.setContainsAnchor(GridBagConstraints.EAST)
									.setContainsInsets(new Insets(0,0,5,5));
							senderReceiverPanel.add(labelR.getLabel(),labelR.getContains());
							
							ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getSenderReceiver().getConsignors().get(index).getAddress(),3,gridy)
									.setContainsFill(GridBagConstraints.HORIZONTAL)
									.setContainsInsets(new Insets(0,0,5,5))
									.setTextFieldColumns(10);
							senderReceiverPanel.add(textField.getTextField(),textField.getContains());
						}
						gridy++;//7
					}				
				}
				gridy++;//8
			}
			
			gridy++;//9
			if(Verification.verifyList(invoice.getSenderReceiver().getConsignees())){
				gridy++;//10
				ComponentLabelPanel labelConsignee = new ComponentLabelPanel("ГРУЗОПОЛУЧАТЕЛИ:", 1, gridy)
						.setLabelFont(FONT)
						.setContainsInsets(new Insets(0,0,5,5))
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsGridheight(2);
				senderReceiverPanel.add(labelConsignee.getLabel(), labelConsignee.getContains());
				gridy++;//11
				
				for(int index = 0;index<invoice.getSenderReceiver().getConsignees().size();index++){
					if(Verification.verifySection(invoice.getSenderReceiver().getConsignees().get(index))){
						gridy++;//12
						if(Verification.verifyField(invoice.getSenderReceiver().getConsignees().get(index).getCountryCode())){
							ComponentLabelPanel label = new ComponentLabelPanel("Код страны",1,gridy)
									.setLabelFont(FONT)
									.setContainsAnchor(GridBagConstraints.WEST)
									.setContainsInsets(new Insets(0,0,5,5));
							senderReceiverPanel.add(label.getLabel(),label.getContains());
						
							ComponentLabelPanel labelR = new ComponentLabelPanel(" [countryCode] ", 2, gridy)
									.setContainsAnchor(GridBagConstraints.EAST)
									.setContainsInsets(new Insets(0,0,5,5));
							senderReceiverPanel.add(labelR.getLabel(),labelR.getContains());
							
							ComponentTextFieldPanel textField = new ComponentTextFieldPanel(WorkingOutcomingTable.Additional.getCountryName(invoice.getSenderReceiver().getConsignees().get(index).getCountryCode()),3,gridy)
									.setContainsFill(GridBagConstraints.HORIZONTAL)
									.setContainsInsets(new Insets(0,0,5,5))
									.setTextFieldColumns(10);
							senderReceiverPanel.add(textField.getTextField(),textField.getContains());
						}
						
						if(Verification.verifyField(invoice.getSenderReceiver().getConsignees().get(index).getUnp())){
							gridy++;//13
							ComponentLabelPanel label = new ComponentLabelPanel("УНП",1,gridy)
									.setLabelFont(FONT)
									.setContainsAnchor(GridBagConstraints.WEST)
									.setContainsInsets(new Insets(0,0,5,5));
							senderReceiverPanel.add(label.getLabel(),label.getContains());
						
							ComponentLabelPanel labelR = new ComponentLabelPanel(" [unp] ", 2, gridy)
									.setContainsAnchor(GridBagConstraints.EAST)
									.setContainsInsets(new Insets(0,0,5,5));
							senderReceiverPanel.add(labelR.getLabel(),labelR.getContains());
							
							ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getSenderReceiver().getConsignees().get(index).getUnp(),3,gridy)
									.setContainsFill(GridBagConstraints.HORIZONTAL)
									.setContainsInsets(new Insets(0,0,5,5))
									.setTextFieldColumns(10);
							senderReceiverPanel.add(textField.getTextField(),textField.getContains());
						}
						
						if(Verification.verifyField(invoice.getSenderReceiver().getConsignees().get(index).getName())){
							gridy++;//14
							ComponentLabelPanel label = new ComponentLabelPanel("Наименование",1,gridy)
									.setLabelFont(FONT)
									.setContainsAnchor(GridBagConstraints.WEST)
									.setContainsInsets(new Insets(0,0,5,5));
							senderReceiverPanel.add(label.getLabel(),label.getContains());
						
							ComponentLabelPanel labelR = new ComponentLabelPanel(" [name] ", 2, gridy)
									.setContainsAnchor(GridBagConstraints.EAST)
									.setContainsInsets(new Insets(0,0,5,5));
							senderReceiverPanel.add(labelR.getLabel(),labelR.getContains());
							
							ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getSenderReceiver().getConsignees().get(index).getName(),3,gridy)
									.setContainsFill(GridBagConstraints.HORIZONTAL)
									.setContainsInsets(new Insets(0,0,5,5))
									.setTextFieldColumns(10);
							senderReceiverPanel.add(textField.getTextField(),textField.getContains());
						}
						
						if(Verification.verifyField(invoice.getSenderReceiver().getConsignees().get(index).getAddress())){
							gridy++;//15
							ComponentLabelPanel label = new ComponentLabelPanel("УНП",1,gridy)
									.setLabelFont(FONT)
									.setContainsAnchor(GridBagConstraints.WEST)
									.setContainsInsets(new Insets(0,0,5,5));
							senderReceiverPanel.add(label.getLabel(),label.getContains());
						
							ComponentLabelPanel labelR = new ComponentLabelPanel(" [address] ", 2, gridy)
									.setContainsAnchor(GridBagConstraints.EAST)
									.setContainsInsets(new Insets(0,0,5,5));
							senderReceiverPanel.add(labelR.getLabel(),labelR.getContains());
							
							ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getSenderReceiver().getConsignees().get(index).getAddress(),3,gridy)
									.setContainsFill(GridBagConstraints.HORIZONTAL)
									.setContainsInsets(new Insets(0,0,5,5))
									.setTextFieldColumns(10);
							senderReceiverPanel.add(textField.getTextField(),textField.getContains());
						}
						gridy++;//16
					}				
				}
			}
			
		}		
		
		gbl_senderReceiverPanel.columnWidths = new int[]{10, 0, 0, 0, 10, 0};
		gbl_senderReceiverPanel.rowHeights = getInt(gridy);
		
		gbl_senderReceiverPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_senderReceiverPanel.rowWeights = getDouble(gridy);
		return senderReceiverPane;
	}
	
	public JScrollPane getDeliveryConditionPanel(){
		JPanel deliveryConditionPanel = new JPanel();
		deliveryConditionPane = new JScrollPane(deliveryConditionPanel);
		GridBagLayout gbl_deliveryConditionPane = new GridBagLayout();
		deliveryConditionPanel.setLayout(gbl_deliveryConditionPane);
		
		int gridy = 0;//0
		if(Verification.verifySection(invoice.getDeliveryCondition().getContract())){
			if(Verification.verifyField(invoice.getDeliveryCondition().getContract().getNumber())){
				gridy++;//1
				ComponentLabelPanel label = new ComponentLabelPanel("Договор (контракт) на поставку товара", 1,gridy)
						.setLabelFont(FONT)
						.setContainsGridwidth(2)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0,0,5,5));
				deliveryConditionPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [contract - number] ", 3, gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0,0,5,5));
				deliveryConditionPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getDeliveryCondition().getContract().getNumber(), 4, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				deliveryConditionPanel.add(textField.getTextField(), textField.getContains());				
			}
			
			if(Verification.verifyField(invoice.getDeliveryCondition().getContract().getDate())){
				gridy++;//2
				ComponentLabelPanel label = new ComponentLabelPanel("Дата договора (контракта)", 1,gridy)
						.setContainsGridwidth(2)
						.setLabelFont(FONT)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0,0,5,5));
				deliveryConditionPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [contract - date] ", 3, gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0,0,5,5));
				deliveryConditionPanel.add(labelR.getLabel(), labelR.getContains());
				
				String date = "";
				try{
					date = new SimpleDateFormat("dd.MM.yyyy").format(InvoiceDateFormat.string2DateReverseSmallDash(invoice.getDeliveryCondition().getContract().getDate()));
				}catch(ParseException e){
					date = invoice.getDeliveryCondition().getContract().getDate();
				}
				
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(date, 4, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				deliveryConditionPanel.add(textField.getTextField(), textField.getContains());	
			}
			gridy++;//3
			if(Verification.verifyList(invoice.getDeliveryCondition().getContract().getDocuments())){
				gridy++;//4
				ComponentLabelPanel labelTitle = new ComponentLabelPanel("ДОКУМЕНТЫ:", 1,gridy)
						.setContainsGridwidth(2)
						.setLabelFont(FONT)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0,0,5,5));
				deliveryConditionPanel.add(labelTitle.getLabel(), labelTitle.getContains());
				gridy++;//5
				
				for(int index = 0;index<invoice.getDeliveryCondition().getContract().getDocuments().size();index++){
					if(Verification.verifySection(invoice.getDeliveryCondition().getContract().getDocuments().get(index))){
						if(invoice.getDeliveryCondition().getContract().getDocuments().size() > 1){
						gridy++;//6
							ComponentLabelPanel labelNumber = new ComponentLabelPanel("Документ №"+String.valueOf(index+1), 1,gridy)
									.setContainsGridwidth(2)
									.setLabelFont(FONT)
									.setContainsAnchor(GridBagConstraints.WEST)
									.setContainsInsets(new Insets(0,0,5,5));
							deliveryConditionPanel.add(labelNumber.getLabel(), labelNumber.getContains());
							gridy++;//7
						}

						if(Verification.verifyField(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getDate())){
							gridy++;//8
							ComponentLabelPanel label = new ComponentLabelPanel("Дата", 2,gridy)
									.setLabelFont(FONT)
									.setContainsAnchor(GridBagConstraints.WEST)
									.setContainsInsets(new Insets(0,0,5,5));
							deliveryConditionPanel.add(label.getLabel(), label.getContains());
							
							ComponentLabelPanel labelR = new ComponentLabelPanel(" [date] ", 3, gridy)
									.setContainsAnchor(GridBagConstraints.EAST)
									.setContainsInsets(new Insets(0,0,5,5));
							deliveryConditionPanel.add(labelR.getLabel(), labelR.getContains());
							
							String date = "";
							try{
								date = new SimpleDateFormat("dd.MM.yyyy").format(InvoiceDateFormat.string2DateReverseSmallDash(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getDate()));
							}catch(ParseException e){
								date = invoice.getDeliveryCondition().getContract().getDocuments().get(index).getDate();
							}
							
							ComponentTextFieldPanel textField = new ComponentTextFieldPanel(date, 4, gridy)
									.setContainsFill(GridBagConstraints.HORIZONTAL)
									.setContainsInsets(new Insets(0,0,5,5))
									.setTextFieldColumns(10);
							deliveryConditionPanel.add(textField.getTextField(), textField.getContains());	
						}

						if(Verification.verifyField(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getBlankCode())){
							gridy++;//9
							ComponentLabelPanel label = new ComponentLabelPanel("Код типа бланка", 2,gridy)
									.setLabelFont(FONT)
									.setContainsAnchor(GridBagConstraints.WEST)
									.setContainsInsets(new Insets(0,0,5,5));
							deliveryConditionPanel.add(label.getLabel(), label.getContains());
							
							ComponentLabelPanel labelR = new ComponentLabelPanel(" [blackCode] ", 3, gridy)
									.setContainsAnchor(GridBagConstraints.EAST)
									.setContainsInsets(new Insets(0,0,5,5));
							deliveryConditionPanel.add(labelR.getLabel(), labelR.getContains());
							
							ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getBlankCode(), 4, gridy)
									.setContainsFill(GridBagConstraints.HORIZONTAL)
									.setContainsInsets(new Insets(0,0,5,5))
									.setTextFieldColumns(10);
							deliveryConditionPanel.add(textField.getTextField(), textField.getContains());	
						}
						
						if(Verification.verifyField(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getSeria())){
							gridy++;//10
							ComponentLabelPanel label = new ComponentLabelPanel("Серия", 2,gridy)
									.setLabelFont(FONT)
									.setContainsAnchor(GridBagConstraints.WEST)
									.setContainsInsets(new Insets(0,0,5,5));
							deliveryConditionPanel.add(label.getLabel(), label.getContains());
							
							ComponentLabelPanel labelR = new ComponentLabelPanel(" [seria] ", 3, gridy)
									.setContainsAnchor(GridBagConstraints.EAST)
									.setContainsInsets(new Insets(0,0,5,5));
							deliveryConditionPanel.add(labelR.getLabel(), labelR.getContains());
							
							ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getSeria(), 4, gridy)
									.setContainsFill(GridBagConstraints.HORIZONTAL)
									.setContainsInsets(new Insets(0,0,5,5))
									.setTextFieldColumns(10);
							deliveryConditionPanel.add(textField.getTextField(), textField.getContains());	
						}
						
						if(Verification.verifyField(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getNumber())){
							gridy++;//11
							ComponentLabelPanel label = new ComponentLabelPanel("Номер", 2, gridy)
									.setLabelFont(FONT)
									.setContainsAnchor(GridBagConstraints.WEST)
									.setContainsInsets(new Insets(0,0,5,5));
							deliveryConditionPanel.add(label.getLabel(), label.getContains());
							
							ComponentLabelPanel labelR = new ComponentLabelPanel(" [number] ", 3, gridy)
									.setContainsAnchor(GridBagConstraints.EAST)
									.setContainsInsets(new Insets(0,0,5,5));
							deliveryConditionPanel.add(labelR.getLabel(), labelR.getContains());
							
							ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getNumber(), 4, gridy)
									.setContainsFill(GridBagConstraints.HORIZONTAL)
									.setContainsInsets(new Insets(0,0,5,5))
									.setTextFieldColumns(10);
							deliveryConditionPanel.add(textField.getTextField(), textField.getContains());	
						}
						
						if(Verification.verifySection(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getDocType())){
							if(Verification.verifyField(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getDocType().getCode())){
								gridy++;//12
								ComponentLabelPanel label = new ComponentLabelPanel("Вид документа", 2,gridy)
										.setLabelFont(FONT)
										.setContainsAnchor(GridBagConstraints.WEST)
										.setContainsInsets(new Insets(0,0,5,5));
								deliveryConditionPanel.add(label.getLabel(), label.getContains());
								
								ComponentLabelPanel labelR = new ComponentLabelPanel(" [docType - code] ", 3, gridy)
										.setContainsAnchor(GridBagConstraints.EAST)
										.setContainsInsets(new Insets(0,0,5,5));
								deliveryConditionPanel.add(labelR.getLabel(), labelR.getContains());
								
								ComponentTextFieldPanel textField= new ComponentTextFieldPanel(WorkingOutcomingTable.Additional.getTypeDocumentName(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getDocType().getCode()), 4, gridy)
										.setContainsFill(GridBagConstraints.HORIZONTAL)
										.setContainsInsets(new Insets(0,0,5,5))
										.setTextFieldColumns(10);
								deliveryConditionPanel.add(textField.getTextField(), textField.getContains());	
							}
							
							if(Verification.verifyField(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getDocType().getValue())){
								gridy++;//13
								ComponentLabelPanel label = new ComponentLabelPanel("Название", 2,gridy)
										.setLabelFont(FONT)
										.setContainsAnchor(GridBagConstraints.WEST)
										.setContainsInsets(new Insets(0,0,5,5));
								deliveryConditionPanel.add(label.getLabel(), label.getContains());
								
								ComponentLabelPanel labelR = new ComponentLabelPanel(" [docType - value] ", 3, gridy)
										.setContainsAnchor(GridBagConstraints.EAST)
										.setContainsInsets(new Insets(0,0,5,5));
								deliveryConditionPanel.add(labelR.getLabel(), labelR.getContains());
								
								ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getDocType().getValue(), 4, gridy)
										.setContainsFill(GridBagConstraints.HORIZONTAL)
										.setContainsInsets(new Insets(0,0,5,5))
										.setTextFieldColumns(10);
								deliveryConditionPanel.add(textField.getTextField(), textField.getContains());	
							}

						}
						
						gridy++;//14
					}
				}
			}
		}
		
		if(Verification.verifyField(invoice.getDeliveryCondition().getDescription())){
			gridy++;//15
			ComponentLabelPanel label = new ComponentLabelPanel("Дополнительные сведения", 1,gridy)
					.setContainsGridheight(2)
					.setLabelFont(FONT)
					.setContainsAnchor(GridBagConstraints.WEST)
					.setContainsInsets(new Insets(0,0,5,5));
			deliveryConditionPanel.add(label.getLabel(), label.getContains());
			
			ComponentLabelPanel labelR = new ComponentLabelPanel(" [description] ", 3, gridy)
					.setContainsAnchor(GridBagConstraints.EAST)
					.setContainsInsets(new Insets(0,0,5,5));
			deliveryConditionPanel.add(labelR.getLabel(), labelR.getContains());
			
			ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getDeliveryCondition().getDescription(), 4, gridy)
					.setContainsFill(GridBagConstraints.HORIZONTAL)
					.setContainsInsets(new Insets(0,0,5,5))
					.setTextFieldColumns(10);
			deliveryConditionPanel.add(textField.getTextField(), textField.getContains());				
		}
		
		
		gbl_deliveryConditionPane.columnWidths = new int[]{10, 10, 0, 0, 0, 10, 0};
		gbl_deliveryConditionPane.rowHeights = getInt(gridy);
		
		gbl_deliveryConditionPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_deliveryConditionPane.rowWeights = getDouble(gridy);
		return deliveryConditionPane;
	}
	
	public JScrollPane getRosterPane(){
		JPanel rosterPanel = new JPanel();
		rosterPane = new JScrollPane(rosterPanel);
		GridBagLayout gbl_rosterPane = new GridBagLayout();
		rosterPanel.setLayout(gbl_rosterPane);
		
		int gridy = 0;//0
		if(Verification.verifySection(invoice.getRoster())){
			if(Verification.verifyField(invoice.getRoster().getTotalCost())){
				gridy++;//1
				ComponentLabelPanel label = new ComponentLabelPanel(HtmlLines.getHtmlText(new String[]{"Всего по счёту. Стоимость товаров (работ,",
																										"услуг) имущественных прав без НДС"}), 1,gridy)
						.setContainsGridheight(2)
						.setContainsGridwidth(2)
						.setLabelFont(FONT)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0,0,5,5));
				rosterPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [totalCost] ", 3, gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0,0,5,5));
				rosterPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getRoster().getTotalCost(), 4, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				rosterPanel.add(textField.getTextField(), textField.getContains());	
				gridy = gridy + 2;//3
			}
			
			if(Verification.verifyField(invoice.getRoster().getTotalExcise())){
				gridy++;//4
				ComponentLabelPanel label = new ComponentLabelPanel("Всего по счёту. Сумма акциза", 1,gridy)
						.setContainsGridwidth(2)
						.setLabelFont(FONT)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0,0,5,5));
				rosterPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [totalExcise] ", 3, gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0,0,5,5));
				rosterPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getRoster().getTotalExcise(), 4, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				rosterPanel.add(textField.getTextField(), textField.getContains());	
			}
			
			if(Verification.verifyField(invoice.getRoster().getTotalVat())){
				gridy++;//5
				ComponentLabelPanel label = new ComponentLabelPanel("Всего по счёту. Сумма НДС", 1,gridy)
						.setContainsGridwidth(2)
						.setLabelFont(FONT)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0,0,5,5));
				rosterPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [totalVat] ", 3, gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0,0,5,5));
				rosterPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getRoster().getTotalVat(), 4, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				rosterPanel.add(textField.getTextField(), textField.getContains());	
			}
			
			if(Verification.verifyField(invoice.getRoster().getTotalCostVat())){
				gridy++;//6
				ComponentLabelPanel label = new ComponentLabelPanel(HtmlLines.getHtmlText(new String[]{"Всего по счёту. Стоимость товаров (работ,",
																										"услуг) имущественных прав с учётом НДС"}), 1,gridy)
						.setContainsGridheight(2)
						.setContainsGridwidth(2)
						.setLabelFont(FONT)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0,0,5,5));
				rosterPanel.add(label.getLabel(), label.getContains());
				
				ComponentLabelPanel labelR = new ComponentLabelPanel(" [totalCostVat] ", 3, gridy)
						.setContainsAnchor(GridBagConstraints.EAST)
						.setContainsInsets(new Insets(0,0,5,5));
				rosterPanel.add(labelR.getLabel(), labelR.getContains());
				
				ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getRoster().getTotalCostVat(), 4, gridy)
						.setContainsFill(GridBagConstraints.HORIZONTAL)
						.setContainsInsets(new Insets(0,0,5,5))
						.setTextFieldColumns(10);
				rosterPanel.add(textField.getTextField(), textField.getContains());	
				gridy = gridy + 2;//8
			}
			
			if(Verification.verifyList(invoice.getRoster().getRosters())){
				gridy++;//9
				ComponentLabelPanel labelTitle = new ComponentLabelPanel("СПИСОК ТОВАРОВ (УСЛУГ): ", 1,gridy)
						.setContainsGridwidth(2)
						.setLabelFont(FONT)
						.setContainsAnchor(GridBagConstraints.WEST)
						.setContainsInsets(new Insets(0,0,5,5));
				rosterPanel.add(labelTitle.getLabel(), labelTitle.getContains());
				gridy++;//10
				
				for(int index = 0;index<invoice.getRoster().getRosters().size();index++){
					if(Verification.verifySection(invoice.getRoster().getRosters().get(index))){
						if(Verification.verifySection(invoice.getRoster().getRosters().get(index))){
							if(Verification.verifyField(invoice.getRoster().getRosters().get(index).getNumber())){
								gridy++;//11
								ComponentLabelPanel label = new ComponentLabelPanel("№ п/п", 1,gridy)
										.setContainsGridwidth(2)
										.setLabelFont(FONT)
										.setContainsAnchor(GridBagConstraints.WEST)
										.setContainsInsets(new Insets(0,0,5,5));
								rosterPanel.add(label.getLabel(), label.getContains());
								
								ComponentLabelPanel labelR = new ComponentLabelPanel(" [number] ", 3, gridy)
										.setContainsAnchor(GridBagConstraints.EAST)
										.setContainsInsets(new Insets(0,0,5,5));
								rosterPanel.add(labelR.getLabel(), labelR.getContains());
								
								ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getRoster().getRosters().get(index).getNumber(), 4, gridy)
										.setContainsFill(GridBagConstraints.HORIZONTAL)
										.setContainsInsets(new Insets(0,0,5,5))
										.setTextFieldColumns(10);
								rosterPanel.add(textField.getTextField(), textField.getContains());	
							}
							
							if(Verification.verifyField(invoice.getRoster().getRosters().get(index).getName())){
								gridy++;//12
								ComponentLabelPanel label = new ComponentLabelPanel(HtmlLines.getHtmlText(new String[]{"Наименование товара (работ, услуг),",
																														"имущественных прав"}), 1,gridy)
										.setContainsGridheight(2)
										.setContainsGridwidth(2)
										.setLabelFont(FONT)
										.setContainsAnchor(GridBagConstraints.WEST)
										.setContainsInsets(new Insets(0,0,5,5));
								rosterPanel.add(label.getLabel(), label.getContains());
								
								ComponentLabelPanel labelR = new ComponentLabelPanel(" [name] ", 3, gridy)
										.setContainsAnchor(GridBagConstraints.EAST)
										.setContainsInsets(new Insets(0,0,5,5));
								rosterPanel.add(labelR.getLabel(), labelR.getContains());
								
								ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getRoster().getRosters().get(index).getName(), 4, gridy)
										.setContainsFill(GridBagConstraints.HORIZONTAL)
										.setContainsInsets(new Insets(0,0,5,5))
										.setTextFieldColumns(10);
								rosterPanel.add(textField.getTextField(), textField.getContains());	
								gridy = gridy + 2;//14
							}
							
							if(Verification.verifyField(invoice.getRoster().getRosters().get(index).getCode())){
								gridy++;//15
								ComponentLabelPanel label = new ComponentLabelPanel("Код товара (ТН ВЭД ЕАЭС)", 1,gridy)
										.setContainsGridwidth(2)
										.setLabelFont(FONT)
										.setContainsAnchor(GridBagConstraints.WEST)
										.setContainsInsets(new Insets(0,0,5,5));
								rosterPanel.add(label.getLabel(), label.getContains());
								
								ComponentLabelPanel labelR = new ComponentLabelPanel(" [code] ", 3, gridy)
										.setContainsAnchor(GridBagConstraints.EAST)
										.setContainsInsets(new Insets(0,0,5,5));
								rosterPanel.add(labelR.getLabel(), labelR.getContains());
								
								ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getRoster().getRosters().get(index).getCode(), 4, gridy)
										.setContainsFill(GridBagConstraints.HORIZONTAL)
										.setContainsInsets(new Insets(0,0,5,5))
										.setTextFieldColumns(10);
								rosterPanel.add(textField.getTextField(), textField.getContains());	
							}
							
							if(Verification.verifyField(invoice.getRoster().getRosters().get(index).getCodeOced())){
								gridy++;//16
								ComponentLabelPanel label = new ComponentLabelPanel("Код видов деятельности (ОКЭД)", 1,gridy)
										.setContainsGridwidth(2)
										.setLabelFont(FONT)
										.setContainsAnchor(GridBagConstraints.WEST)
										.setContainsInsets(new Insets(0,0,5,5));
								rosterPanel.add(label.getLabel(), label.getContains());
								
								ComponentLabelPanel labelR = new ComponentLabelPanel(" [code_oced] ", 3, gridy)
										.setContainsAnchor(GridBagConstraints.EAST)
										.setContainsInsets(new Insets(0,0,5,5));
								rosterPanel.add(labelR.getLabel(), labelR.getContains());
								
								ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getRoster().getRosters().get(index).getCodeOced(), 4, gridy)
										.setContainsFill(GridBagConstraints.HORIZONTAL)
										.setContainsInsets(new Insets(0,0,5,5))
										.setTextFieldColumns(10);
								rosterPanel.add(textField.getTextField(), textField.getContains());	
							}
							
							if(Verification.verifyField(invoice.getRoster().getRosters().get(index).getUnits())){
								gridy++;//17
								ComponentLabelPanel label = new ComponentLabelPanel("Единица измерения", 1,gridy)
										.setContainsGridwidth(2)
										.setLabelFont(FONT)
										.setContainsAnchor(GridBagConstraints.WEST)
										.setContainsInsets(new Insets(0,0,5,5));
								rosterPanel.add(label.getLabel(), label.getContains());
								
								ComponentLabelPanel labelR = new ComponentLabelPanel(" [units] ", 3, gridy)
										.setContainsAnchor(GridBagConstraints.EAST)
										.setContainsInsets(new Insets(0,0,5,5));
								rosterPanel.add(labelR.getLabel(), labelR.getContains());
								
								ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getRoster().getRosters().get(index).getUnits(), 4, gridy)
										.setContainsFill(GridBagConstraints.HORIZONTAL)
										.setContainsInsets(new Insets(0,0,5,5))
										.setTextFieldColumns(10);
								rosterPanel.add(textField.getTextField(), textField.getContains());	
							}
							
							if(Verification.verifyField(invoice.getRoster().getRosters().get(index).getCount())){
								gridy++;//18
								ComponentLabelPanel label = new ComponentLabelPanel("Количество (объём)", 1,gridy)
										.setContainsGridwidth(2)
										.setLabelFont(FONT)
										.setContainsAnchor(GridBagConstraints.WEST)
										.setContainsInsets(new Insets(0,0,5,5));
								rosterPanel.add(label.getLabel(), label.getContains());
								
								ComponentLabelPanel labelR = new ComponentLabelPanel(" [count] ", 3, gridy)
										.setContainsAnchor(GridBagConstraints.EAST)
										.setContainsInsets(new Insets(0,0,5,5));
								rosterPanel.add(labelR.getLabel(), labelR.getContains());
								
								ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getRoster().getRosters().get(index).getCount(), 4, gridy)
										.setContainsFill(GridBagConstraints.HORIZONTAL)
										.setContainsInsets(new Insets(0,0,5,5))
										.setTextFieldColumns(10);
								rosterPanel.add(textField.getTextField(), textField.getContains());	
							}
							
							if(Verification.verifyField(invoice.getRoster().getRosters().get(index).getPrice())){
								gridy++;//19
								ComponentLabelPanel label = new ComponentLabelPanel(HtmlLines.getHtmlText(new String[]{"Цена (тариф) за единицу товара (работы, услуги)",
																														"имущественных прав без учёта НДС"}), 1,gridy)
										.setContainsGridheight(2)
										.setContainsGridwidth(2)
										.setLabelFont(FONT)
										.setContainsAnchor(GridBagConstraints.WEST)
										.setContainsInsets(new Insets(0,0,5,5));
								rosterPanel.add(label.getLabel(), label.getContains());
								
								ComponentLabelPanel labelR = new ComponentLabelPanel(" [price] ", 3, gridy)
										.setContainsAnchor(GridBagConstraints.EAST)
										.setContainsInsets(new Insets(0,0,5,5));
								rosterPanel.add(labelR.getLabel(), labelR.getContains());
								
								ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getRoster().getRosters().get(index).getPrice(), 4, gridy)
										.setContainsFill(GridBagConstraints.HORIZONTAL)
										.setContainsInsets(new Insets(0,0,5,5))
										.setTextFieldColumns(10);
								rosterPanel.add(textField.getTextField(), textField.getContains());	
								gridy = gridy + 2;//21
							}
							
							if(Verification.verifyField(invoice.getRoster().getRosters().get(index).getCost())){
								gridy++;//22
								ComponentLabelPanel label = new ComponentLabelPanel(HtmlLines.getHtmlText(new String[]{"Стоимость товаров (работ, услуг)",
																														"имущественных прав без учёта НДС"}), 1,gridy)
										.setContainsGridheight(2)
										.setContainsGridwidth(2)
										.setLabelFont(FONT)
										.setContainsAnchor(GridBagConstraints.WEST)
										.setContainsInsets(new Insets(0,0,5,5));
								rosterPanel.add(label.getLabel(), label.getContains());
								
								ComponentLabelPanel labelR = new ComponentLabelPanel(" [cost] ", 3, gridy)
										.setContainsAnchor(GridBagConstraints.EAST)
										.setContainsInsets(new Insets(0,0,5,5));
								rosterPanel.add(labelR.getLabel(), labelR.getContains());
								
								ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getRoster().getRosters().get(index).getCost(), 4, gridy)
										.setContainsFill(GridBagConstraints.HORIZONTAL)
										.setContainsInsets(new Insets(0,0,5,5))
										.setTextFieldColumns(10);
								rosterPanel.add(textField.getTextField(), textField.getContains());	
								gridy = gridy + 2;//24
							}
							
							if(Verification.verifyField(invoice.getRoster().getRosters().get(index).getSummaExcise())){
								gridy++;//25
								ComponentLabelPanel label = new ComponentLabelPanel("В том числе сумма акциза, руб", 1,gridy)
										.setContainsGridwidth(2)
										.setLabelFont(FONT)
										.setContainsAnchor(GridBagConstraints.WEST)
										.setContainsInsets(new Insets(0,0,5,5));
								rosterPanel.add(label.getLabel(), label.getContains());
								
								ComponentLabelPanel labelR = new ComponentLabelPanel(" [summaExcise] ", 3, gridy)
										.setContainsAnchor(GridBagConstraints.EAST)
										.setContainsInsets(new Insets(0,0,5,5));
								rosterPanel.add(labelR.getLabel(), labelR.getContains());
								
								ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getRoster().getRosters().get(index).getSummaExcise(), 4, gridy)
										.setContainsFill(GridBagConstraints.HORIZONTAL)
										.setContainsInsets(new Insets(0,0,5,5))
										.setTextFieldColumns(10);
								rosterPanel.add(textField.getTextField(), textField.getContains());	
							}
							
							if(Verification.verifySection(invoice.getRoster().getRosters().get(index).getVat())){
								if(Verification.verifyField(invoice.getRoster().getRosters().get(index).getVat().getRate())){
									gridy++;//26
									ComponentLabelPanel label = new ComponentLabelPanel("Ставка НДС", 2,gridy)
											.setLabelFont(FONT)
											.setContainsAnchor(GridBagConstraints.WEST)
											.setContainsInsets(new Insets(0,0,5,5));
									rosterPanel.add(label.getLabel(), label.getContains());
									
									ComponentLabelPanel labelR = new ComponentLabelPanel(" [vat - rate] ", 3, gridy)
											.setContainsAnchor(GridBagConstraints.EAST)
											.setContainsInsets(new Insets(0,0,5,5));
									rosterPanel.add(labelR.getLabel(), labelR.getContains());
									
									ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getRoster().getRosters().get(index).getVat().getRate(), 4, gridy)
											.setContainsFill(GridBagConstraints.HORIZONTAL)
											.setContainsInsets(new Insets(0,0,5,5))
											.setTextFieldColumns(10);
									rosterPanel.add(textField.getTextField(), textField.getContains());	
								}
								
								if(Verification.verifyField(invoice.getRoster().getRosters().get(index).getVat().getRateType())){
									gridy++;//27
									ComponentLabelPanel label = new ComponentLabelPanel("Ставка НДС (тип)", 2,gridy)
											.setLabelFont(FONT)
											.setContainsAnchor(GridBagConstraints.WEST)
											.setContainsInsets(new Insets(0,0,5,5));
									rosterPanel.add(label.getLabel(), label.getContains());
									
									ComponentLabelPanel labelR = new ComponentLabelPanel(" [vat - rateType] ", 3, gridy)
											.setContainsAnchor(GridBagConstraints.EAST)
											.setContainsInsets(new Insets(0,0,5,5));
									rosterPanel.add(labelR.getLabel(), labelR.getContains());
									
									ComponentTextFieldPanel textField = null;
									switch(invoice.getRoster().getRosters().get(index).getVat().getRateType().trim()){
										case "DECIMAL":{
											textField = new ComponentTextFieldPanel("Фиксированная ставка НДС (кроме 0%)", 4, gridy);
											break;}
										case "ZERO":{
											textField = new ComponentTextFieldPanel("Ставка НДС 0%", 4, gridy);
											break;}
										case "NO_VAT":{
											textField = new ComponentTextFieldPanel("Без НДС", 4, gridy);
											break;}
										case "CALCULATED":{
											textField = new ComponentTextFieldPanel("Расчетная ставка НДС", 4, gridy);
											break;}
										default: {
											textField = new ComponentTextFieldPanel("",4,gridy);
											break;}
									}
									textField.setContainsFill(GridBagConstraints.HORIZONTAL)
											.setContainsInsets(new Insets(0,0,5,5))
											.setTextFieldColumns(10);
									rosterPanel.add(textField.getTextField(), textField.getContains());	
								}
								
								if(Verification.verifyField(invoice.getRoster().getRosters().get(index).getVat().getSummaVat())){
									gridy++;//28
									ComponentLabelPanel label = new ComponentLabelPanel("Сумма НДС", 2,gridy)
											.setLabelFont(FONT)
											.setContainsAnchor(GridBagConstraints.WEST)
											.setContainsInsets(new Insets(0,0,5,5));
									rosterPanel.add(label.getLabel(), label.getContains());
									
									ComponentLabelPanel labelR = new ComponentLabelPanel(" [vat - summaVat] ", 3, gridy)
											.setContainsAnchor(GridBagConstraints.EAST)
											.setContainsInsets(new Insets(0,0,5,5));
									rosterPanel.add(labelR.getLabel(), labelR.getContains());
									
									ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getRoster().getRosters().get(index).getVat().getSummaVat(), 4, gridy)
											.setContainsFill(GridBagConstraints.HORIZONTAL)
											.setContainsInsets(new Insets(0,0,5,5))
											.setTextFieldColumns(10);
									rosterPanel.add(textField.getTextField(), textField.getContains());	
								}
							}
							
							if(Verification.verifyField(invoice.getRoster().getRosters().get(index).getCostVat())){
								gridy++;//29
								ComponentLabelPanel label = new ComponentLabelPanel(HtmlLines.getHtmlText(new String[]{"Стоимость товаров (работ, услуг)",
																														"имущественных прав с учётом НДС"}), 1,gridy)
										.setContainsGridheight(2)
										.setContainsGridwidth(2)
										.setLabelFont(FONT)
										.setContainsAnchor(GridBagConstraints.WEST)
										.setContainsInsets(new Insets(0,0,5,5));
								rosterPanel.add(label.getLabel(), label.getContains());
								
								ComponentLabelPanel labelR = new ComponentLabelPanel(" [costVat] ", 3, gridy)
										.setContainsAnchor(GridBagConstraints.EAST)
										.setContainsInsets(new Insets(0,0,5,5));
								rosterPanel.add(labelR.getLabel(), labelR.getContains());
								
								ComponentTextFieldPanel textField = new ComponentTextFieldPanel(invoice.getRoster().getRosters().get(index).getCostVat	(), 4, gridy)
										.setContainsFill(GridBagConstraints.HORIZONTAL)
										.setContainsInsets(new Insets(0,0,5,5))
										.setTextFieldColumns(10);
								rosterPanel.add(textField.getTextField(), textField.getContains());	
								gridy = gridy + 2;//31
							}
							
							if(Verification.verifyList(invoice.getRoster().getRosters().get(index).getDescriptions())){
								if(invoice.getRoster().getRosters().get(index).getDescriptions().size() > 0){
										gridy++;//32
										ComponentLabelPanel label = new ComponentLabelPanel("Дополнительные данные", 1,gridy)
												.setContainsGridwidth(2)
												.setLabelFont(FONT)
												.setContainsAnchor(GridBagConstraints.WEST)
												.setContainsInsets(new Insets(0,0,5,5));
										rosterPanel.add(label.getLabel(), label.getContains());
										
										ComponentLabelPanel labelR = new ComponentLabelPanel(" [description] ", 3, gridy)
												.setContainsAnchor(GridBagConstraints.EAST)
												.setContainsInsets(new Insets(0,0,5,5));
										rosterPanel.add(labelR.getLabel(), labelR.getContains());
											
								}
								for(int jndex = 0;jndex < invoice.getRoster().getRosters().get(index).getDescriptions().size();jndex++){
									if(Verification.verifySection(invoice.getRoster().getRosters().get(index).getDescriptions().get(jndex))){
										if(Verification.verifyField(invoice.getRoster().getRosters().get(index).getDescriptions().get(jndex).getDescription())){
											if(index > 1){
												ComponentLabelPanel label = new ComponentLabelPanel("", 1,gridy)
														.setContainsGridwidth(2)
														.setLabelFont(FONT)
														.setContainsAnchor(GridBagConstraints.WEST)
														.setContainsInsets(new Insets(0,0,5,5));
												rosterPanel.add(label.getLabel(), label.getContains());
												
												ComponentLabelPanel labelR = new ComponentLabelPanel(" [description] ", 3, gridy)
														.setContainsAnchor(GridBagConstraints.EAST)
														.setContainsInsets(new Insets(0,0,5,5));
												rosterPanel.add(labelR.getLabel(), labelR.getContains());
											}
											ComponentTextFieldPanel textField = null;
											switch(invoice.getRoster().getRosters().get(index).getDescriptions().get(jndex).getDescription().trim()){
												case "DEDUCTION_IN_FULL":{
													textField = new ComponentTextFieldPanel("Вычет в полном объёме", 4, gridy);
													break;
												}
												case "VAT_EXEMPTION":{
													textField = new ComponentTextFieldPanel("Освобождение от НДС", 4, gridy);
													break;
												}
												case "OUTSIDE_RB":{
													textField = new ComponentTextFieldPanel("Реализация за пределами Республики Беларусь", 4, gridy);
													break;
												}
												case "IMPORT_VAT":{
													textField = new ComponentTextFieldPanel("Ввозной НДС", 4, gridy);
													break;
												}
												default:{
													textField = new ComponentTextFieldPanel("", 4, gridy);
													break;
												}
											}											
											textField.setContainsFill(GridBagConstraints.HORIZONTAL)
													.setContainsInsets(new Insets(0,0,5,5))
													.setTextFieldColumns(10);
											rosterPanel.add(textField.getTextField(), textField.getContains());
											gridy++;//33
										}
									}
								}
								//
							}
						
						}
					}
				}
			}
		}		
		
		gbl_rosterPane.columnWidths = new int[]{10, 10, 0, 0, 0, 10, 0};
		gbl_rosterPane.rowHeights = getInt(gridy);
		
		gbl_rosterPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_rosterPane.rowWeights = getDouble(gridy);
		
		return rosterPane;
	}
	
	private static double[] getDouble(int gridy){
		DoubleList doubleList = new DoubleList();
		doubleList.add(0.0);
		for(int index = 0;index<gridy;index++){
			doubleList.add(0.0);
		}
		doubleList.add(0.0);
		doubleList.add(Double.MIN_VALUE);
		return doubleList.getArray();
		
	}
	
	private static int[] getInt(int gridy){
		IntegerList intList = new IntegerList();
  		intList.add(10);
  		for(int index=0; index<gridy; index++){
  			intList.add(0);
  		}
  		intList.add(10);
  		intList.add(0);
  		return intList.getArray();
	}
}