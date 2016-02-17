package com.servlet.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;

public class DataWrappers {

	private static String iString;
	private static int iValue;

	public static Integer getInts(HttpServletRequest request, HttpServletResponse response, String string) {

		iString = request.getParameter(string);
		return iValue = Integer.parseInt(iString);

	}

	public static boolean isPersist(Connection conn, String sql, int depositInt) throws SQLException {
		System.out.println("----isPersist-----");
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, depositInt);
		ResultSet rs = ps.executeQuery();
		return rs.next();
	}
}
