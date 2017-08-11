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

import by.gomelagro.outcoming.base.ApplicationConstants;
import by.gomelagro.outcoming.format.date.InvoiceDateFormat;
import by.gomelagro.outcoming.gui.db.files.data.UnloadedInvoice;
import by.gomelagro.outcoming.gui.db.number.NumberInvoice;
import by.gomelagro.outcoming.gui.frames.count.ILoadCount;
import by.gomelagro.outcoming.gui.frames.count.ISendCount;
import by.gomelagro.outcoming.gui.frames.invoice.Invoice;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.basic.BooleanList;
import by.gomelagro.outcoming.gui.frames.invoice.data.list.basic.StringList;
import by.gomelagro.outcoming.gui.frames.invoice.verification.Validation;
import by.gomelagro.outcoming.gui.frames.list.MonthYearItem;
import by.gomelagro.outcoming.gui.frames.report.component.ResultFont;
import by.gomelagro.outcoming.status.Status;

public class WorkingOutcomingTable {
	
	public static class Statistics{
		public static boolean insertStatisticLoad(ILoadCount insert, ILoadCount update){
			String sql = "INSERT INTO "+ApplicationConstants.DB_TABLENAME+"_LOADED("
					+ "DATE, " 			// 01
					+ "BASEINSERT, " 	// 02
					+ "MISSINSERT, " 	// 03
					+ "ERRORINSERT, "	// 04
					+ "BASEUPDATE, "	// 05
					+ "MISSUPDATE, "	// 06
					+ "ERRORUPDATE "	// 07
					+ ") " 		
					+ " VALUES (?,?,?,?,?,?,?)";
			boolean result = false;
			try (PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				statement.setString(1, new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()));
				statement.setString(2, String.valueOf(insert.getBaseCount()));
				statement.setString(3, String.valueOf(insert.getMissCount()));
				statement.setString(4, String.valueOf(insert.getErrorCount()));
				statement.setString(5, String.valueOf(update.getBaseCount()));
				statement.setString(6, String.valueOf(update.getMissCount()));
				statement.setString(7, String.valueOf(update.getErrorCount()));
				statement.executeUpdate();
				result = true;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			}
			return result;	
		}

		public static StringList getStatisticLoad(int count) throws SQLException{
			StringList list = new StringList();
			if(count > 0){
				String sql = "SELECT * FROM "+ApplicationConstants.DB_TABLENAME+"_LOADED ORDER BY ID DESC LIMIT "+count;
				Statement statement = ConnectionDB.getInstance().getConnection().createStatement();
				ResultSet set = statement.executeQuery(sql);
				while(set.next()){
					list.add(
							"#"+set.getString("ID")+" "+set.getString("DATE")+
							": добавлено - "+set.getString("BASEINSERT")+
							" ЭСЧФ, пропущено - "+set.getString("MISSINSERT")+
							" ЭСЧФ, ошибок - "+set.getString("ERRORINSERT")+
							
							"ЭСЧФ; обновлено - "+set.getString("BASEUPDATE")+
							" ЭСЧФ, пропущено - "+set.getString("MISSUPDATE")+
							" ЭСЧФ, ошибок - "+set.getString("ERRORUPDATE")+" ЭСЧФ;"
							);
				}
			}else{
				list.clear();
			}
			return list;
		}
		
