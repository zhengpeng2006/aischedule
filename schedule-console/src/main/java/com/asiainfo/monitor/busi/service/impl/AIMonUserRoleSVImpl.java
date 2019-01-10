package com.asiainfo.monitor.busi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.monitor.busi.cache.AIMonUserRoleCheckListener;
import com.asiainfo.monitor.busi.cache.MonCacheManager;
import com.asiainfo.monitor.busi.cache.impl.AIMonUserRoleCacheImpl;
import com.asiainfo.monitor.busi.cache.impl.RoleDomain;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonUserGroupRelDAO;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonUserGroupRoleDAO;
import com.asiainfo.monitor.busi.dao.interfaces.IAIMonUserRoleDAO;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserRoleEntityRelSV;
import com.asiainfo.monitor.busi.service.interfaces.IAIMonUserRoleSV;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserRoleBean;
import com.asiainfo.monitor.exeframe.config.bo.BOAIMonUserRoleEngine;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupRelValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserGroupRoleRelValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserRoleEntityRelValue;
import com.asiainfo.monitor.exeframe.config.ivalues.IBOAIMonUserRoleValue;
import com.asiainfo.monitor.exeframe.controlmgr.common.UserPrivConst;
import com.asiainfo.monitor.tools.i18n.AIMonLocaleFactory;

public class AIMonUserRoleSVImpl implements IAIMonUserRoleSV
{

    /**
     * 删除用户角色信息
     * 
     * @param userRoleId
     * @throws Exception
     */
    public void delete(long userRoleId) throws Exception
    {
        IAIMonUserRoleDAO userRoleDao = (IAIMonUserRoleDAO) ServiceFactory.getService(IAIMonUserRoleDAO.class);
        userRoleDao.delete(userRoleId);
        RoleDomain item = (RoleDomain) MonCacheManager.get(AIMonUserRoleCacheImpl.class, userRoleId + "");
        if(item != null)
            item.setEnable(false);
    }

    /**
     * 读取所有用户角色信息
     * 
     * @return
     * @throws Exception
     */
    public IBOAIMonUserRoleValue[] getAllUserRoleBean() throws Exception
    {
        IAIMonUserRoleDAO userRoleDao = (IAIMonUserRoleDAO) ServiceFactory.getService(IAIMonUserRoleDAO.class);
        return userRoleDao.getAllUserRoleBean();
    }

    /**
     * 根据用户角色标识从数据库读取用户角色并简单封装
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public RoleDomain getRoleGuildByIdFromDb(String id) throws Exception
    {
        if(StringUtils.isBlank(id))
            return null;
        IBOAIMonUserRoleValue value = this.getBeanById(Long.valueOf(id));
        return this.wrapperRoleGuildByBean(value);
    }

    /**
     * 根据标识读取RoleGuild对象
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public RoleDomain getRoleGuildById(String id) throws Exception
    {
        if(StringUtils.isBlank(id))
            return null;
        RoleDomain result = (RoleDomain) MonCacheManager.get(AIMonUserRoleCacheImpl.class, id);
        if(result == null) {
            result = this.getRoleGuildByIdFromDb(id);
            if(!MonCacheManager.getCacheReadOnlySet() && result != null) {
                MonCacheManager.put(AIMonUserRoleCacheImpl.class, result.getId(), result);
            }
        }
        return result;
    }

    /**
     * 根据主键取得用户角色信息
     * 
     * @param userRoleId
     * @return
     * @throws Exception
     */
    public IBOAIMonUserRoleValue getBeanById(long userRoleId) throws Exception
    {
        IAIMonUserRoleDAO userRoleDao = (IAIMonUserRoleDAO) ServiceFactory.getService(IAIMonUserRoleDAO.class);
        return userRoleDao.getBeanById(userRoleId);
    }

