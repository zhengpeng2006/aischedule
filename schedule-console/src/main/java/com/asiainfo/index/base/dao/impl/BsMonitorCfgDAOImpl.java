package com.asiainfo.index.base.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.index.base.bo.BOBsMonitorCfgBean;
import com.asiainfo.index.base.bo.BOBsMonitorCfgEngine;
import com.asiainfo.index.base.dao.interfaces.IBsMonitorCfgDAO;
import com.asiainfo.index.base.ivalues.IBOBsMonitorCfgValue;
import com.asiainfo.index.service.interfaces.IBsIndexSV;

public class BsMonitorCfgDAOImpl implements IBsMonitorCfgDAO{

	@Override
	public BOBsMonitorCfgBean getBeanById(int id) throws Exception {
		return BOBsMonitorCfgEngine.getBean(id);
	}
	
	/**
     * @param monitorId 监控ID
     * @param indexKind 指标类型（维度，标量）
     * @return
     * @throws Exception
     */
    @Override
    public IBOBsMonitorCfgValue[] getConditions(int monitorId, String indexKind) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        if(StringUtils.isNotBlank(String.valueOf(monitorId))) {
            condition.append(IBOBsMonitorCfgValue.S_MonitorId).append(" = :monitorId");
            parameter.put("monitorId", monitorId);
        }
        if(StringUtils.isNotBlank(indexKind)){
            condition.append(" and ");
            condition.append(IBOBsMonitorCfgValue.S_IndexKind).append(" = :indexKind");
            parameter.put("indexKind", indexKind);
        }
        condition.append(" order by "+IBOBsMonitorCfgValue.S_IndexCodeMapping);
        IBOBsMonitorCfgValue[] values = BOBsMonitorCfgEngine.getBeans(condition.toString(), parameter);
        return values;
    }
    public static void main(String[] args) throws Exception
    {
        IBsIndexSV bsIndexSV=(IBsIndexSV)ServiceFactory.getService(IBsIndexSV.class);
        bsIndexSV.getConditions(100, "");
    }

}
