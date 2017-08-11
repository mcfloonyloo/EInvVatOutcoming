package by.gomelagro.outcoming.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class XMLValidation {
	public static boolean validateXMLByXSD(String xml, String xsdPath){
		try{
			SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema")
			.newSchema(new File(xsdPath))
			.newValidator()
			.validate(new StreamSource(new StringReader(xml)));
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	public static boolean validateXMLByXSD(String xml, File xsd){
		try{
			SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema")
			.newSchema(xsd)
			.newValidator()
			.validate(new StreamSource(new StringReader(xml)));
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	public static boolean validateXMLByXSD(File xml, File xsd){
		try{
			SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema")
			.newSchema(xsd)
			.newValidator()
			.validate(new StreamSource(xml));
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	public static List<Exception> validateXMLByXSDAndGetErrors(String xml, String xsdPath){
		List<Exception> exceptions = new ArrayList<Exception>();
		try{
			Validator validator = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema")
					.newSchema(new File(xsdPath))
					.newValidator();
			validator.setErrorHandler(new XSDValidationErrorHandler(exceptions));
			validator.validate(new StreamSource(new StringReader(xml)));
					
		}catch(Exception e){
			return null;
		}
		return exceptions;
	}
	
	public static List<Exception> validateXMLByXSDAndGetErrors(String xml, File xsd){
		List<Exception> exceptions = new ArrayList<Exception>();
		try{
			Validator validator = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema")
					.newSchema(xsd)
					.newValidator();
			validator.setErrorHandler(new XSDValidationErrorHandler(exceptions));
			validator.validate(new StreamSource(new StringReader(xml)));
		}catch(Exception e){
			return null;
		}
		return exceptions;
	}
	
	public static List<Exception> validateXMLByXSDAndGetErrors(File xml, File xsd){
		List<Exception> exceptions = new ArrayList<Exception>();
		try{
			Validator validator = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema")
					.newSchema(xsd)
					.newValidator();
			validator.setErrorHandler(new XSDValidationErrorHandler(exceptions));
			validator.validate(new StreamSource(xml));
					
		}catch(Exception e){
			return null;
		}
		return exceptions;
	}	
	
	public static List<Exception> validateXMLByXSDAndGetErrors(byte[] xml, File xsd){
		List<Exception> exceptions = new ArrayList<Exception>();
		try{
			Validator validator = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema")
					.newSchema(xsd)
					.newValidator();
			validator.setErrorHandler(new XSDValidationErrorHandler(exceptions));
			validator.validate(new StreamSource(new ByteArrayInputStream(xml)));
					
		}catch(Exception e){
			return null;
		}
		return exceptions;
	}	
}
