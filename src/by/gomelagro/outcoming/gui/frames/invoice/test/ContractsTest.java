package by.gomelagro.outcoming.gui.frames.invoice.test;

import by.gomelagro.outcoming.gui.db.WorkingOutcomingTable;
import by.gomelagro.outcoming.gui.frames.invoice.Invoice;
import by.gomelagro.outcoming.gui.frames.invoice.data.error.ErrorBooleanItem;
import by.gomelagro.outcoming.gui.frames.invoice.data.error.ErrorBooleanList;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.item.DocumentItem;
import by.gomelagro.outcoming.gui.frames.invoice.verification.Validation;

public class ContractsTest extends AbstractTest {

	public ContractsTest(Invoice invoice) {
		super(invoice);
	}

	@Override
	public ErrorBooleanList getResult() {
		ErrorBooleanList list = new ErrorBooleanList();
		if(Validation.verifySection(getInvoice().getDeliveryCondition().getContract())){
			if(Validation.verifyField(getInvoice().getDeliveryCondition().getContract().getNumber())){
				list.add(new ErrorBooleanItem().setFlagError(true));
			}else{
				list.add(new ErrorBooleanItem().setError("Условия поставки - номер контракта: не пройдена верификация поля").setFlagError(false));
			}
			
			if(Validation.verifyField(getInvoice().getDeliveryCondition().getContract().getDate())){
				list.add(new ErrorBooleanItem().setFlagError(true));
			}else{
				list.add(new ErrorBooleanItem().setError("Условия поставки - дата контракта: не пройдена верификация поля").setFlagError(false));
			}
			
			if(Validation.verifyList(getInvoice().getDeliveryCondition().getContract().getDocuments())){
				int index = 0;
				for(DocumentItem item: getInvoice().getDeliveryCondition().getContract().getDocuments()){
					index++;
					if(Validation.verifySection(item)){
						if(Validation.verifySection(item.getDocType())){
							if(Validation.verifyField(item.getDocType().getCode())){
								switch(item.getDocType().getCode().trim()){
								case "602":{
									if(Validation.verifyField(item.getBlankCode())){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - код бланка документа: не пройдена верификация поля").setFlagError(false));										
									}
									
									if(Validation.verifyField(item.getDate())){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - дата документа: не пройдена верификация поля").setFlagError(false));										
									}
									
									if(Validation.verifyField(item.getSeria())){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - серия документа: не пройдена верификация поля").setFlagError(false));										
									}
									
									if(Validation.verifyField(item.getNumber())){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - номер документа: не пройдена верификация поля").setFlagError(false));										
									}									
									
									if(item.getSeria().length() == 2){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - код документа: неверная длина серии").setFlagError(false));										
									}	
									
									break;
								}
								case "603":{
									if(Validation.verifyField(item.getBlankCode())){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - код бланка документа: не пройдена верификация поля").setFlagError(false));										
									}
									
									if(Validation.verifyField(item.getDate())){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - дата документа: не пройдена верификация поля").setFlagError(false));										
									}
									
									if(Validation.verifyField(item.getSeria())){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - серия документа: не пройдена верификация поля").setFlagError(false));										
									}
									
									if(Validation.verifyField(item.getNumber())){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - номер документа: не пройдена верификация поля").setFlagError(false));										
									}									
									
									if(item.getSeria().length() == 2){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - код документа: неверная длина серии").setFlagError(false));										
									}	
									
									break;
								}
								case "604":{
									if(Validation.verifyField(item.getDate())){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - дата документа: не пройдена верификация поля").setFlagError(false));										
									}
									
									if(Validation.verifyField(item.getNumber())){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - номер документа: не пройдена верификация поля").setFlagError(false));										
									}		
									
									break;
								}
								case "605":{
									if(Validation.verifyField(item.getDate())){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - дата документа: не пройдена верификация поля").setFlagError(false));										
									}
									
									if(Validation.verifyField(item.getNumber())){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - номер документа: не пройдена верификация поля").setFlagError(false));										
									}		
									
									break;
								}
								case "606":{
									if(Validation.verifyField(item.getDate())){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - дата документа: не пройдена верификация поля").setFlagError(false));										
									}
									
									if(Validation.verifyField(item.getNumber())){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - номер документа: не пройдена верификация поля").setFlagError(false));										
									}		
									
									break;
								}
								case "609":{
									if(Validation.verifyField(item.getDate())){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - дата документа: не пройдена верификация поля").setFlagError(false));										
									}
									
									if(Validation.verifyField(item.getNumber())){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - номер документа: не пройдена верификация поля").setFlagError(false));										
									}		
									
									break;
								}
								case "611":{
									if(Validation.verifyField(item.getDate())){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - дата документа: не пройдена верификация поля").setFlagError(false));										
									}
									
									if(Validation.verifyField(item.getNumber())){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - номер документа: не пройдена верификация поля").setFlagError(false));										
									}		
									
									break;
								}
								case "612":{
									if(Validation.verifyField(item.getDate())){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - дата документа: не пройдена верификация поля").setFlagError(false));										
									}
									
									if(Validation.verifyField(item.getNumber())){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - номер документа: не пройдена верификация поля").setFlagError(false));										
									}		
									
									break;
								}
								default:{
									System.out.println("ЭСЧФ "+getInvoice().getGeneral().getNumber()+" \\ Условия поставки \\ Тип документа: "
								+WorkingOutcomingTable.Additional.getTypeDocumentName(item.getDocType().getCode()));
									
									if(Validation.verifyField(item.getDate())){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - дата документа: не пройдена верификация поля").setFlagError(false));										
									}
									
									if(Validation.verifyField(item.getNumber())){
										list.add(new ErrorBooleanItem().setFlagError(true));
									}else{
										list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - номер документа: не пройдена верификация поля").setFlagError(false));										
									}		
									
									break;
								}
								}
							}else{
								list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - код документа: не пройдена верификация поля").setFlagError(false));
							}
						}else{
							list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - тип документа: не пройдена верификация секции").setFlagError(false));
						}
					}else{
						list.add(new ErrorBooleanItem().setError("Строка "+index+". Условия поставки - документ: отсутствует строка значений").setFlagError(false));
					}
				}
			}else{
				list.add(new ErrorBooleanItem().setError("Условия поставки - документы: не пройдена верификация секции").setFlagError(false));
			}
		}else{
			list.add(new ErrorBooleanItem().setError("Условия поставки - контракт: не пройдена верификация секции").setFlagError(false));
		}
		return list;
	}

}
