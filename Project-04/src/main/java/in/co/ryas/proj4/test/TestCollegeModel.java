package in.co.ryas.proj4.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.ryas.proj4.Exception.ApplicationException;
import in.co.ryas.proj4.bean.CollegeBean;
import in.co.ryas.proj4.model.CollegeModel;
 

public class TestCollegeModel {

	public static void main(String[] args) throws Exception {

		  //NEXTPK();
	             //	ADD();
                        //  UPDATE();
		                             //  SEARCH();
		                                         //  FINDBYPK();
		                                               //     FINDBYNAME();
	}
	
	
	

	public static void NEXTPK() throws Exception {

		CollegeModel model = new CollegeModel();

		     int i = model.nextPK();

		System.out.println("next pk is : " + i);
	}

	
	
	public static void ADD() throws Exception {
		
		CollegeBean bean = new CollegeBean();
		
		            bean.setId(1);
		            bean.setName("shad");
		            bean.setAddress("chawni");
		            bean.setState("M.P");
		            bean.setCity("indore");
		            bean.setPhoneNo("0011");
		            bean.setCreatedBy("root");
		            bean.setModifiedBy("admin");
		            bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		            bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		
		            
		            CollegeModel model = new CollegeModel();
		
		                        model.add(bean);
	}
	
	
	
	public static void UPDATE() throws Exception {
		
		CollegeBean bean = new CollegeBean();
		
		            bean.setId(2);
                    bean.setName("azam");
                    bean.setAddress("chawni");
                    bean.setState("M.P");
                    bean.setCity("indore");
                    bean.setPhoneNo("0011");
                    bean.setCreatedBy("root");
                    bean.setModifiedBy("admin");
                    bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
                    bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

       CollegeModel model = new CollegeModel();
                     
                    model.update(bean);
	}
	
	
	
	
	public static void SEARCH() throws ApplicationException {
		
		CollegeBean bean = new CollegeBean();
		
		CollegeModel model = new CollegeModel();
		
		List list = model.search(bean);
		
		Iterator it = list.iterator();
		
		while(it.hasNext()) {
			
			bean = (CollegeBean)it.next();
			
			System.out.print(bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getAddress());
			System.out.print("\t" + bean.getState());
			System.out.print("\t" + bean.getCity());
			System.out.print("\t" + bean.getPhoneNo());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
			
		}
	}
	
	
	
	public static void FINDBYPK() throws ApplicationException {
		
		CollegeModel model = new CollegeModel();
		
		CollegeBean bean = model.findByPK(1);
		
		if(bean != null) {
		
		System.out.print(bean.getId());
		System.out.print("\t" + bean.getName());
		System.out.print("\t" + bean.getAddress());
		System.out.print("\t" + bean.getState());
		System.out.print("\t" + bean.getCity());
		System.out.print("\t" + bean.getPhoneNo());
		System.out.print("\t" + bean.getCreatedBy());
		System.out.print("\t" + bean.getModifiedBy());
		System.out.print("\t" + bean.getCreatedDatetime());
		System.out.println("\t" + bean.getModifiedDatetime());
		
		}
		
	}
	
	
	public static void FINDBYNAME() throws ApplicationException {
		
		CollegeModel model = new CollegeModel();
		
		CollegeBean bean = model.findByName("rays");
		
		if(bean != null) {
			
			System.out.print(bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getAddress());
			System.out.print("\t" + bean.getState());
			System.out.print("\t" + bean.getCity());
			System.out.print("\t" + bean.getPhoneNo());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());

		}
		
	}

}
