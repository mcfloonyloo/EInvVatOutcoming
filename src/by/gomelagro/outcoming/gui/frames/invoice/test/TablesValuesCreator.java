package by.gomelagro.outcoming.gui.frames.invoice.test;

import by.gomelagro.outcoming.gui.frames.invoice.data.error.ErrorItem;

public class TablesValuesCreator extends AbstractCreator {

	public TablesValuesCreator(AbstractTest test) {
		super(test);
	}

	@Override
	public ErrorItem getItem() {
		if(getTest().getResult().getBoolean()){
			return null;
		}else{
			return new ErrorItem().setError("“абличные значени€: обнаружены проблемы").setMessage(getTest().getResult().getMessage()).setFlagError(false);
		}
	}

}
