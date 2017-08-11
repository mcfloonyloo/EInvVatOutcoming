package by.gomelagro.outcoming.gui.frames.invoice.data.error;

public class ErrorBooleanItem {
	private String error;
	private boolean flagError;
	
	public ErrorBooleanItem(){}
	
	public ErrorBooleanItem setError(String error){
		this.error = error;
		return this;
	}
	
	public ErrorBooleanItem setFlagError(boolean flagError){
		this.flagError = flagError;
		return this;
	}
	
	public String getError(){return this.error;}
	public boolean getFlagError(){return this.flagError;}
}
