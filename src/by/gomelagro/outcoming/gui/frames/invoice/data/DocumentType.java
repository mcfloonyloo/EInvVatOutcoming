package by.gomelagro.outcoming.gui.frames.invoice.data;

public class DocumentType implements InvoiceSubSectionIntf {
	private String code = "";
	private String value = "";
	
	public String getCode(){return code;}
	public void setCode(String code){this.code = code;}
	
	public String getValue(){return value;}
	public void setValue(String value){this.value = value;}
	
	public DocumentType(String code, String value){
		this.code = code;
		this.value = value;
	}
}
