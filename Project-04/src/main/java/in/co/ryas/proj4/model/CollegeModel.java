package in.co.ryas.proj4.model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.ryas.proj4.Exception.ApplicationException;
import in.co.ryas.proj4.Exception.DatabaseException;
import in.co.ryas.proj4.Exception.DublicateRecordException;
import in.co.ryas.proj4.bean.CollegeBean;
import in.co.ryas.proj4.util.JDBCDataSource;

 public class CollegeModel {

	/**
	 * Find next PK of College
	 *
	 * @throws DatabaseException
	 */

	public Integer nextPK() throws DatabaseException {
		
		Connection conn = null;

         int pk = 0 ;
         
		try {
			
			conn = JDBCDataSource.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_college");
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				pk = rs.getInt(1);
			}
			rs.close();
			
		} catch (Exception e) {
			
			throw new DatabaseException("Exception :Exception in getting PK");

		} finally {
			
			JDBCDataSource.closeConnection(conn);
		}
		
		return pk + 1;
	}

	/**
	 * Add a College
	 *
	 * @param bean
	 * @throws DatabaseException
	 *
	 */

	public long add(CollegeBean bean) throws ApplicationException, DublicateRecordException {
		
		Connection conn = null;
		
		int pk = 0;
		
		CollegeBean duplicateCollegeName = findByName(bean.getName());

		if (duplicateCollegeName != null) {
			
			throw new DublicateRecordException("College Name alredy exists");

		}
		
		try {
			
			conn = JDBCDataSource.getConnection();
			
			pk = nextPK();

            conn.getAutoCommit();
            
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_COLLEGE VALUES(?,?,?,?,?,?,?,?,?,?)");
			
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getAddress());
			pstmt.setString(4, bean.getState());
			pstmt.setString(5, bean.getCity());
			pstmt.setString(6, bean.getPhoneNo());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());
			
			pstmt.executeUpdate();
			
			conn.commit();
			
			pstmt.close();
			
		} catch (Exception e) {
			
			try {
				
				conn.rollback();
				
			} catch (Exception ex) {
				
				ex.printStackTrace();
				
				throw new ApplicationException("Exception : add rollback exception" + ex.getMessage());
			}
			
			throw new ApplicationException("Exception : Exception in add college");
			
		} finally {
			
			JDBCDataSource.closeConnection(conn);
		}
		
		return pk;
	}

	/**
	 * Delete a College
	 *
	 * @param bean
	 * @throws DatabaseException
	 */

	public void delete(CollegeBean bean) throws ApplicationException {
		
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			
			conn.setAutoCommit(false);
			
			PreparedStatement pstmt = conn.prepareStatement("delete from st_college where id = ?");
			
			pstmt.setLong(1, bean.getId());
			
			pstmt.executeUpdate();
			
			conn.commit();
			
			pstmt.close();
			
		} catch (Exception e) {
			
			try {
				conn.rollback();
				
			} catch (Exception ex) {
				
				throw new ApplicationException("Exception :Delete rollback exception" + ex.getMessage());
			}
			
			throw new ApplicationException("Exception : Exception in delete College");
			
		} finally {
			
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Find User by College
	 *
	 * @param login : get parameter
	 * @return bean
	 * @throws DatabaseException
	 */

	public CollegeBean findByName(String name) throws ApplicationException {
		
		StringBuffer sql = new StringBuffer("select * from st_college where name = ?");
		
		CollegeBean bean = null;
		
		Connection conn = null;
		
		try {
			
			conn = JDBCDataSource.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, name);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				bean = new CollegeBean();
				
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));

			}
			rs.close();
			
		} catch (Exception e) {
			
			throw new ApplicationException("Exception : Exception in getting College byName");

		} finally {
			
			JDBCDataSource.closeConnection(conn);

		}
		
		return bean;
	}

	/**
	 * Find User by College
	 *
	 * @param pk : get parameter
	 * @return bean
	 * @throws DatabaseException
	 */

	public CollegeBean findByPK(long pk) throws ApplicationException {
		
		StringBuffer sql = new StringBuffer("select * from st_college where id = ?");
		
		CollegeBean bean = null;
		
		Connection conn = null;
		
		try {
			
			conn = JDBCDataSource.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setLong(1, pk);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				bean = new CollegeBean();
				
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();
			
		} catch (Exception e) {
			
			throw new ApplicationException("Exception : Exception is getting College byPK");
			
		} finally {
			
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	/**
	 * Update a College
	 *
	 * @param bean
	 * @throws DatabaseException
	 */

	public void update(CollegeBean bean) throws ApplicationException, DublicateRecordException {
		
		Connection conn = null;

		CollegeBean beanExist = findByName(bean.getName());

		// Check if updated College already exist
		if (beanExist != null && beanExist.getId() != bean.getId()) {

			throw new DublicateRecordException("College is already exist");
		}

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false); // Begin transaction
			
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_COLLEGE SET NAME=?,ADDRESS=?,STATE=?,CITY=?,PHONE_NO=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getAddress());
			pstmt.setString(3, bean.getState());
			pstmt.setString(4, bean.getCity());
			pstmt.setString(5, bean.getPhoneNo());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());
			pstmt.setLong(10, bean.getId());
			
			pstmt.executeUpdate();
			
			conn.commit(); // End transaction
			
			pstmt.close();
			
		} catch (Exception e) {
			
			try {
				conn.rollback();
				
			} catch (Exception ex) {
				
				throw new ApplicationException("Exception : update rollback exception " + ex.getMessage());
			}
			// throw new ApplicationException("Exception in updating College ");
		
		} finally {
			
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Search College
	 *
	 * @param bean : Search Parameters
	 * @throws DatabaseException
	 */

	public List search(CollegeBean bean) throws ApplicationException {
		
		return search(bean, 0, 0);
	}

	/**
	 * Search College with pagination
	 *
	 * @return list : List of College
	 * @param bean     : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 *
	 * @throws DatabaseException
	 */

	public List search(CollegeBean bean, int pageNo, int PageSize) throws ApplicationException {
		
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COLLEGE WHERE 1=1");

		if (bean != null) {
			
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			
			if (bean.getAddress() != null && bean.getAddress().length() > 0) {
				sql.append(" AND ADDRESS like '" + bean.getAddress() + "%'");
			}
			
			if (bean.getState() != null && bean.getState().length() > 0) {
				sql.append("AND STATE like'" + bean.getState() + "%'");
			}
			
			if (bean.getCity() != null && bean.getCity().length() > 0) {
				sql.append(" AND CITY like '" + bean.getCity() + "%'");
			}
			
			if (bean.getPhoneNo() != null && bean.getPhoneNo().length() > 0) {
				sql.append(" AND PHONE_NO = " + bean.getPhoneNo());
			}
		}
		
		// if page size is greater than zero then apply pagination
		if (PageSize > 0) {
			
			// Calculate start record index
			pageNo = (pageNo - 1) * PageSize;
			sql.append(" Limit " + pageNo + "," + PageSize);

		}
		ArrayList list = new ArrayList();

		Connection conn = null;
		
		try {
			
			conn = JDBCDataSource.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				bean = new CollegeBean();

				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				
				list.add(bean);
			}
			rs.close();
			
		} catch (Exception e) {
			
			throw new ApplicationException("Exception : Exception in search college");
		} finally {
			
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

	/**
	 * Get List of College
	 *
	 * @return list : List of College
	 * @throws DatabaseException
	 */

	public List list() throws ApplicationException {
		
		return list(0, 0);
	}

	/**
	 * Get List of College with pagination
	 *
	 * @return list : List of College
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws DatabaseException
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {
		
		ArrayList list = new ArrayList();
		
		StringBuffer sql = new StringBuffer("select * from ST_COLLEGE");
		
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;
		
		CollegeBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				bean = new CollegeBean();
				
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				
				list.add(bean);
			}
			rs.close();
			
		} catch (Exception e) {
			
			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			
			JDBCDataSource.closeConnection(conn);
		}

		return list;

	}

}