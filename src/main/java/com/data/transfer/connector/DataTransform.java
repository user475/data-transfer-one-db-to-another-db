package com.data.transfer.connector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataTransform {

	public List<String[]> readDataFromCsvFile(String csvFileLocation) {

		List<String[]> list = new ArrayList<String[]>();

		String line = "";
		String cvsSplitBy = ",";
		try (BufferedReader br = new BufferedReader(new FileReader(csvFileLocation))) {

			while ((line = br.readLine()) != null) {
				String[] row = line.split(cvsSplitBy);
				list.add(row);
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		// TargetDBConnector targetDBConnector = new TargetDBConnector();
		// targetDBConnector.insertIntoTargetDb(connection, "test2", list);
		list.forEach(System.out :: println);
		return list;

	}
	public String[] getCoulmnNameFromFile(String csvFileLocation) {
		String[] coulumnNames =null;
		
		try (BufferedReader br = new BufferedReader(new FileReader(csvFileLocation))) {

			String readLine = br.readLine();
			coulumnNames = readLine.split(",");
			for (String string : coulumnNames) {
				System.out.println("column :: "+string); 
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return coulumnNames;

	}
	
	 
}
