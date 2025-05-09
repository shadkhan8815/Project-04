package in.co.ryas.proj4.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.ryas.proj4.bean.MarksheetBean;
import in.co.ryas.proj4.model.MarksheetModel;

public class TestMarksheetModel {
	
	public static void main(String[] args) throws Exception {
		
		Search();
		
	}

	
	public static void Search() throws Exception {
		
		MarksheetBean bean = new MarksheetBean();
		
		MarksheetModel model = new MarksheetModel();
		
		List list = model.search(bean);

		
		Iterator it = list.iterator();
		
		while (it.hasNext()) {
			
			bean = (MarksheetBean)it.next();
			

		System.out.print(bean.getId());
		System.out.print("\t"+bean.getRollNo());
		System.out.print("\t"+bean.getStudentId());
		System.out.print("\t"+bean.getName());
		System.out.print("\t"+bean.getPhysics());
		System.out.print("\t"+bean.getChemistry());
		System.out.print("\t"+bean.getMaths());
		System.out.print("\t"+bean.getCreatedBy());
		System.out.print("\t"+bean.getModifiedBy());
		System.out.print("\t"+bean.getCreatedDatetime());
		System.out.println("\t"+bean.getModifiedDatetime());
		
		}
		
		
	} 
}
