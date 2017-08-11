package by.gomelagro.outcoming.gui.frames.invoice.data.error;

public class ErrorMessage {
	public String title;
	public String[] message;
	
	public ErrorMessage(String title, String[] message){
		this.title = title;
		this.message = message;
	}

	public String getTitle(){return this.title;}
	public String[] getMessage(){return this.message;}
}
