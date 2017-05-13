package by.gomelagro.outcoming.gui.db;

import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JOptionPane;

import by.gomelagro.outcoming.format.date.InvoiceDateFormat;
import by.gomelagro.outcoming.gui.db.files.data.UnloadedInvoice;
import by.gomelagro.outcoming.gui.db.number.NumberInvoice;
import by.gomelagro.outcoming.gui.frames.invoice.Invoice;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.BooleanList;
import by.gomelagro.outcoming.gui.frames.invoice.verification.Verification;
import by.gomelagro.outcoming.gui.frames.list.MonthYearItem;
import by.gomelagro.outcoming.gui.frames.report.component.ResultFont;
import by.gomelagro.outcoming.status.Status;

public class WorkingOutcomingTable {
	
	public static class Count{
		
		//наличие имени файла в базе данных
		public static boolean isNumberInvoice(String number) throws SQLException{
			String sql = "SELECT COUNT(*) AS COUNT FROM OUTCOMING WHERE NUMBERINVOICE = '"+number+"'";
			Statement statement = ConnectionDB.getInstance().getConnection().createStatement();
			ResultSet set = statement.executeQuery(sql);
			int count = -1;
			while(set.next()){
				count = set.getInt("COUNT");
			}
			if(count == 1){return true;}else{return false;}
		}
		
		//наличие ID статуса Ё—„‘ в базе данных
		public static boolean isOutcomingStatusId(String id) throws SQLException{
			String sql = "SELECT COUNT(*) AS COUNT FROM OUTCOMING_STATUSES WHERE ID = "+id;
			Statement statement = ConnectionDB.getInstance().getConnection().createStatement();
			ResultSet set = statement.executeQuery(sql);
			int count = -1;
			while(set.next()){
				count = set.getInt("COUNT");
			}
			if(count == 1){return true;}else{return false;}
		}
		
