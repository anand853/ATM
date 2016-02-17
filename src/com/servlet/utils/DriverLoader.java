package com.servlet.utils;

import java.io.File;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.atm.servletCapture.CaptureWithdrawalFormData;
import com.mysql.jdbc.Connection;

public class DriverLoader {

	final static Logger logger = Logger.getLogger(DriverLoader.class);

	private static final long serialVersionUID = 1L;

	PrintWriter out;

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/USA";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "root";

	static Connection conn = null;
	static Statement stmt = null;

	public static Connection loadDrivers() {

		logger.info("-----DriverLoader::loadDrivers()-------");

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			logger.info("conection established...");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return conn;

	}

	public static Logger getLogger() {
		logger.info("----DriverLoader::getLogger()-----");
		String log4jConfigFile = System.getProperty("user.dir") + File.separator + "log4j.properties";
		PropertyConfigurator.configure(log4jConfigFile);
		return logger;
	}

}
