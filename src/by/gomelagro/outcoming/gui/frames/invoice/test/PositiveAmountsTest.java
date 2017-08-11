package by.gomelagro.outcoming.gui.frames.invoice.test;

import by.gomelagro.outcoming.gui.frames.invoice.Invoice;
import by.gomelagro.outcoming.gui.frames.invoice.data.error.ErrorBooleanItem;
import by.gomelagro.outcoming.gui.frames.invoice.data.error.ErrorBooleanList;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.item.RosterItem;
import by.gomelagro.outcoming.gui.frames.invoice.verification.Validation;

public class PositiveAmountsTest extends AbstractTest {

	public PositiveAmountsTest(Invoice invoice) {
		super(invoice);
	}

	@Override
	public ErrorBooleanList getResult() {
		ErrorBooleanList list = new ErrorBooleanList();
		if(Validation.verifySection(getInvoice().getRoster())){
			if(Validation.verifyField(getInvoice().getRoster().getTotalCost())){
				if(Double.parseDouble(getInvoice().getRoster().getTotalCost().trim()) >= 0){
					list.add(new ErrorBooleanItem().setFlagError(true));
				}else{
					list.add(new ErrorBooleanItem().setError("������ �� ������� - ����� ��� ���: ������������� ��������").setFlagError(false));
				}
			}
			if(Validation.verifyField(getInvoice().getRoster().getTotalExcise())){
				if(Double.parseDouble(getInvoice().getRoster().getTotalExcise().trim()) >= 0){
					list.add(new ErrorBooleanItem().setFlagError(true));
				}else{
					list.add(new ErrorBooleanItem().setError("������ �� ������� - ����� �������: ������������� ��������").setFlagError(false));
				}
			}
			if(Validation.verifyField(getInvoice().getRoster().getTotalVat())){
				if(Double.parseDouble(getInvoice().getRoster().getTotalVat().trim()) >= 0){
					list.add(new ErrorBooleanItem().setFlagError(true));
				}else{
					list.add(new ErrorBooleanItem().setError("������ �� ������� - ����� ���: ������������� ��������").setFlagError(false));
				}
			}
			if(Validation.verifyField(getInvoice().getRoster().getTotalCostVat())){
				if(Double.parseDouble(getInvoice().getRoster().getTotalCostVat().trim()) >= 0){
					list.add(new ErrorBooleanItem().setFlagError(true));
				}else{
					list.add(new ErrorBooleanItem().setError("������ �� ������� - ����� � ���: ������������� ��������").setFlagError(false));
				}
			}
			if(Validation.verifyList(getInvoice().getRoster().getRosters())){
				int index = 0;
				for(RosterItem item: getInvoice().getRoster().getRosters()){
					index++;
					if(Validation.verifySection(item)){
						if(Validation.verifyField(item.getPrice())){
							if(Double.parseDouble(item.getPrice().trim()) >= 0){
								list.add(new ErrorBooleanItem().setFlagError(true));
							}else{
								list.add(new ErrorBooleanItem().setError("������ "+index+". ������ �� ������ - ���� �� �������: ������������� ��������").setFlagError(false));
							}
						}
						if(Validation.verifyField(item.getCost())){
							if(Double.parseDouble(item.getCost().trim()) >= 0){
								list.add(new ErrorBooleanItem().setFlagError(true));
							}else{
								list.add(new ErrorBooleanItem().setError("������ "+index+". ������ �� ������ - ��������� �������: ������������� ��������").setFlagError(false));
							}
						}
						if(Validation.verifyField(item.getSummaExcise())){
							if(Double.parseDouble(item.getSummaExcise().trim()) >= 0){
								list.add(new ErrorBooleanItem().setFlagError(true));
							}else{
								list.add(new ErrorBooleanItem().setError("������ "+index+". ������ �� ������ - ����� ������: ������������� ��������").setFlagError(false));
							}
						}
						if(Validation.verifyField(item.getCostVat())){
							if(Double.parseDouble(item.getCostVat().trim()) >= 0){
								list.add(new ErrorBooleanItem().setFlagError(true));
							}else{
								list.add(new ErrorBooleanItem().setError("������ "+index+". ������ �� ������ - ��������� ������ � ���: ������������� ��������").setFlagError(false));
							}
						}
						if(Validation.verifySection(item.getVat())){
							if(Validation.verifyField(item.getVat().getSummaVat())){
								if(Double.parseDouble(item.getVat().getSummaVat().trim()) >= 0){
									list.add(new ErrorBooleanItem().setFlagError(true));
								}else{
									list.add(new ErrorBooleanItem().setError("������ "+index+". ������ �� ������ - ����� ���: ������������� ��������").setFlagError(false));
								}
							}
						}
					}
				}
			}
		}
		//System.out.println(invoice.getGeneral().getNumber()+": ");
		//System.out.println(list.toString());
		return list;
	}

}
