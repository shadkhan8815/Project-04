package in.co.ryas.proj4.test;

 import in.co.ryas.proj4.Exception.DatabaseException;
import in.co.ryas.proj4.bean.FacultyBean;
import in.co.ryas.proj4.model.FacultyModel;

public class TestFacultyModel {
	
	public static void main(String[] args) {
		
	//	NextPK();
		
	}

	
	public static void NextPK() throws DatabaseException {
		
		 FacultyModel model = new FacultyModel();
		 
		     int i =  model.nextPK();
		     
		     System.out.println("Nextpk is : " + i);
	}
	
	
	public static void Add() {
		
		FacultyBean bean = new FacultyBean();
		
		bean.setId(1);
	}
}
