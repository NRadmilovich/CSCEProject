package com.bc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {
	public static final String PARAMETERS = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	public static final String USERNAME = "nradmilo";
	public static final String PASSWORD = "c9F-Rp";
	public static final String URL = "jdbc:mysql://cse.unl.edu/" + USERNAME + PARAMETERS;
	
	public static Connection connectionBuilder() {
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return conn;
	}
	public static void close(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(PreparedStatement pre) {
		try {
			if (pre != null && !pre.isClosed()) {
				pre.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}public static void close(ResultSet rs) {
		try {
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
