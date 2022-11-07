package com.trangialam.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLDBManager implements IDBManager {

	private static final String URL = "jdbc:mysql://localhost:3306/shop";
	private static final String USER = "root";
	private static final String PASSWORD = "lam123";
	Connection conn;

	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub

		if (conn == null || conn.isClosed()) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new SQLException(e);
			}
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		}

		return conn;
	}

	public void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

	}

	public void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

	public void closeStatement(ResultSet rs, Statement stmt) {
		closeResultSet(rs);
		closeStatement(stmt);

	}

	public void fastcloseStmt(PreparedStatement pstmt) throws SQLException {
		pstmt.close();

	}

	public void fastcloseStmt(ResultSet rs, PreparedStatement pstmt) throws SQLException {
		// TODO Auto-generated method stub
		rs.close();
		pstmt.close();
	}

	public void closeConnection(ResultSet rs, Statement stmt, Connection con) {
		closeResultSet(rs);
		closeStatement(stmt);
		closeConnection(con);

	}

	public void closeConnection(Statement stmt, Connection con) {
		closeStatement(stmt);
		closeConnection(con);

	}

	public void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

}
