package in.co.ryas.proj4.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.ryas.proj4.Exception.ApplicationException;
import in.co.ryas.proj4.Exception.DatabaseException;
import in.co.ryas.proj4.Exception.DublicateRecordException;
import in.co.ryas.proj4.bean.CourseBean;
import in.co.ryas.proj4.bean.SubjectBean;
import in.co.ryas.proj4.util.JDBCDataSource;
 
 
public class SubjectModel {
	
public int nextPK() throws DatabaseException {
		
		int pk = 0;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(ID) from st_subject");

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}

		} catch (Exception e) {
			throw new DatabaseException("Exception : Exception in getting pk");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk = pk + 1;
	}



	 	public long add(SubjectBean bean) throws ApplicationException, DublicateRecordException {
		
		CourseModel coursemodel = new CourseModel();
		
		CourseBean coursebean = coursemodel.findByPK(bean.getCourseId());
		bean.setCourseName(coursebean.getName());

		SubjectBean dublicatename = findByName(bean.getCourseName());
		
		int pk = 0;

		if (dublicatename != null) {
			throw new DublicateRecordException("Subject Name already exists");
		}
		
 		Connection conn = null;

		try {
			pk = nextPK();
			
			conn = JDBCDataSource.getConnection();
			
			conn.setAutoCommit(false);
			
			PreparedStatement pstmt = conn.prepareStatement("INSERT  ST_SUBJECT VALUE(?,?,?,?,?,?,?,?,?)");
			
			pstmt.setInt(1, pk);
			
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getDescription());
			pstmt.setLong(4, bean.getCourseId());
			pstmt.setString(5, bean.getCourseName());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			
			pstmt.executeUpdate();
			
			conn.commit();
			pstmt.close();
			conn.close();
			
		} catch (Exception e) {
			
			try {
				conn.rollback();
				
			}
			catch (Exception ex) {
				// ex.printStackTrace();
				throw new ApplicationException("Excetion : add rollback Exception " + ex.getMessage());
			}

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;

	}

	 	
	 	public void Delete(SubjectBean bean) throws ApplicationException {
		
	 		Connection conn = null;
	 		
		try {
			conn = JDBCDataSource.getConnection();
			
			conn.setAutoCommit(false);
			
			PreparedStatement pstmt = conn.prepareStatement("DELETE  FROM ST_SUBJECT WHERE ID=?");
			
			pstmt.setLong(1, bean.getId());
			
			pstmt.executeUpdate();
			
			conn.commit();
			pstmt.close();
			
		} catch (Exception e) {
			
			try {
				conn.rollback();
				
			} 
			catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback Wxception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in delete subjecte");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}


	

	public void update(SubjectBean bean) throws ApplicationException, DublicateRecordException {
 
		/*
		 * SubjectBean beanexist = findByName(bean.getSubjectName()); if(beanexist !=
		 * null && beanexist.getId() != bean.getId()) { throw new
		 * DuplicateRecordException("subject is already exist");
		 * 
		 * }
		 */

		CourseModel coursemodel = new CourseModel();
		CourseBean coursebean = coursemodel.findByPK(bean.getCourseId());
		bean.setCourseName(coursebean.getName());

		Connection conn = null ;
		
		try {
			conn = JDBCDataSource.getConnection();
			
			conn.setAutoCommit(false);
			
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_SUBJECT SET SUBJECT_NAME=?,DESCRIPTION=?,COURSE_ID=?,COURSE_NAME=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");

			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getDescription());
			pstmt.setLong(3, bean.getCourseId());
			pstmt.setString(4, bean.getCourseName());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.setLong(9, bean.getId());
			
			pstmt.executeUpdate();
			
			conn.commit();
			pstmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		
			try {
				conn.rollback();
				
			} catch (Exception ex) {
				
				throw new ApplicationException("Exception : update rollback Exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception :Exception in update subject");
		} 
		finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	 	public SubjectBean findByName(String name) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE SUBJECT_NAME=?");
		
		SubjectBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, name);
			
			System.out.println(name + "ABCD");
			
			ResultSet rs = pstmt.executeQuery();
			
			System.out.println("query working");
			
			while (rs.next()) {
				
				bean = new SubjectBean();
				
				bean.setId(1);
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
			rs.close();
			
		} catch (Exception e) {
			
//			 throw new ApplicationException("Exception in getting subject by name");
		} 
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;

	}

	 
	public SubjectBean FindByPK(long pk) throws ApplicationException {
		
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE ID=?");
		
		Connection conn = null;
		SubjectBean bean = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setLong(1, pk);
			
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				
				bean = new SubjectBean();
				
				bean.setId(1);
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
			rs.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting subject by pk");
		} 
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	 
	public List search(SubjectBean bean) throws DatabaseException, ApplicationException {
		return search(bean, 0, 0);
	}

	 	public List search(SubjectBean bean, int pageNo, int pageSize) throws DatabaseException, ApplicationException {
		
	 		StringBuffer sql = new StringBuffer("Select * from ST_SUBJECT where true");
		
	 		if (bean != null) {
	 			
			if (bean.getId() > 0) {
				sql.append(" AND ID = " + bean.getId());
				System.out.println("NOT null");
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND Subject_Name like '" + bean.getName() + "%'");
			}

			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" AND Description like '" + bean.getDescription() + "%'");
			}
			if (bean.getCourseId() > 0) {
				sql.append(" AND Course_id = " + bean.getCourseId());
			}
			if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
				sql.append(" AND course_Name like '" + bean.getCourseName() + "%'");
			}

		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList list = new ArrayList();
		
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			
			System.out.println(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				bean = new SubjectBean();
				
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				
				list.add(bean);
			}
			rs.close();
			
		} catch (Exception e) {
			throw new ApplicationException("Exception in the search" + e.getMessage());
		} 
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

	 
	public List list() throws Exception {
		return list(0, 0);
	}

	 	public List list(int pageNo, int pageSize) throws Exception {

		List list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_subject");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " ," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();
			
			SubjectBean bean;
			
			while (rs.next()) {
				
				bean = new SubjectBean();

				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));

				list.add(bean);
			}
			rs.close();
			pstmt.close();
			conn.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting list " + e.getMessage());

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		return list;
	}

}