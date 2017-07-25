package by.gomelagro.outcoming.gui.frames.folder.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileNameInvoiceValidator {
	private static Pattern pattern;
	private static Matcher matcher;
	
	private static final String FILENAME_INVOICE_PATTERN =
			"^invoice-"+
			"([0-9]{9})-"+
			"([0-9]{4})-"+
			"([0-9]{10})\\."+
			"(\\w*)$";
			

	public static boolean validate(final String name){
		pattern = Pattern.compile(FILENAME_INVOICE_PATTERN);
		matcher = pattern.matcher(name);
		return matcher.matches();
	}
}
