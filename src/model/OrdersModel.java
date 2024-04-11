package model;

import java.time.LocalDateTime;

public class OrdersModel {

	 	private int sNo;
		private String fullName;
		private String address;
		private String city;
		private String refNo;
		private String recAcc;
		private double amount;
		private double comm;
		private double invoiceAmt;
		private int state;
		private boolean reversed;
		private LocalDateTime orderDate;
		int empid;
		
		public OrdersModel(int sNo, String fullname, String address, String city, String refNo, String recAcc, double amount,
				double comm, double invoiceAmt, LocalDateTime orderDate, int state, boolean reversed, int empId) {
			this.sNo = sNo;
			this.fullName = fullname;
			this.address = address;
			this.city = city;
			this.refNo = refNo;
			this.recAcc = recAcc;
			this.amount = amount;
			this.comm = comm;
			this.invoiceAmt = invoiceAmt;
			this.orderDate = orderDate;
			this.state = state;
			this.reversed = reversed;
			this.empid = empId;
		}
		public OrdersModel(int sNo, String fullName, String address, String city, String refNo, String recAcc,
				double amount, double comm, double invoiceAmt, int state, int empid) {
			super();
			this.sNo = sNo;
			this.fullName = fullName;
			this.address = address;
			this.city = city;
			this.refNo = refNo;
			this.recAcc = recAcc;
			this.amount = amount;
			this.comm = comm;
			this.invoiceAmt = invoiceAmt;
			this.state =state;
			this.empid = empid;
		}
		
	
		public int getsNo() {
			return sNo;
		}
		public void setsNo(int sNo) {
			this.sNo = sNo;
		}
		public String getFullName() {
			return fullName;
		}
		public void setFullName(String fullName) {
			this.fullName = fullName;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getRefNo() {
			return refNo;
		}
		public void setRefNo(String refNo) {
			this.refNo = refNo;
		}
		public String getRecAcc() {
			return recAcc;
		}
		public void setRecAcc(String recAcc) {
			this.recAcc = recAcc;
		}
		public double getAmount() {
			return amount;
		}
		public void setAmount(double amount) {
			this.amount = amount;
		}
		public double getComm() {
			return comm;
		}
		public void setComm(double comm) {
			this.comm = comm;
		}
		public double getInvoiceAmt() {
			return invoiceAmt;
		}
		public void setInvoiceAmt(double invoiceAmt) {
			this.invoiceAmt = invoiceAmt;
		}
		public LocalDateTime getOrderDate() {
			return orderDate;
		}
		public void setOrderDate(LocalDateTime orderDate) {
			this.orderDate = orderDate;
		}
		public int getEmpid() {
			return empid;
		}
		public void setEmpid(int empid) {
			this.empid = empid;
		}
		public boolean isReversed() {
			return reversed;
		}
		public void setReversed(boolean reversed) {
			this.reversed = reversed;
		}
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		
		
		
}
		