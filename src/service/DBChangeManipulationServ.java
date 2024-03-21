package service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import common.SessionManager;
import model.Change;
import utils.JDBCUtils;

public class DBChangeManipulationServ {

	private static final String INSERTchange = "INSERT INTO billing (SNo, comm, total, recMoney, changes, id)VALUES "
			+ "(?,?,?,?,?,?)";
	private static final String SELECTall = "SELECT * FROM billing WHERE id=? AND DATE(date_) = ?";
	private static final String SELECTbySno = "SELECT * FROM billing WHERE SNo=? AND id=? AND DATE(date_) = ?";
	
	public static boolean insertBilling(Change bill) 

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
			System.out.println("Neuspeo insert naloga");
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
	public static Change getOrder(String Sno, int id,  LocalDate date) 
	{
		 Change change = null;
		try
		(Connection con = JDBCUtils.getConnection();
		PreparedStatement pst = con.prepareStatement(SELECTbySno);)
		{
			System.out.println("slectorder upit "+SessionManager.getCurrSess());
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
				change = new Change(total, comm, recMoney, changes);
			}
		}catch(SQLException e) 
		{
			System.out.println("DBChangeManipulationServ, getOrder catch block");
		}
	return change;

	}
	
}
