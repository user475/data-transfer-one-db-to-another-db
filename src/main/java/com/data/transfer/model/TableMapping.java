package com.data.transfer.model;

import java.util.Map;

public class TableMapping {
	
	private String sourceTable;
	private String targetTable;
	private Map<String,String> columnMapping;
	
	public TableMapping() {
	}
	
	public TableMapping(String sourceTable, String targetTable, Map<String, String> columnMapping) {
		super();
		this.sourceTable = sourceTable;
		this.targetTable = targetTable;
		this.columnMapping = columnMapping;
	}
	
	public String getSourceTable() {
		return sourceTable;
	}
	public void setSourceTable(String sourceTable) {
		this.sourceTable = sourceTable;
	}
	
	public Map<String, String> getColumnMapping() {
		return columnMapping;
	}
	public void setColumnMapping(Map<String, String> columnMapping) {
		this.columnMapping = columnMapping;
	}
	public String getTargetTable() {
		return targetTable;
	}
	public void setTargetTable(String targetTable) {
		this.targetTable = targetTable;
	}
	@Override
	public String toString() {
		return "TableMapping [sourceTable=" + sourceTable + ", targetTable=" + targetTable + ", columnMapping="
				+ columnMapping + "]";
	}
	
	
	
	
	

}
