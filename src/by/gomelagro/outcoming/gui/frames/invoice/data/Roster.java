package by.gomelagro.outcoming.gui.frames.invoice.data;

import by.gomelagro.outcoming.gui.frames.invoice.data.list.RosterList;

public class Roster implements InvoiceSectionIntf {
	private String totalCostVat;
	private String totalExcise;
	private String totalVat;
	private String totalCost;
	private RosterList roster = null;
	
	public String getTotalCostVat() {return totalCostVat;}
	public String getTotalExcise() {return totalExcise;}
	public String getTotalVat() {return totalVat;}
	public String getTotalCost() {return totalCost;}
	public RosterList getRosters() {return roster;}

	public Roster(Builder build) {
		this.totalCostVat = build.totalCostVat;
		this.totalExcise = build.totalExcise;
		this.totalVat = build.totalVat;
		this.totalCost = build.totalCost;
		this.roster = build.roster;
	}
	
	public static class Builder{
		private String totalCostVat = "";
		private String totalExcise = "";
		private String totalVat = "";
		private String totalCost = "";
		private RosterList roster = null;	
		
		public Builder(){
			roster = new RosterList();
		}
		
		public Builder setTotalCostVat(String totalCostVat) {
			this.totalCostVat = totalCostVat;
			return this;
		}
		public Builder setTotalExcise(String totalExcise) {
			this.totalExcise = totalExcise;
			return this;
		}
		public Builder setTotalVat(String totalVat) {
			this.totalVat = totalVat;
			return this;
		}
		public Builder setTotalCost(String totalCost) {
			this.totalCost = totalCost;
			return this;
		}
		public Builder setRosters(RosterList roster) {
			this.roster = roster;
			return this;
		}
		
		public Roster build(){
			return new Roster(this);
		}
	}
}
