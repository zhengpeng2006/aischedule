package com.asiainfo.schedule.core.client.tf.config.table;

public class PK {
	private Column[] columns = null;

	public PK() {
	}

	public void setColumns(Column[] columns) {
		this.columns = columns;
	}

	public Column[] getColumns() {
		return columns;
	}
}
