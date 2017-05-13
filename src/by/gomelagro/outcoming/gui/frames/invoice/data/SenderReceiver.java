package by.gomelagro.outcoming.gui.frames.invoice.data;

import by.gomelagro.outcoming.gui.frames.invoice.data.list.ConsigneeList;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.ConsignorList;

public class SenderReceiver implements InvoiceSectionIntf {
	private ConsignorList consignors;
	private ConsigneeList consignees;
	
	public ConsignorList getConsignors(){return this.consignors;}
	public ConsigneeList getConsignees(){return this.consignees;}
	
	public SenderReceiver(Builder build){
		this.consignors = build.consignors;
		this.consignees = build.consignees;
	}
	
	public static class Builder{
		public ConsignorList consignors = null;
		public ConsigneeList consignees = null;
		
		public Builder(){}
		
		public Builder setConsignors(ConsignorList consignors){
			this.consignors = consignors;
			return this;
		}
		
		public Builder setConsignees(ConsigneeList consignees){
			this.consignees = consignees;
			return this;
		}
		
		public SenderReceiver build(){
			
			return new SenderReceiver(this);
		}
	}
}
