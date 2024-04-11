package service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import model.AggTransactions;
import model.OrdersModel;
import utils.JDBCUtils;

public class DBOrderManipulationServ {
	
	private static final String INSERTorder ="INSERT INTO orders(SNo, fullname, address, city, refNo, recAcc, amount, comm, total, state, id)"
			+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SELECTorders ="SELECT * FROM orders WHERE id=? AND DATE(date_)=?";
	private static final String SELECTorderBySno ="SELECT * FROM orders WHERE SNo =? AND id=? AND DATE(date_)=?";
	private static final String COUNTorders = "SELECT COUNT(*) FROM orders WHERE id=? AND DATE(date_)=?";
	private static final String GETtotal = "SELECT SUM(total) FROM orders WHERE state = 0 AND id = ?"
			+ " AND DATE(date_) = ?";
	private static final String SELECTunpaidOrders = "SELECT * FROM orders WHERE state=0 AND id = ? AND DATE(date_) = ?";
	private static final String SELECTtoBeReversed = "SELECT * FROM orders WHERE reversed = 1 AND state = 1 AND id=? AND DATE(date_) = ?";
	private static final String SELECTpaid = "SELECT * FROM orders WHERE state = 1 AND (reversed = 1 OR reversed = 0) AND id=? AND DATE(date_)= ?";
	private static final String SELECTcancelled = "SELECT * FROM orders WHERE state = 3 AND id=? AND DATE(date_) = ?";
	private static final String SELECTforwarded = "SELECT * FROM orders WHERE state = 2 AND id=? AND DATE(date_) = ?";
	private static final String SETpaid = "UPDATE orders SET state = 1 WHERE state = 0 AND id=? AND DATE(date_) = ?";
	private static final String SETforward = "UPDATE orders SET state = 2 WHERE state = 1 AND id=? AND DATE(date_) = ?";
	private static final String SETcancelled = "UPDATE orders SET state = 3 WHERE state = 0 AND SNo=? AND id=? AND DATE(date_) = ?";
	private static final String SETtoBeReversed = "UPDATE orders SET reversed = 1, state = 1 WHERE reversed = 0 AND state = 2 AND SNo=? AND id=? AND DATE(date_) = ?";
	private static final String SELECTreversed = "SELECT * FROM orders WHERE state = 2 AND reversed =1 AND id=? AND DATE(date_) = ?";
	
	

	public static boolean insertOrder(OrdersModel order) 

