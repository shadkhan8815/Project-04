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
import in.co.ryas.proj4.util.JDBCDataSource;

public class CourseModel {

	/**
	 * Find next PK of Course
	 *
	 * @throws DatabaseException
	 */

	public Integer nextPK() throws DatabaseException {
		
		Connection conn = null;
		
		int pk = 0;
		
		try {

			conn = JDBCDataSource.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_COURSE");
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				pk = rs.getInt(1);
			}
			rs.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			System.out.println(e.getMessage());
			
			throw new DatabaseException("Exception : Exception in getting pk");

		} finally {
			
			JDBCDataSource.closeConnection(conn);
		}
		
		return  pk + 1;
	}

	/**
	 * Add a Course
	 *
	 * @param bean
	 * @throws DatabaseException
	 *
	 */

	public long add(CourseBean bean) throws ApplicationException, DublicateRecordException {
		
		Connection conn = null;
		
		int pk = 0;

		
		  CourseBean duplicatecourseName = findByName(bean.getName());
		  if(duplicatecourseName != null) { throw new
		  DublicateRecordException("course already exist"); }
		 

		try {
			conn = JDBCDataSource.getConnection();
			
			pk = nextPK();
			
			conn.setAutoCommit(false);
			
			PreparedStatement pstmt = conn.prepareStatement("INSERT  ST_COURSE VALUE(?,?,?,?,?,?,?,?)");
			
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getDescription());
			pstmt.setString(4, bean.getDuration());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			
			pstmt.executeUpdate();
			
			conn.commit();
			
			pstmt.close();
			
			conn.close();
			
		} catch (Exception e) {
			
			try {
				conn.rollback();
				
			} catch (Exception ex) {
				
				ex.printStackTrace();
				// throw new ApplicationException("Excetion : add rollback Exception "
				// +ex.getMessage());
				
			}
			
			// throw new ApplicationException("Exception : Exception in add course" );
		} finally {
			
			JDBCDataSource.closeConnection(conn);
		}
		
		return pk;

	}

	/**
	 * Delete a Course
	 *
	 * @param bean
	 * @throws DatabaseException
	 */
	public void delete(CourseBean bean) throws ApplicationException {
		
		Connection conn = null;
		
		try {
			
			conn = JDBCDataSource.getConnection();
			
			conn.setAutoCommit(false);
			
			PreparedStatement pstmt = conn.prepareStatement("DELETE  FROM ST_COURSE WHERE ID=?");
			
			pstmt.setLong(1, bean.getId());
			
			pstmt.executeUpdate();
			
			conn.commit();
			
			pstmt.close();
			
		} catch (Exception e) {
			
			try {
				
				conn.rollback();
				
			} catch (Exception ex) {
				
				throw new ApplicationException("Exception : Delete rollback Wxception " + ex.getMessage());
			}
			
			throw new ApplicationException("Exception in delete course");

		} finally {
			
			JDBCDataSource.closeConnection(conn);
		}

	}

	/**
	 * Find Course by Name
	 *
	 * @param login : get parameter
	 * @return bean
	 * @throws DatabaseException
	 */
	public CourseBean findByName(String name) throws ApplicationException {
		
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE NAME=?");
		
		CourseBean bean = null;
		
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, name);
			
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				
				bean = new CourseBean();
				
				bean.setId(1);
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setDuration(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));

			}
			rs.close();
			
		} catch (Exception e) {
			
//				 throw new ApplicationException("Exception in getting course by name");
		} finally {
			
			JDBCDataSource.closeConnection(conn);
		}
		
		return bean;

	}

	/**
	 * Find Course by PK
	 *
	 * @param pk : get parameter
	 * @return bean
	 * @throws DatabaseException
	 */

	public CourseBean findByPK(long pk) throws ApplicationException {
		
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE ID=?");
		
		Connection conn = null;
		
		CourseBean bean = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setLong(1, pk);
			
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				
				bean = new CourseBean();
				
				bean.setId(1);
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setDuration(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
			}
			
			rs.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting course by pk");
			
		} finally {
			
			JDBCDataSource.closeConnection(conn);
		}
		
		return bean;
	}

	/**
	 * Update a Course
	 *
	 * @param bean
	 * @throws DatabaseException
	 */

	public void update(CourseBean bean) throws ApplicationException, DublicateRecordException {
		
		Connection conn = null;

		CourseBean beanExist = findByName(bean.getName());
		
		if (beanExist != null && beanExist.getId() != bean.getId()) {
			
			throw new DublicateRecordException("Course is alredy Exist");
		}
		
		try {
			conn = JDBCDataSource.getConnection();
			
			conn.setAutoCommit(false);
			
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_COURSE SET NAME=?,DESCRIPTION=?,DURATION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");

			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getDescription());
			pstmt.setString(3, bean.getDuration());
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreatedDatetime());
			pstmt.setTimestamp(7, bean.getModifiedDatetime());
			pstmt.setLong(8, bean.getId());
			
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
//				 throw new ApplicationException("Exception in updatingcourse" );
		} finally {
			
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Search Course
	 *
	 * @param bean : Search Parameters
	 * @throws DatabaseException
	 */

	public List search(CourseBean bean) throws DatabaseException, ApplicationException {
		
		return search(bean, 0, 0);
	}

	/**
	 * Search Course with pagination
	 *
	 * @return list : List of Course
	 * @param bean     : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 *
	 * @throws DatabaseException
	 */

	public List search(CourseBean bean, int pageNo, int pageSize) throws DatabaseException, ApplicationException {
		
		StringBuffer sql = new StringBuffer("Select * from ST_COURSE where 1=1");
		
		if (bean != null) {
			
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND Name like '" + bean.getName() + "%'");
			}
			
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" AND Description like '" + bean.getDescription() + "%'");
			}
			
			if (bean.getDuration() != null && bean.getDuration().length() > 0) {
				sql.append(" AND Duration like '" + bean.getDuration() + "%'");
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
				
				bean = new CourseBean();
				
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setDuration(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				
				list.add(bean);
			}
			
			rs.close();
			
		} catch (Exception e) {
			
			throw new ApplicationException("Exception in the search" + e.getMessage());
		
		} finally {
			
			JDBCDataSource.closeConnection(conn);
		}
		
		return list;
	}

	/**
	 * Get List of Course
	 *
	 * @return list : List of Course
	 * @throws DatabaseException
	 */

	public List list() throws Exception {
		
		return list(0, 0);
	}

	/**
	 * Get List of Course with pagination
	 *
	 * @return list : List of Course
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws DatabaseException
	 */

	public List list(int pageNo, int pageSize) throws Exception {

		List list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_course");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " ," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();
			
			CourseBean bean;
			
			while (rs.next()) {
				
				bean = new CourseBean();

				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDuration(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));

				list.add(bean);
			}
			rs.close();
			
			pstmt.close();
			
			conn.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			throw new ApplicationException("Exception : Exception in getting lidt " + e.getMessage());

		} finally {
			
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

}