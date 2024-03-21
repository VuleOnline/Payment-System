package service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import common.SessionManager;
import model.OrdersModel;
import utils.JDBCUtils;

public class DBOrderManipulationServ {
	
	private static final String INSERTorder ="INSERT INTO orders(SNo, fullname, address, city, refNo, recAcc, amount, comm, total, id)"
			+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SELECTorders ="SELECT * FROM orders WHERE id=? AND DATE(date_)=?";
	private static final String SELECTorderBySno ="SELECT * FROM orders WHERE SNo =? AND id=? AND DATE(date_)=?";
	private static final String COUNTorders = "SELECT COUNT(*) FROM orders WHERE id=? AND DATE(date_)=?";
	private static final String GETtotal = "SELECT SUM(total) FROM orders WHERE paid = 0 AND id = ? AND cancelled = 0"
			+ " AND DATE(date_) = ?";
	private static final String SELECTunpaidOrders = "SELECT * FROM orders WHERE paid=0 AND cancelled = 0 AND forwarded = 0 AND id = ? AND DATE(date_) = ?";
	private static final String SELECTorderForCancel = "SELECT * FROM orders WHERE SNo = ? AND paid=0 AND cancelled = 0 AND forwarded = 0 AND id=? AND DATE(date_) = ?";
	private static final String SELECTpaid = "SELECT * FROM orders WHERE paid=1 AND id=? AND cancelled = 0 AND forwarded = 0 AND DATE(date_)= ?";
	private static final String SELECTcancelled = "SELECT * FROM orders WHERE cancelled=1 AND id=? AND DATE(date_) = ?";
	private static final String SELECTforwarded = "SELECT * FROM orders WHERE forwarded=1 AND id=?  AND cancelled = 0 AND paid = 1 AND DATE(date_) = ?";
	private static final String SETpaid = "UPDATE orders SET paid = 1 WHERE paid = 0 AND id=? AND cancelled = 0 AND forwarded = 0 AND DATE(date_) = ?";
	private static final String SETforward = "UPDATE orders SET forwarded = 1 WHERE paid = 1 AND id=? AND cancelled = 0 AND forwarded = 0 AND DATE(date_) = ?";
	private static final String SETcancelled = "UPDATE orders SET cancelled = 1 WHERE cancelled = 0 AND SNo=? AND id=? AND DATE(date_) = ?";
	

	public static boolean insertOrder(int Sno, int id,  OrdersModel order) 

