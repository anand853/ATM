package com.atm.servletCapture;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.servlet.utils.Commons;
import com.servlet.utils.DataWrappers;
import com.servlet.utils.DriverLoader;

/**
 * Servlet implementation class CaptureFormData
 */
@WebServlet("/CaptureAccountDetails")
public class CaptureAccountDetailsFormData extends HttpServlet {

	PrintWriter out;
	static Logger logger = Logger.getLogger(CaptureAccountDetailsFormData.class);

	static Connection conn = null;
	static Statement stmt = null;
	static Statement stmt1 = null;

	static Integer lAccount = 0;
	static Integer lPin = 0;
	static Integer lAmount = 0;

	List accountDetails = new Vector();
	List trasaction = new Vector();
	List amountDetails = new Vector();

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
		String transactionType = request.getParameter("transactionType");

		logger.info("The account numer is ->" + lAccount);
		logger.info("The pin value is ->" + lPin);
		logger.info("The amount value is ->" + lAmount);
		logger.info("----doPost::transactionType value is -----" + transactionType);

		accountDetails.add(lAccount);
		accountDetails.add(lPin);
		accountDetails.add(lAmount);

		trasaction.add(lAmount);
		trasaction.add(lAccount);

		amountDetails.add(lAmount);
		amountDetails.add(transactionType);
		amountDetails.add(lAccount);

		boolean flag = DataWrappers.isPersist(conn, Commons.ACCUONTDETAILS_SELECT, lAccount);

		System.out.println(flag);
		if (flag) {
			DataWrappers.accountAvailable(out, conn, accountDetails, amountDetails);
			logger.info("Record is available");
			out.println("<h1>Record is available</h1>");
			out.println("<a href='http://localhost:8080/ATM/'>Back to ATM</a>");

		} else {
			logger.info("Record not available");
			out.println("<h1>Record not available</h1>");
			DataWrappers.dbPersistance(out, conn, Commons.ACCOUNTDETAILS_INSERT, accountDetails);
			DataWrappers.dbTrasactionPersistance(out, conn, Commons.TRANSACTION_INSERT, trasaction);
			DataWrappers.amountPersistance(out, conn, Commons.AMOUNT_INSERT, amountDetails);

		}

	}

}
