package by.gomelagro.outcoming.gui.frames.invoice.test;

import by.gomelagro.outcoming.gui.frames.invoice.data.error.ErrorItem;

public class XMLValidationCreator extends AbstractCreator {

	public XMLValidationCreator(XMLValidationTest test) {
		super(test);
	}

	@Override
	public ErrorItem getItem() {
		if(getTest().getResult().getBoolean()){
			return null;
		}else{
			return new ErrorItem().setError("Правильность структуры файла счета-фактуры: обнаружены проблемы").setMessage(getTest().getResult().getMessage()).setFlagError(false);
		}
	}

}
