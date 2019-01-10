package com.asiainfo.schedule.core.client.tf.template.reload.table;

import com.asiainfo.schedule.core.client.tf.config.table.AbstractTable;

public class ReloadTable extends AbstractTable {

  public static final String SRC = "SRC";
  public static final String DEST = "DEST";

  private String type;

  public ReloadTable() {
  }
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }

  /**
   *
   * @return String
   */
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("数据库连接编码:" + this.getDbAcctCode() + ",表:" + this.getTableName() + ",类型:" + this.getType() + "\n");
    for (int i = 0; i < this.columns.length; i++) {
      sb.append("列:" + this.columns[i].getName() + ",类型:" + this.columns[i].getType() + "\n");
    }
    return sb.toString();
  }

}
