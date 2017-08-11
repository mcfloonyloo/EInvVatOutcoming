package by.gomelagro.outcoming.xml;

import java.io.File;

import by.avest.edoc.tool.ToolException;

public class LoadXSD {
	public static byte[] loadXsdSchema(String xsdFolderName, String doctype) throws ToolException {
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

		byte[] result = LoadXML.readFile(xsdFile);

		return result;
	}
	
	public static File loadXsdSchemaOfFile(String xsdFolderName, String doctype) throws ToolException {
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

		return xsdFile;
	}
}
