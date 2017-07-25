package by.gomelagro.outcoming.gui.frames.count;

public class UpdateCount implements ILoadCount {
	private int baseCount;
	private int missCount;
	private int errorCount;
	
	public int getBaseCount(){return this.baseCount;}
	public int getMissCount(){return this.missCount;}
	public int getErrorCount(){return this.errorCount;}
	
	public void addBaseCount(){this.baseCount++;}
	public void addMissCount(){this.missCount++;}
	public void addErrorCount(){this.errorCount++;}
	
	public UpdateCount(){
		this.baseCount = 0;
		this.missCount = 0;
		this.errorCount = 0;
	}
}
