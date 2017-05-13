package by.gomelagro.outcoming.gui.frames.invoice.data.list.item;

import by.gomelagro.outcoming.gui.frames.invoice.data.DocumentType;

public class DocumentItem implements InvoiceSubSectionListItemIntf {
	private DocumentType docType;
	private String date;
	private String blankCode;
	private String seria;
	private String number;
	
	public DocumentType getDocType() {return docType;}
	public String getDate() {return date;}
	public String getBlankCode() {return blankCode;}
	public String getSeria() {return seria;}
	public String getNumber() {return number;}

	public DocumentItem(Builder build){
		this.docType = build.docType;
		this.date = build.date;
		this.blankCode = build.blankCode;
		this.seria = build.seria;
		this.number = build.number;
	}
	
	public static class Builder{
		private DocumentType docType = null;
		private String date = "";
		private String blankCode = "";
		private String seria = "";
		private String number = "";
		
		public Builder(){}
		
		public Builder setDocType(DocumentType docType) {
			this.docType = docType;
			return this;
		}
		public Builder setDate(String date) {
			this.date = date;
			return this;
		}
		public Builder setBlankCode(String blankCode) {
			this.blankCode = blankCode;
			return this;
		}
		public Builder setSeria(String seria) {
			this.seria = seria;
			return this;
		}
		public Builder setNumber(String number) {
			this.number = number;
			return this;
		}

		public DocumentItem build(){
			return new DocumentItem(this); 
		}
	}
}
