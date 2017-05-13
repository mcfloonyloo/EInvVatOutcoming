package by.gomelagro.outcoming.gui.frames.invoice.data.list.item;

public class SenderItem implements InvoiceSubSectionListItemIntf {
	private String countryCode;
	private String unp;
	private String name;
	private String address;
	
	public String getCountryCode(){return this.countryCode;}
	public String getUnp(){return this.unp;}
	public String getName(){return this.name;}
	public String getAddress(){return this.address;}
	
	public SenderItem(Builder build){
		this.countryCode = build.countryCode;
		this.unp = build.unp;
		this.name = build.name;
		this.address = build.address;
	}
	
	public static class Builder{
		private String countryCode = "";
		private String unp = "";
		private String name = "";
		private String address = "";
		
		public Builder(){}
		
		public Builder setCountryCode(String countryCode){
			this.countryCode = countryCode;
			return this;
		}
		
		public Builder setUnp(String unp){
			this.unp = unp;
			return this;
		}
		
		public Builder setName(String name){
			this.name = name;
			return this;
		}
		
		public Builder setAddress(String address){
			this.address = address;
			return this;
		}
		
		public SenderItem build(){
			return new SenderItem(this);
		}
	}
	
}