    /**
     * 保存或修改用户角色信息
     * 
     * @param value
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserRoleValue value) throws Exception
    {
        boolean modify = value.isModified();
        IAIMonUserRoleDAO userRoleDao = (IAIMonUserRoleDAO) ServiceFactory.getService(IAIMonUserRoleDAO.class);
        if(value.isNew()) {
            IBOAIMonUserRoleValue[] roles = userRoleDao.getUserRoleByCond(value.getRoldCode(), "");
            if(roles != null && roles.length > 0)
                // 角色编码:"+value.getRoldCode()+",已经存在,无法新增!
                throw new Exception(AIMonLocaleFactory.getResource("MOS0000130", value.getRoldCode()));
        }
        userRoleDao.saveOrUpdate(value);
        if(modify) {
            RoleDomain item = (RoleDomain) MonCacheManager.get(AIMonUserRoleCacheImpl.class, value.getUserRoleId() + "");
            if(item != null)
                item.setEnable(false);
        } else {
            RoleDomain item = this.wrapperRoleGuildByBean(value);
            MonCacheManager.put(AIMonUserRoleCacheImpl.class, item.getId(), item);
        }
    }

    /**
     * 根据条件取得用户角色信息
     * 
     * @param roleCode
     * @param roleName
     * @return
     * @throws Exception
     */
    public IBOAIMonUserRoleValue[] getUserRoleByCond(String roleCode, String roleName) throws Exception
    {
        IAIMonUserRoleDAO userRoleDao = (IAIMonUserRoleDAO) ServiceFactory.getService(IAIMonUserRoleDAO.class);
        return userRoleDao.getUserRoleByCond(roleCode, roleName);
    }

    /**
     * 根据条件取得用户角色信息
     * 
     * @param roleCode
     * @param roleName
     * @return
     * @throws Exception
     */
    public IBOAIMonUserRoleValue[] getUserRoleByCond(String roleCode, String roleName, int start, int end) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();

        if(StringUtils.isNotBlank(roleCode)) {
            condition.append(IBOAIMonUserRoleValue.S_RoldCode).append(" = :roleCode");
            parameter.put("roleCode", roleCode);
        }
        if(StringUtils.isNotBlank(roleName)) {
            if(StringUtils.isNotBlank(roleCode)) {
                condition.append(" and ");
            }
            condition.append(IBOAIMonUserRoleValue.S_RoleName).append(" like :roleName");
            parameter.put("roleName", roleName + "%");
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(IBOAIMonUserRoleValue.S_State).append(" = :pstate ");
        parameter.put("pstate", "U");
        
        return BOAIMonUserRoleEngine.getBeans(null, condition.toString(), parameter, start, end, false);

    }

    /**
     * 根据条件取得用户角色记录总数
     * 
     * @param roleCode
     * @param roleName
     * @return
     * @throws Exception
     */
    public int getUserRoleCntByCond(String roleCode, String roleName) throws Exception
    {
        StringBuilder condition = new StringBuilder("");
        Map parameter = new HashMap();

        if(StringUtils.isNotBlank(roleCode)) {
            condition.append(IBOAIMonUserRoleValue.S_RoldCode).append(" = :roleCode");
            parameter.put("roleCode", roleCode);
        }
        if(StringUtils.isNotBlank(roleName)) {
            if(StringUtils.isNotBlank(roleCode)) {
                condition.append(" and ");
            }
            condition.append(IBOAIMonUserRoleValue.S_RoleName).append(" like :roleName");
            parameter.put("roleName", roleName + "%");
        }
        if(StringUtils.isNotBlank(condition.toString())) {
            condition.append(" and ");
        }
        condition.append(IBOAIMonUserRoleValue.S_State).append(" = :pstate ");
        parameter.put("pstate", "U");

        return BOAIMonUserRoleEngine.getBeansCount(condition.toString(), parameter);
    }

    /**
     * 将用户角色值对象封装
     * 
     * @param value
     * @return
     * @throws Exception
     */
    public RoleDomain wrapperRoleGuildByBean(IBOAIMonUserRoleValue value) throws Exception
    {
        RoleDomain result = null;
        if(value == null || StringUtils.isBlank(value.getState()) || value.getState().equals("E"))
            return result;
        result = new RoleDomain();
        result.setId(value.getUserRoleId() + "");
        result.setCode(value.getRoldCode());
        result.setName(value.getRoleName());
        result.setNotes(value.getNotes());
        result.setDomainId(value.getDomainId() + "");
        IAIMonUserRoleEntityRelSV entityRelSV = (IAIMonUserRoleEntityRelSV) ServiceFactory.getService(IAIMonUserRoleEntityRelSV.class);
        IBOAIMonUserRoleEntityRelValue[] entityValues = entityRelSV.getEntityIdByRoleId(result.getId());
        if(entityValues != null && entityValues.length > 0) {
            for(int k = 0; k < entityValues.length; k++) {
                result.addEntity(entityValues[k].getEntityId() + "");
            }
        }
        result.setCacheListener(new AIMonUserRoleCheckListener());
        return result;
    }

