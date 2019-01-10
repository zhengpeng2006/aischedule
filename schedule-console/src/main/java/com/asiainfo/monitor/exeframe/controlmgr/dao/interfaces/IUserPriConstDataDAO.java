package com.asiainfo.monitor.exeframe.controlmgr.dao.interfaces;

import com.asiainfo.monitor.exeframe.config.bo.BOAIMonDomainBean;

public interface IUserPriConstDataDAO {

    public BOAIMonDomainBean[] getDomainInfo() throws Exception;

    public void loadTreeTableData() throws Exception;

}