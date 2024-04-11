package model;

import java.time.LocalDate;

public class DenomEntryModel {
	
	private int denom_value;
	private int quantity;
	private double sum;
	private int empId;
	private LocalDate date;

	
	public DenomEntryModel(int denom_value, int quantity, int empId, LocalDate date) {
		super();
		this.denom_value = denom_value;
		this.quantity = quantity;
		this.empId = empId;
		this.date = date;
	}
	public DenomEntryModel(int denom_value, int empId, LocalDate date) {
		super();
		this.denom_value = denom_value;
		this.empId = empId;
		this.date = date;
	}
	
	public int getDenom_value() {
		return denom_value;
	}
	public void setDenom_value(int denom_value) {
		this.denom_value = denom_value;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getSum() {
		return sum;
	}
	public void setSum(double sum) {
		this.sum = sum;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	
	

}
