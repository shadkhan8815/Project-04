package in.co.ryas.proj4.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.ryas.proj4.Exception.ApplicationException;
import in.co.ryas.proj4.Exception.DatabaseException;
import in.co.ryas.proj4.bean.CourseBean;
import in.co.ryas.proj4.model.CourseModel;

public class TestCourseModel {
	
	public static void main(String[] args) throws Exception {
		
	 //NEXTPK();
		//      ADD();
		  //         UPDATE();
		    //               DELETE();
		      //                   SEARCH();
		        //                        FINDBYPK();
	}
	
	
	
	public static void NEXTPK () throws DatabaseException {
	
		CourseModel model = new CourseModel();
		        
		    int i = model.nextPK();
		    
		    System.out.println("Next pk : " + i);
	}
	
	
	
	public static void ADD () throws Exception {
		
		CourseBean bean = new CourseBean();
		
		           bean.setId(1);
		           bean.setName("shad");
		           bean.setDuration("rays");
		           bean.setDescription("i am student");
		           bean.setCreatedBy("root");
		           bean.setModifiedBy("root");
		           bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		           bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		           
	   CourseModel model = new CourseModel();
	   
	               model.add(bean);
	}
	
	
	
	public static void UPDATE () throws Exception {
		
		CourseBean bean = new CourseBean();
		
                   bean.setId(1);
                   bean.setName("shad");
                   bean.setDuration("admin");
                   bean.setDescription("i am student");
                   bean.setCreatedBy("root");
                   bean.setModifiedBy("admin");
                   bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
                   bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
        
        CourseModel model = new CourseModel();
        
                    model.update(bean);
	}
	
	
	
	public static void DELETE () throws Exception {
		
		CourseModel model = new CourseModel();
        
		         //   model.delete(11);
		
	}
	
	
	public static void SEARCH() throws DatabaseException, ApplicationException {
		
		CourseBean bean = new CourseBean();
		
		CourseModel model = new CourseModel();
		
		List list = model.search(bean);
		
		Iterator it = list.iterator();
		
		while (it.hasNext()) {
			
			bean = (CourseBean)it.next();
			
			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getDuration());
			System.out.print("\t" + bean.getDescription());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
		}
		
	}
	
	
	public static void FINDBYPK() throws ApplicationException {
		
		CourseModel model = new CourseModel();
		
		CourseBean bean = model.findByPK(1);
		
		if (bean != null) {
			
			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getDuration());
			System.out.print("\t" + bean.getDescription());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());

		}
		
	}

}
