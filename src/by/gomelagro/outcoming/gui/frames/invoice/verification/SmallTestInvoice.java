package by.gomelagro.outcoming.gui.frames.invoice.verification;

import by.gomelagro.outcoming.gui.frames.invoice.Invoice;
import by.gomelagro.outcoming.gui.frames.invoice.data.error.ErrorList;
import by.gomelagro.outcoming.gui.frames.invoice.test.ContractsCreator;
import by.gomelagro.outcoming.gui.frames.invoice.test.ContractsTest;
import by.gomelagro.outcoming.gui.frames.invoice.test.DatesCreator;
import by.gomelagro.outcoming.gui.frames.invoice.test.DatesTest;
import by.gomelagro.outcoming.gui.frames.invoice.test.RequisitesCreator;
import by.gomelagro.outcoming.gui.frames.invoice.test.RequisitesTest;
import by.gomelagro.outcoming.gui.frames.invoice.test.RostersCreator;
import by.gomelagro.outcoming.gui.frames.invoice.test.RostersTest;
import by.gomelagro.outcoming.gui.frames.invoice.test.TablesValuesCreator;
import by.gomelagro.outcoming.gui.frames.invoice.test.TablesValuesTest;

public class SmallTestInvoice extends AbstractTestInvoice {
	
	
	public SmallTestInvoice(Invoice invoice){
		super(invoice);
	}
	
	public SmallTestInvoice test(){
		
		ErrorList list = new ErrorList();
		list.add(new DatesCreator(new DatesTest(getInvoice())).getItem());
		list.add(new RequisitesCreator(new RequisitesTest(getInvoice())).getItem());
		list.add(new ContractsCreator(new ContractsTest(getInvoice())).getItem());
		list.add(new RostersCreator(new RostersTest(getInvoice())).getItem());
		list.add(new TablesValuesCreator(new TablesValuesTest(getInvoice())).getItem());		
		setList(list);
		
		return this;
	}
}
