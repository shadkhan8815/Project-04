package in.co.ryas.proj4.bean;

import java.sql.Timestamp;

    public class BaseBean {

	private int id;
	private String createdBy;
	private String modifiedBy;
	private Timestamp createdByDatetime;
	private Timestamp modifiedByDatetime;
	
	

	public void setid(int id) {
		this.id = id;
	}

	public int getid() {
		return id;
	}

	public void setcreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getcreatedBy() {
		return createdBy;
	}

	public void setmodifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getmodifiedBy() {
		return modifiedBy;
	}

	public void setcreatedByDatetime(Timestamp createdByDatetime) {
		this.createdByDatetime = createdByDatetime;
	}

	public Timestamp getcreatedByDatetime() {
		return createdByDatetime;
	}

	public void setmodifiedByDatetime(Timestamp modifiedByDatetime) {
		this.modifiedByDatetime = modifiedByDatetime;
	}

	public Timestamp getmodifiedDatetime() {
		return modifiedByDatetime;
	}

}
