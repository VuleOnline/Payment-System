package dao;

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

public class DBMoneyManipulationDao {

	private static final String INSERTchange = "INSERT INTO aggregate_transactions (SNo, comm, total, recMoney, changes, id)VALUES "
			+ "(?,?,?,?,?,?)"; 
	private static final String SELECTall = "SELECT * FROM aggregate_transactions WHERE id=? AND DATE(date_) = ?";
	private static final String SELECTbySno = "SELECT * FROM aggregate_transactions WHERE SNo=? AND id=? AND DATE(date_) = ?";
	private static final String SelectDenomSum = "SELECT d_sum FROM denomination WHERE d_denom_value = ? AND d_empid=? AND d_date_=?";
	private static final String COUNTcashRegSum = "SELECT SUM(d_sum) AS total_sum FROM denomination WHERE d_empid=? and d_date_ = ?";
	private static final String SubDenom = "UPDATE denomination SET d_quantity = d_quantity-? WHERE d_denom_value =? AND d_empid = ? AND d_date_ = ?";
	private static final String SELECTdenomQuant = "SELECT d_quantity FROM denomination WHERE d_denom_value = ? AND d_empid = ? AND DATE(d_date_) = ?";
	
	private static boolean executeUpdate(String query, Object... params) {
		try (Connection con = JDBCUtils.getConnection();
			 PreparedStatement pst = con.prepareStatement(query)) {
			for (int i = 0; i < params.length; i++) {
				pst.setObject(i + 1, params[i]);
			}
			boolean result =  pst.executeUpdate() > 0;
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			
			return false;
		}
	}
	
	private static ResultSet executeQuery(String query, Object... params) {
		try {
			Connection con = JDBCUtils.getConnection();
			PreparedStatement pst = con.prepareStatement(query);
			for (int i = 0; i < params.length; i++) {
				pst.setObject(i + 1, params[i]);
			}
			return pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean insertBilling(AggTransactions bill) {
		return executeUpdate(INSERTchange, bill.getSNo(), bill.getComm(), bill.getTotal(), bill.getRcvd(), bill.getChange(),
				SessionManager.getEmpIdBySession(SessionManager.getCurrSess()));
	}
	
	public static boolean reverseOrderInAgg(OrdersModel order) {
		String query = "UPDATE aggregate_transactions SET reversed = CASE WHEN reversed IS NULL THEN ? ELSE CONCAT(reversed, '-', ?) END"
				+ " WHERE FIND_IN_SET(?, REPLACE(SNo, '-', ',')) > 0 AND id = ? AND DATE(date_) = ?";
		return executeUpdate(query, "" + order.getsNo(), "" + order.getsNo(), "" + order.getsNo(), order.getEmpid(),
				Date.valueOf(order.getOrderDate().toLocalDate()));
	}
	
	public static ArrayList<String> getSnos(int id, LocalDate date) {
		ArrayList<String> sno = new ArrayList<>();
		try (ResultSet rs = executeQuery(SELECTall, id, Date.valueOf(date))) {
			while (rs.next()) {
				sno.add(rs.getString("SNo"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sno;
	}

	public static AggTransactions getOrder(String Sno, int id, LocalDate date) {
		AggTransactions change = null;
		try (ResultSet rs = executeQuery(SELECTbySno, Sno, id, Date.valueOf(date))) {
			if (rs.next()) {
				change = new AggTransactions(rs.getDouble("total"), rs.getDouble("comm"), rs.getDouble("recMoney"),
						rs.getDouble("changes"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return change;
	}

	
	public static boolean insertDenomEntry(DenomEntryModel denomEntry) {
		String query = "INSERT INTO denomination (d_denom_value, d_quantity, d_empid, d_date_) VALUES (?, ?, ?, ?)"
				+ " ON DUPLICATE KEY UPDATE d_quantity = d_quantity + VALUES(d_quantity)";
		return executeUpdate(query, denomEntry.getDenom_value(), denomEntry.getQuantity(), denomEntry.getEmpId(),
				Date.valueOf(denomEntry.getDate()));
	}

	public static int getDenomQuant(DenomEntryModel denom_obj) {
		int denom_quantity = 0;
		try (ResultSet rs = executeQuery(SELECTdenomQuant, denom_obj.getDenom_value(), denom_obj.getEmpId(),
				Date.valueOf(denom_obj.getDate()))) {
			if (rs.next()) {
				denom_quantity = rs.getInt("d_quantity");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return denom_quantity;
	}

	
	public static boolean subFromDenom(DenomEntryModel denomEntry) {
		return executeUpdate(SubDenom, denomEntry.getQuantity(), denomEntry.getDenom_value(), denomEntry.getEmpId(),
				Date.valueOf(denomEntry.getDate()));
	}

	
	public static boolean insertSafeTransfer(SafeTransfers st) {
		String query = "INSERT INTO safe_transfers (st_denom_value, st_quantity, st_empid, st_date_, st_withdrawal) VALUES (?, ?, ?, ?, ?)"
				+ " ON DUPLICATE KEY UPDATE st_quantity = st_quantity + VALUES(st_quantity)";
		return executeUpdate(query, st.getSt_denom_value(), st.getSt_quantity(), st.getSt_empId(),
				Date.valueOf(st.getSt_date_()), st.isSt_withdrawal());
	}

	
	public static double getDenomSum(double value, int id, LocalDate date) {
		double sum = 0.0;
		try (ResultSet rs = executeQuery(SelectDenomSum, value, id, Date.valueOf(date))) {
			if (rs.next()) {
				sum = rs.getDouble(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sum;
	}

	public static double getCashRegSum(int id, LocalDate date) {
		double sum = 0.0;
		try (ResultSet rs = executeQuery(COUNTcashRegSum, id, Date.valueOf(date))) {
			if (rs.next()) {
				sum = rs.getDouble(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sum;
	}


	public static boolean insertCashDepo(DenomEntryModel denomEntry, int empId, LocalDate date) {
		String query = "INSERT INTO safe_transfers (st_denom_value, st_quantity, st_empid, st_date_, st_withdrawal) VALUES (?, ?, ?, ?, 0)"
				+ " ON DUPLICATE KEY UPDATE st_quantity = IF(st_withdrawal = VALUES(st_withdrawal), st_quantity + VALUES(st_quantity))";
		return executeUpdate(query, denomEntry.getDenom_value(), denomEntry.getQuantity(), empId, Date.valueOf(date));
	}
	
	
	
	
}
