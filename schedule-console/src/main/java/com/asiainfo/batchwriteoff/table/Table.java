package com.asiainfo.batchwriteoff.table;

/**
 * 表
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: AI(NanJing)</p>
 * @author Yang Hua
 * @version 2.0
 */
public  class Table {

 
	private String tableName = null;
	private Column[] columns = null;// 暂时未实现读取表的字段
	private PK pk = null;
	private Index[] idxs = null;

  public Table() {
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public Column[] getColumns() {
    return columns;
  }

  public Index[] getIdxs() {
	return idxs;
}

public void setIdxs(Index[] idxs) {
	this.idxs = idxs;
}

public void setColumns(Column[] columns) {
    this.columns = columns;
  }

  public PK getPk() {
    return pk;
  }

  public void setPk(PK pk) {
    this.pk = pk;
  }



}
