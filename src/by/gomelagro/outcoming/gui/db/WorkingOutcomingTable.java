package by.gomelagro.outcoming.gui.db;

import java.awt.Color;
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
import by.gomelagro.outcoming.gui.frames.list.MonthYearItem;
import by.gomelagro.outcoming.gui.frames.report.component.ResultFont;
import by.gomelagro.outcoming.status.Status;

public class WorkingOutcomingTable {
	
	public static class Count{
		
		//наличие имени файла в базе данных
		public static boolean isNumberInvoice(String number) throws SQLException{
			String sql = "SELECT COUNT(*) AS COUT FROM OUTCOMING WHERE NUMBERINVOICE = '"+number+"'";
			Statement statement = ConnectionDB.getInstance().getConnection().createStatement();
			ResultSet set = statement.executeQuery(sql);
			int count = -1;
			while(set.next()){
				count = set.getInt("COUT");
			}
			if(count == 1){return true;}else{return false;}
		}
		
		//количество всех ЭСЧФ в таблице
		public static int getCountAll(){
			
			String sql = "SELECT COUNT(*) AS COUNT FROM OUTCOMING WHERE STATUSINVOICEEN IS NOT NULL";
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
			String sql = "SELECT COUNT(*) AS COUNT FROM OUTCOMING WHERE NUMBERINVOICE = '"+number+"'";
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
			String sql = "SELECT COUNT(*) AS COUNT FROM OUTCOMING WHERE STATUSINVOICEEN IS NOT NULL AND strftime('%Y',DATECOMMISSION) = '"+date+"'";
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
		
		//количество подписанных ЭСЧФ в таблице
		public static int getCountCompletedInYear(String date){
			String sql = "SELECT COUNT(*) AS COUNT FROM OUTCOMING WHERE STATUSINVOICEEN IS NOT NULL AND STATUSINVOICEEN = 'COMPLETED_SIGNED' AND strftime('%Y',DATECOMMISSION) = '"+date+"'";
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
		
		
		//количество неподписанных ЭСЧФ в таблице
		public static int getCountUncompletedInYear(String date){
			String sql = "SELECT COUNT(*) AS COUNT FROM OUTCOMING WHERE STATUSINVOICEEN IS NOT NULL AND STATUSINVOICEEN = 'COMPLETED' AND strftime('%Y',DATECOMMISSION) = '"+date+"'";
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
		
		//количество отменённых ЭСЧФ
		public static int getCountCancelledInYear(String date){
			String sql = "SELECT COUNT(*) AS COUNT FROM OUTCOMING WHERE STATUSINVOICEEN IS NOT NULL AND (STATUSINVOICEEN = 'CANCELLED' OR STATUSINVOICEEN = 'ON_AGREEMENT_CANCEL') AND strftime('%Y',DATECOMMISSION) = '"+date+"'";
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
		
		//количество ЭСЧФ неопределенного статуса в таблице 
		public static int getCountUndeterminedInYear(String date){
			String sql = "SELECT COUNT(*) AS COUNT FROM OUTCOMING WHERE STATUSINVOICEEN IS NOT NULL AND (STATUSINVOICEEN = 'ON_AGREEMENT' OR "
					+ "STATUSINVOICEEN = 'IN_PROGRESS' OR STATUSINVOICEEN = 'NOT_FOUND' OR STATUSINVOICEEN = 'ERROR') AND strftime('%Y',DATECOMMISSION) = '"+date+"'";
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
			String sql = "SELECT COUNT(*) AS COUNT FROM OUTCOMING WHERE STATUSINVOICEEN IS NOT NULL AND STATUSINVOICEEN = 'COMPLETED_SIGNED' AND strftime('%Y',DATECOMMISSION) = '"+year+"' AND strftime('%m',DATECOMMISSION) = '"+month+"'";
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
			String sql = "SELECT COUNT(*) AS COUNT FROM OUTCOMING WHERE STATUSINVOICEEN IS NOT NULL AND STATUSINVOICEEN = 'COMPLETED' AND strftime('%Y',DATECOMMISSION) = '"+year+"' AND strftime('%m',DATECOMMISSION) = '"+month+"'";
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
			String sql = "SELECT COUNT(*) AS COUNT FROM OUTCOMING WHERE STATUSINVOICEEN IS NOT NULL AND (STATUSINVOICEEN = 'CANCELLED' OR STATUSINVOICEEN = 'ON_AGREEMENT_CANCEL') AND strftime('%Y',DATECOMMISSION) = '"+year+"' AND strftime('%m',DATECOMMISSION) = '"+month+"'";
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
			String sql = "SELECT COUNT(*) AS COUNT FROM OUTCOMING WHERE STATUSINVOICEEN IS NOT NULL AND (STATUSINVOICEEN = 'ON_AGREEMENT' OR "
					+ "STATUSINVOICEEN = 'IN_PROGRESS' OR STATUSINVOICEEN = 'NOT_FOUND' OR STATUSINVOICEEN = 'ERROR') AND strftime('%Y',DATECOMMISSION) = '"+year+"' AND strftime('%m',DATECOMMISSION) = '"+month+"'";
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
		//
	}
	
	public static class Insert{
		
		//добавление ЭСЧФ
		public static boolean insertIncoming(String[] fields) throws SQLException, ParseException{
			String sql = "INSERT INTO OUTCOMING("
					+ "UNP, " 				// 01 06
					+ "CODECOUNTRY, " 		// 02 08
					+ "NAME, " 				// 03 09
					+ "NUMBERINVOICE, " 	// 04 12
					+ "TYPEINVOICE, " 		// 05 13
					+ "STATUSINVOICERU, " 	// 06 14
					+ "STATUSINVOICEEN, " 	// 07 
					+ "DATEISSUE, " 		// 08 15
					+ "DATECOMMISSION, " 	// 09 16
					+ "DATESIGNATURE, " 	// 10 17
					+ "BYINVOICE, " 		// 11 18
					+ "DATECANCELLATION, " 	// 12 19
					+ "TOTALEXCISE, " 		// 13 20
					+ "TOTALVAT, " 			// 14 21
					+ "TOTALALL, " 			// 15 22
					+ "TOTALCOST, " 		// 16 
					+ "DATEDOCUMENT)" 		// 17 29
					+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			boolean result = false;
				PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql);
				statement.setString(1,  fields[6]);
				statement.setString(2,  fields[8]);
				statement.setString(3,  fields[9]);
				statement.setString(4,  fields[12]);
				statement.setString(5,  fields[13]);
				statement.setString(6,  fields[14]);
				statement.setString(7, Status.valueRuOf(fields[14]));
				if(fields[15].trim().length() > 0){
					statement.setString(8,  InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(fields[15])));
				}else{
					statement.setString(8,  fields[15]);
				}
				if(fields[16].trim().length() > 0){
					statement.setString(9,  InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(fields[16])));
				}else{
					statement.setString(9,  fields[16]);
				}
				if(fields[17].trim().length() > 0){
					statement.setString(10,  InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(fields[17])));
				}else{
					statement.setString(10,  fields[17]);
				}
				statement.setString(11, fields[18]);
				if(fields[19].trim().length() > 0){
					statement.setString(12,  InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(fields[19])));
				}else{
					statement.setString(12,  fields[19]);
				}
				statement.setString(13, fields[20].replace(",", "."));
				statement.setString(14, fields[21].replace(",", "."));
				statement.setString(15, fields[22].replace(",", "."));
				statement.setString(16, String.format("%.3f",(Float.parseFloat(fields[22].replace(",", "."))-Float.parseFloat(fields[21].replace(",", "."))-Float.parseFloat(fields[20].replace(",", ".")))));
				if(fields[29].trim().length() > 0){
					statement.setString(17, fields[29]);
				}else{
					statement.setString(17, fields[29]);
				}
				statement.executeUpdate();
				result = true;
				return result;		
		}
		
	}
	
