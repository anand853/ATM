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
import com.mysql.jdbc.Connection;

import jdk.nashorn.internal.ir.RuntimeNode.Request;

/**
 * Servlet implementation class CaptureFormData
 */
@WebServlet("/CaptureFormData")
public class CaptureFormData extends HttpServlet {

	PrintWriter out;
	private static String accountNumber;
	private static String pin;
	private static String amount;

	private static String accountCaptured;
	private static String pinCaptured;
	private static String amountCaptured;

	private static int accountN;
	private static int pinN;
	private static int amountN;

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/USA";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "root";
	Hashtable list = new Hashtable();

	static Connection conn = null;
	static Statement stmt = null;
	static Statement stmt1 = null;

	private static Session session;
	private static Configuration cfg;
	private static SessionFactory factory;
	private static Transaction t;

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
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			insertIntoAccountDetails(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void insertIntoAccountDetails(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {

		response.getWriter();
		String localAccountNumner = request.getParameter("accountNumber");
		Integer lAccount = Integer.parseInt(localAccountNumner);
		String localPin = request.getParameter("pin");
		Integer lPin = Integer.parseInt(localPin);
		String localAmount = request.getParameter("amount");
		Integer lAmount = Integer.parseInt(localAmount);

		String insertTableSQL = "INSERT INTO ACCOUNT_DETAILS VALUES (?,?,?)";
		PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
		preparedStatement.setInt(1, lAccount);
		preparedStatement.setInt(2, lPin);
		preparedStatement.setInt(3, lAmount);

		preparedStatement.executeUpdate();
		System.out.println(" ------ row saved-----------");

	}

}
