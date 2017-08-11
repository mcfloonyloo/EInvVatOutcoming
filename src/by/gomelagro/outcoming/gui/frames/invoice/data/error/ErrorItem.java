package by.gomelagro.outcoming.gui.frames.invoice.data.error;

public class ErrorItem {
	private String error;
	private String[] message;
	private boolean flagError;
	
	public ErrorItem(){}
	
	public ErrorItem setError(String error){
		this.error = error;
		return this;
	}
	
	public ErrorItem setMessage(String[] message){
		this.message = message;
		return this;
	}
	
	public ErrorItem setFlagError(boolean flagError){
		this.flagError = flagError;
		return this;
	}
	
	public String getError(){return this.error;}
	public String[] getMessage(){return this.message;}
	public boolean getFlagError(){return this.flagError;}
}