    /**
     * 根据用户Id获取角色信息
     * 
     * @param userId
     * @return IBOAIMonUserRoleValue[]
     * @throws Exception
     */
    public IBOAIMonUserRoleValue[] queryRolesByUserId(String userId) throws Exception
    {
        List result = null;
        IAIMonUserGroupRelDAO groupRelDao = (IAIMonUserGroupRelDAO) ServiceFactory.getService(IAIMonUserGroupRelDAO.class);
        IBOAIMonUserGroupRelValue groupRelValue = groupRelDao.getUserGroupRelatByUserId(userId);
        if(groupRelValue != null) {
            IAIMonUserGroupRoleDAO groupRoleDao = (IAIMonUserGroupRoleDAO) ServiceFactory.getService(IAIMonUserGroupRoleDAO.class);
            IBOAIMonUserGroupRoleRelValue[] groupRelRoleValues = groupRoleDao.getRoleIdByGroupId(groupRelValue.getUserGroupId() + "");
            IAIMonUserRoleDAO userRoleDao = (IAIMonUserRoleDAO) ServiceFactory.getService(IAIMonUserRoleDAO.class);
            if(groupRelRoleValues != null && groupRelRoleValues.length > 0) {
                result = new ArrayList();
                for(int i = 0; i < groupRelRoleValues.length; i++) {
                    IBOAIMonUserRoleValue value = userRoleDao.getBeanById(groupRelRoleValues[i].getUserRoleId());
                    result.add(value);
                }
            }
        }

        return (IBOAIMonUserRoleValue[]) result.toArray(new IBOAIMonUserRoleValue[0]);
    }

    /**
     * 保存或修改用户角色信息
     * 
     * @param value
     * @throws Exception
     */
    public void saveOrUpdate(IBOAIMonUserRoleValue[] values) throws Exception
    {
        boolean modify = values[0].isModified();
        IAIMonUserRoleDAO userRoleDao = (IAIMonUserRoleDAO) ServiceFactory.getService(IAIMonUserRoleDAO.class);
        userRoleDao.saveOrUpdate(values);
        for(int i = 0; i < values.length; i++) {
            if(modify) {
                RoleDomain item = (RoleDomain) MonCacheManager.get(AIMonUserRoleCacheImpl.class, values[i].getUserRoleId() + "");
                if(item != null)
                    item.setEnable(false);
            } else {
                RoleDomain item = this.wrapperRoleGuildByBean(values[i]);
                MonCacheManager.put(AIMonUserRoleCacheImpl.class, item.getId(), item);
            }

        }
    }

    public IBOAIMonUserRoleValue[] getUserRoleListByGroupId(String userGroupId) throws Exception
    {
        StringBuilder sqlStr = new StringBuilder();
        sqlStr.append("select mr.user_role_id,mr.role_name,mr.rold_code,mr.state,mr.notes ")
                .append(" from ai_mon_user_role mr, ai_mon_user_group_role_rel mugr ").append(" where mr.user_role_id = mugr.user_role_id ")
                .append(" and mugr.user_group_id = :USER_GROUP_ID  and mr.state = :STATE ");

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("USER_GROUP_ID", String.valueOf(userGroupId));
        paramMap.put("STATE", UserPrivConst.USER_STATE_U);

        IBOAIMonUserRoleValue[] userBeanArr = BOAIMonUserRoleEngine.getBeansFromSql(sqlStr.toString(), paramMap);

        return userBeanArr;

    }

    public IBOAIMonUserRoleValue[] getRelUserRoleListByGroupId(String userGroupId) throws Exception
    {
        StringBuilder sqlStr = new StringBuilder();
        sqlStr.append("select mu.user_role_id,mu.role_name,mu.rold_code,mu.state,mu.notes ").append(" from ai_mon_user_role mu ")
                .append(" where mu.state = :STATE ").append(" and mu.user_role_id not in(")
                .append(" select tt.user_role_id from ai_mon_user_group_role_rel tt where tt.user_group_id = :USER_GROUP_ID )");

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("USER_GROUP_ID", String.valueOf(userGroupId));
        paramMap.put("STATE", UserPrivConst.USER_STATE_U);

        BOAIMonUserRoleBean[] userBeanArr = BOAIMonUserRoleEngine.getBeansFromSql(sqlStr.toString(), paramMap);

        return userBeanArr;
    }
}
