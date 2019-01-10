package com.asiainfo.monitor.exeframe.configmgr.service.interfaces;

import java.util.List;

import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonMasterSlaveRelBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonMasterSlaveRelValue;

public interface IAIMonMasterSlaveRelSV
{
    public List<String> getSlaveHostId(String masterHostId) throws Exception;

    public IBOAIMonMasterSlaveRelValue[] qryMasterSlaveByCondition(String masterHostId, String slaveHostId) throws Exception;

    public void delete(IBOAIMonMasterSlaveRelValue[] value) throws Exception;

    public void save(BOAIMonMasterSlaveRelBean masterSlaveBean) throws Exception;

    /**
     * 根据主（主机id）查询关系
     * 
     * @param request
     * @throws Exception
     */
    public IBOAIMonMasterSlaveRelValue[] qryMasterSlaveRelByMasterHostId(String hostId) throws Exception;
    /**
     * 查询所有主备机信息
     * @return
     * @throws Exception
     */
    public IBOAIMonMasterSlaveRelValue[] qryAllMasterSlaveRelInfo() throws Exception;

}
