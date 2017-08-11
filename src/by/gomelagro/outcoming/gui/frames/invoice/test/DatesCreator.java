package by.gomelagro.outcoming.gui.frames.invoice.test;

import by.gomelagro.outcoming.gui.frames.invoice.data.error.ErrorItem;

public class DatesCreator extends AbstractCreator {

	public DatesCreator(AbstractTest test) {
		super(test);
	}

	@Override
	public ErrorItem getItem() {
		if(getTest().getResult().getBoolean()){
			return null;
		}else{
			return new ErrorItem().setError("ƒата совершени€ операции: обнаружены проблемы").setMessage(getTest().getResult().getMessage()).setFlagError(false);
		}		
	}

}
