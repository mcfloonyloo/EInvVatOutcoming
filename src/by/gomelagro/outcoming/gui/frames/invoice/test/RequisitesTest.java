package by.gomelagro.outcoming.gui.frames.invoice.test;

import by.gomelagro.outcoming.gui.frames.invoice.Invoice;
import by.gomelagro.outcoming.gui.frames.invoice.data.error.ErrorBooleanItem;
import by.gomelagro.outcoming.gui.frames.invoice.data.error.ErrorBooleanList;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.item.SenderItem;
import by.gomelagro.outcoming.gui.frames.invoice.verification.Validation;

public class RequisitesTest extends AbstractTest {

	public RequisitesTest(Invoice invoice) {
		super(invoice);
	}

	@Override
	public ErrorBooleanList getResult() {
		ErrorBooleanList list = new ErrorBooleanList();
		//���������
		if(Validation.verifyField(getInvoice().getProvider().getUnp())){
			if(Validation.verifyField(getInvoice().getProvider().getCountryCode())){
				list.add(new ErrorBooleanItem().setFlagError(true));
			}else{
				list.add(new ErrorBooleanItem().setError("��������� - ��� ������: �� �������� ����������� ����").setFlagError(false));
			}
		}
		
		if(Validation.verifyField(getInvoice().getProvider().getName())){
			list.add(new ErrorBooleanItem().setFlagError(true));
		}else{
			list.add(new ErrorBooleanItem().setError("��������� - ������������ �����������: �� �������� ����������� ����").setFlagError(false));
		}
		
		if(Validation.verifyField(getInvoice().getProvider().getAddress())){
			list.add(new ErrorBooleanItem().setFlagError(true));
		}else{
			list.add(new ErrorBooleanItem().setError("��������� - ����� �����������: �� �������� ����������� ����").setFlagError(false));
		}
		
		
		//����������
		if(Validation.verifyField(getInvoice().getRecipient().getUnp())){
			if(Validation.verifyField(getInvoice().getRecipient().getCountryCode())){
				list.add(new ErrorBooleanItem().setFlagError(true));
			}else{
				list.add(new ErrorBooleanItem().setError("���������� - ��� ������: �� �������� ����������� ����").setFlagError(false));
			}
		}		
		
		if(Validation.verifyField(getInvoice().getRecipient().getName())){
			list.add(new ErrorBooleanItem().setFlagError(true));
		}else{
			list.add(new ErrorBooleanItem().setError("���������� - ������������ �����������: �� �������� ����������� ����").setFlagError(false));
		}
		
		if(Validation.verifyField(getInvoice().getRecipient().getAddress())){
			list.add(new ErrorBooleanItem().setFlagError(true));
		}else{
			list.add(new ErrorBooleanItem().setError("���������� - ����� �����������: �� �������� ����������� ����").setFlagError(false));
		}
		
		
		//����������������
		if(Validation.verifyList(getInvoice().getSenderReceiver().getConsignors())){
			int index = 0;
			for(SenderItem item: getInvoice().getSenderReceiver().getConsignors()){
				index++;
				if(Validation.verifySection(item)){
					if(Validation.verifyField(item.getUnp())){
						if(Validation.verifyField(item.getCountryCode())){
							list.add(new ErrorBooleanItem().setFlagError(true));
						}else{
							list.add(new ErrorBooleanItem().setError("������ "+index+". ���������������� - ��� ������: �� �������� ����������� ����").setFlagError(false));
						}
					}
					
					if(Validation.verifyField(item.getName())){
						list.add(new ErrorBooleanItem().setFlagError(true));
					}else{
						list.add(new ErrorBooleanItem().setError("������ "+index+". ���������������� - ������������ �����������: �� �������� ����������� ����").setFlagError(false));
					}
					
					if(Validation.verifyField(item.getAddress())){
						list.add(new ErrorBooleanItem().setFlagError(true));
					}else{
						list.add(new ErrorBooleanItem().setError("������ "+index+". ���������������� - ����� �����������: �� �������� ����������� ����").setFlagError(false));
					}
				}else{
					list.add(new ErrorBooleanItem().setError("������ "+index+". ����������������: �� ������� ������ ��������").setFlagError(false));
				}
			}
		}else{
			list.add(new ErrorBooleanItem().setError("����������������: �� �������� ����������� ������").setFlagError(false));
		}
		
		
		//���������������
		if(Validation.verifyList(getInvoice().getSenderReceiver().getConsignees())){
			int index = 0;
			for(SenderItem item: getInvoice().getSenderReceiver().getConsignees()){
				index++;
				if(Validation.verifySection(item)){
					if(Validation.verifyField(item.getUnp())){
						if(Validation.verifyField(item.getCountryCode())){
							list.add(new ErrorBooleanItem().setFlagError(true));
						}else{
							list.add(new ErrorBooleanItem().setError("������ "+index+". ��������������� - ��� ������: �� �������� ����������� ����").setFlagError(false));
						}
					}
					
					if(Validation.verifyField(item.getName())){
						list.add(new ErrorBooleanItem().setFlagError(true));
					}else{
						list.add(new ErrorBooleanItem().setError("������ "+index+". ��������������� - ������������ �����������: �� �������� ����������� ����").setFlagError(false));
					}
					
					if(Validation.verifyField(item.getAddress())){
						list.add(new ErrorBooleanItem().setFlagError(true));
					}else{
						list.add(new ErrorBooleanItem().setError("������ "+index+". ��������������� - ����� �����������: �� �������� ����������� ����").setFlagError(false));
					}
				}else{
					list.add(new ErrorBooleanItem().setError("������ "+index+". ���������������: �� ������� ������ ��������").setFlagError(false));
				}
			}
		}else{
			list.add(new ErrorBooleanItem().setError("���������������: �� �������� ����������� ������").setFlagError(false));
		}
		return list;
	}

}
