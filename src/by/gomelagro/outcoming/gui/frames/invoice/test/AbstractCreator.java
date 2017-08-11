package by.gomelagro.outcoming.gui.frames.invoice.test;

import by.gomelagro.outcoming.gui.frames.invoice.data.error.ErrorItem;

public abstract class AbstractCreator {
	private AbstractTest test;
	public AbstractTest getTest(){return this.test;}
	
	public <E extends AbstractCreator> AbstractCreator(AbstractTest test){
		this.test = test;
	}
	
	public abstract ErrorItem getItem();
}
