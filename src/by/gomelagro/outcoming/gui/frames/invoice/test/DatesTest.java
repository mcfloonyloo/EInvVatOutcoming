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
										list.add(new ErrorBooleanItem().setError("������"+index+". ������� �������� - ���� ���������: ���� ���������� �������� �� ����� ���� ���������").setFlagError(false));
									}
								}else{
									list.add(new ErrorBooleanItem().setError("������"+index+". ������� �������� - ���� ���������: �� �������� ����������� ����").setFlagError(false));
								}
								break;
							}
							}
						}else{
							list.add(new ErrorBooleanItem().setError("������"+index+". ������� �������� - ��� ���������: �� �������� ����������� ����").setFlagError(false));
						}
					}else{
						list.add(new ErrorBooleanItem().setError("������"+index+". ������� �������� - ���������: �� �������� ����������� ������").setFlagError(false));
					}
				}
			}else{
				list.add(new ErrorBooleanItem().setError("������� �������� - ���������: �� �������� ����������� ������").setFlagError(false));
			}
		}else{
			list.add(new ErrorBooleanItem().setError("�������� ���������� - ���� ���������� ����������: �� �������� ����������� ����").setFlagError(false));
		}
			
		return list;
	}
	
	

}
