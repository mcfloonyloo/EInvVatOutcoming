package by.gomelagro.outcoming.gui.frames.invoice.data;

public class DeliveryCondition implements InvoiceSectionIntf {
	
	private String description;
	private Contract contract;
	
	public String getDescription() {return description;}
	public Contract getContract() {return contract;}

	public DeliveryCondition(Builder build){
		this.description = build.description;
		this.contract = build.contract;
	}
	
	public static class Builder{
		private String description = "";
		private Contract contract = null;
		
		public Builder(){}
				
		public Builder setDescription(String description) {
			this.description = description;
			return this;
		}
		public Builder setContract(Contract contract) {
			this.contract = contract;
			return this;
		}

		public DeliveryCondition build(){
			return new DeliveryCondition(this);
		}
	}
	
}
