package com.jsf.basics.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {

	private final static String dbHost = "jdbc:mysql://localhost:3306/company_db?allowPublicKeyRetrieval=true&useSSL=false";
	private final static String userName = "root";
	private final static String password = "root";
	private final static String jdbcDriver = "com.mysql.jdbc.Driver";
	
	private static Connection connection = null;
	
	public static Connection getDbConnection() {
		
		try {
			if(connection == null) {
				
				Class.forName(jdbcDriver);
				connection = DriverManager.getConnection(dbHost, userName, password);
			}
			return connection;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
