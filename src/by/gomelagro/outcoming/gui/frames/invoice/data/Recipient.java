package by.gomelagro.outcoming.gui.frames.invoice.data;

public class Recipient implements InvoiceSectionIntf {
	private String recipientStatus;
	private String dependentPerson;
	private String residentsOfOffshore;
	private String specialDealGoods;
	private String bigCompany;
	private String countryCode;
	private String unp;
	private String branchCode;
	private String name;
	private String address;
	private String declaration;
	private Taxes taxes;
	private String dateImport;
		
	public String getRecipientStatus() {return recipientStatus;}
	public String getDependentPerson() {return dependentPerson;}
	public String getResidentsOfOffshore() {return residentsOfOffshore;}
	public String getSpecialDealGoods() {return specialDealGoods;}
	public String getBigCompany() {return bigCompany;}
	public String getCountryCode() {return countryCode;}
	public String getUnp() {return unp;}
	public String getBranchCode() {return branchCode;}
	public String getName() {return name;}
	public String getAddress() {return address;}
	public String getDeclaration() {return declaration;}
	public Taxes getTaxes() {return taxes;}
	public String getDateImport() {return dateImport;}

	public Recipient(Builder build){
		this.recipientStatus = build.recipientStatus;
		this.dependentPerson = build.dependentPerson;
		this.residentsOfOffshore = build.residentsOfOffshore;
		this.specialDealGoods = build.specialDealGoods;
		this.bigCompany = build.bigCompany;
		this.countryCode = build.countryCode;
		this.unp = build.unp;
		this.branchCode = build.branchCode;
		this.name = build.name;
		this.address = build.address;
		this.declaration = build.declaration;
		this.taxes = build.taxes;
		this.dateImport = build.dateImport;
	}
	
	public static class Builder{
		private String recipientStatus = "";
		private String dependentPerson = "";
		private String residentsOfOffshore = "";
		private String specialDealGoods = "";
		private String bigCompany = "";
		private String countryCode = "";
		private String unp = "";
		private String branchCode = "";
		private String name = "";
		private String address = "";
		private String declaration = "";
		private Taxes taxes = null;
		private String dateImport = "";
		
		public Builder(){}
		
		public Builder setRecipientStatus(String recipientStatus) {
			this.recipientStatus = recipientStatus;
			return this;
		}
		public Builder setDependentPerson(String dependentPerson) {
			this.dependentPerson = dependentPerson;
			return this;
		}
		public Builder setResidentsOfOffshore(String residentsOfOffshore) {
			this.residentsOfOffshore = residentsOfOffshore;
			return this;
		}
		public Builder setSpecialDealGoods(String specialDealGoods) {
			this.specialDealGoods = specialDealGoods;
			return this;
		}
		public Builder setBigCompany(String bigCompany) {
			this.bigCompany = bigCompany;
			return this;
		}
		public Builder setCountryCode(String countryCode) {
			this.countryCode = countryCode;
			return this;
		}
		public Builder setUnp(String unp) {
			this.unp = unp;
			return this;
		}
		public Builder setBranchCode(String branchCode) {
			this.branchCode = branchCode;
			return this;
		}
		public Builder setName(String name) {
			this.name = name;
			return this;
		}
		public Builder setAddress(String address) {
			this.address = address;
			return this;
		}
		public Builder setDeclaration(String declaration) {
			this.declaration = declaration;
			return this;
		}
		public Builder setTaxes(Taxes taxes) {
			this.taxes = taxes;
			return this;
		}
		public Builder setDateImport(String dateImport) {
			this.dateImport = dateImport;
			return this;
		}


		public Recipient build(){
			return new Recipient(this);
		}
	}
}