	{
		boolean rows = false;
		try
		(Connection con = JDBCUtils.getConnection();
		PreparedStatement pst = con.prepareStatement(INSERTorder);)
		{
			pst.setInt(1, Sno);
			pst.setString(2, order.getFullName());
			pst.setString(3, order.getAddress());
			pst.setString(4, order.getCity());
			pst.setString(5, order.getRefNo());
			pst.setString(6, order.getRecAcc());
			pst.setDouble(7, order.getAmount());
			pst.setDouble(8, order.getComm());
			pst.setDouble(9, order.getInvoiceAmt());
			pst.setInt(10, id);
			rows = pst.executeUpdate()>0;
		}catch(SQLException e) 
		{
			System.out.println("Neuspeo insert naloga");
			e.printStackTrace();
		}
		return rows;
	}
	public static ArrayList<OrdersModel> selectOrders(int id, LocalDate date) 
	{
		ArrayList<OrdersModel> orders = new ArrayList<>();
			try
			(Connection con = JDBCUtils.getConnection();
			PreparedStatement pst = con.prepareStatement(SELECTorders);)
			{
				System.out.println("slectorder upit "+SessionManager.getCurrSess());
			pst.setInt(1, id);
			pst.setDate(2, Date.valueOf(date));
			ResultSet rs = pst.executeQuery();
				while(rs.next()) 
				{
					int SNo =rs.getInt("SNo");
					String fullname = rs.getString("fullname");
					String address = rs.getString("address");
					String city = rs.getString("city");
					String refNo = rs.getString("refNo");
					String recAcc = rs.getString("recAcc");
					Double amount = rs.getDouble("amount");
					Double comm = rs.getDouble("comm");
					Double total = rs.getDouble("total");
					boolean cancelled = rs.getBoolean("cancelled");
					boolean forwarded = rs.getBoolean("forwarded");
					LocalDateTime date_ = rs.getTimestamp("date_").toLocalDateTime();
					int empid  = rs.getInt("id");
					boolean paid = rs.getBoolean("paid");
					OrdersModel order = new OrdersModel(SNo, fullname, address, city, refNo, recAcc, amount, comm, 
							total, cancelled, forwarded, date_, empid, paid);
					orders.add(order);
				}
			}catch(SQLException e) 
			{
				e.printStackTrace();
			}
			return orders;
	}
	public static int countOrders(int id, LocalDate date) 
	{
		int count = 0;
			try
			(Connection con = JDBCUtils.getConnection();
			PreparedStatement pst = con.prepareStatement(COUNTorders);)
			{
				System.out.println("slectorder upit "+SessionManager.getCurrSess());
			pst.setInt(1, id);
			pst.setDate(2, Date.valueOf(date));
			ResultSet rs = pst.executeQuery();
				if(rs.next()) 
				{
					count = rs.getInt(1);
				}
			}catch(SQLException e) 
			{
				e.printStackTrace();
			}
			return count;
	}
	public static OrdersModel getOrderBySno(int Sno, int id, LocalDate date) 
	{
		OrdersModel order = null;
			try
			(Connection con = JDBCUtils.getConnection();
			PreparedStatement pst = con.prepareStatement(SELECTorderBySno);)
			{
				System.out.println("slectorder upit "+SessionManager.getCurrSess());
			pst.setInt(1, Sno);
			pst.setInt(2, id);
			pst.setDate(3, Date.valueOf(date));
			ResultSet rs = pst.executeQuery();
				while(rs.next()) 
				{
					int SNo =rs.getInt("SNo");
					String fullname = rs.getString("fullname");
					String address = rs.getString("address");
					String city = rs.getString("city");
					String refNo = rs.getString("refNo");
					String recAcc = rs.getString("recAcc");
					Double amount = rs.getDouble("amount");
					Double comm = rs.getDouble("comm");
					Double total = rs.getDouble("total");
					boolean cancelled = rs.getBoolean("cancelled");
					boolean forwarded = rs.getBoolean("forwarded");
					LocalDateTime date_ = rs.getTimestamp("date_").toLocalDateTime();
					int empid  = rs.getInt("id");
					boolean paid = rs.getBoolean("paid");
					order = new OrdersModel(SNo, fullname, address, city, refNo, recAcc, amount, comm, 
							total, cancelled, forwarded, date_, empid, paid);
				}
			}catch(SQLException e) 
			{
				e.printStackTrace();
			}
			return order;
	}


