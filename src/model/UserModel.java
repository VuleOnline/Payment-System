package model;



public class UserModel {
	
	int id;
	String fname;
	String lname;
	String uname;
	String password;
	boolean isAdmin= false;

	
	public UserModel(int id, String fname, String lname, String uname, String password, boolean isAdmin) {
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.uname = uname;
		this.password = password;
		this.isAdmin = isAdmin;
	}

	public UserModel(String fname, String lname, String uname, String password, boolean isAdmin) {
		this.fname = fname;
		this.lname = lname;
		this.uname = uname;
		this.password = password;
		this.isAdmin = isAdmin;
	}
	
	public UserModel() {
		super();
		
	}

	public UserModel(int id, boolean isAdmin) {
		this.id = id;
		this.isAdmin = isAdmin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	

}
