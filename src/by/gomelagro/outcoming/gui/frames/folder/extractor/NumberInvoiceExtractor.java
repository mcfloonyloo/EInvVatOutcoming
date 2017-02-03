package by.gomelagro.outcoming.gui.frames.folder.extractor;

public class NumberInvoiceExtractor {
	public static String getNumberInvoice(final String name){
		String[] arrdash = name.trim().split("\\.")[0].split("-");
		return arrdash[1]+"-"+arrdash[2]+"-"+arrdash[3];// arrdash[1] - УНП
														// arrdash[2] - год
	}													// arrdash[3] - номер
}
