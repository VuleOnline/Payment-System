package service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import common.SessionManager;
import model.AggTransactions;
import model.DenomEntryModel;
import model.OrdersModel;
import model.SafeTransfers;
import utils.JDBCUtils;

public class DBMoneyManipulationServ {

	private static final String INSERTchange = "INSERT INTO aggregate_transactions (SNo, comm, total, recMoney, changes, id)VALUES "
			+ "(?,?,?,?,?,?)"; 
	private static final String SELECTall = "SELECT * FROM aggregate_transactions WHERE id=? AND DATE(date_) = ?";
	private static final String SELECTbySno = "SELECT * FROM aggregate_transactions WHERE SNo=? AND id=? AND DATE(date_) = ?";
	private static final String SelectDenomSum = "SELECT d_sum FROM denomination WHERE d_denom_value = ? AND d_empid=? AND d_date_=?";
	private static final String COUNTcashRegSum = "SELECT SUM(d_sum) AS total_sum FROM denomination WHERE d_empid=? and d_date_ = ?";
	private static final String SubDenom = "UPDATE denomination SET d_quantity = d_quantity-? WHERE d_denom_value =? AND d_empid = ? AND d_date_ = ?";
	private static final String SELECTdenomQuant = "SELECT d_quantity FROM denomination WHERE d_denom_value = ? AND d_empid = ? AND DATE(d_date_) = ?";
	
	public static boolean insertBilling(AggTransactions bill) 
	{
		boolean rows = false;
		try
		(Connection con = JDBCUtils.getConnection();
		PreparedStatement pst = con.prepareStatement(INSERTchange);)
		{
			pst.setString(1, bill.getSNo());
			pst.setDouble(2, bill.getComm());
			pst.setDouble(3, bill.getTotal());
			pst.setDouble(4, bill.getRcvd());
			pst.setDouble(5, bill.getChange());
			pst.setInt(6, SessionManager.getEmpIdBySession(SessionManager.getCurrSess()));
			rows = pst.executeUpdate()>0;
		}catch(SQLException e) 
		{
			System.out.println("Order insertation failed.");
			e.printStackTrace();
		}
		return rows;
	}
	public static boolean reverseOrderInAgg(OrdersModel order) 
	{
		boolean rows = false;
		try
		(Connection con = JDBCUtils.getConnection();
		PreparedStatement pst = con.prepareStatement("UPDATE aggregate_transactions"
				+ " SET reversed  = CASE"
				+ " WHEN reversed IS NULL THEN ?"
				+ " ELSE CONCAT(reversed , '-', ?)"
				+ " END"
				+ " WHERE FIND_IN_SET(?, REPLACE(SNo, '-', ',')) > 0"
				+ " AND id = ?"
				+ " AND DATE(date_) = ?");)
		{
		    pst.setString(1, "" + order.getsNo());
		    pst.setString(2, "" + order.getsNo());
		    pst.setString(3, "" + order.getsNo());
			pst.setDouble(4, order.getEmpid());
			pst.setDate(5, Date.valueOf(order.getOrderDate().toLocalDate()));
			rows = pst.executeUpdate()>0;
			
		}catch(SQLException e) 
		{
			System.out.println("Oreder reversion failed");
			e.printStackTrace();
		}
		return rows;
	}
	public static ArrayList<String> getSnos(int id,  LocalDate date) 
	{
		 ArrayList<String> sno = new ArrayList<>();
		try
		(Connection con = JDBCUtils.getConnection();
		PreparedStatement pst = con.prepareStatement(SELECTall);)
		{
			System.out.println("slectorder upit "+SessionManager.getCurrSess());
		pst.setInt(1, id);
		pst.setDate(2, Date.valueOf(date));
		ResultSet rs = pst.executeQuery();
			while(rs.next()) 
			{
				String SNo =rs.getString("SNo");
				sno.add(SNo);
			}
		}catch(SQLException e) 
		{
			e.printStackTrace();
		}
		return sno;

	}
	public static AggTransactions getOrder(String Sno, int id,  LocalDate date) 
	{
		 AggTransactions change = null;
		try
		(Connection con = JDBCUtils.getConnection();
		PreparedStatement pst = con.prepareStatement(SELECTbySno);)
		{
		pst.setString(1, Sno);
		pst.setInt(2, id);
		pst.setDate(3, Date.valueOf(date));
		ResultSet rs = pst.executeQuery();
			while(rs.next()) 
			{
				double total = rs.getDouble("total");
				double comm = rs.getDouble("comm");
				double recMoney = rs.getDouble("recMoney");
				double changes = rs.getDouble("changes");
				change = new AggTransactions(total, comm, recMoney, changes);
			}
		}catch(SQLException e) 
		{
			System.out.println("DBChangeManipulationServ, getOrder catch block");
			e.printStackTrace();
		}
	return change;

	}
	public static boolean insertDenomEntry(DenomEntryModel denomEntry) 
	{
		boolean rows = false;
		try
		(Connection con = JDBCUtils.getConnection();
		PreparedStatement pst = con.prepareStatement("INSERT INTO denomination (d_denom_value, d_quantity, d_empid, d_date_) VALUES (?, ?, ?, ?)"
				+ " ON DUPLICATE KEY UPDATE "
				+ "d_quantity = d_quantity + VALUES(d_quantity)");)	
		{
			pst.setInt(1, denomEntry.getDenom_value());
			pst.setInt(2, denomEntry.getQuantity());
			pst.setInt(3, denomEntry.getEmpId());
			pst.setDate(4, Date.valueOf(denomEntry.getDate()));
			
			rows = pst.executeUpdate()>0;
			
			
		}catch(SQLException e) 
		{
			System.out.println("Insertation of denom failed.");
			e.printStackTrace();
		}
		
		return rows;
	}
	public static int geDenomQuant(DenomEntryModel denom_obj) 
	{
		int denom_quantity = 0;
		try
		(Connection con = JDBCUtils.getConnection();
		PreparedStatement pst = con.prepareStatement(SELECTdenomQuant);)
		{
		pst.setInt(1, denom_obj.getDenom_value());
		pst.setInt(2, denom_obj.getEmpId());
		pst.setDate(3, Date.valueOf(denom_obj.getDate()));
		ResultSet rs = pst.executeQuery();
			while(rs.next()) 
			{
				denom_quantity = rs.getInt("d_quantity");
			}
		}catch(SQLException e) 
		{
			e.printStackTrace();
		}
		return denom_quantity;

	}

