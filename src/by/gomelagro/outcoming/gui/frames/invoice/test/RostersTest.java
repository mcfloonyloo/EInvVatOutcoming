package by.gomelagro.outcoming.gui.frames.invoice.test;

import by.gomelagro.outcoming.gui.frames.invoice.Invoice;
import by.gomelagro.outcoming.gui.frames.invoice.data.error.ErrorBooleanItem;
import by.gomelagro.outcoming.gui.frames.invoice.data.error.ErrorBooleanList;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.item.RosterItem;
import by.gomelagro.outcoming.gui.frames.invoice.verification.Validation;

public class RostersTest extends AbstractTest {

	public RostersTest(Invoice invoice) {
		super(invoice);
	}

	@Override
	public ErrorBooleanList getResult() {
		ErrorBooleanList list = new ErrorBooleanList();
		if(Validation.verifySection(getInvoice().getRoster())){
			if(Validation.verifyList(getInvoice().getRoster().getRosters())){
				int index = 0;
				for(RosterItem item: getInvoice().getRoster().getRosters()){
					index++;
					if(Validation.verifySection(item)){
						if(getInvoice().getRecipient().getCountryCode().trim().equals("112")){
							if(Validation.verifyField(item.getUnits())){
								list.add(new ErrorBooleanItem().setFlagError(true));
							}else{
								list.add(new ErrorBooleanItem().setError("������ "+index+". ").setFlagError(false));
							}
						}else{
							if(Validation.verifySection(getInvoice().getDeliveryCondition().getContract().getDocuments().get(0).getDocType())){
								if(Validation.verifyField(getInvoice().getDeliveryCondition().getContract().getDocuments().get(0).getDocType().getCode())){
									switch(getInvoice().getDeliveryCondition().getContract().getDocuments().get(0).getDocType().getCode().trim()){
									case "602":{
										if(Validation.verifyField(item.getCode())){
											list.add(new ErrorBooleanItem().setFlagError(true));
										}else{
											list.add(new ErrorBooleanItem().setError("������ "+index+". ������ �� ������� - ��� ������ (�� ���): �� �������� ����������� ����").setFlagError(false));
										}
										break;
									}
									case "603":{
										if(Validation.verifyField(item.getCode())){
											list.add(new ErrorBooleanItem().setFlagError(true));
										}else{
											list.add(new ErrorBooleanItem().setError("������ "+index+". ������ �� ������� - ��� ������ (�� ���): �� �������� ����������� ����").setFlagError(false));
										}
										break;
									}
									case "606":{
										if(Validation.verifyField(item.getCodeOced())){
											list.add(new ErrorBooleanItem().setFlagError(true));
										}else{
											list.add(new ErrorBooleanItem().setError("������ "+index+". ������ �� ������� - ��� ����� ������������ (����): �� �������� ����������� ����").setFlagError(false));
										}
										break;
									}
									case "607":{
										if(Validation.verifyField(item.getCode())){
											list.add(new ErrorBooleanItem().setFlagError(true));
										}else{
											list.add(new ErrorBooleanItem().setError("������ "+index+". ������ �� ������� - ��� ������ (�� ���): �� �������� ����������� ����").setFlagError(false));
										}
										break;
									}
									case "612":{
										if(Validation.verifyField(item.getCodeOced())){
											list.add(new ErrorBooleanItem().setFlagError(true));
										}else{
											list.add(new ErrorBooleanItem().setError("������ "+index+". ������ �� ������� - ��� ����� ������������ (����): �� �������� ����������� ����").setFlagError(false));
										}
										break;
									}
									default:{
										if((Validation.verifyField(item.getCode().trim()))||(Validation.verifyField(item.getCodeOced().trim()))){
											list.add(new ErrorBooleanItem().setFlagError(true));
										}else{
											list.add(new ErrorBooleanItem().setError("������ "+index+". ������ �� ������� - ��� ����� ������������ (����) ��� ��� ������ (�� ���): �� �������� ����������� ����").setFlagError(false));
										}
									}									
									}
								}else{
									list.add(new ErrorBooleanItem().setError("������ "+index+". ������� �������� - ��� ���������: �� �������� ����������� ������").setFlagError(false));
								}
							}else{
								list.add(new ErrorBooleanItem().setError("������ "+index+". ������� �������� - ��������: �� �������� ����������� ������").setFlagError(false));
							}
						}
					}else{
						list.add(new ErrorBooleanItem().setError("������ "+index+". ������ �� ������: ����������� ������ ��������").setFlagError(false));
					}
				}
			}
		}else{
			list.add(new ErrorBooleanItem().setError("������ �� �������: �� �������� ����������� ������").setFlagError(false));
		}
		return list;
	}

}
