package by.gomelagro.outcoming.gui.frames.invoice.data.list.item;

import by.gomelagro.outcoming.gui.frames.invoice.data.Vat;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.DescriptionList;

public class RosterItem implements InvoiceSubSectionListItemIntf {
	
	private String number;
	private String name;
	private String code;
	private String codeOced;
	private String units;
	private String count;
	private String price;
	private String cost;
	private String summaExcise;
	private Vat vat;
	private String costVat;
	private DescriptionList descriptions;
	private String skipDeduction;
		
	public String getNumber() {return number;}
	public String getName() {return name;}
	public String getCode() {return code;}
	public String getCodeOced() {return codeOced;}
	public String getUnits() {return units;}
	public String getCount() {return count;}
	public String getPrice() {return price;}
	public String getCost() {return cost;}
	public String getSummaExcise() {return summaExcise;}
	public Vat getVat() {return vat;}
	public String getCostVat() {return costVat;}
	public DescriptionList getDescriptions(){return descriptions;}
	public String getSkipDeduction(){return skipDeduction;}

	public RosterItem(Builder build){
		this.number = build.number;
		this.name = build.name;
		this.code = build.code;
		this.codeOced = build.codeOced;
		this.units = build.units;
		this.count = build.count;
		this.price = build.price;
		this.cost = build.cost;
		this.summaExcise = build.summaExcise;
		this.vat = build.vat;
		this.costVat = build.costVat;
		this.descriptions = build.descriptions;
		this.skipDeduction = build.skipDeduction;
	}
	
	public static class Builder{
		private String number = "";
		private String name = "";
		private String code = "";
		private String codeOced = "";
		private String units = "";
		private String count = "";
		private String price = "";
		private String cost = "";
		private String summaExcise = "";
		private Vat vat = null;
		private String costVat = "";
		private DescriptionList descriptions = null;
		private String skipDeduction = "";
		
		public Builder(){
			descriptions = new DescriptionList();
		}
		
		public Builder setNumber(String number) {
			this.number = number;
			return this;
		}
		public Builder setName(String name) {
			this.name = name;	
			return this;
		}
		public Builder setCode(String code) {
			this.code = code;
			return this;
		}
		public Builder setCodeOced(String codeOced) {
			this.codeOced = codeOced;
			return this;
		}
		public Builder setUnits(String units) {
			this.units = units;
			return this;
		}
		public Builder setCount(String count) {
			this.count = count;
			return this;
		}
		public Builder setPrice(String price) {
			this.price = price;
			return this;
		}
		public Builder setCost(String cost) {
			this.cost = cost;
			return this;
		}
		public Builder setSummaExcise(String summaExcise) {
			this.summaExcise = summaExcise;
			return this;
		}
		public Builder setVat(Vat vat) {
			this.vat = vat;
			return this;
		}
		public Builder setCostVat(String costVat) {
			this.costVat = costVat;
			return this;
		}
		public Builder setDescriptions(DescriptionList descriptions) {
			this.descriptions = descriptions;
			return this;
		}
			public Builder setSkipDeduction(String skipDeduction) {
			this.skipDeduction = skipDeduction;
			return this;
		}


		public RosterItem build(){
			return new RosterItem(this);
		}
		
	}
	
}
