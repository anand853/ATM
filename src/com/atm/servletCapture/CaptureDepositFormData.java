package com.atm.servletCapture;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;

/**
 * Servlet implementation class CaptureDepositFormData
 */
@WebServlet("/CaptureDepositFormData")
public class CaptureDepositFormData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	PrintWriter out;

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/USA";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "root";

	private static String accountDeatilsIDValue;

	private static String depositValue;

	private static String amountValue;

	static Connection conn = null;
	static Statement stmt = null;
	static Statement stmt1 = null;

	Integer accountDetailsInt;
	Integer depositInt;
	Integer amountInt;

	public CaptureDepositFormData() {
		super();
	}

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		out = response.getWriter();

		try {
			insertIntoDeposit(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		out = response.getWriter();

	}

	private void insertIntoDeposit(HttpServletRequest request, HttpServletResponse response) throws SQLException {

		depositValue = request.getParameter("depositID");
		depositInt = Integer.parseInt(depositValue);

		amountValue = request.getParameter("amount");
		amountInt = Integer.parseInt(amountValue);

		accountDeatilsIDValue = request.getParameter("accountDetailsID");
		accountDetailsInt = Integer.parseInt(accountDeatilsIDValue);

		out.println("<h1>The account is value is" + accountDetailsInt);
		out.println("The depositID is value is" + depositInt);
		out.println("The amout is value is" + amountInt + "</h1>");

		System.out.println("The account is value is" + accountDetailsInt + "");
		out.print("<br>");
		System.out.println("The depositID is value is" + depositInt + "");
		out.print("<br>");
		System.out.println("The amout is value is" + amountInt + "");

		boolean flag = saveTheRow();
		System.out.println(flag);

		if (flag) {
			System.out.println("Record is available");
			out.println("<h1>Record is available</h1>");
			out.println("<a href='http://localhost:8080/ATM/index2.html/'>Back to Deposit</a>");

		} else {
			System.out.println("Record not available");
			out.println("<h1>Record not available</h1>");

			String insertTableSQL = "INSERT INTO DEPOSIT VALUES (?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
			preparedStatement.setInt(1, depositInt);
			preparedStatement.setInt(2, amountInt);
			preparedStatement.setInt(3, accountDetailsInt);

			preparedStatement.executeUpdate();
			System.out.println(" ------ so new row saved in the database-----------");
			out.println("<h1> ------ so new row saved in the database-----------</h1>");
			out.println("<a href='http://localhost:8080/ATM/index2.html/'>Back to Deposit</a>");
		}

	}

	private boolean saveTheRow() throws SQLException {

		String sql = "SELECT * FROM DEPOSIT WHERE DEPOSIT_ID=?";

		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, depositInt);
		ResultSet rs = ps.executeQuery();
		return rs.next();

	}
}
