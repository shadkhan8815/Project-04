package in.co.ryas.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import in.co.ryas.proj4.Exception.DatabaseException;
import in.co.ryas.proj4.util.JDBCDataSource;

public class CollegeModel {

	public Integer nextPK() throws DatabaseException {

		Connection conn = null;

		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_college ");

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				pk = rs.getInt(1);
			}

			rs.close();

		} catch (Exception e) {

			throw new DatabaseException("Exception : Exception is getting pk ");

		}

		finally {

			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;

	}

}
