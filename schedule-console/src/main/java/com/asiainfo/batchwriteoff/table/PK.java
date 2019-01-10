package com.asiainfo.batchwriteoff.table;

public class PK {
	
	private String pkName = null;
	private Column[] columns = null;

	public PK() {
	}

	public void setColumns(Column[] columns) {
		this.columns = columns;
	}

	public Column[] getColumns() {
		return columns;
	}

	public String getPkName() {
		return pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}
}
