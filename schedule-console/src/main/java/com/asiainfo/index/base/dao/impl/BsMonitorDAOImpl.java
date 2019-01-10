package com.asiainfo.index.base.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.asiainfo.index.base.bo.BOBsMonitorBean;
import com.asiainfo.index.base.bo.BOBsMonitorEngine;
import com.asiainfo.index.base.dao.interfaces.IBsMonitorDAO;
import com.asiainfo.index.base.ivalues.IBOBsMonitorValue;
import com.asiainfo.index.util.IndexConstants;
import com.asiainfo.index.util.IndexUtil;

public class BsMonitorDAOImpl implements IBsMonitorDAO
{
    @Override
    public BOBsMonitorBean getBeanById(int id)
        throws Exception
    {
        return BOBsMonitorEngine.getBean(id);
    }
    
    @Override
    public Map<Integer, String> getAllMonitors()
        throws Exception
    {
        StringBuffer sql = new StringBuffer();
        Map parameter = new HashMap();
        sql.append(IBOBsMonitorValue.S_State).append(" = :state");
        parameter.put("state", IndexConstants.STATE_NORMAL);
        System.out.println("json2 sql.toString()=" + sql.toString());
        BOBsMonitorBean[] monitors = BOBsMonitorEngine.getBeans(sql.toString(), parameter);
        Map<Integer, String> result = IndexUtil.getMapFromMonitor(monitors);
        return result;
    }
}
