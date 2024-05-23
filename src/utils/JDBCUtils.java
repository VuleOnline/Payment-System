package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;



public class JDBCUtils {
	
	public static Connection getConnection(){
	
	Connection con = null;
	String url ="jdbc:mysql://localhost:3306/payment";
	String username = "root";
	String pass ="root!";


	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection(url, username, pass);
	} catch (ClassNotFoundException e) {
		JOptionPane.showMessageDialog(null, "No driver found.");
		e.printStackTrace();
	}
	catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "No db connection.");
		e.printStackTrace();
	}
	return con;
	
}
	}
