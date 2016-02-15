package com.appstechs.beans;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Connection;

public class Demo {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/USA";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "root";

	static Connection conn = null;
	static Statement stmt = null;
	static Statement stmt1 = null;

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			System.out.println("conection established...");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws SQLException {

		boolean flag = hasRecord();
		System.out.println(flag);
		if (flag) {
			System.out.println("Record is available");
		} else {
			System.out.println("Record not available");
		}
	}

	private static boolean hasRecord() throws SQLException {
		String sql = "SELECT * FROM ACCOUNT_DETAILS WHERE ACCOUNT_DETAILS_ID=?";

		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, 4);
		ResultSet rs = ps.executeQuery();
		return rs.next();

	}

}
