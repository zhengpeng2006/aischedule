package com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces;

import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonHostUserBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonHostUserValue;

public interface IAIMonHostUserDAO
{

    public long getBeanId() throws Exception;

    public BOAIMonHostUserBean getBeanById(long id) throws Exception;

    public void save(BOAIMonHostUserBean value) throws Exception;

    public IBOAIMonHostUserValue qryByCondition(String hostId, String userType);

    /**
     * 根据主机查询所有用户信息
     * @param hostId
     * @return
     * @throws Exception
     */
    public IBOAIMonHostUserValue[] qryByHostId(String hostId) throws Exception;

    public IBOAIMonHostUserValue qryHostUserById(String hostUserId) throws Exception;

    public void delete(IBOAIMonHostUserValue value) throws Exception;

    public void save(IBOAIMonHostUserValue value) throws Exception;

    public boolean isExistByUserName(String hostId, String userName) throws Exception;

    public IBOAIMonHostUserValue[] getAllUserInfo() throws Exception;

}
