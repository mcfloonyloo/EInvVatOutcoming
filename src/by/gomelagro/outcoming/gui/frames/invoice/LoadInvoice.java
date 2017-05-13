package by.gomelagro.outcoming.gui.frames.invoice;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import by.avest.edoc.client.AvDocException;
import by.avest.edoc.tool.ToolException;
import by.gomelagro.outcoming.gui.frames.ConverterXml;
import by.gomelagro.outcoming.gui.frames.invoice.parse.ParseDeliveryCondition;
import by.gomelagro.outcoming.gui.frames.invoice.parse.ParseGeneral;
import by.gomelagro.outcoming.gui.frames.invoice.parse.ParseProvider;
import by.gomelagro.outcoming.gui.frames.invoice.parse.ParseRecipient;
import by.gomelagro.outcoming.gui.frames.invoice.parse.ParseRoster;
import by.gomelagro.outcoming.gui.frames.invoice.parse.ParseSenderReceiver;
import by.gomelagro.outcoming.properties.ApplicationProperties;
import by.gomelagro.outcoming.service.EVatServiceSingleton;

public class LoadInvoice {
	public static Invoice loadPortal(String number){
		Invoice invoice = null;
		try{
			invoice = new Invoice();
			invoice.setContent(EVatServiceSingleton.getInstance().getService().getEDoc(number).getDocument().getEncoded());
			Document document = ConverterXml.byteArray2Xml(EVatServiceSingleton.getInstance().getService().getEDoc(number).getDocument().getEncoded());
			
			//корневой элемент issuance
			Node root = document.getDocumentElement();
			invoice.setSender(root.getAttributes().getNamedItem("sender").getNodeValue());
			NodeList issuanceList = root.getChildNodes();
			for(int index = 0;index < issuanceList.getLength();index++){
				Node element = issuanceList.item(index);
				if(element.getNodeType() != Node.TEXT_NODE)
					switch(element.getNodeName()){
					case "general":{invoice.setGeneral(ParseGeneral.parse(element)); break;}
					case "provider":{invoice.setProvider(ParseProvider.parse(element)); break;}
					case "recipient":{invoice.setRecipient(ParseRecipient.parse(element)); break;}
					case "senderReceiver":{invoice.setSenderReceiver(ParseSenderReceiver.parse(element)); break;}
					case "deliveryCondition":{invoice.setDeliveryCondition(ParseDeliveryCondition.parse(element)); break;}
					case "roster":{invoice.setRoster(ParseRoster.parse(element)); break;}
					}
			}
			
			invoice.setXsdSchema(loadXsdSchema(ApplicationProperties.getInstance().getFolderXsdPath().trim(), invoice.getGeneral().getDocumentType()));
			
		} catch (IOException | AvDocException | ParseException | ToolException e) {
			JOptionPane.showMessageDialog(null, "Ошибка обработки ЭСЧФ"+System.lineSeparator()+e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			invoice = null;
		}
		return invoice;
	}
	
	public static Invoice loadFile(String filename){
		Invoice invoice = null;
		try {
			invoice = new Invoice();
			
			File file  = new File(filename);
			invoice.setConvertContent(readFile(file));		
			
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			//способ открытия XML с нужной кодировкой
			InputSource isrc = new InputSource("file:///"+filename);
			isrc.setEncoding("cp1251");
			Document document = documentBuilder.parse(isrc);
			
			//корневой элемент issuance
			Node root = document.getDocumentElement();
			invoice.setSender(root.getAttributes().getNamedItem("sender").getNodeValue());
			NodeList issuanceList = root.getChildNodes();
			for(int index = 0;index < issuanceList.getLength();index++){
				Node element = issuanceList.item(index);
				if(element.getNodeType() != Node.TEXT_NODE)
					switch(element.getNodeName()){
					case "general":{invoice.setGeneral(ParseGeneral.parse(element)); break;}
					case "provider":{invoice.setProvider(ParseProvider.parse(element)); break;}
					case "recipient":{invoice.setRecipient(ParseRecipient.parse(element)); break;}
					case "senderReceiver":{invoice.setSenderReceiver(ParseSenderReceiver.parse(element)); break;}
					case "deliveryCondition":{invoice.setDeliveryCondition(ParseDeliveryCondition.parse(element)); break;}
					case "roster":{invoice.setRoster(ParseRoster.parse(element)); break;}
					}
			}
			
			invoice.setXsdSchema(loadXsdSchema(ApplicationProperties.getInstance().getFolderXsdPath().trim(), invoice.getGeneral().getDocumentType()));
			
		} catch (ParserConfigurationException | SAXException | IOException | ToolException e) {
			//JOptionPane.showMessageDialog(null, "Ошибка обработки ЭСЧФ"+System.lineSeparator()+e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			invoice = null;
			
		}
		return invoice;
	}	
	
	private static byte[] readFile(File file) throws ToolException {
		byte[] fileData = new byte[(int) file.length()];
		try (DataInputStream dis = new DataInputStream(new FileInputStream(file))){
			
			dis.readFully(fileData);
		} catch (IOException e) {
				
		}
		return fileData;
	}
	
	private static byte[] loadXsdSchema(String xsdFolderName, String doctype) throws ToolException {
		File xsdFile = null;
		doctype = (doctype == null) ? "" : doctype;

		if ((doctype.equalsIgnoreCase("ORIGINAL")) || (doctype.equalsIgnoreCase("ADD_NO_REFERENCE")))
			xsdFile = new File(xsdFolderName, "MNSATI_original.xsd");
		else if (doctype.equalsIgnoreCase("FIXED"))
			xsdFile = new File(xsdFolderName, "MNSATI_fixed.xsd");
		else if (doctype.equalsIgnoreCase("ADDITIONAL"))
			xsdFile = new File(xsdFolderName, "MNSATI_additional.xsd");
		else {
			throw new ToolException(new StringBuilder().append("Неизвестный тип счета-фактуры НДС '").append(doctype)
					.append("'.").toString());
		}

		if ((!(xsdFile.exists())) && (!(xsdFile.isFile()))) {
			throw new ToolException(new StringBuilder().append("Невозможно загрузить XSD файл '")
					.append(xsdFile.getAbsolutePath()).append("'").toString());
		}

		byte[] result = readFile(xsdFile);

		return result;
	}
}
