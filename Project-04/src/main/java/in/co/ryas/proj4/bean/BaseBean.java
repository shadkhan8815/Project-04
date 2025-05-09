package in.co.ryas.proj4.bean;

import java.sql.Timestamp;

    public abstract class BaseBean implements DropdownListBean {

	private long id;
	private String createdBy;
	private String modifiedBy;
	private Timestamp createdDatetime;
	private Timestamp modifiedDatetime;
	
	

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setCreatedDatetime(Timestamp createdDatetime) {
		this.createdDatetime = createdDatetime;
	}

	public Timestamp getCreatedDatetime() {
		return createdDatetime;
	}

	public void setModifiedDatetime(Timestamp modifiedDatetime) {
		this.modifiedDatetime = modifiedDatetime;
	}

	public Timestamp getModifiedDatetime() {
		return modifiedDatetime;
	}
	 
}