	public static boolean subFromDenom(DenomEntryModel denomEntry) 
	{
		boolean rows = false;
		try
		(Connection con = JDBCUtils.getConnection();
		PreparedStatement pst = con.prepareStatement(SubDenom);)
		{
			pst.setInt(1, denomEntry.getQuantity());
			pst.setInt(2, denomEntry.getDenom_value());
			pst.setInt(3, denomEntry.getEmpId());
			pst.setDate(4, Date.valueOf(denomEntry.getDate()));
			
			rows = pst.executeUpdate()>0;
			
			
		}catch(SQLException e) 
		{
			System.out.println("Neuspeo insert apoena");
			e.printStackTrace();
		}
		
		return rows;
	}
	public static boolean insertCashReqi(SafeTransfers st) 
	{
		boolean rows = false;
		try
		(Connection con = JDBCUtils.getConnection();
		PreparedStatement pst = con.prepareStatement("INSERT INTO safe_transfers (st_denom_value, st_quantity, st_empid, st_date_, st_withdrawal) VALUES (?, ?, ?, ?, ?)"
				+ " ON DUPLICATE KEY UPDATE "
				+ "st_quantity = st_quantity + VALUES(st_quantity)");)	
		{
			
			pst.setInt(1, st.getSt_denom_value());
			pst.setInt(2, st.getSt_quantity());
			pst.setInt(3, st.getSt_empId());
			pst.setDate(4, Date.valueOf(st.getSt_date_()));
			pst.setBoolean(5, st.isSt_withdrawal());
			
			rows = pst.executeUpdate()>0;
			
			
		}catch(SQLException e) 
		{
			System.out.println("Failed cash requisition.");
			e.printStackTrace();
		}
		
		return rows;
	}
	public static double getDenomSum(double value, int id, LocalDate date) 
	{
		double sum = 0.0;
		try
		(Connection con = JDBCUtils.getConnection();
		PreparedStatement pst = con.prepareStatement(SelectDenomSum);)
		{
			pst.setDouble(1, value);
			pst.setInt(2, id);
			pst.setDate(3, Date.valueOf(date));
			ResultSet rs = pst.executeQuery();
	        if (rs.next()) {
	            sum = rs.getDouble(1);
	        }
		}catch(SQLException e) 
		{
			System.out.println("Sum gettin failed.");
			e.printStackTrace();
		}
		
		return sum;
	}
	
	public static double getCashRegSum(int id, LocalDate date) 
	{
		double sum = 0.0;
		try
		(Connection con = JDBCUtils.getConnection();
		PreparedStatement pst = con.prepareStatement(COUNTcashRegSum);)
		{
			pst.setInt(1, id);
			pst.setDate(2, Date.valueOf(date));
			ResultSet rs = pst.executeQuery();
	        if (rs.next()) {
	            sum = rs.getDouble(1);
	        }
		}catch(SQLException e) 
		{
			System.out.println("Cash reg sum gettin failed.");
			e.printStackTrace();
		}
		
		return sum;
	}
	public static boolean insertCashDepo(DenomEntryModel denomEntry, int empId, LocalDate date) {
		boolean success = false;
		try
		(Connection con = JDBCUtils.getConnection();
		PreparedStatement pst = con.prepareStatement("INSERT INTO safe_transfers (st_denom_value, st_quantity, st_empid, st_date_, st_withdrawal) VALUES (?, ?, ?, ?, 0)"
				+ " ON DUPLICATE KEY UPDATE "
				+ "st_quantity = IF(st_withdrawal = VALUES(st_withdrawal), st_quantity + VALUES(st_quantity)");)
		{
			pst.setInt(1, denomEntry.getDenom_value());
			pst.setInt(2, denomEntry.getQuantity());
			pst.setInt(3, empId);
			pst.setDate(4, Date.valueOf(date));
	        int rows = pst.executeUpdate();
	        success = rows > 0;
	        
		}
	catch(SQLException e) 
		{
			System.out.println("Denom update failed.");
			e.printStackTrace();
		}
		
		return success;
		
	}
	
	
	
	
}