	public static double getTotal(int id, LocalDate date) 
	{
		double totalSum = 0.0;
		try (Connection con = JDBCUtils.getConnection();
			     PreparedStatement pst = con.prepareStatement(GETtotal)) {
			pst.setInt(1, id);
			pst.setDate(2, Date.valueOf(date));
			ResultSet rs = pst.executeQuery();
			while(rs.next()) 
			{
				totalSum = rs.getDouble(1);
			}
		 } catch (SQLException e) {
		        e.printStackTrace();
		    
		}
		System.out.println("TotalSum je:" +totalSum);
		return totalSum;
	}
	public static ArrayList<OrdersModel> getUnpaidOrders(int id, LocalDate date)
	{
		ArrayList<OrdersModel> orders = new ArrayList<>();
		try
		(Connection con = JDBCUtils.getConnection();
		PreparedStatement pst = con.prepareStatement(SELECTunpaidOrders)){ 
			pst.setInt(1, id);
			pst.setDate(2, Date.valueOf(date));
			ResultSet rs = pst.executeQuery();
			while(rs.next()) 
			{
				int SNo =rs.getInt("SNo");
				String fullname = rs.getString("fullname");
				String address = rs.getString("address");
				String city = rs.getString("city");
				String refNo = rs.getString("refNo");
				String recAcc = rs.getString("recAcc");
				Double amount = rs.getDouble("amount");
				Double comm = rs.getDouble("comm");
				Double total = rs.getDouble("total");
				boolean cancelled = rs.getBoolean("cancelled");
				boolean forwarded = rs.getBoolean("forwarded");
				LocalDateTime date_ = rs.getTimestamp("date_").toLocalDateTime();
				int empid  = rs.getInt("id");
				boolean paid = rs.getBoolean("paid");
				OrdersModel order = new OrdersModel(SNo, fullname, address, city, refNo, recAcc, amount, comm, 
						total, cancelled, forwarded, date_, empid, paid);
				orders.add(order);
			}
		}catch(SQLException e) 
		{
			e.printStackTrace();
		}
		return orders;
				
	}
	public static ArrayList<OrdersModel> getPaid(int id, LocalDate date) 
	{
		ArrayList<OrdersModel> paidOrders = new ArrayList<>();
		try(
			Connection con = JDBCUtils.getConnection();
				PreparedStatement pst = con.prepareStatement(SELECTpaid)){
			pst.setInt(1, id);
			pst.setDate(2, Date.valueOf(date));
			ResultSet rs = pst.executeQuery();
			while(rs.next()) 
			{
				int SNo =rs.getInt("SNo");
				String fullname = rs.getString("fullname");
				String address = rs.getString("address");
				String city = rs.getString("city");
				String refNo = rs.getString("refNo");
				String recAcc = rs.getString("recAcc");
				Double amount = rs.getDouble("amount");
				Double comm = rs.getDouble("comm");
				Double total = rs.getDouble("total");
				boolean cancelled = rs.getBoolean("cancelled");
				boolean forwarded = rs.getBoolean("forwarded");
				LocalDateTime date_ = rs.getTimestamp("date_").toLocalDateTime();
				int empid  = rs.getInt("id");
				boolean paid = rs.getBoolean("paid");
				OrdersModel order = new OrdersModel(SNo, fullname, address, city, refNo, recAcc, amount, comm, 
						total, cancelled, forwarded, date_, empid, paid);
				paidOrders.add(order);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Paid orders catch block");
		}
		return paidOrders;
	}
	public static ArrayList<OrdersModel> getCancelled(int id, LocalDate date) 
	{
		ArrayList<OrdersModel> cancelledOrders = new ArrayList<>();
		try(
			Connection con = JDBCUtils.getConnection();
				PreparedStatement pst = con.prepareStatement(SELECTcancelled)){
			pst.setInt(1, id);
			pst.setDate(2, Date.valueOf(date));
			ResultSet rs = pst.executeQuery();
			while(rs.next()) 
			{
				int SNo =rs.getInt("SNo");
				String fullname = rs.getString("fullname");
				String address = rs.getString("address");
				String city = rs.getString("city");
				String refNo = rs.getString("refNo");
				String recAcc = rs.getString("recAcc");
				Double amount = rs.getDouble("amount");
				Double comm = rs.getDouble("comm");
				Double total = rs.getDouble("total");
				boolean cancelled = rs.getBoolean("cancelled");
				boolean forwarded = rs.getBoolean("forwarded");
				LocalDateTime date_ = rs.getTimestamp("date_").toLocalDateTime();
				int empid  = rs.getInt("id");
				boolean paid = rs.getBoolean("paid");
				OrdersModel order = new OrdersModel(SNo, fullname, address, city, refNo, recAcc, amount, comm, 
						total, cancelled, forwarded, date_, empid, paid);
				cancelledOrders.add(order);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Cancelled orders catch block");
		}
		return cancelledOrders;
	}
	public static ArrayList<OrdersModel> getForwarded(int id, LocalDate date) 
	{
		ArrayList<OrdersModel> forwardedOrders = new ArrayList<>();
		try(
			Connection con = JDBCUtils.getConnection();
				PreparedStatement pst = con.prepareStatement(SELECTforwarded)){
			pst.setInt(1, id);
			pst.setDate(2, Date.valueOf(date));
			ResultSet rs = pst.executeQuery();
			while(rs.next()) 
			{
				int SNo =rs.getInt("SNo");
				String fullname = rs.getString("fullname");
				String address = rs.getString("address");
				String city = rs.getString("city");
				String refNo = rs.getString("refNo");
				String recAcc = rs.getString("recAcc");
				Double amount = rs.getDouble("amount");
				Double comm = rs.getDouble("comm");
				Double total = rs.getDouble("total");
				boolean cancelled = rs.getBoolean("cancelled");
				boolean forwarded = rs.getBoolean("forwarded");
				LocalDateTime date_ = rs.getTimestamp("date_").toLocalDateTime();
				int empid  = rs.getInt("id");
				boolean paid = rs.getBoolean("paid");
				OrdersModel order = new OrdersModel(SNo, fullname, address, city, refNo, recAcc, amount, comm, 
						total, cancelled, forwarded, date_, empid, paid);
				forwardedOrders.add(order);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Forwarded orders catch block");
		}
		return forwardedOrders;
	}
	public static int setPaid(int id, LocalDate date) 
	{
		int row =0;
		try(
			Connection con = JDBCUtils.getConnection();
				PreparedStatement pst = con.prepareStatement(SETpaid)){
			pst.setInt(1, id);
			pst.setDate(2, Date.valueOf(date));
			row = pst.executeUpdate();
			System.out.println("no of paid: " +row);
		}catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Switch paid orders catch block");
		}
		return row;
	}
	public static void forwardOrders(int id, LocalDate date) 
	{
		try(
			Connection con = JDBCUtils.getConnection();
				PreparedStatement pst = con.prepareStatement(SETforward)){
			pst.setInt(1, id);
			pst.setDate(2, Date.valueOf(date));
			int row = pst.executeUpdate();
			System.out.println("no of forwarded: " +row);
		}catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Forward orders catch block");
		}
	}
	public static ArrayList<OrdersModel> selectOrderForCancel(int i, int id, LocalDate date) 
	{
		ArrayList<OrdersModel> orders = new ArrayList<>();
			try
			(Connection con = JDBCUtils.getConnection();
			PreparedStatement pst = con.prepareStatement(SELECTorderForCancel);)
			{
			pst.setInt(1, i);
			pst.setInt(2, id);
			pst.setDate(3, Date.valueOf(date));
			ResultSet rs = pst.executeQuery();
				while(rs.next()) 
				{
					int SNo =rs.getInt("SNo");
					String fullname = rs.getString("fullname");
					String address = rs.getString("address");
					String city = rs.getString("city");
					String refNo = rs.getString("refNo");
					String recAcc = rs.getString("recAcc");
					Double amount = rs.getDouble("amount");
					Double comm = rs.getDouble("comm");
					Double total = rs.getDouble("total");
					boolean cancelled = rs.getBoolean("cancelled");
					boolean forwarded = rs.getBoolean("forwarded");
					LocalDateTime date_ = rs.getTimestamp("date_").toLocalDateTime();
					int empid  = rs.getInt("id");
					boolean paid = rs.getBoolean("paid");
					OrdersModel order = new OrdersModel(SNo, fullname, address, city, refNo, recAcc, amount, comm, 
							total, cancelled, forwarded, date_, empid, paid);
					orders.add(order);
				}
			}catch(SQLException e) 
			{
				e.printStackTrace();
			}
			return orders;
	}
	public static int setCancelled(int i, int id, LocalDate date) 
	{
		int row =0;
		try(
			Connection con = JDBCUtils.getConnection();
				PreparedStatement pst = con.prepareStatement(SETcancelled)){
			pst.setInt(1, i);
			pst.setInt(2, id);
			pst.setDate(3, Date.valueOf(date));
			row = pst.executeUpdate();
			System.out.println("no of cancelled: " +row);
		}catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Set cancelled order catch block");
		}
		return row;
	}
	

}
