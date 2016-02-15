package com.atm.servletCapture;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import com.appstechs.beans.AccountDetails;
import com.mysql.fabric.Response;
import com.mysql.jdbc.Connection;

import jdk.nashorn.internal.ir.RuntimeNode.Request;

/**
 * Servlet implementation class CaptureFormData
 */
@WebServlet("/CaptureFormData")
public class CaptureFormData extends HttpServlet {

	PrintWriter out;

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/USA";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "root";

	static Connection conn = null;
	static Statement stmt = null;
	static Statement stmt1 = null;

	static Integer lAccount = 0;
	static Integer lPin = 0;
	static Integer lAmount = 0;

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
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

	public CaptureFormData() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter();
		try {
			insertIntoAccountDetails(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void insertIntoAccountDetails(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {

		String localAccountNumner = request.getParameter("accountNumber");
		lAccount = Integer.parseInt(localAccountNumner);
		String localPin = request.getParameter("pin");
		lPin = Integer.parseInt(localPin);
		String localAmount = request.getParameter("amount");
		lAmount = Integer.parseInt(localAmount);

		boolean flag = saveTheRow();
		System.out.println(flag);
		if (flag) {
			System.out.println("Record is available");
			out.println("Record is available");
			// out.println("<a href='http://localhost:8080/ATM/'>Back to ATM
			// W3Schools</a>");

		} else {
			System.out.println("Record not available");
			out.println("Record not available");

			String insertTableSQL = "INSERT INTO ACCOUNT_DETAILS VALUES (?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
			preparedStatement.setInt(1, lAccount);
			preparedStatement.setInt(2, lPin);
			preparedStatement.setInt(3, lAmount);

			preparedStatement.executeUpdate();
			System.out.println(" ------ so new row saved in the database-----------");
			out.println(" ------ so new row saved in the database-----------");
			out.println("<a href='http://localhost:8080/ATM/'>Back to ATM W3Schools</a>");
		}

	}

	private static boolean saveTheRow() throws SQLException {

		String sql = "SELECT * FROM ACCOUNT_DETAILS WHERE ACCOUNT_DETAILS_ID=?";

		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, lAccount);
		ResultSet rs = ps.executeQuery();
		return rs.next();

	}

}
