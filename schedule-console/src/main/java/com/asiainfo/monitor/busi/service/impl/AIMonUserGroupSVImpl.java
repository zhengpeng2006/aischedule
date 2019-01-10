package com.asiainfo.monitor.busi.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.AIMonUserGroupCheckListener;
import com.asiainfo.monitor.busi.cache.MonCacheManager;
import com.asiainfo.monitor.busi.cache.impl.AIMonUserGroupCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.GroupRole;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonUserGroupDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserGroupRoleSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserGroupSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserEngine;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserGroupEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupRoleRelValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserValue;
import com.asiainfo.monitor.exeframe.controlmgr.common.UserPrivConst;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class AIMonUserGroupSVImpl implements IAIMonUserGroupSV{

	/**
     * 保存或修改用户组信息
     * 
     * @param value
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserGroupValue value) throws Exception
    {
        boolean modify = value.isModified();
        IAIMonUserGroupDAO userGroupDao = (IAIMonUserGroupDAO) ServiceFactory.getService(IAIMonUserGroupDAO.class);
        if(value.isNew()) {
            IBOAIMonUserGroupValue[] groups = userGroupDao.getUserGroupByCond(value.getGroupCode(), "");
            if(groups != null && groups.length > 0)
                // 用户组编码:"+value.getGroupCode()+",已经存在,无法新增!
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000125", value.getGroupCode()));
        }
        userGroupDao.saveOrUpdate(value);
        if(modify) {
            GroupRole item = (GroupRole) MonCacheManager.get(AIMonUserGroupCacheImpl.class, value.getUserGroupId() + "");
            if(item != null)
                item.setEnable(false);
        } else {
            GroupRole item = this.wrapperUserGroupByBean(value);
            MonCacheManager.put(AIMonUserGroupCacheImpl.class, item.getId(), item);
        }
    }

    /**
     * 批量保存或修改用户组信息
     * 
     * @param values
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserGroupValue[] values) throws Exception
    {
        boolean modify = values[0].isModified();
        IAIMonUserGroupDAO userGroupDao = (IAIMonUserGroupDAO) ServiceFactory.getService(IAIMonUserGroupDAO.class);
        userGroupDao.saveOrUpdate(values);
        for(int i = 0; i < values.length; i++) {
            if(modify) {
                GroupRole item = (GroupRole) MonCacheManager.get(AIMonUserGroupCacheImpl.class, values[i].getUserGroupId() + "");
                if(item != null)
                    item.setEnable(false);
            } else {
                GroupRole item = this.wrapperUserGroupByBean(values[i]);
                MonCacheManager.put(AIMonUserGroupCacheImpl.class, item.getId(), item);
            }
        }
    }

    /**
     * 将用户组值对象封装
     * @param value
     * @return
     * @throws Exception
     */
    public GroupRole wrapperUserGroupByBean(IBOAIMonUserGroupValue value) throws Exception
    {
        GroupRole result = null;
        if(value == null || StringUtils.isBlank(value.getState()) || value.getState().equals("E"))
            return result;
        result = new GroupRole();
        result.setId(value.getUserGroupId() + "");
        result.setCode(value.getGroupCode());
        result.setName(value.getGroupName());
    
        //组关联角色
        IAIMonUserGroupRoleSV groupRoleSV = (IAIMonUserGroupRoleSV) ServiceFactory.getService(IAIMonUserGroupRoleSV.class);
        IBOAIMonUserGroupRoleRelValue[] roleValues = groupRoleSV.getRoleIdByGroupId(result.getId());
        if(roleValues != null && roleValues.length > 0) {
            for(int j = 0; j < roleValues.length; j++) {
                result.addRoles(roleValues[j].getUserRoleId() + "");
            }
        }
    
        result.setCacheListener(new AIMonUserGroupCheckListener());
        return result;
    }

    /**
     * 根据用户组标识读取用户组信息
     * @param id
     * @return
     * @throws Exception
     */
    public GroupRole getUserGroupById(String id) throws Exception
    {
        GroupRole result = null;
        if(StringUtils.isBlank(id))
            // 标识为空
            throw new Exception(AIMonLocaleFactory.getResource("MOS0000098"));
        result = (GroupRole) MonCacheManager.get(AIMonUserGroupCacheImpl.class, id);
        if(result == null) {
            result = this.getUserGroupByIdFromDb(id);
            if(!MonCacheManager.getCacheReadOnlySet() && result != null) {
                MonCacheManager.put(AIMonUserGroupCacheImpl.class, result.getId(), result);
            }
        }
        return result;
    }

    /**
     * 根据条件取得用户组信息
     * 
     * @param groupCode
     * @param groupName
     * @return
     * @throws Exception
     */
    public IBOAIMonUserGroupValue[] getUserGroupByCond(String groupCode, String groupName) throws Exception
    {
        IAIMonUserGroupDAO userGroupDao = (IAIMonUserGroupDAO) ServiceFactory.getService(IAIMonUserGroupDAO.class);
        return userGroupDao.getUserGroupByCond(groupCode, groupName);
    }

    /**
     * 根据条件取得用户组记录总数
     * 
     * @param groupCode
     * @param groupName
     * @return
     * @throws Exception
     */
    public int getUserGroupCntByCond(String groupCode, String groupName) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();

        if(StringUtils.isNotBlank(groupCode)) {
            condition.append(IBOAIMonUserGroupValue.S_GroupCode).append(" = :groupCode");
            parameter.put("groupCode", groupCode);
        }
        if(StringUtils.isNotBlank(groupName)) {
            if(StringUtils.isNotBlank(groupCode)) {
                condition.append(" and ");
            }
            condition.append(IBOAIMonUserGroupValue.S_GroupName).append(" like :groupName");
            parameter.put("groupName", groupName + "%");
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(IBOAIMonUserGroupValue.S_State).append(" = :pstate ");
        parameter.put("pstate", "U");

        return BOAIMonUserGroupEngine.getBeansCount(condition.toString(), parameter);

    }

    /**
     * 根据条件取得用户组信息
     * 
     * @param groupCode
     * @param groupName
     * @return
     * @throws Exception
     */
    public IBOAIMonUserGroupValue[] getUserGroupByCond(String groupCode, String groupName, int start, int end) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();

        if(StringUtils.isNotBlank(groupCode)) {
            condition.append(IBOAIMonUserGroupValue.S_GroupCode).append(" = :groupCode");
            parameter.put("groupCode", groupCode);
        }
        if(StringUtils.isNotBlank(groupName)) {
            if(StringUtils.isNotBlank(groupCode)) {
                condition.append(" and ");
            }
            condition.append(IBOAIMonUserGroupValue.S_GroupName).append(" like :groupName");
            parameter.put("groupName", groupName + "%");
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(IBOAIMonUserGroupValue.S_State).append(" = :pstate ");
        parameter.put("pstate", "U");

        return BOAIMonUserGroupEngine.getBeans(null, condition.toString(), parameter, start, end, false);
    }

    /**
     * 删除用户组信息
     * 
     * @param userGroupId
     * @throws Exception
     */
    public void delete(long userGroupId) throws Exception
    {
        IAIMonUserGroupDAO userGroupDao = (IAIMonUserGroupDAO) ServiceFactory.getService(IAIMonUserGroupDAO.class);
        userGroupDao.delete(userGroupId);
        GroupRole item = (GroupRole) MonCacheManager.get(AIMonUserGroupCacheImpl.class, userGroupId + "");
        if(item != null)
            item.setEnable(false);
    }

    /**
     * 从数据库读取用户组对象并封装
     * @param id
     * @return
     * @throws Exception
     */
    public GroupRole getUserGroupByIdFromDb(String id) throws Exception
    {
        IAIMonUserGroupDAO userGroupDao = (IAIMonUserGroupDAO) ServiceFactory.getService(IAIMonUserGroupDAO.class);
        IBOAIMonUserGroupValue value = userGroupDao.getBeanById(Long.valueOf(id));
        return this.wrapperUserGroupByBean(value);
    }

    /**
     * 读取所有用户的组信息
     * @return
     * @throws Exception
     */
    public IBOAIMonUserGroupValue[] getAllUserGroupBean() throws Exception
    {
        IAIMonUserGroupDAO userGroupDao = (IAIMonUserGroupDAO) ServiceFactory.getService(IAIMonUserGroupDAO.class);
        return userGroupDao.getAllUserGroup();
    }

    /**
     * 根据主键取得用户组信息
     * 
     * @param userGroupId
     * @return
     * @throws Exception
     */
    public IBOAIMonUserGroupValue getBeanById(long userGroupId) throws Exception
    {
        IAIMonUserGroupDAO userGroupDao = (IAIMonUserGroupDAO) ServiceFactory.getService(IAIMonUserGroupDAO.class);
        return userGroupDao.getBeanById(userGroupId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.asiainfo.appframe.ext.monitor.service.interfaces.IAIMonUserGroupSV#getUserListByGroupId(long)
     */
    public IBOAIMonUserValue[] getUserListByGroupId(long userGroupId) throws Exception
    {
        StringBuilder sqlStr = new StringBuilder();
        sqlStr.append("select mu.user_id,mu.user_name,mu.user_code,mu.state,mu.notes ").append(" from ai_mon_user mu, ai_mon_user_group_rel mugr ")
                .append(" where mu.user_id = mugr.user_id ").append(" and mugr.user_group_id = :USER_GROUP_ID  and mu.state = :STATE ");

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("USER_GROUP_ID", String.valueOf(userGroupId));
        paramMap.put("STATE", UserPrivConst.USER_STATE_U);

        BOAIMonUserBean[] userBeanArr = BOAIMonUserEngine.getBeansFromSql(sqlStr.toString(), paramMap);

        return userBeanArr;
    }

    /**
     * 查询未绑定该用户组的用户信息
     * 
     * @param userGroupId
     * @return
     * @throws Exception
     */
    public IBOAIMonUserValue[] getRelUserListByGroupId(long userGroupId) throws Exception
    {
        StringBuilder sqlStr = new StringBuilder();
        sqlStr.append("select mu.user_id,mu.user_name,mu.user_code,mu.state,mu.notes ").append(" from ai_mon_user mu ")
                .append(" where mu.state = :STATE ").append(" and mu.user_id not in(")
                .append(" select tt.user_id from ai_mon_user_group_rel tt where tt.user_group_id = :USER_GROUP_ID )");

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("USER_GROUP_ID", String.valueOf(userGroupId));
        paramMap.put("STATE", UserPrivConst.USER_STATE_U);

        BOAIMonUserBean[] userBeanArr = BOAIMonUserEngine.getBeansFromSql(sqlStr.toString(), paramMap);

        return userBeanArr;
    }

    /**
     * 查询未绑定该用户组的用户总数，便于分页
     * 
     * @param userGroupId
     * @return
     * @throws Exception
     */
    public long getRelUserListCntByGroupId(long userGroupId) throws Exception
    {
        StringBuilder sqlStr = new StringBuilder();
        sqlStr.append("select mu.user_id,mu.user_name,mu.user_code,mu.state,mu.notes ").append(" from ai_mon_user mu ")
                .append(" where mu.state = :STATE ").append(" and mu.user_id not in(")
                .append(" select tt.user_id from ai_mon_user_group_rel tt where tt.user_group_id = :USER_GROUP_ID )");

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("USER_GROUP_ID", String.valueOf(userGroupId));
        paramMap.put("STATE", UserPrivConst.USER_STATE_U);

        BOAIMonUserBean[] userBeanArr = BOAIMonUserEngine.getBeansFromSql(sqlStr.toString(), paramMap);

        return userBeanArr.length;
    }
}
