package com.servlet.utils;

import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.atm.servletCapture.CaptureAccountDetailsFormData;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DataWrappers {

	private static String iString;
	private static int iValue;

	static Logger logger = Logger.getLogger(DataWrappers.class);

	public static Integer getInts(HttpServletRequest request, HttpServletResponse response, String string) {

		iString = request.getParameter(string);
		return iValue = Integer.parseInt(iString);

	}

	public static boolean isPersist(Connection conn, String sql, int depositInt) throws SQLException {
		logger.info("----DataWrappers::isPersist()-----");
		logger.debug("----dbPersistance::depositInt value-----" + depositInt);
		PreparedStatement ps = conn.prepareStatement(sql);
		logger.info("----dbPersistance::ps -----" + ps);
		ps.setInt(1, depositInt);
		logger.debug("----dbPersistance::sql value-----" + sql);
		ResultSet rs = ps.executeQuery();
		return rs.next();
	}

	public static void dbPersistance(PrintWriter out, Connection conn, String insertSql, List list)
			throws SQLException {
		logger.info("----dbPersistance::dbPersistance()-----");
		Integer account = (Integer) list.get(0);
		Integer pin = (Integer) list.get(1);
		Integer amount = (Integer) list.get(2);

		logger.info("----dbPersistance::account()-----" + account + "------------ " + account.getClass());
		logger.info("----dbPersistance::ipin value()-----" + pin + "-----------" + pin.getClass());
		logger.info("----dbPersistance::amount value()-----" + amount + "-----------" + amount.getClass());

		PreparedStatement preparedStatement = conn.prepareStatement(insertSql);
		preparedStatement.setInt(1, account);
		preparedStatement.setInt(2, pin);

		preparedStatement.executeUpdate();
		logger.info(" ------ DataWrappers::dbPersistance()-----------");
		logger.info(" ------ INSERT QUERY---------" + insertSql + "params are " + account + " , " + pin);
		logger.info(" ------ so new row saved in the database*-----------");
		out.println("<h1> ------ so new row saved in the database-----------</h1>");
		out.println("<a href='http://localhost:8080/ATM/'>Back to ATM</a>");

	}

	public static void amountPersistance(PrintWriter out, Connection conn, String amountInsert, List trasaction)
			throws SQLException {

		ResultSet rs1, rs2;
		logger.info("----dbPersistance::amountPersistance()-----");
		Integer amount = (Integer) trasaction.get(0);
		String transaction_type = (String) trasaction.get(1);
		Integer accountNumber = (Integer) trasaction.get(2);

		// getting account details id
		String sql = "SELECT ACCOUNT_DETAILS_ID FROM ACCOUNT_DETAILS WHERE ACCOUNT_NUMBER=?";
		PreparedStatement preparedStatement0 = conn.prepareStatement(sql);
		preparedStatement0.setInt(1, accountNumber);

		rs1 = preparedStatement0.executeQuery();
		rs1.next();
		Integer accountDetailsID = rs1.getInt("ACCOUNT_DETAILS_ID");
		//

		// getting transaction details id
		String sql2 = "SELECT TRANSACTION_ID FROM TRANSACTION WHERE ACCOUNT_DETAILS_ID=?";
		PreparedStatement preparedStatement1 = conn.prepareStatement(sql2);
		preparedStatement1.setInt(1, accountDetailsID);

		rs2 = preparedStatement1.executeQuery();
		rs2.next();
		Integer transactionDetaildID = rs2.getInt("TRANSACTION_ID");

		PreparedStatement preparedStatement = conn.prepareStatement(amountInsert);
		preparedStatement.setInt(1, amount);
		preparedStatement.setString(2, transaction_type);
		preparedStatement.setInt(3, accountDetailsID);
		preparedStatement.setInt(4, transactionDetaildID);

		logger.info("----amountPersistance::amount()-----" + amount + "------------ " + amount.getClass());
		logger.info("----amountPersistance::transaction_type()-----" + transaction_type + "------------ "
				+ transaction_type.getClass());
		logger.info("----amountPersistance::accountDetailsID()-----" + accountDetailsID + "------------ "
				+ accountDetailsID.getClass());
		logger.info("----amountPersistance::transactionDetaildID()-----" + transactionDetaildID + "------------ "
				+ transactionDetaildID.getClass());

		preparedStatement.executeUpdate();
		logger.info(" ------ DataWrappers::amountPersistance()-----------");
		logger.info(" ------ so new row saved in the database*-----------");
		out.println("<h1> ------ so new row saved in the database-----------</h1>");
		out.println("<a href='http://localhost:8080/ATM/'>Back to ATM</a>");

	}

	public static void dbTrasactionPersistance(PrintWriter out, Connection conn, String transactionInsert,
			List trasaction) throws SQLException {
		logger.info("----dbPersistance::dbTrasactionPersistance()-----");

		Statement stmt1 = null;
		ResultSet rs1;
		stmt1 = (Statement) conn.createStatement();

		Integer amount = (Integer) trasaction.get(0);
		Integer accountNumber = (Integer) trasaction.get(1);

		String sql = "SELECT ACCOUNT_DETAILS_ID FROM ACCOUNT_DETAILS WHERE ACCOUNT_NUMBER=?";

		PreparedStatement preparedStatement0 = conn.prepareStatement(sql);
		preparedStatement0.setInt(1, accountNumber);
		rs1 = preparedStatement0.executeQuery();
		rs1.next();
		Integer accountDetailsID = rs1.getInt("ACCOUNT_DETAILS_ID");

		logger.info("----dbPersistance::amount()-----" + amount + "------------ " + amount.getClass());
		logger.info("----dbPersistance::accountDetailsID value()-----" + accountDetailsID + "-----------"
				+ accountDetailsID.getClass());

		PreparedStatement preparedStatement = conn.prepareStatement(transactionInsert);
		preparedStatement.setInt(1, amount);
		preparedStatement.setInt(2, accountDetailsID);

		preparedStatement.executeUpdate();
		logger.info(" ------ INSERT QUERY---------" + transactionInsert + "params are " + "amount is =>" + amount
				+ " , " + "account detail id is =>" + accountDetailsID);
		logger.info(" ------ so new row saved in the database*-----------");
		out.println("<h1> ------ so new row saved in the database-----------</h1>");
		out.println("<a href='http://localhost:8080/ATM/'>Back to ATM</a>");

	}
}
