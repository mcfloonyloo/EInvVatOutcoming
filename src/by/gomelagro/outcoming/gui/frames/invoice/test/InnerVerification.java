package by.gomelagro.outcoming.gui.frames.invoice.test;

public class InnerVerification {
	public static boolean isRateType(String rateType){
		boolean result = false;
		switch(rateType){
		case "DECIMAL": {result = true; break;}
		case "ZERO": {result = true; break;}
		case "NO_VAT": {result = true; break;}
		case "CALCULATED": {result = true; break;}
		default: {result = false; break;}
		}
		return result;
	}
	
	public static boolean isInvoiceDocType(String invoiceDocType){
		boolean result = false;
		switch(invoiceDocType){
		case "ORIGINAL": {result = true; break;}
		case "ADDITIONAL": {result = true; break;}
		case "FIXED": {result = true; break;}
		case "ADD_NO_REFERENCE": {result = true; break;}
		default: {result = false; break;}
		}		
		return result;
	}
	
	public static boolean isProviderStatusType(String providerStatusType){
		boolean result = false;
		switch(providerStatusType){
		case "SELLER": {result = true; break;}
		case "CONSIGNOR": {result = true; break;}
		case "COMMISSIONAIRE": {result = true; break;}
		case "TAX_DEDUCTION_PAYER": {result = true; break;}
		case "TRUSTEE": {result = true; break;}
		case "FOREIGN_ORGANIZATION": {result = true; break;}
		case "AGENT": {result = true; break;}
		case "DEVELOPER": {result = true; break;}
		case "TURNOVERS_ON_SALE_PAYER": {result = true; break;}
		default: {result = false; break;}
		}
		return result;
	}
	
	public static boolean isRecipientStatusType(String recipientStatusType){
		boolean result = false;
		switch(recipientStatusType){
		case "CUSTOMER": {result = true; break;}
		case "CONSUMER": {result = true; break;}
		case "CONSIGNOR": {result = true; break;}
		case "COMMISSIONAIRE": {result = true; break;}
		case "TAX_DEDUCTION_PAYER": {result = true; break;}
		case "FOREIGN_ORGANIZATION": {result = true; break;}
		case "TURNOVERS_ON_SALE_PAYER": {result = true; break;}
		default: {result = false; break;}
		}
		return result;
	}
	
	public static boolean isDescriptionType(String descriptionType){
		boolean result = false;
		switch(descriptionType){
		case "DEDUCTION_IN_FULL": {result = true; break;}
		case "VAT_EXEMPTION": {result = true; break;}
		case "OUTSIDE_RB": {result = true; break;}
		case "IMPORT_VAT": {result = true; break;}
		default: {result = false; break;}
		}
		return result;
	}
}
