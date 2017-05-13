package by.gomelagro.outcoming.gui.frames.invoice.data;

public class Taxes implements InvoiceSubSectionIntf {
	private String number;
	private String date;
	
	public String getNumber(){return number;}
	public String getDate(){return date;}
	
	public void setNumber(String number){this.number = number;}
	public void setDate(String date){this.date = date;}
	
	public Taxes(String number, String date){
		setNumber(number);
		setDate(date);
	}
}
