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
import in.co.ryas.proj4.bean.UserBean;
import in.co.ryas.proj4.util.JDBCDataSource;
 

public class UserModel {

	public int nextPK() throws DatabaseException {

		int pk = 0;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(ID) from st_user");

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}

		}catch (Exception e) {
			
			throw new DatabaseException("Exception : Exception in getting PK " + e);
		} 
		finally {
			
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	
	
	public UserBean findbypk(long id) throws Exception {
		
		Connection conn = null ;
		
		UserBean bean = null ;
try {
	    conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select * from st_user where id = ?");

		pstmt.setLong(1, id);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			
			bean = new UserBean();
			
			bean.setId(rs.getLong(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setLogin(rs.getString(4));
			bean.setPassword(rs.getString(5));
			bean.setDob(rs.getDate(6));
			bean.setMobileNo(rs.getString(7));
			bean.setRoleId(rs.getLong(8));
			bean.setGender(rs.getString(9));
			bean.setCreatedBy(rs.getString(10));
			bean.setModifiedBy(rs.getString(11));
			bean.setCreatedDatetime(rs.getTimestamp(12));
			bean.setModifiedDatetime(rs.getTimestamp(13));
		}
	} catch (Exception e) {
		throw new ApplicationException("Exception : Exception in getting User by PK");
	} 
      finally {
		JDBCDataSource.closeConnection(conn);
	}
	return bean;
}

	
	
	public void add(UserBean bean) throws Exception {

		Connection conn = null;
		
		int pk = 0;

		UserBean existBean = findbyLogin(bean.getLogin());

		if (existBean != null) {
			throw new DublicateRecordException("login already exist..!!");
		}

		 pk = nextPK();

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_user values (?,?,?,?,?,?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getFirstName());
			pstmt.setString(3, bean.getLastName());
			pstmt.setString(4, bean.getLogin());
			pstmt.setString(5, bean.getPassword());
			pstmt.setDate(6, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(7, bean.getMobileNo());
			pstmt.setLong(8, bean.getRoleId());
			pstmt.setString(9, bean.getGender());
			pstmt.setString(10, bean.getCreatedBy());
			pstmt.setString(11, bean.getModifiedBy());
			pstmt.setTimestamp(12, bean.getCreatedDatetime());
			pstmt.setTimestamp(13, bean.getModifiedDatetime());

			int i = pstmt.executeUpdate();

			conn.commit();

			System.out.println("data inserted => " + i);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			try {
				conn.rollback();
				
			} catch (Exception ex) {
				
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add User " + e);
		} 
		finally {
			
			JDBCDataSource.closeConnection(conn);
		}
	}

	
	
	public void update(UserBean bean) throws Exception {

		Connection conn = null;
		
		UserBean existBean = findbyLogin(bean.getLogin());

		if (existBean != null && bean.getId() != existBean.getId()) {
			throw new DublicateRecordException("login already exist..!!");
		}

		try {
			
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_user set first_name=?, last_name=?, login=?, password=?, dob=?, mobile_no=?, role_id=?, gender=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getFirstName());
			pstmt.setString(2, bean.getLastName());
			pstmt.setString(3, bean.getLogin());
			pstmt.setString(4, bean.getPassword());
			pstmt.setDate(5, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(6, bean.getMobileNo());
			pstmt.setLong(7, bean.getRoleId());
			pstmt.setString(8, bean.getGender());
			pstmt.setString(9, bean.getCreatedBy());
			pstmt.setString(10, bean.getModifiedBy());
			pstmt.setTimestamp(11, bean.getCreatedDatetime());
			pstmt.setTimestamp(12, bean.getModifiedDatetime());
			pstmt.setLong(13, bean.getId());

			int i = pstmt.executeUpdate();

			conn.commit();

			System.out.println("data updated => " + i);
			
		} catch (Exception e) {
			
			try {
				conn.rollback();
				
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating User " + e);
		} 
		finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	
	
	public List search(UserBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("select * from st_user where 1=1");

		if (bean != null) {

			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sql.append(" and first_name like '" + bean.getFirstName() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getTime() > 0) {
				sql.append(" and dob like '" + new java.sql.Date(bean.getDob().getTime()) + "%'");
			}
			if (bean.getRoleId() > 0) {
				sql.append(" and role_id = " + bean.getRoleId());
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}


		System.out.println("sql ==>> " + sql.toString());

		Connection conn = null ;
		
		List list = new ArrayList();
		
	try {
		conn = JDBCDataSource.getConnection();
		
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			
			bean = new UserBean();
			
			bean.setId(rs.getInt(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setLogin(rs.getString(4));
			bean.setPassword(rs.getString(5));
			bean.setDob(rs.getDate(6));
			bean.setMobileNo(rs.getString(7));
			bean.setRoleId(rs.getLong(8));
			bean.setGender(rs.getString(9));
			bean.setCreatedBy(rs.getString(10));
			bean.setModifiedBy(rs.getString(11));
			bean.setCreatedDatetime(rs.getTimestamp(12));
			bean.setModifiedDatetime(rs.getTimestamp(13));

			list.add(bean);
		}
		
	} catch (Exception e) {
		throw new ApplicationException("Exception : Exception in search user " + e);
	} 
	finally {
		
		JDBCDataSource.closeConnection(conn);
	}
	
	return list;
}

	
	public UserBean findbyLogin(String login) throws Exception {
		
		Connection conn = null ;
		UserBean bean = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement("select * from st_user where login = ?");

			pstmt.setString(1, login);

			ResultSet rs = pstmt.executeQuery();
					
			while (rs.next()) {
			
				bean = new UserBean();
				
				bean.setId(rs.getInt(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setGender(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDatetime(rs.getTimestamp(12));
				bean.setModifiedDatetime(rs.getTimestamp(13));

			}
			 
			
		}
			 catch (Exception e) {
				 
				 JDBCDataSource.closeConnection(conn);
			 }
			return bean;
		}
		

		 	
	public void delete(long id) throws Exception {
		
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			
			conn.setAutoCommit(false);
			
			PreparedStatement pstmt = conn.prepareStatement("delete from st_user where id = ?");
			
			pstmt.setLong(1, id);

			int i = pstmt.executeUpdate();
			
			conn.commit();

			JDBCDataSource.closeConnection(conn);
			
			System.out.println("data deleted => " + i);

		} catch (Exception e) {
			
			try {
				conn.rollback();
				
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete User " + e);
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public UserBean authenticate(String loginId, String password) throws ApplicationException {

		Connection conn = null;
		UserBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_user where login = ? and password = ?");

			pstmt.setString(1, loginId);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				
				bean = new UserBean();
				
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setGender(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDatetime(rs.getTimestamp(12));
				bean.setModifiedDatetime(rs.getTimestamp(13));
			}
		} 
		catch (Exception e) {
			throw new ApplicationException("Exception : Exception in get roles " + e);
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}


	}
