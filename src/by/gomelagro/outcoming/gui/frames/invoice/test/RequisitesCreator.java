package by.gomelagro.outcoming.gui.frames.invoice.test;

import by.gomelagro.outcoming.gui.frames.invoice.data.error.ErrorItem;

public class RequisitesCreator extends AbstractCreator {

	public RequisitesCreator(AbstractTest test) {
		super(test);
	}

	@Override
	public ErrorItem getItem() {
		if(getTest().getResult().getBoolean()){
			return null;
		}else{
			return new ErrorItem().setError("Реквизиты: обнаружены проблемы").setMessage(getTest().getResult().getMessage()).setFlagError(false);
		}
	}

}
