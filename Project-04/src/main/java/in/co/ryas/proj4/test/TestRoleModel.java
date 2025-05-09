package in.co.ryas.proj4.test;

import java.sql.Timestamp;
import java.util.Date;

import in.co.ryas.proj4.bean.RoleBean;
import in.co.ryas.proj4.model.RoleModel;

public class TestRoleModel {

	public static void main(String[] args) throws Exception {

//		nextPK();
		   //     Add();
		          //    Update();
		                   //    Delete();
	}

	
	
	public static void nextPK() {

		RoleModel model = new RoleModel();

		  int i = model.nextPK();

		System.out.println("next pk : " + i);
	}

	
	
	
	public static void Add() throws Exception {

		RoleBean bean = new RoleBean();
		
		
		         bean.setId(1);
	          	 bean.setName("shad");
		         bean.setDescription("admin");
		         bean.setCreatedBy("root");
		         bean.setModifiedBy("admin");
		         bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		         bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		 
		RoleModel model = new RoleModel();

		          model.add(bean);

	}
	
	
	

	public static void Update() throws Exception {

		RoleBean bean = new RoleBean();

		         bean.setId(1);
		         bean.setName("shad");
		         bean.setDescription("i am student");
		         bean.setCreatedBy("root");
		         bean.setModifiedBy("root");
		         bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		         bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		RoleModel model = new RoleModel();

		          model.update(bean);
	}

	
	
	
	public static void Delete() throws Exception {

		RoleModel model = new RoleModel();

		          model.delete(1);

	}

}
