package com.trangialam.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface IDBManager {

	public Connection getConnection() throws SQLException;

	public void closeResultSet(ResultSet rs);

	public void closeStatement(Statement stmt);

	public void closeStatement(ResultSet rs, Statement stmt);

	public void fastcloseStmt(PreparedStatement pstmt) throws SQLException;

	public void fastcloseStmt(ResultSet rs, PreparedStatement pstmt) throws SQLException;

	public void closeConnection(ResultSet rs, Statement stmt, Connection con);

	public void closeConnection(Statement stmt, Connection con);

	public void closeConnection(Connection con);
}
