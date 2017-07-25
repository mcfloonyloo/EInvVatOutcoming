package by.gomelagro.outcoming.gui.db;

public class DatabaseFieldConstants {	
	public static class Invoice{
		public static final int UNP 				= 8;
		public static final int CODECOUNTRY 		= 7;
		public static final int NAME 				= 10;
		public static final int NUMBERINVOICE 		= 13;
		public static final int TYPEINVOICE 		= 15;
		public static final int DATEISSUE 			= 17;
		public static final int DATETRANSACTION 	= 18;
		public static final int BYINVOICE			= 14;
		public static final int TOTALEXCISE			= 39;
		public static final int TOTALVAT			= 40;
		public static final int TOTALALL			= 41;
		public static final int TOTALCOST			= 38;
	}
	
	public static class Status{
		public static final int STATUSINVOICERU		= 16;
	}
	
	public static class Document{
		public static final int RESERVED 			= -1;
		public static final int DATEDOCUMENT		= 37;
		public static final int BLANKCODEDOCUMENT	= 34;
		public static final int SERIADOCUMENT		= 35;
		public static final int NUMBERDOCUMENT		= 36;
	}
}
