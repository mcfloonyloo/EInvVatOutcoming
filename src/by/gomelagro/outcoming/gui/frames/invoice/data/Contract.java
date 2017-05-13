package by.gomelagro.outcoming.gui.frames.invoice.data;

import by.gomelagro.outcoming.gui.frames.invoice.data.list.DocumentList;

public class Contract implements InvoiceSubSectionIntf {
	private String number;
	private String date;
	private DocumentList documents;
	
	public String getNumber() {return number;}
	public String getDate() {return date;}
	public DocumentList getDocuments() {return documents;}

	public Contract(Builder build){
		this.number = build.number;
		this.date = build.date;
		this.documents = build.documents;
	}
	
	public static class Builder{
		private String number = "";
		private String date = "";
		private DocumentList documents = null;
		
		public Builder(){}
				
		public Builder setNumber(String number) {
			this.number = number;
			return this;
		}
		public Builder setDate(String date) {
			this.date = date;
			return this;
		}
		public Builder setDocuments(DocumentList documents) {
			this.documents = documents;
			return this;
		}
		
		public Contract build(){
			return new Contract(this);
		}
		
	}
	
}
