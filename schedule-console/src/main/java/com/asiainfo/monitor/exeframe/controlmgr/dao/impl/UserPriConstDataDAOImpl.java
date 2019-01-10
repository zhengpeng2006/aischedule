package com.asiainfo.monitor.exeframe.controlmgr.dao.impl;

import com.ai.appframe2.util.criteria.Criteria;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonDomainBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonDomainEngine;
import com.asiainfo.monitor.exeframe.controlmgr.dao.interfaces.IUserPriConstDataDAO;

public class UserPriConstDataDAOImpl implements IUserPriConstDataDAO {

    public BOAIMonDomainBean[] getDomainInfo() throws Exception
    {
        Criteria sql = new Criteria();

        return BOAIMonDomainEngine.getBeans(sql);
    }

    public void loadTreeTableData() throws Exception
    {
        //TODO
    
    }

}