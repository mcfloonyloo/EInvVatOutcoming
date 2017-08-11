package by.gomelagro.outcoming.xml;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import by.avest.edoc.tool.ToolException;

public class LoadXML {
	public static byte[] readFile(File file) throws ToolException {
		byte[] fileData = new byte[(int) file.length()];
		try (DataInputStream dis = new DataInputStream(new FileInputStream(file))){
			
			dis.readFully(fileData);
		} catch (IOException e) {
				
		}
		return fileData;
	}
}
