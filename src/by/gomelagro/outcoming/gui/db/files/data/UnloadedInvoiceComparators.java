package by.gomelagro.outcoming.gui.db.files.data;

import java.util.Comparator;

public class UnloadedInvoiceComparators {
	public static Comparator<UnloadedInvoice> compareToUnp = new Comparator<UnloadedInvoice>(){
		public int compare(UnloadedInvoice invoice1, UnloadedInvoice invoice2){
			return invoice1.getUnp().compareTo(invoice2.getUnp());
		}
	};
	
	public static Comparator<UnloadedInvoice> compareToDateAsc = new Comparator<UnloadedInvoice>(){
		public int compare(UnloadedInvoice invoice1, UnloadedInvoice invoice2){
			return invoice1.getDateTransaction().compareTo(invoice2.getDateTransaction());
		}
	};
	
	public static Comparator<UnloadedInvoice> compareToDateDesc = new Comparator<UnloadedInvoice>(){
		public int compare(UnloadedInvoice invoice1, UnloadedInvoice invoice2){
			return invoice2.getDateTransaction().compareTo(invoice1.getDateTransaction());
		}
	};
	
	public static Comparator<UnloadedInvoice> compareToStatus = new Comparator<UnloadedInvoice>(){

		public int compare(UnloadedInvoice invoice1, UnloadedInvoice invoice2) {
			return invoice1.getStatusInvoice().compareTo(invoice2.getStatusInvoice());
		}
		
	};
}
