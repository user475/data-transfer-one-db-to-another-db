package com.data.transfer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.data.transfer.connector.DataTransform;
import com.data.transfer.connector.SourceDBConnector;
import com.data.transfer.connector.TargetDBConnector;
import com.data.transfer.model.TableMapping;
import com.data.transfer.utils.DBConncector;
import com.data.transfer.utils.DBUtils;
import com.data.transfer.utils.MappingXmlReader;

@SpringBootApplication
public class DataTransferApplication {

	public static void main(String[] args) throws SQLException {

		SpringApplication.run(DataTransferApplication.class, args);
		DBUtils dbPropeties = new DBUtils();
		DataTransferApplication dataTransferApplication = new DataTransferApplication();
		DataTransform dataTransform = new DataTransform();
		TargetDBConnector targetDBConnector = new TargetDBConnector();
		MappingXmlReader mappingXmlReader = new MappingXmlReader();
		SourceDBConnector sourceDB = new SourceDBConnector();

		String sourceDatabaseUrl, targetDatabaseUrl = null;
		String sourceUserName, targetUserName = null;
		String sourcePassword, targetPassword = null;
		String sourceDriverClass, targetDriverClass = null;

		Properties readPropertiesFile;
		try {
			readPropertiesFile = dbPropeties.readPropertiesFile("/dbconnection.properties");
			sourceDatabaseUrl = readPropertiesFile.getProperty("source.database.url");
			sourceUserName = readPropertiesFile.getProperty("source.database.userName");
			sourcePassword = readPropertiesFile.getProperty("source.database.password");
			sourceDriverClass = readPropertiesFile.getProperty("source.database.driver-class-name");
			targetDatabaseUrl = readPropertiesFile.getProperty("target.database.url");
			targetUserName = readPropertiesFile.getProperty("target.database.userName");
			targetPassword = readPropertiesFile.getProperty("target.database.password");
			targetDriverClass = readPropertiesFile.getProperty("target.database.driver-class-name");
			String fileLocation = dbPropeties.readPropertiesFile("/dbconnection.properties")
					.getProperty("datatransfer.fileName");

			DBConncector dbConncector = new DBConncector();
			Connection sourceConnection = dbConncector.getConnection(sourceDriverClass, sourceDatabaseUrl,
					sourceUserName, sourcePassword);
			Connection targetConnection = dbConncector.getConnection(targetDriverClass, targetDatabaseUrl,
					targetUserName, targetPassword);

			// to read xml file
			List<TableMapping> tableMappingList = mappingXmlReader.getTableMappingList();

			for (TableMapping tableMapping : tableMappingList) {

				// to get data
				String sourceTable = tableMapping.getSourceTable().replaceAll("\\s", "");
				String targetTable = tableMapping.getTargetTable().replaceAll("\\s", "");
				String fileName = sourceTable + "-" + targetTable;

				Set<String> listOfsourceColumns = tableMapping.getColumnMapping().keySet();
				List<String> sourceColumns = new ArrayList<String>();
				sourceColumns.addAll(listOfsourceColumns);
				ResultSet rs = sourceDB.getResultSet(sourceConnection, sourceTable, sourceColumns);
				sourceDB.saveDataToFile(rs, fileName);

				// to write data
				HashMap<String, String> sourceColumnDataypeMap = dbPropeties.getColumnType(rs);

				Collection<String> targetColumnsCollection = tableMapping.getColumnMapping().values();
				List<String> targetColumns = new ArrayList<String>();
				targetColumns.addAll(targetColumnsCollection);
				String queryForUpdate = targetDBConnector.getInsertQuery(targetTable, targetColumns);
				List<String[]> data = dataTransform.readDataFromCsvFile(fileLocation + fileName + ".csv");
				targetDBConnector.insertIntoTargetDb(targetConnection, queryForUpdate, sourceColumns,
						sourceColumnDataypeMap, data);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
