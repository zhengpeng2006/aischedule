package com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces;

import java.util.Map;

import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonGroupBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonGroupValue;

public interface IAIMonGroupDAO
{

    public long getBeanId() throws Exception;

    public BOAIMonGroupBean getBeanById(long id) throws Exception;

    public void save(BOAIMonGroupBean value) throws Exception;

    /** 
     * 根据条件查询Group
     * @param cond : String
     * @param para : Map
     * @return IBOAIMonGroupValue[]
     * @throws Exception , RemoteException
     */
    public IBOAIMonGroupValue[] queryGroup(String cond, Map para) throws Exception;

    /** 
     * 根据组名，获取组信息
     * @param ruleType
     * @return IBOAIMonGroupValue[]
     * @throws Exception
     */
    public IBOAIMonGroupValue[] queryGroupByName(String groupName) throws Exception;

    public IBOAIMonGroupValue getGroupByGroupId(String groupId) throws Exception;

    public IBOAIMonGroupValue[] getAllGroup() throws Exception;

    /** 
     * 批量保存或修改应用服务器组设�?
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonGroupValue[] values) throws Exception;

    /** 
     * 保存或修改应用服务器组设�?
     * @param value
     * @return
     * @throws Exception
     */
    public long saveOrUpdate(IBOAIMonGroupValue value) throws Exception;

    /** 
     * 删除组信�?
     * @param groupId
     * @throws Exception
     */
    public void deleteGroup(long groupId) throws Exception;

    public boolean isExistGroupByCode(String groupCode) throws Exception;

    public boolean isExistGroupByName(String groupName) throws Exception;
    
    /**
     * 根据权限信息获取分组信息
     * @param priv
     * @return
     * @throws Exception
     */
    public IBOAIMonGroupValue[] getGroupByPriv(String priv) throws Exception;

}
