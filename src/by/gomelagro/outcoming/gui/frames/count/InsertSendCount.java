package by.gomelagro.outcoming.gui.frames.count;

public class InsertSendCount implements ISendCount {	
	private int validCount;
	private int acceptCount;
	private int errorCount;
	private int invalidCount;
	
	public int getValidCount(){return this.validCount;}
	public int getAcceptCount(){return this.acceptCount;}
	public int getErrorCount(){return this.errorCount;}
	public int getInValidCount(){return this.invalidCount;}
	
	public void addValidCount(){this.validCount++;}
	public void addAcceptCount(){this.acceptCount++;}
	public void addErrorCount(){this.errorCount++;}
	public void addInValidCount(){this.invalidCount++;}
	
	public InsertSendCount(){
		this.validCount = 0;
		this.acceptCount = 0;
		this.errorCount = 0;
		this.invalidCount = 0;
	}
}
