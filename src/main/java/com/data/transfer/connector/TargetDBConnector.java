package com.data.transfer.connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TargetDBConnector {

	public void insertIntoTargetDb(Connection connection, String query, List<String> columnNames,
			HashMap<String, String> sourceColumnTypeMap, List<String[]> resultData) {

		try {
			resultData.remove(0);
			PreparedStatement statement = connection.prepareStatement(query);
			for (String[] data : resultData) {
				for (int i = 0; i < columnNames.size(); i++) {
					String name = columnNames.get(i);
					String datatype = sourceColumnTypeMap.get(name);
					if (datatype.equalsIgnoreCase("String"))
						statement.setString(i+1, data[i]);
					else if (datatype.equalsIgnoreCase("Date"))
						 statement.setDate(i+1, (java.sql.Date) new Date(data[i]));
					else if (datatype.equalsIgnoreCase("Integer"))
						statement.setInt(i+1, Integer.parseInt(data[i]));
				}
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String getInsertQuery(String tableName, List<String> columnNames) {

		String query = "insert into " + tableName + " (";
		for (int i = 0; i < columnNames.size(); i++) {
			System.out.println("columnNames.get(i)" + columnNames.get(i));
			query = query + columnNames.get(i) + ((i != (columnNames.size() - 1)) ? "," : "");
		}
		query = query + " )  values (";
		for (int i = 0; i < columnNames.size(); i++) {

			query = query + ((i != (columnNames.size() - 1)) ? "? ," : "?");
		}
		query = query + " )";
		System.out.println("query" + query);
		return query;
	}

}
