package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import utils.JDBCUtils;
import model.UserModel;

public class DBEmpManipulationServ {
	
	private static final String INSERTuser ="INSERT INTO users VALUES(NULL, ?, ?, ?, ?, ?)";
	private static final String SELECTusers ="SELECT * FROM users";
	private static final String SELECTuserByID ="SELECT * FROM users WHERE id=?";
	private static final String UPDATEuser ="UPDATE users SET fname=?, lname=?, uname=?, password=?,"
			+ "isAdmin=? WHERE id=?";
	private static final String DELETEuser = "DELETE FROM users WHERE id=?";
	private static final String SELECTregUser = "SELECT id, isAdmin FROM users WHERE BINARY uname=? AND BINARY password=?";
	
	
	
	
	public static boolean insertUser(UserModel user) 

	{
		boolean rows = false;
		try
		(Connection con = JDBCUtils.getConnection();
		PreparedStatement pst = con.prepareStatement(INSERTuser);)
		{
			pst.setString(1, user.getFname());
			pst.setString(2, user.getLname());
			pst.setString(3, user.getUname());
			pst.setString(4, user.getPassword());
			pst.setBoolean(5, user.isAdmin());
			rows = pst.executeUpdate()>0;
		}catch(SQLException e) 
		{
			 JOptionPane.showMessageDialog(null, "This username is taken", "Warning", JOptionPane.WARNING_MESSAGE);
			 e.printStackTrace();
		}
		return rows;
	}
	public static ArrayList<UserModel> selectUsers() 
	{
		ArrayList<UserModel> users = new ArrayList<>();
			try
			(Connection con = JDBCUtils.getConnection();
			PreparedStatement pst = con.prepareStatement(SELECTusers);)
			{
			ResultSet rs = pst.executeQuery();
				while(rs.next()) 
				{
					int ID =rs.getInt("id");
					String fname = rs.getString("fname");
					String lname = rs.getString("lname");
					String uname = rs.getString("uname");
					String pass = rs.getString("password");
					boolean isAdmin = rs.getBoolean("isAdmin");
					UserModel user = new UserModel(ID, fname, lname, uname, pass, isAdmin);
					users.add(user);
				}
			}catch(SQLException e) 
			{
				JOptionPane.showMessageDialog(null, "Wrong input.", "Warning", JOptionPane.WARNING_MESSAGE);
				e.printStackTrace();
			}
			return users;
	}
	public static UserModel selectUserByID(int id) 
	{
		UserModel user=null;
	
		try
		(Connection con = JDBCUtils.getConnection();
		PreparedStatement pst = con.prepareStatement(SELECTuserByID);)
		{
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) 
			{
				int ID = rs.getInt("id");
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String uname = rs.getString("uname");
				String pass = rs.getString("password");
				boolean isAdmin = rs.getBoolean("isAdmin");
				user = new UserModel(ID, fname, lname, uname, pass, isAdmin);
			}
			}catch(SQLException e) 
			{
				JOptionPane.showMessageDialog(null, "Wrong input.", "Warning", JOptionPane.WARNING_MESSAGE);
				e.printStackTrace();
			}
	
		
		return user;
	}
	
	public static boolean updateUser(UserModel u)
	{
		boolean status = false;
		try
		(Connection con = JDBCUtils.getConnection();
		PreparedStatement pst = con.prepareStatement(UPDATEuser);){
			pst.setString(1, u.getFname());
			pst.setString(2, u.getLname());
			pst.setString(3, u.getUname());
			pst.setString(4, u.getPassword());
			pst.setBoolean(5, u.isAdmin());
			pst.setInt(6, u.getId());
			int rows = pst.executeUpdate();
			if(rows>0) 
			{
				status = true;
		          
			}else {
				status = false;
			}
		}catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(null, "Wrong input.", "Warning", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		
		return status;
		
	}
	
	public static boolean deleteUser(int id) 
	{
		boolean deleted = false;
		try
		(Connection con = JDBCUtils.getConnection();
		PreparedStatement pst = con.prepareStatement(DELETEuser);){
			pst.setInt(1, id);
			if(pst.executeUpdate()>0) 
			{
				deleted = true;
			}
		}catch(SQLException e) 
		{
			JOptionPane.showMessageDialog(null, "Wrong input.", "Warning", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		return deleted;
	}
	public static UserModel loginCheck(UserModel userInput)
	{
		UserModel user = null;
		try 
		(Connection con = JDBCUtils.getConnection();
		PreparedStatement pst = con.prepareStatement(SELECTregUser);){
		
			pst.setString(1, userInput.getUname());
			pst.setString(2, userInput.getPassword());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) 
			{
				int userId = rs.getInt("id");
				boolean isAdmin = rs.getBoolean("isAdmin");
				user = new UserModel(userId, isAdmin);
			}
		
		}catch(SQLException e) 
		{
			JOptionPane.showMessageDialog(null, "Wrong input.", "Warning", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		
		return user;
		
	}
	

}