	public static class Lists{
		
		//список всех ЭСЧФ для обновления
		public static List<String> selectNumbersInvoice(){
			List<String> list = new ArrayList<String>();
			String sql = "SELECT NUMBERINVOICE FROM OUTCOMING";
			try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
				list.clear();
				ResultSet set = statement.executeQuery(sql);
				while(set.next()){
					list.add(set.getString(1).trim());
				}
				return list;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return null;			
			}
		}
		
		//список неподписанных ЭСЧФ для обновления
		public static List<String> selectNotSignedNumbersInvoice(){
			List<String> list = new ArrayList<String>();
			String sql = "SELECT NUMBERINVOICE FROM OUTCOMING WHERE STATUSINVOICEEN IS NOT NULL AND (STATUSINVOICEEN = 'COMPLETED' "
					+ "OR STATUSINVOICEEN = 'ON_AGREEMENT' OR STATUSINVOICEEN = 'IN_PROGRESS' OR STATUSINVOICEEN = 'NOT_FOUND')";
			try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
				list.clear();
				ResultSet set = statement.executeQuery(sql);
				while(set.next()){
					list.add(set.getString(1).trim());
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
			String sql = "SELECT UNP, DATECOMMISSION, NUMBERINVOICE, STATUSINVOICEEN, TOTALCOST, TOTALVAT, TOTALALL, DATEDOCUMENT FROM OUTCOMING WHERE STATUSINVOICEEN IS NOT NULL";
			try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
				list.clear();
				ResultSet set = statement.executeQuery(sql);
				String statusRu = "";
				while(set.next()){
					if(set.getString("STATUSINVOICEEN").trim().equals("COMPLETED_SIGNED")){
						statusRu = "Подписан";
					}else{
						statusRu = "Не подписан";
					}
					String dateDocument = "";
					if(set.getString("DATEDOCUMENT").length()>0){
						dateDocument = InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateReverseSmallDash(set.getString("DATEDOCUMENT").trim()));
					}
					list.add(new UnloadedInvoice.Builder()
							.setUnp(set.getString("UNP").trim())
							//.setDateCommission(InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateSmallDash(set.getString("DATECOMMISSION").trim())))
							.setDateCommission(new SimpleDateFormat("dd.MM.yyyy").parse(InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateReverseSmallDash(set.getString("DATECOMMISSION").trim()))))
							.setNumberInvoice(set.getString("NUMBERINVOICE").trim())
							.setStatusInvoiceRu(statusRu)
							.setTotalCost(set.getString("TOTALCOST").trim())
							.setTotalVat(set.getString("TOTALVAT").trim())
							.setTotalAll(set.getString("TOTALALL").trim())
							.setDateDocument(dateDocument.trim())
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
			String sql = "SELECT UNP, DATECOMMISSION, NUMBERINVOICE, STATUSINVOICEEN, TOTALCOST, TOTALVAT, TOTALALL, DATEDOCUMENT FROM OUTCOMING WHERE STATUSINVOICEEN IS NOT NULL AND DATECOMMISSION = '"+date.toString()+"' "+status+" ORDER BY strftime('%Y',DATECOMMISSION), DATE(DATECOMMISSION)";
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
					String dateDocument = "";
					if(set.getString("DATEDOCUMENT").length()>0){
						dateDocument = InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateReverseSmallDash(set.getString("DATEDOCUMENT").trim()));
					}
					list.add(new UnloadedInvoice.Builder()
							.setUnp(set.getString("UNP").trim())
							//.setDateCommission(InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateReverseSmallDash(set.getString("DATECOMMISSION").trim())))
							.setDateCommission(new SimpleDateFormat("dd.MM.yyyy").parse(InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateReverseSmallDash(set.getString("DATECOMMISSION").trim()))))
							.setNumberInvoice(set.getString("NUMBERINVOICE").trim())
							.setStatusInvoiceRu(statusRu)
							.setTotalCost(set.getString("TOTALCOST").trim())
							.setTotalVat(set.getString("TOTALVAT").trim())
							.setTotalAll(set.getString("TOTALALL").trim())
							.setDateDocument(dateDocument.trim())
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
			String sql = "SELECT UNP, DATECOMMISSION, NUMBERINVOICE, STATUSINVOICEEN, TOTALCOST, TOTALVAT, TOTALALL, DATEDOCUMENT FROM OUTCOMING WHERE STATUSINVOICEEN IS NOT NULL AND DATECOMMISSION BETWEEN '"+leftDate.toString()+"' AND '"+date.toString()+"' "+status;
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
					String dateDocument = "";
					if(set.getString("DATEDOCUMENT").length()>0){
						dateDocument = InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateReverseSmallDash(set.getString("DATEDOCUMENT").trim()));
					}
					list.add(new UnloadedInvoice.Builder()
							.setUnp(set.getString("UNP").trim())
							//.setDateCommission(InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateReverseSmallDash(set.getString("DATECOMMISSION").trim())))
							.setDateCommission(new SimpleDateFormat("dd.MM.yyyy").parse(InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateReverseSmallDash(set.getString("DATECOMMISSION").trim()))))
							.setNumberInvoice(set.getString("NUMBERINVOICE").trim())
							.setStatusInvoiceRu(statusRu)
							.setTotalCost(set.getString("TOTALCOST").trim())
							.setTotalVat(set.getString("TOTALVAT").trim())
							.setTotalAll(set.getString("TOTALALL").trim())
							.setDateDocument(dateDocument.trim())
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
		
	}
		
	public static class Date{
	
		//список годов на основе таблицы
		public static List<String> selectYearInvoice(){
			List<String> list = new ArrayList<String>();
			String sql = "SELECT strftime('%Y',DATECOMMISSION) as cYEAR FROM OUTCOMING GROUP BY cYEAR ORDER BY cYEAR DESC";
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
			String sql = "SELECT strftime('%Y',DATECOMMISSION) as cYEAR, strftime('%m',DATECOMMISSION) as cMONTH FROM OUTCOMING GROUP BY cYEAR, cMONTH HAVING cYEAR = '"+year+"' ORDER BY cMONTH DESC";
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
			String sql = "SELECT date(DATECOMMISSION,'start of month') AS startMonth FROM OUTCOMING GROUP BY date(DATECOMMISSION,'start of month') HAVING strftime('%Y',DATECOMMISSION) = '"+year+"' AND strftime('%m',DATECOMMISSION) = '"+month+"'";
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
			String sql = "SELECT date(DATECOMMISSION,'start of month','+1 month','-1 day') AS endMonth FROM OUTCOMING GROUP BY date(DATECOMMISSION,'start of month') HAVING strftime('%Y',DATECOMMISSION) = '"+year+"' AND strftime('%m',DATECOMMISSION) = '"+month+"'";
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
}
