package by.gomelagro.outcoming.gui.db;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import by.gomelagro.outcoming.format.date.InvoiceDateFormat;
import by.gomelagro.outcoming.gui.db.files.data.UnloadedInvoice;
import by.gomelagro.outcoming.gui.frames.report.component.ResultFont;
import by.gomelagro.outcoming.status.Status;

public class WorkingOutcomingTable {
	
	//���������� ���� ���� � �������
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
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"������",JOptionPane.ERROR_MESSAGE);
			return -1;
		}
	}
	
	//���������� ������������ ���� � ������� 
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
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"������",JOptionPane.ERROR_MESSAGE);
			return -1;
		}
	}	
	
	//���������� ���� ���� � ������� �� ���
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
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"������",JOptionPane.ERROR_MESSAGE);
			return -1;
		}
	}
	
	//���������� ����������� ���� � �������
	public static int getCountCompleted(String date){
		String sql = "SELECT COUNT(*) AS COUNT FROM OUTCOMING WHERE STATUSINVOICEEN IS NOT NULL AND STATUSINVOICEEN = 'COMPLETED_SIGNED' AND strftime('%Y',DATECOMMISSION) = '"+date+"'";
		try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
			int count = -1;
			ResultSet set = statement.executeQuery();
			while(set.next()){
				count = set.getInt(1);
			}
			return count;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"������",JOptionPane.ERROR_MESSAGE);
			return -1;
		}
	}
	
	
	//���������� ������������� ���� � �������
	public static int getCountNoCompleted(String date){
		String sql = "SELECT COUNT(*) AS COUNT FROM OUTCOMING WHERE STATUSINVOICEEN IS NOT NULL AND STATUSINVOICEEN = 'COMPLETED' AND strftime('%Y',DATECOMMISSION) = '"+date+"'";
		try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
			int count = -1;
			ResultSet set = statement.executeQuery();
			while(set.next()){
				count = set.getInt(1);
			}
			return count;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"������",JOptionPane.ERROR_MESSAGE);
			return -1;
		}
	}
	
	//���������� ��������� ����
	public static int getCountCancelled(String date){
		String sql = "SELECT COUNT(*) AS COUNT FROM OUTCOMING WHERE STATUSINVOICEEN IS NOT NULL AND (STATUSINVOICEEN = 'CANCELLED' OR STATUSINVOICEEN = 'ON_AGREEMENT_CANCEL') AND strftime('%Y',DATECOMMISSION) = '"+date+"'";
		try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
			int count = -1;
			ResultSet set = statement.executeQuery();
			while(set.next()){
				count = set.getInt(1);
			}
			return count;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"������",JOptionPane.ERROR_MESSAGE);
			return -1;
		}
	}
	
	//���������� ���� ��������������� ������� � ������� 
	public static int getCountUndetermined(String date){
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
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"������",JOptionPane.ERROR_MESSAGE);
			return -1;
		}
	}
	
	//���������� ����
	public static boolean insertIncoming(String[] fields) throws SQLException, ParseException{
		String sql = "INSERT INTO OUTCOMING(UNP, CODECOUNTRY, NAME, NUMBERINVOICE, TYPEINVOICE, STATUSINVOICERU, STATUSINVOICEEN, DATEISSUE, DATECOMMISSION, "
				+ "DATESIGNATURE, BYINVOICE, DATECANCELLATION, TOTALEXCISE, TOTALVAT, TOTALALL, TOTALCOST) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		boolean result = false;
			PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql);
			statement.setString(1,  fields[5]);
			statement.setString(2,  fields[6]);
			statement.setString(3,  fields[7]);
			statement.setString(4,  fields[10]);
			statement.setString(5,  fields[11]);
			statement.setString(6,  fields[12]);
			statement.setString(7, Status.valueRuOf(fields[12]));
			if(fields[13].trim().length() > 0){
				statement.setString(8,  InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(fields[13])));
			}else{
				statement.setString(8,  fields[13]);
			}
			if(fields[14].trim().length() > 0){
				statement.setString(9,  InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(fields[14])));
			}else{
				statement.setString(9,  fields[14]);
			}
			if(fields[15].trim().length() > 0){
				statement.setString(10,  InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(fields[15])));
			}else{
				statement.setString(10,  fields[15]);
			}
			statement.setString(11, fields[16]);
			if(fields[17].trim().length() > 0){
				statement.setString(12,  InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(fields[17])));
			}else{
				statement.setString(12,  fields[17]);
			}
			statement.setString(13, fields[18].replace(",", "."));
			statement.setString(14, fields[19].replace(",", "."));
			statement.setString(15, fields[20].replace(",", "."));
			statement.setString(16, String.format("%.3f",(Float.parseFloat(fields[20].replace(",", "."))-Float.parseFloat(fields[19].replace(",", "."))-Float.parseFloat(fields[18].replace(",", ".")))));
			statement.executeUpdate();
			result = true;
			return result;		
	}
	
	//������ ���� ���� ��� ����������
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
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"������",JOptionPane.ERROR_MESSAGE);
			return null;			
		}
	}
	
	//������ ������������� ���� ��� ����������
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
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"������",JOptionPane.ERROR_MESSAGE);
			return null;			
		}
	}
	
	//������ ���� ��� ������
	public static List<UnloadedInvoice> selectSignedNumbersInvoice(){
		List<UnloadedInvoice> list = new ArrayList<UnloadedInvoice>();
		String sql = "SELECT UNP, DATECOMMISSION, NUMBERINVOICE, STATUSINVOICEEN, TOTALCOST, TOTALVAT, TOTALALL FROM OUTCOMING WHERE STATUSINVOICEEN IS NOT NULL";
		try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
			list.clear();
			ResultSet set = statement.executeQuery(sql);
			String statusRu = "";
			while(set.next()){
				if(set.getString("STATUSINVOICEEN").trim().equals("COMPLETED_SIGNED")){
					statusRu = "��������";
				}else{
					statusRu = "�� ��������";
				}
				
				list.add(new UnloadedInvoice.Builder()
						.setUnp(set.getString("UNP").trim())
						.setDateCommission(InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateSmallDash(set.getString("DATECOMMISSION").trim())))
						.setNumberInvoice(set.getString("NUMBERINVOICE").trim())
						.setStatusInvoiceRu(statusRu)
						.setTotalCost(set.getString("TOTALCOST").trim())
						.setTotalVat(set.getString("TOTALVAT").trim())
						.setTotalAll(set.getString("TOTALALL").trim())
						.build());
			}
			return list;
		} catch (SQLException | ParseException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"������",JOptionPane.ERROR_MESSAGE);
			return null;			
		}
	}
		
	//������ ���� ��� ������ �� ����
	public static List<UnloadedInvoice> selectSignedNumbersInvoiceAtDate(Date date, Comparator<UnloadedInvoice> comparator, String status){
		List<UnloadedInvoice> list = new ArrayList<UnloadedInvoice>();
		String sql = "SELECT UNP, DATECOMMISSION, NUMBERINVOICE, STATUSINVOICEEN, TOTALCOST, TOTALVAT, TOTALALL FROM OUTCOMING WHERE STATUSINVOICEEN IS NOT NULL AND DATECOMMISSION = '"+date.toString()+"' "+status;
		try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
			list.clear();
			ResultSet set = statement.executeQuery(sql);
			String statusRu = "";
			Color color = Color.BLACK;
			while(set.next()){
				if(set.getString("STATUSINVOICEEN").trim().equals("COMPLETED_SIGNED")){
					statusRu = "��������";
					color = ResultFont.getGreen();
				}else if((set.getString("STATUSINVOICEEN").trim().equals("CANCELLED"))||(set.getString("STATUSINVOICEEN").trim().equals("ON_AGREEMENT_CANCELLED"))){
					statusRu = "�����������";
					color = ResultFont.getBlack();
				}
				else{
					statusRu = "�� ��������";
					color = ResultFont.getRed();
				}
				list.add(new UnloadedInvoice.Builder()
						.setUnp(set.getString("UNP").trim())
						.setDateCommission(InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateReverseSmallDash(set.getString("DATECOMMISSION").trim())))
						.setNumberInvoice(set.getString("NUMBERINVOICE").trim())
						.setStatusInvoiceRu(statusRu)
						.setTotalCost(set.getString("TOTALCOST").trim())
						.setTotalVat(set.getString("TOTALVAT").trim())
						.setTotalAll(set.getString("TOTALALL").trim())
						.setColor(color)
						.build());
			}
			Collections.sort(list, comparator);
			return list;
		} catch (SQLException | ParseException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"������",JOptionPane.ERROR_MESSAGE);
			return null;			
		}
	}
	
	//������ ���� ��� ������ �� ������
	public static List<UnloadedInvoice> selectSignedNumbersInvoiceAtBetween(Date leftDate, Date rightDate, Comparator<UnloadedInvoice> comparator, String status){
		List<UnloadedInvoice> list = new ArrayList<UnloadedInvoice>();
		String sql = "SELECT UNP, DATECOMMISSION, NUMBERINVOICE, STATUSINVOICEEN, TOTALCOST, TOTALVAT, TOTALALL FROM OUTCOMING WHERE STATUSINVOICEEN IS NOT NULL AND DATECOMMISSION BETWEEN '"+leftDate.toString()+"' AND '"+rightDate.toString()+"' "+status;
		try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
			list.clear();
			ResultSet set = statement.executeQuery(sql);
			String statusRu = "";
			Color color = Color.BLACK;
			while(set.next()){
				if(set.getString("STATUSINVOICEEN").trim().equals("COMPLETED_SIGNED")){
					statusRu = "��������";
					color = ResultFont.getGreen();
				}else if((set.getString("STATUSINVOICEEN").trim().equals("CANCELLED"))||(set.getString("STATUSINVOICEEN").trim().equals("ON_AGREEMENT_CANCELLED"))){
					statusRu = "�����������";
					color = ResultFont.getBlack();
				}
				else{
					statusRu = "�� ��������";
					color = ResultFont.getRed();
				}
				list.add(new UnloadedInvoice.Builder()
						.setUnp(set.getString("UNP").trim())
						.setDateCommission(InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateReverseSmallDash(set.getString("DATECOMMISSION").trim())))
						.setNumberInvoice(set.getString("NUMBERINVOICE").trim())
						.setStatusInvoiceRu(statusRu)
						.setTotalCost(set.getString("TOTALCOST").trim())
						.setTotalVat(set.getString("TOTALVAT").trim())
						.setTotalAll(set.getString("TOTALALL").trim())
						.setColor(color)
						.build());
			}
			Collections.sort(list, comparator);
			return list;
		} catch (SQLException | ParseException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"������",JOptionPane.ERROR_MESSAGE);
			return null;			
		}
	}
	
	
	//������ ����� �� ������ �������
	public static List<String> selectYearInvoice(){
		List<String> list = new ArrayList<String>();
		String sql = "SELECT strftime('%Y',DATECOMMISSION) as cYEAR FROM OUTCOMING GROUP BY cYEAR ORDER BY cYEAR";
		try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
			ResultSet set = statement.executeQuery(sql);
			while(set.next()){
				list.add(set.getString("cYEAR"));
			}
			return list;
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"������",JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
	}

	//���������� �������� ����
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
	
	//���������� �������� ��� �������� �����
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
	
	//������� ����� ����� � ���� ������
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
}
