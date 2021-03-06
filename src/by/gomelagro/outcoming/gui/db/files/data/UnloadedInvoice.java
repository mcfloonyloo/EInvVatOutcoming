package by.gomelagro.outcoming.gui.db.files.data;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import by.gomelagro.outcoming.format.date.InvoiceDateFormat;

public class UnloadedInvoice {
	private String unp;
	private Date dateTransaction;
	private String numberInvoice;
	private String statusinvoiceRu;
	private String totalCost;
	private String totalVat;
	private String totalAll;
	private Date dateDocument;
	private Color color;
	
	public String getUnp(){return this.unp;}
	public Date getDateTransaction(){return this.dateTransaction;}
	public String getNumberInvoice(){return this.numberInvoice;}
	public String getStatusInvoice(){return this.statusinvoiceRu;}
	public String getTotalCost(){return this.totalCost;}
	public String getTotalVat(){return this.totalVat;}
	public String getTotalAll(){return this.totalAll;}
	public Date getDateDocument(){return this.dateDocument;}
	public Color getColor(){return this.color;}
	
	private UnloadedInvoice(Builder builder){
		this.unp = builder.unp;
		this.dateTransaction = builder.dateTransaction;
		this.numberInvoice = builder.numberInvoice;
		this.statusinvoiceRu = builder.statusinvoiceRu;
		this.totalCost = builder.totalCost;
		this.totalVat = builder.totalVat;
		this.totalAll = builder.totalAll;
		this.dateDocument = builder.dateDocument;
		this.color = builder.color;
	}
	
	public static class Builder{
		private String unp = "";
		private Date dateTransaction = null;
		private String numberInvoice = "";
		private String statusinvoiceRu = "";
		private String totalCost = "";
		private String totalVat = "";
		private String totalAll = "";
		private Date dateDocument = null;
		private Color color = Color.BLACK;
		
		public Builder(){}
		
		public Builder setUnp(String unp){
			this.unp = unp;
			return this;
		}
		
		public Builder setDateTransaction(Date dateTransaction){
			this.dateTransaction = dateTransaction;
			return this;
		}
		
		public Builder setNumberInvoice(String numberInvoice){
			this.numberInvoice = numberInvoice;
			return this;
		}
		
		public Builder setStatusInvoiceRu(String statusInvoiceRu){
			this.statusinvoiceRu = statusInvoiceRu;
			return this;
		}
		
		public Builder setTotalCost(String totalCost){
			this.totalCost = totalCost.replace(",", ".");
			return this;
		}
		
		public Builder setTotalVat(String totalVat){
			this.totalVat = totalVat.replace(",", ".");
			return this;
		}
		
		public Builder setTotalAll(String totalAll){
			this.totalAll = totalAll.replace(",", ".");
			return this;
		}
		
		public Builder setDateDocument(Date dateDocument){
			this.dateDocument = dateDocument;
			return this;
		}
		
		public Builder setColor(Color color){
			this.color = color;
			return this;
		}
		
		public UnloadedInvoice build(){
			return new UnloadedInvoice(this);
		}
	}
	
	public Date getFormattedDateDocument(){
		Date date;
		try {
			date = new SimpleDateFormat("dd.MM.yyyy").parse(InvoiceDateFormat.dateSmallDot2String(getDateDocument()));
		} catch (ParseException e) {
			date = null;
		}
		return date;
	}
	
	public String toTrimString(){
		if(getDateDocument() == null){
			return getUnp()+";"+new SimpleDateFormat("dd.MM.yyyy").format(getDateTransaction())+";"+getNumberInvoice()+";"+getStatusInvoice()+";"+String.format("%.3f",Float.parseFloat(getTotalCost()))+";"+String.format("%.3f",Float.parseFloat(getTotalVat()))+";"+String.format("%.3f",Float.parseFloat(getTotalAll()))+";";
		}else{
			return getUnp()+";"+new SimpleDateFormat("dd.MM.yyyy").format(getDateTransaction())+";"+getNumberInvoice()+";"+getStatusInvoice()+";"+String.format("%.3f",Float.parseFloat(getTotalCost()))+";"+String.format("%.3f",Float.parseFloat(getTotalVat()))+";"+String.format("%.3f",Float.parseFloat(getTotalAll()))+";"+new SimpleDateFormat("dd.MM.yyyy").format(getFormattedDateDocument());
		}
		
	}
	
	public String toString(){
		if(getDateDocument() == null){
			return String.format("%10s", getUnp())+"; "+new SimpleDateFormat("dd.MM.yyyy").format(getDateTransaction())+";"+String.format("%26s",getNumberInvoice())+";"+String.format("%12s", getStatusInvoice())+";"+String.format("%12.3f",Float.parseFloat(getTotalCost()))+";"+String.format("%12.3f",Float.parseFloat(getTotalVat()))+";"+String.format("%12.3f",Float.parseFloat(getTotalAll()))+";";
		}else{
			return String.format("%10s", getUnp())+"; "+new SimpleDateFormat("dd.MM.yyyy").format(getDateTransaction())+";"+String.format("%26s",getNumberInvoice())+";"+String.format("%12s", getStatusInvoice())+";"+String.format("%12.3f",Float.parseFloat(getTotalCost()))+";"+String.format("%12.3f",Float.parseFloat(getTotalVat()))+";"+String.format("%12.3f",Float.parseFloat(getTotalAll()))+"; "+String.format("%14s", new SimpleDateFormat("dd.MM.yyyy").format(getFormattedDateDocument()));
		}
	}
}
