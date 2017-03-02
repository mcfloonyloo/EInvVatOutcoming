package by.gomelagro.outcoming.gui.frames.folder.extractor;

public class NumberInvoiceExtractor {
	public static String getNumberInvoice(final String name){
		String[] arrdash = name.trim().split("\\.")[0].split("-");
		return arrdash[1].trim()+"-"+arrdash[2].trim()+"-"+arrdash[3].trim();// arrdash[1] - УНП
														// arrdash[2] - год
	}													// arrdash[3] - номер
}
