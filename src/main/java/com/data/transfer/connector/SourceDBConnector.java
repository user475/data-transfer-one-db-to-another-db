package com.data.transfer.connector;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.data.transfer.utils.DBUtils;

import au.com.bytecode.opencsv.CSVWriter;

public class SourceDBConnector {

	public ResultSet getResultSet(Connection connection, String tableName, List<String> columnNames) {
		String query = null;
		if (columnNames != null && columnNames.size() > 0)
			query = getSelectQuery(tableName, columnNames);
		else
			query = "select * from " + tableName;

		ResultSet rs = null;

		try {
			rs = connection.createStatement().executeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public String saveDataToFile(ResultSet rs, String tableName) {
		String fileLocation = null;
		try {
			DBUtils dbPropeties = new DBUtils();

			fileLocation = dbPropeties.readPropertiesFile("/dbconnection.properties")
					.getProperty("datatransfer.fileName");
			System.out.println("getDataFromDbAndStoreIntoCsv ::::fileLocation" + fileLocation);
			// Obtain ResultSetMetaData

			writeToFile(rs, fileLocation + tableName);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileLocation + tableName;
	}

	public String getSelectQuery(String tableName, List<String> columnNames) {

		String query = "select ";
		System.out.println("column name size" + columnNames.size());
		for (int i = 0; i < columnNames.size(); i++) {
			System.out.println("columnNames.get(i)" + columnNames.get(i));
			query = query + columnNames.get(i) + ((i != (columnNames.size() - 1)) ? "," : "  from ");
		}
		query = query + tableName;
		System.out.println("query" + query);
		return query;
	}

	public String writeToFile(ResultSet rs, String fileLocation) {

		CSVWriter csvWriter = null;
		try {
			if (rs != null) {
				File file = new File(fileLocation + ".csv");
				if (!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					csvWriter = new CSVWriter(new FileWriter(file, false), ',', CSVWriter.NO_QUOTE_CHARACTER,
							CSVWriter.NO_ESCAPE_CHARACTER, System.getProperty("line.separator"));
					csvWriter.writeAll(rs, true);

					csvWriter.flush();

				} catch (IOException e1) {
					e1.printStackTrace();
				} finally {
					try {
						csvWriter.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				DataTransform dataTransform = new DataTransform();
				dataTransform.getCoulmnNameFromFile(file.toString());

				System.out.println("file location" + file.toString());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return fileLocation;
	}

}