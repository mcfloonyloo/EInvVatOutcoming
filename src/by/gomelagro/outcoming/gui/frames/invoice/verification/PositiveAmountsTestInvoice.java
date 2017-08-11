package by.gomelagro.outcoming.gui.frames.invoice.verification;

import by.gomelagro.outcoming.gui.frames.invoice.Invoice;
import by.gomelagro.outcoming.gui.frames.invoice.data.error.ErrorList;
import by.gomelagro.outcoming.gui.frames.invoice.test.PositiveAmountsCreator;
import by.gomelagro.outcoming.gui.frames.invoice.test.PositiveAmountsTest;

public class PositiveAmountsTestInvoice extends AbstractTestInvoice{
	
	public PositiveAmountsTestInvoice(Invoice invoice){
		super(invoice);
	}
	
	public PositiveAmountsTestInvoice test(){
		
		ErrorList list = new ErrorList();
		list.add(new PositiveAmountsCreator(new PositiveAmountsTest(getInvoice())).getItem());		
		setList(list);
		
		return this;
	}
}
