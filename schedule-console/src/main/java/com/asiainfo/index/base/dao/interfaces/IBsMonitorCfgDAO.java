package com.asiainfo.index.base.dao.interfaces;

import com.asiainfo.index.base.bo.BOBsMonitorCfgBean;
import com.asiainfo.index.base.ivalues.IBOBsMonitorCfgValue;

public interface IBsMonitorCfgDAO {
	BOBsMonitorCfgBean getBeanById(int id) throws Exception;
	
	/**
     * @param monitorId 监控ID
     * @param indexKind 指标类型（维度，标量）
     * @return
     * @throws Exception
     */
	IBOBsMonitorCfgValue[] getConditions(int monitorId, String indexType) throws Exception;
}
