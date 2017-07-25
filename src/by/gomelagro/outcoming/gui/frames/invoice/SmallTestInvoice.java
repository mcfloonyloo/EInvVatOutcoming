package by.gomelagro.outcoming.gui.frames.invoice;

import java.sql.SQLException;

import by.gomelagro.outcoming.gui.db.WorkingOutcomingTable;
import by.gomelagro.outcoming.gui.frames.invoice.data.Description;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.BooleanList;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.ErrorBooleanList;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.item.DocumentItem;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.item.ErrorBooleanItem;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.item.RosterItem;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.item.SenderItem;
import by.gomelagro.outcoming.gui.frames.invoice.verification.Verification;

public class SmallTestInvoice {
	
	private Invoice invoice = null;
	private ErrorBooleanList list = null;
	
	public SmallTestInvoice(Invoice invoice){
		this.invoice = invoice;
		this.list = new ErrorBooleanList();
	}
	
	public SmallTestInvoice test(){
		
		ErrorBooleanList list = new ErrorBooleanList();
		if(!testDates()){//если ошибок не обнаружено
			list.add(new ErrorBooleanItem().setError("Дата совершения операции: обнаружены проблемы").setFlagError(false));
		}
				
		if(!testRequisites()){
			list.add(new ErrorBooleanItem().setError("Реквизиты: обнаружены проблемы").setFlagError(false));
		}
		
		if(!testContracts()){
			list.add(new ErrorBooleanItem().setError("Условия поставки: обнаружены проблемы").setFlagError(false));
		}
		
		if(!testRosters()){
			list.add(new ErrorBooleanItem().setError("Данные по товарам: обнаружены проблемы").setFlagError(false));
		}
		
		if(!testTablesValues()){
			list.add(new ErrorBooleanItem().setError("Табличные значения: обнаружены проблемы").setFlagError(false));
		}
		
		this.list = list;
		
		return this;
	}
	
	public boolean getResult(){
		boolean result = true;
		for(int index = 0; index < list.size(); index++){
			if(list.get(index).getFlagError() == false){
				result = false;
			}
		}
		return result;
	}
	
	public String getErrorLine(){
		String line = "<html>";
		if(!getResult()){
			for(int index = 0; index < this.list.size(); index++){
				line = line + this.list.get(index).getError();
				if(index < this.list.size()){
					line = line + "<br>";
				}
			}
		}
		line = line + "</html>";
		return line;
	}
	
	private boolean testDates(){
		boolean result = true;
		BooleanList list = new BooleanList();
		
		if(Verification.verifyField(invoice.getGeneral().getDateTransaction())){
			if(Verification.verifyList(invoice.getDeliveryCondition().getContract().getDocuments())){
				for(DocumentItem item: invoice.getDeliveryCondition().getContract().getDocuments()){
					if(Verification.verifySection(item.getDocType())){
						if(Verification.verifyField(item.getDocType().getCode())){
							switch(item.getDocType().getCode().trim()){
							case "609":{
								list.add(true);
								break;
							}
							case "612":{
								list.add(true);
								break;
							}
							default:{
								if(Verification.verifyField(item.getDate())){
									list.add(item.getDate().trim().equals(invoice.getGeneral().getDateTransaction().trim()));							
								}else{
									list.add(false);
								}
								break;
							}
							}
						}else{
							list.add(false);
						}
					}else{
						list.add(false);
					}
				}
			}else{
				list.add(false);
			}
		}else{
			list.add(false);
		}
		result = list.getResult();
		return result;
	}
	
	private boolean testRequisites(){
		boolean result = true;
		BooleanList list = new BooleanList();
		//Поставщик
		if(Verification.verifyField(invoice.getProvider().getUnp())){
			list.add(Verification.verifyField(invoice.getProvider().getCountryCode()));
		}
		list.add(Verification.verifyField(invoice.getProvider().getName()));
		list.add(Verification.verifyField(invoice.getProvider().getAddress()));
		//Получатель
		if(Verification.verifyField(invoice.getRecipient().getUnp())){
			list.add(Verification.verifyField(invoice.getRecipient().getCountryCode()));
		}		
		list.add(Verification.verifyField(invoice.getRecipient().getName()));
		list.add(Verification.verifyField(invoice.getRecipient().getAddress()));
		//Грузоотправитель
		if(Verification.verifyList(invoice.getSenderReceiver().getConsignors())){
			for(SenderItem item: invoice.getSenderReceiver().getConsignors()){
				if(Verification.verifySection(item)){
					if(Verification.verifyField(item.getUnp())){
						list.add(Verification.verifyField(item.getCountryCode()));
					}
					list.add(Verification.verifyField(item.getName()));
					list.add(Verification.verifyField(item.getAddress()));
				}else{
					list.add(false);
				}
			}
		}else{
			list.add(false);
		}
		//Грузополучатель
		if(Verification.verifyList(invoice.getSenderReceiver().getConsignees())){
			for(SenderItem item: invoice.getSenderReceiver().getConsignees()){
				if(Verification.verifySection(item)){
					if(Verification.verifyField(item.getUnp())){
						list.add(Verification.verifyField(item.getCountryCode()));
					}
					list.add(Verification.verifyField(item.getName()));
					list.add(Verification.verifyField(item.getAddress()));
				}else{
					list.add(false);
				}
			}
		}else{
			list.add(false);
		}
		result = list.getResult();
		return result;
	}
	
