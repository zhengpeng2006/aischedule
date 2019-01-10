package com.asiainfo.monitor.exeframe.configmgr.service.interfaces;

import java.util.List;

import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonHostConModeBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonHostConModeValue;

public interface IAIMonHostConModeSV
{
    public IBOAIMonHostConModeValue qryByConId(String conId) throws Exception;

    public List<String> getConIdList(String hostId) throws Exception;

    public IBOAIMonHostConModeValue[] qryHostConModeByCondition(String hostId, String conId) throws Exception;

    public void delete(IBOAIMonHostConModeValue[] value) throws Exception;

    public void save(BOAIMonHostConModeBean hostConBean) throws Exception;

    /**
     * 查询该主机所有的连接方式
     * @param hostId
     * @return
     * @throws Exception
     */
    public IBOAIMonHostConModeValue[] qryBuHostId(String hostId) throws Exception;

}
