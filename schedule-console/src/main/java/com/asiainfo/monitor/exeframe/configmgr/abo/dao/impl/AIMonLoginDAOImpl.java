package com.asiainfo.monitor.exeframe.configmgr.abo.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonLoginEngine;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonLoginDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonLoginValue;

public class AIMonLoginDAOImpl implements IAIMonLoginDAO {
    /**
     * 通过ip和loginName查询登录信息
     */
    @Override
    public IBOAIMonLoginValue qryLoginInfoByIpAndName(String ip, String loginName) throws Exception {
        IBOAIMonLoginValue loginValue = null;
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        if (StringUtils.isNotBlank(ip)) {
            condition.append(IBOAIMonLoginValue.S_Ip + "= :ip");
            parameter.put("ip", ip);
        }
        if (StringUtils.isNotBlank(loginName)) {
            condition.append(" and ");
            condition.append(IBOAIMonLoginValue.S_LoginName + "= :loginName");
            parameter.put("loginName", loginName);
        }
        IBOAIMonLoginValue[] values = BOAIMonLoginEngine.getBeans(condition.toString(), parameter);
        if (values != null && values.length > 0) {
            loginValue = values[0];
        }
        return loginValue;
    }

    /**
     * 新建和保存登录信息
     */
    @Override
    public void saveLoginInfo(IBOAIMonLoginValue loginValue) throws Exception {
        BOAIMonLoginEngine.save(loginValue);
    }

    /**
     * 删除登录信息
     */
    @Override
    public void deleteLoginInfo(IBOAIMonLoginValue loginValue) throws Exception {
        if (loginValue != null) {
            loginValue.delete();
            BOAIMonLoginEngine.save(loginValue);
        }
    }

}
