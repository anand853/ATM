package com.atm.servletCapture;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.mysql.jdbc.Connection;
import com.servlet.utils.DataWrappers;
import com.servlet.utils.DriverLoader;


/**
 * Servlet implementation class CaptureFormData
 */
@WebServlet("/CaptureAccountDetails")
public class CaptureAccountDetailsFormData extends HttpServlet {

	PrintWriter out;
	static Logger logger = Logger.getLogger(CaptureAccountDetailsFormData.class);
	String sql = "SELECT * FROM ACCOUNT_DETAILS WHERE ACCOUNT_DETAILS_ID=?";

	static Connection conn = null;
	static Statement stmt = null;
	static Statement stmt1 = null;

	static Integer lAccount = 0;
	static Integer lPin = 0;
	static Integer lAmount = 0;

	private static final long serialVersionUID = 1L;

	public CaptureAccountDetailsFormData() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		conn = DriverLoader.loadDrivers();
		logger.info("----CaptureAccountDetailsFormData::doPost()-----");

		logger = DriverLoader.getLogger();
		System.out.println("logger ends here");
		// response.setContentType("text/html");
		out = response.getWriter();
		try {
			insertIntoAccountDetails(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void insertIntoAccountDetails(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		
		logger.info("----CaptureAccountDetailsFormData::insertIntoAccountDetails()-----");

		lAccount = DataWrappers.getInts(request, response, "accountNumber");
		lPin = DataWrappers.getInts(request, response, "pin");
		lAmount = DataWrappers.getInts(request, response, "amount");

		logger.info("The account numer is ->" + lAccount);
		logger.info("The pin value is ->" + lPin);
		logger.info("The amount value is ->" + lAmount);

		boolean flag = DataWrappers.isPersist(conn, sql, lAccount);
		System.out.println(flag);
		if (flag) {
			logger.info("Record is available");
			out.println("<h1>Record is available</h1>");
			out.println("<a href='http://localhost:8080/ATM/'>Back to ATM</a>");

		} else {
			logger.info("Record not available");
			out.println("<h1>Record not available</h1>");

			String insertTableSQL = "INSERT INTO ACCOUNT_DETAILS VALUES (?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
			preparedStatement.setInt(1, lAccount);
			preparedStatement.setInt(2, lPin);
			preparedStatement.setInt(3, lAmount);

			preparedStatement.executeUpdate();
			logger.info(" ------ so new row saved in the database-----------");
			out.println("<h1> ------ so new row saved in the database-----------</h1>");
			out.println("<a href='http://localhost:8080/ATM/'>Back to ATM</a>");
		}

	}

}
