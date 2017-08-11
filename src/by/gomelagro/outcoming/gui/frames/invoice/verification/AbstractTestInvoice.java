package by.gomelagro.outcoming.gui.frames.invoice.verification;

import by.gomelagro.outcoming.gui.frames.invoice.Invoice;
import by.gomelagro.outcoming.gui.frames.invoice.data.error.ErrorList;

public abstract class AbstractTestInvoice {
	private Invoice invoice = null;
	private ErrorList list = null;
	
	protected Invoice getInvoice(){return this.invoice;}
	protected void setInvoice(Invoice invoice){this.invoice = invoice;}
	
	public ErrorList getList(){return this.list;}
	protected void setList(ErrorList list){this.list = list;}
	
	public <E extends AbstractTestInvoice> AbstractTestInvoice (Invoice invoice){
		this.invoice = invoice;
		this.list = new ErrorList();
	}
	
	abstract public <E extends AbstractTestInvoice> AbstractTestInvoice test();
	
	public String getErrorLine(){
		String line = "<html>";
		if(!list.getFlagErrorResult()){
			for(int index = 0; index < list.size(); index++){
				if(list.get(index) != null){
					line = line + "<b>" + list.get(index).getError() + "</b>";
					for(int jndex = 0; jndex < list.get(index).getMessage().length;jndex++){
						line = line + "<br>" + list.get(index).getMessage()[jndex].trim();
					}
				}
			}
		}
		line += "</html>";
		return line;
	}
}
