package com.atm.servletCapture;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

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
 * Servlet implementation class CaptureWithdrawalFormData
 */
@WebServlet("/CaptureWithdrawal")
public class CaptureWithdrawalFormData extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static Logger logger = null;

	PrintWriter out;

	private static String accountDeatilsIDValue;
	private static String withdrawalValue;
	private static String amountValue;

	static Connection conn = null;
	static Statement stmt = null;
	static Statement stmt1 = null;

	Integer accountDetailsInt;
	Integer withdrawalInt;
	Integer amountInt;

	List list = new Vector();

	public CaptureWithdrawalFormData() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// logger.info("in logger sir");
		out = response.getWriter();
		logger = DriverLoader.getLogger();
		conn = DriverLoader.loadDrivers();

		try {
			insertIntoDeposit(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		out = response.getWriter();

	}

	private void insertIntoDeposit(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		logger.info("----CaptureWithdrawalFormData::CaptureWithdrawalFormData()-----");

		accountDetailsInt = DataWrappers.getInts(request, response, "accountNumber");
		withdrawalInt = DataWrappers.getInts(request, response, "withdrawalID");
		amountInt = DataWrappers.getInts(request, response, "amount");

		list.add(accountDetailsInt);
		list.add(withdrawalInt);
		list.add(amountInt);

		out.println("<h1>The account is value is" + accountDetailsInt);
		out.println("The withdrawalID is value is" + withdrawalInt);
		out.println("The amout is value is" + amountInt + "</h1>");

		logger.info("The account is value is" + accountDetailsInt + "");
		out.print("<br>");
		logger.info("The withdrawalID is value is" + withdrawalInt + "");
		out.print("<br>");
		logger.info("The amout is value is" + amountInt + "");

		boolean flag = DataWrappers.isPersist(conn, Commons.WITHDRAWAL_SELECT, accountDetailsInt);
		logger.info(flag);

		if (flag) {
			logger.info("Record is available");
			out.println("<h1>Record is available</h1>");
			out.println("<a href='http://localhost:8080/ATM/index2.html/'>Back to Deposit</a>");

		} else {
			logger.info("Record not available");
			out.println("<h1>Record not available</h1>");
			DataWrappers.dbPersistance(out, conn, Commons.WITHDRAWAL_INSERT, list);
		}

	}

}
