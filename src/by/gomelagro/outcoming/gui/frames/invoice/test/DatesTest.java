package by.gomelagro.outcoming.gui.frames.invoice.test;

import by.gomelagro.outcoming.gui.frames.invoice.Invoice;
import by.gomelagro.outcoming.gui.frames.invoice.data.error.ErrorBooleanItem;
import by.gomelagro.outcoming.gui.frames.invoice.data.error.ErrorBooleanList;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.item.DocumentItem;
import by.gomelagro.outcoming.gui.frames.invoice.verification.Validation;

public class DatesTest extends AbstractTest{

	public DatesTest(Invoice invoice) {
		super(invoice);
	}

	@Override
	public ErrorBooleanList getResult() {
		ErrorBooleanList list = new ErrorBooleanList();
		
		if(Validation.verifyField(getInvoice().getGeneral().getDateTransaction())){
			if(Validation.verifyList(getInvoice().getDeliveryCondition().getContract().getDocuments())){
				int index = 0;
				for(DocumentItem item: getInvoice().getDeliveryCondition().getContract().getDocuments()){
					index++;
					if(Validation.verifySection(item.getDocType())){
						if(Validation.verifyField(item.getDocType().getCode())){
							switch(item.getDocType().getCode().trim()){
							case "609":{
								list.add(new ErrorBooleanItem().setFlagError(true));
								break;
							}
							case "612":{
								list.add(new ErrorBooleanItem().setFlagError(true));
								break;
							}
							default:{
								if(Validation.verifyField(item.getDate())){
									if(item.getDate().trim().equals(getInvoice().getGeneral().getDateTransaction().trim())){
										list.add(new ErrorBooleanItem().setFlagError(true));						
									}else{
										list.add(new ErrorBooleanItem().setError("Строка"+index+". Условия поставки - дата документа: дата совершения операции не равна дате документа").setFlagError(false));
									}
								}else{
									list.add(new ErrorBooleanItem().setError("Строка"+index+". Условия поставки - дата документа: не пройдена верификация поля").setFlagError(false));
								}
								break;
							}
							}
						}else{
							list.add(new ErrorBooleanItem().setError("Строка"+index+". Условия поставки - код документа: не пройдена верификация поля").setFlagError(false));
						}
					}else{
						list.add(new ErrorBooleanItem().setError("Строка"+index+". Условия поставки - документа: не пройдена верификация секции").setFlagError(false));
					}
				}
			}else{
				list.add(new ErrorBooleanItem().setError("Условия поставки - документы: не пройдена верификация секции").setFlagError(false));
			}
		}else{
			list.add(new ErrorBooleanItem().setError("Основная информация - дата совершения транзакции: не пройдена верификация поля").setFlagError(false));
		}
			
		return list;
	}
	
	

}
