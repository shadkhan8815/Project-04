package in.co.ryas.proj4.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.ryas.proj4.Exception.ApplicationException;
import in.co.ryas.proj4.Exception.DatabaseException;
import in.co.ryas.proj4.Exception.DublicateRecordException;
import in.co.ryas.proj4.bean.CollegeBean;
import in.co.ryas.proj4.bean.StudentBean;
import in.co.ryas.proj4.util.JDBCDataSource;
 

public class StudentModel {

	public int nextPK() throws DatabaseException {

		int pk = 0;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(ID) from st_student");

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

	
	
	public long add(StudentBean bean) throws Exception {

		CollegeModel collegemodel = new CollegeModel();
		CollegeBean collegebean = collegemodel.findByPK(bean.getCollegeID());
		bean.setCollegeName(collegebean.getName());
		
		StudentBean dublicatename = findbyEmail(bean.getEmail());
		
		int pk = 0 ;
		
		if (dublicatename != null) {
			throw new DublicateRecordException("Email already exists");

		}
		
		Connection conn = null;

		  pk = nextPK();

		try {
			System.out.println(pk + " in ModelJDBC");

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_student values (?,?,?,?,?,?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getFirstName());
			pstmt.setString(3, bean.getLastName());
			pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(5, bean.getGender());
			pstmt.setString(6, bean.getMobileNo());
			pstmt.setString(7, bean.getEmail());
			pstmt.setLong(8, bean.getCollegeID());
			pstmt.setString(9, bean.getCollegeName());
			pstmt.setString(10, bean.getCreatedBy());
			pstmt.setString(11, bean.getModifiedBy());
			pstmt.setTimestamp(12, bean.getCreatedDatetime());
			pstmt.setTimestamp(13, bean.getModifiedDatetime());

			int i = pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

			System.out.println("data inserted => " + i);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			try {
				conn.rollback();

			} catch (Exception ex) {
				
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Student");
		} 
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	
	
	public StudentBean findbyPK(long pk) throws Exception {

		StringBuffer sql = new StringBuffer("select * from student where id = ?");
		
		StudentBean bean = null;

		Connection conn = null ;
		
  try {
		conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement( sql.toString());

		pstmt.setLong(1, pk);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			
			bean = new StudentBean();
			
			bean.setId(rs.getLong(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setDob(rs.getDate(4));
			bean.setGender(rs.getString(5));
			bean.setMobileNo(rs.getString(6));
			bean.setEmail(rs.getString(7));
			bean.setCollegeID(rs.getLong(8));
			bean.setCollegeName(rs.getString(9));
			bean.setCreatedBy(rs.getString(10));
			bean.setModifiedBy(rs.getString(11));
			bean.setCreatedDatetime(rs.getTimestamp(12));
			bean.setModifiedDatetime(rs.getTimestamp(13));
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

	
	
	
	public void update(StudentBean bean) throws Exception {
		
		StudentBean existbean = findbyEmail(bean.getEmail());
		
		if (existbean != null && existbean.getId() != bean.getId()) {
			throw new DublicateRecordException("Email Id is already exist");

		}
		CollegeModel collegemodel = new CollegeModel();
		CollegeBean collegebean = collegemodel.findByPK(bean.getCollegeID());
		bean.setCollegeName(collegebean.getName());

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
"update st_student set first_name=?, last_name=?, dob=?, gender=?, mobile_no=?, email=?, college_id=?, college_name=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=? ");

			pstmt.setString(1, bean.getFirstName());
			pstmt.setString(2, bean.getLastName());
			pstmt.setDate(3, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(4, bean.getGender());
			pstmt.setString(5, bean.getMobileNo());
			pstmt.setString(6, bean.getEmail());
			pstmt.setLong(7, bean.getCollegeID());
			pstmt.setString(8, bean.getCollegeName());
			pstmt.setString(9, bean.getCreatedBy());
			pstmt.setString(10, bean.getModifiedBy());
			pstmt.setTimestamp(11, bean.getCreatedDatetime());
			pstmt.setTimestamp(12, bean.getModifiedDatetime());
			pstmt.setLong(13, bean.getId());

			int i = pstmt.executeUpdate();

			conn.commit();
			pstmt.close();
			
			System.out.println("data updated => " + i);
		}
		catch (Exception e) {
			
			try {
				conn.rollback();
				
			} catch (Exception ex) {
				// throw new ApplicationException("Exception : Delete rollback
				// exception"+ex.getMessage());
			}
			// throw new ApplicationException("Exception in updating Student");
		} 
		finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	
	

	public List search(StudentBean bean) throws Exception {
		return search(bean, 0, 0);
	}
	
	
	
	public List search(StudentBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("select * from st_student where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sql.append(" AND FIRST_NAME like '" + bean.getFirstName() + "%'");
			}
			if (bean.getLastName() != null && bean.getLastName().length() > 0) {
				sql.append(" AND LAST_NAME like '" + bean.getLastName() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getDate() > 0) {
				sql.append(" AND DOB = " + bean.getDob());
			}
			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" AND MOBILE_NO like '" + bean.getMobileNo() + "%'");
			}
			if (bean.getEmail() != null && bean.getEmail().length() > 0) {
				sql.append(" AND EMAIL_ID like '" + bean.getEmail() + "%'");
			}
			if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {
				sql.append(" AND COLLEGE_NAME = '" + bean.getCollegeName() + "%'");
			}
			if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {
				sql.append(" AND COLLEGE_NAME = '" + bean.getCollegeName() + "%'");
			}
			if (bean.getCollegeID() > 0) {
				sql.append(" AND COLLEGE_ID = '" + bean.getCollegeID() + "%'");
			}
		}
		
		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
 		}
		
		ArrayList list = new ArrayList();

        Connection conn = null ; 
        
   try { 
       conn  = JDBCDataSource.getConnection();
 
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			
			bean = new StudentBean();
			
			bean.setId(rs.getInt(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setDob(rs.getDate(4));
			bean.setGender(rs.getString(5));
			bean.setMobileNo(rs.getString(6));
			bean.setEmail(rs.getString(7));
			bean.setCollegeID(rs.getLong(8));
			bean.setCollegeName(rs.getString(9));
			bean.setCreatedBy(rs.getString(10));
			bean.setModifiedBy(rs.getString(11));
			bean.setCreatedDatetime(rs.getTimestamp(12));
			bean.setModifiedDatetime(rs.getTimestamp(13));

			list.add(bean);
		}
		rs.close();
		
	} catch (Exception e) {
		throw new ApplicationException("Exception : Exception in search Student");
	} 
   finally {
		JDBCDataSource.closeConnection(conn);
	}

	return list;
}
	
	
	public StudentBean findbyEmail(String email) throws Exception {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_STUDENT WHERE EMAIL_id=?");
		
		StudentBean bean = null;
		
		Connection conn = null;
		
  try {
	    conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select * from st_student where email = ?");

		pstmt.setString(1, email);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			
			bean = new StudentBean();
			
			bean.setId(rs.getInt(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setDob(rs.getDate(4));
			bean.setGender(rs.getString(5));
			bean.setMobileNo(rs.getString(6));
			bean.setEmail(rs.getString(7));
			bean.setCollegeID(rs.getLong(8));
			bean.setCollegeName(rs.getString(9));
			bean.setCreatedBy(rs.getString(10));
			bean.setModifiedBy(rs.getString(11));
			bean.setCreatedDatetime(rs.getTimestamp(12));
			bean.setModifiedDatetime(rs.getTimestamp(13));

		}
		
		rs.close();
		
	} catch (Exception e) {
		
		// throw new ApplicationException("Exception : Exception in getting User by
		// Email");
	}
  finally {
	  
		JDBCDataSource.closeConnection(conn);
	}
	return bean;
}


	
	public void delete(StudentBean bean) throws Exception {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			
			conn.setAutoCommit(false);
			
			PreparedStatement pstmt = conn.prepareStatement("delete from st_student where id = ?");
			
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
	
	
	
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		
		ArrayList list = new ArrayList();
		
		StringBuffer sql = new StringBuffer("select * from ST_STUDENT");

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
				
				StudentBean bean = new StudentBean();
				
				bean.setId(rs.getLong(1));
				bean.setCollegeID(rs.getLong(2));
				bean.setCollegeName(rs.getString(3));
				bean.setFirstName(rs.getString(4));
				bean.setLastName(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setEmail(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				
				list.add(bean);
			}
			rs.close();
			
		} catch (Exception e) {
			
			throw new ApplicationException("Exception : Exception in getting list of Student");
		}
		finally {
			
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

}