		public static boolean insertStatisticSend(ISendCount insert, ILoadCount update){
			String sql = "INSERT INTO "+ApplicationConstants.DB_TABLENAME+"_SENDED("
					+ "DATE, " 			// 01
					+ "VALIDINSERT, " 	// 02
					+ "ACCEPTINSERT, " 	// 03
					+ "ERRORINSERT, "	// 04
					+ "INVALIDINSERT"	// 05
					+ "BASEUPDATE, "	// 06
					+ "MISSUPDATE, "	// 07
					+ "ERRORUPDATE "	// 08
					+ ") " 		
					+ " VALUES (?,?,?,?,?,?,?,?)";
			boolean result = false;
			try (PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				statement.setString(1, new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()));
				statement.setString(2, String.valueOf(insert.getValidCount()));
				statement.setString(3, String.valueOf(insert.getAcceptCount()));
				statement.setString(4, String.valueOf(insert.getErrorCount()));
				statement.setString(4, String.valueOf(insert.getInValidCount()));
				statement.setString(6, String.valueOf(update.getBaseCount()));
				statement.setString(7, String.valueOf(update.getMissCount()));
				statement.setString(8, String.valueOf(update.getErrorCount()));
				statement.executeUpdate();
				result = true;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			}
			return result;
		}
		
		public static StringList getStatisticSend(int count) throws SQLException{
			StringList list = new StringList();
			if(count > 0){
				String sql = "SELECT * FROM "+ApplicationConstants.DB_TABLENAME+"_SENDED ORDER BY ID DESC LIMIT "+count;
				Statement statement = ConnectionDB.getInstance().getConnection().createStatement();
				ResultSet set = statement.executeQuery(sql);
				while(set.next()){
					list.add(
							"#"+set.getString("ID")+" "+set.getString("DATE")+
							": добавлено - "+set.getString("VALIDINSERT")+
							" ЭСЧФ, пропущено - "+set.getString("ACCEPTINSERT")+
							" ЭСЧФ, ошибок - "+set.getString("ERRORINSERT")+
							" ЭСЧФ, некорректных - "+set.getString("INVALIDINSERT")+
							
							"ЭСЧФ; обновлено - "+set.getString("UPDATE")+
							" ЭСЧФ, пропущено - "+set.getString("MISSUPDATE")+
							" ЭСЧФ, ошибок - "+set.getString("ERRORUPDATE")+" ЭСЧФ;"
							);
				}
			}else{
				list.clear();
			}
			return list;
		}
	}
	
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
		
		//наличие ID статуса ЭСЧФ в базе данных
		public static boolean isStatusOutcomingId(String outcomingId) throws SQLException{
			String sql = "SELECT COUNT(*) AS COUNT FROM OUTCOMING_STATUSES WHERE OUTCOMINGID = "+outcomingId;
			Statement statement = ConnectionDB.getInstance().getConnection().createStatement();
			ResultSet set = statement.executeQuery(sql);
			int count = -1;
			while(set.next()){
				count = set.getInt("COUNT");
			}
			if(count > 0){return true;}else{return false;}
		}
		
		//количество всех ЭСЧФ в таблице
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
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		//количество определенной ЭСЧФ в таблице 
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
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}	
		
		//количество всех ЭСЧФ в таблице за год
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
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		//количество подписанных ЭСЧФ в таблице за год
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
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		
		//количество неподписанных ЭСЧФ в таблице за год
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
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		//количество отменённых ЭСЧФ за год
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
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		//количество ЭСЧФ неопределенного статуса в таблице за год
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
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		//
		//количество подписанных ЭСЧФ в таблице за месяц года
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
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		
		//количество неподписанных ЭСЧФ в таблице за месяц года
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
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		//количество отменённых ЭСЧФ за месяц года
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
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		//количество ЭСЧФ неопределенного статуса в таблице за месяц года
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
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}	
		
		//наличие единицы измерения в базе данных
		public static boolean isUnit(String unitNumber) throws SQLException{
			String sql = "SELECT COUNT(*) AS COUNT FROM UNIT WHERE CODE = '"+unitNumber+"'";
			Statement statement = ConnectionDB.getInstance().getConnection().createStatement();
			ResultSet set = statement.executeQuery(sql);
			int count = -1;
			while(set.next()){
				count = set.getInt("COUNT");
			}
			if(count == 1){return true;}else{return false;}
		}
		
		//наличие кода страны в базе данных
		public static boolean isCountryCode(String countryCode) throws SQLException{
			String sql = "SELECT COUNT(*) AS COUNT FROM COUNTRY WHERE CODE = '"+countryCode+"'";
			Statement statement = ConnectionDB.getInstance().getConnection().createStatement();
			ResultSet set = statement.executeQuery(sql);
			int count = -1;
			while(set.next()){
				count = set.getInt("COUNT");
			}
			if(count == 1){return true;}else{return false;}
		}		
		
		//наличие кода страны в базе данных
		public static boolean isBranch(String branchCode) throws SQLException{
			String sql = "SELECT COUNT(*) AS COUNT FROM BRANCH WHERE CODE = '"+branchCode+"'";
			Statement statement = ConnectionDB.getInstance().getConnection().createStatement();
			ResultSet set = statement.executeQuery(sql);
			int count = -1;
			while(set.next()){
				count = set.getInt("COUNT");
			}
			if(count == 1){return true;}else{return false;}
		}
		
		//наличие кода страны в базе данных
		public static boolean isTypeDocument(String typeDocument) throws SQLException{
			String sql = "SELECT COUNT(*) AS COUNT FROM TYPEDOCUMENT WHERE CODE = '"+typeDocument+"'";
			Statement statement = ConnectionDB.getInstance().getConnection().createStatement();
			ResultSet set = statement.executeQuery(sql);
			int count = -1;
			while(set.next()){
				count = set.getInt("COUNT");
			}
			if(count == 1){return true;}else{return false;}
		}
	}
	
	public static class Field{
		
		public static String getOutcomingStatus(String outcomingId){
			String status = "";
			try {
				if(WorkingOutcomingTable.Count.isStatusOutcomingId(outcomingId)){
					String sql = "SELECT MAX(ID) AS 'MAX', STATUSINVOICEEN FROM OUTCOMING_STATUSES WHERE OUTCOMINGID = "+outcomingId;
					try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
						ResultSet set = statement.executeQuery();
						if(set != null){
							while(set.next()){
								status = set.getString("STATUSINVOICEEN").trim();
							}
						}
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
						System.err.println("Ошибка обработки SQL");
						status = "";
					}
				}
			} catch (HeadlessException | SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				System.err.println("Ошибка обработки");
				status = "";
			}
			return status;
		}
		
		public static int getOutcomingId(String number){
			String sql = "SELECT ID FROM OUTCOMING WHERE NUMBERINVOICE = '"+number+"'";
			int id = -1; 
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				ResultSet set = statement.executeQuery();
				while(set.next()){id = set.getInt(1);}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			}
			return id;
		}
		
	}
	
	public static class Insert{
		
		public static boolean insertOutcoming(String[] fields) throws SQLException, ParseException{
			BooleanList list = new BooleanList();
			
			boolean isAdd = false;
			isAdd = insertOutcomingBase(fields);
			int id = -1;
			id = lastInsertRowId(isAdd);
			if(id == -1){
				list.add(false);
			}else{
				list.add(true);
				
				list.add(insertOutcomingStatuses(id, fields));
				list.add(insertOutcomingDocuments(id, fields));
			}
						
			return list.getResult();
		}
				
		private static int lastInsertRowId(boolean isAdd){
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
					JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
					return -1;
				}	
			}else{
				return -1;
			}
		}
		
		private static boolean insertOutcomingBase(String[] fields) throws SQLException, ParseException{
			String sql = "INSERT INTO "+ApplicationConstants.DB_TABLENAME+"("
					+ "UNP, " 				// 01	08
					+ "CODECOUNTRY, " 		// 02	07
					+ "NAME, " 				// 03	10
					+ "NUMBERINVOICE, " 	// 04	13
					+ "TYPEINVOICE, " 		// 05	15
					+ "DATEISSUE, " 		// 06	17
					+ "DATETRANSACTION, " 	// 07	18
					+ "BYINVOICE, " 		// 08	14
					+ "TOTALEXCISE, " 		// 09	39
					+ "TOTALVAT, " 			// 10	40
					+ "TOTALALL, " 			// 11	41
					+ "TOTALCOST)" 			// 12	37
					+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
			boolean result = false;
				PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql);
				statement.setString(1,  fields[DatabaseFieldConstants.Invoice.UNP]);
				statement.setString(2,  fields[DatabaseFieldConstants.Invoice.CODECOUNTRY]);
				statement.setString(3,  fields[DatabaseFieldConstants.Invoice.NAME]);
				statement.setString(4,  fields[DatabaseFieldConstants.Invoice.NUMBERINVOICE]);
				statement.setString(5,  fields[DatabaseFieldConstants.Invoice.TYPEINVOICE]);
				if(fields[DatabaseFieldConstants.Invoice.DATEISSUE].trim().length() > 0){
					statement.setString(6,  InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(fields[DatabaseFieldConstants.Invoice.DATEISSUE])));
				}else{
					statement.setString(6,  fields[DatabaseFieldConstants.Invoice.DATEISSUE]);
				}
				if(fields[DatabaseFieldConstants.Invoice.DATETRANSACTION].trim().length() > 0){
					statement.setString(7,  InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(fields[DatabaseFieldConstants.Invoice.DATETRANSACTION])));
				}else{
					statement.setString(7,  fields[DatabaseFieldConstants.Invoice.DATETRANSACTION]);
				}
				statement.setString(8, fields[DatabaseFieldConstants.Invoice.BYINVOICE]);
				statement.setString(9, fields[DatabaseFieldConstants.Invoice.TOTALEXCISE].replace(",", "."));
				statement.setString(10, fields[DatabaseFieldConstants.Invoice.TOTALVAT].replace(",", "."));
				statement.setString(11, fields[DatabaseFieldConstants.Invoice.TOTALALL].replace(",", "."));
				statement.setString(12, fields[DatabaseFieldConstants.Invoice.TOTALCOST].replace(",", "."));
				statement.executeUpdate();
				result = true;
				return result;		
		}
		
		private static boolean insertOutcomingStatuses(int id, String[] fields) throws SQLException, ParseException{
			String sql = "INSERT INTO "+ApplicationConstants.DB_TABLENAME+"_STATUSES ("
					+ "OUTCOMINGID, "		// 01 	
					+ "STATUSINVOICERU, " 	// 02	16
					+ "STATUSINVOICEEN)" 	// 03
					+ " VALUES (?,?,?)";
			boolean result = false;
				PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql);
				statement.setString(1,  String.valueOf(id));
				statement.setString(2,  fields[DatabaseFieldConstants.Status.STATUSINVOICERU]);
				statement.setString(3, Status.valueRuOf(fields[DatabaseFieldConstants.Status.STATUSINVOICERU]));
				statement.executeUpdate();
				result = true;
				return result;		
		}

		private static boolean insertOutcomingDocuments(int id, String[] fields) throws SQLException, ParseException{
			String sql = "INSERT INTO "+ApplicationConstants.DB_TABLENAME+"_DOCUMENTS ("
					+ "OUTCOMINGID, " 		// 01 
					+ "CODEDOCUMENT, "		// 02
					+ "DATEDOCUMENT, "		// 03	37
					+ "BLANKCODEDOCUMENT, "	// 04	34
					+ "SERIADOCUMENT, "		// 05	35
					+ "NUMBERDOCUMENT)" 	// 06	36
					+ " VALUES (?,?,?,?,?,?)";
			boolean result = false;
				PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql);
				statement.setString(1,  String.valueOf(id));
				statement.setString(2,  "");
				if(fields[DatabaseFieldConstants.Document.DATEDOCUMENT].trim().length() > 0){
					statement.setString(3, fields[DatabaseFieldConstants.Document.DATEDOCUMENT]);
				}else{
					statement.setString(3,  fields[DatabaseFieldConstants.Document.DATEDOCUMENT]);
				}
				statement.setString(4,  fields[DatabaseFieldConstants.Document.BLANKCODEDOCUMENT]);
				statement.setString(5,  fields[DatabaseFieldConstants.Document.SERIADOCUMENT]);
				statement.setString(6,  fields[DatabaseFieldConstants.Document.NUMBERDOCUMENT]);
				
				statement.executeUpdate();
				result = true;
				return result;		
		}
		
		//добавление ЭСЧФ
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
				if(Validation.verifyField(invoice.getGeneral().getDateIssuance().trim())){
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
				
				if(Validation.verifyField(invoice.getGeneral().getInvoice())){
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
					JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
					return -1;
				}	
			}else{
				return -1;
			}
		}
		
		//добавление статуса ЭСЧФ
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
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			}
			return result;		
		}

		//добавление документов ЭСЧФ
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
			if(Validation.verifyList(invoice.getDeliveryCondition().getContract().getDocuments())){
				for(int index = 0; index < invoice.getDeliveryCondition().getContract().getDocuments().size(); index++){
					try{
						PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql);
						statement.setInt(1,  outcomingId);
						
						if(Validation.verifySection(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getDocType())){
							if(Validation.verifyField(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getDocType().getCode().trim())){
								statement.setString(2, invoice.getDeliveryCondition().getContract().getDocuments().get(index).getDocType().getCode().trim());
							}else{
								statement.setString(2, "");
							}
						}else{
							statement.setString(2, "");
						}
						
						if(Validation.verifyField(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getDate().trim())){
							if(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getDate().trim().length() > 0){
								statement.setString(3, invoice.getDeliveryCondition().getContract().getDocuments().get(index).getDate());
							}else{
								statement.setString(3, "");
							}
						}else{
							statement.setString(3, "");
						}
						
						if(Validation.verifyField(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getBlankCode().trim())){
							statement.setString(4, invoice.getDeliveryCondition().getContract().getDocuments().get(index).getBlankCode().trim());
						}else{
							statement.setString(4, "");
						}
						
						if(Validation.verifyField(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getSeria().trim())){
							statement.setString(5, invoice.getDeliveryCondition().getContract().getDocuments().get(index).getSeria().trim());
						}else{
							statement.setString(5, "");
						}
						
						if(Validation.verifyField(invoice.getDeliveryCondition().getContract().getDocuments().get(index).getNumber().trim())){
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
		
		//список всех ЭСЧФ для обновления
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
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return null;			
			}
		}
		
		//список неподписанных ЭСЧФ для обновления
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
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return null;			
			}
		}
		
	}
	
	public static class Report{
		
		//список ЭСЧФ для отчета
		public static List<UnloadedInvoice> selectSignedNumbersInvoice(){
			List<UnloadedInvoice> list = new ArrayList<UnloadedInvoice>();
			//String sql ="SELECT OUTCOMING.ID, OUTCOMING.UNP, OUTCOMING.DATETRANSACTION, OUTCOMING.NUMBERINVOICE, OUTCOMING_STATUSES.STATUSINVOICEEN, OUTCOMING.TOTALCOST, OUTCOMING.TOTALVAT, OUTCOMING.TOTALALL, MIN(OUTCOMING_DOCUMENTS.DATEDOCUMENT) AS 'MIN' "+
			String sql ="SELECT OUTCOMING.ID, OUTCOMING.UNP, OUTCOMING.DATETRANSACTION, OUTCOMING.NUMBERINVOICE, OUTCOMING_STATUSES.ID, OUTCOMING_STATUSES.STATUSINVOICEEN, OUTCOMING.TOTALCOST, OUTCOMING.TOTALVAT, OUTCOMING.TOTALALL, OUTCOMING_DOCUMENTS.DATEDOCUMENT "+
						"FROM (OUTCOMING INNER JOIN OUTCOMING_STATUSES ON (OUTCOMING.ID = OUTCOMING_STATUSES.OUTCOMINGID)) INNER JOIN OUTCOMING_DOCUMENTS ON OUTCOMING.ID = OUTCOMING_DOCUMENTS.OUTCOMINGID "+
						"GROUP BY OUTCOMING.NUMBERINVOICE "+
						"HAVING OUTCOMING_STATUSES.STATUSINVOICEEN IS NOT NULL ";
			try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
				list.clear();
				ResultSet set = statement.executeQuery(sql);
				String statusRu = "";
				while(set.next()){
					if(set.getString("OUTCOMING_STATUSES.STATUSINVOICEEN").trim().equals("COMPLETED_SIGNED")){
						statusRu = "Подписан";
					}else{
						statusRu = "Не подписан";
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
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return null;			
			}
		}
			
		//список ЭСЧФ для отчета на дату
		public static List<UnloadedInvoice> selectSignedNumbersInvoiceAtDate(java.util.Date date, Comparator<UnloadedInvoice> comparator, String status){
			List<UnloadedInvoice> list = new ArrayList<UnloadedInvoice>();
			String sql ="SELECT OUTCOMING.ID, OUTCOMING.UNP, OUTCOMING.DATETRANSACTION, OUTCOMING.NUMBERINVOICE, OUTCOMING_STATUSES.ID, OUTCOMING_STATUSES.STATUSINVOICEEN, OUTCOMING.TOTALCOST, OUTCOMING.TOTALVAT, OUTCOMING.TOTALALL, OUTCOMING_DOCUMENTS.DATEDOCUMENT "+
						"FROM (OUTCOMING INNER JOIN OUTCOMING_STATUSES ON (OUTCOMING.ID = OUTCOMING_STATUSES.OUTCOMINGID)) INNER JOIN OUTCOMING_DOCUMENTS ON OUTCOMING.ID = OUTCOMING_DOCUMENTS.OUTCOMINGID "+
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
						statusRu = "Подписан";
						color = ResultFont.getGreen();
					}else if((set.getString("STATUSINVOICEEN").trim().equals("CANCELLED"))||(set.getString("STATUSINVOICEEN").trim().equals("ON_AGREEMENT_CANCELLED"))){
						statusRu = "Аннулирован";
						color = ResultFont.getBlack();
					}
					else{
						statusRu = "Не подписан";
						color = ResultFont.getRed();
					}
					java.util.Date dateDocument = null;
					java.util.Date dateTransaction = null;
					System.out.print("{ЭСЧФ "+set.getString("NUMBERINVOICE")+ " DATETRANSACTION="+set.getString("DATETRANSACTION")+" DATEDOCUMENT="+set.getString("DATEDOCUMENT"));
					if(set.getString("DATETRANSACTION").trim().length() > 0){
						dateTransaction = new SimpleDateFormat("dd.MM.yyyy").parse(InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateReverseSmallDash(set.getString("DATETRANSACTION").trim())));
					}
						
					if(set.getString("DATEDOCUMENT").trim().length() > 0){
						dateDocument = new SimpleDateFormat("dd.MM.yyyy").parse(InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateReverseSmallDash(set.getString("DATEDOCUMENT").trim())));
					}
					System.out.println(" }");
					list.add(new UnloadedInvoice.Builder()
							.setUnp(set.getString("UNP").trim())
							.setDateTransaction(dateTransaction)
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
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return null;			
			}
		}
		
		//список ЭСЧФ для отчета на период
		public static List<UnloadedInvoice> selectSignedNumbersInvoiceAtBetween(java.util.Date leftDate, java.util.Date date, Comparator<UnloadedInvoice> comparator, String status){
			List<UnloadedInvoice> list = new ArrayList<UnloadedInvoice>();
			String sql ="SELECT OUTCOMING.ID, OUTCOMING.UNP, OUTCOMING.DATETRANSACTION, OUTCOMING.NUMBERINVOICE, OUTCOMING_STATUSES.ID, OUTCOMING_STATUSES.STATUSINVOICEEN, OUTCOMING.TOTALCOST, OUTCOMING.TOTALVAT, OUTCOMING.TOTALALL, OUTCOMING_DOCUMENTS.DATEDOCUMENT "+
						"FROM (OUTCOMING INNER JOIN OUTCOMING_STATUSES ON (OUTCOMING.ID = OUTCOMING_STATUSES.OUTCOMINGID)) INNER JOIN OUTCOMING_DOCUMENTS ON OUTCOMING.ID = OUTCOMING_DOCUMENTS.OUTCOMINGID "+
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
						statusRu = "Подписан";
						color = ResultFont.getGreen();
					}else if((set.getString("STATUSINVOICEEN").trim().equals("CANCELLED"))||(set.getString("STATUSINVOICEEN").trim().equals("ON_AGREEMENT_CANCELLED"))){
						statusRu = "Аннулирован";
						color = ResultFont.getBlack();
					}
					else{
						statusRu = "Не подписан";
						color = ResultFont.getRed();
					}
					java.util.Date dateDocument = null;
					java.util.Date dateTransaction = null;
					//System.out.print("{ЭСЧФ "+set.getString("NUMBERINVOICE")+ " DATETRANSACTION="+set.getString("DATETRANSACTION")+" DATEDOCUMENT="+set.getString("DATEDOCUMENT"));
					if(set.getString("DATETRANSACTION").trim().length() > 0){
						dateTransaction = new SimpleDateFormat("dd.MM.yyyy").parse(InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateReverseSmallDash(set.getString("DATETRANSACTION").trim())));
					}
						
					if(set.getString("DATEDOCUMENT").trim().length() > 0){
						dateDocument = new SimpleDateFormat("dd.MM.yyyy").parse(InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateReverseSmallDash(set.getString("DATEDOCUMENT").trim())));
					}
					//System.out.println(" }");
					list.add(new UnloadedInvoice.Builder()
							.setUnp(set.getString("UNP").trim())
							.setDateTransaction(dateTransaction)
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
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return null;			
			}
		}
	
	}

	public static class Update{

		//обновление статусов ЭСЧФ
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
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return null;
			}
			
		}
	
		//получение списка пар Год-Месяц 
		public static List<MonthYearItem> selectMonthYear(String year){
			List<MonthYearItem> list = new ArrayList<MonthYearItem>();
			String sql = "SELECT strftime('%Y',DATETRANSACTION) as cYEAR, strftime('%m',DATETRANSACTION) as cMONTH FROM OUTCOMING GROUP BY cYEAR, cMONTH HAVING cYEAR = '"+year+"' ORDER BY cMONTH DESC";
			try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
				ResultSet set = statement.executeQuery(sql);
				while(set.next()){list.add(new MonthYearItem.Builder().setYear(set.getString("cYEAR")).setMonth(set.getString("cMONTH")).build());}
				return list;
			}catch(SQLException e){
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}
		
		//получение даты начала месяца
		public static String getStartMonthOfDate(String month, String year){
			String sql = "SELECT date(DATETRANSACTION,'start of month') AS startMonth FROM OUTCOMING GROUP BY date(DATETRANSACTION,'start of month') HAVING strftime('%Y',DATETRANSACTION) = '"+year+"' AND strftime('%m',DATETRANSACTION) = '"+month+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				String start = "";
				ResultSet set = statement.executeQuery();
				while(set.next()){start = set.getString("startMonth");}
				return start;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return "01.01.1999";
			}
		}
		
		//получение даты конца месяца
		public static String getEndMonthOfDate(String month, String year){
			String sql = "SELECT date(DATETRANSACTION,'start of month','+1 month','-1 day') AS endMonth FROM OUTCOMING GROUP BY date(DATETRANSACTION,'start of month') HAVING strftime('%Y',DATETRANSACTION) = '"+year+"' AND strftime('%m',DATETRANSACTION) = '"+month+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				String start = "";
				ResultSet set = statement.executeQuery();
				while(set.next()){start = set.getString("endMonth");}
				return start;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return false;
			}
			return result;
		}
	}
	
	public static class Additional{
		
		//-Страны------------------------------
		
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
						JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
					}
				}else{
					country = "[ЗНАЧЕНИЕ НЕ ОБНАРУЖЕНО]";
				}
			} catch (HeadlessException | SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			}
			return country;
		}
		
		//-Типы документов---------------------
		
		//наличие типа документов в базе данных
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
			String typeDocument = "";
			try {
				if(WorkingOutcomingTable.Additional.isTypeDocumentCode(code)){
					String sql = "SELECT NAME FROM TYPEDOCUMENT WHERE CODE = '"+code+"'";
					try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
						ResultSet set = statement.executeQuery();
						if(set != null){
							while(set.next()){
								typeDocument = set.getString(1).trim();
							}
						}
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
					}
				}else{
					typeDocument = "[ЗНАЧЕНИЕ НЕ ОБНАРУЖЕНО]";
				}
			} catch (HeadlessException | SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			}
			return typeDocument;
		}
		
		//-Инспекции Министерства по налогам и сборам
		
		//наличие кода инспекции в базе данных
		public static boolean isIMTDCode(String code) throws SQLException{
			String sql = "SELECT COUNT(*) AS COUNT FROM IMTD WHERE CODE = '"+code+"'";
			Statement statement = ConnectionDB.getInstance().getConnection().createStatement();
			ResultSet set = statement.executeQuery(sql);
			int count = -1;
			while(set.next()){
				count = set.getInt("COUNT");
			}
			if(count == 1){return true;}else{return false;}
		}

		public static String getIMTDName(String code){
			String imtdName = "";
			try {
				if(WorkingOutcomingTable.Additional.isIMTDCode(code)){
					String sql = "SELECT SHORTNAME FROM IMTD WHERE CODE = '"+code+"'";
					try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
						ResultSet set = statement.executeQuery();
						if(set != null){
							while(set.next()){
								imtdName = set.getString(1).trim();
							}
						}
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
					}
				}else{
					imtdName = "[ЗНАЧЕНИЕ НЕ ОБНАРУЖЕНО]";
				}
			} catch (HeadlessException | SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			}
			return imtdName;
		}
		
		//-Единицы измерения-------------------
		
		//наличие кода единицы измерения в базе данных
		public static boolean isUnitCode(String code) throws SQLException{
			String sql = "SELECT COUNT(*) AS COUNT FROM UNIT WHERE CODE = '"+code+"'";
			Statement statement = ConnectionDB.getInstance().getConnection().createStatement();
			ResultSet set = statement.executeQuery(sql);
			int count = -1;
			while(set.next()){
				count = set.getInt("COUNT");
			}
			if(count == 1){return true;}else{return false;}
		}

		public static String getUnitName(String code){
			String unit = "";
			try {
				if(WorkingOutcomingTable.Additional.isUnitCode(code)){
					String sql = "SELECT FULLNAME FROM UNIT WHERE CODE = '"+code+"'";
					try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
						ResultSet set = statement.executeQuery();
						if(set != null){
							while(set.next()){
								unit = set.getString(1).trim();
							}
						}
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
					}
				}else{
					unit = "[ЗНАЧЕНИЕ НЕ ОБНАРУЖЕНО]";
				}
			} catch (HeadlessException | SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			}
			return unit;
		}
		
		//-Филиалы организаций-----------------
		
		//наличие кода филиала в базе данных
		public static boolean isBranchCode(String code, String unp) throws SQLException{
			String sql = "SELECT COUNT(*) AS COUNT FROM UNIT WHERE CODE = '"+code+"' AND UNP = '"+unp+"'";
			Statement statement = ConnectionDB.getInstance().getConnection().createStatement();
			ResultSet set = statement.executeQuery(sql);
			int count = -1;
			while(set.next()){
				count = set.getInt("COUNT");
			}
			if(count == 1){return true;}else{return false;}
		}

		public static String getBranchName(String code, String unp){
			String branchName = "";
			try {
				if(WorkingOutcomingTable.Additional.isBranchCode(code, unp)){
					String sql = "SELECT SHORTNAME FROM UNIT WHERE CODE = '"+code+"' AND UNP = '"+unp+"'";
					try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
						ResultSet set = statement.executeQuery();
						if(set != null){
							while(set.next()){
								branchName = set.getString(1).trim();
							}
						}
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
					}
				}else{
					branchName = "[ЗНАЧЕНИЕ НЕ ОБНАРУЖЕНО]";
				}
			} catch (HeadlessException | SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			}
			return branchName;
		}
	}
}
