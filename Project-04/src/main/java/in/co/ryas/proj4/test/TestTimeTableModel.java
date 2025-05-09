package in.co.ryas.proj4.test;

import java.util.List;

import in.co.ryas.proj4.bean.TimeTableBean;
import in.co.ryas.proj4.model.TimeTableModel;

public class TestTimeTableModel {
	
	public static void main(String[] args) {
		
	//	NEXTPK();
		   //   FINDBYPK();
	}

	public static void NEXTPK() {
		
		TimeTableModel model = new TimeTableModel();
		
		int i = model.nextPK();
		
		System.out.println("NEXT PK IS : " + i);
		
	}
	
	
	public static void FINDBYPK() {
		
		TimeTableModel model = new TimeTableModel();
		
		TimeTableBean bean = model.findByPK(767);
 		if(bean != null) {
			
			System.out.print(bean.getId());
			System.out.print("\t" + bean.getSemester());
			System.out.print("\t" + bean.getDescription());
			System.out.print("\t" + bean.getExamDate());
			System.out.print("\t" + bean.getExamTime());
			System.out.print("\t" + bean.getCourseId());
			System.out.print("\t" + bean.getCourseName());
			System.out.print("\t" + bean.getSubjectId());
			System.out.print("\t" + bean.getSubjectName());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
			
		}
	}
	
	
	public static void SEARCH() {
		
		TimeTableModel model = new TimeTableModel();
		
		TimeTableBean bean = new TimeTableBean();
		
	
		
	}
}
