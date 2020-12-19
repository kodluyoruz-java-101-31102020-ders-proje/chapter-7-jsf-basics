package com.jsf.basics.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbSQLQuery {

	public static ResultSet select(String sql) {
		
		try {
			
			Connection dbConnection = DbConnection.getDbConnection();
			Statement statement = dbConnection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = statement.executeQuery(sql);
			return resultSet;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static PreparedStatement createPreparedStatement(String sql) {
		
		try {
			
			Connection dbConnection = DbConnection.getDbConnection();
			return dbConnection.prepareStatement(sql);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