	private boolean testContracts(){
		boolean result = true;
		BooleanList list = new BooleanList();
		if(Verification.verifySection(invoice.getDeliveryCondition().getContract())){
			list.add(Verification.verifyField(invoice.getDeliveryCondition().getContract().getNumber()));
			list.add(Verification.verifyField(invoice.getDeliveryCondition().getContract().getDate()));
			if(Verification.verifyList(invoice.getDeliveryCondition().getContract().getDocuments())){
				for(DocumentItem item: invoice.getDeliveryCondition().getContract().getDocuments()){
					if(Verification.verifySection(item)){
						if(Verification.verifySection(item.getDocType())){
							if(Verification.verifyField(item.getDocType().getCode())){
								switch(item.getDocType().getCode().trim()){
								case "602":{
									list.add(Verification.verifyField(item.getBlankCode()));
									list.add(Verification.verifyField(item.getDate()));
									list.add(Verification.verifyField(item.getSeria()));
									list.add(Verification.verifyField(item.getNumber()));
									list.add(item.getSeria().length() == 2);
									break;
								}
								case "603":{
									list.add(Verification.verifyField(item.getBlankCode()));
									list.add(Verification.verifyField(item.getDate()));
									list.add(Verification.verifyField(item.getSeria()));
									list.add(Verification.verifyField(item.getNumber()));
									list.add(item.getSeria().length() == 2);
									break;
								}
								case "604":{
									list.add(Verification.verifyField(item.getNumber()));
									list.add(Verification.verifyField(item.getDate()));
									break;
								}
								case "605":{
									list.add(Verification.verifyField(item.getNumber()));
									list.add(Verification.verifyField(item.getDate()));
									break;
								}
								case "606":{
									list.add(Verification.verifyField(item.getNumber()));
									list.add(Verification.verifyField(item.getDate()));
									break;
								}
								case "609":{
									list.add(Verification.verifyField(item.getNumber()));
									list.add(Verification.verifyField(item.getDate()));
									break;
								}
								case "611":{
									list.add(Verification.verifyField(item.getNumber()));
									list.add(Verification.verifyField(item.getDate()));
									break;
								}
								case "612":{
									list.add(Verification.verifyField(item.getNumber()));
									list.add(Verification.verifyField(item.getDate()));
									break;
								}
								default:{
									System.out.println("ЭСЧФ "+invoice.getGeneral().getNumber()+" \\ Условия поставки \\ Тип документа: "
								+WorkingOutcomingTable.Additional.getTypeDocumentName(item.getDocType().getCode()));
									list.add(Verification.verifyField(item.getDate()));
									list.add(Verification.verifyField(item.getNumber()));
									break;
								}
								}
							}else{
								list.add(false);
							}
						}else{
							list.add(false);
						}
					}else{
						list.add(false);
					}
				}
			}else{
				list.add(false);
			}
		}else{
			list.add(false);
		}
		result = list.getResult();
		return result;
	}
	
