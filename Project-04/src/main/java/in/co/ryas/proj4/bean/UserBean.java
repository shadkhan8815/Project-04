package in.co.ryas.proj4.bean;

import java.util.Date;

public class UserBean {

	private String firstName;
	private String lastName;
	private String login;
	private String password;
	private Date dob;
	private String gender;
	private String mobileNo;
	private long roleId;
	private String confirmPassword;

	public void setfirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getfirstName() {
		return firstName;
	}

	public void setlastName(String lastName) {
		this.lastName = lastName;
	}

	public String getlastNmae() {
		return lastName;
	}

	public void setlogin(String login) {
		this.login = login;
	}

	public String getlogin() {
		return login;
	}

	public void setpassword(String password) {
		this.password = password;
	}

	public String getpassword() {
		return password;
	}

	public void setdob(Date dob) {
		this.dob = dob;
	}

	public Date getdob() {
		return dob;
	}

	public void setgender(String gender) {
		this.gender = gender;
	}

	public String getgender() {
		return gender;
	}

	public void setmobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getmobileNo() {
		return mobileNo;
	}

	public void setroleId(long roleId) {
		this.roleId = roleId;
	}

	public long getroleId() {
		return roleId;
	}

	public void setconfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getconfirmPasword() {
		return confirmPassword;
	}
}
