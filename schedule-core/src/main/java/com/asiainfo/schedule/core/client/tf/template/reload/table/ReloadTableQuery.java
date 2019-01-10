package com.asiainfo.schedule.core.client.tf.template.reload.table;

import com.asiainfo.schedule.core.client.TaskDealParam;
import com.asiainfo.schedule.core.client.tf.config.CfgTf;
import com.asiainfo.schedule.core.client.tf.config.CfgTfDtl;
import com.asiainfo.schedule.core.client.tf.config.CfgTfMapping;
import com.asiainfo.schedule.core.client.tf.config.table.Column;
import com.asiainfo.schedule.core.client.tf.config.table.PK;
import com.asiainfo.schedule.core.client.tf.util.QueryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ReloadTableQuery {
	private transient static Logger log = LoggerFactory.getLogger(ReloadTableQuery.class);

	private CfgTf objCfgTf = null;

	private ReloadTable srcTable = null;
	private ReloadTable[] destTable = null;

	/**
	 * 
	 * @param objInitParameter
	 *          InitParameter
	 * @param objCfgTf
	 *          CfgTf
	 * @throws Exception
	 */
	public ReloadTableQuery(TaskDealParam objInitParameter, CfgTf objCfgTf) throws Exception {
		this.objCfgTf = objCfgTf;

		// 源表
		this.srcTable = this.readTableInfo(objCfgTf.getSrcDbAcctCode(), objCfgTf.getSrcTableName(), ReloadTable.SRC);

		if (log.isInfoEnabled()) {
			log.info("源表对应的信息\n" + this.srcTable.toString());
		}

		// reload表
		CfgTfDtl[] objCfgTfDtl = objCfgTf.getObjCfgTfDtl();
		if (objCfgTfDtl == null || objCfgTfDtl.length == 0) {
			throw new Exception("数据库连接编码:" + objCfgTf.getCfgTfCode() + ",必须至少配置一条reload表");
		}

		List<ReloadTable> dest = new ArrayList<ReloadTable>();
		for (int i = 0; i < objCfgTfDtl.length; i++) {
			if (objCfgTfDtl[i].getTfType().equals(ReloadTable.DEST)) {
				ReloadTable obj = readTableInfo(objCfgTfDtl[i].getDbAcctCode(), objCfgTfDtl[i].getTableName(), ReloadTable.DEST);
				if (log.isInfoEnabled()) {
					log.info("reload表" + (dest.size() + 1) + "对应的信息\n" + obj.toString());
				}
				dest.add(obj);
			} else {
				throw new Exception("无法识别的表类型:" + objCfgTfDtl[i].getTfType());
			}
		}

		this.destTable = (ReloadTable[]) dest.toArray(new ReloadTable[0]);

		// 检查表结构
		Column[] srcColumn = this.srcTable.getColumns();

		// 检查reload表的列
		for (int i = 0; i < this.destTable.length; i++) {
			Column[] destColumn = this.destTable[i].getColumns();

			for (int k = 0; k < destColumn.length; k++) {
				boolean isExist = false;
				for (int j = 0; j < srcColumn.length; j++) {
					if (destColumn[k].getSrcCloumnName().equals(srcColumn[j].getName())) {
						isExist = true;
						break;
					}
				}
				if (!isExist) {
					throw new Exception("reload表:" + this.destTable[i].getTableName() + ",列:" + destColumn[k].getName() + ",无法找到对应的源表中的列");
				}
			}
		}

	}

	public ReloadTable getSrcTable() {
		return this.srcTable;
	}

	public ReloadTable[] getDestTable() {
		return this.destTable;
	}

	/**
	 * 
	 * @param dbAcctCode
	 *          String
	 * @param tableName
	 *          String
	 * @param type
	 *          String
	 * @throws Exception
	 * @return Table
	 */
	private ReloadTable readTableInfo(String dbAcctCode, String tableName, String type) throws Exception {
		ReloadTable rtn = new ReloadTable();

		if (!QueryUtil.isExistTable(dbAcctCode, tableName)) {
			throw new Exception("数据库连接编码:" + dbAcctCode + ",表:" + tableName + "不存在");
		}

		Column[] columns = QueryUtil.readTableColumns(dbAcctCode, tableName);
		PK pk = QueryUtil.readTablePK(dbAcctCode, tableName, columns);
		rtn.setPk(pk);

		//
		// 1、源表列的映射
		//
		if (type.equals(ReloadTable.SRC)) {
			for (int i = 0; i < columns.length; i++) {
				columns[i].setSrcCloumnName(columns[i].getName());
			}
		}
		//
		// 源表列的映射
		//

		//
		// 2、开始reload表的列的映射
		//
		if (type.equals(ReloadTable.DEST)) {
			// 映射列
			CfgTfDtl[] tmpCfgTfDtl = this.objCfgTf.getObjCfgTfDtl();

			CfgTfDtl objCfgTfDtl = null;
			for (int i = 0; i < tmpCfgTfDtl.length; i++) {
				if (tmpCfgTfDtl[i].getDbAcctCode().equals(dbAcctCode) && tmpCfgTfDtl[i].getTableName().equals(tableName)) {
					objCfgTfDtl = tmpCfgTfDtl[i];
					break;
				}
			}

			CfgTfMapping[] objCfgTfMapping = objCfgTfDtl.getObjCfgTfMapping();
			if (objCfgTfMapping == null || objCfgTfMapping.length == 0) {
				// 无需映射
				for (int i = 0; i < columns.length; i++) {
					columns[i].setSrcCloumnName(columns[i].getName());
				}
			} else {
				// 需要映射
				for (int i = 0; i < objCfgTfMapping.length; i++) {
					for (int j = 0; j < columns.length; j++) {
						if (objCfgTfMapping[i].getTfColumnName().equals(columns[j].getName())) {
							columns[j].setSrcCloumnName(objCfgTfMapping[i].getSrcColumnName());
							break;
						}
					}
				}
			}

		}
		//
		// reload列的映射结束
		//

		//
		// 3、裁减reload表的列
		//
		if (type.equals(ReloadTable.DEST)) {
			// 映射列
			CfgTfDtl[] tmpCfgTfDtl = this.objCfgTf.getObjCfgTfDtl();

			CfgTfDtl objCfgTfDtl = null;
			for (int i = 0; i < tmpCfgTfDtl.length; i++) {
				if (tmpCfgTfDtl[i].getDbAcctCode().equals(dbAcctCode) && tmpCfgTfDtl[i].getTableName().equals(tableName)) {
					objCfgTfDtl = tmpCfgTfDtl[i];
					break;
				}
			}

			List<Column> leftCols = new ArrayList<Column>();
			CfgTfMapping[] objCfgTfMapping = objCfgTfDtl.getObjCfgTfMapping();
			if (objCfgTfMapping != null && objCfgTfMapping.length != 0) {
				// 需要裁减
				for (int i = 0; i < columns.length; i++) {
					for (int j = 0; j < objCfgTfMapping.length; j++) {
						if (columns[i].getName().equals(objCfgTfMapping[j].getTfColumnName())) {
							leftCols.add(columns[i]);
						}
					}
				}
				columns = (Column[]) leftCols.toArray(new Column[0]);
			}
		}
		//
		// 裁减reload表的列结束
		//

		rtn.setTableName(tableName);
		rtn.setType(type);
		rtn.setDbAcctCode(dbAcctCode);
		rtn.setColumns(columns);
		if (rtn.getColumns() == null || rtn.getColumns().length == 0) {
			throw new Exception("数据库连接编码:" + dbAcctCode + ",表:" + tableName + "的列为空");
		}

		return rtn;
	}

}
