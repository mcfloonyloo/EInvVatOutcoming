package by.gomelagro.outcoming.gui.frames.count;

public interface ISendCount extends ICount {
	public void addValidCount();
	public void addAcceptCount();
	public void addErrorCount();
	public void addInValidCount();
	
	public int getValidCount();
	public int getAcceptCount();
	public int getErrorCount();
	public int getInValidCount();
}