	private boolean testRosters(){
		BooleanList list = new BooleanList();
		if(Verification.verifySection(invoice.getRoster())){
			if(Verification.verifyList(invoice.getRoster().getRosters())){
				for(RosterItem item: invoice.getRoster().getRosters()){
					if(Verification.verifySection(item)){
						if(invoice.getRecipient().getCountryCode().trim().equals("112")){
							if(Verification.verifyField(item.getUnits())){
								list.add(true);
							}else{
								list.add(false);
							}
						}else{
							if(Verification.verifySection(invoice.getDeliveryCondition().getContract().getDocuments().get(0).getDocType())){
								if(Verification.verifyField(invoice.getDeliveryCondition().getContract().getDocuments().get(0).getDocType().getCode())){
									switch(invoice.getDeliveryCondition().getContract().getDocuments().get(0).getDocType().getCode().trim()){
									case "602":{
										list.add(Verification.verifyField(item.getCode()));
										break;
									}
									case "603":{
										list.add(Verification.verifyField(item.getCode()));
										break;
									}
									case "606":{
										list.add(Verification.verifyField(item.getCodeOced()));
										break;
									}
									case "607":{
										list.add(Verification.verifyField(item.getCode()));
										break;
									}
									case "612":{
										list.add(Verification.verifyField(item.getCodeOced()));
										break;
									}
									default:{
										list.add((Verification.verifyField(item.getCode()))||(Verification.verifyField(item.getCodeOced())));	
									}									
									}
								}else{
									list.add(false);
								}
							}else{
								list.add(false);
							}
						}
					}else{
						list.add(false);
					}
				}
			}else{
				list.add(false);
			}
		}else{
			list.add(false);
		}
		return list.getResult();
	}
	