		//количество всех Ё—„‘ в таблице
		public static int getCountAll(){
			String sql = "SELECT COUNT(*) AS COUNT FROM OUTCOMING INNER JOIN OUTCOMING_STATUSES ON OUTCOMING.ID = OUTCOMING_STATUSES.OUTCOMINGID "+
						 "WHERE OUTCOMING_STATUSES.STATUSINVOICEEN IS NOT NULL";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				int count = -1;
				ResultSet set = statement.executeQuery();
				while(set.next()){
					count = set.getInt(1);
				}
				return count;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		//количество определенной Ё—„‘ в таблице 
		public static int getCountRecord(String number){
			String sql = "SELECT COUNT(*) AS COUNT FROM OUTCOMING " +
						 "WHERE OUTCOMING.NUMBERINVOICE = '"+number+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				int count = -1;
				ResultSet set = statement.executeQuery();
				while(set.next()){
					count = set.getInt(1);
				}
				return count;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}	
		
		//количество всех Ё—„‘ в таблице за год
		public static int getCountAllInYear(String date){
			String sql ="SELECT COUNT(*) AS 'COUNT' FROM OUTCOMING_STATUSES INNER JOIN OUTCOMING ON OUTCOMING_STATUSES.OUTCOMINGID = OUTCOMING.ID "+
						"WHERE OUTCOMING_STATUSES.ID IN (SELECT MAX(OUTCOMING_STATUSES.ID) AS 'MAX' FROM OUTCOMING_STATUSES GROUP BY 'MAX', OUTCOMING_STATUSES.OUTCOMINGID)"+
						"AND strftime('%Y', OUTCOMING.DATETRANSACTION) = '"+date+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				int count = -1;
				ResultSet set = statement.executeQuery();
				while(set.next()){
					count = set.getInt(1);
				}
				return count;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		//количество подписанных Ё—„‘ в таблице за год
		public static int getCountCompletedInYear(String date){
			String sql = "SELECT COUNT(*) AS 'COUNT' FROM OUTCOMING_STATUSES INNER JOIN OUTCOMING ON OUTCOMING_STATUSES.OUTCOMINGID = OUTCOMING.ID "+
						 "WHERE OUTCOMING_STATUSES.ID IN (SELECT MAX(OUTCOMING_STATUSES.ID) AS 'MAX' FROM OUTCOMING_STATUSES GROUP BY 'MAX', OUTCOMING_STATUSES.OUTCOMINGID "+
						 "HAVING (OUTCOMING_STATUSES.STATUSINVOICEEN = 'COMPLETED_SIGNED')) "+
						 "AND strftime('%Y',DATETRANSACTION) = '"+date+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				int count = -1;
				ResultSet set = statement.executeQuery();
				while(set.next()){
					count = set.getInt(1);
				}
				return count;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		
		//количество неподписанных Ё—„‘ в таблице за год
		public static int getCountUncompletedInYear(String date){
			String sql ="SELECT COUNT(*) AS 'COUNT' FROM OUTCOMING_STATUSES INNER JOIN OUTCOMING ON OUTCOMING_STATUSES.OUTCOMINGID = OUTCOMING.ID "+
					 	"WHERE OUTCOMING_STATUSES.ID IN (SELECT MAX(OUTCOMING_STATUSES.ID) AS 'MAX' FROM OUTCOMING_STATUSES GROUP BY 'MAX', OUTCOMING_STATUSES.OUTCOMINGID "+
					 	"HAVING (OUTCOMING_STATUSES.STATUSINVOICEEN = 'COMPLETED')) "+
					 	"AND strftime('%Y',DATETRANSACTION) = '"+date+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				int count = -1;
				ResultSet set = statement.executeQuery();
				while(set.next()){
					count = set.getInt(1);
				}
				return count;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		//количество отменЄнных Ё—„‘ за год
		public static int getCountCancelledInYear(String date){
			String sql ="SELECT COUNT(*) AS 'COUNT' FROM OUTCOMING_STATUSES INNER JOIN OUTCOMING ON OUTCOMING_STATUSES.OUTCOMINGID = OUTCOMING.ID "+
					 	"WHERE OUTCOMING_STATUSES.ID IN (SELECT MAX(OUTCOMING_STATUSES.ID) AS 'MAX' FROM OUTCOMING_STATUSES GROUP BY 'MAX', OUTCOMING_STATUSES.OUTCOMINGID "+
					 	"HAVING (OUTCOMING_STATUSES.STATUSINVOICEEN = 'CANCELLED')) "+
					 	"AND strftime('%Y',DATETRANSACTION) = '"+date+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				int count = -1;
				ResultSet set = statement.executeQuery();
				while(set.next()){
					count = set.getInt(1);
				}
				return count;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		//количество Ё—„‘ неопределенного статуса в таблице за год
		public static int getCountUndeterminedInYear(String date){
			String sql ="SELECT COUNT(*) AS 'COUNT' FROM OUTCOMING_STATUSES INNER JOIN OUTCOMING ON OUTCOMING_STATUSES.OUTCOMINGID = OUTCOMING.ID "+
						"WHERE OUTCOMING_STATUSES.ID IN (SELECT MAX(OUTCOMING_STATUSES.ID) AS 'MAX' FROM OUTCOMING_STATUSES GROUP BY 'MAX', OUTCOMING_STATUSES.OUTCOMINGID "+
					 	"HAVING (OUTCOMING_STATUSES.STATUSINVOICEEN = 'ON_AGREEMENT' OR OUTCOMING_STATUSES.STATUSINVOICEEN = 'IN_PROGRESS' OR OUTCOMING_STATUSES.STATUSINVOICEEN = 'NOT_FOUND' OR OUTCOMING_STATUSES.STATUSINVOICEEN = 'ERROR')) "+
						"AND strftime('%Y',DATETRANSACTION) = '"+date+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				int count = -1;
				ResultSet set = statement.executeQuery();
				while(set.next()){
					count = set.getInt(1);
				}
				return count;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		//
		//количество подписанных Ё—„‘ в таблице за мес€ц года
		public static int getCountCompletedInMonthYear(String month, String year){
			String sql ="SELECT COUNT(*) AS 'COUNT' FROM OUTCOMING_STATUSES INNER JOIN OUTCOMING ON OUTCOMING_STATUSES.OUTCOMINGID = OUTCOMING.ID "+
					 	"WHERE OUTCOMING_STATUSES.ID IN (SELECT MAX(OUTCOMING_STATUSES.ID) AS 'MAX' FROM OUTCOMING_STATUSES GROUP BY 'MAX', OUTCOMING_STATUSES.OUTCOMINGID "+
					 	"HAVING (OUTCOMING_STATUSES.STATUSINVOICEEN = 'COMPLETED_SIGNED')) "+
						"AND strftime('%Y',DATETRANSACTION) = '"+year+"' AND strftime('%m',DATETRANSACTION) = '"+month+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				int count = -1;
				ResultSet set = statement.executeQuery();
				while(set.next()){count = set.getInt(1);}
				return count;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		
		//количество неподписанных Ё—„‘ в таблице за мес€ц года
		public static int getCountUncompletedInMonthYear(String month, String year){
			String sql ="SELECT COUNT(*) AS 'COUNT' FROM OUTCOMING_STATUSES INNER JOIN OUTCOMING ON OUTCOMING_STATUSES.OUTCOMINGID = OUTCOMING.ID "+
				 		"WHERE OUTCOMING_STATUSES.ID IN (SELECT MAX(OUTCOMING_STATUSES.ID) AS 'MAX' FROM OUTCOMING_STATUSES GROUP BY 'MAX', OUTCOMING_STATUSES.OUTCOMINGID "+
				 		"HAVING (OUTCOMING_STATUSES.STATUSINVOICEEN = 'COMPLETED')) "+
					 	"AND strftime('%Y',DATETRANSACTION) = '"+year+"' AND strftime('%m',DATETRANSACTION) = '"+month+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				int count = -1;
				ResultSet set = statement.executeQuery();
				while(set.next()){count = set.getInt(1);}
				return count;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		//количество отменЄнных Ё—„‘ за мес€ц года
		public static int getCountCancelledInMonthYear(String month, String year){
			String sql ="SELECT COUNT(*) AS 'COUNT' FROM OUTCOMING_STATUSES INNER JOIN OUTCOMING ON OUTCOMING_STATUSES.OUTCOMINGID = OUTCOMING.ID "+
				 		"WHERE OUTCOMING_STATUSES.ID IN (SELECT MAX(OUTCOMING_STATUSES.ID) AS 'MAX' FROM OUTCOMING_STATUSES GROUP BY 'MAX', OUTCOMING_STATUSES.OUTCOMINGID "+
				 		"HAVING (OUTCOMING_STATUSES.STATUSINVOICEEN = 'CANCELLED')) "+
				 		"AND strftime('%Y',DATETRANSACTION) = '"+year+"' AND strftime('%m',DATETRANSACTION) = '"+month+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				int count = -1;
				ResultSet set = statement.executeQuery();
				while(set.next()){count = set.getInt(1);}
					return count;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		//количество Ё—„‘ неопределенного статуса в таблице за мес€ц года
		public static int getCountUndeterminedInMonthYear(String month, String year){
			String sql ="SELECT COUNT(*) AS 'COUNT' FROM OUTCOMING_STATUSES INNER JOIN OUTCOMING ON OUTCOMING_STATUSES.OUTCOMINGID = OUTCOMING.ID "+
						"WHERE OUTCOMING_STATUSES.ID IN (SELECT MAX(OUTCOMING_STATUSES.ID) AS 'MAX' FROM OUTCOMING_STATUSES GROUP BY 'MAX', OUTCOMING_STATUSES.OUTCOMINGID "+
						"HAVING (OUTCOMING_STATUSES.STATUSINVOICEEN = 'ON_AGREEMENT' OR OUTCOMING_STATUSES.STATUSINVOICEEN = 'IN_PROGRESS' OR OUTCOMING_STATUSES.STATUSINVOICEEN = 'NOT_FOUND' OR OUTCOMING_STATUSES.STATUSINVOICEEN = 'ERROR')) "+
						"AND strftime('%Y',DATETRANSACTION) = '"+year+"' AND strftime('%m',DATETRANSACTION) = '"+month+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				int count = -1;
				ResultSet set = statement.executeQuery();
				while(set.next()){count = set.getInt(1);}
				return count;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}	
		//
		
	}
	
	public static class Field{
		
		public static String getOutcomingStatus(String outcomingId){
			String status = "";
			try {
				if(WorkingOutcomingTable.Count.isOutcomingStatusId(outcomingId)){
					String sql = "SELECT MAX(ID) AS 'MAX', STATUSINVOICEEN FROM OUTCOMING_STATUSES WHERE OUTCOMINGID = "+outcomingId;
					try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
						ResultSet set = statement.executeQuery();
						if(set != null){
							while(set.next()){
								status = set.getString(2).trim();
							}
						}
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
					}
				}
			} catch (HeadlessException | SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
			}
			return status;
		}
		
		public static int getOutcomingId(String number){
			String sql = "SELECT ID FROM OUTCOMING WHERE NUMBERINVOICE = "+number;
			int id = -1; 
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				ResultSet set = statement.executeQuery();
				while(set.next()){id = set.getInt(1);}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
			}
			return id;
		}
		
	}
	
	public static class Insert{
		
		//добавление Ё—„‘
		public static int insertOutcomingFile(Invoice invoice){
			String sql = "INSERT INTO OUTCOMING("
					+ "UNP, " 				// 01
					+ "CODECOUNTRY, " 		// 02
					+ "NAME, " 				// 03
					+ "NUMBERINVOICE, " 	// 04
					+ "TYPEINVOICE, " 		// 05
					+ "DATEISSUE, " 		// 06
					+ "DATETRANSACTION, " 	// 07
					+ "BYINVOICE, " 		// 08
					+ "TOTALEXCISE, " 		// 09
					+ "TOTALVAT, " 			// 10
					+ "TOTALALL, " 			// 11
					+ "TOTALCOST)" 			// 12
					+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
			boolean isAdd = true;
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				statement.setString(1,  invoice.getRecipient().getUnp().trim());
				statement.setString(2,  invoice.getRecipient().getCountryCode().trim());
				statement.setString(3,  invoice.getRecipient().getName().trim());
				statement.setString(4,  invoice.getGeneral().getNumber().trim());
				statement.setString(5,  invoice.getGeneral().getDocumentType().trim());
				if(Verification.verifyField(invoice.getGeneral().getDateIssuance().trim())){
					if(invoice.getGeneral().getDateIssuance().trim().length() > 0){
						//statement.setString(6, InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(invoice.getGeneral().getDateIssuance().trim())));
						statement.setString(6, invoice.getGeneral().getDateIssuance().trim());
					}else{
						statement.setString(6, "");
					}
				}else{
					statement.setString(6, "");
				}
				
				if(invoice.getGeneral().getDateTransaction().trim().length() > 0){
					//statement.setString(7, InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(invoice.getGeneral().getDateTransaction().trim())));
					statement.setString(7, invoice.getGeneral().getDateTransaction().trim());
				}else{
					statement.setString(7, "");
				}
				
				if(Verification.verifyField(invoice.getGeneral().getInvoice())){
					statement.setString(8, invoice.getGeneral().getInvoice().trim());
				}else{
					statement.setString(8, "");
				}
				statement.setString(9, invoice.getRoster().getTotalExcise().trim().replace(",", "."));
				statement.setString(10, invoice.getRoster().getTotalVat().trim().replace(",", "."));
				statement.setString(11, invoice.getRoster().getTotalCostVat().trim().replace(",", "."));
				statement.setString(12, invoice.getRoster().getTotalCost().replace(",", "."));
				statement.executeUpdate();
			}catch(SQLException e){
				isAdd = false;
			}
			
			if(isAdd){
				String sqlId = "select last_insert_rowid()";
				try(PreparedStatement statementID = ConnectionDB.getInstance().getConnection().prepareStatement(sqlId)){
					int count = -1;
					ResultSet set = statementID.executeQuery();
					while(set.next()){
						count = set.getInt(1);
					}
					return count;
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
					return -1;
				}	
			}else{
				return -1;
			}
		}
		
		//добавление статуса Ё—„‘
		public static boolean insertOutcomingStatusesFile(String outcomingId, String statusEn, String statusRu){
			String sql = "INSERT INTO OUTCOMING_STATUSES("
					+ "OUTCOMINGID, " 			// 01
					+ "STATUSINVOICEEN, " 		// 02
					+ "STATUSINVOICERU) " 		// 03
					+ " VALUES (?,?,?)";
			boolean result = false;
			try (PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				statement.setString(1,  outcomingId.trim());
				statement.setString(2, statusEn);
				statement.setString(3, statusRu);
				statement.executeUpdate();
				result = true;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
			}
			return result;		
		}

		//добавление документов Ё—„‘
		public static boolean insertOutcomingDocumentsFile(int outcomingId, Invoice invoice){
			String sql = "INSERT INTO OUTCOMING_DOCUMENTS("
					+ "OUTCOMINGID, " 		// 01
					+ "CODEDOCUMENT, " 		// 02
					+ "DATEDOCUMENT, " 		// 03
					+ "BLANKCODEDOCUMENT, "	// 04
					+ "SERIADOCUMENT, "		// 05
					+ "NUMBERDOCUMENT)"		// 06
					+ " VALUES (?,?,?,?,?,?)";
			BooleanList list = new BooleanList();
			if(Verification.verifyList(invoice.getDeliveryCondition().getContract().getDocuments())){
				for(int index = 0; index < invoice.getDeliveryCondition().getContract().getDocuments().size(); index++){
					try{
						PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql);
						statement.setInt(1,  outcomingId);
						
						if(Verification.verifySection(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getDocType())){
							if(Verification.verifyField(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getDocType().getCode().trim())){
								statement.setString(2, invoice.getDeliveryCondition().getContract().getDocuments().get(index).getDocType().getCode().trim());
							}else{
								statement.setString(2, "");
							}
						}else{
							statement.setString(2, "");
						}
						
						if(Verification.verifyField(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getDate().trim())){
							if(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getDate().trim().length() > 0){
								//statement.setString(3, InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getDate().trim())));
								statement.setString(3, invoice.getDeliveryCondition().getContract().getDocuments().get(index).getDate());
							}else{
								statement.setString(3, "");
							}
						}else{
							statement.setString(3, "");
						}
						
						if(Verification.verifyField(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getBlankCode().trim())){
							statement.setString(4, invoice.getDeliveryCondition().getContract().getDocuments().get(index).getBlankCode().trim());
						}else{
							statement.setString(4, "");
						}
						
						if(Verification.verifyField(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getSeria().trim())){
							statement.setString(5, invoice.getDeliveryCondition().getContract().getDocuments().get(index).getSeria().trim());
						}else{
							statement.setString(5, "");
						}
						
						if(Verification.verifyField(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getNumber().trim())){
							statement.setString(6, invoice.getDeliveryCondition().getContract().getDocuments().get(index).getNumber().trim());
						}else{
							statement.setString(6, "");
						}
						
						statement.executeUpdate();
						list.add(true);
					}catch (SQLException /*| ParseException*/ e) {
						list.add(false);
					}
				}
			}
			return list.getResult();		
		}
	}
	
	public static class Lists{
		
		//список всех Ё—„‘ дл€ обновлени€
		public static List<NumberInvoice> selectNumbersInvoice(){
			List<NumberInvoice> list = new ArrayList<NumberInvoice>();
			String sql = "SELECT ID, NUMBERINVOICE FROM OUTCOMING";
			try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
				list.clear();
				ResultSet set = statement.executeQuery(sql);
				while(set.next()){
					list.add(new NumberInvoice().addId(set.getString(1).trim()).addNumber(set.getString(2).trim()));
				}
				return list;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
				return null;			
			}
		}
		
		//список неподписанных Ё—„‘ дл€ обновлени€
		public static List<NumberInvoice> selectNotSignedNumbersInvoice(){
			List<NumberInvoice> list = new ArrayList<NumberInvoice>();
			String sql = "SELECT MAX(OUTCOMING_STATUSES.ID) AS 'M', OUTCOMING_STATUSES.OUTCOMINGID, OUTCOMING_STATUSES.STATUSINVOICEEN FROM OUTCOMING "+
						 "INNER JOIN OUTCOMING_STATUSES ON OUTCOMING_STATUSES.OUTCOMINGID = OUTCOMING.ID GROUP BY 'M', OUTCOMING.NUMBERINVOICE "+
						 "HAVING OUTCOMING_STATUSES.STATUSINVOICEEN IS NOT NULL AND (OUTCOMING_STATUSES.STATUSINVOICEEN = 'COMPLETED' "+
						 "OR OUTCOMING_STATUSES.STATUSINVOICEEN = 'ON_AGREEMENT' OR OUTCOMING_STATUSES.STATUSINVOICEEN = 'IN_PROGRESS' "+
						 "OR OUTCOMING_STATUSES.STATUSINVOICEEN = 'NOT_FOUND')";
			try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
				list.clear();
				ResultSet set = statement.executeQuery(sql);
				while(set.next()){
					list.add(new NumberInvoice().addId(set.getString(1).trim()).addNumber(set.getString(2).trim()));
				}
				return list;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
				return null;			
			}
		}
		
	}
	
	public static class Report{
		
		//список Ё—„‘ дл€ отчета
		public static List<UnloadedInvoice> selectSignedNumbersInvoice(){
			List<UnloadedInvoice> list = new ArrayList<UnloadedInvoice>();
			String sql ="SELECT OUTCOMING.ID, OUTCOMING.UNP, OUTCOMING.DATETRANSACTION, OUTCOMING.NUMBERINVOICE, OUTCOMING_STATUSES.STATUSINVOICEEN, OUTCOMING.TOTALCOST, OUTCOMING.TOTALVAT, OUTCOMING.TOTALALL, MIN(OUTCOMING_DOCUMENTS.DATEDOCUMENT) AS 'MIN' "+
						"FROM (OUTCOMING INNER JOIN OUTCOMING_STATUSES ON OUTCOMING.ID = OUTCOMING_STATUSES.OUTCOMINGID) LEFT JOIN OUTCOMING_DOCUMENTS ON OUTCOMING_DOCUMENTS.OUTCOMINGID = OUTCOMING.ID "+
						"GROUP BY OUTCOMING.NUMBERINVOICE "+
						"HAVING OUTCOMING_STATUSES.STATUSINVOICEEN IS NOT NULL ";
			try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
				list.clear();
				ResultSet set = statement.executeQuery(sql);
				String statusRu = "";
				while(set.next()){
					if(set.getString("OUTCOMING_STATUSES.STATUSINVOICEEN").trim().equals("COMPLETED_SIGNED")){
						statusRu = "ѕодписан";
					}else{
						statusRu = "Ќе подписан";
					}
					java.util.Date dateDocument = null;
					if(set.getString("MIN") != null){
						dateDocument = new SimpleDateFormat("dd.MM.yyyy").parse(InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateReverseSmallDash(set.getString("MIN").trim())));
					}
					list.add(new UnloadedInvoice.Builder()
							.setUnp(set.getString("UNP").trim())
							.setDateTransaction(new SimpleDateFormat("dd.MM.yyyy").parse(InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateReverseSmallDash(set.getString("DATETRANSACTION").trim()))))
							.setNumberInvoice(set.getString("NUMBERINVOICE").trim())
							.setStatusInvoiceRu(statusRu)
							.setTotalCost(set.getString("TOTALCOST").trim())
							.setTotalVat(set.getString("TOTALVAT").trim())
							.setTotalAll(set.getString("TOTALALL").trim())
							.setDateDocument(dateDocument)
							.build());
				}
				return list;
			} catch (SQLException | ParseException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
				return null;			
			}
		}
			
