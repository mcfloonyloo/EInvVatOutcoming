package by.gomelagro.outcoming.gui.frames.invoice.test;

import java.util.List;

import by.avest.edoc.tool.ToolException;
import by.gomelagro.outcoming.gui.frames.invoice.Invoice;
import by.gomelagro.outcoming.gui.frames.invoice.data.error.ErrorBooleanItem;
import by.gomelagro.outcoming.gui.frames.invoice.data.error.ErrorBooleanList;
import by.gomelagro.outcoming.properties.ApplicationProperties;
import by.gomelagro.outcoming.xml.LoadXSD;
import by.gomelagro.outcoming.xml.XMLValidation;

public class XMLValidationTest extends AbstractTest {

	public XMLValidationTest(Invoice invoice) {
		super(invoice);
	}

	@Override
	public ErrorBooleanList getResult() {
		ErrorBooleanList list = new ErrorBooleanList();
		
		try {
			List<Exception> exceptions = XMLValidation.validateXMLByXSDAndGetErrors(getInvoice().getContent(),
					LoadXSD.loadXsdSchemaOfFile(ApplicationProperties.getInstance().getFolderXsdPath().trim(), 
							getInvoice().getGeneral().getDocumentType()));
			if(exceptions != null){
				for(int index = 0;index < exceptions.size();index++){
					list.add(new ErrorBooleanItem().setError(exceptions.get(index).getLocalizedMessage()).setFlagError(false));
				}
			}
		} catch (ToolException e) {
			list.add(new ErrorBooleanItem().setError(e.getMessage()).setFlagError(false));
		}
		
		return list;
	}

}