	private boolean testTablesValues(){
		boolean result = false;
		BooleanList list = new BooleanList();
		 
		//Проверка табличных значений Country
		if(Verification.verifySection(invoice.getProvider())){
			if(Verification.verifyField(invoice.getProvider().getCountryCode())){
				try {
					list.add(WorkingOutcomingTable.Count.isCountryCode(invoice.getProvider().getCountryCode()));
				} catch (SQLException e) {
					System.err.println("Метод проверки табличных значений ЭСЧФ "+invoice.getGeneral().getNumber()+": ошибка получения значения кода страны поставщика");
					list.add(false);
				}
			}else{
				list.add(false);
			}
		}
	
		if(Verification.verifySection(invoice.getRecipient())){
			if(Verification.verifyField(invoice.getRecipient().getCountryCode())){
				try{
					list.add(WorkingOutcomingTable.Count.isCountryCode(invoice.getRecipient().getCountryCode()));
				} catch (SQLException e) {
					System.err.println("Метод проверки табличных значений ЭСЧФ "+invoice.getGeneral().getNumber()+": ошибка получения значения кода страны получателя");
					list.add(false);
				}
			}else{
				list.add(false);
			}
		}
		
		if(Verification.verifySection(invoice.getSenderReceiver())){
			if(Verification.verifyList(invoice.getSenderReceiver().getConsignors())){
				for(SenderItem item: invoice.getSenderReceiver().getConsignors()){
					if(Verification.verifySection(item)){
						if(Verification.verifyField(item.getCountryCode())){
							try{
								list.add(WorkingOutcomingTable.Count.isCountryCode(item.getCountryCode()));
							} catch (SQLException e) {
								System.err.println("Метод проверки табличных значений ЭСЧФ "+invoice.getGeneral().getNumber()+": ошибка получения значения кода страны грузоотправителя");
								list.add(false);
							}
						}else{
							list.add(false);
						}
					}else{
						list.add(false);
					}
				}
			}
			if(Verification.verifyList(invoice.getSenderReceiver().getConsignees())){
				if(Verification.verifyList(invoice.getSenderReceiver().getConsignees())){
					for(SenderItem item: invoice.getSenderReceiver().getConsignees()){
						if(Verification.verifySection(item)){
							if(Verification.verifyField(item.getCountryCode())){
								try{
									list.add(WorkingOutcomingTable.Count.isCountryCode(item.getCountryCode()));
								} catch (SQLException e) {
									System.err.println("Метод проверки табличных значений ЭСЧФ "+invoice.getGeneral().getNumber()+": ошибка получения значения из таблицы грузополучателя");
									list.add(false);
								}
							}else{
								list.add(false);
							}
						}else{
							list.add(false);
						}
					}
				}
			}
		}
		
		//проверка табличных значений Branch
		if(Verification.verifySection(invoice.getProvider())){
			if(Verification.verifyField(invoice.getProvider().getBranchCode())){
				try {
					list.add(WorkingOutcomingTable.Count.isBranch(invoice.getProvider().getBranchCode()));
				} catch (SQLException e) {
					System.err.println("Метод проверки табличных значений ЭСЧФ "+invoice.getGeneral().getNumber()+": ошибка получения значения кода филиала поставщика");
					list.add(false);
				}
			}
		}
		if(Verification.verifySection(invoice.getRecipient())){
			if(Verification.verifyField(invoice.getRecipient().getBranchCode())){
				try {
					list.add(WorkingOutcomingTable.Count.isBranch(invoice.getRecipient().getBranchCode()));
				} catch (SQLException e) {
					System.err.println("Метод проверки табличных значений ЭСЧФ "+invoice.getGeneral().getNumber()+": ошибка получения значения кода филиала получателя");
					list.add(false);
				}
			}
		}
		
		//проверка значения DocumentType
		if(Verification.verifySection(invoice.getDeliveryCondition())){
			if(Verification.verifySection(invoice.getDeliveryCondition().getContract())){
				if(Verification.verifyList(invoice.getDeliveryCondition().getContract().getDocuments())){
					for(DocumentItem item: invoice.getDeliveryCondition().getContract().getDocuments()){
						if(Verification.verifySection(item)){
							if(Verification.verifySection(item.getDocType())){
								if(Verification.verifyField(item.getDocType().getCode())){
									try {
										list.add(WorkingOutcomingTable.Count.isTypeDocument(item.getDocType().getCode()));
									} catch (SQLException e) {
										System.err.println("Метод проверки табличных значений ЭСЧФ "+invoice.getGeneral().getNumber()+": ошибка получения значения типа документа");
										list.add(false);
									}
								}else{
									list.add(false);
								}
							}else{
								list.add(false);
							}
						}else{
							list.add(false);
						}
					}
				}
			}
		}
		
		//Проверка значения Invoice DocumentType
		if(Verification.verifySection(invoice.getGeneral())){
			if(Verification.verifyField(invoice.getGeneral().getDocumentType())){
				list.add(InnerVerification.isInvoiceDocType(invoice.getGeneral().getDocumentType()));
			}else{
				list.add(false);
			}
		}
		
		//Проверка значения Provider StatusType
		if(Verification.verifySection(invoice.getProvider())){
			if(Verification.verifyField(invoice.getProvider().getProviderStatus())){
				list.add(InnerVerification.isProviderStatusType(invoice.getProvider().getProviderStatus()));
			}else{
				list.add(false);
			}
		}
		
		//Проверка значения Recipient StatusType
		if(Verification.verifySection(invoice.getRecipient())){
			if(Verification.verifyField(invoice.getRecipient().getRecipientStatus())){
				list.add(InnerVerification.isRecipientStatusType(invoice.getRecipient().getRecipientStatus()));
			}else{
				list.add(false);
			}
		}
		
		//Проверка значения RosterItem DescriptionType
		if(Verification.verifySection(invoice.getRoster())){
			if(Verification.verifyList(invoice.getRoster().getRosters())){
				for(RosterItem item: invoice.getRoster().getRosters()){
					if(Verification.verifySection(item)){
						if(Verification.verifyList(item.getDescriptions())){
							for(Description description: item.getDescriptions()){
								if(Verification.verifySection(description)){
									list.add(InnerVerification.isDescriptionType(description.getDescription()));
								}else{
									list.add(false);
								}
							}
						}else{
							list.add(false);
						}
					}else{
						list.add(false);
					}
				}
			}
		}
		
		//Проверка значения RosterItem RateType
		if(Verification.verifySection(invoice.getRoster())){
			if(Verification.verifyList(invoice.getRoster().getRosters())){
				for(RosterItem item: invoice.getRoster().getRosters()){
					if(Verification.verifySection(item)){
						if(Verification.verifySection(item.getVat())){
							if(Verification.verifyField(item.getVat().getRateType())){
								list.add(InnerVerification.isRateType(item.getVat().getRateType()));
							}else{
								list.add(false);
							}
						}else{
							list.add(false);
						}
					}
				}
			}
		}
		
		//проверка табличных значений Unit
		if(Verification.verifySection(invoice.getRoster())){
			if(Verification.verifyList(invoice.getRoster().getRosters())){
				for(RosterItem item: invoice.getRoster().getRosters()){
					if(Verification.verifySection(item)){
						if(Verification.verifyField(item.getUnits())){
							try {
								list.add(WorkingOutcomingTable.Count.isUnit(item.getUnits()));
							} catch (SQLException e) {
								System.err.println("Метод проверки табличных значений ЭСЧФ "+invoice.getGeneral().getNumber()+": ошибка получения значения единицы измерения");
								list.add(false);
							}
						}else{
							list.add(false);
						}
					}else{
						list.add(false);
					}
				}
			}else{
				list.add(false);
			}
		}
		result = list.getResult();
		return result;
	}
	