	{
		boolean rows = false;
		try
		(Connection con = JDBCUtils.getConnection();
		PreparedStatement pst = con.prepareStatement(INSERTorder);)
		{
			pst.setInt(1, order.getsNo());
			pst.setString(2, order.getFullName());
			pst.setString(3, order.getAddress());
			pst.setString(4, order.getCity());
			pst.setString(5, order.getRefNo());
			pst.setString(6, order.getRecAcc());
			pst.setDouble(7, order.getAmount());
			pst.setDouble(8, order.getComm());
			pst.setDouble(9, order.getInvoiceAmt());
			pst.setInt(10, order.getState());
			pst.setInt(11, order.getEmpid());
			rows = pst.executeUpdate()>0;
		}catch(SQLException e) 
		{
			System.out.println("Order insertation failed.");
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
					LocalDateTime date_ = rs.getTimestamp("date_").toLocalDateTime();
					int state = rs.getInt("state");
					boolean reversed = rs.getBoolean("reversed");
					int empid  = rs.getInt("id");
					OrdersModel order = new OrdersModel(SNo, fullname, address, city, refNo, recAcc, amount, comm, 
							total,  date_, state,  reversed, empid);
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
					LocalDateTime date_ = rs.getTimestamp("date_").toLocalDateTime();
					int state = rs.getInt("state");
					boolean reversed = rs.getBoolean("reversed");
					int empid  = rs.getInt("id");
					order = new OrdersModel(SNo, fullname, address, city, refNo, recAcc, amount, comm, 
							total,  date_, state,  reversed, empid);
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
				LocalDateTime date_ = rs.getTimestamp("date_").toLocalDateTime();
				int state = rs.getInt("state");
				boolean reversed = rs.getBoolean("reversed");
				int empid  = rs.getInt("id");
				OrdersModel order = new OrdersModel(SNo, fullname, address, city, refNo, recAcc, amount, comm, 
						total,  date_, state,  reversed, empid);
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
				LocalDateTime date_ = rs.getTimestamp("date_").toLocalDateTime();
				int state = rs.getInt("state");
				boolean reversed = rs.getBoolean("reversed");
				int empid  = rs.getInt("id");
				OrdersModel order = new OrdersModel(SNo, fullname, address, city, refNo, recAcc, amount, comm, 
						total, date_, state, reversed, empid);
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
				LocalDateTime date_ = rs.getTimestamp("date_").toLocalDateTime();
				int state = rs.getInt("state");
				boolean reversed = rs.getBoolean("reversed");
				int empid  = rs.getInt("id");
				OrdersModel order = new OrdersModel(SNo, fullname, address, city, refNo, recAcc, amount, comm, 
						total,  date_, state,  reversed, empid);
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
				LocalDateTime date_ = rs.getTimestamp("date_").toLocalDateTime();
				int state = rs.getInt("state");
				boolean reversed = rs.getBoolean("reversed");
				int empid  = rs.getInt("id");
				OrdersModel order = new OrdersModel(SNo, fullname, address, city, refNo, recAcc, amount, comm, 
						total, date_, state, reversed, empid);
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
	public static void forwardOrders(int sNo, LocalDate date) 
	{
		try(
			Connection con = JDBCUtils.getConnection();
				PreparedStatement pst = con.prepareStatement(SETforward)){
			pst.setInt(1, sNo);
			pst.setDate(2, Date.valueOf(date));
			pst.executeUpdate();
		}catch(Exception e)
		{
			System.out.println("Forward orders catch block");
			e.printStackTrace();
			
		}
	}
	public static OrdersModel selectOrderForReverse(int id, LocalDate date) 
	{
		OrdersModel order = null;
			try
			(Connection con = JDBCUtils.getConnection();
			PreparedStatement pst = con.prepareStatement(SELECTtoBeReversed))
			{
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
					LocalDateTime date_ = rs.getTimestamp("date_").toLocalDateTime();
					int state = rs.getInt("state");
					boolean reversed = rs.getBoolean("reversed");
					int empid  = rs.getInt("id");
					order = new OrdersModel(SNo, fullname, address, city, refNo, recAcc, amount, comm, 
							total, date_, state, reversed, empid);
				}
			}catch(SQLException e) 
			{
				e.printStackTrace();
			}
			return order;
	}
	public static int setCancelled(int sNo, int id, LocalDate date) 
	{
		int row =0;
		try(
			Connection con = JDBCUtils.getConnection();
				PreparedStatement pst = con.prepareStatement(SETcancelled)){
			pst.setInt(1, sNo);
			pst.setInt(2, id);
			pst.setDate(3, Date.valueOf(date));
			row = pst.executeUpdate();
		}catch(Exception e)
		{
			System.out.println("Set cancelled order catch block");
			e.printStackTrace();
			
		}
		return row;
	}
	public static ArrayList<AggTransactions> getAggAdminQuery(LocalDate date, int id) 
	{
		ArrayList<AggTransactions> orders = new ArrayList<>();
		StringBuilder SELECTfromAgg = new StringBuilder("SELECT * FROM aggregate_transactions WHERE recMoney IS NOT NULL");
		 if (date != null) 
		 {
			 SELECTfromAgg.append(" AND DATE(date_) = '").append(date).append("'");
		 }
		 if (id != 0) 
		 {
			 SELECTfromAgg.append(" AND id = ").append(id);
		 }
		 try
			(Connection con = JDBCUtils.getConnection();
			PreparedStatement pst = con.prepareStatement(SELECTfromAgg.toString());)
			{
			ResultSet rs = pst.executeQuery();
				while(rs.next()) 
				{
					String Sno =rs.getString("SNo");
					Double comm = rs.getDouble("comm");
					Double total = rs.getDouble("total");
					Double recMoney = rs.getDouble("recMoney");
					Double change = rs.getDouble("changes");
					String reversed = rs.getString("reversed");
					int empid  = rs.getInt("id");
					LocalDateTime date_ = rs.getTimestamp("date_").toLocalDateTime();
					
					AggTransactions order = new AggTransactions(Sno, comm, total, recMoney, change, reversed, empid, date_);
					orders.add(order);
				}
			}catch(SQLException e) 
			{
				e.printStackTrace();
			}
			return orders;
	
		
	}
	public static ArrayList<OrdersModel> getAdminQueryOrders(LocalDate date, int sNo, int id, boolean all, boolean rev, boolean forw, boolean canc) {
		StringBuilder SELECTadminsQuery = new StringBuilder("SELECT * FROM orders WHERE refNo IS NOT NULL");
		ArrayList<OrdersModel> orders = new ArrayList<>();
		 if (date != null) 
		 {
			 SELECTadminsQuery.append(" AND DATE(date_) = '").append(date).append("'");
		 }
		 if (sNo != 0) 
		 {
			 SELECTadminsQuery.append(" AND SNo = ").append(sNo);
		 }
		 if (id != 0) 
		 {
			 SELECTadminsQuery.append(" AND id = ").append(id);
		 }
		 if (all) 
		 {
			 SELECTadminsQuery.append(" AND (state = 2 OR state = 3)");
		 }
		 if (rev) 
		 {
			 SELECTadminsQuery.append(" AND reversed = 1");
		 }
		 if (forw) 
		 {
			 SELECTadminsQuery.append(" AND state = 2 AND reversed = 0");
		 }
		 if (canc) 
		 {
			 SELECTadminsQuery.append(" AND state = 3");
		 } 
		 try
			(Connection con = JDBCUtils.getConnection();
			PreparedStatement pst = con.prepareStatement(SELECTadminsQuery.toString());)
			{
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
					LocalDateTime date_ = rs.getTimestamp("date_").toLocalDateTime();
					int state = rs.getInt("state");
					boolean reversed = rs.getBoolean("reversed");
					int empid  = rs.getInt("id");
					OrdersModel order = new OrdersModel(SNo, fullname, address, city, refNo, recAcc, amount, comm, 
							total, date_, state, reversed, empid);
					orders.add(order);
				}
			}catch(SQLException e) 
			{
				e.printStackTrace();
			}
			return orders;
	}
	public static int setToBeReverse(int sNo, int id, LocalDate date) {
		int row =0;
		try(
			Connection con = JDBCUtils.getConnection();
				PreparedStatement pst = con.prepareStatement(SETtoBeReversed)){
			pst.setInt(1, sNo);
			pst.setInt(2, id);
			pst.setDate(3, Date.valueOf(date));
			row = pst.executeUpdate();
			System.out.println("no of reversed: " +row);
		}catch(Exception e)
		{
			System.out.println("Set reverse order catch block");
			e.printStackTrace();
			
		}
		return row;
	}
	
	public static ArrayList<OrdersModel> getReversedOrders(int id, LocalDate date) {
		ArrayList<OrdersModel> revOrders = new ArrayList<>();
		try(
			Connection con = JDBCUtils.getConnection();
				PreparedStatement pst = con.prepareStatement(SELECTreversed)){
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
				LocalDateTime date_ = rs.getTimestamp("date_").toLocalDateTime();
				int state = rs.getInt("state");
				boolean reversed = rs.getBoolean("reversed");
				int empid  = rs.getInt("id");
				OrdersModel order = new OrdersModel(SNo, fullname, address, city, refNo, recAcc, amount, comm, 
						total, date_, state, reversed, empid);
				revOrders.add(order);
			}
		}catch(Exception e)
		{
			System.out.println("Paid orders catch block");
			e.printStackTrace();
			
		}
		return revOrders;
	}
		
	
	

}
