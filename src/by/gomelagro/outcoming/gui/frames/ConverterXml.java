package by.gomelagro.outcoming.gui.frames;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import by.avest.edoc.client.AvDocException;

public class ConverterXml {
	public static Document byteArray2Xml(byte[] doc) throws AvDocException, IOException {
		Document result = null;
		try (ByteArrayInputStream bais = new ByteArrayInputStream(doc)){
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			result = dbf.newDocumentBuilder().parse(bais);
		} catch (SAXException e) {} 
		catch (ParserConfigurationException e) {} 
		return result;
	}
}