	public static class InnerVerification{	
		public static boolean isRateType(String rateType){
			boolean result = false;
			switch(rateType){
			case "DECIMAL": {result = true; break;}
			case "ZERO": {result = true; break;}
			case "NO_VAT": {result = true; break;}
			case "CALCULATED": {result = true; break;}
			default: {result = false; break;}
			}
			return result;
		}
		
		public static boolean isInvoiceDocType(String invoiceDocType){
			boolean result = false;
			switch(invoiceDocType){
			case "ORIGINAL": {result = true; break;}
			case "ADDITIONAL": {result = true; break;}
			case "FIXED": {result = true; break;}
			case "ADD_NO_REFERENCE": {result = true; break;}
			default: {result = false; break;}
			}		
			return result;
		}
		
		public static boolean isProviderStatusType(String providerStatusType){
			boolean result = false;
			switch(providerStatusType){
			case "SELLER": {result = true; break;}
			case "CONSIGNOR": {result = true; break;}
			case "COMMISSIONAIRE": {result = true; break;}
			case "TAX_DEDUCTION_PAYER": {result = true; break;}
			case "TRUSTEE": {result = true; break;}
			case "FOREIGN_ORGANIZATION": {result = true; break;}
			case "AGENT": {result = true; break;}
			case "DEVELOPER": {result = true; break;}
			case "TURNOVERS_ON_SALE_PAYER": {result = true; break;}
			default: {result = false; break;}
			}
			return result;
		}
		
		public static boolean isRecipientStatusType(String recipientStatusType){
			boolean result = false;
			switch(recipientStatusType){
			case "CUSTOMER": {result = true; break;}
			case "CONSUMER": {result = true; break;}
			case "CONSIGNOR": {result = true; break;}
			case "COMMISSIONAIRE": {result = true; break;}
			case "TAX_DEDUCTION_PAYER": {result = true; break;}
			case "FOREIGN_ORGANIZATION": {result = true; break;}
			case "TURNOVERS_ON_SALE_PAYER": {result = true; break;}
			default: {result = false; break;}
			}
			return result;
		}
		
		public static boolean isDescriptionType(String descriptionType){
			boolean result = false;
			switch(descriptionType){
			case "DEDUCTION_IN_FULL": {result = true; break;}
			case "VAT_EXEMPTION": {result = true; break;}
			case "OUTSIDE_RB": {result = true; break;}
			case "IMPORT_VAT": {result = true; break;}
			default: {result = false; break;}
			}
			return result;
		}
	}
	
	public boolean isPositiveAmounts(){
		BooleanList list = new BooleanList();
		if(Verification.verifySection(invoice.getRoster())){
			if(Verification.verifyField(invoice.getRoster().getTotalCost())){
				list.add(!(Double.parseDouble(invoice.getRoster().getTotalCost().trim()) < 0));
			}
			if(Verification.verifyField(invoice.getRoster().getTotalExcise())){
				list.add(!(Double.parseDouble(invoice.getRoster().getTotalExcise().trim()) < 0));
			}
			if(Verification.verifyField(invoice.getRoster().getTotalVat())){
				list.add(!(Double.parseDouble(invoice.getRoster().getTotalVat().trim()) < 0));
			}
			if(Verification.verifyField(invoice.getRoster().getTotalCostVat())){
				list.add(!(Double.parseDouble(invoice.getRoster().getTotalCostVat().trim()) < 0));
			}
			if(Verification.verifyList(invoice.getRoster().getRosters())){
				for(RosterItem item: invoice.getRoster().getRosters()){
					if(Verification.verifySection(item)){
						if(Verification.verifyField(item.getPrice())){
							list.add(!(Double.parseDouble(item.getPrice().trim()) < 0));
						}
						if(Verification.verifyField(item.getCost())){
							list.add(!(Double.parseDouble(item.getCost().trim()) < 0));
						}
						if(Verification.verifyField(item.getSummaExcise())){
							list.add(!(Double.parseDouble(item.getSummaExcise().trim()) < 0));
						}
						if(Verification.verifyField(item.getCostVat())){
							list.add(!(Double.parseDouble(item.getCostVat().trim()) < 0));
						}
						if(Verification.verifySection(item.getVat())){
							if(Verification.verifyField(item.getVat().getSummaVat())){
								list.add(!(Double.parseDouble(item.getVat().getSummaVat().trim()) < 0));
							}
						}
					}
				}
			}
		}
		System.out.println(invoice.getGeneral().getNumber()+": ");
		System.out.println(list.toString());
		return list.getResult();
	}
}
