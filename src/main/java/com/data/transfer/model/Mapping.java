package com.data.transfer.model;

public class Mapping {
	
	private String sourceTable;
	private String sourceColumn;
	private String destinationTable;
	private String destinationColumn;
	
	
	
	public Mapping() {
		super();
	}
	
	public Mapping(String sourceTable, String sourceColumn, String destinationTable, String destinationColumn) {
		super();
		this.sourceTable = sourceTable;
		this.sourceColumn = sourceColumn;
		this.destinationTable = destinationTable;
		this.destinationColumn = destinationColumn;
	}

	public String getSourceTable() {
		return sourceTable;
	}
	public void setSourceTable(String sourceTable) {
		this.sourceTable = sourceTable;
	}
	public String getSourceColumn() {
		return sourceColumn;
	}
	public void setSourceColumn(String sourceColumn) {
		this.sourceColumn = sourceColumn;
	}
	public String getDestinationTable() {
		return destinationTable;
	}
	public void setDestinationTable(String destinationTable) {
		this.destinationTable = destinationTable;
	}
	public String getDestinationColumn() {
		return destinationColumn;
	}
	public void setDestinationColumn(String destinationColumn) {
		this.destinationColumn = destinationColumn;
	}
	
	@Override
	public String toString() {
		return "Mapping [sourceTable=" + sourceTable + ", sourceColumn=" + sourceColumn + ", destinationTable="
				+ destinationTable + ", destinationColumn=" + destinationColumn + "]";
	}
	

}
