package com.asiainfo.monitor.exeframe.controlmgr.service.interfaces;

import com.ailk.common.data.IDataBus;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonDomainBean;

public interface IUserPriConstDataSV {

    public IDataBus getUserPrivConstByCode(String constCode) throws Exception;

    public BOAIMonDomainBean[] getDomainInfo() throws Exception;

    public void loadTreeTableData() throws Exception;

}