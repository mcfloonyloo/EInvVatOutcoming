package by.gomelagro.outcoming.gui.frames.invoice.verification;

import by.gomelagro.outcoming.gui.frames.invoice.Invoice;
import by.gomelagro.outcoming.gui.frames.invoice.data.error.ErrorList;
import by.gomelagro.outcoming.gui.frames.invoice.test.XMLValidationCreator;
import by.gomelagro.outcoming.gui.frames.invoice.test.XMLValidationTest;

public class XMLValidationTestInvoice extends AbstractTestInvoice {
	
	public XMLValidationTestInvoice(Invoice invoice) {
		super(invoice);
	}

	@Override
	public <E extends AbstractTestInvoice> XMLValidationTestInvoice test() {
		ErrorList list = new ErrorList();
		list.add(new XMLValidationCreator(new XMLValidationTest(getInvoice())).getItem());
		setList(list);
		return this;
	}

}
