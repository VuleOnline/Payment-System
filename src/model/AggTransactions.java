package model;

import java.time.LocalDateTime;

public class AggTransactions {
	
	private String SNo;
	private double comm;
	private double total;
	private double rcvd;
	private double change;
	private String reversed;
	private int empid;
	private LocalDateTime date;
	
	public AggTransactions(String sNo, double comm, double total, double rcvd, double change, String reversed, int empid, LocalDateTime date) {
		super();
		this.SNo = sNo;
		this.comm = comm;
		this.total = total;
		this.rcvd = rcvd;
		this.change = change;
		this.reversed = reversed;
		this.empid = empid;
		this.date = date;
	}


	public AggTransactions(String sNo, double comm, double total, double rcvd, double change, int empid) {
		super();
		this.SNo = sNo;
		this.comm = comm;
		this.total = total;
		this.rcvd = rcvd;
		this.change = change;
		this.empid = empid;
	}
	


	public AggTransactions(double total, double comm, double rcvd, double change) {
		super();

		this.total = total;
		this.comm = comm;
		this.rcvd = rcvd;
		this.change = change;
	}


	public String getSNo() {
		return SNo.toString();
	}

	public void setSNo(String sNo) {
		this.SNo = sNo;
	}

	public double getComm() {
		return comm;
	}

	public void setComm(double comm) {
		this.comm = comm;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getRcvd() {
		return rcvd;
	}

	public void setRcvd(double rcvd) {
		this.rcvd = rcvd;
	}

	public double getChange() {
		return change;
	}

	public void setChange(double change) {
		this.change = change;
	}

	public int getEmpid() {
		return empid;
	}

	public void setEmpid(int empid) {
		this.empid = empid;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}


	public String getReversed() {
		return reversed;
	}


	public void setReversed(String reversed) {
		this.reversed = reversed;
	}
	
	
	

}
