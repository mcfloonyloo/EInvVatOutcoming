package by.gomelagro.outcoming.gui.frames.count;

public interface ILoadCount extends ICount {
	public void addBaseCount();
	public void addMissCount();
	public void addErrorCount();
	
	public int getBaseCount();
	public int getMissCount();
	public int getErrorCount();
}
