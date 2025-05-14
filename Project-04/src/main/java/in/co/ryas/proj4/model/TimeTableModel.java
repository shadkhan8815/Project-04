package in.co.ryas.proj4.model;

import java.sql.Connection;
 import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import in.co.ryas.proj4.bean.TimeTableBean;
import in.co.ryas.proj4.util.JDBCDataSource;

 
public class TimeTableModel {
	
public int nextPK() {
		
		int pk = 0;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement("select max(ID) from st_timetable");

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return pk + 1;
	}


   public TimeTableBean findByPK(long id) throws SQLException {
	   
	   Connection conn = null ;
	   TimeTableBean bean = null ;
	   
	   try {
		   int pk = nextPK();
		   
		conn = JDBCDataSource.getConnection();
		
		conn.setAutoCommit(false);
		
		PreparedStatement stmt = conn.prepareStatement("select * from st_timetable where id = ?");

		stmt.setLong(1, id);
		
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			
			bean = new TimeTableBean();
			
			bean.setId(rs.getLong(1));
			bean.setSemester(rs.getString(2));
			bean.setDescription(rs.getString(3));
			bean.setExamDate(rs.getDate(4));
			bean.setExamTime(rs.getString(5));
			bean.setCourseId(rs.getLong(6));
			bean.setCourseName(rs.getString(7));
			bean.setSubjectId(rs.getLong(8));
			bean.setSubjectName(rs.getString(9));
			bean.setCreatedBy(rs.getString(10));
			bean.setModifiedBy(rs.getString(11));
			bean.setCreatedDatetime(rs.getTimestamp(12));
			bean.setModifiedDatetime(rs.getTimestamp(13));
		}
		conn.commit();
		stmt.close();
		rs.close();
		
	} catch (Exception e) {
 		e.printStackTrace();
 		conn.rollback();
	}
	return bean;
	   
   }
   
   public void Search() {
	
}
}