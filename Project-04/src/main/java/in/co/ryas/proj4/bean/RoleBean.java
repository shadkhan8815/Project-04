package in.co.ryas.proj4.bean;

public class RoleBean extends BaseBean{
	
	public static final int ADMIN = 1;
	public static final int STUDENT = 2;
	public static final int COLLEGE = 3;
	public static final int FACULTY = 4;
	public static final int KIOSK = 5;

	private String name;
	private String description;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	 	
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null ;
	}

}