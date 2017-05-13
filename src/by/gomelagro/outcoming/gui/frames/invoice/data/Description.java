package by.gomelagro.outcoming.gui.frames.invoice.data;

public class Description implements InvoiceSubSectionIntf {
	private String description = "";
	
	public String getDescription(){return description;}
	public void setDescription(String description){
		this.description = description;
	}
	
	public Description(String description){
		setDescription(description);
	}
}
