package com.asiainfo.monitor.busi.dao.impl;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.monitor.busi.dao.interfaces.IAIMonUserRoleEntityRelDAO;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserRoleEntityRelEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserRoleEntityRelValue;

public class AIMonUserRoleEntityRelDAOImpl implements IAIMonUserRoleEntityRelDAO
{

    /**
     * 根据条件取得角色与实体关系信息
     * 
     * @param condition
     * @param parameter
     * @return
     * @throws Exception
     */
    public IBOAIMonUserRoleEntityRelValue[] query(String condition, Map parameter) throws Exception
    {
        return BOAIMonUserRoleEntityRelEngine.getBeans(condition, parameter);
    }

    /**
     * 根据主键取得角色与实体关系信息
     * 
     * @param relateId
     * @return
     * @throws Exception
     */
    public IBOAIMonUserRoleEntityRelValue getBeanById(long relateId) throws Exception
    {
        return BOAIMonUserRoleEntityRelEngine.getBean(relateId);
    }

    /**
     * 根据角色ID取得对应的实体权限ID
     * 
     * @param roleId
     * @return
     * @throws Exception
     */
    public IBOAIMonUserRoleEntityRelValue[] getEntityIdByRoleId(String roleId) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        if(StringUtils.isNotBlank(roleId)) {
            condition.append(IBOAIMonUserRoleEntityRelValue.S_UserRoleId + " = :roleId");
            parameter.put("roleId", Long.parseLong(roleId));
        }
        return query(condition.toString(), parameter);
    }

    /**
     * 保存或修改角色与实体关系信息
     * 
     * @param value
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserRoleEntityRelValue value) throws Exception
    {
        if(value.isNew()) {
            value.setRelateId(BOAIMonUserRoleEntityRelEngine.getNewId().longValue());
        }
        BOAIMonUserRoleEntityRelEngine.save(value);
    }

    /**
     * 批量保存或修改角色与实体关系信息
     * 
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserRoleEntityRelValue[] values) throws Exception
    {
        if(values != null && values.length > 0) {
            for(int i = 0; i < values.length; i++) {
                if(values[i].isNew()) {
                    values[i].setRelateId(BOAIMonUserRoleEntityRelEngine.getNewId().longValue());
                }
            }
        } else {
            throw new Exception("no data to saveOrUpdate...");
        }
        BOAIMonUserRoleEntityRelEngine.saveBatch(values);
    }

    /**
     * 删除角色与实体关系信息
     * 
     * @param relateId
     * @throws Exception
     */
    public void delete(long relateId) throws Exception
    {
        IBOAIMonUserRoleEntityRelValue value = BOAIMonUserRoleEntityRelEngine.getBean(relateId);
        if(null != value) {
            value.delete();
            BOAIMonUserRoleEntityRelEngine.save(value);
        }
    }

    public boolean checkUserRoleEntityRel(String entityId, String userRoleId) throws RemoteException, Exception
    {
        StringBuilder sb = new StringBuilder("");
        sb.append(IBOAIMonUserRoleEntityRelValue.S_EntityId + " = :entityId  and ");
        sb.append(IBOAIMonUserRoleEntityRelValue.S_UserRoleId + " = :userRoleId ");
        Map parameter = new HashMap();
        parameter.put("entityId", entityId);
        parameter.put("userRoleId", userRoleId);
        IBOAIMonUserRoleEntityRelValue[] values = BOAIMonUserRoleEntityRelEngine.getBeans(sb.toString(), parameter);
        if(values != null && values.length >= 1)
            return true;
        else
            return false;
    }

}
