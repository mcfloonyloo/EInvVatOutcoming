package by.gomelagro.outcoming.gui.db.number;

public class NumberInvoice {
	private String id;
	private String number;
	
	public String getId(){return this.id;}
	public String getNumber(){return this.number;}
	
	public void setId(String id){this.id = id;}
	public void setNumber(String number){this.number = number;}
	
	public NumberInvoice addId(String id){this.id = id;return this;}
	public NumberInvoice addNumber(String number){this.number = number;return this;}
	
	public NumberInvoice(){
		id = "";
		number = "";
	}
	
}
