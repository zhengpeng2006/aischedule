package com.asiainfo.batchwriteoff.table;

public class Column {

	private String name = null;
	private String type = null;
	private String isNull = null;

	public Column() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIsNull() {
		return isNull;
	}

	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}

}
