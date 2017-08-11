package by.gomelagro.outcoming.gui.frames.invoice.test;

import by.gomelagro.outcoming.gui.frames.invoice.Invoice;
import by.gomelagro.outcoming.gui.frames.invoice.data.error.ErrorBooleanList;

public abstract class AbstractTest {
	private Invoice invoice;
	public Invoice getInvoice(){return this.invoice;}
	
	public AbstractTest(Invoice invoice){
		this.invoice = invoice;
	}
	
	public abstract ErrorBooleanList getResult();
}
