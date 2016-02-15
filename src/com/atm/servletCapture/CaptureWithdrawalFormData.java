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
 * Servlet implementation class CaptureWithdrawalFormData
 */
@WebServlet("/CaptureWithdrawalFormData")
public class CaptureWithdrawalFormData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	PrintWriter out;

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/USA";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "root";

	private static String accountDeatilsIDValue;

	private static String withdrawalValue;

	private static String amountValue;

	static Connection conn = null;
	static Statement stmt = null;
	static Statement stmt1 = null;

	Integer accountDetailsInt;
	Integer withdrawalInt;
	Integer amountInt;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CaptureWithdrawalFormData() {
		super();
		// TODO Auto-generated constructor stub
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

		withdrawalValue = request.getParameter("withdrawalID");
		withdrawalInt = Integer.parseInt(withdrawalValue);

		amountValue = request.getParameter("amount");
		amountInt = Integer.parseInt(amountValue);

		accountDeatilsIDValue = request.getParameter("accountNumber");
		accountDetailsInt = Integer.parseInt(accountDeatilsIDValue);

		out.println("<h1>The account is value is" + accountDetailsInt);
		out.println("The withdrawalID is value is" + withdrawalInt);
		out.println("The amout is value is" + amountInt + "</h1>");

		System.out.println("The account is value is" + accountDetailsInt + "");
		out.print("<br>");
		System.out.println("The withdrawalID is value is" + withdrawalInt + "");
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

			String insertTableSQL = "INSERT INTO WITHDRAWAL VALUES (?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
			preparedStatement.setInt(1, withdrawalInt);
			preparedStatement.setInt(2, amountInt);
			preparedStatement.setInt(3, accountDetailsInt);

			preparedStatement.executeUpdate();
			System.out.println(" ------ so new row saved in the database-----------");
			out.println("<h1> ------ so new row saved in the database-----------</h1>");
			out.println("<a href='http://localhost:8080/ATM/index2.html/'>Back to Deposit</a>");
		}

	}

	private boolean saveTheRow() throws SQLException {

		String sql = "SELECT * FROM WITHDRAWAL WHERE WITHDRAWAL_ID=?";

		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, withdrawalInt);
		ResultSet rs = ps.executeQuery();
		return rs.next();

	}
}
