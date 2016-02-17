package com.atm.servletCapture;

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

import org.apache.log4j.Logger;

import com.mysql.jdbc.Connection;
import com.servlet.utils.Commons;
import com.servlet.utils.DataWrappers;
import com.servlet.utils.DriverLoader;

/**
 * Servlet implementation class CaptureDepositFormData
 */
@WebServlet("/CaptureDeposit")
public class CaptureDepositFormData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static Logger logger = null;

	PrintWriter out;

	static Connection conn = null;
	static Statement stmt = null;
	static Statement stmt1 = null;

	Integer accountDetailsInt;
	Integer depositInt;
	Integer amountInt;

	public CaptureDepositFormData() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		out = response.getWriter();
		logger = DriverLoader.getLogger();
		conn = DriverLoader.loadDrivers();

		try {
			insertIntoDeposit(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		out = response.getWriter();

	}

	private void insertIntoDeposit(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		logger.info("----CaptureDepositFormData::insertIntoDeposit()-----");
		accountDetailsInt = DataWrappers.getInts(request, response, "accountDetailsID");
		depositInt = DataWrappers.getInts(request, response, "depositID");
		amountInt = DataWrappers.getInts(request, response, "amount");

		out.println("<h1>The account is value is" + accountDetailsInt);
		out.println("The depositID is value is" + depositInt);
		out.println("The amout is value is" + amountInt + "</h1>");

		logger.info("The account is value is" + accountDetailsInt + "");
		out.print("<br>");
		logger.info("The depositID is value is" + depositInt + "");
		out.print("<br>");
		logger.info("The amout is value is" + amountInt + "");

		boolean flag = DataWrappers.isPersist(conn, Commons.DEPOSIT_SELECT, depositInt);
		logger.info(flag);

		if (flag) {
			logger.info("Record is available");
			out.println("<h1>Record is available</h1>");
			out.println("<a href='http://localhost:8080/ATM/index2.html/'>Back to Deposit</a>");

		} else {
			logger.info("Record not available");
			out.println("<h1>Record not available</h1>");

			String insertTableSQL = "INSERT INTO DEPOSIT VALUES (?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
			preparedStatement.setInt(1, depositInt);
			preparedStatement.setInt(2, amountInt);
			preparedStatement.setInt(3, accountDetailsInt);

			preparedStatement.executeUpdate();
			logger.info(" ------ so new row saved in the database-----------");
			out.println("<h1> ------ so new row saved in the database-----------</h1>");
			out.println("<a href='http://localhost:8080/ATM/'>Back to ATM</a>");
		}

	}

}
