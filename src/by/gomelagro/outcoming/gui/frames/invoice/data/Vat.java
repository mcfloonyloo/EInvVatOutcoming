package by.gomelagro.outcoming.gui.frames.invoice.data;

public class Vat implements InvoiceSubSectionIntf {
	private String rate;
	private String rateType;
	private String summaVat;
	
	public String getRate() {return rate;}
	public String getRateType() {return rateType;}
	public String getSummaVat() {return summaVat;}
	
	public Vat(Builder build){
		this.rate = build.rate;
		this.rateType = build.rateType;
		this.summaVat = build.summaVat;
	}
	
	public static class Builder{
		private String rate = "";
		private String rateType = "";
		private String summaVat = "";
		
		public Builder(){}
		
		public Builder setRate(String rate) {
			this.rate = rate;
			return this;
		}
		public Builder setRateType(String rateType) {
			this.rateType = rateType;
			return this;
		}
		public Builder setSummaVat(String summaVat) {
			this.summaVat = summaVat;
			return this;
		}
		
		public Vat build(){
			return new Vat(this);
		}
	}
	
}
