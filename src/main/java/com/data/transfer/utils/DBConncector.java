package com.data.transfer.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConncector {

	public Connection getConnection(String driverClass,String databaseUrl, String userName, String password) {
		Connection connection = null;
			
		try {
			
			Class.forName(driverClass);
			connection = DriverManager.getConnection(databaseUrl, userName, password);

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
