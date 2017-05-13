package by.gomelagro.outcoming.gui.frames.invoice.data;

public class General implements InvoiceSectionIntf {
	private String number;
	private String dateIssuance;
	private String dateTransaction;
	private String documentType;
	private String invoice;
	private String sendToRecipient;
	private String dateCancelled;
	
	public String getNumber(){return this.number;}
	public String getDateIssuance(){return this.dateIssuance;}
	public String getDateTransaction(){return this.dateTransaction;}
	public String getDocumentType(){return this.documentType;}
	public String getInvoice(){return this.invoice;}
	public String getSendToRecipient(){return this.sendToRecipient;}
	public String getDateCancelled(){return this.dateCancelled;}
	
	public General(Builder build){
		this.number = build.number;
		this.dateIssuance = build.dateIssuance;
		this.dateTransaction = build.dateTransaction;
		this.documentType = build.documentType;
		this.invoice = build.invoice;
		this.sendToRecipient = build.sendToRecipient;
		this.dateCancelled = build.dateCancelled;
	}
	
	public static class Builder{
		private String number = "";
		private String dateIssuance = "";
		private String dateTransaction = "";
		private String documentType = "";
		private String invoice = "";
		private String sendToRecipient = "";
		private String dateCancelled = "";
		
		public Builder(){}

		public Builder setNumber(String number){
			this.number = number;
			return this;
		}
		
		public Builder setDateIssuance(String dateIssuance){
			this.dateIssuance = dateIssuance;
			return this;
		}
		
		public Builder setDateTransaction(String dateTransaction){
			this.dateTransaction = dateTransaction;
			return this;
		}
		
		public Builder setDocumentType(String documentType){
			this.documentType = documentType;
			return this;
		}
		
		public Builder setInvoice(String invoice){
			this.invoice = invoice;
			return this;
		}
		
		public Builder setSendToRecipient(String sendToRecipient){
			this.sendToRecipient = sendToRecipient;
			return this;
		}
		
		public Builder setDateCancelled(String dateCancelled){
			this.dateCancelled = dateCancelled;
			return this;
		}
		
		public General build(){
			return new General(this);
		}
		
	}
	
}
