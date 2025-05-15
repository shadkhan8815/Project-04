package in.co.ryas.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.ryas.proj4.Exception.ApplicationException;
import in.co.ryas.proj4.Exception.DatabaseException;
import in.co.ryas.proj4.Exception.DublicateRecordException;
import in.co.ryas.proj4.bean.CollegeBean;
import in.co.ryas.proj4.bean.CourseBean;
import in.co.ryas.proj4.bean.FacultyBean;
import in.co.ryas.proj4.bean.SubjectBean;
import in.co.ryas.proj4.util.JDBCDataSource;



public class FacultyModel {

	public int nextPK() throws DatabaseException {

		Connection conn = null;
		
		int pk = 0;
		
		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(ID) from st_faculty");

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				
				pk = rs.getInt(1);
			}

			rs.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new DatabaseException("Exception : Exception in getting pk");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
			
		}
		return pk + 1;
	}

	
	
	public FacultyBean findByPK(long pk) throws ApplicationException {
		
        StringBuffer sql = new StringBuffer("select * from st_faculty where id = ?");
        
        Connection conn = null ;
        
        FacultyBean bean = null;


   try {
	    conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());

		pstmt.setLong(1, pk);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			
			bean = new FacultyBean();
			
			bean.setId(rs.getLong(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setDob(rs.getDate(4)); // or rs.getTimestamp(4) if needed
			bean.setGender(rs.getString(5));
			bean.setMobileNo(rs.getString(6));
			bean.setEmail(rs.getString(7));
			bean.setCollegeId(rs.getLong(8));
			bean.setCollegeName(rs.getString(9));
			bean.setCourseId(rs.getLong(10));
			bean.setCourseName(rs.getString(11));
			bean.setSubjectId(rs.getLong(12));
			bean.setSubjectName(rs.getString(13));
			bean.setCreatedBy(rs.getString(14));
			bean.setModifiedBy(rs.getString(15));
			bean.setCreatedDatetime(rs.getTimestamp(16));
			bean.setModifiedDatetime(rs.getTimestamp(17));
		}
		rs.close();
		
   } catch (Exception e) {
	   
		throw new ApplicationException("Exception : Exception in getting User by pk");

   }
   finally {
	
	   JDBCDataSource.closeConnection(conn);
}
		
   return bean;
   
		
	}

	
	
	
	public long add(FacultyBean bean) throws Exception {

		Connection conn = null;

		int pk = 0 ;
		
		CollegeModel collegemodel = new CollegeModel();
		CollegeBean collegebean = collegemodel.findByPK(bean.getCollegeId());
		bean.setCollegeName(collegebean.getName());
		
		CourseModel coursemodel = new CourseModel();
		CourseBean coursebean = coursemodel.findByPK(bean.getCourseId());
		bean.setCourseName(coursebean.getName());
		
		SubjectModel subjectmodel = new SubjectModel();
 		SubjectBean subjectbean = subjectmodel.FindByPK(bean.getSubjectId());
		bean.setSubjectName(subjectbean.getName());
		
		FacultyBean existbean = findByEmail(bean.getEmail());
		
		if(existbean != null) {
			throw new DublicateRecordException("Email already exists");
		}

		try {
			pk = nextPK();
			
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);
			
			System.out.println("abc666666abc");

			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_faculty values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getFirstName());
			pstmt.setString(3, bean.getLastName());
			pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(5, bean.getGender());
			pstmt.setString(6, bean.getMobileNo());
			pstmt.setString(7, bean.getEmail());
			pstmt.setLong(8, bean.getCollegeId());
			pstmt.setString(9, bean.getCollegeName());
			pstmt.setLong(10, bean.getCourseId());
			pstmt.setString(11, bean.getCourseName());
			pstmt.setLong(12, bean.getSubjectId());
			pstmt.setString(13, bean.getSubjectName());
			pstmt.setString(14, bean.getCreatedBy());
			pstmt.setString(15, bean.getModifiedBy());
			pstmt.setTimestamp(16, bean.getCreatedDatetime());
			pstmt.setTimestamp(17, bean.getModifiedDatetime());

			int i = pstmt.executeUpdate();

			pstmt.close();
			conn.commit();
			conn.close();

 		} catch ( Exception e) {
 			
 	   try {
 		   conn.rollback();
 		   
 	   }catch (Exception e1) {
 		   
 		   e1.printStackTrace();
 		   System.out.println(e1.getMessage());
 	}
 	   finally {
		JDBCDataSource.closeConnection(conn);
	}
  		}
		return pk;
	}

	
	
	
	public void update(FacultyBean bean) throws Exception {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
"update st_faculty set first_name=?, last_name=?, dob=?, gender=?, mobile_no=?, email=?, collage_id=?, collage_name=?, course_id=?, subject_id=?, subject_name=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=? ");

			pstmt.setString(1, bean.getFirstName());
			pstmt.setString(2, bean.getLastName());
			pstmt.setDate(3, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(4, bean.getGender());
			pstmt.setString(5, bean.getMobileNo());
			pstmt.setString(6, bean.getEmail());
			pstmt.setLong(7, bean.getCollegeId());
			pstmt.setString(8, bean.getCollegeName());
			pstmt.setLong(9, bean.getCourseId());
			pstmt.setString(10, bean.getCourseName());
			pstmt.setLong(11, bean.getSubjectId());
			pstmt.setString(12, bean.getSubjectName());
			pstmt.setString(13, bean.getCreatedBy());
			pstmt.setString(14, bean.getModifiedBy());
			pstmt.setTimestamp(15, bean.getCreatedDatetime());
			pstmt.setTimestamp(16, bean.getModifiedDatetime());
			pstmt.setLong(17, bean.getId());

			int i = pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

 
 		} catch (Exception e) {
			e.printStackTrace();
			
			try {
				conn.rollback();
				
			} catch (Exception e1) {
				throw new ApplicationException("Exception : update rollback Exception " + e1.getMessage());

 			}
 		}	finally {
				JDBCDataSource.closeConnection(conn);
		
 		}
	}
	
	
	public List list() throws ApplicationException {
		return list(0, 0);
	}
	
	public List list(int pageNo, int pageSize) throws ApplicationException {
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_FACULTY");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);

		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				FacultyBean bean = new FacultyBean();
				
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setEmail(rs.getString(5));
				bean.setMobileNo(rs.getString(6));
				bean.setCollegeId(rs.getLong(7));
				bean.setCollegeName(rs.getString(8));
				bean.setCourseId(rs.getLong(9));
				bean.setCourseName(rs.getString(10));
				bean.setDob(rs.getDate(11));
				bean.setSubjectId(rs.getLong(12));
				bean.setSubjectName(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDatetime(rs.getTimestamp(16));
				bean.setModifiedDatetime(rs.getTimestamp(17));
				
				list.add(bean);
			}
			rs.close();
			
		} catch (Exception e) {
			
			throw new ApplicationException("Exception : Exception in getting list of faculty");
		} 
		finally {
			
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
	
	
	public List search(FacultyBean bean) throws Exception {
		return search(bean, 0, 0);
	}

	public List search(FacultyBean bean, int pageNo, int pageSize) throws Exception {

		 
		StringBuffer sql = new StringBuffer("select * from st_faculty where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getCollegeId() > 0) {
				sql.append(" AND college_Id = " + bean.getCollegeId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().trim().length() > 0) {
				sql.append(" AND FIRST_NAME like '" + bean.getFirstName() + "%'");
			}
			if (bean.getLastName() != null && bean.getLastName().trim().length() > 0) {
				sql.append(" AND LAST_NAME like '" + bean.getLastName() + "%'");
			}

			if (bean.getEmail() != null && bean.getEmail().length() > 0) {
				sql.append(" AND Email_Id like '" + bean.getEmail() + "%'");
			}

			if (bean.getGender() != null && bean.getGender().length() > 0) {
				sql.append(" AND Gender like '" + bean.getGender() + "%'");
			}

			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" AND Mobile_No like '" + bean.getMobileNo() + "%'");
			}
			if (bean.getCollegeId() > 0) {
				sql.append(" AND college_Id = " + bean.getCollegeId());
			}

			if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {
				sql.append(" AND college_Name like '" + bean.getCollegeName() + "%'");
			}
			if (bean.getCourseId() > 0) {
				sql.append(" AND course_Id = " + bean.getCourseId());
			}
			if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
				sql.append(" AND course_Name like '" + bean.getCourseName() + "%'");
			}
			if (bean.getSubjectId() > 0) {
				sql.append(" AND Subject_Id = " + bean.getSubjectId());
			}
			if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0) {
				sql.append(" AND subject_Name like '" + bean.getSubjectName() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;
		
		ArrayList list = new ArrayList();
		
try {	
	   conn = JDBCDataSource.getConnection();
	
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());

		ResultSet rs = pstmt.executeQuery();
		
		System.out.println(sql);

		while (rs.next()) {
			
			bean = new FacultyBean();
			
			bean.setId(rs.getInt(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setDob(rs.getDate(4));
			bean.setGender(rs.getString(5));
			bean.setMobileNo(rs.getString(6));
			bean.setEmail(rs.getString(7));
			bean.setCollegeId(rs.getLong(8));
			bean.setCollegeName(rs.getString(9));
			bean.setCourseId(rs.getLong(10));
			bean.setCourseName(rs.getString(11));
            bean.setSubjectId(rs.getLong(12));
			bean.setSubjectName(rs.getString(12));
			bean.setCreatedBy(rs.getString(14));
			bean.setModifiedBy(rs.getString(15));
			bean.setCreatedDatetime(rs.getTimestamp(16));
			bean.setModifiedDatetime(rs.getTimestamp(17));

			list.add(bean);
		}
		rs.close();
		
    } catch (Exception e) {
    	
    }finally {
		JDBCDataSource.closeConnection(conn);
	}
       return list;
 	}
			
		 
	
	
	public FacultyBean findByEmail(String email) throws Exception {
		
		StringBuffer sql = new StringBuffer("select * from st_faculty where email = ? ");
		
		FacultyBean bean = null;
		
		Connection conn = null;
		
		try {

		 conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(sql.toString() );

		pstmt.setString(1, email);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			
			bean = new FacultyBean();
			
			bean.setId(rs.getInt(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setDob(rs.getDate(4));
			bean.setGender(rs.getString(5));
			bean.setMobileNo(rs.getString(6));
			bean.setEmail(rs.getString(7));
			bean.setCollegeId(rs.getLong(8));
			bean.setCollegeName(rs.getString(9));
			bean.setCourseId(rs.getLong(10));
			bean.setCourseName(rs.getString(9));
			bean.setSubjectId(rs.getLong(11));
			bean.setSubjectName(rs.getString(12));
			bean.setCreatedBy(rs.getString(13));
			bean.setModifiedBy(rs.getString(14));
			bean.setCreatedDatetime(rs.getTimestamp(15));
			bean.setModifiedDatetime(rs.getTimestamp(16));
		
		}
		rs.close();
		
	} catch (Exception e) {
		 
	} finally {
		JDBCDataSource.closeConnection(conn);
	}
	return bean;
}
	

	
	
	public void delete(FacultyBean bean) throws Exception {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			
			conn.setAutoCommit(false);
			
			PreparedStatement pstmt = conn.prepareStatement("delete from st_faculty where id = ?");
			
			pstmt.setLong(1, bean.getId());

			int i = pstmt.executeUpdate();
			
			conn.commit();
			pstmt.close();
			
			System.out.println("data deleted => " + i);

		} catch (Exception e) {
			
			try {
				conn.rollback();
				
			} catch (Exception ex) {
				
				throw new ApplicationException("Exception : delete rollback exception  " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Student");
		} 
		finally {
			
			JDBCDataSource.closeConnection(conn);
		}
	}

}