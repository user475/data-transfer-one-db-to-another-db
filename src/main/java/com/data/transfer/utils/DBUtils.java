package com.data.transfer.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

public class DBUtils {

	public Properties readPropertiesFile(String fileName) throws IOException {
		InputStream inputStream = null;
		Properties prop = null;
		try {
			prop = new Properties();
			/// dbconnection.properties
			inputStream = this.getClass().getResourceAsStream(fileName);
			// create Properties class object
			if (inputStream != null) {
				// load properties file into it
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file not found in the classpath");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}
		return prop;
	}

	public HashMap<String, String> getColumnType(ResultSet rs) {
		HashMap<String, String> hashMap = new HashMap<>();
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int totalNumberOfColumnsInTable = rsmd.getColumnCount();
			for (int i = 1; i <= totalNumberOfColumnsInTable; i++) {
				String key = rsmd.getColumnName(i).toLowerCase();
				if (rsmd.getColumnTypeName(i).equals("INTEGER") || rsmd.getColumnTypeName(i).equals("DECIMAL") ) {
					hashMap.put(key, "Integer");
				} else if (rsmd.getColumnTypeName(i).equals("VARCHAR2") ||rsmd.getColumnTypeName(i).equals("VARCHAR") ) {
					hashMap.put(key, "String");
				} else if (rsmd.getColumnTypeName(i).equals("DATE")) {
					hashMap.put(key, "Date");
				} else if (rsmd.getColumnTypeName(i).equals("CLOB")) {
					hashMap.put(key, "String");
				} else if (rsmd.getColumnTypeName(i).equals("BLOB")) {
					hashMap.put(key, "String");
				}
			}

		} catch (SQLException se) {
			se.printStackTrace();
		}
		return hashMap;
	}

}
