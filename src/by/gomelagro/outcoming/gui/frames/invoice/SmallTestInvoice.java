package by.gomelagro.outcoming.gui.frames.invoice;

import by.gomelagro.outcoming.gui.db.WorkingOutcomingTable;
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
									break;
								}
								case "603":{
									list.add(Verification.verifyField(item.getBlankCode()));
									list.add(Verification.verifyField(item.getDate()));
									list.add(Verification.verifyField(item.getSeria()));
									list.add(Verification.verifyField(item.getNumber()));
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
								+WorkingOutcomingTable.Additional.getTypeDocumentName(item.getBlankCode()));
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
		boolean result = true;
		BooleanList list = new BooleanList();
		if(Verification.verifySection(invoice.getRoster())){
			if(Verification.verifyList(invoice.getRoster().getRosters())){
				for(RosterItem item: invoice.getRoster().getRosters()){
					if(Verification.verifySection(item)){
						if(invoice.getRecipient().getCountryCode().trim().equals("112")){
							list.add(true);
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
		result = list.getResult();
		return result;
	}
	
}