		//список Ё—„‘ дл€ отчета на дату
		public static List<UnloadedInvoice> selectSignedNumbersInvoiceAtDate(java.util.Date date, Comparator<UnloadedInvoice> comparator, String status){
			List<UnloadedInvoice> list = new ArrayList<UnloadedInvoice>();
			String sql ="SELECT OUTCOMING.ID, OUTCOMING.UNP, OUTCOMING.DATETRANSACTION, OUTCOMING.NUMBERINVOICE, OUTCOMING_STATUSES.STATUSINVOICEEN, OUTCOMING.TOTALCOST, OUTCOMING.TOTALVAT, OUTCOMING.TOTALALL, MIN(OUTCOMING_DOCUMENTS.DATEDOCUMENT) AS 'MIN' "+
						"FROM (OUTCOMING INNER JOIN OUTCOMING_STATUSES ON OUTCOMING.ID = OUTCOMING_STATUSES.OUTCOMINGID) LEFT JOIN OUTCOMING_DOCUMENTS ON OUTCOMING_DOCUMENTS.OUTCOMINGID = OUTCOMING.ID "+
						"GROUP BY OUTCOMING.NUMBERINVOICE "+
						"HAVING OUTCOMING_STATUSES.STATUSINVOICEEN IS NOT NULL "+
						"AND OUTCOMING.DATETRANSACTION = '"+date.toString()+"' "+status+" ORDER BY strftime('%Y',OUTCOMING.DATETRANSACTION), DATE(OUTCOMING.DATETRANSACTION)";
			try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
				list.clear();
				ResultSet set = statement.executeQuery(sql);
				String statusRu = "";
				Color color = Color.BLACK;
				while(set.next()){
					if(set.getString("STATUSINVOICEEN").trim().equals("COMPLETED_SIGNED")){
						statusRu = "ѕодписан";
						color = ResultFont.getGreen();
					}else if((set.getString("STATUSINVOICEEN").trim().equals("CANCELLED"))||(set.getString("STATUSINVOICEEN").trim().equals("ON_AGREEMENT_CANCELLED"))){
						statusRu = "јннулирован";
						color = ResultFont.getBlack();
					}
					else{
						statusRu = "Ќе подписан";
						color = ResultFont.getRed();
					}
					java.util.Date dateDocument = null;
					if(set.getString("MIN") != null){
						dateDocument = new SimpleDateFormat("dd.MM.yyyy").parse(InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateReverseSmallDash(set.getString("MIN").trim())));
					}
					list.add(new UnloadedInvoice.Builder()
							.setUnp(set.getString("UNP").trim())
							.setDateTransaction(new SimpleDateFormat("dd.MM.yyyy").parse(InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateReverseSmallDash(set.getString("DATETRANSACTION").trim()))))
							.setNumberInvoice(set.getString("NUMBERINVOICE").trim())
							.setStatusInvoiceRu(statusRu)
							.setTotalCost(set.getString("TOTALCOST").trim())
							.setTotalVat(set.getString("TOTALVAT").trim())
							.setTotalAll(set.getString("TOTALALL").trim())
							.setDateDocument(dateDocument)
							.setColor(color)
							.build());
				}
				Collections.sort(list, comparator);
				return list;
			} catch (SQLException | ParseException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
				return null;			
			}
		}
		
		//список Ё—„‘ дл€ отчета на период
		public static List<UnloadedInvoice> selectSignedNumbersInvoiceAtBetween(java.util.Date leftDate, java.util.Date date, Comparator<UnloadedInvoice> comparator, String status){
			List<UnloadedInvoice> list = new ArrayList<UnloadedInvoice>();
			String sql ="SELECT OUTCOMING.ID, OUTCOMING.UNP, OUTCOMING.DATETRANSACTION, OUTCOMING.NUMBERINVOICE, OUTCOMING_STATUSES.STATUSINVOICEEN, OUTCOMING.TOTALCOST, OUTCOMING.TOTALVAT, OUTCOMING.TOTALALL, MIN(OUTCOMING_DOCUMENTS.DATEDOCUMENT) AS 'MIN' "+
						"FROM (OUTCOMING INNER JOIN OUTCOMING_STATUSES ON OUTCOMING.ID = OUTCOMING_STATUSES.OUTCOMINGID) LEFT JOIN OUTCOMING_DOCUMENTS ON OUTCOMING_DOCUMENTS.OUTCOMINGID = OUTCOMING.ID "+
						"GROUP BY OUTCOMING.NUMBERINVOICE "+
						"HAVING OUTCOMING_STATUSES.STATUSINVOICEEN IS NOT NULL "+ 
						"AND OUTCOMING.DATETRANSACTION BETWEEN '"+leftDate.toString()+"' AND '"+date.toString()+"' "+status;
			try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
				list.clear();
				ResultSet set = statement.executeQuery(sql);
				String statusRu = "";
				Color color = Color.BLACK;
				while(set.next()){
					if(set.getString("STATUSINVOICEEN").trim().equals("COMPLETED_SIGNED")){
						statusRu = "ѕодписан";
						color = ResultFont.getGreen();
					}else if((set.getString("STATUSINVOICEEN").trim().equals("CANCELLED"))||(set.getString("STATUSINVOICEEN").trim().equals("ON_AGREEMENT_CANCELLED"))){
						statusRu = "јннулирован";
						color = ResultFont.getBlack();
					}
					else{
						statusRu = "Ќе подписан";
						color = ResultFont.getRed();
					}
					java.util.Date dateDocument = null;
					if(set.getString("MIN") != null){
						dateDocument = new SimpleDateFormat("dd.MM.yyyy").parse(InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateReverseSmallDash(set.getString("MIN").trim())));
					}
					list.add(new UnloadedInvoice.Builder()
							.setUnp(set.getString("UNP").trim())
							.setDateTransaction(new SimpleDateFormat("dd.MM.yyyy").parse(InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateReverseSmallDash(set.getString("DATETRANSACTION").trim()))))
							.setNumberInvoice(set.getString("NUMBERINVOICE").trim())
							.setStatusInvoiceRu(statusRu)
							.setTotalCost(set.getString("TOTALCOST").trim())
							.setTotalVat(set.getString("TOTALVAT").trim())
							.setTotalAll(set.getString("TOTALALL").trim())
							.setDateDocument(dateDocument)
							.setColor(color)
							.build());
				}
				Collections.sort(list, comparator);
				return list;
			} catch (SQLException | ParseException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
				return null;			
			}
		}
	
	}

	public static class Update{

		//обновление статусов Ё—„‘
		public static boolean updateStatus(String status, String number) throws SQLException{
			String sql = "UPDATE OUTCOMING SET STATUSINVOICEEN = ?, STATUSINVOICERU = ? WHERE NUMBERINVOICE = ?";
			boolean result = false;
			PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql);
			statement.setString(1, Status.valueOf(status.trim()).getEnValue());
			statement.setString(2, Status.valueOf(status.trim()).getRuValue());
			statement.setString(3, number);
			statement.executeUpdate();
			result = true;
			return result;
		}
		
		//обновление статусов при загрузке файла
		public static boolean updateStatusFromFile(String ruStatus, String number) throws SQLException{
			String sql = "UPDATE OUTCOMING SET STATUSINVOICEEN = ? WHERE NUMBERINVOICE = ?";
			boolean result = false;
			PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql);
			statement.setString(1, Status.valueRuOf(ruStatus.trim()));
			statement.setString(2, number);
			statement.executeUpdate();
			result = true;
			return result;
		}
		
		public static boolean updateDateFromFile(String column, String date, String number) throws SQLException{
			String sql = "UPDATE OUTCOMING SET ? = ? WHERE NUMBERINVOICE = ?";
			boolean result = false;
			PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql);
			statement.setString(1, column.trim());
			statement.setString(2, date.trim());
			statement.setString(3, number.trim());
			statement.executeUpdate();
			result = true;
			return result;
		}
		
	}
		
	public static class Date{
	
		//список годов на основе таблицы
		public static List<String> selectYearInvoice(){
			List<String> list = new ArrayList<String>();
			String sql = "SELECT strftime('%Y',DATETRANSACTION) as cYEAR FROM OUTCOMING GROUP BY cYEAR ORDER BY cYEAR DESC";
			try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
				ResultSet set = statement.executeQuery(sql);
				while(set.next()){
					list.add(set.getString("cYEAR"));
				}
				return list;
			}catch(SQLException e){
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
				return null;
			}
			
		}
	
		//получение списка пар √од-ћес€ц 
		public static List<MonthYearItem> selectMonthYear(String year){
			List<MonthYearItem> list = new ArrayList<MonthYearItem>();
			String sql = "SELECT strftime('%Y',DATETRANSACTION) as cYEAR, strftime('%m',DATETRANSACTION) as cMONTH FROM OUTCOMING GROUP BY cYEAR, cMONTH HAVING cYEAR = '"+year+"' ORDER BY cMONTH DESC";
			try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
				ResultSet set = statement.executeQuery(sql);
				while(set.next()){list.add(new MonthYearItem.Builder().setYear(set.getString("cYEAR")).setMonth(set.getString("cMONTH")).build());}
				return list;
			}catch(SQLException e){
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}
		
		//получение даты начала мес€ца
		public static String getStartMonthOfDate(String month, String year){
			String sql = "SELECT date(DATETRANSACTION,'start of month') AS startMonth FROM OUTCOMING GROUP BY date(DATETRANSACTION,'start of month') HAVING strftime('%Y',DATETRANSACTION) = '"+year+"' AND strftime('%m',DATETRANSACTION) = '"+month+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				String start = "";
				ResultSet set = statement.executeQuery();
				while(set.next()){start = set.getString("startMonth");}
				return start;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
				return "01.01.1999";
			}
		}
		
		//получение даты конца мес€ца
		public static String getEndMonthOfDate(String month, String year){
			String sql = "SELECT date(DATETRANSACTION,'start of month','+1 month','-1 day') AS endMonth FROM OUTCOMING GROUP BY date(DATETRANSACTION,'start of month') HAVING strftime('%Y',DATETRANSACTION) = '"+year+"' AND strftime('%m',DATETRANSACTION) = '"+month+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				String start = "";
				ResultSet set = statement.executeQuery();
				while(set.next()){start = set.getString("endMonth");}
				return start;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
				return "31.01.1999";
			}
		}
	
	}
	
	public static class Table{
		
		//получение списка столбцов
		public static List<String> getColumns(){
			List<String> list = new ArrayList<String>();
			String sql = "PRAGMA table_info(outcoming) ";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				ResultSet set = statement.executeQuery();
				while(set.next()){list.add(set.getString("name"));}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
			}
			return list;
		}

		//есть ли столбец в списке столбцов?
		public static boolean isContainsColumn(List<String> list, String column){
			boolean result = false;
			if(list.contains(column)){
				result = true;
			}
			return result;
		}
		
		//добавление столбца в таблицу
		public static boolean addColumn(String column, String type){
			String sql = "ALTER TABLE outcoming ADD COLUMN "+column.trim()+" "+type.trim();
			boolean result = false;
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				statement.executeUpdate();
				result = true;
			}catch(SQLException e){
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
				return false;
			}
			return result;
		}
	}
	
	public static class Additional{
		
		//-—траны------------------------------
		
		//наличие кода страны в базе данных
		public static boolean isCountryCode(String code) throws SQLException{
			String sql = "SELECT COUNT(*) AS COUNT FROM COUNTRY WHERE CODE = '"+code+"'";
			Statement statement = ConnectionDB.getInstance().getConnection().createStatement();
			ResultSet set = statement.executeQuery(sql);
			int count = -1;
			while(set.next()){
				count = set.getInt("COUNT");
			}
			if(count == 1){return true;}else{return false;}
		}
		
		public static String getCountryName(String code){
			String country = "";
			try {
				if(WorkingOutcomingTable.Additional.isCountryCode(code)){
					String sql = "SELECT NAME FROM COUNTRY WHERE CODE = '"+code+"'";
					try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
						ResultSet set = statement.executeQuery();
						if(set != null){
							while(set.next()){
								country = set.getString(1).trim();
							}
						}
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
					}
				}
			} catch (HeadlessException | SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
			}
			return country;
		}
		
		//-“ипы документов---------------------
		
		//наличие кода страны в базе данных
		public static boolean isTypeDocumentCode(String code) throws SQLException{
			String sql = "SELECT COUNT(*) AS COUNT FROM TYPEDOCUMENT WHERE CODE = '"+code+"'";
			Statement statement = ConnectionDB.getInstance().getConnection().createStatement();
			ResultSet set = statement.executeQuery(sql);
			int count = -1;
			while(set.next()){
				count = set.getInt("COUNT");
			}
			if(count == 1){return true;}else{return false;}
		}

		public static String getTypeDocumentName(String code){
			String country = "";
			try {
				if(WorkingOutcomingTable.Additional.isTypeDocumentCode(code)){
					String sql = "SELECT NAME FROM TYPEDOCUMENT WHERE CODE = '"+code+"'";
					try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
						ResultSet set = statement.executeQuery();
						if(set != null){
							while(set.next()){
								country = set.getString(1).trim();
							}
						}
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
					}
				}
			} catch (HeadlessException | SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"ќшибка",JOptionPane.ERROR_MESSAGE);
			}
			return country;
		}
		
	}
}
