package com.asiainfo.schedule.core.client.tf.config.table;

@SuppressWarnings("rawtypes")
public class Column {
	public static final String ROW_ID_COLUMN_NAME = "ROWID";

	private String name = null;

	private Class type = null;
	private String srcCloumnName = null;
	private boolean rowId = false;

	public Column() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public String getSrcCloumnName() {
		return srcCloumnName;
	}

	public void setSrcCloumnName(String srcCloumnName) {
		this.srcCloumnName = srcCloumnName;
	}

	public boolean isRowId() {
		return rowId;
	}

	public void setRowId(boolean rowId) {
		this.rowId = rowId;
	}

	public Column copy() {
		Column obj = new Column();
		obj.setName(this.getName());
		obj.setType(this.getType());
		obj.setSrcCloumnName(this.getSrcCloumnName());
		obj.setRowId(this.isRowId());
		return obj;
	}

}
