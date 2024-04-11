package model;

import java.time.LocalDate;

public class SafeTransfers {
	
	private int st_denom_value;
	private int st_quantity;
	private double st_sum;
	private int st_empId;
	private LocalDate st_date_;
	private boolean st_withdrawal;
	public SafeTransfers(int st_denom_value, int st_quantity,int st_empId, LocalDate st_date_,
			boolean st_withdrawal) {
		super();
		this.st_denom_value = st_denom_value;
		this.st_quantity = st_quantity;
		this.st_empId = st_empId;
		this.st_date_ = st_date_;
		this.st_withdrawal = st_withdrawal;
	}
	public int getSt_denom_value() {
		return st_denom_value;
	}
	public void setSt_denom_value(int st_denom_value) {
		this.st_denom_value = st_denom_value;
	}
	public int getSt_quantity() {
		return st_quantity;
	}
	public void setSt_quantity(int st_quantity) {
		this.st_quantity = st_quantity;
	}
	public double getSt_sum() {
		return st_sum;
	}
	public void setSt_sum(double st_sum) {
		this.st_sum = st_sum;
	}
	public int getSt_empId() {
		return st_empId;
	}
	public void setSt_empId(int st_empId) {
		this.st_empId = st_empId;
	}
	public LocalDate getSt_date_() {
		return st_date_;
	}
	public void setSt_date_(LocalDate st_date_) {
		this.st_date_ = st_date_;
	}
	public boolean isSt_withdrawal() {
		return st_withdrawal;
	}
	public void setSt_withdrawal(boolean st_withdrawal) {
		this.st_withdrawal = st_withdrawal;
	}
	
	

}
