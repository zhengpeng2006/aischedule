package com.asiainfo.monitor.exeframe.configmgr.abo.dao.impl;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.service.ServiceFactory;
import com.ai.comframe.utils.TimeUtil;
import com.asiainfo.monitor.busi.cache.impl.AIMonGroupCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.Group;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonGroupSV;
import com.asiainfo.monitor.common.CommonConst;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonGroupBean;
import com.asiainfo.monitor.exeframe.configmgr.abo.bo.BOAIMonGroupEngine;
import com.asiainfo.monitor.exeframe.configmgr.abo.dao.interfaces.IAIMonGroupDAO;
import com.asiainfo.monitor.exeframe.configmgr.abo.ivalues.IBOAIMonGroupValue;

public class AIMonGroupDAOImpl implements IAIMonGroupDAO
{

    public long getBeanId() throws Exception
    {
        return BOAIMonGroupEngine.getNewId().longValue();
    }

    public BOAIMonGroupBean getBeanById(long id) throws Exception
    {
        return BOAIMonGroupEngine.getBean(id);
    }

    public void save(BOAIMonGroupBean value) throws Exception
    {
        if(value.isNew() && value.getGroupId() <= 0)
            value.setGroupId(getBeanId());
        BOAIMonGroupEngine.save(value);
    }

    /** 
     * 根据条件查询Group
     * @param cond : String
     * @param para : Map
     * @return IBOAIMonGroupValue[]
     * @throws Exception , RemoteException
     */
    public IBOAIMonGroupValue[] queryGroup(String cond, Map para) throws Exception
    {
        return BOAIMonGroupEngine.getBeans(cond, para);
    }

    /** 
     * 根据组名，获取组信息
     * @param ruleType
     * @return IBOAIMonGroupValue[]
     * @throws Exception
     */
    public IBOAIMonGroupValue[] queryGroupByName(String groupName) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        if(StringUtils.isNotBlank(groupName)) {
            condition.append(IBOAIMonGroupValue.S_GroupName).append(" like :groupName");
            parameter.put("groupName", "%" + groupName + "%");
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' ");
        return queryGroup(condition.toString(), parameter);
    }

    /** 
     * 根据组标识，读取组信息
     * @param groupId
     * @return
     * @throws Exception
     */
    public IBOAIMonGroupValue getGroupByGroupId(String groupId) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        long groupIdLong = Long.parseLong(groupId);
        if(StringUtils.isNotBlank(groupId)) {
            condition.append(IBOAIMonGroupValue.S_GroupId).append(" = :groupId");
            parameter.put("groupId", groupIdLong);
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append("state='U'");
        IBOAIMonGroupValue[] values = BOAIMonGroupEngine.getBeans(condition.toString(), parameter);
        if(values.length > 0) {
            return values[0];
        }
        return null;
        //return BOAIMonGroupEngine.getBean(Long.parseLong(groupId));
    }

    /** 
     * 返回组信息
     * @return
     * @throws Exception
     */
    public IBOAIMonGroupValue[] getAllGroup() throws Exception
    {
        return this.queryGroup(IBOAIMonGroupValue.S_State + "='U'" + " order by " + IBOAIMonGroupValue.S_GroupId, null);
    }

    /** 
     * 批量保存或修改应用服务器组
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonGroupValue[] values) throws Exception
    {
        if(values != null && values.length > 0) {
            for(int i = 0; i < values.length; i++) {
                if(values[i].isNew()) {
                    values[i].setGroupId(BOAIMonGroupEngine.getNewId().longValue());
                    values[i].setState(CommonConst.STATE_U);
                    values[i].setCreateDate(TimeUtil.getSysTime());
                }
            }
        } else {
            throw new Exception("no data to saveOrUpdate...");
        }
        BOAIMonGroupEngine.saveBatch(values);
    }

    /** 
     * 保存或修改应用服务器组
     * @param value
     * @throws Exception
     */
    public long saveOrUpdate(IBOAIMonGroupValue value) throws Exception
    {
        if(value.isNew()) {
            value.setGroupId(BOAIMonGroupEngine.getNewId().longValue());
            value.setState(CommonConst.STATE_U);
            value.setCreateDate(TimeUtil.getSysTime());
        }
        BOAIMonGroupEngine.save(value);

        String groupId = Long.toString(value.getGroupId());

        Map<String, Group> groupMap = CacheFactory.getAll(AIMonGroupCacheImpl.class);
        Group group = new Group();
        group.setGroup_Id(groupId);
        group.setGroup_Name(value.getGroupName());
        group.setGroup_Desc(value.getGroupDesc());
        group.setState(value.getState());
        if(groupMap != null) {
            groupMap.put(groupId, group);
        }
        return value.getGroupId();
    }

    /** 
     * 删除组信息
     * @param groupId
     * @throws Exception
     */
    public void deleteGroup(long groupId) throws Exception
    {
        IBOAIMonGroupValue value = BOAIMonGroupEngine.getBean(groupId);
        if(null != value) {
            value.setState("E");
            BOAIMonGroupEngine.save(value);
            Map<String, Group> groupMap = CacheFactory.getAll(AIMonGroupCacheImpl.class);
            groupMap.remove(Long.toString(groupId));
        }

    }

    @Override
    public boolean isExistGroupByCode(String groupCode) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        if(StringUtils.isNotBlank(groupCode)) {
            condition.append(IBOAIMonGroupValue.S_GroupCode + "= :groupCode");
            parameter.put("groupCode", groupCode);
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' ");
        int count=BOAIMonGroupEngine.getBeansCount(condition.toString(), parameter);
        if(count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isExistGroupByName(String groupName) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        if(StringUtils.isNotBlank(groupName)) {
            condition.append(IBOAIMonGroupValue.S_GroupName + "= :groupName");
            parameter.put("groupName", groupName);
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' ");
        int count = BOAIMonGroupEngine.getBeansCount(condition.toString(), parameter);
        if(count > 0) {
            return true;
        }

        return false;

    }

    @Override
    public IBOAIMonGroupValue[] getGroupByPriv(String priv) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();
        if(StringUtils.isNotBlank(priv)) {
            condition.append(IBOAIMonGroupValue.S_Priv).append(" like :priv");
            parameter.put("priv", "%" + priv + "%");
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(" state = 'U' ");
        condition.append(" order by " + IBOAIMonGroupValue.S_GroupId);
        return queryGroup(condition.toString(), parameter);
    }
    
    public static void main(String[] args) throws RemoteException, Exception
	{
		IAIMonGroupDAO sv=(IAIMonGroupDAO) ServiceFactory.getService(IAIMonGroupDAO.class);
		IBOAIMonGroupValue[] values=sv.getGroupByPriv("-110_调度#");
		if(values!=null&&values.length>0){
			for(IBOAIMonGroupValue groupValue:values){
				System.out.println(groupValue.getGroupName());
			}
		}
	}

}
