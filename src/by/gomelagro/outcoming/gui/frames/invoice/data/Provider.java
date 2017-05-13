package by.gomelagro.outcoming.gui.frames.invoice.data;

public class Provider implements InvoiceSectionIntf{
	private String providerStatus;
	private String dependentPerson;
	private String residentsOfOffshore;
	private String specialDealGoods;
	private String bigCompany;
	private String countryCode;
	private String unp;
	private String branchCode;
	private String name;
	private String address;
	private ForInvoice principal;
	private ForInvoice vendor;
	private String declaration;
	private String dateRelease;
	private String dateActualExport;
	private Taxes taxes;
	
	public String getProviderStatus() {return providerStatus;}
	public String getDependentPerson() {return dependentPerson;}
	public String getResidentsOfOffshore() {return residentsOfOffshore;}
	public String getSpecialDealGoods() {return specialDealGoods;}
	public String getBigCompany() {return bigCompany;}
	public String getCountryCode() {return countryCode;}
	public String getUnp() {return unp;}
	public String getBranchCode(){return branchCode;}
	public String getName() {return name;}
	public String getAddress() {return address;}
	public ForInvoice getPrincipal() {return principal;}
	public ForInvoice getVendor() {return vendor;}
	public String getDeclaration() {return declaration;}
	public String getDateRelease() {return dateRelease;}
	public String getDateActualExport() {return dateActualExport;}
	public Taxes getTaxes() {return taxes;}
	
	public Provider(Builder build){
		this.providerStatus = build.providerStatus;
		this.dependentPerson = build.dependentPerson;
		this.residentsOfOffshore = build.residentsOfOffshore;
		this.specialDealGoods = build.specialDealGoods;
		this.bigCompany = build.bigCompany;
		this.countryCode = build.countryCode;
		this.unp = build.unp;
		this.branchCode = build.branchCode;
		this.name = build.name;
		this.address = build.address;
		this.principal = build.principal;
		this.vendor = build.vendor;
		this.declaration = build.declaration;
		this.dateRelease = build.dateRelease;
		this.dateActualExport = build.dateActualExport;
		this.taxes = build.taxes;
	}
	
	public static class Builder{
		private String providerStatus = "";
		private String dependentPerson = "";
		private String residentsOfOffshore = "";
		private String specialDealGoods = "";
		private String bigCompany = "";
		private String countryCode = "";
		private String unp = "";
		private String branchCode = "";
		private String name = "";
		private String address = "";
		private ForInvoice principal = null;
		private ForInvoice vendor = null;
		private String declaration = "";
		private String dateRelease = "";
		private String dateActualExport = "";
		private Taxes taxes = null;
		
		public Builder(){}
		
		public Builder setProviderStatus(String providerStatus) {
			this.providerStatus = providerStatus;
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
		public Builder setBranchCode(String branchCode){
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
		
		public Builder setPrincipal(ForInvoice principal) {
			this.principal = principal;
			return this;
		}

		public Builder setVendor(ForInvoice vendor) {
			this.vendor = vendor;
			return this;
		}

		public Builder setDeclaration(String declaration) {
			this.declaration = declaration;
			return this;
		}

		public Builder setDateRelease(String dateRelease) {
			this.dateRelease = dateRelease;
			return this;
		}

		public Builder setDateActualExport(String dateActualExport) {
			this.dateActualExport = dateActualExport;
			return this;
		}

		public Builder setTaxes(Taxes taxes) {
			this.taxes = taxes;
			return this;
		}

		public Provider build(){
			return new Provider(this);
		}
		
	}
}
