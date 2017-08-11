package by.gomelagro.outcoming.xml;

import java.util.List;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XSDValidationErrorHandler implements ErrorHandler {

	private List<Exception> exceptions;
	
	public XSDValidationErrorHandler(List<Exception> exceptions){
		this.exceptions = exceptions;
	}
	
	@Override
	public void error(SAXParseException exception) throws SAXException {
		this.exceptions.add(exception);
	}

	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		this.exceptions.add(exception);
	}

	@Override
	public void warning(SAXParseException exception) throws SAXException {
		this.exceptions.add(exception);
	}

}
